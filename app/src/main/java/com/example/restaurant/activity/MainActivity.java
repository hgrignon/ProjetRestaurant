package com.example.restaurant.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.services.RestaurantServices;

import java.util.ArrayList;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final RestaurantServices restaurantServices = new RestaurantServices();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_details);

        RestaurantServices services = new RestaurantServicesImpl();
        Restaurant resto = services.parseRestaurant();


        ((TextView) findViewById(R.id.restaurantDescription)).setText(resto.getDescription() + "\n" +  resto.getAdresse());
        ((TextView) findViewById(R.id.restaurantName)).setText(resto.getNom());

        ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.restaurant_placeholder);

        // Get number of stars
        int stars = Math.round(resto.getEtoiles());
        String displayedStars = "";
        for (int i = 1; i<=5; i++){
            if (i<=stars)
                displayedStars += "⭐";
            else displayedStars += "★";
        }
        ((TextView) findViewById(R.id.stars)).setText(displayedStars);




    }
}
