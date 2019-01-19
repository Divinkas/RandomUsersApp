package com.example.user.randomusersapp.Model;

import com.bumptech.glide.request.RequestOptions;
import com.example.user.randomusersapp.R;

public class Constants {
    public static final String RESULT_PARAM = "results";
    public static final String ICN_PARAM = "inc";

    static final int COUNT_RANDOM_CONTACTS = 20;
    static final String INC_LIST_PARAM = "name,medium,thumbnail,phone,email,picture,dob";

    public static final String USER_KEY = "user_key";

    public static final RequestOptions GLIDE_OPTIONS = new RequestOptions().centerCrop().error(R.drawable.user_default);
}
