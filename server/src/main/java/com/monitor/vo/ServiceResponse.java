package com.monitor.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * class to represent service
 * response
 * @author prashant
 */
public class ServiceResponse implements Serializable {
    private static final long serialVersionUID = 1441288447307234959L;
    @SerializedName("status")
    private final int status;

    @SerializedName("message")
    private final String message;

    @SerializedName("success")
    private final boolean success;

    public ServiceResponse(int status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
