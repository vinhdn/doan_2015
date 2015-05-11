package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;

public class UserFSModel
{
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("gender")
    private String gender;
    @SerializedName("homeCity")
    private String homeCity;
    @SerializedName("id")
    private String id;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("photo")
    private PhotoFSModel photo;

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getGender()
    {
        return this.gender;
    }

    public String getHomeCity()
    {
        return this.homeCity;
    }

    public String getId()
    {
        return this.id;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public PhotoFSModel getPhoto()
    {
        return this.photo;
    }

    public void setFirstName(String paramString)
    {
        this.firstName = paramString;
    }

    public void setGender(String paramString)
    {
        this.gender = paramString;
    }

    public void setHomeCity(String paramString)
    {
        this.homeCity = paramString;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setLastName(String paramString)
    {
        this.lastName = paramString;
    }

    public void setPhoto(PhotoFSModel paramPhotoFSModel)
    {
        this.photo = paramPhotoFSModel;
    }
}

