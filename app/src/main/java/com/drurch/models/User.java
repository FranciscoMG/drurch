package com.drurch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinicio on 14/5/2017.
 */

public class User implements Parcelable {

    private String email;
    private String name;
    private String password;
    private String img;

    protected User(Parcel in) {
        email = in.readString();
        name = in.readString();
        password = in.readString();
        img = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(img);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {
    }

    public User(String email, String name, String password, String img) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }
}
