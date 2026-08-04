package com.example.siframework.sample.member.repository;

import com.example.siframework.core.exception.BusinessException;
import com.example.siframework.sample.member.domain.Member;
import com.example.siframework.sample.member.error.MemberErrorCode;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * MemberRepository가 프레임워크 공통 Repository 기능을
 * 정상적으로 사용하는지 검증한다.
 */
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 회원_ID로_회원을_필수_조회한다() {
        // given
        Member savedMember = repository.save(
            new Member(
                "repository-member",
                "저장소 회원"
            )
        );

        entityManager.flush();
        entityManager.clear();

        // when
        Member foundMember =
            repository.findByIdOrThrow(
                savedMember.id(),
                MemberErrorCode.MEMBER_NOT_FOUND
            );

        // then
        assertEquals(
            savedMember.id(),
            foundMember.id()
        );

        assertEquals(
            "repository-member",
            foundMember.loginId()
        );
    }

    @Test
    void 회원이_존재하지_않으면_회원_미존재_예외가_발생한다() {
        // given
        Long notExistingId = Long.MAX_VALUE;

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> repository.findByIdOrThrow(
                    notExistingId,
                    MemberErrorCode.MEMBER_NOT_FOUND
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
            "회원을 찾을 수 없습니다.",
            exception.getMessage()
        );
    }

    @Test
    void 회원이_존재하지_않으면_상세_메시지를_포함할_수_있다() {
        // given
        Long notExistingId = Long.MAX_VALUE;

        String detailMessage =
            "회원 조회에 실패했습니다. memberId="
                + notExistingId;

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> repository.findByIdOrThrow(
                    notExistingId,
                    MemberErrorCode.MEMBER_NOT_FOUND,
                    detailMessage
                )
            );

        // then
        assertSame(
            MemberErrorCode.MEMBER_NOT_FOUND,
            exception.errorCode()
        );

        assertEquals(
            detailMessage,
            exception.getMessage()
        );

        assertEquals(
            "회원을 찾을 수 없습니다.",
            exception.defaultMessage()
        );
    }
}