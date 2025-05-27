package com.example.chaekimjeo7.Model;

public class ChatRoom {
    private String roomId;
    private String otherUserId;
    private String otherUserName;
    private String otherUserProfileImage;
    private String lastMessage;
    private String lastSentAt;

    public ChatRoom(String roomId, String otherUserId, String otherUserName,
                    String otherUserProfileImage, String lastMessage, String lastSentAt) {
        this.roomId = roomId;
        this.otherUserId = otherUserId;
        this.otherUserName = otherUserName;
        this.otherUserProfileImage = otherUserProfileImage;
        this.lastMessage = lastMessage;
        this.lastSentAt = lastSentAt;
    }

    public String getRoomId() { return roomId; }
    public String getOtherUserId() { return otherUserId; }
    public String getOtherUserName() { return otherUserName; }
    public String getOtherUserProfileImage() { return otherUserProfileImage; }
    public String getLastMessage() { return lastMessage; }
    public String getLastSentAt() { return lastSentAt; }
}

