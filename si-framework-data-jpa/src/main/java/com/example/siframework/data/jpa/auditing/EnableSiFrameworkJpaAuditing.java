package com.example.siframework.data.jpa.auditing;

import com.example.siframework.data.jpa.config.JpaAuditingAuditorAutoConfiguration;
import com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SI 공통 프레임워크의 JPA 감사 기능을 활성화한다.
 *
 * <p>이 애너테이션은 Spring Data JPA 감사 기능을 활성화하고
 * 프레임워크가 제공하는 시간 공급자와 감사 사용자 공급자를
 * 각각 연결한다.</p>
 *
 * <p>활성화되는 감사 정보는 다음과 같다.</p>
 *
 * <ul>
 *     <li>{@code @CreatedDate}: 생성 일시</li>
 *     <li>{@code @LastModifiedDate}: 최종 수정 일시</li>
 *     <li>{@code @CreatedBy}: 생성 사용자</li>
 *     <li>{@code @LastModifiedBy}: 최종 수정 사용자</li>
 * </ul>
 *
 * <p>사용 예:</p>
 *
 * <pre>
 * {@code
 * @Configuration(proxyBeanMethods = false)
 * @EnableSiFrameworkJpaAuditing
 * public class JpaAuditingConfiguration {
 * }
 * }
 * </pre>
 *
 * <p>사용자 감사 기능을 사용하려면 소비 애플리케이션에
 * {@code CurrentAuditorProvider} Bean이 등록되어 있어야 한다.</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaAuditing(
    dateTimeProviderRef =
        JpaAuditingTimeAutoConfiguration
            .DATE_TIME_PROVIDER_BEAN_NAME,

    auditorAwareRef =
        JpaAuditingAuditorAutoConfiguration
            .AUDITOR_AWARE_BEAN_NAME
)
public @interface EnableSiFrameworkJpaAuditing {
}