package com.example.siframework.data.jpa.auditing;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.Optional;

/**
 * Spring Data JPA 감사 기능에 현재 날짜와 시간을 제공한다.
 *
 * <p>{@link Clock}을 통해 현재 시간을 조회하므로 운영 환경에서는
 * 시스템 시간을 사용하고, 테스트 환경에서는 고정되거나 조절 가능한
 * 시간을 주입할 수 있다.</p>
 *
 * <p>이 클래스가 제공하는 시간은 다음 감사 애너테이션에서 사용된다.</p>
 *
 * <ul>
 *     <li>{@code @CreatedDate}</li>
 *     <li>{@code @LastModifiedDate}</li>
 * </ul>
 */
public final class JpaAuditingDateTimeProvider
    implements DateTimeProvider {

    /**
     * 현재 시각과 시간대를 제공하는 Java 표준 시간 공급자다.
     */
    private final Clock clock;

    /**
     * JPA 감사 시간 공급자를 생성한다.
     *
     * @param clock 현재 시각을 제공할 Clock
     * @throws NullPointerException clock이 null인 경우
     */
    public JpaAuditingDateTimeProvider(Clock clock) {
        this.clock = Objects.requireNonNull(
            clock,
            "Clock은 null일 수 없습니다."
        );
    }

    /**
     * Spring Data JPA 감사 기능에서 사용할 현재 시간을 반환한다.
     *
     * <p>{@link LocalDateTime}은 주입된 Clock의 시간대와 현재 시각을
     * 기준으로 생성된다.</p>
     *
     * @return 현재 LocalDateTime을 포함한 Optional
     */
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(
            LocalDateTime.now(clock)
        );
    }
}