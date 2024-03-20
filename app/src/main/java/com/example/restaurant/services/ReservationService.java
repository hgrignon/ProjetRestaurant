package com.example.restaurant.services;

import java.util.HashMap;

public interface ReservationService {
    HashMap<String, Integer> computeCurrentCapacityForEachDays(String restaurantId, int capacity);

    void addReservation(String restaurantId, String date, int persNb, String name);
}
