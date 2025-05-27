package com.example.chaekimjeo7.UI.main.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.chaekimjeo7.Network.RetrofitHelper;
import com.example.chaekimjeo7.Network.ApiCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chaekimjeo7.UI.mypage.MyPageActivity;
import com.example.chaekimjeo7.Model.Book;
import com.example.chaekimjeo7.Network.*;
import com.example.chaekimjeo7.Network.RetrofitService;
import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.chat.list.ChatListActivity;
import com.example.chaekimjeo7.UI.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListAllActivity extends AppCompatActivity {

    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList = new ArrayList<>();

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

        bookAdapter = new BookAdapter(bookList, this);
        bookRecyclerView.setAdapter(bookAdapter);

        // 실제 서버에서 데이터 받아오기
        getAllBooksFromServer();

        // 🔗 XML 연결
        searchInput = findViewById(R.id.searchInput);
        searchByTitleButton = findViewById(R.id.searchByTitleButton);
        searchByProfessorButton = findViewById(R.id.searchByProfessorButton);

        // 🔘 검색 기준 선택
        searchByTitleButton.setOnClickListener(v -> {
            selectedType = SearchType.TITLE;
            highlightSelectedTab(searchByTitleButton);
        });

        searchByProfessorButton.setOnClickListener(v -> {
            selectedType = SearchType.PROFESSOR;
            highlightSelectedTab(searchByProfessorButton);
        });

        // 🔍 검색어 입력 감지
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

        // 4. 하단 네비게이션 바
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
            } else if (id == R.id.nav_chat) {
                startActivity(new Intent(this, ChatListActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, MyPageActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    // ✅ 서버에서 교재 목록 받아오기 (fetchAllBooks 사용)
    private void getAllBooksFromServer() {
        RetrofitHelper.fetchAllBooks(this, new ApiCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> response) {
                bookList.clear();
                bookList.addAll(response);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(BookListAllActivity.this, "서버 오류: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void highlightSelectedTab(Button selected) {
        Button[] allTabs = {searchByTitleButton, searchByProfessorButton};
        for (Button btn : allTabs) {
            btn.setBackgroundResource(R.drawable.tab_unselected);
        }
        selected.setBackgroundResource(R.drawable.tab_selected);
    }

    private void saveRecentKeyword(String keyword) {
        SharedPreferences prefs = getSharedPreferences(PREF_RECENT, Context.MODE_PRIVATE);
        Set<String> recentSet = prefs.getStringSet(PREF_KEY, new LinkedHashSet<>());

        Set<String> newSet = new LinkedHashSet<>(recentSet);
        newSet.add(keyword);

        prefs.edit().putStringSet(PREF_KEY, newSet).apply();
    }
}

