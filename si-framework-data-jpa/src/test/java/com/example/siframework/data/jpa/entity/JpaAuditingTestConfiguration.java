package com.example.siframework.data.jpa.entity;

import com.example.siframework.data.jpa.auditing.EnableSiFrameworkJpaAuditing;
import com.example.siframework.data.jpa.auditing.JpaAuditingDateTimeProvider;
import com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration;
import com.example.siframework.data.jpa.support.AdjustableClock;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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
@EnableSiFrameworkJpaAuditing
@ImportAutoConfiguration(
    JpaAuditingTimeAutoConfiguration.class
)
@SpringBootApplication
class JpaAuditingTestConfiguration {

    /**
     * 테스트에서 시간을 직접 조절할 수 있는 Clock을 등록한다.
     *
     * <p>사용자 Clock Bean이 존재하므로 자동 설정의 기본 Clock은
     * 등록되지 않는다.</p>
     *
     * @return 테스트용 AdjustableClock
     */
    @Bean
    AdjustableClock adjustableClock() {
        return new AdjustableClock(
            Instant.parse("2026-01-01T01:00:00Z"),
            ZoneId.of("Asia/Seoul")
        );
    }
}