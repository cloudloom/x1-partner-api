package com.tracebucket.x1.partner.api.config;

import com.tracebucket.tron.context.EnableAutoAssemblerResolution;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 25-May-15.
 */
@Configuration(value = "x1PartnerAssemblerConfig")
@EnableAutoAssemblerResolution(basePackages = {"com.tracebucket.x1.partner.api.rest.assembler"})
public class AssemblerConfiguration {
}