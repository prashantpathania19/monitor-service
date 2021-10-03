package com.monitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MonitorServicePollScheduler {
    private static final Logger log = LoggerFactory.getLogger(MonitorServicePollScheduler.class);
    private MonitorServicePoller monitorServicePoller;

    @Autowired
    public MonitorServicePollScheduler(MonitorServicePoller monitorServicePoller) {
        this.monitorServicePoller = monitorServicePoller;
    }

    @Scheduled(fixedDelayString = "10000")
    public void job(){
        try {
            log.info("==============MonitorServicePollScheduler Call===============");
            monitorServicePoller.poll();
        } catch(Exception ex) {
            log.error("error while executing job", ex);
        }
    }
}
