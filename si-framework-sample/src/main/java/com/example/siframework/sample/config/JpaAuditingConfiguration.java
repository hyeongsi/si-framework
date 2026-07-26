package com.example.siframework.sample.config;

import com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration(proxyBeanMethods = false)
@EnableJpaAuditing(
    dateTimeProviderRef =
        JpaAuditingTimeAutoConfiguration
            .DATE_TIME_PROVIDER_BEAN_NAME
)
public class JpaAuditingConfiguration {
}
