package com.example.restaurant.data;

public class Restaurant {
    String objectId;
    String nom;
    Float etoiles;
    String adresse;
    String description;
    Review avis; // Liste? jsp
    InfoReservations infos;
    Reservation resa; // Liste? jsp
    RestaurantLabel labels; // Liste? jsp

    //Image;
    //Menu

    public Restaurant(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Restaurant(String nom, Float nbEtoiles) {
        this.nom = nom;
        this.etoiles = nbEtoiles;
    }

    public Restaurant(String nom, float nbEtoiles, String adresse, String description) {
        this.nom = nom;
        this.etoiles = nbEtoiles;
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

    public Float getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(Float etoiles) {
        this.etoiles = etoiles;
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
