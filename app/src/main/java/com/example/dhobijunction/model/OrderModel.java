package com.example.dhobijunction.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderModel implements Serializable {
    String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    String Name;
    String Email;
    String number;
    String Address;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    String total;
    @ServerTimestamp
    Date timestamp;
    String deliverTime;
    String PayMentMethod;

    public String getPayMentMethod() {
        return PayMentMethod;
    }

    public void setPayMentMethod(String payMentMethod) {
        PayMentMethod = payMentMethod;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }



    List<CheckoutModel> modelList;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public List<CheckoutModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<CheckoutModel> modelList) {
        this.modelList = modelList;
    }
}
