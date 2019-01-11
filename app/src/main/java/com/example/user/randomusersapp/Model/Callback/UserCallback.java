package com.example.user.randomusersapp.Model.Callback;

import com.example.user.randomusersapp.Model.Data.UserItem;

import java.util.List;

public interface UserCallback {
    void load_user_list(List<UserItem> user_list);
}
