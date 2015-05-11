package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;

public class TipFSModel
{
    @SerializedName("createAt")
    private long createAt;
    @SerializedName("id")
    private String id;
    @SerializedName("photourl")
    private String photoUrl;
    @SerializedName("text")
    private String text;
    @SerializedName("type")
    private String type;
    @SerializedName("user")
    private UserFSModel user;

    public long getCreateAt()
    {
        return this.createAt;
    }

    public String getId()
    {
        return this.id;
    }

    public String getPhotoUrl()
    {
        return this.photoUrl;
    }

    public String getText()
    {
        return this.text;
    }

    public String getType()
    {
        return this.type;
    }

    public UserFSModel getUser()
    {
        return this.user;
    }

    public void setCreateAt(long paramLong)
    {
        this.createAt = paramLong;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setPhotoUrl(String paramString)
    {
        this.photoUrl = paramString;
    }

    public void setText(String paramString)
    {
        this.text = paramString;
    }

    public void setType(String paramString)
    {
        this.type = paramString;
    }

    public void setUser(UserFSModel paramUserFSModel)
    {
        this.user = paramUserFSModel;
    }
}