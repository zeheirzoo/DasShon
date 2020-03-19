package com.example.project.models;

import java.sql.Timestamp;
import java.util.Date;

public class User {
    int id ;
   // String name;
    String nom ;
    String prenom;
    String discriminator;
    String numTel;
    String NumAssurance;
    String email;
    //Date email_verified_at;
    String password;
    Date date;

    String _token;



    public User(int id, String nom, String prenom, String discriminator, String numTel, String numAssurance, String email, String password, String _token) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.discriminator = discriminator;
        this.numTel = numTel;
        NumAssurance = numAssurance;
        this.email = email;
        this.password = password;
        this._token = _token;
    }

    public User(String nom, String password) {
        this.nom = nom;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }





   /* public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }*/




    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }




    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }





    public String getNumTel() {
        return numTel;
    }
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }




    public String getNumAssurance() {
        return NumAssurance;
    }
    public void setNumAssurance(String numAssurance) {
        NumAssurance = numAssurance;
    }





    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }






    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }





    public String getDiscriminator() {
        return discriminator;
    }
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }





    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
          //      ", name='" + name + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", discriminator='" + discriminator + '\'' +
                ", numTel='" + numTel + '\'' +
                ", NumAssurance='" + NumAssurance + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }
}
