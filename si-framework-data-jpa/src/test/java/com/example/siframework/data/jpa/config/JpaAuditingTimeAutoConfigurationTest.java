package com.example.siframework.data.jpa.config;

import com.example.siframework.data.jpa.auditing.JpaAuditingDateTimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration.DATE_TIME_PROVIDER_BEAN_NAME;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

class JpaAuditingTimeAutoConfigurationTest {

    /**
     * 테스트마다 독립된 Spring ApplicationContext를 생성한다.
     */
    private final ApplicationContextRunner contextRunner =
        new ApplicationContextRunner()
            .withConfiguration(
                AutoConfigurations.of(
                    JpaAuditingTimeAutoConfiguration.class
                )
            );

    @Test
    void Clock이_없으면_기본_Clock을_자동_등록한다() {
        contextRunner.run(context -> {
            //then
            context.assertThat()
                .hasSingleBean(Clock.class);

            Clock clock = context.getBean(Clock.class);

            assertInstanceOf(
                Clock.class,
                clock
            );
        });
    }

    @Test
    void JPA_감사_시간_공급자를_자동_등록한다() {
        contextRunner.run(context -> {
            //then
            context.assertThat()
                .hasBean(DATE_TIME_PROVIDER_BEAN_NAME);

            Object bean = context.getBean(
                DATE_TIME_PROVIDER_BEAN_NAME
            );

            assertInstanceOf(
                JpaAuditingDateTimeProvider.class,
                bean
            );

            context.assertThat()
                .hasSingleBean(DateTimeProvider.class);
        });
    }

    @Test
    void 사용자가_등록한_Clock을_우선한다() {
        contextRunner
            .withUserConfiguration(
                CustomClockConfiguration.class
            )
            .run(context -> {
                //given
                Clock expectedClock =
                    context.getBean(
                        "customClock",
                        Clock.class
                    );

                //when
                Clock actualClock =
                    context.getBean(Clock.class);

                //then
                context.assertThat()
                    .hasSingleBean(Clock.class);

                assertSame(
                    expectedClock,
                    actualClock
                );
            });
    }

    @Test
    void 약속된_이름의_DateTimeProvider가_있으면_기본_구현을_등록하지_않는다() {
        contextRunner
            .withUserConfiguration(
                CustomDateTimeProviderConfiguration.class
            )
            .run(context -> {
                //given
                DateTimeProvider expectedProvider =
                    context.getBean(
                        DATE_TIME_PROVIDER_BEAN_NAME,
                        DateTimeProvider.class
                    );

                //then
                assertSame(
                    CustomDateTimeProviderConfiguration
                        .CUSTOM_PROVIDER,
                    expectedProvider
                );

                context.assertThat()
                    .doesNotHaveBean(
                        JpaAuditingDateTimeProvider.class
                    );
            });
    }

    /**
     * 소비 애플리케이션이 직접 Clock을 등록한 상황을 재현한다.
     */
    @Configuration(proxyBeanMethods = false)
    static class CustomClockConfiguration {

        @Bean
        Clock customClock() {
            return Clock.fixed(
                Instant.parse("2026-01-01T00:00:00Z"),
                ZoneId.of("UTC")
            );
        }
    }

    /**
     * 소비 애플리케이션이 프레임워크 약속 이름으로
     * DateTimeProvider를 교체한 상황을 재현한다.
     */
    @Configuration(proxyBeanMethods = false)
    static class CustomDateTimeProviderConfiguration {

        private static final DateTimeProvider CUSTOM_PROVIDER =
            () -> java.util.Optional.of(
                Instant.parse("2026-01-01T00:00:00Z")
            );

        @Bean(name = DATE_TIME_PROVIDER_BEAN_NAME)
        DateTimeProvider customDateTimeProvider() {
            return CUSTOM_PROVIDER;
        }
    }
}