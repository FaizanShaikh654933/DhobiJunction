package com.example.dhobijunction.model;

public class CartModel {
    String image;
    String price;
    String title;
    String sId;
    String qty;
    String pId;
    String cartItemId;
    String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public CartModel(String image, String price, String title, String sId, String qty, String pId,String cartItemId,String total) {
        this.image = image;
        this.price = price;
        this.title = title;
        this.sId = sId;
        this.qty = qty;
        this.pId = pId;
        this.cartItemId = cartItemId;
        this.total = total;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
    public CartModel(){}
}
