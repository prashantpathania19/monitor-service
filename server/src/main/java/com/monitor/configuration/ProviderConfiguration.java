package com.monitor.configuration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ProviderConfiguration {
    @Bean
    public ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                .setNameFormat("InjectedService-%d").build());
    }

    @Bean
    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.enableComplexMapKeySerialization();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        return builder.create();
    }
}
