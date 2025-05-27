package com.example.chaekimjeo7.Model;

public class ChatMessage {
    private String messageText; // 메시지 내용
    private boolean isSentByMe; // 내가 보낸 메시지인지 여부

    public ChatMessage(String messageText, boolean isSentByMe) {
        this.messageText = messageText;
        this.isSentByMe = isSentByMe;
    }

    public String getMessageText() {
        return messageText;
    }

    public boolean isSentByMe() {
        return isSentByMe;
    }
}
