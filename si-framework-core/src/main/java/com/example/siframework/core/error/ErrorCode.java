package com.example.siframework.core.error;

/**
 * 프레임워크와 업무 모듈에서 사용하는 오류 코드의 공통 계약이다.
 *
 * <p>오류를 문자열 메시지만으로 처리하지 않고,
 * 시스템이 식별할 수 있는 코드와 기본 메시지를 함께 제공하도록 한다.</p>
 *
 * <p>이 인터페이스는 Spring MVC, HTTP, JPA 같은 특정 기술에
 * 의존하지 않는다. 따라서 웹 애플리케이션뿐 아니라 배치,
 * 메시지 소비자, 일반 Java 애플리케이션에서도 재사용할 수 있다.</p>
 */
public interface ErrorCode {

    /**
     * 시스템이 오류를 식별하기 위한 고유 코드를 반환한다.
     *
     * <p>예:</p>
     *
     * <pre>
     * COMMON-001
     * VALIDATION-001
     * MEMBER-001
     * </pre>
     *
     * @return 비어 있지 않은 오류 코드
     */
    String code();

    /**
     * 오류의 기본 메시지를 반환한다.
     *
     * <p>실제 예외가 발생할 때 상세 메시지가 별도로 전달되더라도,
     * 오류 코드 자체가 가지는 기본 설명으로 사용한다.</p>
     *
     * @return 비어 있지 않은 기본 오류 메시지
     */
    String message();
}