package com.example.siframework.sample.member.service;

import com.example.siframework.core.exception.BusinessException;
import com.example.siframework.sample.member.domain.Member;
import com.example.siframework.sample.member.error.MemberErrorCode;
import com.example.siframework.sample.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MemberServiceTest {

    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(
            MemberRepository.class
        );

        memberService = new MemberService(
            memberRepository
        );
    }

    @Test
    void 중복되지_않은_로그인_ID로_회원을_등록한다() {
        // given
        String loginId = "member01";
        String name = "홍길동";

        when(
            memberRepository.existsByLoginId(loginId)
        ).thenReturn(false);

        when(
            memberRepository.save(any(Member.class))
        ).thenAnswer(invocation ->
            invocation.getArgument(0)
        );

        // when
        Member member = memberService.register(
            loginId,
            name
        );

        // then
        assertEquals(loginId, member.loginId());
        assertEquals(name, member.name());

        verify(memberRepository)
            .existsByLoginId(loginId);

        verify(memberRepository)
            .save(any(Member.class));
    }

    @Test
    void 로그인_ID가_중복되면_회원을_등록할_수_없다() {
        // given
        String loginId = "duplicate-member";

        when(
            memberRepository.existsByLoginId(loginId)
        ).thenReturn(true);

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> memberService.register(
                    loginId,
                    "홍길동"
                )
            );

        // then
        assertSame(
            MemberErrorCode.DUPLICATE_LOGIN_ID,
            exception.errorCode()
        );

        assertEquals(
            "MEMBER-002",
            exception.code()
        );

        assertEquals(
            "이미 사용 중인 로그인 ID입니다. loginId="
                + loginId,
            exception.getMessage()
        );

        verify(memberRepository, never())
            .save(any(Member.class));
    }

    @Test
    void 회원_ID로_회원을_조회한다() {
        // given
        Long memberId = 1L;

        Member member = new Member(
            "member01",
            "홍길동"
        );

        when(
            memberRepository.findByIdOrThrow(
                memberId,
                MemberErrorCode.MEMBER_NOT_FOUND,
                "회원을 찾을 수 없습니다. memberId="
                    + memberId
            )
        ).thenReturn(member);

        // when
        Member foundMember =
            memberService.findById(memberId);

        // then
        assertSame(member, foundMember);
    }

    @Test
    void 로그인_ID로_회원을_조회한다() {
        // given
        String loginId = "member01";

        Member member = new Member(
            loginId,
            "홍길동"
        );

        when(
            memberRepository.findByLoginId(loginId)
        ).thenReturn(Optional.of(member));

        // when
        Member foundMember =
            memberService.findByLoginId(loginId);

        // then
        assertSame(member, foundMember);
    }

    @Test
    void 로그인_ID에_해당하는_회원이_없으면_예외가_발생한다() {
        // given
        String loginId = "not-existing-member";

        when(
            memberRepository.findByLoginId(loginId)
        ).thenReturn(Optional.empty());

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> memberService.findByLoginId(
                    loginId
                )
            );

        // then
        assertSame(
            MemberErrorCode.MEMBER_NOT_FOUND,
            exception.errorCode()
        );

        assertEquals(
            "MEMBER-001",
            exception.code()
        );

        assertEquals(
            "회원을 찾을 수 없습니다. loginId="
                + loginId,
            exception.getMessage()
        );
    }

    @Test
    void 회원_Repository가_null이면_서비스를_생성할_수_없다() {
        // when
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> new MemberService(null)
            );

        // then
        assertEquals(
            "회원 Repository는 null일 수 없습니다.",
            exception.getMessage()
        );
    }
}