package com.example.siframework.data.jpa.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdjustableCurrentAuditorProviderTest {

    @Test
    void 초기_감사_사용자를_반환한다() {
        // given
        AdjustableCurrentAuditorProvider provider =
            new AdjustableCurrentAuditorProvider(
                "create-user"
            );

        // when
        String auditor = provider.currentAuditor()
            .orElseThrow();

        // then
        assertEquals("create-user", auditor);
    }

    @Test
    void 현재_감사_사용자를_변경한다() {
        // given
        AdjustableCurrentAuditorProvider provider =
            new AdjustableCurrentAuditorProvider(
                "create-user"
            );

        // when
        provider.changeAuditor("modify-user");

        // then
        assertEquals(
            "modify-user",
            provider.currentAuditor().orElseThrow()
        );
    }

    @Test
    void 초기_감사_사용자가_null이면_생성에_실패한다() {
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new AdjustableCurrentAuditorProvider(
                    null
                )
            );

        assertEquals(
            "초기 감사 사용자는 비어 있을 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 초기_감사_사용자가_공백이면_생성에_실패한다() {
        IllegalArgumentException exception =
            assertThrows(
                IllegalArgumentException.class,
                () -> new AdjustableCurrentAuditorProvider(
                    " "
                )
            );

        assertEquals(
            "초기 감사 사용자는 비어 있을 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 변경할_감사_사용자가_공백이면_변경에_실패한다() {
        // given
        AdjustableCurrentAuditorProvider provider =
            new AdjustableCurrentAuditorProvider(
                "create-user"
            );

        // when
        IllegalArgumentException exception =
            assertThrows(
                IllegalArgumentException.class,
                () -> provider.changeAuditor(" ")
            );

        // then
        assertEquals(
            "변경할 감사 사용자는 비어 있을 수 없습니다.",
            exception.getMessage()
        );
    }
}