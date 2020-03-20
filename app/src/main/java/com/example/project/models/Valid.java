package com.example.project.models;

public class Valid {

    int id_user;
    int orderArticle;
    String discriminator;
    int num_conception;


    public Valid(int id_user, int orderArticle, String discriminator, int num_conception) {
        this.id_user = id_user;
        this.orderArticle = orderArticle;
        this.discriminator = discriminator;
        this.num_conception = num_conception;

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
