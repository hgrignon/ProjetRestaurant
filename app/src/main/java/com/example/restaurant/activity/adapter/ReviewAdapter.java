package com.example.restaurant.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.ceylonlabs.imageviewpopup.ImagePopup;
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

            holder = new ReviewAdapter.ViewHolder();
            holder.auteur = convertView.findViewById(R.id.auteur);
            holder.avis = convertView.findViewById(R.id.Avis);
            holder.nombreEtoile = convertView.findViewById(R.id.nombreEtoileAvis);
            holder.images = convertView.findViewById(R.id.GalleryPhotoInComm);


            convertView.setTag(holder);
        } else {
            holder = (ReviewAdapter.ViewHolder) convertView.getTag();
        }

        final Review review = getItem(position);
        ArrayList<byte[]> listImages = review.getPictures();
        GalleryAdapterForComm galleryAdapter = new GalleryAdapterForComm(mContext,listImages);

        holder.auteur.setText(review.getAuteur());
        holder.nombreEtoile.setText(String.valueOf(review.getNbEtoiles()) + "/5");
        holder.avis.setText(review.getAvis());
        holder.images.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.images.setAdapter(galleryAdapter);
     /*   final ImagePopup imagePopup = new ImagePopup(mContext);
        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(imagePopup.getDrawable());
            }
        });
*/

        return convertView;
    }

}
