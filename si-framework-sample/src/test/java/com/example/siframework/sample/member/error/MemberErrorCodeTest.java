package com.example.siframework.sample.member.error;

import com.example.siframework.core.error.ErrorCode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberErrorCodeTest {

    @Test
    void 회원_오류_코드는_ErrorCode를_구현한다() {
        // given
        MemberErrorCode errorCode =
            MemberErrorCode.MEMBER_NOT_FOUND;

        // then
        assertInstanceOf(
            ErrorCode.class,
            errorCode
        );
    }

    @Test
    void 회원_미존재_오류_정보를_반환한다() {
        // given
        MemberErrorCode errorCode =
            MemberErrorCode.MEMBER_NOT_FOUND;

        // then
        assertEquals(
            "MEMBER-001",
            errorCode.code()
        );

        assertEquals(
            "회원을 찾을 수 없습니다.",
            errorCode.message()
        );
    }

    @Test
    void 로그인_ID_중복_오류_정보를_반환한다() {
        // given
        MemberErrorCode errorCode =
            MemberErrorCode.DUPLICATE_LOGIN_ID;

        // then
        assertEquals(
            "MEMBER-002",
            errorCode.code()
        );

        assertEquals(
            "이미 사용 중인 로그인 ID입니다.",
            errorCode.message()
        );
    }

    @Test
    void 모든_회원_오류_코드는_MEMBER_접두어를_사용한다() {
        Arrays.stream(MemberErrorCode.values())
            .forEach(errorCode ->
                assertTrue(
                    errorCode.code()
                        .startsWith("MEMBER-")
                )
            );
    }

    @Test
    void 모든_회원_오류_코드와_메시지는_비어_있지_않다() {
        Arrays.stream(MemberErrorCode.values())
            .forEach(errorCode -> {
                assertTrue(
                    !errorCode.code().isBlank()
                );

                assertTrue(
                    !errorCode.message().isBlank()
                );
            });
    }

    @Test
    void 회원_오류_코드는_중복되지_않는다() {
        // given
        MemberErrorCode[] errorCodes =
            MemberErrorCode.values();

        // when
        Set<String> uniqueCodes =
            Arrays.stream(errorCodes)
                .map(MemberErrorCode::code)
                .collect(Collectors.toSet());

        // then
        assertEquals(
            errorCodes.length,
            uniqueCodes.size()
        );
    }
}