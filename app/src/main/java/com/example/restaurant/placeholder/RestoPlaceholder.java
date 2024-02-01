package com.example.restaurant.placeholder;

import com.example.restaurant.data.Restaurant;

import java.util.ArrayList;

public class RestoPlaceholder {

    public static ArrayList<Restaurant> getRestaurantsList() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            restaurants.add(new Restaurant("resto" + i, 4));
        }
        return restaurants;
    }

    public static Restaurant getRestaurantDetails() {
        return new Restaurant("resto détaillé", 4, "4 adresse du resto", "la description description description");
    }
}
