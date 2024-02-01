package com.example.restaurant.services;

import android.util.Log;

import com.example.restaurant.data.Restaurant;
import com.example.restaurant.placeholder.RestoPlaceholder;

import java.util.ArrayList;
import android.util.Log;

import com.example.restaurant.data.Restaurant;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RestaurantServicesImpl implements RestaurantServices {

    public Restaurant RestaurantAdapter(ParseObject o) {
        return new Restaurant((String) o.get("nom"), (String) o.get("description"));
    }

    @Override
    public ArrayList<Restaurant> parseRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.orderByDescending("createdAt");

        try {
            List<ParseObject> objects = query.find();
            for (ParseObject o : objects) {
                restaurants.add(RestaurantAdapter(o));
                Log.d("MainActivity",(String)o.get("nom"));
                Log.d("MainActivity",(String)o.get("description"));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return restaurants;
    }

    @Override
    public Restaurant parseRestaurant(String restaurantId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.whereEqualTo("objectId", restaurantId);
        Restaurant restaurant;

        try {
            ParseObject object = query.getFirst();
            restaurant = RestaurantAdapter(object);
            Log.d("MainActivity",(String)object.get("nom"));
            Log.d("MainActivity",(String)restaurant.getNom());
            Log.d("MainActivity",(String)object.get("description"));
            Log.d("MainActivity",(String)restaurant.getDescription());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return restaurant;
    }

    @Override
    public void addReview(String restaurantId) {

    }
}
