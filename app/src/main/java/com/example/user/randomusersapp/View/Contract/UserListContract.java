package com.example.user.randomusersapp.View.Contract;

import com.example.user.randomusersapp.Model.Data.UserItem;

import java.util.List;

public interface UserListContract {
    void setList(List<UserItem> list);
    void hideLoading();
    void showLoading();
    void showErrorLoading();
}
