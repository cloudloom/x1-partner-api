package com.tracebucket.x1.partner.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by sadath on 25-May-15.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.tracebucket.x1.partner.api.config"})
public class DefaultPartnerStarter {
    public static void main(String[] args) {
        SpringApplication.run(DefaultPartnerStarter.class, args);
    }
}