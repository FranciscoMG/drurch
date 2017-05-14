package com.drurch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinicio on 14/5/2017.
 */

public class Comment implements Parcelable {

    private String description;
    private int created;
    private int user_id;

    protected Comment(Parcel in) {
        description = in.readString();
        created = in.readInt();
        user_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(created);
        dest.writeInt(user_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public Comment() {
    }

    public Comment(String description, int created, int user_id) {
        this.description = description;
        this.created = created;
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static Creator<Comment> getCREATOR() {
        return CREATOR;
    }
}
