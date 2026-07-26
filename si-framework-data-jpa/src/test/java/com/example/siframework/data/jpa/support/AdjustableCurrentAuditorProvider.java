package com.example.siframework.data.jpa.support;

import com.example.siframework.core.context.CurrentAuditorProvider;

import java.util.Objects;
import java.util.Optional;

/**
 * 테스트에서 현재 감사 사용자를 직접 변경할 수 있는
 * CurrentAuditorProvider 구현체다.
 */
public final class AdjustableCurrentAuditorProvider
    implements CurrentAuditorProvider {

    /**
     * 현재 감사 사용자 식별자다.
     */
    private String currentAuditor;

    /**
     * 조절 가능한 감사 사용자 공급자를 생성한다.
     *
     * @param initialAuditor 초기 감사 사용자 식별자
     */
    public AdjustableCurrentAuditorProvider(
        String initialAuditor
    ) {
        this.currentAuditor = requireText(
            initialAuditor,
            "초기 감사 사용자는 비어 있을 수 없습니다."
        );
    }

    /**
     * 현재 감사 사용자를 반환한다.
     *
     * @return 현재 감사 사용자
     */
    @Override
    public Optional<String> currentAuditor() {
        return Optional.of(currentAuditor);
    }

    /**
     * 현재 감사 사용자를 변경한다.
     *
     * @param currentAuditor 변경할 감사 사용자 식별자
     */
    public void changeAuditor(String currentAuditor) {
        this.currentAuditor = requireText(
            currentAuditor,
            "변경할 감사 사용자는 비어 있을 수 없습니다."
        );
    }

    /**
     * 문자열이 null이거나 공백인지 검증한다.
     *
     * @param value 검증할 값
     * @param message 검증 실패 메시지
     * @return 검증된 문자열
     */
    private static String requireText(
        String value,
        String message
    ) {
        Objects.requireNonNull(value, message);

        if (value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }
}