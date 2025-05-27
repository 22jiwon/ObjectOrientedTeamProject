package com.example.chaekimjeo7.UI.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chaekimjeo7.Model.ChatMessage;
import com.example.chaekimjeo7.R;

import java.util.List;

public class ChatMessageAdapter extends BaseAdapter {

    private Context context;
    private List<ChatMessage> messageList;

    public ChatMessageAdapter(Context context, List<ChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_message_item, parent, false);
        }

        ChatMessage message = messageList.get(position);

        LinearLayout leftLayout = view.findViewById(R.id.leftMessageLayout);
        LinearLayout rightLayout = view.findViewById(R.id.rightMessageLayout);
        TextView leftText = view.findViewById(R.id.leftMessageText);
        TextView rightText = view.findViewById(R.id.rightMessageText);

        if (message.isSentByMe()) {
            // 오른쪽 말풍선 표시 (내 메시지)
            leftLayout.setVisibility(View.GONE);
            rightLayout.setVisibility(View.VISIBLE);
            rightText.setText(message.getMessageText());
        } else {
            // 왼쪽 말풍선 표시 (상대방 메시지)
            rightLayout.setVisibility(View.GONE);
            leftLayout.setVisibility(View.VISIBLE);
            leftText.setText(message.getMessageText());
        }

        return view;
    }
}
