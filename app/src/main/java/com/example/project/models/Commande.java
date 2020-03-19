package com.example.project.models;

import java.util.Date;

public class Commande {
    int num_conception;
    String ccl;
    String prenomClient;
    String numTelClient;
    String emailClient;
    Date date ;

    public Commande(String ccl, String prenomClient, String numTelClient, String emailClient, Date date) {
        this.ccl = ccl;
        this.prenomClient = prenomClient;
        this.numTelClient = numTelClient;
        this.emailClient = emailClient;
        this.date = date;
    }
    public int getNum_conception() {
        return num_conception;
    }
    public void setNum_conception(int num_conception) {
        this.num_conception = num_conception;
    }




    public String getCcl() {
        return ccl;
    }
    public void setCcl(String ccl) {
        this.ccl = ccl;
    }





    public String getPrenomClient() {
        return prenomClient;
    }
    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }






    public String getNumTelClient() {
        return numTelClient;
    }
    public void setNumTelClient(String numTelClient) {
        this.numTelClient = numTelClient;
    }





    public String getEmailClient() {
        return emailClient;
    }
    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }




    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Commande{" +
                "num_conception=" + num_conception +
                ", ccl='" + ccl + '\'' +
                ", prenomClient='" + prenomClient + '\'' +
                ", numTelClient='" + numTelClient + '\'' +
                ", emailClient='" + emailClient + '\'' +
                ", date=" + date +
                '}';
    }
}
