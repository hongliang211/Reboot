package com.example.admin.reboot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.example.admin.reboot.R;

public class activity_chat extends Activity {
    private List<ChatMessage> list;
    private ListView chat_listview;
    private EditText chat_input;
    private Button chat_send;
    private ChatMessageAdapter chatAdapter;
    private ChatMessage chatMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
/*
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);
                }
            }
        });
*/



        initView();
        initListener();
        initData();
    }

    // 1.初始试图
    private void initView() {
        // 1.初始化
        chat_listview = (ListView) findViewById(R.id.chat_listview);
        chat_input = (EditText) findViewById(R.id.chat_input_message);
        chat_send = (Button) findViewById(R.id.chat_send);
    }

    // 2.设置监听事件
    private void initListener() {
        chat_send.setOnClickListener(onClickListener);
    }

    // 3.初始化数据
    private void initData() {
        list = new ArrayList<ChatMessage>();
        list.add(new ChatMessage("您好,被窝为您服务!", ChatMessage.Type.INCOUNT, new Date()));
        chatAdapter = new ChatMessageAdapter(list);
        chat_listview.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    // 4.发送消息聊天
    private void chat() {
        // 1.判断是否输入内容
        final String send_message = chat_input.getText().toString().trim();
        if (TextUtils.isEmpty(send_message)) {
            Toast.makeText(activity_chat.this, "不能发送空白信息哦",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 2.自己输入的内容也是一条记录，记录刷新
        ChatMessage sendChatMessage = new ChatMessage();
        sendChatMessage.setMessage(send_message);
        sendChatMessage.setData(new Date());
        sendChatMessage.setType(ChatMessage.Type.OUTCOUNT);
        list.add(sendChatMessage);
        chatAdapter.notifyDataSetChanged();
        chat_input.setText("");

        // 3.发送你的消息，去服务器端，返回数据
        new Thread() {
            public void run() {
                ChatMessage chat = HttpUtils.sendMessage(send_message);
                Message message = new Message();
                message.what = 0x1;
                message.obj = chat;
                handler.sendMessage(message);
            };
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    chatMessage = (ChatMessage) msg.obj;
                }
                // 添加数据到list中，更新数据
                list.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
            }
        };
    };

    // 点击事件监听
    OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chat_send:
                    chat();
                    break;
            }
        }
    };
/*

    @Override  //沉浸式，去掉状态栏，去掉
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
*/

}

