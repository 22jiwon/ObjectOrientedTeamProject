package com.example.chaekimjeo7.UI.main.book;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.chat.ChatActivity;
import com.example.chaekimjeo7.UI.chat.list.ChatListActivity;
import com.example.chaekimjeo7.UI.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookPurchaseDetailActivity extends AppCompatActivity {

    private ImageView mainImage;
    private TextView bookTitleView, professorTag, categoryTag;
    private TextView salePriceView, discountRateView, bookDescriptionView;
    private TextView originalPriceView;
    private ImageView bookLikeIcon;
    private ImageButton reportButton;
    private Button inquiryButton;

    private boolean isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_book_detail);

        // 🔗 XML 뷰 연결
        mainImage = findViewById(R.id.bookMainImage);
        bookTitleView = findViewById(R.id.bookTitle);
        professorTag = findViewById(R.id.professorTag);
        categoryTag = findViewById(R.id.categoryTag);
        salePriceView = findViewById(R.id.salePrice);
        discountRateView = findViewById(R.id.discountRate);
        bookDescriptionView = findViewById(R.id.bookDescription);
        originalPriceView = findViewById(R.id.textOriginalPrice);
        bookLikeIcon = findViewById(R.id.bookLikeIcon);
        reportButton = findViewById(R.id.reportButton);
        inquiryButton = findViewById(R.id.inquiryButton);
        ImageButton backButton = findViewById(R.id.backButton); // ✅ 추가

        // 📦 Intent로 전달된 데이터 꺼내기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String professor = intent.getStringExtra("professor");
        String category = intent.getStringExtra("category");
        String priceStr = intent.getStringExtra("price");
        String officialPriceStr = intent.getStringExtra("officialPrice");
        String description = intent.getStringExtra("description");
        String imageUriStr = intent.getStringExtra("imageUri");

        // 뒤로가기 버튼 동작 처리
        backButton.setOnClickListener(v -> finish()); // ✅ 추가

        // 🧮 할인율 계산
        String discountRateText = "";
        if (officialPriceStr != null && priceStr != null) {
            try {
                int officialPrice = Integer.parseInt(officialPriceStr);
                int price = Integer.parseInt(priceStr);
                if (officialPrice > 0 && price <= officialPrice) {
                    int discount = (int) Math.round((officialPrice - price) * 100.0 / officialPrice);
                    discountRateText = discount + "%";
                }
            } catch (NumberFormatException e) {
                discountRateText = "";
            }
        }

        // 📋 텍스트 세팅
        bookTitleView.setText(title);
        professorTag.setText(professor);
        categoryTag.setText(category);
        salePriceView.setText(priceStr + "원");
        discountRateView.setText(discountRateText);
        bookDescriptionView.setText(description);
        if (officialPriceStr != null && !officialPriceStr.isEmpty()) {
            originalPriceView.setText("정가 " + officialPriceStr + "원");
        }

        // 🖼 이미지 세팅
        if (imageUriStr != null) {
            Uri imageUri = Uri.parse(imageUriStr);
            mainImage.setImageURI(imageUri);
        }

        // ❤️ 찜 기능
        bookLikeIcon.setOnClickListener(v -> {
            isLiked = !isLiked;
            if (isLiked) {
                bookLikeIcon.setImageResource(R.drawable.ic_heart_filled);
                Toast.makeText(this, "찜 목록에 추가됐어요!", Toast.LENGTH_SHORT).show();
            } else {
                bookLikeIcon.setImageResource(R.drawable.ic_heart_outline);
                Toast.makeText(this, "찜 목록에서 제거됐어요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 📩 문의하기 버튼
        inquiryButton.setOnClickListener(v -> {
            String sellerName = "이기연"; // TODO: 서버 응답에서 받아오기
            Intent chatIntent = new Intent(this, ChatActivity.class);
            chatIntent.putExtra("sellerName", sellerName);
            chatIntent.putExtra("bookTitle", title);
            chatIntent.putExtra("bookPrice", priceStr);  // ✅ 이 줄 추가!
            chatIntent.putExtra("bookImageResId", R.drawable.book_sample1); // 필요 시 이미지도 전달
            startActivity(chatIntent);
        });

        // 🚨 신고 기능
        reportButton.setOnClickListener(v -> {
            String[] reportReasons = {
                    "비방 및 욕설",
                    "부적절한 사진",
                    "무통보 거래 파기",
                    "기타"
            };
            final int[] selected = {-1};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("신고 사유를 선택해주세요")
                    .setSingleChoiceItems(reportReasons, -1, (dialog, which) -> {
                        selected[0] = which;
                    })
                    .setPositiveButton("신고하기", (dialog, which) -> {
                        if (selected[0] == -1) {
                            Toast.makeText(this, "신고 사유를 선택해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            String reason = reportReasons[selected[0]];
                            Toast.makeText(this, "신고가 접수되었습니다: " + reason, Toast.LENGTH_LONG).show();

                            // TODO: 서버에 신고 전송
                            // ex: POST /api/reports { sellerId, reason, productId }
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
        });

        // ⬇️ 하단 네비게이션 바
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                finish();
                return true;
            } else if (id == R.id.nav_chat) {
                startActivity(new Intent(this, ChatListActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }
}

