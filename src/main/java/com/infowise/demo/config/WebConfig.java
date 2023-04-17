package com.infowise.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/Users/infowise/img/") // mac version
//                .addResourceLocations("file:///E:\\img/") //-> window version
                .setCachePeriod(3600) // 24시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}