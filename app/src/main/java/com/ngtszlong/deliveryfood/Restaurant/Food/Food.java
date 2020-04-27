package com.ngtszlong.deliveryfood.Restaurant.Food;

public class Food {
    String description_chi;
    String description_eng;
    String image_2;
    String image_main;
    String name_chi;
    String name_eng;
    String other_chi;
    String other_eng;
    String price;
    String restaurant_No;
    String restaurant_eng;
    String restaurant_chi;

    public Food() {
    }

    public Food(String description_chi, String description_eng, String image_2, String image_main, String name_chi, String name_eng, String other_chi, String other_eng, String price, String restaurant_No, String restaurant_eng, String restaurant_chi) {
        this.description_chi = description_chi;
        this.description_eng = description_eng;
        this.image_2 = image_2;
        this.image_main = image_main;
        this.name_chi = name_chi;
        this.name_eng = name_eng;
        this.other_chi = other_chi;
        this.other_eng = other_eng;
        this.price = price;
        this.restaurant_No = restaurant_No;
        this.restaurant_eng = restaurant_eng;
        this.restaurant_chi = restaurant_chi;
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

    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    public String getImage_main() {
        return image_main;
    }

    public void setImage_main(String image_main) {
        this.image_main = image_main;
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

    public String getOther_chi() {
        return other_chi;
    }

    public void setOther_chi(String other_chi) {
        this.other_chi = other_chi;
    }

    public String getOther_eng() {
        return other_eng;
    }

    public void setOther_eng(String other_eng) {
        this.other_eng = other_eng;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRestaurant_No() {
        return restaurant_No;
    }

    public void setRestaurant_No(String restaurant_No) {
        this.restaurant_No = restaurant_No;
    }

    public String getRestaurant_eng() {
        return restaurant_eng;
    }

    public void setRestaurant_eng(String restaurant_eng) {
        this.restaurant_eng = restaurant_eng;
    }

    public String getRestaurant_chi() {
        return restaurant_chi;
    }

    public void setRestaurant_chi(String restaurant_chi) {
        this.restaurant_chi = restaurant_chi;
    }
}
