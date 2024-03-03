package com.example.restaurant.services;

import com.example.restaurant.data.Restaurant;
import com.example.restaurant.data.Review;

import java.util.ArrayList;

public interface RestaurantServices {
    ArrayList<Restaurant> parseRestaurants();

    Restaurant parseRestaurant(String restaurantId);

    void addReview(Review review);

    ArrayList<Review> parseReviews(String restaurantId);
}
