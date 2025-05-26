package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Signup2Activity extends AppCompatActivity {

    private ImageView imageProfile;
    private EditText editNickname, editBirth;
    private ImageButton btnSelectDate;
    private Button btnDone, btnCheckId;
    private Uri selectedImageUri;

    String email, password, password2, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        imageProfile = findViewById(R.id.imageProfile);
        editNickname = findViewById(R.id.editNickname);
        editBirth = findViewById(R.id.tv_selected_date);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnDone = findViewById(R.id.buttonNext);
        btnCheckId = findViewById(R.id.buttonCheckId);  // 중복확인 버튼 연결

        // 1단계 정보 받기
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        password2 = intent.getStringExtra("password2");
        phone = intent.getStringExtra("phone");

        // 갤러리 열기
        findViewById(R.id.btnAddPhoto).setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setType("image/*");
            startActivityForResult(pickIntent, 100);
        });

        // 날짜 선택
        btnSelectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String birth = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                editBirth.setText(birth);
                editBirth.setTextColor(Color.BLACK);
            }, y, m, d).show();
        });

        // 닉네임 중복 확인
        btnCheckId.setOnClickListener(v -> {
            String nickname = editNickname.getText().toString().trim();
            if (nickname.isEmpty()) {
                Toast.makeText(this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 여기에서 실제 서버에 닉네임 중복 확인 요청 보내는 코드가 들어가야 함
            checkNicknameDuplicate(nickname);
        });

        // 가입 완료
        btnDone.setOnClickListener(v -> {
            String nickname = editNickname.getText().toString().trim();
            String birth = editBirth.getText().toString().trim();

            try {
                JSONObject json = new JSONObject();
                json.put("userId", 1);
                json.put("name", nickname);
                json.put("email", email);
                json.put("password", password);
                json.put("password2", password2);
                json.put("birth", birth);
                json.put("phone", phone);

                Log.d("회원가입 JSON", json.toString());
                Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show();

                // 🔽 로그인 페이지로 이동
                Intent goToLogin = new Intent(Signup2Activity.this, LoginActivity.class);
                goToLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goToLogin);
                finish(); // 뒤로가기 시 현재 액티비티가 남지 않도록 종료

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    // 이미지 선택 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageProfile.setImageURI(selectedImageUri);
        }
    }

    // 닉네임 중복확인 (임시 로직, 실제 서버 요청으로 대체 필요)
    private void checkNicknameDuplicate(String nickname) {
        // 예시: 닉네임이 "taken123"이면 중복
        if (nickname.equalsIgnoreCase("taken123")) {
            Toast.makeText(this, "이미 사용 중인 닉네임입니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
