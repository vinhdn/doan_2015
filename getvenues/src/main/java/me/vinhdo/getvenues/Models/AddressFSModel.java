package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;
import java.util.Iterator;
import java.util.List;

public class AddressFSModel
{
    @SerializedName("bestPhoto")
    private PhotoFSModel bestPhoto;
    @SerializedName("contact")
    private ContactFSModel contact;
    @SerializedName("createAt")
    private long createAt;
    @SerializedName("id")
    private String id;
    @SerializedName("categories")
    private List<CategoryFSModel> listCategories;
    private LikesFSModel listLikes;
    private List<TimeOpenFSModel> listTimeOpens;
    private List<TipFSModel> listTips;
    @SerializedName("location")
    private LocationFSModel location;
    @SerializedName("name")
    private String name;
    @SerializedName("rating")
    private float rating;
    @SerializedName("ratingSignals")
    private int ratingSignals;
    @SerializedName("url")
    private String url;

    public PhotoFSModel getBestPhoto()
    {
        return this.bestPhoto;
    }

    public ContactFSModel getContact()
    {
        return this.contact;
    }

    public long getCreateAt()
    {
        return this.createAt;
    }

    public String getId()
    {
        return this.id;
    }

    public List<CategoryFSModel> getListCategories()
    {
        return this.listCategories;
    }

    public LikesFSModel getListLikes()
    {
        return this.listLikes;
    }

    public List<TimeOpenFSModel> getListTimeOpens()
    {
        return this.listTimeOpens;
    }

    public List<TipFSModel> getListTips()
    {
        return this.listTips;
    }

    public LocationFSModel getLocation()
    {
        return this.location;
    }

    public String getName()
    {
        return this.name;
    }

    public float getRating()
    {
        return this.rating;
    }

    public int getRatingSignals()
    {
        return this.ratingSignals;
    }

    public String getTiemOpen()
    {
        String str = "";
        if (this.listTimeOpens.size() > 0)
        {
            Iterator localIterator = this.listTimeOpens.iterator();
            while (localIterator.hasNext())
            {
                TimeOpenFSModel localTimeOpenFSModel = (TimeOpenFSModel)localIterator.next();
                str = str + localTimeOpenFSModel.getTime() + ";";
            }
        }
        if (str.charAt(-1 + str.length()) == ',') {
            str = str.substring(0, -1 + str.length());
        }
        return str;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setBestPhoto(PhotoFSModel paramPhotoFSModel)
    {
        this.bestPhoto = paramPhotoFSModel;
    }

    public void setContact(ContactFSModel paramContactFSModel)
    {
        this.contact = paramContactFSModel;
    }

    public void setCreateAt(long paramLong)
    {
        this.createAt = paramLong;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setListCategories(List<CategoryFSModel> paramList)
    {
        this.listCategories = paramList;
    }

    public void setListLikes(LikesFSModel paramLikesFSModel)
    {
        this.listLikes = paramLikesFSModel;
    }

    public void setListTimeOpens(List<TimeOpenFSModel> paramList)
    {
        this.listTimeOpens = paramList;
    }

    public void setListTips(List<TipFSModel> paramList)
    {
        this.listTips = paramList;
    }

    public void setLocation(LocationFSModel paramLocationFSModel)
    {
        this.location = paramLocationFSModel;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setRating(float paramFloat)
    {
        this.rating = paramFloat;
    }

    public void setRatingSignals(int paramInt)
    {
        this.ratingSignals = paramInt;
    }

    public void setUrl(String paramString)
    {
        this.url = paramString;
    }
}
