package com.example.mongo_db.Configuration;

import com.example.mongo_db.Security.ClientFilter;
import com.example.mongo_db.Security.ClientRegistrationFilter;
import com.example.mongo_db.Service.Admin.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private StatisticService service;

    @Bean
    public FilterRegistrationBean<ClientFilter> clientFilter() {
        FilterRegistrationBean<ClientFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ClientFilter());
        filter.addUrlPatterns("/shop/client/account/*", "/shop/client/registration/address", "/shop/client", "/shop/producer/request/form");
        return filter;
    }

    @Bean
    public FilterRegistrationBean<ClientRegistrationFilter> clientRegistrationFilter() {
        FilterRegistrationBean<ClientRegistrationFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ClientRegistrationFilter());
        filter.addUrlPatterns("/shop/client/registration/verification");
        return filter;
    }

    @Bean
    public FilterRegistrationBean<VisitorFiler> newVisitorFilter() {
        FilterRegistrationBean<VisitorFiler> filter = new FilterRegistrationBean<>();
        filter.setFilter(new VisitorFiler(service));
        filter.addUrlPatterns("/shop/*");
        return filter;
    }

}
