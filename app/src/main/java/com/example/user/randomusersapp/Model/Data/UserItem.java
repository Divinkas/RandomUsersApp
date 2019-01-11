package com.example.user.randomusersapp.Model.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserItem implements Parcelable {
    @SerializedName("name")
    @Expose
    public Name name;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("dob")
    @Expose
    public Dob dob;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("picture")
    @Expose
    public Picture picture;

    protected UserItem(Parcel in) {
        email = in.readString();
        phone = in.readString();
        dob = in.readParcelable(Dob.class.getClassLoader());
        name = in.readParcelable(Name.class.getClassLoader());
        picture = in.readParcelable(Picture.class.getClassLoader());
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override
        public UserItem createFromParcel(Parcel in) {
            return new UserItem(in);
        }

        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeParcelable(dob, flags);
        dest.writeParcelable(name, flags);
        dest.writeParcelable(picture, flags);
    }
}
