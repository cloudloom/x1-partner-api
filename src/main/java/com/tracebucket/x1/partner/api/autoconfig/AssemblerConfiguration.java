package com.tracebucket.x1.partner.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingAssemblerBeans;
import com.tracebucket.tron.context.EnableAutoAssemblerResolution;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 25-May-15.
 */
@Configuration
@Conditional(value = NonExistingAssemblerBeans.class)
@EnableAutoAssemblerResolution(basePackages = {"com.tracebucket.x1.**.api.rest.assembler"})
public class AssemblerConfiguration {
}