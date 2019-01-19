package com.example.user.randomusersapp.Model.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersListResponse {
    @SerializedName("results")
    @Expose
    private List<UserItem> results = null;

    public List<UserItem> getResults() {
        return results;
    }

}
