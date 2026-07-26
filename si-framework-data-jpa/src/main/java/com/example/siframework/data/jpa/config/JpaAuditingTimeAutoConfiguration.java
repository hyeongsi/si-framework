package com.example.siframework.data.jpa.config;

import com.example.siframework.data.jpa.auditing.JpaAuditingDateTimeProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.Clock;

/**
 * JPA 감사 시간 처리에 필요한 기본 Bean을 자동 구성한다.
 *
 * <p>소비 애플리케이션이 별도의 {@link Clock} 또는
 * 지정된 이름의 {@link DateTimeProvider} Bean을 등록하지 않은 경우에만
 * 프레임워크의 기본 Bean을 등록한다.</p>
 *
 * <p>기본 Clock은 JVM의 시스템 시각과 기본 시간대를 사용한다.</p>
 *
 * <p>이 자동 설정은 JPA 감사 시간 공급자만 구성하며,
 * {@code @EnableJpaAuditing}을 통한 감사 기능 활성화는
 * 소비 애플리케이션이 명시적으로 수행해야 한다.</p>
 */
@AutoConfiguration
@ConditionalOnClass(DateTimeProvider.class)
public class JpaAuditingTimeAutoConfiguration {

    /**
     * 프레임워크가 등록하는 JPA 감사 시간 공급자의 Bean 이름이다.
     *
     * <p>{@code @EnableJpaAuditing}의 {@code dateTimeProviderRef}에서
     * 이 상수를 사용할 수 있다.</p>
     */
    public static final String DATE_TIME_PROVIDER_BEAN_NAME =
        "siFrameworkJpaAuditingDateTimeProvider";

    /**
     * 시스템 시각과 JVM 기본 시간대를 사용하는 Clock을 등록한다.
     *
     * <p>소비 애플리케이션에 이미 Clock 타입 Bean이 존재하면
     * 이 기본 Bean은 등록하지 않는다.</p>
     *
     * @return 시스템 기본 시간대를 사용하는 Clock
     */
    @Bean
    @ConditionalOnMissingBean(Clock.class)
    Clock siFrameworkClock() {
        return Clock.systemDefaultZone();
    }

    /**
     * Spring Data JPA 감사 기능에서 사용할
     * DateTimeProvider를 등록한다.
     *
     * <p>동일한 이름의 Bean이 이미 존재하면
     * 소비 애플리케이션의 Bean을 우선하고
     * 프레임워크 기본 Bean은 등록하지 않는다.</p>
     *
     * @param clock 현재 시간을 제공하는 Clock
     * @return JPA 감사 시간 공급자
     */
    @Bean(name = DATE_TIME_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(
        name = DATE_TIME_PROVIDER_BEAN_NAME
    )
    DateTimeProvider siFrameworkJpaAuditingDateTimeProvider(
        Clock clock
    ) {
        return new JpaAuditingDateTimeProvider(clock);
    }
}