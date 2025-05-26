    package com.example.chaekimjeo7;

    import android.os.Bundle;
    import android.widget.Button;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import android.widget.LinearLayout;
    import android.view.View;

    import java.util.ArrayList;
    import java.util.List;

    public class SalesHistoryActivity extends AppCompatActivity {

        RecyclerView recyclerView;
        SalesAdapter salesAdapter;
        List<SaleItem> salesList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sales_history);

            recyclerView = findViewById(R.id.recycler_sales);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            Button btnSetSelling = findViewById(R.id.btn_set_selling);
            Button btnSetReserved = findViewById(R.id.btn_set_reserved);
            Button btnSetSold = findViewById(R.id.btn_set_sold);
            Button btnChangeMode = findViewById(R.id.btn_change_status);

            salesList = new ArrayList<>();
            salesList.add(new SaleItem("자료구조", "12,000원", "2025-05-10", "판매중", "https://example.com/book1.jpg"));
            salesList.add(new SaleItem("운영체제", "15,000원", "2025-05-11", "예약중", "https://example.com/book2.jpg"));

            salesAdapter = new SalesAdapter(salesList);
            recyclerView.setAdapter(salesAdapter);

            LinearLayout layoutStatusButtons = findViewById(R.id.layout_status_buttons);

            btnChangeMode.setOnClickListener(v -> {
                salesAdapter.toggleCheckboxVisibility(); // 체크박스 보이게
                layoutStatusButtons.setVisibility(View.VISIBLE); // 상태 버튼들도 보이게
            });

            btnSetSelling.setOnClickListener(v -> salesAdapter.updateStatusForSelectedItems("판매중"));
            btnSetReserved.setOnClickListener(v -> salesAdapter.updateStatusForSelectedItems("예약중"));
            btnSetSold.setOnClickListener(v -> salesAdapter.updateStatusForSelectedItems("판매완료"));
        }
    }
