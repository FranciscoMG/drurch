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
    private String img;
    private String user_name;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public static Creator<Comment> getCREATOR() {
        return CREATOR;
    }

    public Comment() {
    }

    public Comment(int id, String description, int created, int node_id, int user_id, String img, String user_name) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.node_id = node_id;
        this.user_id = user_id;
        this.img = img;
        this.user_name = user_name;
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        description = in.readString();
        created = in.readInt();
        node_id = in.readInt();
        user_id = in.readInt();
        img = in.readString();
        user_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeInt(created);
        dest.writeInt(node_id);
        dest.writeInt(user_id);
        dest.writeString(img);
        dest.writeString(user_name);
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
}
