package com.example.user.randomusersapp.Presenter;

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
    private NetworkModel networkModel;
    private UserListContract contract;

    private Disposable usersDisposable = null;

    public UsersListPresenter(UserListContract contract){
        this.contract = contract;
        networkModel = new NetworkModel();
    }

    public void loadData(){
        usersDisposable = networkModel
                .getUsers()
                .map((Function<UsersListResponse, List<UserItem>>)
                        usersListResponse -> usersListResponse == null ? new ArrayList<>() : usersListResponse.getResults())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userItems -> {
                    contract.hideLoading();
                    contract.setList(userItems);
                }, throwable -> contract.showErrorLoading());
    }

    public void unSubscribe(){
        if(usersDisposable!= null) {
            usersDisposable.dispose();
        }
    }
}
