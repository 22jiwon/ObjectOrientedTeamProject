package com.example.chaekimjeo7.UI.mypage.favorite;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chaekimjeo7.Model.FavoriteItem;
import com.example.chaekimjeo7.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavoriteAdapter adapter;
    List<FavoriteItem> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recycler_favorites);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        favoriteList = new ArrayList<>();
        adapter = new FavoriteAdapter(this, favoriteList);
        recyclerView.setAdapter(adapter);

        loadFavorites();

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void loadFavorites() {
        SharedPreferences prefs = getSharedPreferences("favorites", MODE_PRIVATE);
        Map<String, ?> allFavorites = prefs.getAll();

        for (Map.Entry<String, ?> entry : allFavorites.entrySet()) {
            String[] value = entry.getValue().toString().split("::");
            if (value.length == 2) {
                favoriteList.add(new FavoriteItem(value[0], value[1]));
            }
        }

        adapter.notifyDataSetChanged();
    }
}
