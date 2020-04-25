package com.ngtszlong.deliveryfood.Restaurant;

public class RestaurantFood {
    String restaurantNo;
    String Foodname_chi;
    String Foodname_eng;
    String timeslot;
    String price;

    public RestaurantFood() {
    }

    public RestaurantFood(String restaurantNo, String foodname_chi, String foodname_eng, String timeslot, String price) {
        this.restaurantNo = restaurantNo;
        Foodname_chi = foodname_chi;
        Foodname_eng = foodname_eng;
        this.timeslot = timeslot;
        this.price = price;
    }

    public String getRestaurantNo() {
        return restaurantNo;
    }

    public void setRestaurantNo(String restaurantNo) {
        this.restaurantNo = restaurantNo;
    }

    public String getFoodname_chi() {
        return Foodname_chi;
    }

    public void setFoodname_chi(String foodname_chi) {
        Foodname_chi = foodname_chi;
    }

    public String getFoodname_eng() {
        return Foodname_eng;
    }

    public void setFoodname_eng(String foodname_eng) {
        Foodname_eng = foodname_eng;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
