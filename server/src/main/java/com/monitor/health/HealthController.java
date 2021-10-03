package com.monitor.health;

import com.monitor.form.HealthForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public HealthForm health() {
        HealthForm healthForm = new HealthForm();
        healthForm.setHealthy(true);
        healthForm.setReason("Monitor Service is alive");
        return healthForm;
    }
}
