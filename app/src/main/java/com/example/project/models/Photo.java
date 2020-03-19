package com.example.project.models;

import java.util.Date;

public class Photo {

    int id_photo;
    String path;
   // Date date;


    public int getId_photo() {
        return id_photo;
    }
    public void setId_photo(int id_photo) {
        this.id_photo = id_photo;
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
        return "Photo{" +
                "id_photo=" + id_photo +
                ", path='" + path + '\'' +
            //    ", date=" + date +
                '}';
    }
}
