package com.example.project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reserve {

    @SerializedName("user_id")
    int user_id;
    @SerializedName("order")
    int order;
    String discriminator;
    int num_conception;
    @SerializedName("filenames")
    List<String> filenames;


    public Reserve(int user_id, int order, String discriminator, int num_conception, List<String> filenames) {
        this.user_id = user_id;
        this.order = order;
        this.discriminator = discriminator;
        this.num_conception = num_conception;
        this.filenames = filenames;
    }

    public Reserve(int id) {
        this.user_id=id;
    }


    public List<String> getPhotos() {
        return filenames;
    }

    public void setPhotos(List<String> filenames) {
        this.filenames = filenames;
    }

    public int getId_user() {
        return user_id;
    }

    public void setId_user(int user_id) {
        this.user_id = user_id;
    }

    public int getOrderArticle() {
        return order;
    }

    public void setOrderArticle(int order) {
        this.order = order;
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
