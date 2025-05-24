public class MypageResponse {
    private String profileImage;
    private String nickname;
    private double rating;
    private int reviewCount;
    private int sellCount;
    private int buyCount;
    private int reportCount;
    private String reportReason;

    public String getProfileImage() { return profileImage; }
    public String getNickname() { return nickname; }
    public double getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public int getSellCount() { return sellCount; }
    public int getBuyCount() { return buyCount; }
    public int getReportCount() { return reportCount; }
    public String getReportReason() { return reportReason; }
}
