package com.example.chaekimjeo7;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("reviewId")
    private int reviewId;

    @SerializedName("reviewerNickname")
    private String reviewerNickname;

    @SerializedName("rating")
    private double rating;

    @SerializedName("content")
    private String content;

    @SerializedName("createdAt")
    private String createdAt;

    // getter
    public int getReviewId() {
        return reviewId;
    }

    public String getReviewerNickname() {
        return reviewerNickname;
    }

    public double getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
