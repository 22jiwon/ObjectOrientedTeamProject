package com.example.chaekimjeo7;

public class SaleItem {
    public String title;
    public String price;
    public String createdAt;
    public String status;
    public String imageUrl;
    public boolean isSelected;

    public SaleItem(String title, String price, String createdAt, String status, String imageUrl) {
        this.title = title;
        this.price = price;
        this.createdAt = createdAt;
        this.status = status;
        this.imageUrl = imageUrl;
        this.isSelected = false;
    }
}