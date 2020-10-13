package com.example.project.models;

import java.util.Date;

public class Photo {

    int id;
    String path;


    public int getId_photo() {
        return id;
    }
    public void setId_photo(int id) {
        this.id = id;
    }






    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }






  /*  public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }*/


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}
