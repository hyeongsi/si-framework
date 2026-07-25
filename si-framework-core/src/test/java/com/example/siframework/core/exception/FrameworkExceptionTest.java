package com.example.siframework.core.exception;

import com.example.siframework.core.error.CommonErrorCode;
import com.example.siframework.core.error.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameworkExceptionTest {

    @Test
    void 오류_코드의_기본_메시지로_예외를_생성한다() {
        //given
        ErrorCode errorCode = CommonErrorCode.INVALID_REQUEST;

        //when
        FrameworkException exception = new TestFrameWorkException(errorCode);

        //then
        assertSame(errorCode, exception.errorCode());
        assertEquals("COMMON-002", exception.code());
        assertEquals("요청 값이 올바르지 않습니다.", exception.defaultMessage());
        assertEquals("요청 값이 올바르지 않습니다.", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void 상세_메시지가_있으면_예외_메시지로_사용한다() {
        //given
        String detailMessage = "시작일은 종료일보다 이후일 수 없습니다.";

        //when
        FrameworkException exception = new TestFrameWorkException(CommonErrorCode.INVALID_REQUEST, detailMessage);

        //then
        assertEquals(detailMessage, exception.getMessage());
        assertEquals("요청 값이 올바르지 않습니다.", exception.defaultMessage());
    }

    @Test
    void 상세_메시지가_공백이면_기본_메시지를_사용한다() {
        //when
        FrameworkException exception = new TestFrameWorkException(CommonErrorCode.INVALID_REQUEST, "   ");

        //then
        assertEquals(CommonErrorCode.INVALID_REQUEST.message(), exception.getMessage());
    }

    @Test
    void 원인_예외를_보존한다() {
        //given
        RuntimeException cause = new RuntimeException("원인 예외");

        //when
        FrameworkException exception =
            new TestFrameWorkException(
                CommonErrorCode.UNEXPECTED_ERROR,
                cause
            );

        // then
        assertSame(cause, exception.getCause());
        assertEquals(
            CommonErrorCode.UNEXPECTED_ERROR.message(),
            exception.getMessage()
        );
    }

    @Test
    void 오류_코드가_null이면_예외_생성에_실패한다() {
        //when
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new TestFrameWorkException(null)
            );

        //then
        assertEquals(
            "오류 코드는 null일 수 없습니다.",
            exception.getMessage()
        );
    }

    private static final class TestFrameWorkException extends FrameworkException {

        private static final long serialVersionUID = 1L;

        TestFrameWorkException(ErrorCode errorCode) {
            super(errorCode);
        }

        TestFrameWorkException(ErrorCode errorCode, String detailMessage) {
            super(errorCode, detailMessage);
        }

        public TestFrameWorkException(ErrorCode errorCode, Throwable cause) {
            super(errorCode, cause);
        }
    }
}