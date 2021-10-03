package com.monitor.web.api.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@CrossOrigin
@RestController
public class FormController {
    private final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;

    @Autowired
    public FormController(PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer) {
        this.propertySourcesPlaceholderConfigurer = propertySourcesPlaceholderConfigurer;
    }

    @RequestMapping(value="/form",  method = GET)
    public ModelAndView displayPage() {
        return new ModelAndView("monitor");
    }
}
