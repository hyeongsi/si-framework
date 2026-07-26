package com.example.siframework.data.jpa.support;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

/**
 * 테스트에서 현재 시간을 직접 이동할 수 있는 Clock 구현체다.
 *
 * <p>실제 시스템 시간을 기다리지 않고도
 * 생성 시각과 수정 시각의 변화를 안정적으로 검증할 수 있다.</p>
 *
 * <p>운영 코드가 아니라 테스트 소스에서만 사용하는 보조 클래스다.</p>
 */
public final class AdjustableClock extends Clock {

    /**
     * 현재 Clock이 반환할 기준 시각이다.
     */
    private Instant currentInstant;

    /**
     * 현재 Clock이 사용하는 시간대다.
     */
    private final ZoneId zone;

    /**
     * 조절 가능한 Clock을 생성한다.
     *
     * @param initialInstant 초기 시각
     * @param zone 사용할 시간대
     * @throws NullPointerException initialInstant 또는 zone이 null인 경우
     */
    public AdjustableClock(
        Instant initialInstant,
        ZoneId zone
    ) {
        this.currentInstant = Objects.requireNonNull(
            initialInstant,
            "초기 시각은 null일 수 없습니다."
        );

        this.zone = Objects.requireNonNull(
            zone,
            "시간대는 null일 수 없습니다."
        );
    }

    /**
     * 현재 Clock의 시간대를 반환한다.
     *
     * @return 현재 시간대
     */
    @Override
    public ZoneId getZone() {
        return zone;
    }

    /**
     * 동일한 현재 시각을 유지하면서
     * 다른 시간대를 사용하는 새 Clock을 반환한다.
     *
     * @param zone 변경할 시간대
     * @return 지정된 시간대를 사용하는 새 AdjustableClock
     */
    @Override
    public Clock withZone(ZoneId zone) {
        return new AdjustableClock(
            currentInstant,
            Objects.requireNonNull(
                zone,
                "시간대는 null일 수 없습니다."
            )
        );
    }

    /**
     * 현재 시각을 반환한다.
     *
     * @return 현재 Instant
     */
    @Override
    public Instant instant() {
        return currentInstant;
    }

    /**
     * 현재 시각을 지정한 기간만큼 앞으로 이동한다.
     *
     * @param duration 이동할 기간
     * @throws NullPointerException duration이 null인 경우
     * @throws IllegalArgumentException duration이 음수인 경우
     */
    public void advance(Duration duration) {
        Duration requiredDuration = Objects.requireNonNull(
            duration,
            "이동 기간은 null일 수 없습니다."
        );

        if (requiredDuration.isNegative()) {
            throw new IllegalArgumentException(
                "이동 기간은 음수일 수 없습니다."
            );
        }

        currentInstant = currentInstant.plus(requiredDuration);
    }

    /**
     * 현재 시각을 지정한 시각으로 변경한다.
     *
     * @param instant 변경할 시각
     * @throws NullPointerException instant가 null인 경우
     */
    public void setInstant(Instant instant) {
        currentInstant = Objects.requireNonNull(
            instant,
            "변경할 시각은 null일 수 없습니다."
        );
    }
}