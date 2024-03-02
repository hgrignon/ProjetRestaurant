package com.example.restaurant.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

public class PhotoEditorActivity extends AppCompatActivity
{


    public RestaurantServices restaurantServices = new RestaurantServicesImpl();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);
    }
}
