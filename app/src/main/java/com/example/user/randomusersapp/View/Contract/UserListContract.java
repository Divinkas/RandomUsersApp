package com.example.user.randomusersapp.View.Contract;

import com.example.user.randomusersapp.Model.Data.UserItem;

import java.util.List;

public interface UserListContract {
    void set_list(List<UserItem> list);
    void hide_loading();
    void show_loading();
}
