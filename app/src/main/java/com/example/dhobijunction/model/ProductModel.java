package com.example.dhobijunction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {


    @SerializedName("product_id")
    @Expose
    String product_id;

    @SerializedName("product_name")
    @Expose
    String product_name;

    @SerializedName("product_image")
    @Expose
    String product_image;

    @SerializedName("product_description")
    @Expose
    String product_description;

    @SerializedName("product_price")
    @Expose
    String product_price;

    @SerializedName("product_Rs")
    @Expose
    String product_Rs;

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_Rs() {
        return product_Rs;
    }
}
