package com.example.project.models;

public class Reclamation {

    int id_reclamation;
    int article_id;
    int user_id;
    int fautif_id;


    public Reclamation(int article_id, int user_id, int fautif_id) {
        this.article_id = article_id;
        this.user_id = user_id;
        this.fautif_id = fautif_id;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id_reclamation=" + id_reclamation +
                ", article_id=" + article_id +
                ", user_id=" + user_id +
                ", fautif_id=" + fautif_id +
                '}';
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFautif_id() {
        return fautif_id;
    }

    public void setFautif_id(int fautif_id) {
        this.fautif_id = fautif_id;
    }
}
