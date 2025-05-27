package com.example.chaekimjeo7.Model;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private int salePrice;
    private int originalPrice;
    private int marketPrice;
    private int imageResId;
    private String professor;
    private String category;
    private boolean isMyPost;
    private String conditionDescription;  // ✅ 책 상태 설명 필드 추가

    // ✅ 생성자
    public Book(String title, int salePrice, int marketPrice, int originalPrice,
                int imageResId, String professor, String category,
                boolean isMyPost, String conditionDescription) {
        this.title = title;
        this.salePrice = salePrice;
        this.marketPrice = marketPrice;
        this.originalPrice = originalPrice;
        this.imageResId = imageResId;
        this.professor = professor;
        this.category = category;
        this.isMyPost = isMyPost;
        this.conditionDescription = conditionDescription;
    }

    // ✅ Getter 메서드들
    public String getTitle() { return title; }
    public int getSalePrice() { return salePrice; }
    public int getMarketPrice() { return marketPrice; }
    public int getOriginalPrice() { return originalPrice; }
    public int getImageResId() { return imageResId; }
    public String getProfessor() { return professor; }
    public String getCategory() { return category; }
    public boolean isMyPost() { return isMyPost; }
    public String getConditionDescription() { return conditionDescription; }  // ✅ 추가 Getter
}
