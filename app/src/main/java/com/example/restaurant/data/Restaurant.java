package com.example.restaurant.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.osmdroid.util.GeoPoint;

import java.util.Arrays;
import java.util.Collection;

public class Restaurant {
    String objectId;
    String nom;
    int nbEtoiles;
    String adresse;
    String description;
    RestaurantLabel labels;
    GeoPoint position;
    Bitmap image;
    int capacity;

    public final static Collection<String> RestaurantListView = Arrays.asList("objectId", "nom", "latitude", "longitude", "adresse");
    public final static Collection<String> RestaurantDetailsView = Arrays.asList("objectId", "nom", "adresse", "description", "image", "capacity");

    public static Restaurant RestaurantAdapterList(ParseObject o, int nbEtoiles) {
        return new Restaurant(
                (String) o.getObjectId(),
                (String) o.get("nom"),
                nbEtoiles,
                (Double) o.get("latitude"),
                (Double) o.get("longitude"),
                (String) o.get("adresse")
                );
    }

    public static Restaurant RestaurantAdapterDetails(ParseObject o, int nbEtoiles) {
        return new Restaurant(
                (String) o.get("nom"),
                nbEtoiles,
                (String) o.get("adresse"),
                (String) o.get("description"),
                (ParseFile) o.get("image"),
                (int) o.get("capacity")
        );
    }

    public Restaurant(String objectId, String nom, int nbEtoiles, Double latitude, Double longitude, String adresse) {
        this.objectId = objectId;
        this.nom = nom;
        this.nbEtoiles = nbEtoiles;
        if (latitude != null && longitude != null )
            this.position = new GeoPoint(latitude, longitude);
        this.adresse = adresse;
    }
    public Restaurant(String nom, int nbEtoiles) {
        this.nom = nom;
        this.nbEtoiles = nbEtoiles;
    }
    public Restaurant(String nom, int nbEtoiles, String adresse, String description, ParseFile image, int capacity) {
        this.nom = nom;
        this.nbEtoiles = nbEtoiles;
        this.adresse = adresse;
        this.description = description;
        this.setImage(image);
        this.capacity = capacity;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getNom() {
        return nom;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDescription() {
        return description;
    }

    public GeoPoint getPosition() {
        return position;
    }

    public Bitmap getImage() {
        return image;
    }

    private void setImage(ParseFile image) {
        if (image != null ) {
            try {
                this.image = BitmapFactory.decodeByteArray(image.getData(), 0, image.getData().length);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getCapacity() {
        return capacity;
    }
}
