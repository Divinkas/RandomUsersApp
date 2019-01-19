package com.example.user.randomusersapp.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.randomusersapp.Model.Constants;
import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.R;
import com.example.user.randomusersapp.View.UserDetailsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_PROGRESS = 100;
    private final int progressLayoutResId;
    private boolean isShowProgress = false;

    private Context context;
    private List<UserItem> list;


    public UserAdapter(int linkLayoutProgress, Context context, List<UserItem> list) {
        progressLayoutResId = linkLayoutProgress;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        if(isShowProgress) return list.size()+1;
        return list.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isShowProgress && viewType == TYPE_PROGRESS) {
            view = LayoutInflater.from(context).inflate(progressLayoutResId, parent, false);
            return new LoadMoreViewHolder(view);

        } else{
            view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
            return new UsersViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size() && isShowProgress) {
            return TYPE_PROGRESS;
        } else return 1;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UsersViewHolder) {
            UsersViewHolder contactHolder = ((UsersViewHolder) holder);
            Glide
                    .with(context)
                    .load(list.get(position).picture.medium)
                    .apply(Constants.GLIDE_OPTIONS)
                    .into(contactHolder.circleImageViewContactImage);

            contactHolder.textViewContactFullName
                    .setText(list.get(position).name.first + " " + list.get(position).name.last);

            contactHolder.linearLayoutContainer.setOnClickListener(v -> {
                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra(Constants.USER_KEY, list.get(position));
                context.startActivity(intent);
            });
        }
        if(holder instanceof LoadMoreViewHolder){
            ((LoadMoreViewHolder)holder).view.setVisibility(View.VISIBLE);
        }
    }

    public boolean isBottomProgressPosition(int position) {
        return position == list.size() && isShowProgress;
    }

    public void startLoadMore(){
        isShowProgress = true;
        notifyDataSetChanged();
    }

    public void stopLoadMore(){
        isShowProgress = false;
        notifyDataSetChanged();
    }

    public void addList(List<UserItem> newList){
        list.addAll(newList);
        notifyDataSetChanged();
    }
    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayoutContainer;
        TextView textViewContactFullName;
        CircleImageView circleImageViewContactImage;

        UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContactFullName = itemView.findViewById(R.id.tv_user_full_name);
            circleImageViewContactImage = itemView.findViewById(R.id.cv_user_image);
            linearLayoutContainer = itemView.findViewById(R.id.ll_user_container);
        }
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        View view;
        LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }

}
