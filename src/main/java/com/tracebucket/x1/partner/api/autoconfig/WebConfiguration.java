package com.tracebucket.x1.partner.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingWebBeans;
import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 25-May-15.
 */
@Configuration
@Conditional(value = NonExistingWebBeans.class)
@ComponentScan(basePackages = {"com.tracebucket.x1.**.api.rest"})
public class WebConfiguration {
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}