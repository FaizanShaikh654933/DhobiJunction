package com.example.admin.model;

public class OffersModel {
    String offerId;
    String title;
    String price;
    String code;
    String date;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String s, String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String s, String price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String s, String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String s, String date) {
        this.date = date;
    }
}
