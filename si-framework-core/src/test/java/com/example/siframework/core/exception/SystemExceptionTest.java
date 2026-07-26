package com.example.siframework.core.exception;

import com.example.siframework.core.error.CommonErrorCode;
import com.example.siframework.core.error.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SystemExceptionTest {

    @Test
    void 오류_코드의_기본_메시지로_시스템_예외를_생성한다() {
        // given
        ErrorCode errorCode =
            CommonErrorCode.UNEXPECTED_ERROR;

        // when
        SystemException exception =
            new SystemException(errorCode);

        // then
        assertSame(errorCode, exception.errorCode());
        assertEquals("COMMON-001", exception.code());
        assertEquals(
            "예상하지 못한 오류가 발생했습니다.",
            exception.defaultMessage()
        );
        assertEquals(
            "예상하지 못한 오류가 발생했습니다.",
            exception.getMessage()
        );
        assertNull(exception.getCause());
    }

    @Test
    void 상세_메시지를_포함한_시스템_예외를_생성한다() {
        // given
        String detailMessage =
            "외부 시스템 호출 중 오류가 발생했습니다.";

        // when
        SystemException exception =
            new SystemException(
                CommonErrorCode.UNEXPECTED_ERROR,
                detailMessage
            );

        // then
        assertEquals(detailMessage, exception.getMessage());
        assertEquals(
            CommonErrorCode.UNEXPECTED_ERROR.message(),
            exception.defaultMessage()
        );
    }

    @Test
    void 원인_예외를_포함한_시스템_예외를_생성한다() {
        // given
        RuntimeException cause =
            new RuntimeException("연결 시간 초과");

        // when
        SystemException exception =
            new SystemException(
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
    void 상세_메시지와_원인_예외를_함께_보관한다() {
        // given
        String detailMessage =
            "외부 시스템 호출에 실패했습니다.";

        RuntimeException cause =
            new RuntimeException("Connection timeout");

        // when
        SystemException exception =
            new SystemException(
                CommonErrorCode.UNEXPECTED_ERROR,
                detailMessage,
                cause
            );

        // then
        assertEquals(detailMessage, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void SystemException은_FrameworkException의_하위_타입이다() {
        // when
        SystemException exception =
            new SystemException(
                CommonErrorCode.UNEXPECTED_ERROR
            );

        // then
        assertInstanceOf(
            FrameworkException.class,
            exception
        );
    }

    @Test
    void 오류_코드가_null이면_생성에_실패한다() {
        // when
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new SystemException(null)
            );

        // then
        assertEquals(
            "오류 코드는 null일 수 없습니다.",
            exception.getMessage()
        );
    }
}