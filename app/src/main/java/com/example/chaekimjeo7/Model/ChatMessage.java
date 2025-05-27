package com.example.chaekimjeo7.Model;

import com.google.gson.annotations.SerializedName;

public class ChatMessage {

    @SerializedName("senderId")
    private int senderId;

    @SerializedName("message")
    private String message;

    @SerializedName("sentAt")
    private String sentAt;

    public int getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getSentAt() {
        return sentAt;
    }
}
