package com.example.siframework.sample;

import com.example.siframework.data.jpa.auditing.JpaAuditingDateTimeProvider;
import com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * data-jpa 모듈의 자동 설정이 sample 애플리케이션에서
 * 실제로 로딩되는지 검증한다.
 */
@SpringBootTest
class JpaAuditingAutoConfigurationIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void JPA_감사_시간_관련_Bean이_자동_등록된다() {
        //when
        Clock clock = applicationContext.getBean(
            Clock.class
        );

        DateTimeProvider dateTimeProvider =
            applicationContext.getBean(
                JpaAuditingTimeAutoConfiguration
                    .DATE_TIME_PROVIDER_BEAN_NAME,
                DateTimeProvider.class
            );

        //then
        assertNotNull(clock);

        assertInstanceOf(
            JpaAuditingDateTimeProvider.class,
            dateTimeProvider
        );
    }
}