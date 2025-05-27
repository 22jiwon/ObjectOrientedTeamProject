package com.example.chaekimjeo7.UI.main.book;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.chat.list.ChatListActivity;
import com.example.chaekimjeo7.UI.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookSellDetailActivity extends AppCompatActivity {

    private ImageView mainImage;
    private TextView bookTitleView, professorTag, categoryTag;
    private TextView salePriceView, discountRateView, bookDescriptionView;
    private TextView originalPriceView;

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
        discountRateView = findViewById(R.id.discountRate); // ✅ 할인율 텍스트뷰 연결
        bookDescriptionView = findViewById(R.id.bookDescription);
        originalPriceView = findViewById(R.id.textOriginalPrice);
        ImageButton backButton = findViewById(R.id.backButton);


        // ✅ 찜 아이콘과 문의 버튼 비활성화
        ImageView likeIcon = findViewById(R.id.bookLikeIcon);
        Button inquiryButton = findViewById(R.id.inquiryButton);
        ImageButton reportButton = findViewById(R.id.reportButton);

        likeIcon.setVisibility(View.GONE);
        inquiryButton.setVisibility(View.GONE);
        reportButton.setVisibility(View.GONE);

        // 📦 Intent로 전달받은 데이터 꺼내기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String professor = intent.getStringExtra("professor");
        String category = intent.getStringExtra("category");
        String priceStr = intent.getStringExtra("price");
        String officialPriceStr = intent.getStringExtra("officialPrice"); // ✅ 정가 받기
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
                discountRateText = ""; // 오류 시 표시하지 않음
            }
        }

        // 📋 텍스트 세팅
        bookTitleView.setText(title);
        professorTag.setText(professor);
        categoryTag.setText(category);
        salePriceView.setText(priceStr + "원");
        discountRateView.setText(discountRateText); // ✅ 할인율 표시
        bookDescriptionView.setText(description);

        // 🖼 이미지 세팅
        if (imageUriStr != null) {
            Uri imageUri = Uri.parse(imageUriStr);
            mainImage.setImageURI(imageUri); // ✅ 이미지 적용
        }

        if (officialPriceStr != null && !officialPriceStr.isEmpty()) {
            originalPriceView.setText("정가 " + officialPriceStr + "원"); // ✅ 정가 표시 추가
        }

        // ✅ 하단 네비게이션 바 클릭 이벤트 처리
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent homeIntent = new Intent(BookSellDetailActivity.this, MainActivity.class);  // ✅ 이름 다르게
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                finish();
                return true;
            } else if (id == R.id.nav_chat) {
                Intent chatIntent = new Intent(BookSellDetailActivity.this, ChatListActivity.class);
                startActivity(chatIntent);
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }

            return false;
        });
    }
}
