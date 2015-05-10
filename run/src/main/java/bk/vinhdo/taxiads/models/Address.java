package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bk.vinhdo.taxiads.utils.Keys;

/**
 * Created by Vinh on 1/20/15.
 */
public class Address {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("name")
    private String name;

    private ArrayList<Photo> listPhoto;
    private Photo bestPhoto;

    private ArrayList<Tip> listTips;

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Tip> getListTips() {
        return listTips;
    }

    public void setListTips(ArrayList<Tip> listTips) {
        this.listTips = listTips;
    }

    public Photo getBestPhoto() {
        return bestPhoto;
    }

    public void setBestPhoto(Photo bestPhoto) {
        this.bestPhoto = bestPhoto;
    }

    public ArrayList<Photo> getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(ArrayList<Photo> listPhoto) {
        this.listPhoto = listPhoto;
    }

    private Location location;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    private Contact contact;

    private Category category;

    private int likes;
    private long createAt;

    public float getRate() {
        return rate;
    }

    public String getIDMarker() {
        return IDMarker;
    }

    public void setIDMarker(String IDMarker) {
        this.IDMarker = IDMarker;
    }

    public String IDMarker;

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    private JSONObject json;
    private double lat;
    private double lng;

    public Address(String title, double lat, double lng, float rate, TYPE_ADDRESS type) {
        this.title = title;
        this.lat = lat;
        this.lng = lng;
        this.rate = rate;
        this.type = type;
    }

    public Address() {
        this.id = "";
        this.title = "";
        this.lat = 0;
        this.lng = 0;
        this.rate = 0f;
        this.type = TYPE_ADDRESS.NONE;
        this.json = new JSONObject();
    }

    public Address(JSONObject jo) throws JSONException {
        id = jo.getString(Keys.id);
        title = jo.getString(Keys.name);
        createAt = jo.getLong(Keys.createAt);
        if (!jo.isNull(Keys.location))
            location = new Location(jo.getJSONObject(Keys.location));
        if (!jo.isNull("rating")) {
            rate = ((float) jo.getDouble("rating"));
        }
        if (!jo.isNull(Keys.likes))
            likes = jo.getJSONObject(Keys.likes).getInt(Keys.count);
        listPhoto = new ArrayList<>();
        if (!jo.isNull(Keys.photo)) {
            JSONObject joPhoto = jo.getJSONObject(Keys.photo);
            if (!joPhoto.isNull(Keys.group)) {
                JSONArray jaGroups = joPhoto.getJSONArray(Keys.group);
                if (jaGroups.length() > 0) {
                    if (!jaGroups.getJSONObject(0).isNull(Keys.items)) {
                        JSONArray jaPhotos = jaGroups.getJSONObject(0).getJSONArray(Keys.items);
                        for (int i = 0; i < jaPhotos.length(); i++) {
                            Photo pt = new Photo(jaPhotos.getJSONObject(i));
                            listPhoto.add(pt);
                        }
                    }
                }
            }

        }

        listTips = new ArrayList<>();
        if (!jo.isNull(Keys.tip)) {
            if(!jo.getJSONObject(Keys.tip).isNull(Keys.count) && jo.getJSONObject(Keys.tip).getInt(Keys.count) > 0) {
                JSONArray jaPhotos = jo.getJSONObject(Keys.tip).getJSONArray(Keys.group).getJSONObject(0).getJSONArray(Keys.items);
                for (int i = 0; i < jaPhotos.length(); i++) {
                    Tip pt = new Tip(jaPhotos.getJSONObject(i));
                    listTips.add(pt);
                }
            }
        }
        if (!jo.isNull(Keys.bestPhoto))
            bestPhoto = new Photo(jo.getJSONObject(Keys.bestPhoto));

        if (!jo.isNull(Keys.contact))
            contact = new Contact(jo.getJSONObject(Keys.contact));
    }

    private float rate;

    public TYPE_ADDRESS getType() {
        return type;
    }

    public void setType(TYPE_ADDRESS type) {
        this.type = type;
    }

    private TYPE_ADDRESS type;

    public enum TYPE_ADDRESS {
        NONE(0),
        CAFE(1);

        private int value;

        TYPE_ADDRESS(int value) {
            this.value = value;
        }

        public String getName() {
            return "CAFE";
        }

        public int getValue() {
            return this.value;
        }
    }
}
