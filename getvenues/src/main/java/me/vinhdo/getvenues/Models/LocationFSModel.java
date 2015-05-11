package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LocationFSModel
{
    @SerializedName("address")
    private String address;
    @SerializedName("formattedAddress")
    private List<String> formattedAddress;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    public String getAddress()
    {
        return this.address;
    }

    public List<String> getFormattedAddress()
    {
        return this.formattedAddress;
    }

    public double getLat()
    {
        return this.lat;
    }

    public double getLng()
    {
        return this.lng;
    }

    public void setAddress(String paramString)
    {
        this.address = paramString;
    }

    public void setFormattedAddress(List<String> paramList)
    {
        this.formattedAddress = paramList;
    }

    public void setLat(double paramDouble)
    {
        this.lat = paramDouble;
    }

    public void setLng(double paramDouble)
    {
        this.lng = paramDouble;
    }
}

