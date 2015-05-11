package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;

public class PhotoFSModel
{
    @SerializedName("createAt")
    private long createAt;
    @SerializedName("height")
    private int height;
    @SerializedName("prefix")
    private String prefix;
    @SerializedName("suffix")
    private String suffix;
    @SerializedName("witdh")
    private int width;

    public long getCreateAt()
    {
        return this.createAt;
    }

    public int getHeight()
    {
        return this.height;
    }

    public String getPrefix()
    {
        return this.prefix;
    }

    public String getSuffix()
    {
        return this.suffix;
    }

    public String getURL(int paramInt)
    {
        return this.prefix + paramInt + this.suffix;
    }

    public int getWidth()
    {
        return this.width;
    }

    public void setCreateAt(long paramLong)
    {
        this.createAt = paramLong;
    }

    public void setHeight(int paramInt)
    {
        this.height = paramInt;
    }

    public void setPrefix(String paramString)
    {
        this.prefix = paramString;
    }

    public void setSuffix(String paramString)
    {
        this.suffix = paramString;
    }

    public void setWidth(int paramInt)
    {
        this.width = paramInt;
    }
}