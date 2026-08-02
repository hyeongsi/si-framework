package com.example.siframework.sample.member.domain;

import com.example.siframework.core.exception.BusinessException;
import com.example.siframework.sample.member.error.MemberErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {

    @Test
    void 신규_회원은_활성_상태로_생성된다() {
        // when
        Member member = new Member(
            "member01",
            "홍길동"
        );

        // then
        assertNull(member.id());
        assertEquals("member01", member.loginId());
        assertEquals("홍길동", member.name());
        assertEquals(
            MemberStatus.ACTIVE,
            member.status()
        );
        assertNull(member.version());
    }

    @Test
    void 회원_이름을_변경한다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        // when
        member.changeName("김회원");

        // then
        assertEquals("김회원", member.name());
    }

    @Test
    void 활성_회원을_정지한다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        // when
        member.suspend();

        // then
        assertEquals(
            MemberStatus.SUSPENDED,
            member.status()
        );
    }

    @Test
    void 정지_회원을_다시_활성화한다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        member.suspend();

        // when
        member.activate();

        // then
        assertEquals(
            MemberStatus.ACTIVE,
            member.status()
        );
    }

    @Test
    void 활성_회원을_탈퇴시킨다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        // when
        member.withdraw();

        // then
        assertEquals(
            MemberStatus.WITHDRAWN,
            member.status()
        );
    }

    @Test
    void 탈퇴한_회원은_이름을_변경할_수_없다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        member.withdraw();

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> member.changeName("변경 이름")
            );

        // then
        assertSame(
            MemberErrorCode.MEMBER_OPERATION_NOT_ALLOWED,
            exception.errorCode()
        );

        assertEquals(
            "탈퇴한 회원은 정보를 변경할 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 활성_회원은_다시_활성화할_수_없다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                member::activate
            );

        // then
        assertSame(
            MemberErrorCode.MEMBER_OPERATION_NOT_ALLOWED,
            exception.errorCode()
        );

        assertEquals(
            "정지 회원만 다시 활성화할 수 있습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 정지_회원은_다시_정지할_수_없다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        member.suspend();

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                member::suspend
            );

        // then
        assertEquals(
            "활성 회원만 정지할 수 있습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 이미_탈퇴한_회원은_다시_탈퇴할_수_없다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        member.withdraw();

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                member::withdraw
            );

        // then
        assertEquals(
            "이미 탈퇴한 회원입니다.",
            exception.getMessage()
        );
    }

    @Test
    void 로그인_ID가_null이면_생성할_수_없다() {
        // when
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new Member(
                    null,
                    "홍길동"
                )
            );

        // then
        assertEquals(
            "로그인 ID는 비어 있을 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 로그인_ID가_공백이면_생성할_수_없다() {
        // when
        IllegalArgumentException exception =
            assertThrows(
                IllegalArgumentException.class,
                () -> new Member(
                    " ",
                    "홍길동"
                )
            );

        // then
        assertEquals(
            "로그인 ID는 비어 있을 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 회원_이름이_공백이면_생성할_수_없다() {
        // when
        IllegalArgumentException exception =
            assertThrows(
                IllegalArgumentException.class,
                () -> new Member(
                    "member01",
                    " "
                )
            );

        // then
        assertEquals(
            "회원 이름은 비어 있을 수 없습니다.",
            exception.getMessage()
        );
    }
}