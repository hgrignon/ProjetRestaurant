package com.example.restaurant.services;

import static com.example.restaurant.data.Restaurant.RestaurantAdapterDetails;
import static com.example.restaurant.data.Restaurant.RestaurantAdapterList;
import static com.example.restaurant.data.Review.ReviewAdapter;

import android.util.Log;

import com.example.restaurant.data.Restaurant;

import java.util.ArrayList;

import com.example.restaurant.data.Review;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class RestaurantServicesImpl implements RestaurantServices {

    @Override
    public ArrayList<Restaurant> parseRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.orderByDescending("createdAt");

        try {
            List<ParseObject> objects = query.find();
            StringBuilder log = new StringBuilder();
            for (ParseObject o : objects) {
                restaurants.add(RestaurantAdapterList(o, computeNbEtoilesAverage(o.getObjectId())));
                log.append(" | ").append(o.getObjectId()).append(" : ").append((String) o.get("nom"));
            }
            Log.d("RestaurantServices", "parse all restaurants ->" + log);
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
            restaurant = RestaurantAdapterDetails(object, computeNbEtoilesAverage(object.getObjectId()));
            Log.d("RestaurantServices", "parse one restaurant " + object.getObjectId() + " : " + (String)object.get("nom"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return restaurant;
    }

    private int computeNbEtoilesAverage(String restaurantId) {
        ArrayList<Review> reviews = parseReviews(restaurantId);
        if (reviews.size() == 0)
            return 0;
        int average = 0;
        for (Review review : reviews) {
            average += review.getNbEtoiles();
        }
        return (int) (average / reviews.size());
    }

    public ArrayList<Review> parseReviews(String restaurantId) {
        ArrayList<Review> reviews = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Review");
        query.orderByDescending("createdAt");
        query.whereEqualTo("restaurantId", restaurantId);

        try {
            List<ParseObject> objects = query.find();
            StringBuilder log = new StringBuilder();
            for (ParseObject o : objects) {
                reviews.add(ReviewAdapter(o));
                log.append(" | ").append(o.getObjectId());
            }
            Log.d("RestaurantServices", "parse all " + restaurantId + " reviews ->" + log);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return reviews;
    }

    @Override
    public void addReview(Review review) {
        ParseObject parseObject = new ParseObject("Review");

        parseObject.put("restaurantId", review.getRestaurantId());
        parseObject.put("auteur", review.getAuteur());
        parseObject.put("nbEtoiles", review.getNbEtoiles());
        parseObject.put("avis", review.getAvis());

        try{
            parseObject.save();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
