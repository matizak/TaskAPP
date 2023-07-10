package com.matias.taskapp.config;

import feign.Client;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
}
