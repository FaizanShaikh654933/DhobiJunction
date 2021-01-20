package com.example.dhobijunction.adapter;

import com.example.dhobijunction.model.CartModel;
import com.example.dhobijunction.model.ProModel;
import com.example.dhobijunction.model.ProductModel;

public interface OnAddToCartListner {
    public void addToCart(ProModel product,String proQty);
}
