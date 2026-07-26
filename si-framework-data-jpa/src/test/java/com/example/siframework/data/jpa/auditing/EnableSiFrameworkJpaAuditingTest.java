package com.example.siframework.data.jpa.auditing;

import com.example.siframework.data.jpa.config.JpaAuditingAuditorAutoConfiguration;
import com.example.siframework.data.jpa.config.JpaAuditingTimeAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnableSiFrameworkJpaAuditingTest {

    @Test
    void 프레임워크_감사_활성화_애너테이션은_EnableJpaAuditing을_포함한다() {
        //when
        EnableJpaAuditing enableJpaAuditing =
            findEnableJpaAuditing();

        //then
        assertNotNull(enableJpaAuditing);
    }

    @Test
    void 프레임워크_감사_시간_공급자_Bean_이름을_사용한다() {
        //given
        EnableJpaAuditing enableJpaAuditing =
            findEnableJpaAuditing();

        //when
        String dateTimeProviderRef =
            enableJpaAuditing.dateTimeProviderRef();

        //then
        assertEquals(
            JpaAuditingTimeAutoConfiguration
                .DATE_TIME_PROVIDER_BEAN_NAME,
            dateTimeProviderRef
        );
    }

    @Test
    void 프레임워크_감사_사용자_Bean_이름을_사용한다() {
        //given
        EnableJpaAuditing enableJpaAuditing =
            findEnableJpaAuditing();

        //when
        String auditorAwareRef =
            enableJpaAuditing.auditorAwareRef();

        //then
        assertEquals(
            JpaAuditingAuditorAutoConfiguration
                .AUDITOR_AWARE_BEAN_NAME,
            auditorAwareRef
        );
    }

    /**
     * 테스트 설정 클래스에 합성된
     * EnableJpaAuditing 애너테이션을 조회한다.
     *
     * @return 병합된 EnableJpaAuditing 애너테이션
     */
    private EnableJpaAuditing findEnableJpaAuditing() {
        EnableJpaAuditing enableJpaAuditing =
            AnnotatedElementUtils.findMergedAnnotation(
                TestJpaAuditingConfiguration.class,
                EnableJpaAuditing.class
            );

        assertNotNull(enableJpaAuditing);

        return enableJpaAuditing;
    }

    /**
     * 합성 애너테이션의 메타 애너테이션 구성을
     * 검증하기 위한 테스트 설정 클래스다.
     */
    @EnableSiFrameworkJpaAuditing
    private static class TestJpaAuditingConfiguration {
    }
}