package bk.vinhdo.taxiads.models;

/**
 * Created by khanhnguyen on 4/15/15.
 */
public class ResponseModel {

    private int success;

    private String message;

    private String data;

    private String errorDetails;

    private String rideErrorCode;


    public ResponseModel() {
    }

    public ResponseModel(int success, String message) {
        this.success = success;
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success == 1;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getRideErrorCode() {
        return rideErrorCode;
    }

    public void setRideErrorCode(String rideErrorCode) {
        this.rideErrorCode = rideErrorCode;
    }
}
