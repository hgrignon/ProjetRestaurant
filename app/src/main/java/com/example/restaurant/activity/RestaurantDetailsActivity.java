package com.example.restaurant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

public class RestaurantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;

        setContentView(R.layout.restaurant_details);

        RestaurantServices services = new RestaurantServicesImpl();
        String id = getIntent().getStringExtra("idObject");
        Restaurant resto = services.parseRestaurant(id);


        ((TextView) findViewById(R.id.restaurantDescription)).setText(resto.getDescription() + "\n" +  resto.getAdresse());
        ((TextView) findViewById(R.id.restaurantName)).setText(resto.getNom());
        setDetailsImage(resto);

        // Get number of stars
        int stars = Math.round(resto.getNbEtoiles());
        String displayedStars = "";
        for (int i = 1; i<=5; i++){
            if (i<=stars)
                displayedStars += "⭐";
            else displayedStars += "★";
        }
        ((TextView) findViewById(R.id.stars)).setText(displayedStars);
        findViewById(R.id.stars).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra("idRestaurant",id);
                RestaurantDetailsActivity.this.startActivity(intent);
            }
        });

    }

    private void setDetailsImage(Restaurant resto) {
        if (resto.getImage() != null) {
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(resto.getImage());
        }
        else
            ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.restaurant_placeholder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_list_resto) {
            Intent intentMain = new Intent(RestaurantDetailsActivity.this , MainActivity.class);
            RestaurantDetailsActivity.this.startActivity(intentMain);
            return true;
        } else
        if (item.getItemId() == R.id.menu_map) {
            Intent intentMain = new Intent(RestaurantDetailsActivity.this , MapActivity.class);
            RestaurantDetailsActivity.this.startActivity(intentMain);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
