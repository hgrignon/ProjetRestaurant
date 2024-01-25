package com.example.restaurant.services;

import android.util.Log;

import com.example.restaurant.data.Restaurant;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RestaurantServices {
    public Restaurant RestaurantAdapter(ParseObject o) {
        return new Restaurant((String) o.get("a"), (String) o.get("b"));
    }

    public ArrayList<Restaurant> parseRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.orderByDescending("createdAt");

        try {
            List<ParseObject> objects = query.find();
            for (ParseObject o : objects) {
                restaurants.add(RestaurantAdapter(o));
                Log.d("MainActivity",(String)o.get("nom"));}
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        return restaurants;
    }
}
