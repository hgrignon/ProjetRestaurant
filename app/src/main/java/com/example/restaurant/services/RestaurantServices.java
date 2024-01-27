package com.example.restaurant.services;

import com.example.restaurant.data.Restaurant;

import java.util.ArrayList;

public interface RestaurantServices {
    ArrayList<Restaurant> parseRestaurants();

    Restaurant parseRestaurant();

    void addReview(String restaurantId);
}
