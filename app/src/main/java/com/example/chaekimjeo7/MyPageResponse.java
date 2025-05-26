package com.example.chaekimjeo7;

import java.util.List;

public class MyPageResponse {
    private String userId;
    private String nickname;
    private String profileImage;
    private double rating;
    private int reviewCount;
    private int sellCount;
    private int buyCount;
    private int reportCount;
    private String reportReason;
    private ScheduleInfo scheduleInfo;

    // 게터/세터 생략 가능 (Lombok 쓰면 간단히 처리 가능)

    public String getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getProfileImage() { return profileImage; }
    public double getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public int getSellCount() { return sellCount; }
    public int getBuyCount() { return buyCount; }
    public int getReportCount() { return reportCount; }
    public String getReportReason() { return reportReason; }
    public ScheduleInfo getScheduleInfo() { return scheduleInfo; }

    public class ScheduleInfo {
        private boolean uploaded;
        private List<String> scheduleSummary;
        private String lastUploaded;

        public boolean isUploaded() { return uploaded; }
        public List<String> getScheduleSummary() { return scheduleSummary; }
        public String getLastUploaded() { return lastUploaded; }
    }
}
