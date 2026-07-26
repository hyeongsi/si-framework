package com.example.siframework.data.jpa.entity;

import com.example.siframework.data.jpa.support.AdjustableClock;
import com.example.siframework.data.jpa.support.AdjustableCurrentAuditorProvider;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BaseAuditEntity의 시간 및 사용자 감사 동작을 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
@Transactional
class BaseAuditEntityTest {

    /**
     * 각 테스트가 시작할 기준 UTC 시각이다.
     */
    private static final Instant INITIAL_INSTANT =
        Instant.parse("2026-01-01T01:00:00Z");

    /**
     * 테스트 시간대다.
     */
    private static final ZoneId TEST_ZONE =
        ZoneId.of("Asia/Seoul");

    /**
     * 최초 저장 사용자다.
     */
    private static final String CREATE_USER =
        "create-user";

    /**
     * 수정 사용자다.
     */
    private static final String MODIFY_USER =
        "modify-user";

    @Autowired
    private TestAuditEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AdjustableClock clock;

    @Autowired
    private AdjustableCurrentAuditorProvider
        currentAuditorProvider;

    /**
     * 각 테스트의 시간과 감사 사용자를 초기화한다.
     */
    @BeforeEach
    void setUp() {
        clock.setInstant(INITIAL_INSTANT);

        currentAuditorProvider.changeAuditor(
            CREATE_USER
        );
    }

    @Test
    void 엔티티를_저장하면_시간과_감사_사용자가_설정된다() {
        // given
        TestAuditEntity entity =
            new TestAuditEntity("최초 이름");

        LocalDateTime expectedDateTime =
            LocalDateTime.ofInstant(
                INITIAL_INSTANT,
                TEST_ZONE
            );

        // when
        TestAuditEntity savedEntity =
            repository.save(entity);

        entityManager.flush();

        // then
        assertEquals(
            expectedDateTime,
            savedEntity.createdAt()
        );

        assertEquals(
            expectedDateTime,
            savedEntity.modifiedAt()
        );

        assertEquals(
            CREATE_USER,
            savedEntity.createdBy()
        );

        assertEquals(
            CREATE_USER,
            savedEntity.modifiedBy()
        );
    }

    @Test
    void 엔티티를_수정하면_생성_정보는_유지되고_수정_정보만_변경된다() {
        // given
        TestAuditEntity entity =
            repository.save(
                new TestAuditEntity("최초 이름")
            );

        entityManager.flush();

        Long entityId = entity.id();

        LocalDateTime originalCreatedAt =
            entity.createdAt();

        String originalCreatedBy =
            entity.createdBy();

        clock.advance(Duration.ofHours(1));

        currentAuditorProvider.changeAuditor(
            MODIFY_USER
        );

        entityManager.clear();

        TestAuditEntity foundEntity =
            repository.findById(entityId)
                .orElseThrow();

        // when
        foundEntity.changeName("변경된 이름");

        entityManager.flush();

        // then
        LocalDateTime expectedModifiedAt =
            LocalDateTime.ofInstant(
                INITIAL_INSTANT.plus(
                    Duration.ofHours(1)
                ),
                TEST_ZONE
            );

        assertEquals(
            originalCreatedAt,
            foundEntity.createdAt()
        );

        assertEquals(
            originalCreatedBy,
            foundEntity.createdBy()
        );

        assertEquals(
            expectedModifiedAt,
            foundEntity.modifiedAt()
        );

        assertEquals(
            MODIFY_USER,
            foundEntity.modifiedBy()
        );
    }
}