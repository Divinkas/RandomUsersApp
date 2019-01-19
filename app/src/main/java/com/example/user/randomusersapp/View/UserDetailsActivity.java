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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsActivity extends AppCompatActivity {
    private static final int CALL_PERMISSION = 100;
    private UserItem userItem;

    CircleImageView circleImageViewContactAvatar;

    TextView textViewContactFullNAME;
    TextView textViewContactAge;

    AppCompatEditText appCompatEditTextContactPhone;
    AppCompatEditText appCompatEditTextContactMail;
    AppCompatEditText appCompatEditTextContactBirthday;

    Button buttonCall;
    Toolbar toolbar;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        init_view();
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.user_info);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        userItem = getIntent().getParcelableExtra(Constants.USER_KEY);
        if (userItem != null) {
            Glide
                    .with(this)
                    .load(userItem.picture.large)
                    .apply(Constants.GLIDE_OPTIONS)
                    .into(circleImageViewContactAvatar);

            textViewContactFullNAME.setText(userItem.name.first + " " + userItem.name.last);
            textViewContactAge.setText(userItem.dob.getAge() + " years old");

            appCompatEditTextContactMail.setText(userItem.email);
            appCompatEditTextContactBirthday.setText(getRightDate(userItem.dob.getDate()));
            appCompatEditTextContactPhone.setText(userItem.phone);

            buttonCall.setOnClickListener(view -> callByNumber(userItem.phone));
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getRightDate(String date_val) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try { date = format1.parse(date_val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format2.format(date);
    }

    private void callByNumber(String tell) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
            } else{
                startCall(tell);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void startCall(String tell){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tell));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSION) {
            startCall(userItem.phone);
        }
    }

    private void init_view() {
        circleImageViewContactAvatar = findViewById(R.id.cv_user_big_image);
        textViewContactFullNAME = findViewById(R.id.tv_user_pib);
        textViewContactAge = findViewById(R.id.tv_user_age);
        appCompatEditTextContactPhone = findViewById(R.id.apc_user_phone);
        appCompatEditTextContactMail = findViewById(R.id.apc_user_mail);
        appCompatEditTextContactBirthday = findViewById(R.id.apc_user_birthday);
        buttonCall = findViewById(R.id.btn_call);
        toolbar = findViewById(R.id.toolbar);
    }
}
