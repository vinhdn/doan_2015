package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinhdo on 4/30/15.
 */
public class RateModel extends BaseModel{
    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("rate")
    private int rate;

    @SerializedName("address_id")
    private String addressId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("date_create")
    private long dateCreate;

    @SerializedName("date_update")
    private long dateUpdate;

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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
