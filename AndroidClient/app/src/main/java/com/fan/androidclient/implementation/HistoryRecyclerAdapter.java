package com.fan.androidclient.implementation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.androidclient.R;
import com.fan.androidclient.greendao.models.History;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder> {
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
        View viewV = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);

        return new HistoryViewHolder(viewV);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = mHistoryDataList.get(position);
        holder.questionText.setText(history.getQuestion());
        holder.answerText.setText(history.getAnswer());
        holder.dateTextView.setText(history.getReference());
        holder.referenceText.setText(history.getReference());
        holder.itemView.setOnClickListener(view ->{
            onItemClick.onItemClick(history);
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryDataList.size();
    }
    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView questionText;
        public TextView answerText;
        public TextView referenceText;
        public TextView dateTextView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
//            questionText = itemView.findViewById(R.id.sn_tv);
//            answerText = itemView.findViewById(R.id.points_tv);
//            dateTextView = itemView.findViewById(R.id.points_user_tv);

        }
    }
}
