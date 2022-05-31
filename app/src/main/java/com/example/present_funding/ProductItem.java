package com.example.present_funding;

public class ProductItem {
    private String img, brand, name, price;

    public ProductItem(String img, String brand, String name, String price) {
        this.img = img;
        this.price = brand;
        this.name = name;
        this.price = price;
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

}


