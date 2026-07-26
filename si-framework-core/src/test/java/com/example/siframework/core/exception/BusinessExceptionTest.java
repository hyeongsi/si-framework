package com.example.siframework.core.exception;

import com.example.siframework.core.error.CommonErrorCode;
import com.example.siframework.core.error.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessExceptionTest {

    @Test
    void 오류_코드의_기본_메시지로_업무_예외를_생성한다() {
        // given
        ErrorCode errorCode =
            CommonErrorCode.RESOURCE_NOT_FOUND;

        // when
        BusinessException exception =
            new BusinessException(errorCode);

        // then
        assertSame(errorCode, exception.errorCode());
        assertEquals("COMMON-004", exception.code());
        assertEquals(
            "요청한 리소스를 찾을 수 없습니다.",
            exception.defaultMessage()
        );
        assertEquals(
            "요청한 리소스를 찾을 수 없습니다.",
            exception.getMessage()
        );
        assertNull(exception.getCause());
    }

    @Test
    void 상세_메시지를_포함한_업무_예외를_생성한다() {
        // given
        String detailMessage =
            "프로젝트 ID가 100인 프로젝트를 찾을 수 없습니다.";

        // when
        BusinessException exception =
            new BusinessException(
                CommonErrorCode.RESOURCE_NOT_FOUND,
                detailMessage
            );

        // then
        assertEquals(detailMessage, exception.getMessage());
        assertEquals(
            CommonErrorCode.RESOURCE_NOT_FOUND.message(),
            exception.defaultMessage()
        );
    }

    @Test
    void 원인_예외를_포함한_업무_예외를_생성한다() {
        // given
        IllegalArgumentException cause =
            new IllegalArgumentException("잘못된 입력값");

        // when
        BusinessException exception =
            new BusinessException(
                CommonErrorCode.INVALID_REQUEST,
                cause
            );

        // then
        assertSame(cause, exception.getCause());
        assertEquals(
            CommonErrorCode.INVALID_REQUEST.message(),
            exception.getMessage()
        );
    }

    @Test
    void 상세_메시지와_원인_예외를_함께_보관한다() {
        // given
        String detailMessage =
            "입력값 변환 과정에서 오류가 발생했습니다.";

        IllegalArgumentException cause =
            new IllegalArgumentException("변환 실패");

        // when
        BusinessException exception =
            new BusinessException(
                CommonErrorCode.INVALID_REQUEST,
                detailMessage,
                cause
            );

        // then
        assertEquals(detailMessage, exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void BusinessException은_FrameworkException의_하위_타입이다() {
        // when
        BusinessException exception =
            new BusinessException(
                CommonErrorCode.INVALID_REQUEST
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
                () -> new BusinessException(null)
            );

        // then
        assertEquals(
            "오류 코드는 null일 수 없습니다.",
            exception.getMessage()
        );
    }
}