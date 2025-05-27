package com.example.chaekimjeo7.UI.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.example.chaekimjeo7.Model.LoginResponse;
import com.example.chaekimjeo7.Network.RetrofitClient;
import com.example.chaekimjeo7.Network.RetrofitService;
import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button loginButton;
    private TextView textLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        textLinks = findViewById(R.id.textLinks);

        // 회원가입 이동
        textLinks.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Signup1Activity.class);
            startActivity(intent);
        });

        // 로그인 시도
        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        RetrofitService service = RetrofitClient.getClient().create(RetrofitService.class);
        service.login(loginData).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse result = response.body();
                    if (result != null && !result.success && "banned".equals(result.reason)) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("접근 제한")
                                .setMessage(result.message != null ? result.message : "접근이 제한된 계정입니다.")
                                .setPositiveButton("확인", null)
                                .show();
                        return;
                    }

                    Toast.makeText(getApplicationContext(), result.nickname + "님 로그인 성공!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "서버 응답 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
