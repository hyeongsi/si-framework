package com.example.siframework.core.context;

import java.util.Optional;

/**
 * 현재 작업을 수행하는 감사 주체의 식별자를 제공하는 계약이다.
 *
 * <p>감사 주체는 반드시 로그인 사용자일 필요는 없다.
 * 실행 환경에 따라 다음과 같은 주체가 될 수 있다.</p>
 *
 * <ul>
 *     <li>인증된 사용자 ID</li>
 *     <li>배치 실행 계정</li>
 *     <li>스케줄러 시스템 계정</li>
 *     <li>외부 연동 시스템 식별자</li>
 *     <li>메시지 소비자 식별자</li>
 * </ul>
 *
 * <p>이 인터페이스는 Spring Security, Servlet, JPA 등의
 * 특정 기술에 의존하지 않는다. 각 실행 환경은 이 계약을 구현해
 * 현재 감사 주체를 제공해야 한다.</p>
 */
@FunctionalInterface
public interface CurrentAuditorProvider {

    /**
     * 현재 작업을 수행하는 감사 주체의 식별자를 반환한다.
     *
     * <p>현재 감사 주체를 확인할 수 없는 경우에는
     * 빈 {@link Optional}을 반환한다.</p>
     *
     * @return 현재 감사 주체 식별자
     */
    Optional<String> currentAuditor();
}