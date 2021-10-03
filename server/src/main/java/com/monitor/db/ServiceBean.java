package com.monitor.db;

/**
 * class representing
 * service bean
 * @author prashant
 */
public class ServiceBean {
    private Long id;
    private String serviceName;
    private String serviceUrl;
    private String created;
    private String updated;
    private String status;

    public ServiceBean(Builder builder) {
        this.id = builder.id;
        this.serviceName = builder.serviceName;
        this.serviceUrl = builder.serviceUrl;
        this.created = builder.created;
        this.updated = builder.updated;
        this.status = builder.status;
    }

    public Long getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getStatus() {
        return status;
    }

    public static class Builder {
        private Long id;
        private String serviceName;
        private String serviceUrl;
        private String created;
        private String updated;
        private String status;

        public Builder() {}

        public ServiceBean.Builder id(long id) {
            this.id = id;
            return this;
        }

        public ServiceBean.Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public ServiceBean.Builder serviceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
            return this;
        }

        public ServiceBean.Builder created(String created) {
            this.created = created;
            return this;
        }

        public ServiceBean.Builder updated(String updated) {
            this.updated = updated;
            return this;
        }

        public ServiceBean.Builder status(String status) {
            this.status = status;
            return this;
        }

        public ServiceBean build() {
            return new ServiceBean(this);
        }
    }
}
