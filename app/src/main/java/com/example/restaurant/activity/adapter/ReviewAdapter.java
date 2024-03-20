package com.example.restaurant.activity.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.bumptech.glide.Glide;
import com.example.restaurant.R;
import com.example.restaurant.data.Review;

import java.util.ArrayList;

public class ReviewAdapter extends ArrayAdapter<Review> {
    private LayoutInflater inflater;
    private Context mContext;

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        super(context, 0, reviews);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView auteur;
        TextView avis;
        TextView nombreEtoile;
        RecyclerView images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_review_list_component, viewGroup, false);

            holder = new ViewHolder();
            holder.auteur = convertView.findViewById(R.id.auteur);
            holder.avis = convertView.findViewById(R.id.Avis);
            holder.nombreEtoile = convertView.findViewById(R.id.nombreEtoileAvis);
            holder.images = convertView.findViewById(R.id.GalleryPhotoInComm);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Review review = getItem(position);
        ArrayList<byte[]> listImages = review.getPictures();
        GalleryAdapterForComm galleryAdapter = new GalleryAdapterForComm(mContext, listImages);

        holder.auteur.setText(review.getAuteur());
        holder.nombreEtoile.setText(String.valueOf(review.getNbEtoiles()/2) + "/5");
        holder.avis.setText(review.getAvis());
        holder.images.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.images.setAdapter(galleryAdapter);

        final int reviewPosition = position;
        holder.images.setAdapter(galleryAdapter);

        galleryAdapter.setOnItemClickListener(new GalleryAdapterForComm.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Review review = getItem(reviewPosition);
                Dialog settingsDialog = new Dialog(mContext);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                View dialogView = inflater.inflate(R.layout.image_popup_layout, null);
                settingsDialog.setContentView(dialogView);

                ImageView imageView = dialogView.findViewById(R.id.popupImageView);

                byte[] imageBytes = review.getPictures().get(position);
                Glide.with(mContext)
                        .load(imageBytes)
                        .into(imageView);

                settingsDialog.show();
            }
        });
        return convertView;
    }
}

