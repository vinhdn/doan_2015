package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
import com.google.gson.annotations.SerializedName;

public class ResponseModel
{
    @SerializedName("data")
    private String data;
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private int success;

    public ResponseModel() {}

    public ResponseModel(int paramInt, String paramString)
    {
        this.success = paramInt;
        this.message = paramString;
    }

    public String getData()
    {
        return this.data;
    }

    public String getMessage()
    {
        return this.message;
    }

    public int getSuccess()
    {
        return this.success;
    }

    public boolean isSuccess()
    {
        if(success == 1)
            return true;
        return false;
    }

    public void setData(String paramString)
    {
        this.data = paramString;
    }

    public void setMessage(String paramString)
    {
        this.message = paramString;
    }

    public void setSuccess(int paramInt)
    {
        this.success = paramInt;
    }
}

