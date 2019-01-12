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
import com.example.user.randomusersapp.Presenter.UsersListPresenter;
import com.example.user.randomusersapp.R;
import com.example.user.randomusersapp.View.UserDetailsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private Context context;
    private UsersListPresenter presenter;
    private List<UserItem> list;

    public UsersAdapter(Context context, List<UserItem> list, UsersListPresenter presenter) {
        this.context = context;
        this.list = list;
        this.presenter = presenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Glide.with(context).load(list.get(position).picture.medium).apply(Constants.options).into(holder.user_image);
        holder.tv_user_full_name.setText(list.get(position).name.first + " " + list.get(position).name.last);
        holder.ll_container.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserDetailsActivity.class);
            intent.putExtra(Constants.user_key, list.get(position));
            context.startActivity(intent);
        });
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add_list(List<UserItem> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
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
