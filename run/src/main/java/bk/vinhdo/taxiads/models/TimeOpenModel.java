package bk.vinhdo.taxiads.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;

import bk.vinhdo.taxiads.models.BaseModel;

/**
 * Created by vinhdo on 4/30/15.
 */

@DatabaseTable(tableName = "TimeOpen")
public class TimeOpenModel extends BaseModel{
    @SerializedName("id")
    @DatabaseField(id = true)
    private String id;

    @SerializedName("address_id")
    @DatabaseField
    private String addressId;

    @SerializedName("weekday")
    @DatabaseField
    private String weekDay;

    @SerializedName("time_open")
    @DatabaseField
    private String timeOpen;

    @SerializedName("time_close")
    @DatabaseField
    private String timeClose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    public static List<TimeOpenModel> getByAddress(String addressId) {
        List<TimeOpenModel> cityModels = null;
        try {
            cityModels = databaseHelper.getTimeOpenDao().queryForEq("addressId", addressId);
            return cityModels;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
