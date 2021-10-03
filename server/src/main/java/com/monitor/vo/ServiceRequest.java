package com.monitor.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * class representing
 * service request
 * @author prashant
 */
public class ServiceRequest implements Serializable {
    private static final long serialVersionUID = -6016647776477235191L;
    @SerializedName("serviceName")
    private String serviceName;
    @SerializedName("serviceUrl")
    private String serviceUrl;

    /**
     * constructor with parameters
     * @param serviceName - refers to service name
     * @param serviceUrl - refers to service url
     */
    public ServiceRequest(String serviceName, String serviceUrl) {
        this.serviceName = serviceName;
        this.serviceUrl = serviceUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                '}';
    }
}
