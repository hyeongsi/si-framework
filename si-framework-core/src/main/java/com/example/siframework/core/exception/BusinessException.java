package com.example.siframework.core.exception;

import com.example.siframework.core.error.ErrorCode;

/**
 * 정상적인 업무 처리 과정에서 발생할 수 있는
 * 업무 규칙 위반을 표현하는 예외다.
 *
 * <p>시스템 장애가 아니라 사용자의 요청, 데이터 상태,
 * 업무 정책 등에 의해 예상 가능하게 발생하는 오류에 사용한다.</p>
 *
 * <p>예:</p>
 *
 * <ul>
 *     <li>요청한 데이터가 존재하지 않음</li>
 *     <li>중복된 데이터 등록 시도</li>
 *     <li>현재 상태에서 허용되지 않는 작업 수행</li>
 *     <li>업무 규칙에 맞지 않는 값 입력</li>
 * </ul>
 */
public class BusinessException extends FrameworkException {

    /**
     * 예외 직렬화 버전 식별자다.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 오류 코드의 기본 메시지로 업무 예외를 생성한다.
     *
     * @param errorCode 업무 오류를 식별하는 오류 코드
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 상세 메시지를 포함하는 업무 예외를 생성한다.
     *
     * <p>상세 메시지가 null이거나 공백이면
     * 오류 코드의 기본 메시지를 사용한다.</p>
     *
     * @param errorCode 업무 오류를 식별하는 오류 코드
     * @param detailMessage 예외가 발생한 구체적인 상황
     */
    public BusinessException(
        ErrorCode errorCode,
        String detailMessage
    ) {
        super(errorCode, detailMessage);
    }

    /**
     * 원인 예외를 포함하는 업무 예외를 생성한다.
     *
     * <p>일반적인 업무 예외에서는 원인 예외가 필요하지 않을 수 있지만,
     * 하위 계층의 예외를 업무 의미로 변환해야 하는 경우 사용할 수 있다.</p>
     *
     * @param errorCode 업무 오류를 식별하는 오류 코드
     * @param cause 원인이 된 예외
     */
    public BusinessException(
        ErrorCode errorCode,
        Throwable cause
    ) {
        super(errorCode, cause);
    }

    /**
     * 상세 메시지와 원인 예외를 모두 포함하는 업무 예외를 생성한다.
     *
     * @param errorCode 업무 오류를 식별하는 오류 코드
     * @param detailMessage 예외가 발생한 구체적인 상황
     * @param cause 원인이 된 예외
     */
    public BusinessException(
        ErrorCode errorCode,
        String detailMessage,
        Throwable cause
    ) {
        super(errorCode, detailMessage, cause);
    }
}