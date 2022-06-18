package com.example.present_funding;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Funding {
    private String uid, addr, addr_detail, collection, day, month, prod_img, prod_name, prod_price, host_name, fid;

    public Funding(String addr, String addr_detail, String collection, String day, String month, String prod_img, String prod_name, String prod_price, String host_name, String fid) {
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public void setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getProd_img() {
        return prod_img;
    }

    public void setProd_img(String prod_img) {
        this.prod_img = prod_img;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_price() { return prod_price; }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String uname) {
        this.host_name = uname;
    }

}
