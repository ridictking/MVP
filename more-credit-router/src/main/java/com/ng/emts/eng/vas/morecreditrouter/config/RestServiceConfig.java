package com.ng.emts.eng.vas.morecreditrouter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//import java.net.http.HttpClient;

@Configuration
public class RestServiceConfig {

    @Autowired
    RestTemplateBuilder restTemplateErrorHandler;

    @Bean
    public RestTemplate routingRestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }


}
