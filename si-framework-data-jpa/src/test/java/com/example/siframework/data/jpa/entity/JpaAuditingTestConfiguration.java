package com.example.siframework.data.jpa.entity;

import com.example.siframework.data.jpa.auditing.JpaAuditingDateTimeProvider;
import com.example.siframework.data.jpa.support.AdjustableClock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.time.ZoneId;

/**
 * BaseTimeEntity 통합 테스트에서 사용할
 * 최소 Spring Boot 및 JPA 감사 설정이다.
 */
@EnableJpaAuditing(
    dateTimeProviderRef =  "jpaAuditingDateTimeProvider"
)
@SpringBootApplication
class JpaAuditingTestConfiguration {

    @Bean
    AdjustableClock adjustableClock() {
        return new AdjustableClock(
            Instant.parse("2026-01-01T01:00:00Z"),
            ZoneId.of("Asia/Seoul")
        );
    }

    @Bean
    DateTimeProvider jpaAuditingDateTimeProvider(
        AdjustableClock adjustableClock
    ) {
        return new JpaAuditingDateTimeProvider(
            adjustableClock
        );
    }
}