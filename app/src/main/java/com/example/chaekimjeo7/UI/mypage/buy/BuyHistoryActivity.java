package com.example.chaekimjeo7.UI.mypage.buy;

import android.os.Bundle;
import android.widget.Button;

import com.example.chaekimjeo7.Model.BuyItem;
import com.example.chaekimjeo7.Model.BuyResponse;
import com.example.chaekimjeo7.Network.RetrofitClient;
import com.example.chaekimjeo7.Network.RetrofitService;
import com.example.chaekimjeo7.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BuyAdapter adapter;
    private List<BuyItem> allItems = new ArrayList<>();

    private Button btnAll, btnSelling, btnSold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_history);

        recyclerView = findViewById(R.id.recyclerBuyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAll = findViewById(R.id.btn_all);
        btnSelling = findViewById(R.id.btn_selling);
        btnSold = findViewById(R.id.btn_sold);

        adapter = new BuyAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadBuyHistory();

        btnAll.setOnClickListener(v -> filterList("ALL"));
        btnSelling.setOnClickListener(v -> filterList("IN_PROGRESS"));
        btnSold.setOnClickListener(v -> filterList("COMPLETED"));
    }

    private void loadBuyHistory() {
        RetrofitService RetrofitService = RetrofitClient.getClient().create(RetrofitService.class);
        Call<BuyResponse> call = RetrofitService.getBuyHistory(); // BuyApi로 호출

        call.enqueue(new Callback<BuyResponse>() {
            @Override
            public void onResponse(Call<BuyResponse> call, Response<BuyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allItems = response.body().getBuys();
                    adapter.updateList(allItems);
                }
            }

            @Override
            public void onFailure(Call<BuyResponse> call, Throwable t) {
                // 에러 처리
            }
        });
    }
    private void filterList(String status) {
        if (status.equals("ALL")) {
            adapter.updateList(allItems);
            return;
        }

        List<BuyItem> filtered = new ArrayList<>();
        for (BuyItem item : allItems) {
            if (item.status.equals(status)) {
                filtered.add(item);
            }
        }
        adapter.updateList(filtered);
    }
}
