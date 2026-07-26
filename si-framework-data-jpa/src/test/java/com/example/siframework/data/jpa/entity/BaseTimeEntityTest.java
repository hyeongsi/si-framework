package com.example.siframework.data.jpa.entity;

import com.example.siframework.data.jpa.support.AdjustableClock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseTimeEntity의 JPA 감사 동작을 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
@Transactional
class BaseTimeEntityTest {

    /**
     * 테스트에서 사용하는 초기 UTC 시각이다.
     */
    private static final Instant INITIAL_INSTANT =
        Instant.parse("2026-01-01T01:00:00Z");

    /**
     * 테스트에서 사용하는 시간대다.
     */
    private static final ZoneId TEST_ZONE =
        ZoneId.of("Asia/Seoul");


    /**
     * 테스트 엔티티 저장과 조회에 사용한다.
     */
    @Autowired
    private TestEntityRepository repository;

    /**
     * 영속성 컨텍스트의 flush와 clear를 제어하기 위해 사용한다.
     */
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AdjustableClock clock;

    /**
     * 각 테스트가 동일한 시각에서 시작하도록 초기화한다.
     */
    @BeforeEach
    void setUp() {
        clock.setInstant(INITIAL_INSTANT);
    }

    @Test
    void 엔티티를_저장하면_생성_일시와_수정_일시가_설정된다() {
        //given
        TestEntity entity = new TestEntity("최초 이름");

        //when
        TestEntity savedEntity = repository.save(entity);
        entityManager.flush();

        //then
        LocalDateTime expectedDateTime =
            LocalDateTime.ofInstant(
                INITIAL_INSTANT,
                TEST_ZONE
            );

        assertNotNull(savedEntity.createdAt());
        assertNotNull(savedEntity.modifiedAt());

        assertEquals(
            expectedDateTime,
            savedEntity.modifiedAt()
        );

        assertEquals(
            expectedDateTime,
            savedEntity.modifiedAt()
        );
    }

    @Test
    void 엔티티를_수정하면_수정_일시만_변경된다()
        throws InterruptedException {

        //given
        TestEntity entity = repository.save(
            new TestEntity("최초 이름")
        );

        entityManager.flush();

        Long entityId = entity.id();
        LocalDateTime originalCreatedAt = entity.createdAt();
        LocalDateTime originalModifiedAt = entity.modifiedAt();

        clock.advance(Duration.ofHours(1));

        // 영속성 컨텍스트를 초기화해 DB에서 다시 조회한다.
        entityManager.clear();

        TestEntity foundEntity = repository.findById(entityId)
            .orElseThrow();

        //when
        foundEntity.changeName("변경된 이름");
        entityManager.flush();

        //then
        LocalDateTime expectedModifiedAt =
            LocalDateTime.ofInstant(
                INITIAL_INSTANT.plus(Duration.ofHours(1)),
                TEST_ZONE
            );

        assertEquals(
            originalCreatedAt,
            foundEntity.createdAt()
        );

        assertEquals(
            expectedModifiedAt,
            foundEntity.modifiedAt()
        );

        assertTrue(
            foundEntity.modifiedAt()
                .isAfter(originalModifiedAt)
        );
    }
}