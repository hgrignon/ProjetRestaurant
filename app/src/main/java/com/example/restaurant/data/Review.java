package com.example.restaurant.data;

import com.parse.ParseObject;

public class Review {
    String restaurantId;
    String auteur;
    int nbEtoiles;
    String avis;
    // Photos

    public static Review ReviewAdapter(ParseObject o) {
        return new Review(
                (String) o.get("restaurantId"),
                (String) o.get("auteur"),
                (int) o.get("nbEtoiles"),
                (String) o.get("avis")
        );
    }

    public Review(String restaurantId,String auteur, int nbEtoiles, String avis) {
        this.restaurantId = restaurantId;
        this.auteur = auteur;
        this.nbEtoiles = nbEtoiles;
        this.avis = avis;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }
}
