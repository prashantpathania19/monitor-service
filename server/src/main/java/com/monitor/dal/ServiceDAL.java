package com.monitor.dal;

import com.monitor.db.ServiceBean;
import com.monitor.db.ServiceSQLs;
import com.monitor.util.JdbiConnectionHelper;
import com.monitor.vo.ServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * class representing data access
 * layer for service
 * @author prashant
 */
@Repository
public class ServiceDAL {
    private static final Logger log = LoggerFactory.getLogger(ServiceDAL.class);
    private final JdbiConnectionHelper jdbiConnectionHelper;

    @Autowired
    public ServiceDAL(JdbiConnectionHelper jdbiConnectionHelper) {
        this.jdbiConnectionHelper = jdbiConnectionHelper;
    }

    public void saveService(ServiceRequest serviceRequest){
        try {
            jdbiConnectionHelper.attach(ServiceSQLs.class).saveNewService(serviceRequest.getServiceName(), serviceRequest.getServiceUrl(), "PENDING");
        } catch (Exception ex) {
            log.error("error while executing saveService method", ex);
        }
    }

    public void updateServiceUrl(ServiceRequest serviceRequest) {
        try {
            jdbiConnectionHelper.attach(ServiceSQLs.class).updateServiceUrl(serviceRequest.getServiceName(), serviceRequest.getServiceUrl());
        } catch (Exception ex) {
            log.error("error while executing updateServiceUrl method", ex);
        }
    }

    public void updateServiceStatus(String serviceName, String status) {
        try {
            jdbiConnectionHelper.attach(ServiceSQLs.class).updateServiceStatus(serviceName, status);
        } catch (Exception ex) {
            log.error("error while executing updateServiceStatus method", ex);
        }
    }

    public void deleteService(String serviceName) {
        try {
            jdbiConnectionHelper.attach(ServiceSQLs.class).delete(serviceName);
        } catch (Exception ex) {
            log.error("error while executing deleteService method", ex);
        }
    }

    public Optional<ServiceBean> getService(String serviceName) {
        try {
            return jdbiConnectionHelper.attach(ServiceSQLs.class).getServiceByName(serviceName);
        } catch (Exception ex) {
            log.error("error while executing getService method", ex);
        }
        return Optional.empty();
    }

    public List<ServiceBean> getServices() {
        try {
            return jdbiConnectionHelper.attach(ServiceSQLs.class).getServices();
        } catch (Exception ex) {
            log.error("error while executing getServices method", ex);
        }
        return Collections.emptyList();
    }
}
