package com.example.chaekimjeo7.UI.chat.list;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chaekimjeo7.UI.chat.room.ChatRoomActivity;
import com.example.chaekimjeo7.UI.main.MainActivity;
import com.example.chaekimjeo7.Model.ChatRoom;
import com.example.chaekimjeo7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private ListView chatListView;
    private List<ChatRoom> chatRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListView = findViewById(R.id.chatListView);
        chatRooms = new ArrayList<>();

        // 더미
        chatRooms.add(new ChatRoom(
                "room-001",               // roomId
                "user-001",               // otherUserId
                "이기연",                 // otherUserName
                "https://example.com/p1.jpg", // otherUserProfileImage
                "안녕하세요 책 구매하고 싶어요", // lastMessage
                "2025-05-03T10:30:00"     // lastSentAt
        ));

        chatRooms.add(new ChatRoom(
                "room-002",
                "user-002",
                "정예원",
                "https://example.com/p2.jpg",
                "안녕하세요!",
                "2025-05-03T11:00:00"
        ));


        ChatListAdapter adapter = new ChatListAdapter(this, chatRooms);
        chatListView.setAdapter(adapter);

        // 채팅방 클릭 시 채팅방 화면으로 이동
        chatListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            ChatRoom selectedChat = chatRooms.get(position);
            Intent intent = new Intent(ChatListActivity.this, ChatRoomActivity.class);
            intent.putExtra("userName", selectedChat.getOtherUserName());
            startActivity(intent);
        });

        // ✅ 하단 네비게이션 바 클릭 이벤트 처리
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_chat); // 현재 탭 강조

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(ChatListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.nav_chat) {
                // 현재 화면이므로 아무 동작 안 함
                return true;
            } else if (id == R.id.nav_profile) {
                // 프로필 화면 연결할 경우 여기에 작성
                return true;
            }

            return false;
        });
    }
}
