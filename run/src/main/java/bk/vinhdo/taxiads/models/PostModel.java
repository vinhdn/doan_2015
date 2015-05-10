package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by vinhdo on 4/30/15.
 */
@DatabaseTable(tableName = "post")
public class PostModel extends BaseModel{
    @SerializedName("id")
    @DatabaseField
    private String id;

    @SerializedName("owner_id")
    @DatabaseField
    private String ownerId;

    @SerializedName("address_id")
    @DatabaseField
    private String addressId;

    @SerializedName("type")
    @DatabaseField
    private int type;

    @SerializedName("title")
    @DatabaseField
    private String title;

    @SerializedName("content")
    @DatabaseField
    private String content;

    @SerializedName("image")
    @DatabaseField
    private String image;

    @SerializedName("date_create")
    @DatabaseField
    private long dateCreate;

    @SerializedName("date_update")
    @DatabaseField
    private long dateUpdate;

    @SerializedName("state")
    @DatabaseField
    private int state;

    @SerializedName("user")
    private UserModel user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public long getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(long dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
