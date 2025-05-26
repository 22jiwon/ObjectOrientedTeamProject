package com.example.chaekimjeo7;

import android.os.Bundle;
import android.widget.GridView;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    GridView categoryGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 등록 버튼 연결
        ImageButton fabRegister = findViewById(R.id.fabRegister);
        fabRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookRegisterActivity.class);
            startActivity(intent);
        });

        // ✅ 교재 추천 바로가기 버튼
        Button recommendBtn = findViewById(R.id.recommendBtn);
        recommendBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookRecommendActivity.class);
            intent.putExtra("userId", 1);
            startActivity(intent);
        });


        // 시스템 바 패딩 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 카테고리 그리드 설정
        categoryGrid = findViewById(R.id.categoryGrid);

        List<CategoryItem> categories = new ArrayList<>();
        categories.add(new CategoryItem("문과대학", R.drawable.ic_liberal_1));
        categories.add(new CategoryItem("이과대학", R.drawable.ic_science_2));
        categories.add(new CategoryItem("공과대학", R.drawable.ic_engineering_3));
        categories.add(new CategoryItem("생활과학", R.drawable.ic_life_4));
        categories.add(new CategoryItem("사회과학", R.drawable.ic_social_5));
        categories.add(new CategoryItem("법과대학", R.drawable.ic_law_6));
        categories.add(new CategoryItem("경상대학", R.drawable.ic_economy_7));
        categories.add(new CategoryItem("음악대학", R.drawable.ic_music_8));
        categories.add(new CategoryItem("약학대학", R.drawable.ic_pharmacy_9));
        categories.add(new CategoryItem("미술대학", R.drawable.ic_art_10));

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        categoryGrid.setAdapter(adapter);

        // 카테고리 클릭 시 해당 Activity로 이동
        categoryGrid.setOnItemClickListener((parent, view, position, id) -> {
            CategoryItem selected = categories.get(position);
            Intent intent = new Intent(MainActivity.this, BookListByengineeringActivity.class);
            intent.putExtra("category", selected.getName());
            startActivity(intent);
        });

        // 바텀 네비게이션 클릭 이벤트 처리
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true; // 현재 화면
            } else if (itemId == R.id.nav_chat) {
                Intent intent = new Intent(MainActivity.this, ChatListActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });
    }
}

