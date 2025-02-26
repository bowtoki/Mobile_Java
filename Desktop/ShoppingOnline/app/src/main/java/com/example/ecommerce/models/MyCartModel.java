package com.example.ecommerce.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {

    String currentTime;
    String currentDate;
    String productName;
    String price;
    int totalPrice;;
    String totalQuantity;

    public MyCartModel() {
    }

    public MyCartModel(String currentTime, String currentDate, String productName, String price, int totalPrice, String totalQuantity) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productName = productName;
        this.price = price;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}

