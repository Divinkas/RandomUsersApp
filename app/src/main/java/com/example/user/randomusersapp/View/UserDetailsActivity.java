package com.example.user.randomusersapp.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.randomusersapp.Model.Constants;
import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsActivity extends AppCompatActivity {
    private static final int CALL_PERMISSION = 100;
    private UserItem userItem;

    CircleImageView cv_user_big_image;
    TextView tv_user_pib;
    TextView tv_user_age;
    AppCompatEditText apc_user_phone;
    AppCompatEditText apc_user_mail;
    AppCompatEditText apc_user_birthday;
    Button btn_call;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        init_view();
        userItem = getIntent().getParcelableExtra(Constants.user_key);
        if (userItem != null) {
            Glide.with(this).load(userItem.picture.large).apply(Constants.options).into(cv_user_big_image);
            tv_user_pib.setText(userItem.name.first + " " + userItem.name.last);
            tv_user_age.setText(userItem.dob.getAge() + " years old");
            apc_user_mail.setText(userItem.email);
            apc_user_birthday.setText(get_right_date_format(userItem.dob.getDate()));
            apc_user_phone.setText(userItem.phone);
            btn_call.setOnClickListener(view -> call_by_number(userItem.phone));
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String get_right_date_format(String date_val) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format1.parse(date_val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format2.format(date);
    }

    private void call_by_number(String tell) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
                return;
            }
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tell));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSION) {
            call_by_number(userItem.phone);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void init_view() {
        cv_user_big_image = findViewById(R.id.cv_user_big_image);
        tv_user_pib = findViewById(R.id.tv_user_pib);
        tv_user_age = findViewById(R.id.tv_user_age);
        apc_user_phone = findViewById(R.id.apc_user_phone);
        apc_user_mail = findViewById(R.id.apc_user_mail);
        apc_user_birthday = findViewById(R.id.apc_user_birthday);
        btn_call = findViewById(R.id.btn_call);
    }
}
