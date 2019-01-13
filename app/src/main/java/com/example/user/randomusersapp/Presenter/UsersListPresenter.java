package com.example.user.randomusersapp.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.Model.Data.UsersListResponse;
import com.example.user.randomusersapp.Model.NetworkModel;
import com.example.user.randomusersapp.View.Contract.UserListContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UsersListPresenter {
    private Context context;
    private NetworkModel networkModel;
    private UserListContract contract;

    private Disposable users_disposable = null;

    public UsersListPresenter(Context context, UserListContract contract){
        this.context = context;
        this.contract = contract;
        networkModel = new NetworkModel();
    }

    public void load_data(){
        users_disposable = networkModel
                .get_users()
                .map((Function<UsersListResponse, List<UserItem>>)
                        usersListResponse -> usersListResponse == null ? new ArrayList<>() : usersListResponse.getResults())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userItems -> {
                    contract.hide_loading();
                    contract.set_list(userItems);
                }, throwable -> Toast.makeText(context, "Ошибка загрузки данных!", Toast.LENGTH_SHORT).show());
    }

    public void next_load(){
        load_data();
    }

    public void un_subscribe(){
        if(users_disposable!= null) {
            users_disposable.dispose();
        }
    }
}
