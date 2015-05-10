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

import bk.vinhdo.taxiads.utils.Keys;

/**
 * Created by vinhdo on 2/27/15.
 */
public class User {

    private String id;
    private String first_name;
    private String last_name;
    private int gender; // 1 - male +++ other - female
    private String avatar;
    private String pre_avatar;

    public String getSuf_avatar() {
        return suf_avatar;
    }

    public void setSuf_avatar(String suf_avatar) {
        this.suf_avatar = suf_avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPre_avatar() {
        return pre_avatar;
    }

    public void setPre_avatar(String pre_avatar) {
        this.pre_avatar = pre_avatar;
    }

    private String suf_avatar;



    public User(JSONObject joU) throws JSONException {
        id = joU.getString(Keys.id);
        if(!joU.isNull(Keys.firstName))
            first_name = joU.getString(Keys.firstName);
        if(!joU.isNull(Keys.lastName))
            last_name = joU.getString(Keys.lastName);
        if(!joU.isNull(Keys.gender))
            gender = joU.getString(Keys.gender).equals(Keys.male) ? 1 : 0;
        if(!joU.isNull(Keys.prefix) && joU.isNull(Keys.suffix)){
            pre_avatar = joU.getString(Keys.prefix);
            suf_avatar = joU.getString(Keys.suffix);
        }
    }
}
