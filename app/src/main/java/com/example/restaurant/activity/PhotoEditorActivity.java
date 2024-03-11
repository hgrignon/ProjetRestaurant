package com.example.restaurant.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.activity.adapter.EditingToolsAdapter;
import com.example.restaurant.enums.ToolType;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.ViewType;
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder;
import ja.burhanrashid52.photoeditor.shape.ShapeType;

public class PhotoEditorActivity extends AppCompatActivity implements OnPhotoEditorListener, ShapeBSFragment.Properties, View.OnClickListener, EditingToolsAdapter.OnItemSelected {
    private static final int PICK_REQUEST = 53   ;
    PhotoEditorView mPhotoEditorView;
    PhotoEditor mPhotoEditor;
    TextView mTxtCurrentTool;
    RecyclerView mRvTools;
    View mRvFilters;
    EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    ShapeBuilder mShapeBuilder;
    ShapeBSFragment mShapeBSFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);

        initViews();

        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);

        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mPhotoEditorView.getSource().setImageResource(R.drawable.restaurant_placeholder);

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




       /* mPhotoEditor.saveAsFile(filePath, new PhotoEditor.OnSaveListener() {
            @Override
            public void onSuccess(@NonNull String imagePath) {
                Log.e("PhotoEditor","Image Saved Successfully");
            }

            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("PhotoEditor","Failed to save Image");
            }
        }); */
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
        //mRootView = findViewById(R.id.rootView);

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


            //case R.id.imgSave:
              //  saveImage();
               // break;
            //case R.id.imgClose:
              //  onBackPressed();
                //break;
            //case R.id.imgShare:
              //  shareImage();
                //break;
            //case R.id.imgCamera:
              //  Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(cameraIntent, CAMERA_REQUEST);
                //break;

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
            case FILTER:
                mTxtCurrentTool.setText(R.string.label_filter);
                showFilter(true);
                break;
            case EMOJI:
                mEmojiBSFragment.show(getSupportFragmentManager(), mEmojiBSFragment.getTag());
                break;
            case STICKER:
                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
                break;*/
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
}
