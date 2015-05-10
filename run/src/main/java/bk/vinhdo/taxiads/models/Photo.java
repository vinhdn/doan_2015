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
public class Photo {
    private String id;
    private Long createAt;
    private String prefix;
    private String suffix;
    private String link;
    private int height;

    private User ower;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int width;

    public  Photo(JSONObject joPt) throws JSONException {
        if(!joPt.isNull(Keys.id)){
            id = joPt.getString(Keys.id);
        }
        if(!joPt.isNull(Keys.createAt)){
            createAt = joPt.getLong(Keys.createAt);
        }
        if(!joPt.isNull(Keys.prefix) && joPt.isNull(Keys.suffix)){
            prefix = joPt.getString(Keys.prefix);
            suffix = joPt.getString(Keys.suffix);
        }
        if(!joPt.isNull(Keys.width) && joPt.isNull(Keys.height)){
            width = joPt.getInt(Keys.width);
            height = joPt.getInt(Keys.height);
        }
        if(!joPt.isNull(Keys.user)){
            ower = new User(joPt.getJSONObject(Keys.user));
        }
    }

    public User getOwer() {
        return ower;
    }
}
