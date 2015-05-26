package com.tracebucket.x1.partner.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 25-May-15.
 */
@Configuration(value = "x1PartnerWebConfig")
@ComponentScan(basePackages = {"com.tracebucket.x1.partner.api.rest"})
public class WebConfiguration {
}