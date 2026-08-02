package com.example.siframework.data.jpa.entity;

import com.example.siframework.data.jpa.auditing.EnableSiFrameworkJpaAuditing;
import com.example.siframework.data.jpa.config.JpaAuditingAuditorAutoConfiguration;
import com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration;
import com.example.siframework.data.jpa.support.AdjustableClock;
import com.example.siframework.data.jpa.support.AdjustableCurrentAuditorProvider;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.ZoneId;

/**
 * JPA 감사 통합 테스트에서 사용할
 * Spring Boot 애플리케이션 설정이다.
 */
@EnableSiFrameworkJpaAuditing
@ImportAutoConfiguration({
    JpaAuditingTimeAutoConfiguration.class,
    JpaAuditingAuditorAutoConfiguration.class
})
@SpringBootApplication
public class JpaAuditingTestConfiguration {

    /**
     * 테스트에서 현재 시각을 직접 변경할 수 있는 Clock을 등록한다.
     *
     * @return 테스트용 Clock
     */
    @Bean
    AdjustableClock adjustableClock() {
        return new AdjustableClock(
            Instant.parse("2026-01-01T01:00:00Z"),
            ZoneId.of("Asia/Seoul")
        );
    }

    /**
     * 테스트에서 현재 감사 사용자를 직접 변경할 수 있는
     * 공급자를 등록한다.
     *
     * @return 테스트용 감사 사용자 공급자
     */
    @Bean
    AdjustableCurrentAuditorProvider adjustableCurrentAuditorProvider() {

        return new AdjustableCurrentAuditorProvider(
            "create-user"
        );
    }
}