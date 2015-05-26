package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by vinhdo on 4/30/15.
 */
@DatabaseTable(tableName = "Address")
public class AddressModel extends BaseModel{
    @SerializedName("id")
    @DatabaseField(id = true)
    private String id;

    @SerializedName("owner_id")
    @DatabaseField
    private String ownerId;

    @SerializedName("name")
    @DatabaseField
    private String name;

    @SerializedName("email")
    @DatabaseField
    private String email;

    @SerializedName("category_id")
    @DatabaseField
    private String categoryId;

    @SerializedName("avatar")
    @DatabaseField
    private String avatar;

    @SerializedName("avatar_url")
    @DatabaseField
    private String avatarUrl;

    @SerializedName("about")
    @DatabaseField
    private String about;

    @SerializedName("cover")
    @DatabaseField
    private String cover;

    @SerializedName("state")
    @DatabaseField
    private int state;

    @SerializedName("lat")
    @DatabaseField
    private double lat;

    @SerializedName("lng")
    @DatabaseField
    private double lng;

    @SerializedName("address")
    @DatabaseField
    private String address;

    @SerializedName("city")
    @DatabaseField
    private String city;

    @SerializedName("suburb")
    @DatabaseField
    private String suburb;

    @SerializedName("street_number")
    @DatabaseField
    private String streetNumber;

    @SerializedName("phone_number")
    @DatabaseField
    private String phoneNumber;

    @SerializedName("date_create")
    @DatabaseField
    private long dateCreate;

    @SerializedName("posts")
    private List<PostModel> listPosts;

    @SerializedName("rate")
    @DatabaseField
    private float rate;

    @SerializedName("rate_number")
    @DatabaseField
    private int rateNumber;

    @SerializedName("category")
    private CategoryModel category;

    @SerializedName("time_open")
    private List<TimeOpenModel> listTimeOpen;

    @SerializedName("is_like")
    @DatabaseField
    private boolean isLike;

    @SerializedName("is_owner")
    @DatabaseField
    private boolean isOwner;

    @SerializedName("likes")
    private List<UserModel> listLikes;

    @SerializedName("best_photo")
    @DatabaseField
    private String bestPhoto;

    @SerializedName("id_marker")
    private String idMarker;

    @SerializedName("id_in_list")
    private int idInList = -1;

    @SerializedName("distance")
    private int distance;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public List<PostModel> getListPosts() {
        return listPosts;
    }

    public void setListPosts(List<PostModel> listPosts) {
        this.listPosts = listPosts;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getRateNumber() {
        return rateNumber;
    }

    public void setRateNumber(int rateNumber) {
        this.rateNumber = rateNumber;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public List<UserModel> getListLikes() {
        return listLikes;
    }

    public void setListLikes(List<UserModel> listLikes) {
        this.listLikes = listLikes;
    }

    public List<TimeOpenModel> getListTimeOpen() {
        if(listTimeOpen == null)
            listTimeOpen = TimeOpenModel.getByAddress(id);
        return listTimeOpen;
    }

    public void setListTimeOpen(List<TimeOpenModel> listTimeOpen) {
        this.listTimeOpen = listTimeOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdMarker() {
        return idMarker;
    }

    public void setIdMarker(String idMarker) {
        this.idMarker = idMarker;
    }

    public int getIdInList() {
        return idInList;
    }

    public void setIdInList(int idInList) {
        this.idInList = idInList;
    }

    public String getBestPhoto() {
        return bestPhoto;
    }

    public void setBestPhoto(String bestPhoto) {
        this.bestPhoto = bestPhoto;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
