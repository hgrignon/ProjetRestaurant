package com.example.restaurant.activity.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurant.R;
import com.example.restaurant.activity.MainActivity;
import com.example.restaurant.activity.RestaurantDetailsActivity;
import com.example.restaurant.data.Restaurant;
import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private LayoutInflater inflater;
    private Context mContext;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurant) {
        super(context, 0, restaurant);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }


    private static class ViewHolder {
        TextView titreRestau;
        TextView adresse;
        Button button2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_restaurant_list_component, viewGroup, false);

            holder = new ViewHolder();
            holder.titreRestau = convertView.findViewById(R.id.TitreRestau);
            holder.adresse = convertView.findViewById(R.id.Adresse);
            holder.button2 = convertView.findViewById(R.id.button2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Restaurant restaurant = getItem(position);

        holder.titreRestau.setText(restaurant.getNom());
        holder.adresse.setText(restaurant.getAdresse());

        // Set the tag to the button to get the correct position on click
        holder.button2.setTag(position);

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Restaurant selectedItem = getItem(position);

                Log.d("aze :", selectedItem.getNom());

                Intent intent = new Intent(mContext, RestaurantDetailsActivity.class);
                intent.putExtra("idObject", selectedItem.getObjectId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
