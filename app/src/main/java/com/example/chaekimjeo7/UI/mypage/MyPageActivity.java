package com.example.chaekimjeo7.UI.mypage;

import com.example.chaekimjeo7.UI.mypage.buy.BuyHistoryActivity;
import com.example.chaekimjeo7.UI.mypage.favorite.FavoritesActivity;
import com.example.chaekimjeo7.Model.MyPageResponse;
import com.example.chaekimjeo7.Network.RetrofitClient;
import com.example.chaekimjeo7.Network.RetrofitService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.bumptech.glide.Glide;
import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.mypage.review.ReviewActivity;
import com.example.chaekimjeo7.UI.mypage.sales.SalesHistoryActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {

    private ImageView imageProfile;
    private TextView tvUserName, tvScore, tvReview, tvSellCount, tvBuyCount, tvReportCount;
    private ImageView btnHeart, btnReport;
    private LinearLayout btnSell, btnBuy, btnAddTimetable;
    private GridLayout timetableGrid;

    private String reportReason = "";
    private String reportMessage = ""; // 서버에서 받은 신고 메시지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        imageProfile = findViewById(R.id.imageProfile);
        tvUserName = findViewById(R.id.tvUserName);
        tvScore = findViewById(R.id.tvScore);
        tvReview = findViewById(R.id.tvReview);
        tvSellCount = findViewById(R.id.cell_num);
        tvBuyCount = findViewById(R.id.purchase_num);
        tvReportCount = findViewById(R.id.tvReportCount);
        btnHeart = findViewById(R.id.icHeart);
        btnReport = findViewById(R.id.icReport);
        btnSell = findViewById(R.id.cardSell);
        btnBuy = findViewById(R.id.cardBuy);
        btnAddTimetable = findViewById(R.id.btnAddTimetable);
        timetableGrid = findViewById(R.id.timetableGrid);

        loadMyPageData();  // 서버에서 사용자 정보 + 신고 메시지 받아오기
        setClickEvents();  // 버튼 클릭 이벤트 설정
    }

    private void loadMyPageData() {
        RetrofitService api = RetrofitClient.getClient().create(RetrofitService.class);
        Call<MyPageResponse> call = api.getMyPage();
        call.enqueue(new Callback<MyPageResponse>() {
            @Override
            public void onResponse(Call<MyPageResponse> call, Response<MyPageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MyPageResponse data = response.body();

                    Glide.with(MyPageActivity.this)
                            .load(data.getProfileImage())
                            .into(imageProfile);

                    tvUserName.setText(data.getNickname());
                    tvScore.setText(String.valueOf(data.getRating()));
                    tvReview.setText("| 후기 " + data.getReviewCount());
                    tvSellCount.setText(String.valueOf(data.getSellCount()));
                    tvBuyCount.setText(String.valueOf(data.getBuyCount()));
                    tvReportCount.setText(String.valueOf(data.getReportCount()));

                    reportReason = data.getReportReason();
                    reportMessage = data.getMessage(); // ✅ 서버에서 신고 메시지 받아오기

                    showScheduleGrid(data.getScheduleInfo().getScheduleSummary());

                    // ✅ 앱 최초 실행 시 한 번만 신고 메시지 팝업 띄우기
                    if (reportMessage != null && !reportMessage.isEmpty()) {
                        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                        boolean alreadyShown = prefs.getBoolean("report_shown", false);

                        if (!alreadyShown) {
                            new AlertDialog.Builder(MyPageActivity.this)
                                    .setTitle("신고 알림")
                                    .setMessage(reportMessage)
                                    .setPositiveButton("확인", null)
                                    .show();

                            prefs.edit().putBoolean("report_shown", true).apply(); // ✅ 중복 방지
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPageResponse> call, Throwable t) {
                Toast.makeText(MyPageActivity.this, "서버 통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClickEvents() {
        tvReview.setOnClickListener(v -> startActivity(new Intent(this, ReviewActivity.class)));
        btnHeart.setOnClickListener(v -> startActivity(new Intent(this, FavoritesActivity.class)));
        btnSell.setOnClickListener(v -> startActivity(new Intent(this, SalesHistoryActivity.class)));
        btnBuy.setOnClickListener(v -> startActivity(new Intent(this, BuyHistoryActivity.class)));

        // ✅ 사이렌 아이콘 클릭 시 신고 메시지 확인
        btnReport.setOnClickListener(v -> {
            String messageText = (reportMessage != null && !reportMessage.isEmpty())
                    ? reportMessage
                    : "신고 내역이 없습니다.";

            new AlertDialog.Builder(this)
                    .setTitle("신고 내역")
                    .setMessage(messageText)
                    .setPositiveButton("확인", null)
                    .show();
        });

        btnAddTimetable.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView = inflater.inflate(R.layout.mp_add_timetable, null);

            Spinner daySpinner = dialogView.findViewById(R.id.spinnerDay);
            Spinner timeSpinner = dialogView.findViewById(R.id.spinnerTime);
            EditText editSubject = dialogView.findViewById(R.id.editSubject);
            EditText editProfessor = dialogView.findViewById(R.id.editProfessor);

            ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    new String[]{"월", "화", "수", "목", "금"});
            dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            daySpinner.setAdapter(dayAdapter);

            ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    new String[]{"1교시", "2교시", "3교시", "4교시"});
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSpinner.setAdapter(timeAdapter);

            new AlertDialog.Builder(this)
                    .setTitle("시간표 등록")
                    .setView(dialogView)
                    .setPositiveButton("등록", (dialog, which) -> {
                        String subject = editSubject.getText().toString();
                        String professor = editProfessor.getText().toString();
                        String day = daySpinner.getSelectedItem().toString();
                        String time = timeSpinner.getSelectedItem().toString();

                        String scheduleText = day + " " + time + " " + subject + " (" + professor + ")";
                        uploadSchedule(scheduleText);
                    })
                    .setNegativeButton("취소", null)
                    .show();
        });
    }

    private void uploadSchedule(String scheduleText) {
        RetrofitService api = RetrofitClient.getClient().create(RetrofitService.class);
        Call<Void> call = api.postSchedule(scheduleText);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MyPageActivity.this, "등록 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyPageActivity.this, "등록 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showScheduleGrid(List<String> scheduleList) {
        timetableGrid.removeAllViews();
        timetableGrid.setColumnCount(6); // 요일 5 + 시간 라벨
        timetableGrid.setRowCount(5);   // 최대 4교시 + 헤더

        String[] days = {"", "월", "화", "수", "목", "금"};
        for (int i = 0; i < days.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(days[i]);
            tv.setPadding(8, 8, 8, 8);
            timetableGrid.addView(tv);
        }

        for (int row = 1; row <= 4; row++) {
            for (int col = 0; col < 6; col++) {
                TextView cell = new TextView(this);
                cell.setPadding(8, 8, 8, 8);
                cell.setBackgroundResource(R.drawable.cell_border);
                if (col == 0) cell.setText(row + "교시");
                timetableGrid.addView(cell);
            }
        }

        for (String s : scheduleList) {
            String[] parts = s.split(" ");
            if (parts.length >= 3) {
                String day = parts[0];
                int row = Integer.parseInt(parts[1].replace("교시", ""));
                String subject = parts[2];

                int col = java.util.Arrays.asList("월", "화", "수", "목", "금").indexOf(day) + 1;
                int index = row * 6 + col;
                TextView targetCell = (TextView) timetableGrid.getChildAt(index);
                targetCell.setText(subject);
            }
        }
    }
}
