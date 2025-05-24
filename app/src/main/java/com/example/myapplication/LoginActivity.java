package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONObject;
import org.json.JSONException;

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

        // ✅ 회원가입 이동 처리
        textLinks.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,Signup1Activity.class);
            startActivity(intent);
        });

        // ✅ 로그인 버튼 처리
        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://YOUR_SERVER_IP:8080/api/login"; // 서버 주소로 수정

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                loginData,
                response -> {
                    String nickname = response.optString("nickname");
                    Toast.makeText(getApplicationContext(), nickname + "님 로그인 성공!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}
