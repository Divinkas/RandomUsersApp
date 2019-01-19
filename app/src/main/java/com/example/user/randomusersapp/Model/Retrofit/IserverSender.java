package com.example.user.randomusersapp.Model.Retrofit;

import com.example.user.randomusersapp.Model.Constants;
import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.Model.Data.UsersListResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IserverSender {
    @GET("api")
    Observable<UsersListResponse> load_random_users(@Query(Constants.RESULT_PARAM) int result,
                                                    @Query(Constants.ICN_PARAM) String inc);

}
