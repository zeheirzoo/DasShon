package com.example.project.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Valid {

    @SerializedName("user_id")
    int user_id;
    @SerializedName("order")
    int order;
    String discriminator;
    int num_conception;


    public Valid(int user_id, int order, String discriminator, int num_conception) {
        this.user_id = user_id;
        this.order = order;
        this.discriminator = discriminator;
        this.num_conception = num_conception;

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

    @Override
    public String toString() {
        return "Valid{" +
                "user_id=" + user_id +
                ", order=" + order +
                ", discriminator='" + discriminator + '\'' +
                ", num_conception=" + num_conception +
                '}';
    }
}
