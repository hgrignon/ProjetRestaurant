package com.example.restaurant.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.services.RestaurantServices;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final RestaurantServices restaurantServices = new RestaurantServices();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Restaurant> restaurants = restaurantServices.parseRestaurants();
        Log.d("MainActivity","1 - " + restaurants.size());

    }
}
