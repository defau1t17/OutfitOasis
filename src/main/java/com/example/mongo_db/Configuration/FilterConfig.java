package com.example.mongo_db.Configuration;

import com.example.mongo_db.Security.ClientFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ClientFilter> clientFilter() {
        FilterRegistrationBean<ClientFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ClientFilter());
        filter.addUrlPatterns("/shop/client/account/*", "/shop/client/registration/address");

        return filter;
    }
}
