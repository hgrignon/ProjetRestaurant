package com.example.restaurant.services;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    @Override
    public HashMap<String, Integer> computeCurrentCapacityForEachDays(String restaurantId, int capacity) {
        HashMap<String, Integer> daysAndCapacity = new HashMap<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reservation");
        query.selectKeys(Arrays.asList("objectId", "nbPers", "day"));
        query.orderByDescending("createdAt");
        query.whereEqualTo("restaurantId", restaurantId);

        try {
            List<ParseObject> objects = query.find();
            for (ParseObject o : objects) {
                if (daysAndCapacity.putIfAbsent((String) o.get("day"), (int) o.get("nbPers")) == null);
                else daysAndCapacity.computeIfPresent((String) o.get("day"), (k,v) -> v + (int) o.get("nbPers"));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return daysAndCapacity;
    }

    @Override
    public void addReservation(String restaurantId, String date, int persNb, String name) {

        ParseObject parseObject = new ParseObject("Reservation");

        parseObject.put("restaurantId", restaurantId);
        parseObject.put("day", date);
        parseObject.put("nbPers", persNb);
        parseObject.put("name", name);

        try{
            parseObject.save();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
