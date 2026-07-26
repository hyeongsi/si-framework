package com.example.siframework.data.jpa.support;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdjustableClockTest {

    @Test
    void 초기_시각과_시간대를_반환한다() {
        //given
        Instant initialInstant =
            Instant.parse("2026-01-01T01:00:00Z");

        ZoneId zone = ZoneId.of("Asia/Seoul");

        AdjustableClock clock =
            new AdjustableClock(
                initialInstant,
                zone
            );

        //then
        assertEquals(initialInstant, clock.instant());
        assertEquals(zone, clock.getZone());
    }

    @Test
    void 현재_시각을_지정한_기간만큼_앞으로_이동한다() {
        //given
        AdjustableClock clock =
            new AdjustableClock(
                Instant.parse("2026-01-01T01:00:00Z"),
                ZoneId.of("Asia/Seoul")
            );

        //when
        clock.advance(Duration.ofHours(2));

        //then
        assertEquals(
            Instant.parse("2026-01-01T03:00:00Z"),
            clock.instant()
        );
    }

    @Test
    void 현재_시각을_직접_변경한다() {
        //given
        AdjustableClock clock =
            new AdjustableClock(
                Instant.parse("2026-01-01T01:00:00Z"),
                ZoneId.of("Asia/Seoul")
            );

        Instant changedInstant =
            Instant.parse("2026-02-01T00:00:00Z");

        //when
        clock.setInstant(changedInstant);

        //then
        assertEquals(changedInstant, clock.instant());
    }

    @Test
    void 다른_시간대의_새_Clock을_생성한다() {
        //given
        AdjustableClock clock =
            new AdjustableClock(
                Instant.parse("2026-01-01T01:00:00Z"),
                ZoneId.of("Asia/Seoul")
            );

        ZoneId changedZone = ZoneId.of("UTC");

        //when
        Clock changedClock = clock.withZone(changedZone);

        //then
        assertNotSame(clock, changedClock);
        assertEquals(clock.instant(), changedClock.instant());
        assertEquals(changedZone, changedClock.getZone());
    }

    @Test
    void 음수_기간으로_이동할_수_없다() {
        //given
        AdjustableClock clock =
            new AdjustableClock(
                Instant.parse("2026-01-01T01:00:00Z"),
                ZoneId.of("Asia/Seoul")
            );

        //when
        IllegalArgumentException exception =
            assertThrows(
                IllegalArgumentException.class,
                () -> clock.advance(
                    Duration.ofMinutes(-1)
                )
            );

        //then
        assertEquals(
            "이동 기간은 음수일 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 초기_시각이_null이면_생성에_실패한다() {
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new AdjustableClock(
                    null,
                    ZoneId.of("Asia/Seoul")
                )
            );

        assertEquals(
            "초기 시각은 null일 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 시간대가_null이면_생성에_실패한다() {
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new AdjustableClock(
                    Instant.parse(
                        "2026-01-01T01:00:00Z"
                    ),
                    null
                )
            );

        assertEquals(
            "시간대는 null일 수 없습니다.",
            exception.getMessage()
        );
    }
}