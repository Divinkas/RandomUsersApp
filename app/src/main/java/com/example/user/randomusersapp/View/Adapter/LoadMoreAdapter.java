package com.example.user.randomusersapp.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class LoadMoreAdapter<T> extends BaseAdapter<T> {
    private final int progressLayoutResId;

    public LoadMoreAdapter(int linkLayoutProgress, Context context, List<T> list){
        super(list, context);
        progressLayoutResId = linkLayoutProgress;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProgressViewHolder){
            ((ProgressViewHolder) holder).view.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProgressViewHolder(LayoutInflater.from(context).inflate(progressLayoutResId, parent, false));
    }

    static class ProgressViewHolder extends BaseViewHolder {
        View view;
        ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
