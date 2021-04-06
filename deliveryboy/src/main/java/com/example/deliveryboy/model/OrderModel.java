package com.example.deliveryboy.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderModel implements Serializable {
    String Email;
    String number;
    String Address;
    String Total;
    String Name;
    String oId;

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    List<CheckoutModel> modelList;

    public List<CheckoutModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<CheckoutModel> modelList) {
        this.modelList = modelList;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getPayMentMethod() {
        return PayMentMethod;
    }

    public void setPayMentMethod(String payMentMethod) {
        PayMentMethod = payMentMethod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @ServerTimestamp
    Date timestamp;
    String deliverTime;
    String PayMentMethod;
    String orderId;
}
