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

public class UserAdapter extends LoadMoreAdapter<UserItem> {
    private final int TYPE_PROGRESS = 100;
    private boolean isShowProgress = false;

    public UserAdapter(int linkLayoutProgress, Context context, List<UserItem> list) {
        super(linkLayoutProgress, context, list);
    }

    public void start_load_more() {
        isShowProgress = true;
        notifyDataSetChanged();
    }

    public void stop_load_more() {
        isShowProgress = false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isShowProgress && viewType == TYPE_PROGRESS) {
            return super.onCreateViewHolder(parent, viewType);
        } else
            return new UsersViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false));
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
            UsersViewHolder user_holder = ((UsersViewHolder) holder);
            Glide.with(getContext()).load(list.get(position).picture.medium).apply(Constants.options).into(user_holder.user_image);
            user_holder.tv_user_full_name.setText(list.get(position).name.first + " " + list.get(position).name.last);
            user_holder.ll_container.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), UserDetailsActivity.class);
                intent.putExtra(Constants.user_key, list.get(position));
                getContext().startActivity(intent);
            });
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (isShowProgress) { return list.size() + 1; }
        return list.size();
    }

    public boolean isBottomProgressPosition(int position) {
        return position == list.size() && isShowProgress;
    }

    class UsersViewHolder extends BaseViewHolder {
        LinearLayout ll_container;
        TextView tv_user_full_name;
        CircleImageView user_image;

        UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_full_name = itemView.findViewById(R.id.tv_user_full_name);
            user_image = itemView.findViewById(R.id.cv_user_image);
            ll_container = itemView.findViewById(R.id.ll_user_container);
        }
    }
}
