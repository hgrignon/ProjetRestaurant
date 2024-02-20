package com.example.restaurant.activity;

import android.content.Intent;
import android.os.Bundle;
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
        ArrayList<String> restaurants = initListe();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, restaurants);
        listRestaurant.setAdapter(adapter);

        listRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Intent intentMain = new Intent(MainActivity.this ,
                        RestaurantDetailsActivity.class);
                intentMain.putExtra("idObject",selectedItem);
                MainActivity.this.startActivity(intentMain);
            }
        });
    }

    public ArrayList<String> initListe(){
        ArrayList<Restaurant> liste = restaurantServices.parseRestaurants();
        ArrayList<String> temp = new ArrayList();
        for(Restaurant restau : liste){
            temp.add(restau.getObjectId());
        }
        return temp;
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
