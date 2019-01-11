package com.example.user.randomusersapp.Model;

import com.bumptech.glide.request.RequestOptions;
import com.example.user.randomusersapp.R;

public class Constants {
    public static final String result_parametr = "results";
    public static final String inc_parametr = "inc";
    //public static final String type_val = "noinfo";

    static final int count_users_loaded = 20;
    static final String inc_parametr_list = "name,medium,thumbnail,phone,email,picture,dob";

    public static final String user_key= "user_key";

    public static final RequestOptions options = new RequestOptions().centerCrop().error(R.drawable.user_default);
}
