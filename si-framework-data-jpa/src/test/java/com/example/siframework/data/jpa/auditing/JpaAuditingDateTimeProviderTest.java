package com.example.siframework.data.jpa.auditing;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JpaAuditingDateTimeProviderTest {

    @Test
    void Clock을_기준으로_현재_시간을_반환한다() {
        //given
        ZoneId zoneId = ZoneId.of("Asia/Seoul");

        Clock clock = Clock.fixed(
            Instant.parse("2026-01-01T01:00:00Z"),
            zoneId
        );

        JpaAuditingDateTimeProvider provider =
            new JpaAuditingDateTimeProvider(clock);

        //when
        Optional<TemporalAccessor> result =
            provider.getNow();

        //then
        TemporalAccessor temporalAccessor =
            result.orElseThrow();

        LocalDateTime dateTime = assertInstanceOf(
            LocalDateTime.class,
            temporalAccessor
        );

        assertEquals(
            LocalDateTime.of(
                2026,
                1,
                1,
                10,
                0
            ),
            dateTime
        );
    }

    @Test
    void Clock이_null이면_생성에_실패한다() {
        //when
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> new JpaAuditingDateTimeProvider(null)
        );

        //then
        assertEquals(
            "Clock은 null일 수 없습니다.",
            exception.getMessage()
        );
    }
}