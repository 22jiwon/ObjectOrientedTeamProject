package com.example.chaekimjeo7;

public class BuyItem {
    public String bookId;
    public String title;
    public int price;
    public String status; // "COMPLETED", "IN_PROGRESS"
    public String imageUrl;
    public boolean hasReview;

    public BuyItem(String bookId, String title, int price, String status, String imageUrl, boolean hasReview) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.status = status;
        this.imageUrl = imageUrl;
        this.hasReview = hasReview;
    }
}
