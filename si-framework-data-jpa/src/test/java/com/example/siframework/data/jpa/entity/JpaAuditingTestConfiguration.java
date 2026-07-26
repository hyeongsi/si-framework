package com.example.siframework.data.jpa.entity;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * BaseTimeEntity 통합 테스트에서 사용할
 * 최소 Spring Boot 및 JPA 감사 설정이다.
 */
@EnableJpaAuditing
@SpringBootApplication
class JpaAuditingTestConfiguration {
}