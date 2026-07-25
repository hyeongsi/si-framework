package com.example.siframework.core.error;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorCodeTest {

    @Test
    void 구현체를_통해_오류_코드와_메시지를_조회할_수_있다() {
        //given
        ErrorCode errorCode = TestErrorCode.INVALID_REQUEST;

        //when
        String code = errorCode.code();
        String message = errorCode.message();

        //then
        Assertions.assertEquals("TEST-001", code);
        Assertions.assertEquals("요청 값이 올바르지 않습니다.", message);
    }

    private enum TestErrorCode implements ErrorCode {
        INVALID_REQUEST(
            "TEST-001",
            "요청 값이 올바르지 않습니다."
        )
        ;

        private final String code;
        private final String message;

        TestErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String code() {
            return code;
        }

        @Override
        public String message() {
            return message;
        }
    }
}
