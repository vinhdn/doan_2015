package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import bk.vinhdo.taxiads.models.BaseModel;

/**
 * Created by vinhdo on 4/30/15.
 */
@DatabaseTable(tableName = "Message")
public class MessageModel extends BaseModel{
    @SerializedName("id")
    @DatabaseField(id = true)
    private String id;

    @SerializedName("user_id")
    @DatabaseField
    private String userId;

    @SerializedName("message")
    @DatabaseField
    private String message;

    @SerializedName("address_id")
    @DatabaseField
    private String addressId;

    @SerializedName("date_create")
    @DatabaseField
    private long dateCreate;

    @SerializedName("state")
    @DatabaseField
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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
}
