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
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Signup2Activity extends AppCompatActivity {

    private ImageView imageProfile;
    private EditText editNickname, editBirth;
    private RadioButton checkboxRealname;
    private ImageButton btnSelectDate;
    private Button btnDone;
    private Uri selectedImageUri;

    String email, password, password2, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        imageProfile = findViewById(R.id.imageProfile);
        editNickname = findViewById(R.id.editNickname);
        checkboxRealname = findViewById(R.id.checkboxRealname);
        editBirth = findViewById(R.id.tv_selected_date);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnDone = findViewById(R.id.buttonNext);

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

        // 가입 완료
        btnDone.setOnClickListener(v -> {
            String nickname = editNickname.getText().toString().trim();
            String name = checkboxRealname.isChecked() ? "홍길동" : nickname;
            String birth = editBirth.getText().toString().trim();

            try {
                JSONObject json = new JSONObject();
                json.put("userId", 1);
                json.put("name", name);
                json.put("email", email);
                json.put("password", password);
                json.put("password2", password2);
                json.put("birth", birth);
                json.put("phone", phone);

                Log.d("회원가입 JSON", json.toString());
                Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show();

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
}
