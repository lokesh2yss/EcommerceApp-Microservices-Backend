package com.codingshuttle.ecommerce.inventory_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }


    @Bean
    public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
}
