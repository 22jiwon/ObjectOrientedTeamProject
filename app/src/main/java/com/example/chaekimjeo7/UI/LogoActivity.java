// LogoActivity.java — 아래 코드로 정리해 주세요
package com.example.chaekimjeo7.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaekimjeo7.UI.auth.LoginActivity;
import com.example.chaekimjeo7.R;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 💡 2초 후 LoginActivity로 이동
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LogoActivity.this, LoginActivity.class); // 또는 MainActivity
            startActivity(intent);
            finish(); // 뒤로가기 시 다시 로고 안 보이도록
        }, 2000);
    }
}
