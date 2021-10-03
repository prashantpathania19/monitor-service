package com.monitor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Configuration for swagger documentation
 * @author prashant
 */
@Configuration
@EnableSwagger2
public class SwaggerDocConfig {
    private final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;

    @Autowired
    public SwaggerDocConfig(
            PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer) {this.propertySourcesPlaceholderConfigurer = propertySourcesPlaceholderConfigurer;}

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kry.monitor.web"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(generateApiInfo());
    }

    private ApiInfo generateApiInfo() {
        return new ApiInfo(
                "monitor-service service", "REST API for monitor-service service", "Version " + propertySourcesPlaceholderConfigurer.getAppliedPropertySources().get("environmentProperties").getProperty("version"),
                "",
                new Contact("Prashant Pathania", "", "prashantpathania19@gmail.com"),
                "", "", Collections.emptyList());
    }

}