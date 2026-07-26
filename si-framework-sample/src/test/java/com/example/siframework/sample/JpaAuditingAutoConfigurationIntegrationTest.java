package com.example.siframework.sample;

import com.example.siframework.data.jpa.auditing.JpaAuditingDateTimeProvider;
import com.example.siframework.data.jpa.auditing.SpringDataAuditorAwareAdapter;
import com.example.siframework.data.jpa.config.JpaAuditingAuditorAutoConfiguration;
import com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JpaAuditingAutoConfigurationIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void JPA_감사_관련_Bean이_자동_등록된다() {
        //when
        Clock clock =
            applicationContext.getBean(Clock.class);

        DateTimeProvider dateTimeProvider =
            applicationContext.getBean(
                JpaAuditingTimeAutoConfiguration
                    .DATE_TIME_PROVIDER_BEAN_NAME,
                DateTimeProvider.class
            );

        AuditorAware<?> auditorAware =
            applicationContext.getBean(
                JpaAuditingAuditorAutoConfiguration
                    .AUDITOR_AWARE_BEAN_NAME,
                AuditorAware.class
            );

        //then
        assertNotNull(clock);

        assertInstanceOf(
            JpaAuditingDateTimeProvider.class,
            dateTimeProvider
        );

        assertInstanceOf(
            SpringDataAuditorAwareAdapter.class,
            auditorAware
        );

        assertEquals(
            "SAMPLE-SYSTEM",
            auditorAware
                .getCurrentAuditor()
                .orElseThrow()
        );
    }
}