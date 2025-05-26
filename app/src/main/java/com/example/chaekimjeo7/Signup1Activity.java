package com.example.chaekimjeo7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class Signup1Activity extends AppCompatActivity {

    EditText emailFront, password, passwordConfirm;
    EditText phone1, phone2, phone3;
    Button btnCheckEmail, btnNext;

    final List<String> registeredEmails = Arrays.asList("test@sookmyung.ac.kr", "admin@sookmyung.ac.kr");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        emailFront = findViewById(R.id.editTextEmailFront);
        password = findViewById(R.id.editTextPassword);
        passwordConfirm = findViewById(R.id.editTextPasswordConfirm);
        phone1 = findViewById(R.id.editTextPhone1);
        phone2 = findViewById(R.id.editTextPhone2);
        phone3 = findViewById(R.id.editTextPhone3);
        btnCheckEmail = findViewById(R.id.buttonCheckId);
        btnNext = findViewById(R.id.buttonNext);

        // 이메일 중복 확인
        btnCheckEmail.setOnClickListener(v -> {
            String email = emailFront.getText().toString().trim() + "@sookmyung.ac.kr";
            if (registeredEmails.contains(email)) {
                Toast.makeText(this, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 다음 버튼 → Signup2Activity로 이동
        btnNext.setOnClickListener(v -> {
            String email = emailFront.getText().toString().trim() + "@sookmyung.ac.kr";
            String pw = password.getText().toString().trim();
            String pw2 = passwordConfirm.getText().toString().trim();
            String phone = phone1.getText().toString().trim() + "-" +
                    phone2.getText().toString().trim() + "-" +
                    phone3.getText().toString().trim();

            Intent intent = new Intent(Signup1Activity.this, Signup2Activity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", pw);
            intent.putExtra("password2", pw2);
            intent.putExtra("phone", phone);
            startActivity(intent);
        });
    }
}
