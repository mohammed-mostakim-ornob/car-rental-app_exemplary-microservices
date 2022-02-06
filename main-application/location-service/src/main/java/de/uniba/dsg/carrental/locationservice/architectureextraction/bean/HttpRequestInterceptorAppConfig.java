package de.uniba.dsg.carrental.locationservice.architectureextraction.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class HttpRequestInterceptorAppConfig implements WebMvcConfigurer {

    private final HttpRequestInterceptor httpRequestInterceptor;

    @Autowired
    public HttpRequestInterceptorAppConfig(HttpRequestInterceptor httpRequestInterceptor) {
        this.httpRequestInterceptor = httpRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpRequestInterceptor);
    }
}
