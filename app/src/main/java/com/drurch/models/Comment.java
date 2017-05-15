package com.drurch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinicio on 14/5/2017.
 */

public class Comment implements Parcelable {

    private int id;
    private String description;
    private int created;
    private int node_id;
    private int user_id;


    protected Comment(Parcel in) {
        id = in.readInt();
        description = in.readString();
        created = in.readInt();
        node_id = in.readInt();
        user_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeInt(created);
        dest.writeInt(node_id);
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

    public Comment(int id, String description, int created, int node_id, int user_id) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.node_id = node_id;
        this.user_id = user_id;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
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

    @Override
    public String toString() {
        return description;
    }
}
