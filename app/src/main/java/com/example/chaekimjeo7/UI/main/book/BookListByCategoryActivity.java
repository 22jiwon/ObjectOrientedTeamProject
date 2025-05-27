package com.example.chaekimjeo7.UI.main.book;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chaekimjeo7.Model.Book;
import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.chat.list.ChatListActivity;
import com.example.chaekimjeo7.UI.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class BookListByCategoryActivity extends AppCompatActivity {

    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;

    private EditText searchInput;
    private Button searchByTitleButton, searchByProfessorButton;

    private enum SearchType { TITLE, PROFESSOR, NONE }
    private SearchType selectedType = SearchType.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_by_category);

        String categoryName = getIntent().getStringExtra("category");
        TextView pageTitle = findViewById(R.id.pageTitle);
        if (categoryName != null && !categoryName.isEmpty()) {
            pageTitle.setText(categoryName);
        }

        // XML 연결
        bookRecyclerView = findViewById(R.id.bookRecyclerView);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchInput = findViewById(R.id.searchInput);
        searchByTitleButton = findViewById(R.id.searchByTitleButton);
        searchByProfessorButton = findViewById(R.id.searchByProfessorButton);

        // 샘플 데이터 (카테고리에 따라 동적으로 넣을 수도 있음)
        List<Book> fullList = new ArrayList<>();
        fullList.add(new Book("고전문학읽기", 8000, 17000, 12000, R.drawable.book_sample1, "이문학", "문과대학", false, "필기 있음"));
        fullList.add(new Book("서양철학입문", 9500, 18000, 14000, R.drawable.book_sample2, "서철학", "문과대학", false, "깨끗함"));
        fullList.add(new Book("현대사회의 이해", 10500, 19000, 15000, R.drawable.book_sample3, "김사회", "문과대학", false, "사용감 보통"));

        fullList.add(new Book("미적분학", 10000, 19000, 14000, R.drawable.book_sample4, "정수학", "이과대학", false, "사용감 있음"));
        fullList.add(new Book("물리학 실험", 11000, 20000, 15000, R.drawable.book_sample5, "김물리", "이과대학", false, "깨끗하게 사용함"));
        fullList.add(new Book("화학의 세계", 9000, 18000, 13000, R.drawable.book_sample5, "박화학", "이과대학", false, "상태 보통"));

        fullList.add(new Book("인공지능개론", 15000, 25000, 18000, R.drawable.book_sample5, "박교수", "공과대학", false, "깨끗함"));
        fullList.add(new Book("프로그래밍 기초", 14000, 22000, 16000, R.drawable.book_sample5, "이공학", "공과대학", false, "필기 조금 있음"));
        fullList.add(new Book("논리회로", 12000, 21000, 15000, R.drawable.book_sample5, "최교수", "공과대학", false, "겉표지 훼손 없음"));

        bookList = new ArrayList<>();
        for (Book book : fullList) {
            if (book.getCategory().equals(categoryName)) {
                bookList.add(book);
            }
        }
        bookAdapter = new BookAdapter(bookList, this);
        bookRecyclerView.setAdapter(bookAdapter);

        // 버튼 클릭 → 검색 기준 설정
        searchByTitleButton.setOnClickListener(v -> {
            selectedType = SearchType.TITLE;
            highlightSelectedTab(searchByTitleButton);
        });

        searchByProfessorButton.setOnClickListener(v -> {
            selectedType = SearchType.PROFESSOR;
            highlightSelectedTab(searchByProfessorButton);
        });

        // 입력 시 필터링
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (selectedType == SearchType.NONE) {
                    Toast.makeText(BookListByCategoryActivity.this, "검색 기준을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                bookAdapter.filter(s.toString(), selectedType == SearchType.TITLE);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home); // 현재 위치 표시

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.nav_chat) {
                Intent intent = new Intent(this, ChatListActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }

            return false;
        });
    }

    private void highlightSelectedTab(Button selected) {
        Button[] allTabs = {searchByTitleButton, searchByProfessorButton};
        for (Button btn : allTabs) {
            btn.setBackgroundResource(R.drawable.tab_unselected);
        }
        selected.setBackgroundResource(R.drawable.tab_selected);
    }
}
