package com.fan.androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.fan.androidclient.R;
import com.fan.androidclient.databinding.ActivityChatBinding;
import com.fan.androidclient.implementation.ChatRecyclerViewAdapter;
import com.fan.androidclient.models.ChatData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatActivity extends AppCompatActivity implements ChatRecyclerViewAdapter.OnItemClick {
    ActivityChatBinding binding;
    public ChatRecyclerViewAdapter adapter;
    public List<ChatData> chatDataList;
    Handler handler;
    ExecutorService executor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatDataList = new ArrayList<>();
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        setOnClickToViews();
        updateList();
    }
    public void setOnClickToViews(){
        binding.sendIV.setOnClickListener(view ->{

            chatDataList.add(new ChatData(binding.chatET.getText().toString(),true,"Feb 24, 2023 10:00"));
            adapter.mChatDataList = chatDataList;
            binding.chatET.setText(null);
            if(chatDataList.size() > 0){
                adapter.notifyItemRangeInserted(chatDataList.size(),chatDataList.size());
                binding.chatRV.smoothScrollToPosition(chatDataList.size() - 1);
            }
        });
    }
    public void updateList(){
        adapter = new ChatRecyclerViewAdapter(ChatActivity.this,chatDataList);
        adapter.onItemClick = this;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRV.setLayoutManager(linearLayoutManager);
        binding.chatRV.setAdapter(adapter);
    }

    @Override
    public void onItemClick(ChatData chatData) {

    }
}