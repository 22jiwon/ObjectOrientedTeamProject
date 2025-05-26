package com.example.myapplication;

import android.content.Intent;
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
import com.bumptech.glide.Glide;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.gridlayout.widget.GridLayout;


public class MyPageActivity extends AppCompatActivity {

    private ImageView imageProfile;
    private TextView tvUserName, tvScore, tvReview, tvSellCount, tvBuyCount, tvReportCount;
    private ImageView btnHeart, btnReport;
    private LinearLayout btnSell, btnBuy, btnAddTimetable;
    private String reportReason = "";
    private GridLayout timetableGrid;

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

        loadMyPageData();
        setClickEvents();
    }

    private void loadMyPageData() {
        MyPageApi api = ApiClient.getClient().create(MyPageApi.class);
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

                    showScheduleGrid(data.getScheduleInfo().getScheduleSummary());
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

        btnReport.setOnClickListener(v -> {
            String reasonMessage;
            switch (reportReason) {
                case "INSULT":
                    reasonMessage = "비방 및 욕설";
                    break;
                case "INAPPROPRIATE_IMAGE":
                    reasonMessage = "부적절한 사진";
                    break;
                case "NO_SHOW":
                    reasonMessage = "무통보 거래 파기";
                    break;
                default:
                    reasonMessage = "기타";
            }

            new AlertDialog.Builder(this)
                    .setTitle("신고 사유")
                    .setMessage(reasonMessage)
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
        MyPageApi api = ApiClient.getClient().create(MyPageApi.class);
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
