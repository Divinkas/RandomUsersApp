package com.example.user.randomusersapp.Model.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Name implements Parcelable {
    public String title;
    public String first;
    public String last;

    protected Name(Parcel in) {
        title = in.readString();
        first = in.readString();
        last = in.readString();
    }

    public static final Creator<Name> CREATOR = new Creator<Name>() {
        @Override
        public Name createFromParcel(Parcel in) {
            return new Name(in);
        }

        @Override
        public Name[] newArray(int size) {
            return new Name[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(first);
        dest.writeString(last);
    }
}
