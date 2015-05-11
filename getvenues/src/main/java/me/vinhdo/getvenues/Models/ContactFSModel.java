package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;

public class ContactFSModel
{
    @SerializedName("facebook")
    private String facebookId;
    @SerializedName("phone")
    private String phone;

    public String getFacebookId()
    {
        return this.facebookId;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public void setFacebookId(String paramString)
    {
        this.facebookId = paramString;
    }

    public void setPhone(String paramString)
    {
        this.phone = paramString;
    }
}

