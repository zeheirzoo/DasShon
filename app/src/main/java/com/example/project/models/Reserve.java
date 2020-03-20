package com.example.project.models;

import java.util.List;

public class Reserve {

    int id_user;
    int orderArticle;
    String discriminator;
    int num_conception;
    List<String> photos;


    public Reserve(int id_user, int orderArticle, String discriminator, int num_conception, List<String> photos) {
        this.id_user = id_user;
        this.orderArticle = orderArticle;
        this.discriminator = discriminator;
        this.num_conception = num_conception;
        this.photos = photos;
    }

    public Reserve(int id) {
        this.id_user=id;
    }


    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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
