package com.example.restaurant.activity.editor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.restaurant.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;
import java.util.List;

public class StickerBSFragment extends BottomSheetDialogFragment {

    private StickerListener mStickerListener;

    public void setStickerListener(StickerListener stickerListener) {
        mStickerListener = stickerListener;
    }

    public interface StickerListener {
        void onStickerClick(Bitmap bitmap);
    }

    private final BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetCallback() {
        @Override
        public void onStateChanged(View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(View bottomSheet, float slideOffset) {
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();


        if (behavior != null && behavior instanceof BottomSheetBehavior<?>) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        RecyclerView rvEmoji = contentView.findViewById(R.id.rvEmoji);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvEmoji.setLayoutManager(gridLayoutManager);

        StickerAdapter stickerAdapter = new StickerAdapter();
        rvEmoji.setAdapter(stickerAdapter);
        rvEmoji.setHasFixedSize(true);
        rvEmoji.setItemViewCacheSize(stickerPathList.size());
    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sticker, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // Load sticker image from remote url
            Glide.with(requireContext())
                    .asBitmap()
                    .load(stickerPathList.get(position))
                    .into(holder.imgSticker);
        }

        @Override
        public int getItemCount() {
            return stickerPathList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgSticker;

            public ViewHolder(View itemView) {
                super(itemView);
                imgSticker = itemView.findViewById(R.id.imgSticker);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mStickerListener != null) {
                            Glide.with(requireContext())
                                    .asBitmap()
                                    .load(stickerPathList.get(getLayoutPosition()))
                                    .into(new CustomTarget<Bitmap>(256, 256) {
                                        @Override
                                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                            mStickerListener.onStickerClick(resource);
                                        }

                                        @Override
                                        public void onLoadCleared(Drawable placeholder) {
                                        }
                                    });
                        }
                        dismiss();
                    }
                });
            }
        }
    }

    public static final List<String> stickerPathList = Arrays.asList(
            "https://cdn-icons-png.flaticon.com/256/4392/4392452.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392455.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392459.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392462.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392465.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392467.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392469.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392471.png",
            "https://cdn-icons-png.flaticon.com/256/4392/4392522.png"
    );
}
