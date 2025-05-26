package com.example.myapplication;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import retrofit2.*;
import java.util.*;

public class ReviewActivity extends AppCompatActivity {
    private ImageView imageProfile;
    private TextView textNickname, textAverageRating, textReviewCount;
    private RatingBar ratingBar;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter adapter;

    private List<ReviewItem> reviewList = new ArrayList<>();
    private Map<String, Integer> keywordCountMap = new HashMap<>();

    private int sellerId = 1;
    private int productId = 88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        imageProfile = findViewById(R.id.imageProfile);
        textNickname = findViewById(R.id.textNickname);
        textAverageRating = findViewById(R.id.textAverageRating);
        textReviewCount = findViewById(R.id.textReviewCount);
        ratingBar = findViewById(R.id.ratingBar);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewAdapter(reviewList);
        reviewRecyclerView.setAdapter(adapter);

        loadReviews();
        loadUserProfile(); // 마이페이지에서 끌어온 정보
    }

    private void loadUserProfile() {
        // 예시 - SharedPreferences나 Intent 등에서 받아올 수 있음
        String nickname = "책좋아";
        String profileImage = "https://cdn.site/img.jpg";

        textNickname.setText(nickname);
        Glide.with(this).load(profileImage).circleCrop().into(imageProfile);
    }

    private void loadReviews() {
        ReviewApi api = RetrofitClient.getClient().create(ReviewApi.class);
        api.getReviews(productId, sellerId, 20, 0, "latest")
                .enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            reviewList.clear();
                            reviewList.addAll(response.body().reviews);
                            adapter.notifyDataSetChanged();

                            updateStats();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                        Toast.makeText(ReviewActivity.this, "리뷰 불러오기 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateStats() {
        float totalRating = 0;
        keywordCountMap.clear();

        for (ReviewItem item : reviewList) {
            totalRating += item.rating;

            if (item.keywords != null) {
                for (String keyword : item.keywords) {
                    if (keywordCountMap.containsKey(keyword)) {
                        keywordCountMap.put(keyword, keywordCountMap.get(keyword) + 1);
                    } else {
                        keywordCountMap.put(keyword, 1);
                    }
                }
            }
        }

        float avgRating = reviewList.isEmpty() ? 0 : totalRating / reviewList.size();
        ratingBar.setRating(avgRating);
        textAverageRating.setText(String.format("%.2f", avgRating));
        textReviewCount.setText("후기 " + reviewList.size());

        updateKeywordUI();
    }

    private void updateKeywordUI() {
        // API 21 호환용으로 Map.of 대신 직접 생성
        Map<Integer, Integer> keywordIds = new HashMap<>();
        keywordIds.put(1, R.id.keyword1_count);
        keywordIds.put(2, R.id.keyword2_count);
        keywordIds.put(3, R.id.keyword3_count);

        // List.of → Arrays.asList로 대체
        List<String> targetKeywords = Arrays.asList(
                "답장이 빨라요",
                "교재설명과 실제상품이 동일해요",
                "친절하고 배려가 넘쳐요"
        );

        for (int i = 0; i < targetKeywords.size(); i++) {
            String key = targetKeywords.get(i);

            int count;
            if (keywordCountMap.containsKey(key)) {
                count = keywordCountMap.get(key);
            } else {
                count = 0;
            }

            TextView countView = findViewById(keywordIds.get(i + 1));
            countView.setText(String.valueOf(count));
        }
    }
}
