package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import bk.vinhdo.taxiads.models.BaseModel;

/**
 * Created by vinhdo on 4/30/15.
 */
@DatabaseTable(tableName = "User")
public class UserModel extends BaseModel{
    @SerializedName("id")
    @DatabaseField
    private String id;

    @SerializedName("facebook_id")
    @DatabaseField
    private String facebookId;

    @SerializedName("password_id")
    @DatabaseField
    private String password;

    @SerializedName("first_name")
    @DatabaseField
    private String fisrtName;

    @SerializedName("last_name")
    @DatabaseField
    private String lastName;

    @SerializedName("lat")
    @DatabaseField
    private double lat;

    @SerializedName("lng")
    @DatabaseField
    private double lng;

    @SerializedName("birthday")
    @DatabaseField
    private long birthDay;

    @SerializedName("gender")
    @DatabaseField
    private String gender;

    @SerializedName("avatar")
    @DatabaseField
    private String avatar;

    @SerializedName("state")
    @DatabaseField
    private int state;

    @SerializedName("email")
    @DatabaseField
    private String email;

    @SerializedName("type")
    @DatabaseField
    private int type;

    @SerializedName("access_token")
    @DatabaseField
    private String accessToken;

    @SerializedName("address")
    @DatabaseField
    private String address;

    @SerializedName("date_create")
    @DatabaseField
    private long dateCreate;

    @SerializedName("date_update")
    @DatabaseField
    private long dateUpdate;

    @SerializedName("last_login")
    @DatabaseField
    private long lastLogin;

    @SerializedName("avatar_url")
    @DatabaseField
    private String avatarUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public static UserModel getCurrentUser() {
        try {
            return databaseHelper.getUserDao().queryForAll().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
