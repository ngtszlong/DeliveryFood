package com.ngtszlong.deliveryfood.Order;

public class Order {
    String restaurant_No;
    String restaurantname_chi;
    String restaurantname_eng;
    String name_chi;
    String name_eng;
    String price;
    String uid;
    String time;

    public Order() {
    }

    public Order(String restaurant_No, String restaurantname_chi, String restaurantname_eng, String name_chi, String name_eng, String price, String uid, String time) {
        this.restaurant_No = restaurant_No;
        this.restaurantname_chi = restaurantname_chi;
        this.restaurantname_eng = restaurantname_eng;
        this.name_chi = name_chi;
        this.name_eng = name_eng;
        this.price = price;
        this.uid = uid;
        this.time = time;
    }

    public String getRestaurant_No() {
        return restaurant_No;
    }

    public void setRestaurant_No(String restaurant_No) {
        this.restaurant_No = restaurant_No;
    }

    public String getRestaurantname_chi() {
        return restaurantname_chi;
    }

    public void setRestaurantname_chi(String restaurantname_chi) {
        this.restaurantname_chi = restaurantname_chi;
    }

    public String getRestaurantname_eng() {
        return restaurantname_eng;
    }

    public void setRestaurantname_eng(String restaurantname_eng) {
        this.restaurantname_eng = restaurantname_eng;
    }

    public String getName_chi() {
        return name_chi;
    }

    public void setName_chi(String name_chi) {
        this.name_chi = name_chi;
    }

    public String getName_eng() {
        return name_eng;
    }

    public void setName_eng(String name_eng) {
        this.name_eng = name_eng;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

