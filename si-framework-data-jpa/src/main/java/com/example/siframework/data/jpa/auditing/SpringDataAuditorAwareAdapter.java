package com.example.siframework.data.jpa.auditing;

import com.example.siframework.core.context.CurrentAuditorProvider;
import org.springframework.data.domain.AuditorAware;

import java.util.Objects;
import java.util.Optional;

/**
 * 프레임워크의 현재 감사 사용자 조회 계약을
 * Spring Data의 {@link AuditorAware}로 연결하는 어댑터다.
 *
 * <p>{@link CurrentAuditorProvider}는 특정 기술에 의존하지 않고
 * 현재 감사 주체를 제공한다. 이 클래스는 해당 결과를
 * Spring Data JPA 감사 기능이 이해할 수 있는 형태로 전달한다.</p>
 *
 * <p>이 어댑터가 반환하는 감사 주체는 다음 필드에 사용될 수 있다.</p>
 *
 * <ul>
 *     <li>{@code @CreatedBy}</li>
 *     <li>{@code @LastModifiedBy}</li>
 * </ul>
 */
public final class SpringDataAuditorAwareAdapter
    implements AuditorAware<String> {

    /**
     * 현재 실행 문맥에서 감사 주체를 조회하는 기술 독립 계약이다.
     */
    private final CurrentAuditorProvider currentAuditorProvider;

    /**
     * Spring Data 감사 사용자 어댑터를 생성한다.
     *
     * @param currentAuditorProvider 현재 감사 주체 공급자
     * @throws NullPointerException 공급자가 null인 경우
     */
    public SpringDataAuditorAwareAdapter(
        CurrentAuditorProvider currentAuditorProvider
    ) {
        this.currentAuditorProvider = Objects.requireNonNull(
            currentAuditorProvider,
            "현재 감사 사용자 공급자는 null일 수 없습니다."
        );
    }

    /**
     * 현재 감사 주체의 식별자를 반환한다.
     *
     * <p>현재 감사 주체를 확인할 수 없는 경우에는
     * 빈 Optional을 그대로 반환한다.</p>
     *
     * @return 현재 감사 주체 식별자
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return currentAuditorProvider.currentAuditor();
    }
}