package com.drurch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinicio on 14/5/2017.
 */

public class Node implements Parcelable {

    private int type;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private int score;
    private int user_id;
    private float distance;


    protected Node(Parcel in) {
        type = in.readInt();
        title = in.readString();
        description = in.readString();
        score = in.readInt();
        user_id = in.readInt();
        distance = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(score);
        dest.writeInt(user_id);
        dest.writeFloat(distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel in) {
            return new Node(in);
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };

    public Node(int type, String title, String description, Double latitude, Double longitude, int score, int user_id, float distance) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
        this.user_id = user_id;
        this.distance = distance;
    }

    public Node() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public static Creator<Node> getCREATOR() {
        return CREATOR;
    }
}
