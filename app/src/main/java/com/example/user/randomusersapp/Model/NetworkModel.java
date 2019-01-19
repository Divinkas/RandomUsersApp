package com.example.user.randomusersapp.Model;

import com.example.user.randomusersapp.Model.Data.UsersListResponse;
import com.example.user.randomusersapp.Model.Retrofit.IserverSender;
import com.example.user.randomusersapp.Model.Retrofit.RetrofitClient;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class NetworkModel {
    private IserverSender iserverSender;

    public NetworkModel() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iserverSender = retrofit.create(IserverSender.class);
    }

    public Observable<UsersListResponse> getUsers(){
        return iserverSender.load_random_users(
                Constants.COUNT_RANDOM_CONTACTS,
                Constants.INC_LIST_PARAM);
    }
}
