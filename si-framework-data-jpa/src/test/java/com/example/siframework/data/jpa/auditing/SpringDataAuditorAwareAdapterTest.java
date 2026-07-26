package com.example.siframework.data.jpa.auditing;

import com.example.siframework.core.context.CurrentAuditorProvider;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpringDataAuditorAwareAdapterTest {

    @Test
    void 현재_감사_사용자를_Spring_Data에_전달한다() {
        // given
        CurrentAuditorProvider currentAuditorProvider =
            () -> Optional.of("user-100");

        SpringDataAuditorAwareAdapter adapter =
            new SpringDataAuditorAwareAdapter(
                currentAuditorProvider
            );

        // when
        Optional<String> currentAuditor =
            adapter.getCurrentAuditor();

        // then
        assertEquals(
            "user-100",
            currentAuditor.orElseThrow()
        );
    }

    @Test
    void 현재_감사_사용자가_없으면_빈_Optional을_전달한다() {
        // given
        CurrentAuditorProvider currentAuditorProvider =
            Optional::empty;

        SpringDataAuditorAwareAdapter adapter =
            new SpringDataAuditorAwareAdapter(
                currentAuditorProvider
            );

        // when
        Optional<String> currentAuditor =
            adapter.getCurrentAuditor();

        // then
        assertTrue(currentAuditor.isEmpty());
    }

    @Test
    void 공급자가_반환한_Optional을_그대로_전달한다() {
        // given
        Optional<String> expected =
            Optional.of("batch-system");

        CurrentAuditorProvider currentAuditorProvider =
            () -> expected;

        SpringDataAuditorAwareAdapter adapter =
            new SpringDataAuditorAwareAdapter(
                currentAuditorProvider
            );

        // when
        Optional<String> actual =
            adapter.getCurrentAuditor();

        // then
        assertSame(expected, actual);
    }

    @Test
    void Spring_Data_AuditorAware_계약을_구현한다() {
        // given
        SpringDataAuditorAwareAdapter adapter =
            new SpringDataAuditorAwareAdapter(
                () -> Optional.of("user-100")
            );

        // then
        assertInstanceOf(
            AuditorAware.class,
            adapter
        );
    }

    @Test
    void 현재_감사_사용자_공급자가_null이면_생성에_실패한다() {
        // when
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new SpringDataAuditorAwareAdapter(null)
            );

        // then
        assertEquals(
            "현재 감사 사용자 공급자는 null일 수 없습니다.",
            exception.getMessage()
        );
    }
}