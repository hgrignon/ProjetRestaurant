package com.example.restaurant.activity.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private LayoutInflater inflater;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurant) {
        super(context, 0, restaurant);
        inflater = LayoutInflater.from(context);
    }
    private static class ViewHolder {
        TextView titreRestau;
        TextView adresse;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_restaurant_list_component, viewGroup, false);
            holder.titreRestau = convertView.findViewById(R.id.TitreRestau);
            holder.adresse = convertView.findViewById(R.id.Adresse);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant restaurant = getItem(position);
        holder.titreRestau.setText(restaurant.getNom());
        holder.adresse.setText(restaurant.getAdresse());

        // Définissez d'autres attributs de votre restaurant

        return convertView;
    }
}
