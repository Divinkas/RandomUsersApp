package com.example.user.randomusersapp.Presenter;

import android.content.Context;

import com.example.user.randomusersapp.Model.Callback.UserCallback;
import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.Model.NetworkModel;
import com.example.user.randomusersapp.View.Contract.UserListContract;

import java.util.List;

public class UsersListPresenter implements UserCallback {
    private Context context;
    private NetworkModel networkModel;
    private UserListContract contract;

    public UsersListPresenter(Context context, UserListContract contract){
        this.context = context;
        this.contract = contract;
        networkModel = new NetworkModel();
    }

    public void load_data(){
        contract.show_loading();
        networkModel.load_random_users(this);
    }

    public void next_load(){
        networkModel.load_random_users(this);
    }

    @Override
    public void load_user_list(List<UserItem> user_list) {
        contract.hide_loading();
        contract.set_list(user_list);
    }
}
