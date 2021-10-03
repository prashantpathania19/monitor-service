/*
 * Copyright (c) 2021. ComeOn! All rights reserved. Company Confidential.
 */
package com.monitor.service;

import com.monitor.dal.ServiceDAL;
import com.monitor.db.ServiceBean;
import com.monitor.service.MonitorService;
import com.monitor.service.MonitorServicePoller;
import com.monitor.service.MonitorServicesLookup;
import com.monitor.vo.ServiceRequest;
import com.monitor.vo.ServiceResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * class containing JUnit test
 * cases for MonitorService
 * @author prashant
 */
@RunWith(SpringRunner.class)
public class MonitorServiceTest {
    @Mock
    private ServiceDAL serviceDAL;
    @Mock
    private MonitorServicesLookup monitorServicesLookup;
    @Mock
    private MonitorServicePoller monitorServicePoller;
    @InjectMocks
    private MonitorService monitorService;
    private ServiceRequest serviceRequest;
    private Optional<ServiceBean> serviceBeanOptional;

    @Before
    public void before() {
        serviceRequest = new ServiceRequest("google", "http://www.google.com");
        serviceBeanOptional = Optional.ofNullable(new ServiceBean.Builder().serviceName("google").serviceUrl("http://www.google.com").build());
    }

    @Test
    public void saveService_whenResponseIsSuccess() {
        Mockito.doNothing().when(serviceDAL).saveService(serviceRequest);
        Mockito.doNothing().when(monitorServicesLookup).addService(serviceRequest.getServiceName());
        ServiceResponse serviceResponse = monitorService.saveService(serviceRequest);
        Assert.assertNotNull(serviceResponse);
        Assert.assertEquals(200, serviceResponse.getStatus());
    }

    @Test
    public void saveService_whenResponseIsNotSuccess() {
        serviceRequest = new ServiceRequest("google", "http");
        ServiceResponse serviceResponse = monitorService.saveService(serviceRequest);
        Assert.assertNotNull(serviceResponse);
        Assert.assertEquals(200, serviceResponse.getStatus());
        Assert.assertEquals("Invalid Url", serviceResponse.getMessage());
    }

    @Test
    public void updateService_whenResponseIsSuccess() {
        Mockito.when(serviceDAL.getService(serviceRequest.getServiceName())).thenReturn(serviceBeanOptional);
        Mockito.doNothing().when(serviceDAL).updateServiceUrl(serviceRequest);
        Mockito.doNothing().when(monitorServicesLookup).addService(serviceRequest.getServiceName());
        ServiceResponse serviceResponse = monitorService.updateService(serviceRequest);
        Assert.assertNotNull(serviceResponse);
        Assert.assertEquals(200, serviceResponse.getStatus());
    }

    @Test
    public void updateService_whenResponseIsNotSuccess() {
        Mockito.when(serviceDAL.getService(serviceRequest.getServiceName())).thenReturn(Optional.empty());
        ServiceResponse serviceResponse = monitorService.updateService(serviceRequest);
        Assert.assertNotNull(serviceResponse);
        Assert.assertEquals(200, serviceResponse.getStatus());
        Assert.assertEquals("Unable to find the Service", serviceResponse.getMessage());
    }

    @Test
    public void removeService_whenResponseIsSuccess() {
        String serviceName = "google";
        Mockito.when(serviceDAL.getService(serviceName)).thenReturn(serviceBeanOptional);
        Mockito.doNothing().when(serviceDAL).deleteService(serviceName);
        Mockito.doNothing().when(monitorServicesLookup).invalidate(serviceName);
        ServiceResponse serviceResponse = monitorService.removeService(serviceName);
        Assert.assertNotNull(serviceResponse);
        Assert.assertEquals(200, serviceResponse.getStatus());
        Assert.assertEquals("Service deleted", serviceResponse.getMessage());
    }

    @Test
    public void removeService_whenResponseIsNotSuccess() {
        String serviceName = "google";
        Mockito.when(serviceDAL.getService(serviceName)).thenReturn(Optional.empty());
        ServiceResponse serviceResponse = monitorService.removeService(serviceName);
        Assert.assertNotNull(serviceResponse);
        Assert.assertEquals(200, serviceResponse.getStatus());
        Assert.assertEquals("Unable to find the Service", serviceResponse.getMessage());
    }

    @Test
    public void getServices_whenResponseIsSuccess() {
        List<Optional<ServiceBean>> serviceBeanOptionalList = new ArrayList<>();
        serviceBeanOptionalList.add(serviceBeanOptional);
        Mockito.when(monitorServicesLookup.getServices()).thenReturn(serviceBeanOptionalList);
        List<ServiceBean> serviceBeanList = monitorService.getServiceList();
        Assert.assertNotNull(serviceBeanList);
        Assert.assertFalse(serviceBeanList.isEmpty());
    }

    @Test
    public void getServices_whenResponseIsNotSuccess() {
        Mockito.when(monitorServicesLookup.getServices()).thenReturn(new ArrayList<>());
        List<ServiceBean> serviceBeanList = monitorService.getServiceList();
        Assert.assertNotNull(serviceBeanList);
        Assert.assertTrue(serviceBeanList.isEmpty());
    }

    @After
    public void tearDown() {
        this.serviceDAL = null;
        this.monitorServicesLookup = null;
        this.monitorServicePoller = null;
        this.monitorService = null;
        this.serviceRequest = null;
        this.serviceBeanOptional = null;
    }
}
