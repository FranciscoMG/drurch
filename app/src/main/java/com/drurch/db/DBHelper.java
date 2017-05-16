package com.drurch.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.drurch.models.Comment;
import com.drurch.models.Node;
import com.drurch.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Vinicio on 14/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // DB name
    public static final String DATABASE_NAME = "Drurch.db";

    // Table users
    public static final String USERS_TABLE_NAME = "users";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_IMG = "img";

    // Table nodes
    public static final String NODES_TABLE_NAME = "nodes";
    public static final String NODE_COLUMN_ID = "id";
    public static final String NODE_COLUMN_TYPE = "type";
    public static final String NODE_COLUMN_TITLE = "title";
    public static final String NODE_COLUMN_DESCRIPTION = "description";
    public static final String NODE_COLUMN_LATITUDE = "latitude";
    public static final String NODE_COLUMN_LONGITUDE = "longitude";
    public static final String NODE_COLUMN_SCORE = "score";
    public static final String NODE_COLUMN_USER = "user_id";

    // Table comments
    public static final String COMMENTS_TABLE_NAME = "comments";
    public static final String COMMENT_COLUMN_ID = "id";
    public static final String COMMENT_COLUMN_DESCRIPTION = "description";
    public static final String COMMENT_COLUMN_CREATED = "created";
    public static final String COMMENT_COLUMN_USER = "user_id";
    public static final String COMMENT_COLUMN_NODE = "node_id";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, name TEXT, password TEXT, img TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS nodes(id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, title TEXT, description TEXT, latitude DOUBLE, longitude DOUBLE, score INTEGER, user_id INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS comments(id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, created INTEGER, user_id INTEGER, node_id INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS nodes");
        db.execSQL("DROP TABLE IF EXISTS comments");
        onCreate(db);
    }

    public void truncate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM users");
        db.execSQL("DELETE FROM nodes");
        db.execSQL("DELETE FROM comments");
    }

    // Users ---------------------------------------------------------------------------------------
    public boolean insertUser(String email, String name, String password, String img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("img", img);
        return db.insert("users", null, contentValues) > 0;
    }
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM users WHERE id=" + id + "", null);
        if (response.moveToFirst()){
            User user = new User();
            user.setId(response.getInt(response.getColumnIndex(USER_COLUMN_ID)));
            user.setEmail(response.getString(response.getColumnIndex(USER_COLUMN_EMAIL)));
            user.setName(response.getString(response.getColumnIndex(USER_COLUMN_NAME)));
            user.setPassword(response.getString(response.getColumnIndex(USER_COLUMN_PASSWORD)));
            user.setImg(response.getString(response.getColumnIndex(USER_COLUMN_IMG)));
            return user;
        } else {
            return null;
        }
    }
    public boolean checkUserCredentialsBoolean(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM users WHERE email= '"+ email +"' AND password= '"+ password +"'", null);
        if (response.moveToFirst()){
            return true;
        }
        return false;
    }
    public User checkUserCredentialsUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM users WHERE email= '"+ email +"' AND password= '"+ password +"'", null);
        if (response.moveToFirst()){
            User user = new User();
            user.setId(response.getInt(response.getColumnIndex(USER_COLUMN_ID)));
            user.setEmail(response.getString(response.getColumnIndex(USER_COLUMN_EMAIL)));
            user.setName(response.getString(response.getColumnIndex(USER_COLUMN_NAME)));
            user.setPassword(response.getString(response.getColumnIndex(USER_COLUMN_PASSWORD)));
            user.setImg(response.getString(response.getColumnIndex(USER_COLUMN_IMG)));
            return user;
        }
        return null;
    }
    public boolean updateUser(Integer id, String email, String name, String password, String img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("img", img);
        return db.update("users", contentValues, "id = ?", new String[]{Integer.toString(id)}) > 0;
    }
    public boolean deleteUser(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users", "id = ?", new String[]{Integer.toString(id)}) > 0;
    }
    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM users", null);
        response.moveToFirst();

        while (response.isAfterLast() == false) {
            User user = new User();
            user.setId(response.getInt(response.getColumnIndex(USER_COLUMN_ID)));
            user.setEmail(response.getString(response.getColumnIndex(USER_COLUMN_EMAIL)));
            user.setName(response.getString(response.getColumnIndex(USER_COLUMN_NAME)));
            user.setPassword(response.getString(response.getColumnIndex(USER_COLUMN_PASSWORD)));
            user.setImg(response.getString(response.getColumnIndex(USER_COLUMN_IMG)));
            list.add(user);
            response.moveToNext();
        }
        return list;
    }
    public int getUsersNumberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int usersRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return usersRows;
    }


    // Nodes ---------------------------------------------------------------------------------------
    public boolean insertNode(int type, String title, String description, double latitude, double longitude, int score, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", type);
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("score", score);
        contentValues.put("user_id", user_id);
        return db.insert("nodes", null, contentValues) > 0;
    }
    public Node getNode(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM nodes WHERE id=" + id + "", null);
        if (response.moveToFirst()) {
            Node node = new Node();
            node.setId(response.getInt(response.getColumnIndex(NODE_COLUMN_ID)));
            node.setTitle(response.getString(response.getColumnIndex(NODE_COLUMN_TITLE)));
            node.setType(response.getInt(response.getColumnIndex(NODE_COLUMN_TYPE)));
            node.setDescription(response.getString(response.getColumnIndex(NODE_COLUMN_DESCRIPTION)));
            node.setLatitude(response.getDouble(response.getColumnIndex(NODE_COLUMN_LATITUDE)));
            node.setLongitude(response.getDouble(response.getColumnIndex(NODE_COLUMN_LONGITUDE)));
            node.setScore(response.getInt(response.getColumnIndex(NODE_COLUMN_SCORE)));
            node.setUser_id(response.getInt(response.getColumnIndex(NODE_COLUMN_USER)));
            return node;
        }
        return null;
    }
    public boolean updateNode(Integer id, int type, String title, String description, double latitude, double longitude, int score, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", type);
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("score", score);
        contentValues.put("user_id", user_id);
        return db.update("nodes", contentValues, "id = ?", new String[]{Integer.toString(id)}) > 0;
    }
    public boolean deleteNode(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("nodes", "id = ?", new String[]{Integer.toString(id)}) > 0;
    }
    public ArrayList<Node> getAllNodes() {
        ArrayList<Node> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM nodes", null);
        response.moveToFirst();

        while (response.isAfterLast() == false) {
            Node node = new Node();
            node.setId(response.getInt(response.getColumnIndex(NODE_COLUMN_ID)));
            node.setTitle(response.getString(response.getColumnIndex(NODE_COLUMN_TITLE)));
            node.setType(response.getInt(response.getColumnIndex(NODE_COLUMN_TYPE)));
            node.setDescription(response.getString(response.getColumnIndex(NODE_COLUMN_DESCRIPTION)));
            node.setLatitude(response.getDouble(response.getColumnIndex(NODE_COLUMN_LATITUDE)));
            node.setLongitude(response.getDouble(response.getColumnIndex(NODE_COLUMN_LONGITUDE)));
            node.setScore(response.getInt(response.getColumnIndex(NODE_COLUMN_SCORE)));
            node.setUser_id(response.getInt(response.getColumnIndex(NODE_COLUMN_USER)));
            list.add(node);
            response.moveToNext();
        }
        return list;
    }
    public int getNodesNumberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int usersRows = (int) DatabaseUtils.queryNumEntries(db, NODES_TABLE_NAME);
        return usersRows;
    }
    public ArrayList<Node> getNearestNodes(int type, double latitude, double longitude, float distanceInMeters) {
        ArrayList<Node> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM nodes WHERE type=" + type + "", null);
        response.moveToFirst();

        while (response.isAfterLast() == false) {
            float distance = distantFrom(
                    latitude,
                    longitude,
                    response.getDouble(response.getColumnIndex(NODE_COLUMN_LATITUDE)),
                    response.getDouble(response.getColumnIndex(NODE_COLUMN_LONGITUDE)));
            if (distance < distanceInMeters) {
                Node node = new Node();
                node.setId(response.getInt(response.getColumnIndex(NODE_COLUMN_ID)));
                node.setTitle(response.getString(response.getColumnIndex(NODE_COLUMN_TITLE)));
                node.setType(response.getInt(response.getColumnIndex(NODE_COLUMN_TYPE)));
                node.setDescription(response.getString(response.getColumnIndex(NODE_COLUMN_DESCRIPTION)));
                node.setLatitude(response.getDouble(response.getColumnIndex(NODE_COLUMN_LATITUDE)));
                node.setLongitude(response.getDouble(response.getColumnIndex(NODE_COLUMN_LONGITUDE)));
                node.setScore(response.getInt(response.getColumnIndex(NODE_COLUMN_SCORE)));
                node.setUser_id(response.getInt(response.getColumnIndex(NODE_COLUMN_USER)));
                node.setDistance(distance);
                list.add(node);
            }
            response.moveToNext();
        }
        Collections.sort(list, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return Float.compare(node1.getDistance(), node2.getDistance());
            }
        });
        return list;
    }
    private float distantFrom (double lat1, double lng1, double lat2, double lng2 )
    {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return new Float(dist * meterConversion).floatValue();
    }



    // Comments ------------------------------------------------------------------------------------
    public boolean insertComment(String description, int created, int user_id, int node_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", description);
        contentValues.put("created", created);
        contentValues.put("user_id", user_id);
        contentValues.put("node_id", node_id);
        return db.insert("comments", null, contentValues) > 0;
    }
    public Comment getComment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM comments WHERE id=" + id + "", null);
        if(response.moveToFirst()) {
            Comment comment = new Comment();
            comment.setId(response.getInt(response.getColumnIndex(COMMENT_COLUMN_ID)));
            comment.setDescription(response.getString(response.getColumnIndex(COMMENT_COLUMN_DESCRIPTION)));
            comment.setCreated(response.getInt(response.getColumnIndex(COMMENT_COLUMN_CREATED)));
            comment.setUser_id(response.getInt(response.getColumnIndex(COMMENT_COLUMN_USER)));
            comment.setNode_id(response.getInt(response.getColumnIndex(COMMENT_COLUMN_NODE)));
            if(comment.getUser_id() < 0){
                comment.setImg("");
            } else {
                comment.setImg(getUser(comment.getUser_id()).getImg());
            }
            return comment;
        }
        return null;
    }
    public boolean updateComment(Integer id, String description, int created, int user_id, int node_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", description);
        contentValues.put("created", created);
        contentValues.put("user_id", user_id);
        contentValues.put("node_id", node_id);
        return db.update("comments", contentValues, "id = ?", new String[]{Integer.toString(id)}) > 0;
    }
    public boolean deleteComment(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("comments", "id = ?", new String[]{Integer.toString(id)}) > 0;
    }
    public ArrayList<Comment> getAllComments() {
        ArrayList<Comment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM comments", null);
        response.moveToFirst();

        while (response.isAfterLast() == false) {
            Comment comment = new Comment();
            comment.setId(response.getInt(response.getColumnIndex(COMMENT_COLUMN_ID)));
            comment.setDescription(response.getString(response.getColumnIndex(COMMENT_COLUMN_DESCRIPTION)));
            comment.setCreated(response.getInt(response.getColumnIndex(COMMENT_COLUMN_CREATED)));
            comment.setUser_id(response.getInt(response.getColumnIndex(COMMENT_COLUMN_USER)));
            comment.setNode_id(response.getInt(response.getColumnIndex(COMMENT_COLUMN_NODE)));
            if(comment.getUser_id() < 0){
                comment.setImg("");
            } else {
                comment.setImg(getUser(comment.getUser_id()).getImg());
            }
            list.add(comment);
            response.moveToNext();
        }
        return list;
    }
    public ArrayList<Comment> getCommentsByNode(int node_id) {
        ArrayList<Comment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery("SELECT * FROM comments WHERE node_id = " + node_id + " ORDER BY created", null);
        response.moveToFirst();

        while (response.isAfterLast() == false) {
            Comment comment = new Comment();
            comment.setId(response.getInt(response.getColumnIndex(COMMENT_COLUMN_ID)));
            comment.setDescription(response.getString(response.getColumnIndex(COMMENT_COLUMN_DESCRIPTION)));
            comment.setCreated(response.getInt(response.getColumnIndex(COMMENT_COLUMN_CREATED)));
            comment.setUser_id(response.getInt(response.getColumnIndex(COMMENT_COLUMN_USER)));
            comment.setNode_id(response.getInt(response.getColumnIndex(COMMENT_COLUMN_NODE)));
            if(comment.getUser_id() < 0){
                comment.setImg("");
                comment.setUser_name("");
            } else {
                comment.setImg(getUser(comment.getUser_id()).getImg());
                comment.setUser_name(getUser(comment.getUser_id()).getName());
            }
            list.add(comment);
            response.moveToNext();
        }
        return list;
    }
    public int getCommentsNumberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int usersRows = (int) DatabaseUtils.queryNumEntries(db, COMMENTS_TABLE_NAME);
        return usersRows;
    }
}
