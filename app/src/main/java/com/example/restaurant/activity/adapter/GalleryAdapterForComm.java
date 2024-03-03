package com.example.restaurant.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurant.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class GalleryAdapterForComm extends RecyclerView.Adapter<GalleryAdapterForComm.ImageViewHolder> {

    private Context mContext;
    private ArrayList<byte[]> mImageList;

    public GalleryAdapterForComm(Context context, ArrayList<byte[]> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_image_list, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        byte[] imageBytes = mImageList.get(position);
        Glide.with(mContext)
                .load(imageBytes)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(mImageList == null){
            return 0;
        }
        return mImageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageReviewShow);
        }
    }
}

