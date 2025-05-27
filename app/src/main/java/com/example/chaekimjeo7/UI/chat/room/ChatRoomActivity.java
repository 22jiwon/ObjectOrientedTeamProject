package com.example.chaekimjeo7.UI.chat.room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chaekimjeo7.UI.chat.ChatMessageAdapter;
import com.example.chaekimjeo7.Model.ChatMessage;
import com.example.chaekimjeo7.R;
import com.example.chaekimjeo7.UI.chat.list.ChatListActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView messageListView;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> messageList;
    private EditText messageEditText;
    private ImageView sendButton;
    private TextView confirmTransactionButton; // ✅ Button → TextView로 변경

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // ① 상단 이름 표시
        String userName = getIntent().getStringExtra("userName");
        TextView titleView = findViewById(R.id.chatRoomTitle);
        titleView.setText(userName);

        // ② 뒤로가기 버튼 클릭 처리
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatRoomActivity.this, ChatListActivity.class);
            startActivity(intent);
            finish();
        });

        // ③ 메시지 리스트 연결
        messageListView = findViewById(R.id.messageListView);
        messageList = new ArrayList<>();

        // 예시 메시지
        messageList.add(new ChatMessage("안녕하세요! 책 구매하고 싶어요.", false));
        messageList.add(new ChatMessage("넵! 가능합니다. 상태는 거의 새 책이에요.", true));
        messageList.add(new ChatMessage("좋아요, 언제 거래 가능하실까요?", false));

        adapter = new ChatMessageAdapter(this, messageList);
        messageListView.setAdapter(adapter);

        // ④ 메시지 입력창 및 전송 버튼
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String text = messageEditText.getText().toString().trim();
            if (!text.isEmpty()) {
                ChatMessage newMessage = new ChatMessage(text, true);
                messageList.add(newMessage);
                adapter.notifyDataSetChanged();
                messageEditText.setText("");

                messageListView.post(() -> messageListView.setSelection(adapter.getCount() - 1));
            }
        });

        // ✅ ⑤ 거래확정 버튼 처리 (TextView → setOnClickListener)
        confirmTransactionButton = findViewById(R.id.confirmTransactionButton);
        confirmTransactionButton.setOnClickListener(v -> {
            Toast.makeText(this, "거래가 확정되었습니다.", Toast.LENGTH_SHORT).show();

            // TODO: 구매자/판매자 내역 저장 로직
        });
    }
}
