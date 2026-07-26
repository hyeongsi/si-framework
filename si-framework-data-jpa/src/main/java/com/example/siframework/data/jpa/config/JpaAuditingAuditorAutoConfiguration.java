package com.example.siframework.data.jpa.config;

import com.example.siframework.core.context.CurrentAuditorProvider;
import com.example.siframework.data.jpa.auditing.SpringDataAuditorAwareAdapter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

/**
 * JPA 감사 사용자 처리에 필요한 Spring Data AuditorAware를
 * 자동으로 구성한다.
 *
 * <p>소비 애플리케이션에 {@link CurrentAuditorProvider} Bean이
 * 존재할 때만 프레임워크의 {@link AuditorAware} Bean을 등록한다.</p>
 *
 * <p>소비 애플리케이션이 프레임워크에서 약속한 이름으로
 * AuditorAware Bean을 직접 등록하면 사용자 정의 Bean을 우선하고
 * 프레임워크 기본 어댑터는 등록하지 않는다.</p>
 */
@AutoConfiguration(
    after = JpaAuditingTimeAutoConfiguration.class
)
@ConditionalOnClass(AuditorAware.class)
public class JpaAuditingAuditorAutoConfiguration {

    /**
     * 프레임워크가 등록하는 Spring Data AuditorAware Bean 이름이다.
     *
     * <p>{@code @EnableJpaAuditing}의 {@code auditorAwareRef}에서
     * 이 이름을 사용한다.</p>
     */
    public static final String AUDITOR_AWARE_BEAN_NAME =
        "siFrameworkJpaAuditorAware";

    /**
     * 기술 독립적인 현재 감사 사용자 공급자를
     * Spring Data AuditorAware로 변환해 등록한다.
     *
     * @param currentAuditorProvider 현재 감사 사용자 공급자
     * @return Spring Data 감사 사용자 어댑터
     */
    @Bean(name = AUDITOR_AWARE_BEAN_NAME)
    @ConditionalOnBean(CurrentAuditorProvider.class)
    @ConditionalOnMissingBean(
        name = AUDITOR_AWARE_BEAN_NAME
    )
    AuditorAware<String> siFrameworkJpaAuditorAware(
        CurrentAuditorProvider currentAuditorProvider
    ) {
        return new SpringDataAuditorAwareAdapter(
            currentAuditorProvider
        );
    }
}