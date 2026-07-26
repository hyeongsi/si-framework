package com.example.siframework.sample.config;

import com.example.siframework.data.jpa.auditing.EnableSiFrameworkJpaAuditing;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableSiFrameworkJpaAuditing
public class JpaAuditingConfiguration {
}
