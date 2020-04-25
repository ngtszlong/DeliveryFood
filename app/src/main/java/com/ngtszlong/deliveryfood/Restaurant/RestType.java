package com.ngtszlong.deliveryfood.Restaurant;

public class RestType {
    String type_chi;
    String type_eng;
    String image;

    public RestType(String type_chi, String type_eng, String image) {
        this.type_chi = type_chi;
        this.type_eng = type_eng;
        this.image = image;
    }

    public RestType() { }

    public String getType_chi() {
        return type_chi;
    }

    public void setType_chi(String type_chi) {
        this.type_chi = type_chi;
    }

    public String getType_eng() {
        return type_eng;
    }

    public void setType_eng(String type_eng) {
        this.type_eng = type_eng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
