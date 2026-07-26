package com.example.siframework.core.context;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrentAuditorProviderTest {

    @Test
    void 현재_감사_주체_식별자를_반환한다() {
        //given
        CurrentAuditorProvider provider =
            () -> Optional.of("user-100");

        //when
        Optional<String> currentAuditor =
            provider.currentAuditor();

        //then
        assertEquals(
            "user-100",
            currentAuditor.orElseThrow()
        );
    }

    @Test
    void 현재_감사_주체가_없으면_빈_Optional을_반환한다() {
        //given
        CurrentAuditorProvider provider =
            Optional::empty;

        //when
        Optional<String> currentAuditor =
            provider.currentAuditor();

        //then
        assertTrue(currentAuditor.isEmpty());
    }
}