package com.example.siframework.core.error;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CommonErrorCodeTest {

    @Test
    void 공통_오류_코드는_코드와_기본_메시지를_제공한다() {
        //given
        CommonErrorCode errorCode = CommonErrorCode.INVALID_REQUEST;

        //when
        String code = errorCode.code();
        String message = errorCode.message();

        //then
        assertEquals("COMMON-002", code);
        assertEquals("요청 값이 올바르지 않습니다.", message);
    }

    @Test
    void 모든_공통_오류_코드와_메시지는_비어_있지_않다() {
        Arrays.stream(CommonErrorCode.values())
            .forEach(errorCode -> {
                assertFalse(errorCode.code().isBlank());
                assertFalse(errorCode.message().isBlank());
            });
    }

    @Test
    void 모든_공통_오류_코드는_고유하다() {
        //given
        CommonErrorCode[] errorCodes = CommonErrorCode.values();

        Set<String> uniqueCodes = new HashSet<>();

        //when
        Arrays.stream(errorCodes)
            .map(CommonErrorCode::code)
            .forEach(uniqueCodes::add);

        //then
        assertEquals(errorCodes.length, uniqueCodes.size());
    }

    @Test
    void 모든_공통_오류_코드는_COMMON_접두사로_시작한다() {
        Arrays.stream(CommonErrorCode.values())
            .map(CommonErrorCode::code)
            .forEach(code ->
                assertTrue(code.startsWith("COMMON-"))
            );
    }
}
