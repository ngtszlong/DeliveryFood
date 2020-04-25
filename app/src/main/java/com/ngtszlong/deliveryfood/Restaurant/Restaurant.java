package com.ngtszlong.deliveryfood.Restaurant;

public class Restaurant {
    String type_eng;
    String restaurant_chi;
    String restaurant_eng;
    String image;

    public Restaurant() {

    }

    public Restaurant(String type_eng, String restaurant_chi, String restaurant_eng, String image) {
        this.type_eng = type_eng;
        this.restaurant_chi = restaurant_chi;
        this.restaurant_eng = restaurant_eng;
        this.image = image;
    }

    public String getType_eng() {
        return type_eng;
    }

    public void setType_eng(String type_eng) {
        this.type_eng = type_eng;
    }

    public String getRestaurant_chi() {
        return restaurant_chi;
    }

    public void setRestaurant_chi(String restaurant_chi) {
        this.restaurant_chi = restaurant_chi;
    }

    public String getRestaurant_eng() {
        return restaurant_eng;
    }

    public void setRestaurant_eng(String restaurant_eng) {
        this.restaurant_eng = restaurant_eng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}