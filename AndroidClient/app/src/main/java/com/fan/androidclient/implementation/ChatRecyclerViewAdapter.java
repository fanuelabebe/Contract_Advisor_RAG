package com.fan.androidclient.implementation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.androidclient.R;
import com.fan.androidclient.databinding.RecievedChatListItemBinding;
import com.fan.androidclient.databinding.SentChatListItemBinding;
import com.fan.androidclient.greendao.models.History;
import com.fan.androidclient.models.ChatData;

import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    public List<ChatData> mChatDataList;
    public OnItemClick onItemClick;
    public int SENT_MESSAGE = 1;
    public int RECEIVED_MESSAGE = 2;
    public interface OnItemClick{
        void onItemClick(ChatData chatData);
    }
    public ChatRecyclerViewAdapter(Context mContext, List<ChatData> mChatDataList) {
        this.mContext = mContext;
        this.mChatDataList = mChatDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType == SENT_MESSAGE){
           return new SentChatViewHolder(
                   SentChatListItemBinding.inflate(
                           LayoutInflater.from(parent.getContext()),
                           parent,
                           false
                   )
           );
       }else{
           return new ReceivedChatViewHolder(
                   RecievedChatListItemBinding.inflate(
                           LayoutInflater.from(parent.getContext()),
                           parent,
                           false
                   )
           );
       }
    }

    @Override
    public int getItemViewType(int position) {
        if(mChatDataList.get(position).isSent()){
            return SENT_MESSAGE;
        }else {
            return RECEIVED_MESSAGE;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatData chatData = mChatDataList.get(position);
        if(getItemViewType(position) == SENT_MESSAGE){
            ((SentChatViewHolder) holder).setData(chatData);
        }else{
            ((ReceivedChatViewHolder) holder).setData(chatData);
        }

        holder.itemView.setOnClickListener(view ->{
            onItemClick.onItemClick(chatData);
        });
    }

    @Override
    public int getItemCount() {
        return mChatDataList.size();
    }
    static class SentChatViewHolder extends RecyclerView.ViewHolder {
       private final SentChatListItemBinding binding;
        SentChatViewHolder(SentChatListItemBinding sentChatListItemBinding){
           super(sentChatListItemBinding.getRoot());
           binding = sentChatListItemBinding;
       }
       void setData(ChatData chatData){
            binding.question.setText(chatData.getMessage());
            binding.date.setText(chatData.getDate());
       }
    }

    static class ReceivedChatViewHolder extends RecyclerView.ViewHolder {
        private final RecievedChatListItemBinding binding;
        ReceivedChatViewHolder(RecievedChatListItemBinding recievedChatListItemBinding){
            super(recievedChatListItemBinding.getRoot());
            binding = recievedChatListItemBinding;
        }
        void setData(ChatData chatData){
            binding.message.setText(chatData.getMessage());
            binding.date.setText(chatData.getDate());
        }
    }

}
