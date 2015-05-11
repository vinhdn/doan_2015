package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */
public class ResponseFSModel
{
    private int code;
    private String errorDetails;
    private String errorType;
    private String response;

    public ResponseFSModel() {}

    public ResponseFSModel(int paramInt, String paramString)
    {
        this.code = paramInt;
        this.errorDetails = paramString;
    }

    public int getCode()
    {
        return this.code;
    }

    public String getErrorDetails()
    {
        return this.errorDetails;
    }

    public String getErrorType()
    {
        return this.errorType;
    }

    public String getResponse()
    {
        return this.response;
    }

    public boolean isSuccess()
    {
        if (this.code == 200){
            return true;
        }
        return false;
    }

    public void setCode(int paramInt)
    {
        this.code = paramInt;
    }

    public void setErrorDetails(String paramString)
    {
        this.errorDetails = paramString;
    }

    public void setErrorType(String paramString)
    {
        this.errorType = paramString;
    }

    public void setResponse(String paramString)
    {
        this.response = paramString;
    }
}

