package com.example.siframework.core.exception;

import com.example.siframework.core.error.ErrorCode;

import java.util.Objects;

/**
 * SI 공통 프레임워크에서 사용하는 모든 런타임 예외의 최상위 기반 클래스다.
 *
 * <p>프레임워크 예외가 공통으로 오류 코드 정보를 보관하도록 하며,
 * 기본 메시지, 상세 메시지, 원인 예외를 일관된 방식으로 처리한다.</p>
 *
 * <p>이 클래스는 직접 생성해서 사용하기보다는
 * {@code BusinessException}, {@code SystemException}과 같은
 * 구체적인 예외 클래스가 상속해서 사용하도록 설계되었다.</p>
 */
public abstract class FrameworkException extends RuntimeException {

    /**
     * 예외 객체 직렬화 과정에서 사용하는 버전 식별자다.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 발생한 예외의 종류를 식별하는 오류 코드다.
     */
    private final ErrorCode errorCode;

    /**
     * 오류 코드의 기본 메시지를 사용하는 예외를 생성한다.
     *
     * @param errorCode 예외를 식별하는 오류 코드
     * @throws NullPointerException errorCode가 null인 경우
     */
    protected FrameworkException(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    /**
     * 별도의 상세 메시지를 사용하는 예외를 생성한다.
     *
     * <p>상세 메시지가 null이거나 공백이면
     * 오류 코드의 기본 메시지를 사용한다.</p>
     *
     * @param errorCode 예외를 식별하는 오류 코드
     * @param detailMessage 예외 발생 상황의 상세 메시지
     * @throws NullPointerException errorCode가 null인 경우
     */
    protected FrameworkException(
        ErrorCode errorCode,
        String detailMessage
    ) {
        this(errorCode, detailMessage, null);
    }

    /**
     * 원인 예외를 포함하는 예외를 생성한다.
     *
     * @param errorCode 예외를 식별하는 오류 코드
     * @param cause 원인이 된 예외
     * @throws NullPointerException errorCode가 null인 경우
     */
    protected FrameworkException(
        ErrorCode errorCode,
        Throwable cause
    ) {
        this(errorCode, null, cause);
    }

    /**
     * 상세 메시지와 원인 예외를 모두 포함하는 예외를 생성한다.
     *
     * <p>상세 메시지가 null이거나 공백이면
     * 오류 코드의 기본 메시지를 사용한다.</p>
     *
     * @param errorCode 예외를 식별하는 오류 코드
     * @param detailMessage 예외 발생 상황의 상세 메시지
     * @param cause 원인이 된 예외
     * @throws NullPointerException errorCode가 null인 경우
     */
    protected FrameworkException(
        ErrorCode errorCode,
        String detailMessage,
        Throwable cause
    ) {
        super(resolveMessage(errorCode, detailMessage), cause);

        this.errorCode = Objects.requireNonNull(
            errorCode,
            "오류 코드는 null일 수 없습니다."
        );
    }

    /**
     * 예외에 연결된 오류 코드 객체를 반환한다.
     *
     * @return 오류 코드
     */
    public final ErrorCode errorCode() {
        return errorCode;
    }

    /**
     * 시스템이 오류를 식별할 수 있는 문자열 코드를 반환한다.
     *
     * @return 문자열 오류 코드
     */
    public final String code() {
        return errorCode.code();
    }

    /**
     * 오류 코드에 정의된 기본 메시지를 반환한다.
     *
     * <p>{@link #getMessage()}는 예외 생성 시 전달된 상세 메시지를
     * 반환할 수 있지만, 이 메서드는 항상 오류 코드의 기본 메시지를
     * 반환한다.</p>
     *
     * @return 오류 코드의 기본 메시지
     */
    public final String defaultMessage() {
        return errorCode.message();
    }

    /**
     * 실제 예외 메시지를 결정한다.
     *
     * <p>상세 메시지가 존재하면 상세 메시지를 사용하고,
     * 상세 메시지가 없으면 오류 코드의 기본 메시지를 사용한다.</p>
     *
     * @param errorCode 오류 코드
     * @param detailMessage 상세 메시지
     * @return 최종 예외 메시지
     */
    private static String resolveMessage(
        ErrorCode errorCode,
        String detailMessage
    ) {
        ErrorCode requiredErrorCode = Objects.requireNonNull(
            errorCode,
            "오류 코드는 null일 수 없습니다."
        );

        if (detailMessage == null || detailMessage.isBlank()) {
            return requiredErrorCode.message();
        }

        return detailMessage;
    }
}