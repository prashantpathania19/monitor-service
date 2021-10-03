package com.monitor.service;

import com.monitor.dal.ServiceDAL;
import com.monitor.db.ServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class MonitorServicesLookup {
	private ServiceDAL serviceDAL;
	private Map<String, Optional<ServiceBean>> serviceBeanCache;

	@Autowired
	public MonitorServicesLookup(ServiceDAL serviceDAL) {
		this.serviceDAL = serviceDAL;
		serviceBeanCache = new ConcurrentHashMap<>();
	}

	public List<Optional<ServiceBean>> getServices() {
		return serviceBeanCache.values().stream().collect(Collectors.toList());
	}

	@Transactional
	public void addService(String serviceName) {
		Optional<ServiceBean> serviceBeanOptional = serviceDAL.getService(serviceName);
		if (serviceBeanOptional.isPresent()) {
			serviceBeanCache.put(serviceName, serviceBeanOptional);
		}
	}

	public void addService(String serviceName, ServiceBean serviceBean) {
		serviceBeanCache.put(serviceName, Optional.ofNullable(serviceBean));
	}

	public void invalidate(String serviceName) {
		serviceBeanCache.remove(serviceName);
	}
}
