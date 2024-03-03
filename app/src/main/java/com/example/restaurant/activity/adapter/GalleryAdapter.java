package com.example.restaurant.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.data.Review;

import java.io.IOException;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private  ArrayList<Uri> gallery;
    public GalleryAdapter(Context context, ArrayList<Uri> gallery) {
        super();
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.gallery = gallery;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_image_list, viewGroup, false);
            holder = new GalleryAdapter.ImageViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageReviewShow);
            convertView.setTag(holder);
        } else {
                holder = (GalleryAdapter.ImageViewHolder) convertView.getTag();
        }

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),gallery.get(position) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bitmap bitmap2 = bitmap.copy(bitmap.getConfig(), true);

        holder.imageView.setImageBitmap(bitmap2);
        return convertView;
    }

}
