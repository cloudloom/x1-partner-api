package com.tracebucket.x1.partner.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by sadath on 25-May-15.
 */
@Configuration(value = "x1PartnerServiceConfig")
@ComponentScan(basePackages = {"com.tracebucket.x1.partner.api.service.impl"}, scopedProxy = ScopedProxyMode.INTERFACES)
@EnableTransactionManagement(proxyTargetClass = true)
public class ServiceConfiguration {
}