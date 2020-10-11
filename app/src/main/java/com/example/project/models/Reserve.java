package com.example.project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reserve {

    @SerializedName("user_id")
    int user_id;
    @SerializedName("order")
    int orderArticle;
    String discriminator;
    int num_conception;
    @SerializedName("filenames")
    List<String> photos;


    public Reserve(int user_id, int orderArticle, String discriminator, int num_conception, List<String> photos) {
        this.user_id = user_id;
        this.orderArticle = orderArticle;
        this.discriminator = discriminator;
        this.num_conception = num_conception;
        this.photos = photos;
    }

    public Reserve(int id) {
        this.user_id=id;
    }


    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public int getId_user() {
        return user_id;
    }

    public void setId_user(int user_id) {
        this.user_id = user_id;
    }

    public int getOrderArticle() {
        return orderArticle;
    }

    public void setOrderArticle(int orderArticle) {
        this.orderArticle = orderArticle;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public int getNum_conception() {
        return num_conception;
    }

    public void setNum_conception(int num_conception) {
        this.num_conception = num_conception;
    }

}
