package com.example.siframework.sample;

import com.example.siframework.core.error.CommonErrorCode;
import com.example.siframework.core.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * sample 모듈이 core 모듈의 공개 API를
 * 정상적으로 사용할 수 있는지 검증한다.
 */
class CoreModuleIntegrationTest {

    /**
     * sample 모듈에서 core의 오류 코드와
     * 업무 예외를 사용할 수 있는지 검증한다.
     */
    @Test
    void sample_모듈에서_core_모듈의_공개_API를_사용할_수_있다() {
        //given
        BusinessException exception =
            new BusinessException(
                CommonErrorCode.INVALID_REQUEST
            );

        //then
        assertEquals("COMMON-002", exception.code());
        assertEquals(
            "요청 값이 올바르지 않습니다.",
            exception.getMessage()
        );
        assertInstanceOf(
            RuntimeException.class,
            exception
        );
    }
}