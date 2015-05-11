package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;

public class CategoryFSModel
{
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("primary")
    private boolean primary;

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isPrimary()
    {
        return this.primary;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setPrimary(boolean paramBoolean)
    {
        this.primary = paramBoolean;
    }
}

