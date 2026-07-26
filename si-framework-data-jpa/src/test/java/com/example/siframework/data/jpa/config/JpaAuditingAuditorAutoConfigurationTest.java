package com.example.siframework.data.jpa.config;

import com.example.siframework.core.context.CurrentAuditorProvider;
import com.example.siframework.data.jpa.auditing.SpringDataAuditorAwareAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

import static com.example.siframework.data.jpa.config.JpaAuditingAuditorAutoConfiguration.AUDITOR_AWARE_BEAN_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

class JpaAuditingAuditorAutoConfigurationTest {

    /**
     * 테스트마다 독립된 Spring 컨텍스트를 생성한다.
     */
    private final ApplicationContextRunner contextRunner =
        new ApplicationContextRunner()
            .withConfiguration(
                AutoConfigurations.of(
                    JpaAuditingAuditorAutoConfiguration.class
                )
            );

    @Test
    void CurrentAuditorProvider가_없으면_AuditorAware를_등록하지_않는다() {
        contextRunner.run(context -> {
            //then
            context.assertThat()
                .doesNotHaveBean(CurrentAuditorProvider.class);

            context.assertThat()
                .doesNotHaveBean(AuditorAware.class);

            context.assertThat()
                .doesNotHaveBean(AUDITOR_AWARE_BEAN_NAME);
        });
    }

    @Test
    void CurrentAuditorProvider가_있으면_AuditorAware를_자동_등록한다() {
        contextRunner
            .withUserConfiguration(
                CurrentAuditorProviderConfiguration.class
            )
            .run(context -> {
                // then
                context.assertThat()
                    .hasSingleBean(
                        CurrentAuditorProvider.class
                    );

                context.assertThat()
                    .hasSingleBean(AuditorAware.class);

                context.assertThat()
                    .hasBean(AUDITOR_AWARE_BEAN_NAME);

                AuditorAware<?> auditorAware =
                    context.getBean(
                        AUDITOR_AWARE_BEAN_NAME,
                        AuditorAware.class
                    );

                assertInstanceOf(
                    SpringDataAuditorAwareAdapter.class,
                    auditorAware
                );
            });
    }

    @Test
    void CurrentAuditorProvider의_감사_사용자를_전달한다() {
        contextRunner
            .withUserConfiguration(
                CurrentAuditorProviderConfiguration.class
            )
            .run(context -> {
                //given
                AuditorAware<?> auditorAware =
                    context.getBean(
                        AUDITOR_AWARE_BEAN_NAME,
                        AuditorAware.class
                    );

                //when
                Object currentAuditor =
                    auditorAware
                        .getCurrentAuditor()
                        .orElseThrow();

                //then
                assertEquals(
                    "test-user",
                    currentAuditor
                );
            });
    }

    @Test
    void 약속된_이름의_AuditorAware가_있으면_사용자_Bean을_우선한다() {
        contextRunner
            .withUserConfiguration(
                CurrentAuditorProviderConfiguration.class,
                CustomAuditorAwareConfiguration.class
            )
            .run(context -> {
                // given
                AuditorAware<?> actual =
                    context.getBean(
                        AUDITOR_AWARE_BEAN_NAME,
                        AuditorAware.class
                    );

                // then
                assertSame(
                    CustomAuditorAwareConfiguration
                        .CUSTOM_AUDITOR_AWARE,
                    actual
                );

                context.assertThat()
                    .doesNotHaveBean(
                        SpringDataAuditorAwareAdapter.class
                    );
            });
    }

    /**
     * 소비 애플리케이션이 CurrentAuditorProvider를
     * 직접 제공하는 상황을 재현한다.
     */
    @Configuration(proxyBeanMethods = false)
    static class CurrentAuditorProviderConfiguration {

        @Bean
        CurrentAuditorProvider currentAuditorProvider() {
            return () -> Optional.of("test-user");
        }
    }

    /**
     * 소비 애플리케이션이 프레임워크 약속 이름으로
     * AuditorAware를 교체하는 상황을 재현한다.
     */
    @Configuration(proxyBeanMethods = false)
    static class CustomAuditorAwareConfiguration {

        private static final AuditorAware<String>
            CUSTOM_AUDITOR_AWARE =
            () -> Optional.of("custom-user");

        @Bean(name = AUDITOR_AWARE_BEAN_NAME)
        AuditorAware<String> customAuditorAware() {
            return CUSTOM_AUDITOR_AWARE;
        }
    }
}