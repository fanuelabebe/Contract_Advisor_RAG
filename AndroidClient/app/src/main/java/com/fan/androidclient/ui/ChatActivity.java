package com.fan.androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.fan.androidclient.R;
import com.fan.androidclient.databinding.ActivityChatBinding;
import com.fan.androidclient.greendao.implementation.Insert;
import com.fan.androidclient.greendao.models.History;
import com.fan.androidclient.implementation.ChatRecyclerViewAdapter;
import com.fan.androidclient.implementation.GsonParser;
import com.fan.androidclient.implementation.Volley;
import com.fan.androidclient.models.ChatData;
import com.fan.androidclient.models.RagResponse;
import com.fan.androidclient.models.RequestPackage;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatActivity extends AppCompatActivity implements ChatRecyclerViewAdapter.OnItemClick, Volley.OnCallDone {
    private final String TAG = ChatActivity.this.getClass().getSimpleName();
    ActivityChatBinding binding;
    public ChatRecyclerViewAdapter adapter;
    public List<ChatData> chatDataList;
    Handler handler;
    ExecutorService executor;
//    public String ragUrl = "http://10.0.2.2:8000/getanswer?question=";
    public String ragUrl = "http://192.168.43.74:8000/getanswer?question=";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US);
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
        binding.topAppBar.setNavigationOnClickListener(view -> {
            this.finish();
        });
        binding.sendIV.setOnClickListener(view ->{
            String question = binding.chatET.getText().toString();
            if(!question.equals("")) {
                binding.progress.setVisibility(View.VISIBLE);
                binding.sendIV.setVisibility(View.INVISIBLE);
                addSentMessageToList(question);
                getAnswer(question);
            }else{
                Toast.makeText(ChatActivity.this,"Please write something",Toast.LENGTH_SHORT).show();
            }
        });
        binding.historyIV.setOnClickListener(view->{
            startActivity(new Intent(this,HistoryActivity.class));
        });
    }

    public void getAnswer(String question){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod(Request.Method.GET);
        requestPackage.setUri(ragUrl+question);
        Volley volley = new Volley(ChatActivity.this,requestPackage,60 * 1000);
        volley.onCallDone = this;
        volley.getJsonObject();
    }

    public void saveToHistory(RagResponse ragResponse){
        executor.execute(()->{
            History history = new History(ragResponse.getQuestion(),ragResponse.getAnswer(),"",new Date());
            Insert.insertHistoryData(ChatActivity.this,history);
        });
    }

    public void addSentMessageToList(String question){
        chatDataList.add(new ChatData(question,true,simpleDateFormat.format(new Date())));
        adapter.mChatDataList = chatDataList;
        binding.chatET.setText(null);
        if(chatDataList.size() > 0){
            adapter.notifyItemRangeInserted(chatDataList.size(),chatDataList.size());
            binding.chatRV.smoothScrollToPosition(chatDataList.size() - 1);
        }
    }

    public void addReceivedMessageToList(String received){
        handler.post(()->{
            chatDataList.add(new ChatData(received,false,simpleDateFormat.format(new Date())));
            adapter.mChatDataList = chatDataList;
            adapter.notifyItemRangeInserted(chatDataList.size(),chatDataList.size());
            binding.chatRV.smoothScrollToPosition(chatDataList.size() - 1);

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
    @Override
    public void onJsonObjectResponse(JSONObject response) {
        Log.v(TAG,"Response: "+response);
        binding.sendIV.setVisibility(View.VISIBLE);
        binding.progress.setVisibility(View.GONE);
        RagResponse ragResponse = GsonParser.parseRagResponse(response.toString());
        if(ragResponse != null){
            addReceivedMessageToList(ragResponse.getAnswer());
            saveToHistory(ragResponse);
        }
    }

    @Override
    public void onStringResponse(String response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        binding.sendIV.setVisibility(View.VISIBLE);
        binding.progress.setVisibility(View.GONE);
    }
}