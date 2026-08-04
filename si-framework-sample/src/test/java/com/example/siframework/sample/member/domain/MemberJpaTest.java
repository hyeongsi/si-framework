package com.example.siframework.sample.member.domain;

import com.example.siframework.sample.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaTest {

    @Autowired
    private MemberRepository repository;

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

    @Test
    void 동일한_로그인_ID의_회원을_중복_저장할_수_없다() {
        // given
        repository.save(
            new Member(
                "unique-member",
                "첫 번째 회원"
            )
        );

        entityManager.flush();
        entityManager.clear();



        // when & then
        assertThrows(
            org.springframework.dao.DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(
                new Member(
                    "unique-member",
                    "두 번째 회원"
                )
            )
        );
    }
}