package com.example.user.randomusersapp.Model;

import com.example.user.randomusersapp.Model.Callback.UserCallback;
import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.Model.Data.UsersListResponse;
import com.example.user.randomusersapp.Model.Retrofit.IserverSender;
import com.example.user.randomusersapp.Model.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NetworkModel {
    private IserverSender iserverSender;

    public NetworkModel() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iserverSender = retrofit.create(IserverSender.class);
    }

    public void load_random_users(UserCallback callback) {
        Observer<List<UserItem>> observer = new Observer<List<UserItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onNext(List<UserItem> results) {

                callback.load_user_list(results);
            }

        };

        iserverSender.load_random_users(
                Constants.count_users_loaded,
                Constants.inc_parametr_list)
                .map((Function<UsersListResponse, List<UserItem>>)
                    usersListResponse -> usersListResponse == null ? new ArrayList<>() : usersListResponse.getResults())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
