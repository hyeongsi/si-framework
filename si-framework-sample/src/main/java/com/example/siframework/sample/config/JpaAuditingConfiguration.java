package com.example.siframework.sample.config;

import com.example.siframework.core.context.CurrentAuditorProvider;
import com.example.siframework.data.jpa.auditing.EnableSiFrameworkJpaAuditing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration(proxyBeanMethods = false)
@EnableSiFrameworkJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    CurrentAuditorProvider currentAuditorProvider() {
        return () -> Optional.of("SAMPLE-SYSTEM");
    }
}
