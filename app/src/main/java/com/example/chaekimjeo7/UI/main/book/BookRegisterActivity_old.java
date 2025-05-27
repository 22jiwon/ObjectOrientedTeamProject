package com.example.chaekimjeo7.UI.main.book;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chaekimjeo7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class BookRegisterActivity_old extends AppCompatActivity {

    private static final int IMAGE_PICK_REQUEST = 101;
    private static final int MAX_IMAGE_COUNT = 5;

    private ArrayList<Uri> imageUris = new ArrayList<>();
    private LinearLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_register);

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Spinner 초기화
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        String[] categories = {"문과대학", "이과대학", "공과대학", "생활과학", "사회과학", "법과대학",
                "경상대학", "음악대학", "약학대학", "미술대학", "교양과목", "자격증"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // 이미지 업로드 관련
        imageContainer = findViewById(R.id.imageContainer);
        ImageView imageUploadButton = findViewById(R.id.imageUploadButton);
        imageUploadButton.setOnClickListener(v -> {
            if (imageUris.size() >= MAX_IMAGE_COUNT) {
                Toast.makeText(this, "이미지는 최대 5장까지 업로드할 수 있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_REQUEST);
        });

        // 등록 버튼 클릭 처리
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            EditText editTitle = findViewById(R.id.editTitle);
            EditText editProfessor = findViewById(R.id.editProfessor);
            EditText editPrice = findViewById(R.id.editPrice);
            EditText editDescription = findViewById(R.id.editDescription);

            String title = editTitle.getText().toString();
            String professor = editProfessor.getText().toString();
            String price = editPrice.getText().toString();
            String description = editDescription.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            if (title.isEmpty() || price.isEmpty() || description.isEmpty() || imageUris.isEmpty()) {
                Toast.makeText(this, "모든 필드를 채우고 이미지를 최소 1장 업로드해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "교재 등록 요청을 전송합니다 (서버 없음).", Toast.LENGTH_SHORT).show();
        });

        // 하단 네비게이션 처리 (예시)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            // TODO: 네비게이션 동작 구현
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imageUris.add(imageUri);

                ImageView preview = new ImageView(this);
                preview.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
                preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                preview.setImageURI(imageUri);

                imageContainer.addView(preview);
            }
        }
    }
}