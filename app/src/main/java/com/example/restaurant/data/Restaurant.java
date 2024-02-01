package com.example.restaurant.data;

import com.parse.ParseObject;

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

    //Image;
    //Menu

    public static Restaurant RestaurantAdapterList(ParseObject o, int nbEtoiles) {
        return new Restaurant(
                (String) o.get("nom"),
                nbEtoiles
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

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
