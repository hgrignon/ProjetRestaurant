package com.example.restaurant.data;

import com.parse.ParseObject;

import org.osmdroid.util.GeoPoint;

public class Restaurant {
    String objectId;
    String nom;
    // 1 = 1/2 etoiles
    int nbEtoiles;
    String adresse;
    String description;
    Review avis; // Liste? jsp
    InfoReservations infos;
    Reservation resa; // Liste? jsp
    RestaurantLabel labels; // Liste? jsp

    GeoPoint position;

    //Image;
    //Menu

    public static Restaurant RestaurantAdapterList(ParseObject o, int nbEtoiles) {
        return new Restaurant(
                (String) o.getObjectId(),
                (String) o.get("nom"),
                nbEtoiles,
                (Double) o.get("latitude"),
                (Double) o.get("longitude")
        );
    }

    public static Restaurant RestaurantAdapterDetails(ParseObject o, int nbEtoiles) {
        return new Restaurant(
                (String) o.get("nom"),
                nbEtoiles,
                (String) o.get("adresse"),
                (String) o.get("description")
        );
    }

    public Restaurant(String objectId, String nom, int nbEtoiles, Double latitude, Double longitude) {
        this.objectId = objectId;
        this.nom = nom;
        this.nbEtoiles = nbEtoiles;
        if (latitude != null && longitude != null )
            this.position = new GeoPoint(latitude, longitude);
    }
    public Restaurant(String nom, int nbEtoiles) {
        this.nom = nom;
        this.nbEtoiles = nbEtoiles;
    }
    public Restaurant(String nom, int nbEtoiles, String adresse, String description) {
        this.nom = nom;
        this.nbEtoiles = nbEtoiles;
        this.adresse = adresse;
        this.description = description;
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
}
