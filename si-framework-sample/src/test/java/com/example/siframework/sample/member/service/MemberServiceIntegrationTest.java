package com.example.siframework.sample.member.service;

import com.example.siframework.core.exception.BusinessException;
import com.example.siframework.sample.member.domain.Member;
import com.example.siframework.sample.member.domain.MemberStatus;
import com.example.siframework.sample.member.error.MemberErrorCode;
import com.example.siframework.sample.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 회원을_등록한다() {
        // when
        Member member = memberService.register(
            "service-member",
            "서비스 회원"
        );

        entityManager.flush();

        // then
        assertNotNull(member.id());

        assertEquals(
            "service-member",
            member.loginId()
        );

        assertEquals(
            "서비스 회원",
            member.name()
        );

        assertEquals(
            MemberStatus.ACTIVE,
            member.status()
        );

        assertEquals(
            "SAMPLE-SYSTEM",
            member.createdBy()
        );

        assertNotNull(member.version());
    }

    @Test
    void 중복된_로그인_ID로_회원을_등록할_수_없다() {
        // given
        memberService.register(
            "duplicate-service-member",
            "첫 번째 회원"
        );

        entityManager.flush();

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> memberService.register(
                    "duplicate-service-member",
                    "두 번째 회원"
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
    }

    @Test
    void 등록한_회원을_ID로_조회한다() {
        // given
        Member savedMember = memberService.register(
            "find-by-id-member",
            "ID 조회 회원"
        );

        entityManager.flush();
        entityManager.clear();

        // when
        Member foundMember =
            memberService.findById(
                savedMember.id()
            );

        // then
        assertEquals(
            savedMember.id(),
            foundMember.id()
        );

        assertEquals(
            "find-by-id-member",
            foundMember.loginId()
        );
    }

    @Test
    void 등록한_회원을_로그인_ID로_조회한다() {
        // given
        Member savedMember = memberService.register(
            "find-by-login-member",
            "로그인 조회 회원"
        );

        entityManager.flush();
        entityManager.clear();

        // when
        Member foundMember =
            memberService.findByLoginId(
                "find-by-login-member"
            );

        // then
        assertEquals(
            savedMember.id(),
            foundMember.id()
        );

        assertEquals(
            "로그인 조회 회원",
            foundMember.name()
        );
    }

    @Test
    void 존재하지_않는_회원_ID를_조회하면_예외가_발생한다() {
        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> memberService.findById(
                    Long.MAX_VALUE
                )
            );

        // then
        assertSame(
            MemberErrorCode.MEMBER_NOT_FOUND,
            exception.errorCode()
        );
    }
}