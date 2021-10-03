package com.monitor.service;

import com.monitor.dal.ServiceDAL;
import com.monitor.db.ServiceBean;
import com.monitor.util.ServiceStatusEnum;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * class to poll
 * saved service
 * @author prashant
 */
@Component
public class MonitorServicePoller {
    private static final Logger log = LoggerFactory.getLogger(MonitorServicePoller.class);
    private ServiceDAL serviceDAL;
    private MonitorServicesLookup monitorServicesLookup;

    @Autowired
    public MonitorServicePoller(ServiceDAL serviceDAL, MonitorServicesLookup monitorServicesLookup) {
        this.serviceDAL = serviceDAL;
        this.monitorServicesLookup = monitorServicesLookup;
    }

    @Transactional
    public void init() {
        log.info("============MonitorServicePoller Init==============");
        List<ServiceBean> serviceBeanList = serviceDAL.getServices();
        serviceBeanList.forEach(serviceBean -> monitorServicesLookup.addService(serviceBean.getServiceName(), serviceBean));
    }

    @Transactional
    public void poll() {
        try {
            monitorServicesLookup.getServices().forEach(serviceBeanOptional -> {
                String urlStatus = getUrlStatus(serviceBeanOptional.get().getServiceUrl());
                serviceDAL.updateServiceStatus(serviceBeanOptional.get().getServiceName(), urlStatus);
                monitorServicesLookup.addService(serviceBeanOptional.get().getServiceName());
            });
        } catch (Exception ex) {
            log.error("error while executing load method for MonitoServicePoller", ex);
        }
    }

    private String getUrlStatus(String url) {
        String status = ServiceStatusEnum.STATUS_FAIL.getName();
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                status = ServiceStatusEnum.STATUS_OK.getName();
            } else {
                status = ServiceStatusEnum.STATUS_FAIL.getName();
            }
        } catch (Exception ex) {
            log.error("error while checking status for url {}", url, ex);
        }
        return status;
    }
}
