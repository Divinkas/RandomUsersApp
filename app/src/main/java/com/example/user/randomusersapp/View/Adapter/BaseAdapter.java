package com.example.user.randomusersapp.View.Adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> list;
    protected final Context context;

    BaseAdapter(List<T> list, Context context){
        this.list = list;
        this.context = context;
    }

    BaseAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public int getItemCount() { return list.size(); }

    public void add_list(List<T> new_list){
        list.addAll(new_list);
        notifyDataSetChanged();
    }
    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }
    public Context getContext() { return context; }
    public List<T> getList() { return list; }
    public void setList(List<T> list) { this.list = list; }

    static class BaseViewHolder extends RecyclerView.ViewHolder{
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
