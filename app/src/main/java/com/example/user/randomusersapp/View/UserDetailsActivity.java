package com.example.user.randomusersapp.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.randomusersapp.Model.Constants;
import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsActivity extends AppCompatActivity {
    CircleImageView cv_user_big_image;
    TextView tv_user_pib;
    TextView tv_user_age;
    AppCompatEditText apc_user_phone;
    AppCompatEditText apc_user_mail;
    AppCompatEditText apc_user_birthday;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        init_view();
        UserItem userItem = getIntent().getParcelableExtra(Constants.user_key);
        if (userItem != null) {
            Glide.with(this).load(userItem.picture.large).apply(Constants.options).into(cv_user_big_image);
            tv_user_pib.setText(userItem.name.first + " " + userItem.name.last);
            tv_user_age.setText(userItem.dob.getAge() + " years old");
            apc_user_mail.setText(userItem.email);
            //"date":"1985-08-24T14:47:08Z"
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format1.parse(userItem.dob.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(format2.format(date));

            apc_user_birthday.setText(format2.format(date));
            apc_user_phone.setText(userItem.phone);
        }
    }

    private void init_view() {
        cv_user_big_image = findViewById(R.id.cv_user_big_image);
        tv_user_pib = findViewById(R.id.tv_user_pib);
        tv_user_age = findViewById(R.id.tv_user_age);
        apc_user_phone = findViewById(R.id.apc_user_phone);
        apc_user_mail = findViewById(R.id.apc_user_mail);
        apc_user_birthday = findViewById(R.id.apc_user_birthday);
    }
}
