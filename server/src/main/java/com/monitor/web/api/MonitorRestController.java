package com.monitor.web.api;

import com.google.gson.Gson;
import com.monitor.db.ServiceBean;
import com.monitor.service.MonitorService;
import com.monitor.vo.ServiceRequest;
import com.monitor.vo.ServiceResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * rest controller for
 * monitor service
 * @author prashant
 */
@RestController
public class MonitorRestController {
    private static final Logger log = LoggerFactory.getLogger(MonitorRestController.class);
    private MonitorService monitorService;
    private Gson gson;

    @Autowired
    public MonitorRestController(MonitorService monitorService, Gson gson) {
        this.monitorService = monitorService;
        this.gson = gson;
    }

    /**
     * rest endpoint
     * for saving new service
     * @param jsonData - refers to json data
     * @return ServiceResponse
     */
    @ApiOperation(value = "Saves new Service in database", response = Object.class)
    @ApiResponse(code = 200,  message = "new Service added")
    @PostMapping("/monitor/api/save")
    public ResponseEntity saveService(@RequestBody String jsonData) {
        ResponseEntity<ServiceResponse> responseEntity;
        try {
            ServiceRequest serviceRequest= gson.fromJson(jsonData, ServiceRequest.class);
            responseEntity = ResponseEntity.ok(monitorService.saveService(serviceRequest));
        } catch (Exception e) {
            log.error("Failed to save new Service for data {}", jsonData, e);
            responseEntity = ResponseEntity.ok(new ServiceResponse(200, "unable to save new service", false));
        }
        return responseEntity;
    }

    /**
     * rest endpoint
     * for updating service
     * @param jsonData - refers to json data
     * @return ServiceResponse
     */
    @ApiOperation(value = "Updates Service in database", response = Object.class)
    @ApiResponse(code = 200,  message = "updated service successfully")
    @PostMapping("/monitor/api/update")
    public ResponseEntity updateService(@RequestBody String jsonData) {
        ResponseEntity<ServiceResponse> responseEntity;
        try {
            ServiceRequest serviceRequest = gson.fromJson(jsonData, ServiceRequest.class);
            responseEntity = ResponseEntity.ok(monitorService.updateService(serviceRequest));
        } catch (Exception e) {
            log.error("Failed to update service for data {}", jsonData, e);
            responseEntity = ResponseEntity.ok(new ServiceResponse(200, "unable to update service", false));
        }
        return responseEntity;
    }

    /**
     * rest endpoint
     * for removing service
     * @param jsonData - refers to jsonData string
     * @return ServiceResponse
     */
    @ApiOperation(value = "removes Service in database", response = Object.class)
    @ApiResponse(code = 200,  message = "service removed successfully")
    @PostMapping("/monitor/api/remove")
    public ResponseEntity removeService(@RequestBody String jsonData) {
        ResponseEntity<ServiceResponse> responseEntity;
        try {
            ServiceRequest serviceRequest = gson.fromJson(jsonData, ServiceRequest.class);
            if (serviceRequest == null) {
                responseEntity = ResponseEntity.ok(new ServiceResponse(200, "service name can't be null or empty", false));
                return responseEntity;
            }
            responseEntity = ResponseEntity.ok(monitorService.removeService(serviceRequest.getServiceName()));
        } catch (Exception e) {
            log.error("Failed to remove service for jsonData {}", jsonData, e);
            responseEntity = ResponseEntity.ok(new ServiceResponse(200, "unable to remove service", false));
        }
        return responseEntity;
    }

    /**
     * rest endpoint
     * for fetching all services
     * @return List<ServiceBean>
     */
    @ApiOperation(value = "Fetches list of all activities stored in db", response = Object.class)
    @ApiResponse(code = 200,  message = "Successfully retrieved list of all LimitsActivityResponse")
    @GetMapping("/monitor/api/services")
    public ResponseEntity<List<ServiceBean>> getAllServices() {
        return ResponseEntity.ok(monitorService.getServiceList());
    }
}
