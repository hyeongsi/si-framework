package com.example.siframework.core.error;

/**
 * 프레임워크 전반에서 공통으로 사용할 수 있는 오류 코드를 정의한다.
 *
 * <p>특정 업무 도메인에 종속되지 않는 오류만 포함한다.</p>
 *
 * <p>회원, 주문, 결재, 계약 등의 업무별 오류는 이 enum에 추가하지 않고,
 * 각 업무 모듈에서 별도의 ErrorCode 구현체로 정의해야 한다.</p>
 */
public enum CommonErrorCode implements ErrorCode {

    /**
     * 애플리케이션에서 예상하지 못한 오류가 발생한 경우 사용한다.
     */
    UNEXPECTED_ERROR(
        "COMMON-001",
        "예상하지 못한 오류가 발생했습니다."
    ),

    /**
     * 요청 형식이나 요청 내용이 올바르지 않은 경우 사용한다.
     */
    INVALID_REQUEST(
        "COMMON-002",
        "요청 값이 올바르지 않습니다."
    ),

    /**
     * 입력값이 정의된 검증 규칙을 만족하지 못한 경우 사용한다.
     */
    INPUT_VALIDATION_FAILED(
        "COMMON-003",
        "입력값 검증에 실패했습니다."
    ),

    /**
     * 요청한 리소스 또는 데이터를 찾을 수 없는 경우 사용한다.
     */
    RESOURCE_NOT_FOUND(
        "COMMON-004",
        "요청한 리소스를 찾을 수 없습니다."
    ),

    /**
     * 동일한 식별자나 값의 리소스가 이미 존재하는 경우 사용한다.
     */
    DUPLICATE_RESOURCE(
        "COMMON-005",
        "이미 존재하는 리소스입니다."
    ),

    /**
     * 현재 상태나 업무 규칙상 실행할 수 없는 작업인 경우 사용한다.
     */
    OPERATION_NOT_ALLOWED(
        "COMMON-006",
        "허용되지 않은 작업입니다."
    );

    /**
     * 시스템과 클라이언트가 오류를 식별하기 위한 고유 코드다.
     */
    private final String code;

    /**
     * 오류 코드의 기본 메시지다.
     */
    private final String message;

    /**
     * 공통 오류 코드를 생성한다.
     *
     * @param code 오류 식별 코드
     * @param message 기본 오류 메시지
     */
    CommonErrorCode(
        String code,
        String message
    ) {
        this.code = requireText(code, "오류 코드는 비어 있을 수 없습니다.");
        this.message = requireText(message, "오류 메시지는 비어 있을 수 없습니다.");
    }

    /**
     * 오류 식별 코드를 반환한다.
     *
     * @return 오류 코드
     */
    @Override
    public String code() {
        return code;
    }

    /**
     * 오류의 기본 메시지를 반환한다.
     *
     * @return 기본 오류 메시지
     */
    @Override
    public String message() {
        return message;
    }

    /**
     * 문자열이 null이거나 공백인지 검증한다.
     *
     * <p>enum 상수 선언 오류를 애플리케이션 시작 시점에
     * 빠르게 발견하기 위한 내부 검증 메서드다.</p>
     *
     * @param value 검증할 문자열
     * @param errorMessage 검증 실패 시 사용할 메시지
     * @return 검증된 문자열
     * @throws IllegalArgumentException 문자열이 null이거나 공백인 경우
     */
    private static String requireText(
        String value,
        String errorMessage
    ) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return value;
    }
}