package com.example.siframework.core.exception;

import com.example.siframework.core.error.ErrorCode;

/**
 * 시스템 내부 오류 또는 기술적 장애를 표현하는 예외다.
 *
 * <p>사용자의 정상적인 업무 요청에 따른 오류가 아니라,
 * 애플리케이션 내부 처리, 외부 시스템 연동, 파일 입출력,
 * 데이터 변환 등 기술 영역에서 발생한 문제에 사용한다.</p>
 *
 * <p>예:</p>
 *
 * <ul>
 *     <li>데이터베이스 접근 실패</li>
 *     <li>외부 API 호출 실패</li>
 *     <li>파일 읽기 또는 쓰기 실패</li>
 *     <li>데이터 직렬화 또는 역직렬화 실패</li>
 *     <li>예상하지 못한 런타임 오류</li>
 * </ul>
 */
public class SystemException extends FrameworkException {

    /**
     * 예외 직렬화 버전 식별자다.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 오류 코드의 기본 메시지로 시스템 예외를 생성한다.
     *
     * @param errorCode 시스템 오류를 식별하는 오류 코드
     */
    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 상세 메시지를 포함하는 시스템 예외를 생성한다.
     *
     * <p>상세 메시지가 null이거나 공백이면
     * 오류 코드의 기본 메시지를 사용한다.</p>
     *
     * @param errorCode 시스템 오류를 식별하는 오류 코드
     * @param detailMessage 예외가 발생한 구체적인 기술 상황
     */
    public SystemException(
        ErrorCode errorCode,
        String detailMessage
    ) {
        super(errorCode, detailMessage);
    }

    /**
     * 원인 예외를 포함하는 시스템 예외를 생성한다.
     *
     * <p>시스템 오류는 하위 기술 예외를 감싸는 경우가 많으므로
     * 원인 예외를 가능한 한 보존하는 것이 중요하다.</p>
     *
     * @param errorCode 시스템 오류를 식별하는 오류 코드
     * @param cause 원인이 된 예외
     */
    public SystemException(
        ErrorCode errorCode,
        Throwable cause
    ) {
        super(errorCode, cause);
    }

    /**
     * 상세 메시지와 원인 예외를 모두 포함하는 시스템 예외를 생성한다.
     *
     * @param errorCode 시스템 오류를 식별하는 오류 코드
     * @param detailMessage 예외가 발생한 구체적인 기술 상황
     * @param cause 원인이 된 예외
     */
    public SystemException(
        ErrorCode errorCode,
        String detailMessage,
        Throwable cause
    ) {
        super(errorCode, detailMessage, cause);
    }
}