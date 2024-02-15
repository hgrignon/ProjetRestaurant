package com.example.restaurant.placeholder;

import com.example.restaurant.data.Restaurant;

import java.util.ArrayList;

public class RestoPlaceholder {

    public static ArrayList<Restaurant> getRestaurantsList() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            restaurants.add(new Restaurant("resto" + i, 4f));
        }
        return restaurants;
    }

    public static Restaurant getRestaurantDetails() {
        return new Restaurant("resto détaillé", 4f, "4 adresse du resto",
                "Lorem ipsum dolor sit amet. Ab nisi excepturi hic dolores tempora est porro minima aut optio omnis et dolores harum est reiciendis consequatur. Est quia deleniti eum doloribus rerum 33 itaque placeat aut voluptatem maxime? Ea ratione sequi non dicta sunt non quaerat nobis est corporis galisum.\n" +
                "\n" +
                "Et voluptatem iure et dolorum iste in reiciendis quia sed assumenda suscipit id aperiam natus. Et perferendis labore ut dolores laudantium eos voluptate minima.\n" +
                "\n" +
                "Qui beatae iusto ab dolores fugit non temporibus quibusdam ut facilis illum cum eveniet doloribus quo repellat nesciunt qui voluptate doloremque? Ab unde minima et cupiditate voluptatibus et voluptas aliquam.\n");
    }
}
