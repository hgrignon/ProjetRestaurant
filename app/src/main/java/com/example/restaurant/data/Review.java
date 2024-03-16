package com.example.restaurant.data;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Review {
    String restaurantId;
    String auteur;
    float nbEtoiles;
    String avis;
    ArrayList<byte[]> pictures;

    public final static Collection<String> ReviewListView = Arrays.asList("restaurantId", "auteur", "nbEtoiles", "avis", "pictures");
    public final static Collection<String> ReviewStarsNumberView = Arrays.asList("restaurantId", "nbEtoiles");

    public static Review ReviewAdapter(ParseObject o) {
        return new Review(
                (String) o.get("restaurantId"),
                (String) o.get("auteur"),
                (int) o.get("nbEtoiles"),
                (String) o.get("avis"),
                (ArrayList<byte[]>) o.get("pictures")
        );
    }

    public static int ReviewStarsAdapter(ParseObject o) {
        return (int) o.get("nbEtoiles");
    }

    public Review(String restaurantId,String auteur, float nbEtoiles, String avis, ArrayList<byte[]> pictures) {
        this.restaurantId = restaurantId;
        this.auteur = auteur;
        this.nbEtoiles = nbEtoiles;
        this.avis = avis;
        this.pictures = pictures;
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

    public float getNbEtoiles() {
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

    public ArrayList<byte[]> getPictures() {
        return pictures;
    }

}
