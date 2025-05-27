package com.example.chaekimjeo7.UI.main.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chaekimjeo7.Model.Book;
import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.chat.list.ChatListActivity;
import com.example.chaekimjeo7.UI.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BookListAllActivity extends AppCompatActivity {

    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;

    private EditText searchInput;
    private Button searchByTitleButton, searchByProfessorButton;
    private LinearLayout recentSearchContainer;

    private enum SearchType { TITLE, PROFESSOR, NONE }
    private SearchType selectedType = SearchType.NONE;

    private static final String PREF_RECENT = "recent_search";
    private static final String PREF_KEY = "keywords";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        bookRecyclerView = findViewById(R.id.bookRecyclerView);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 샘플 데이터
        bookList = new ArrayList<>();
        // 공과대학
        bookList.add(new Book("인공지능개론", 15000, 25000, 18000, R.drawable.book_sample1, "박교수", "공과대학", false, "깨끗함"));
        bookList.add(new Book("프로그래밍 기초", 14000, 22000, 16000, R.drawable.book_sample2, "이공학", "공과대학", true, "필기 조금 있음"));
        bookList.add(new Book("논리회로", 12000, 21000, 15000, R.drawable.book_sample3, "최교수", "공과대학", false, "겉표지 훼손 없음"));

        // 이과대학
        bookList.add(new Book("미적분학", 10000, 19000, 14000, R.drawable.book_sample4, "정수학", "이과대학", false, "사용감 있음"));
        bookList.add(new Book("물리학 실험", 11000, 20000, 15000, R.drawable.book_sample5, "김물리", "이과대학", false, "깨끗하게 사용함"));
        bookList.add(new Book("화학의 세계", 9000, 18000, 13000, R.drawable.book_sample5, "박화학", "이과대학", false, "상태 보통"));

        // 문과대학
        bookList.add(new Book("고전문학읽기", 8000, 17000, 12000, R.drawable.book_sample5, "이문학", "문과대학", false, "필기 있음"));
        bookList.add(new Book("서양철학입문", 9500, 18000, 14000, R.drawable.book_sample5, "서철학", "문과대학", false, "깨끗함"));
        bookList.add(new Book("현대사회의 이해", 10500, 19000, 15000, R.drawable.book_sample5, "김사회", "문과대학", false, "사용감 보통"));


        bookAdapter = new BookAdapter(bookList, this);
        bookRecyclerView.setAdapter(bookAdapter);

        // 🔗 XML 연결
        searchInput = findViewById(R.id.searchInput);
        searchByTitleButton = findViewById(R.id.searchByTitleButton);
        searchByProfessorButton = findViewById(R.id.searchByProfessorButton);

        // 🔘 버튼 선택 로직
        searchByTitleButton.setOnClickListener(v -> {
            selectedType = SearchType.TITLE;
            highlightSelectedTab(searchByTitleButton);
        });

        searchByProfessorButton.setOnClickListener(v -> {
            selectedType = SearchType.PROFESSOR;
            highlightSelectedTab(searchByProfessorButton);
        });

        // 🔍 검색 입력 감지
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();

                if (selectedType == SearchType.NONE) {
                    Toast.makeText(BookListAllActivity.this, "검색 기준을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                bookAdapter.filter(keyword, selectedType == SearchType.TITLE);

                if (!keyword.isEmpty()) {
                    saveRecentKeyword(keyword);
                }
            }
        });

        // ⬇️ 하단 네비게이션 바
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_chat) {
                startActivity(new Intent(this, ChatListActivity.class));
                return true;
            }
            return false;
        });
    }

    // 버튼 하이라이트 표시
    private void highlightSelectedTab(Button selected) {
        Button[] allTabs = {searchByTitleButton, searchByProfessorButton};
        for (Button btn : allTabs) {
            btn.setBackgroundResource(R.drawable.tab_unselected);
        }
        selected.setBackgroundResource(R.drawable.tab_selected);
    }

    // 최근 검색어 저장
    private void saveRecentKeyword(String keyword) {
        SharedPreferences prefs = getSharedPreferences(PREF_RECENT, Context.MODE_PRIVATE);
        Set<String> recentSet = prefs.getStringSet(PREF_KEY, new LinkedHashSet<>());

        Set<String> newSet = new LinkedHashSet<>(recentSet);
        newSet.add(keyword);

        prefs.edit().putStringSet(PREF_KEY, newSet).apply();
    }
}
