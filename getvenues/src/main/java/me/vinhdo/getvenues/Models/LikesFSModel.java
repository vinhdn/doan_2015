package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LikesFSModel
{
    @SerializedName("count")
    private int count;
    @SerializedName("items")
    private List<UserFSModel> listUsers;
    @SerializedName("summary")
    private String summary;

    public int getCount()
    {
        return this.count;
    }

    public List<UserFSModel> getListUsers()
    {
        return this.listUsers;
    }

    public String getSummary()
    {
        return this.summary;
    }

    public void setCount(int paramInt)
    {
        this.count = paramInt;
    }

    public void setListUsers(List<UserFSModel> paramList)
    {
        this.listUsers = paramList;
    }

    public void setSummary(String paramString)
    {
        this.summary = paramString;
    }
}

