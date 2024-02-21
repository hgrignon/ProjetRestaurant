package com.example.restaurant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restaurant.R;
import com.example.restaurant.activity.adapter.RestaurantAdapter;
import com.example.restaurant.data.Restaurant;

import java.util.ArrayList;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

public class MainActivity extends AppCompatActivity {

    private final RestaurantServices restaurantServices = new RestaurantServicesImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ListView listRestaurant = (ListView) findViewById(R.id.listeRestau);
        ArrayList<Restaurant> restaurants = initListe();
        RestaurantAdapter adapter = new RestaurantAdapter(this, restaurants);
        listRestaurant.setAdapter(adapter);

    }

    public ArrayList<Restaurant> initListe(){
        return restaurantServices.parseRestaurants();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_map) {
            Intent intentMain = new Intent(MainActivity.this , MapActivity.class);
            MainActivity.this.startActivity(intentMain);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
