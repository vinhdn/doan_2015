package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import bk.vinhdo.taxiads.models.BaseModel;

/**
 * Created by vinhdo on 4/30/15.
 */
@DatabaseTable(tableName = "Comment")
public class CommentModel extends BaseModel{
    @SerializedName("id")
    @DatabaseField
    private String id;

    @SerializedName("comment")
    @DatabaseField
    private String comment;

    @SerializedName("post_id")
    @DatabaseField
    private String postId;

    @SerializedName("owner_id")
    @DatabaseField
    private String ownerId;

    @SerializedName("type")
    @DatabaseField
    private int type;

    @SerializedName("sate")
    @DatabaseField
    private int state;

    @SerializedName("date_create")
    @DatabaseField
    private long dateCreate;

    public long getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(long dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("date_update")
    @DatabaseField
    private long dateUpdate;
}
