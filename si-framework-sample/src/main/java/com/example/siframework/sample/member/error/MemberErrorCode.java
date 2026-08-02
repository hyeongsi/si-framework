package com.example.siframework.sample.member.error;

import com.example.siframework.core.error.ErrorCode;

import java.util.Objects;

/**
 * sample 회원 도메인에서 사용하는 오류 코드를 정의한다.
 *
 * <p>회원 조회, 등록, 수정 등 회원 업무 처리 과정에서
 * 발생할 수 있는 오류를 식별한다.</p>
 */
public enum MemberErrorCode implements ErrorCode {

    /**
     * 요청한 회원을 찾을 수 없는 경우다.
     */
    MEMBER_NOT_FOUND(
        "MEMBER-001",
        "회원을 찾을 수 없습니다."
    ),

    /**
     * 이미 사용 중인 로그인 ID로 회원을 등록하려는 경우다.
     */
    DUPLICATE_LOGIN_ID(
        "MEMBER-002",
        "이미 사용 중인 로그인 ID입니다."
    ),

    /**
     * 회원 상태에서 허용되지 않는 작업을 요청한 경우다.
     */
    MEMBER_OPERATION_NOT_ALLOWED(
        "MEMBER-003",
        "현재 회원 상태에서는 요청한 작업을 수행할 수 없습니다."
    );

    /**
     * 외부에 전달되는 오류 식별 코드다.
     */
    private final String code;

    /**
     * 사용자에게 제공할 기본 오류 메시지다.
     */
    private final String message;

    /**
     * 회원 오류 코드를 생성한다.
     *
     * @param code 오류 식별 코드
     * @param message 기본 오류 메시지
     */
    MemberErrorCode(
        String code,
        String message
    ) {
        this.code = requireText(
            code,
            "오류 코드는 비어 있을 수 없습니다."
        );

        this.message = requireText(
            message,
            "오류 메시지는 비어 있을 수 없습니다."
        );
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
     * 기본 오류 메시지를 반환한다.
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
     * @param value 검증할 값
     * @param errorMessage 검증 실패 메시지
     * @return 검증된 문자열
     */
    private static String requireText(
        String value,
        String errorMessage
    ) {
        String requiredValue = Objects.requireNonNull(
            value,
            errorMessage
        );

        if (requiredValue.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return requiredValue;
    }
}