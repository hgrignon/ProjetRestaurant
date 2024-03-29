package com.example.restaurant.activity.editor;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileSaveHelper implements LifecycleObserver {
    private final ContentResolver mContentResolver;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<FileMeta> fileCreatedResult = new MutableLiveData<>();
    private OnFileCreateResult resultListener;
    private final Observer<FileMeta> observer = fileMeta -> {
        if (resultListener != null) {
            resultListener.onFileCreateResult(fileMeta.isCreated, fileMeta.filePath, fileMeta.error, fileMeta.uri);
        }
    };

    public FileSaveHelper(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public FileSaveHelper(AppCompatActivity activity) {
        this(activity.getContentResolver());
        addObserver(activity);
    }

    private void addObserver(LifecycleOwner lifecycleOwner) {
        fileCreatedResult.observe(lifecycleOwner, observer);
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void release() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public void createFile(String fileNameToSave, OnFileCreateResult listener) {
        resultListener = listener;
        executor.submit(() -> {
            Cursor cursor = null;
            try {
                ContentValues newImageDetails = new ContentValues();
                Uri imageCollection = buildUriCollection(newImageDetails);
                Uri editedImageUri = getEditedImageUri(fileNameToSave, newImageDetails, imageCollection);
                cursor = mContentResolver.query(editedImageUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    String filePath = cursor.getString(columnIndex);
                    updateResult(true, filePath, null, editedImageUri, newImageDetails);
                } else {
                    updateResult(false, null, "Cursor is null or empty", null, null);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                updateResult(false, null, ex.getMessage(), null, null);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }

    private Uri getEditedImageUri(String fileNameToSave, ContentValues newImageDetails, Uri imageCollection) throws IOException {
        newImageDetails.put(MediaStore.Images.Media.DISPLAY_NAME, fileNameToSave);
        Uri editedImageUri = mContentResolver.insert(imageCollection, newImageDetails);
        if (editedImageUri != null) {
            mContentResolver.openOutputStream(editedImageUri).close();
        }
        return editedImageUri;
    }

    @SuppressLint("InlinedApi")
    private Uri buildUriCollection(ContentValues newImageDetails) {
        Uri imageCollection;
        if (isSdkHigherThan28()) {
            imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            newImageDetails.put(MediaStore.Images.Media.IS_PENDING, 1);
        } else {
            imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        return imageCollection;
    }

    @SuppressLint("InlinedApi")
    public void notifyThatFileIsNowPubliclyAvailable(ContentResolver contentResolver) {
        if (isSdkHigherThan28()) {
            executor.submit(() -> {
                FileMeta value = fileCreatedResult.getValue();
                if (value != null && value.imageDetails != null) {
                    value.imageDetails.clear();
                    value.imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0);
                    contentResolver.update(value.uri, value.imageDetails, null, null);
                }
            });
        }
    }

    private void updateResult(boolean result, String filePath, String error, Uri uri, ContentValues newImageDetails) {
        fileCreatedResult.postValue(new FileMeta(result, filePath, uri, error, newImageDetails));
    }

    static class FileMeta {
        boolean isCreated;
        String filePath;
        Uri uri;
        String error;
        ContentValues imageDetails;

        FileMeta(boolean isCreated, String filePath, Uri uri, String error, ContentValues imageDetails) {
            this.isCreated = isCreated;
            this.filePath = filePath;
            this.uri = uri;
            this.error = error;
            this.imageDetails = imageDetails;
        }
    }

    interface OnFileCreateResult {
        void onFileCreateResult(boolean created, String filePath, String error, Uri uri);
    }

    public static boolean isSdkHigherThan28() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }
}
