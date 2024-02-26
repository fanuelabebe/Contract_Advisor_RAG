package com.fan.androidclient.implementation;

import static com.fan.androidclient.ui.ChatActivity.simpleDateFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.androidclient.R;
import com.fan.androidclient.databinding.HistoryListItemBinding;
import com.fan.androidclient.databinding.SentChatListItemBinding;
import com.fan.androidclient.greendao.models.History;
import com.fan.androidclient.models.ChatData;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    public List<History> mHistoryDataList;
    public OnItemClick onItemClick;
    public interface OnItemClick{
    void onItemClick(History history);
    }
    public HistoryRecyclerAdapter(Context mContext, List<History> mHistoryDataList) {
        this.mContext = mContext;
        this.mHistoryDataList = mHistoryDataList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryRecyclerAdapter.HistoryViewHolder(
                HistoryListItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History history = mHistoryDataList.get(position);
        ((HistoryViewHolder) holder).setData(history);

        holder.itemView.setOnClickListener(view ->{
            onItemClick.onItemClick(history);
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryDataList.size();
    }
    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final HistoryListItemBinding binding;
        HistoryViewHolder(HistoryListItemBinding historyListItemBinding){
            super(historyListItemBinding.getRoot());
            binding = historyListItemBinding;
        }
        void setData(History history){
            binding.question.setText("Q: " + history.getQuestion());
            binding.answer.setText("A: "+ history.getAnswer());
            binding.reference.setText(history.getReference());
            binding.dateTV.setText(simpleDateFormat.format(history.getDate()));
        }
    }
}
