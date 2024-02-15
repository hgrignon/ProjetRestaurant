package com.example.restaurant.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.activity.components.RestaurantListComponent;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.placeholder.RestoPlaceholder;

import java.util.ArrayList;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

public class MainActivity extends AppCompatActivity {

    private final RestaurantServices restaurantServices = new RestaurantServicesImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listRestaurant = (ListView) findViewById(R.id.listeRestau);
        //recup liste placeholder
        ArrayList<String> restaurants = initListeTemp();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, restaurants);
        listRestaurant.setAdapter(adapter);

        listRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getSelectedItem();
                Intent intentMain = new Intent(MainActivity.this ,
                        DetailsActivity.class);
                intentMain.putExtra("idObject",selectedItem);
                MainActivity.this.startActivity(intentMain);
            }
        });
    }

    public ArrayList<String> initListeTemp(){
        ArrayList<Restaurant> liste = restaurantServices.parseRestaurants();
        ArrayList<String> temp=new ArrayList();
        for(Restaurant restau : liste){
            temp.add(restau.getObjectId());
        }
        return temp;
    }
}
