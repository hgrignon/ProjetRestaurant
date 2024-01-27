package com.example.restaurant.services;

import com.example.restaurant.data.Restaurant;
import com.example.restaurant.placeholder.RestoPlaceholder;

import java.util.ArrayList;

public class RestaurantServicesImpl implements RestaurantServices {

    @Override
    public ArrayList<Restaurant> parseRestaurants() {
        return RestoPlaceholder.getRestaurantsList();
    }

    @Override
    public Restaurant parseRestaurant() {
        return RestoPlaceholder.getRestaurantDetails();
    }

    @Override
    public void addReview(String restaurantId) {

    }
}
