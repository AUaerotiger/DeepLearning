package com.gdms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
	
	@Value("${learning.image-url}")
    private String IMAGE_URL;
	
	@Value("${learning.image-path}")
    private String IMAGE_PATH;

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(IMAGE_URL + "/**")
                .addResourceLocations(
                        "file://" + IMAGE_PATH)
                .setCachePeriod(0);        
	}

}
