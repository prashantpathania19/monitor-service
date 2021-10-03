package com.monitor.web.api;

import com.google.gson.Gson;
import com.monitor.db.ServiceBean;
import com.monitor.service.MonitorService;
import com.monitor.vo.ServiceRequest;
import com.monitor.vo.ServiceResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test cases with URL mapping for
 * Monitor Service Controller
 * @author prashant
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MonitorRestController.class)
public class TextRestControllerMapWithMockIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private MonitorService monitorService;

    @Test
    public void testSaveService() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest("google", "http://www.google.com");
        ServiceResponse serviceResponse = new ServiceResponse(200, "Service Added", true);
        when(monitorService.saveService(Mockito.any())).thenReturn(serviceResponse);
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                .post("/monitor/api/save")
                .content(new Gson().toJson(serviceRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.
                andExpect(status().isOk()).
                andDo(print());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        assertNotNull(response);
        assertEquals(serviceResponse.getMessage(), new Gson().fromJson(response, ServiceResponse.class).getMessage());
    }

    @Test
    public void testUpdateService() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest("google", "http://www.google.com");
        ServiceResponse serviceResponse = new ServiceResponse(200, "Service Url Updated", true);
        when(monitorService.updateService(Mockito.any())).thenReturn(serviceResponse);
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/monitor/api/update")
                        .content(new Gson().toJson(serviceRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.
                andExpect(status().isOk()).
                andDo(print());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        assertNotNull(response);
        assertEquals(serviceResponse.getMessage(), new Gson().fromJson(response, ServiceResponse.class).getMessage());
    }

    @Test
    public void testRemoveService() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest("google", "");
        ServiceResponse serviceResponse = new ServiceResponse(200, "Service deleted", true);
        when(monitorService.removeService(Mockito.anyString())).thenReturn(serviceResponse);
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/monitor/api/remove")
                        .content(new Gson().toJson(serviceRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.
                andExpect(status().isOk()).
                andDo(print());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        assertNotNull(response);
        assertEquals(serviceResponse.getMessage(), new Gson().fromJson(response, ServiceResponse.class).getMessage());
    }

    @Test
    public void testGetAllServices() throws Exception {
        ServiceBean serviceBean = new ServiceBean.Builder().serviceName("google").serviceUrl("http://www.google.com").build();
        List<ServiceBean> serviceBeanList = new ArrayList<>();
        serviceBeanList.add(serviceBean);
        when(monitorService.getServiceList()).thenReturn(serviceBeanList);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/monitor/api/services")).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertTrue(!new Gson().fromJson(result, List.class).isEmpty());
    }

    @After
    public void tearDown() {
        this.mvc = null;
        this.monitorService = null;
    }
}