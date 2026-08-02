package com.example.siframework.sample.member.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class MemberJpaTest {

    @Autowired
    private MemberTestRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 회원을_저장하면_감사_정보와_버전이_설정된다() {
        // given
        Member member = new Member(
            "member01",
            "홍길동"
        );

        // when
        Member savedMember =
            repository.save(member);

        entityManager.flush();

        // then
        assertNotNull(savedMember.id());
        assertNotNull(savedMember.createdAt());
        assertNotNull(savedMember.modifiedAt());
        assertEquals(
            "SAMPLE-SYSTEM",
            savedMember.createdBy()
        );
        assertEquals(
            "SAMPLE-SYSTEM",
            savedMember.modifiedBy()
        );
        assertNotNull(savedMember.version());
    }

    @Test
    void 회원을_수정하면_상태와_버전이_변경된다() {
        // given
        Member member = repository.save(
            new Member(
                "member02",
                "김회원"
            )
        );

        entityManager.flush();

        Long memberId = member.id();
        Long originalVersion = member.version();

        entityManager.clear();

        Member foundMember =
            repository.findById(memberId)
                .orElseThrow();

        // when
        foundMember.suspend();
        entityManager.flush();

        // then
        assertEquals(
            MemberStatus.SUSPENDED,
            foundMember.status()
        );

        assertEquals(
            originalVersion + 1L,
            foundMember.version()
        );

        assertNotNull(foundMember.modifiedAt());

        assertEquals(
            "SAMPLE-SYSTEM",
            foundMember.modifiedBy()
        );
    }
}