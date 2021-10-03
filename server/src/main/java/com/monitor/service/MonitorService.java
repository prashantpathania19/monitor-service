package com.monitor.service;

import com.monitor.dal.ServiceDAL;
import com.monitor.db.ServiceBean;
import com.monitor.vo.ServiceRequest;
import com.monitor.vo.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * this class represents service
 * layer for monitor service
 * @author prashant
 */
@Component
public class MonitorService {
    private static final Logger log = LoggerFactory.getLogger(MonitorService.class);
    private ServiceDAL serviceDAL;
    private MonitorServicesLookup monitorServicesLookup;
    private MonitorServicePoller monitorServicePoller;

    @Autowired
    public MonitorService(ServiceDAL serviceDAL, MonitorServicesLookup monitorServicesLookup, MonitorServicePoller monitorServicePoller) {
        this.serviceDAL = serviceDAL;
        this.monitorServicesLookup = monitorServicesLookup;
        this.monitorServicePoller = monitorServicePoller;
    }

    @PostConstruct
    public void init() {
        monitorServicePoller.init();
    }

    /**
     * method to save
     * new service
     * @param serviceRequest - refers to serviceRequest object
     * @return ServiceResponse
     */
    @Transactional
    public ServiceResponse saveService(ServiceRequest serviceRequest) {
        log.info("serviceRequest for saveService method is {}", serviceRequest);
        boolean isValidUrl = isValidUrl(serviceRequest.getServiceUrl());
        if (!isValidUrl) {
            return new ServiceResponse(200, "Invalid Url", false);
        }
        serviceDAL.saveService(serviceRequest);
        monitorServicesLookup.addService(serviceRequest.getServiceName());
        return new ServiceResponse(200, "Service Added", true);
    }

    /**
     * method to update
     * service
     * @param serviceRequest - refers to serviceRequest object
     * @return ServiceResponse
     */
    @Transactional
    public ServiceResponse updateService(ServiceRequest serviceRequest) {
        log.info("serviceRequest for updateService method is {}", serviceRequest);
        boolean isValidUrl = isValidUrl(serviceRequest.getServiceUrl());
        if (!isValidUrl) {
            return new ServiceResponse(200, "Invalid Url", false);
        }
        Optional<ServiceBean> serviceBeanOptional = serviceDAL.getService(serviceRequest.getServiceName());
        if (!serviceBeanOptional.isPresent()) {
            return new ServiceResponse(200, "Unable to find the Service", false);
        }
        ServiceBean serviceBean = serviceBeanOptional.get();
        if (serviceBean.getServiceUrl().equals(serviceRequest.getServiceUrl())) {
            return new ServiceResponse(200, "Data already exists. No need to update", false);
        }
        serviceDAL.updateServiceUrl(serviceRequest);
        monitorServicesLookup.addService(serviceRequest.getServiceName());
        return new ServiceResponse(200, "Service Url Updated", true);
    }

    /**
     * method to delete
     * service
     * @param serviceName - refers to serviceName string
     * @return ServiceResponse
     */
    @Transactional
    public ServiceResponse removeService(String serviceName) {
        log.info("serviceName for removeService method is {}", serviceName);
        Optional<ServiceBean> serviceBeanOptional = serviceDAL.getService(serviceName);
        if (!serviceBeanOptional.isPresent() ||
                (serviceBeanOptional.isPresent() && !serviceBeanOptional.get().getServiceName().equalsIgnoreCase(serviceName))) {
            return new ServiceResponse(200, "Unable to find the Service", false);
        }
        serviceDAL.deleteService(serviceName);
        monitorServicesLookup.invalidate(serviceName);
        return new ServiceResponse(200, "Service deleted", true);
    }

    /**
     * method to get
     * list of service
     * @return String
     */
    @Transactional
    public List<ServiceBean> getServiceList() {
        List<Optional<ServiceBean>> serviceBeanOptionalList = monitorServicesLookup.getServices();
        return serviceBeanOptionalList.stream().filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * method to validate url
     * @param url - refers to validateUrl
     * @return boolean
     */
    private boolean isValidUrl(String url) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
