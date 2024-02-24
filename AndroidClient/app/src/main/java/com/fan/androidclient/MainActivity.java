package com.fan.androidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.fan.androidclient.databinding.ActivityMainBinding;
import com.fan.androidclient.ui.ChatActivity;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String [] contracts = {"Raptor Contract"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSpinnerData();
        setOnClickToViews();

    }
    public void setOnClickToViews(){
        binding.loginB.setOnClickListener(view ->{
            startActivity(new Intent(this, ChatActivity.class));
        });
    }
    public void setSpinnerData(){
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, contracts);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.contractSp.setAdapter(ad);
    }
}