package com.example.present_funding;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Fundings {
    private String uid, fid, host_name, img, brand, name, price, addr, addr_detail, month, day, collection;

    public Fundings(){//String uid, String fid, String host_name, String img, String brand, String name, String price, String addr, String addr_detail,String month, String  day,String collection) {
//        this.uid = uid;
//        this.fid = fid;
//        this.host_name = host_name;
//        this.img = img;
//        this.brand = brand;
//        this.name = name;
//        this.price = price;
//        this.addr = addr;
//        this.addr_detail = addr_detail;
//        this.month = month;
//        this.day = day;
//        this.collection = collection;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
