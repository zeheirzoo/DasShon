package com.example.project.models;

import java.util.Date;

public class Article {
    int id_article;
    String nomArticle;
    String productionEtat;
    String productionVal;
    String qualityEtat;
    String qualityVal;
    String stockEtat;
    String stockVal;
    Date date;

    int orderArticle;


    public Article(String nomArticle, String productionEtat, String productionVal, String qualityEtat, String qualityVal, String stockEtat, String stockVal, Date date, int orderArticle) {
        this.nomArticle = nomArticle;
        this.productionEtat = productionEtat;
        this.productionVal = productionVal;
        this.qualityEtat = qualityEtat;
        this.qualityVal = qualityVal;
        this.stockEtat = stockEtat;
        this.stockVal = stockVal;
        this.date = date;
        this.orderArticle = orderArticle;
    }

    public int getOrderArticle() {
        return orderArticle;
    }

    public void setOrderArticle(int orderArticle) {
        this.orderArticle = orderArticle;
    }
    public int getId_article() {
        return id_article;
    }
    public void setId_article(int id_article) {
        this.id_article = id_article;
    }




    public String getNomArticle() {
        return nomArticle;
    }
    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }





    public String getProductionEtat() {
        return productionEtat;
    }
    public void setProductionEtat(String productionEtat) {
        this.productionEtat = productionEtat;
    }





    public String getProductionVal() {
        return productionVal;
    }
    public void setProductionVal(String productionVal) {
        this.productionVal = productionVal;
    }






    public String getQualityEtat() {
        return qualityEtat;
    }
    public void setQualityEtat(String qualityEtat) {
        this.qualityEtat = qualityEtat;
    }





    public String getQualityVal() {
        return qualityVal;
    }
    public void setQualityVal(String qualityVal) {
        this.qualityVal = qualityVal;
    }







    public String getStockEtat() {
        return stockEtat;
    }
    public void setStockEtat(String stockEtat) {
        this.stockEtat = stockEtat;
    }





    public String getStockVal() {
        return stockVal;
    }
    public void setStockVal(String stockVal) {
        this.stockVal = stockVal;
    }







    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }






    @Override
    public String toString() {
        return "Article{" +
                "id_article=" + id_article +
                ", nomArticle='" + nomArticle + '\'' +
                ", productionEtat='" + productionEtat + '\'' +
                ", productionVal='" + productionVal + '\'' +
                ", qualityEtat='" + qualityEtat + '\'' +
                ", qualityVal='" + qualityVal + '\'' +
                ", stockEtat='" + stockEtat + '\'' +
                ", stockVal='" + stockVal + '\'' +
                ", date=" + date +
                '}';
    }
}
