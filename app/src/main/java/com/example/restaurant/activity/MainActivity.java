package com.example.restaurant.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_details);

        RestaurantServices services = new RestaurantServicesImpl();
        Restaurant resto = services.parseRestaurant();


        ((TextView) findViewById(R.id.restaurantDescription)).setText(resto.getDescription());
        ((TextView) findViewById(R.id.restaurantName)).setText(resto.getNom());

        //((ImageView) findViewById(R.id.etoile1)).setImageDrawable(getResources().getDrawable(R.drawable.etoile_pleine));


    }
}
