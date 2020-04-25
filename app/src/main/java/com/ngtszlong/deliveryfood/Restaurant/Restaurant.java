package com.ngtszlong.deliveryfood.Restaurant;

public class Restaurant {
    private String restaurant_No;
    private String type_eng;
    private String type_chi;
    private String restaurant_chi;
    private String restaurant_eng;
    private String image;
    private String description_chi;
    private String description_eng;
    private String location_chi;
    private String location_eng;
    private String delivery_time;

    public Restaurant() {

    }

    public Restaurant(String restaurant_No, String type_eng, String type_chi, String restaurant_chi, String restaurant_eng, String image, String description_chi, String description_eng, String location_chi, String location_eng, String delivery_time) {
        this.restaurant_No = restaurant_No;
        this.type_eng = type_eng;
        this.type_chi = type_chi;
        this.restaurant_chi = restaurant_chi;
        this.restaurant_eng = restaurant_eng;
        this.image = image;
        this.description_chi = description_chi;
        this.description_eng = description_eng;
        this.location_chi = location_chi;
        this.location_eng = location_eng;
        this.delivery_time = delivery_time;
    }

    public String getRestaurant_No() {
        return restaurant_No;
    }

    public void setRestaurant_No(String restaurant_No) {
        this.restaurant_No = restaurant_No;
    }

    public String getType_eng() {
        return type_eng;
    }

    public void setType_eng(String type_eng) {
        this.type_eng = type_eng;
    }

    public String getType_chi() {
        return type_chi;
    }

    public void setType_chi(String type_chi) {
        this.type_chi = type_chi;
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

    public String getDescription_chi() {
        return description_chi;
    }

    public void setDescription_chi(String description_chi) {
        this.description_chi = description_chi;
    }

    public String getDescription_eng() {
        return description_eng;
    }

    public void setDescription_eng(String description_eng) {
        this.description_eng = description_eng;
    }

    public String getLocation_chi() {
        return location_chi;
    }

    public void setLocation_chi(String location_chi) {
        this.location_chi = location_chi;
    }

    public String getLocation_eng() {
        return location_eng;
    }

    public void setLocation_eng(String location_eng) {
        this.location_eng = location_eng;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }
}