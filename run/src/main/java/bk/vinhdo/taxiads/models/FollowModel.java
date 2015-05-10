package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;

import bk.vinhdo.taxiads.models.BaseModel;

/**
 * Created by vinhdo on 4/30/15.
 */
public class FollowModel extends BaseModel{
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_follow_id")
    private String userFollowId;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUserFollowId() {
        return userFollowId;
    }

    public void setUserFollowId(String userFollowId) {
        this.userFollowId = userFollowId;
    }
}
