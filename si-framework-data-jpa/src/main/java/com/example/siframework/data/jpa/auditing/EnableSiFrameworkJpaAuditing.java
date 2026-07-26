package com.example.siframework.data.jpa.auditing;

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
 * <p>Spring Data JPA의 감사 기능을 활성화하고,
 * 프레임워크가 제공하는 DateTimeProvider를 생성 및 수정 시간의
 * 공급자로 연결한다.</p>
 *
 * <p>이 애너테이션을 사용하려면 다음 조건이 충족되어야 한다.</p>
 *
 * <ul>
 *     <li>Spring Data JPA가 활성화되어 있어야 한다.</li>
 *     <li>프레임워크의 JPA 자동 설정이 적용되어야 한다.</li>
 *     <li>감사 대상 엔티티가 감사 애너테이션을 사용해야 한다.</li>
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
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaAuditing(
    dateTimeProviderRef =
        JpaAuditingTimeAutoConfiguration
            .DATE_TIME_PROVIDER_BEAN_NAME
)
public @interface EnableSiFrameworkJpaAuditing {
}