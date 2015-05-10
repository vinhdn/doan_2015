package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;

import bk.vinhdo.taxiads.models.BaseModel;

/**
 * Created by vinhdo on 4/30/15.
 */
public class ImageModel extends BaseModel{
    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("post_id")
    private String postId;

    @SerializedName("url")
    private String url;

    @SerializedName("date_create")
    private String dateCreate;

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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getSate() {
        return sate;
    }

    public void setSate(String sate) {
        this.sate = sate;
    }

    @SerializedName("sate")
    private String sate;
}
