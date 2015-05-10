/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bk.vinhdo.taxiads.models;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.annotations.SerializedName;

import bk.vinhdo.taxiads.utils.Keys;

/**
 * Created by vinhdo on 2/27/15.
 */
public class Tip {
    @SerializedName("id")
    private String id;
    @SerializedName("text")
    private String content;
    @SerializedName("createAt")
    private long createAt;
    @SerializedName("likes")
    private int likes;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    private User user;

    public Tip(JSONObject joTip) throws JSONException {
        id = joTip.getString(Keys.id);
        createAt = joTip.getLong(Keys.createAt);
        content = joTip.getString(Keys.text);
        if(!joTip.isNull(Keys.user))
        user = new User(joTip.getJSONObject(Keys.user));
    }

}
