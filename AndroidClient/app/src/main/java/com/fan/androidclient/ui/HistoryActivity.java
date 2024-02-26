package com.fan.androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.fan.androidclient.R;
import com.fan.androidclient.databinding.ActivityHistoryBinding;
import com.fan.androidclient.greendao.implementation.Select;
import com.fan.androidclient.greendao.models.History;
import com.fan.androidclient.implementation.ChatRecyclerViewAdapter;
import com.fan.androidclient.implementation.HistoryRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity implements HistoryRecyclerAdapter.OnItemClick{
    ActivityHistoryBinding binding;
    HistoryRecyclerAdapter adapter;
    Handler handler;
    ExecutorService executor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        setOnClickToViews();
        getHistoryAndShow();
    }
    public void setOnClickToViews(){
        binding.topAppBar.setNavigationOnClickListener(view -> {
            this.finish();
        });
    }

    public void getHistoryAndShow(){
        binding.progress.setVisibility(View.VISIBLE);
        executor.execute(()->{
            List<History> historyList = getHistoryList();
            handler.post(()->{
                updateList(historyList);
                binding.progress.setVisibility(View.GONE);
            });
        });
    }

    public List<History> getHistoryList(){
        List<History> historyList = new ArrayList<>();
        historyList.add(new History("What are the payments to the Advisor under the Agreement?",
                "According to section 6: 1. Fees of $9 per hour up to a monthly limit of $1,500, 2. Workspace expense of $100 per month, 3. " +
                        "Other reasonable and actual expenses if approved by the company in writing and in advance.","",new Date()));
        historyList.add(new History("Can the Agreement or any of its obligations be assigned?",
                "Under section 1.1 the Advisor can’t assign any of his obligations without the prior written consent of the Company, 2. Under section 9 " +
                        " the Advisor may not assign the Agreement and the Company may assign it, 3 Under section 9 of the Undertaking the Company may assign the Undertaking.","",new Date()));
//        return Select.fetchStoryList(HistoryActivity.this);
        return historyList;
    }
    public void updateList(List<History> historyList){
        adapter = new HistoryRecyclerAdapter(HistoryActivity.this,historyList);
        adapter.onItemClick = this;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.historyRV.setLayoutManager(linearLayoutManager);
        binding.historyRV.setAdapter(adapter);
    }

    @Override
    public void onItemClick(History history) {

    }
}