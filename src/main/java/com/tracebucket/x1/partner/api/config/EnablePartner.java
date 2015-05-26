package com.tracebucket.x1.partner.api.config;

import com.tracebucket.x1.partner.api.DefaultPartnerStarter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sadath on 25-May-15.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DefaultPartnerStarter.class})
public @interface EnablePartner {
}