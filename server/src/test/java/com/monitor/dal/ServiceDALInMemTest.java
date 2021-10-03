/*
 * Copyright (c) 2021. ComeOn! All rights reserved. Company Confidential.
 */
package com.monitor.dal;

import com.monitor.test.InMemoryDBTestUtil;
import com.monitor.db.ServiceBean;
import com.monitor.db.ServiceSQLs;
import com.monitor.util.JdbiConnectionHelper;
import com.monitor.vo.ServiceRequest;
import org.jdbi.v3.core.Handle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * class containing JUnit test
 * cases for AadhaarDALInMemTest
 * @author prashant
 */
@RunWith(SpringRunner.class)
public class ServiceDALInMemTest {
    private static Handle handle;
    @Mock
    private JdbiConnectionHelper jdbiConnectionHelper;
    @InjectMocks
    private ServiceDAL serviceDAL;
    private ServiceRequest serviceRequest;

    @Before
    public void before() throws IOException, ClassNotFoundException {
        handle = InMemoryDBTestUtil.createDBandReturnHandle();
        handle.execute("INSERT INTO monitor_service (id, service_name, service_url, created, updated, status) VALUES(0, 'google', 'https://www.google.com', curtime(), curtime(), 'PENDING');");
        ServiceSQLs serviceSQLs = InMemoryDBTestUtil.getDBI().onDemand(ServiceSQLs.class);
        when(jdbiConnectionHelper.attach(ServiceSQLs.class)).thenReturn(serviceSQLs);
        serviceRequest = new ServiceRequest("gmail", "http://www.gmail.com");
    }

    @Test
    public void saveService() {
        serviceDAL.saveService(serviceRequest);
        Optional<ServiceBean> serviceBeanOptional = serviceDAL.getService(serviceRequest.getServiceName());
        Assert.assertTrue(serviceBeanOptional.isPresent());
        Assert.assertEquals(serviceRequest.getServiceName(), serviceBeanOptional.get().getServiceName());
    }

    @Test
    public void updateService() {
        serviceRequest = new ServiceRequest("google", "http://www.gmail.se");
        serviceDAL.updateServiceUrl(serviceRequest);
        Optional<ServiceBean> serviceBeanOptional = serviceDAL.getService(serviceRequest.getServiceName());
        Assert.assertTrue(serviceBeanOptional.isPresent());
        Assert.assertEquals(serviceRequest.getServiceUrl(), serviceBeanOptional.get().getServiceUrl());
    }

    @Test
    public void removeService() {
        serviceDAL.deleteService("google");
        Optional<ServiceBean> serviceBeanOptional = serviceDAL.getService("google");
        Assert.assertFalse(serviceBeanOptional.isPresent());
    }

    @After
    public void tearDown() {
        handle.execute("DROP ALL OBJECTS;");
        serviceDAL = null;
        serviceRequest = null;
    }
}
