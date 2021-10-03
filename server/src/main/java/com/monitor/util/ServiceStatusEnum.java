package com.monitor.util;

public enum ServiceStatusEnum {
    STATUS_OK("OK"), STATUS_FAIL("FAIL"), STATUS_PENDING("PENDING");

    private String name;

    ServiceStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
