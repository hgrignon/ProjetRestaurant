package com.example.restaurant.activity;

import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.restaurant.R;
import com.example.restaurant.activity.adapter.EditingToolsAdapter;
import com.example.restaurant.enums.ToolType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.io.IOException;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.ViewType;
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder;
import ja.burhanrashid52.photoeditor.shape.ShapeType;

public class PhotoEditorActivity extends AppCompatActivity implements OnPhotoEditorListener, FilterListener, StickerBSFragment.StickerListener, ShapeBSFragment.Properties, View.OnClickListener, EditingToolsAdapter.OnItemSelected {
    private static final int PICK_REQUEST = 53   ;
    public static final int READ_WRITE_STORAGE = 52;
    String saveUrl;
    PhotoEditorView mPhotoEditorView;
    PhotoEditor mPhotoEditor;
    TextView mTxtCurrentTool;
    RecyclerView mRvTools;
    RecyclerView mRvFilters;
    EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    ShapeBuilder mShapeBuilder;
    ShapeBSFragment mShapeBSFragment;
    StickerBSFragment mStickerBSFragment;
    boolean mIsFilterVisible = false;
    FileSaveHelper mSaveFileHelper;
    ProgressDialog mProgressDialog = null;
    ConstraintSet mConstraintSet = new ConstraintSet();
    ConstraintLayout mRootView;
    FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            saveUrl="Not Found";
        }
        else{
            saveUrl = extras.getString("imagePath");
        }

        initViews();

        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);

        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvFilters.setLayoutManager(llmFilters);
        mRvFilters.setAdapter(mFilterViewAdapter);

        mPhotoEditorView = findViewById(R.id.photoEditorView);
        Glide.with(this).load(saveUrl).into(mPhotoEditorView.getSource());
        //mPhotoEditorView.getSource().setImageResource(R.drawable.restaurant_placeholder);

        mStickerBSFragment = new StickerBSFragment();
        mStickerBSFragment.setStickerListener(this);

        Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "font/emojione-android.ttf");

        mShapeBSFragment = new ShapeBSFragment();
        mShapeBSFragment.setPropertiesChangeListener(this);

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true)
                .setClipSourceImage(true)
                .setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build();


        mPhotoEditor.setOnPhotoEditorListener(this);
        mSaveFileHelper = new FileSaveHelper(this);
    }

    @Override
    public void onAddViewListener(@Nullable ViewType viewType, int i) {
    }

    @Override
    public void onEditTextChangeListener(@Nullable View view, @Nullable String s, int i) {

    }

    @Override
    public void onRemoveViewListener(@Nullable ViewType viewType, int i) {

    }

    @Override
    public void onStartViewChangeListener(@Nullable ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(@Nullable ViewType viewType) {

    }

    @Override
    public void onTouchSourceImage(@Nullable MotionEvent motionEvent) {

    }


    private void initViews() {
        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mTxtCurrentTool = findViewById(R.id.txtCurrentTool);
        mRvTools = findViewById(R.id.rvConstraintTools);
        mRvFilters = findViewById(R.id.rvFilterView);
        mRootView = findViewById(R.id.rootView);

        ImageView imgUndo = findViewById(R.id.imgUndo);
        imgUndo.setOnClickListener(this);

        ImageView imgRedo = findViewById(R.id.imgRedo);
        imgRedo.setOnClickListener(this);

        ImageView imgCamera = findViewById(R.id.imgCamera);
        imgCamera.setOnClickListener(this);

        ImageView imgGallery = findViewById(R.id.imgGallery);
        imgGallery.setOnClickListener(this);

        ImageView imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);

        ImageView imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgUndo){
                mPhotoEditor.undo();}
        else if (v.getId() == R.id.imgRedo) {
            mPhotoEditor.redo();
        }
        else if(v.getId() == R.id.imgGallery) {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_REQUEST);
        }
        else if(v.getId() == R.id.imgSave){
            saveImage(saveUrl, new SaveImageCallback() {
                @Override
                public void onSaveImageComplete() {
                    onBackPressed();
                }
            });
        }
        else if(v.getId() == R.id.imgClose){
            onBackPressed();
        }
    }


    @SuppressLint("MissingPermission")
    private void saveImage(String path, SaveImageCallback callback) {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showLoading("Saving...");
            Uri pathuri = Uri.parse(path);
            try {
                File file = new File(pathuri.getPath());
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();

                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideLoading();
                        showSnackbar("Image Saved Successfully");
                        mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                        if (callback!=null){
                            callback.onSaveImageComplete();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideLoading();
                        showSnackbar("Failed to save Image");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                hideLoading();
                showSnackbar(e.getMessage());
            }
        }
    }


    public boolean requestPermission(String permission) {
        boolean isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{permission}, READ_WRITE_STORAGE);
        }
        return isGranted;
    }
    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showSnackbar(@NonNull String message) {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onToolSelected(ToolType toolType) {
        switch (toolType) {
            case SHAPE:
                mPhotoEditor.setBrushDrawingMode(true);
                mShapeBuilder = new ShapeBuilder();
                mPhotoEditor.setShape(mShapeBuilder);
                mTxtCurrentTool.setText(R.string.label_shape);
                showBottomSheetDialogFragment(mShapeBSFragment);
                break;
            /*case TEXT:
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
                    @Override
                    public void onDone(String inputText, int colorCode) {
                        mPhotoEditor.addText(inputText, colorCode);
                        mTxtCurrentTool.setText(R.string.label_text);
                    }
                });
                break;
            case ERASER:
                mPhotoEditor.brushEraser();
                mTxtCurrentTool.setText(R.string.label_eraser);
                break;

            case EMOJI:
                mEmojiBSFragment.show(getSupportFragmentManager(), mEmojiBSFragment.getTag());
                break;*/
            case FILTER:
                mTxtCurrentTool.setText(R.string.label_filter);
                showFilter(true);
                break;
            case STICKER:
                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
                break;
        }

    }




    private void showBottomSheetDialogFragment(BottomSheetDialogFragment fragment) {
        if (fragment == null || fragment.isAdded()) {
            return;
        }
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeColor(colorCode));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeOpacity(opacity));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onShapeSizeChanged(int shapeSize) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeSize((float) shapeSize));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onShapePicked(ShapeType shapeType) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeType(shapeType));
    }

    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
        mTxtCurrentTool.setText(R.string.label_sticker);
    }

    protected void showLoading(String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void showFilter(boolean isVisible) {
        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        int rvFilterId = mRvFilters.getId();

        if (isVisible) {
            mConstraintSet.clear(rvFilterId, ConstraintSet.START);
            mConstraintSet.connect(rvFilterId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(rvFilterId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        } else {
            mConstraintSet.connect(rvFilterId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(rvFilterId, ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);
    }


    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }
}
