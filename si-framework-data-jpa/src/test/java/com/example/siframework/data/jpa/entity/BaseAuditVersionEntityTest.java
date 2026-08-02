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
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * BaseAuditVersionEntity의 감사 정보와
 * 낙관적 잠금 버전 동작을 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
@Transactional
class BaseAuditVersionEntityTest {

    private static final Instant INITIAL_INSTANT =
        Instant.parse("2026-01-01T01:00:00Z");

    private static final ZoneId TEST_ZONE =
        ZoneId.of("Asia/Seoul");

    private static final String CREATE_USER =
        "create-user";

    private static final String MODIFY_USER =
        "modify-user";

    @Autowired
    private TestAuditVersionEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AdjustableClock clock;

    @Autowired
    private AdjustableCurrentAuditorProvider
        currentAuditorProvider;

    /**
     * 각 테스트에서 사용할 시간과 감사 사용자를 초기화한다.
     */
    @BeforeEach
    void setUp() {
        clock.setInstant(INITIAL_INSTANT);
        currentAuditorProvider.changeAuditor(CREATE_USER);
    }

    @Test
    void 엔티티를_저장하면_감사_정보와_버전이_설정된다() {
        // given
        TestAuditVersionEntity entity =
            new TestAuditVersionEntity("최초 이름");

        LocalDateTime expectedDateTime =
            LocalDateTime.ofInstant(
                INITIAL_INSTANT,
                TEST_ZONE
            );

        // when
        TestAuditVersionEntity savedEntity =
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

        assertNotNull(savedEntity.version());
    }

    @Test
    void 엔티티를_수정하면_수정_감사_정보와_버전이_변경된다() {
        // given
        TestAuditVersionEntity entity =
            repository.save(
                new TestAuditVersionEntity("최초 이름")
            );

        entityManager.flush();

        Long entityId = entity.id();
        Long originalVersion = entity.version();

        LocalDateTime originalCreatedAt =
            entity.createdAt();

        String originalCreatedBy =
            entity.createdBy();

        clock.advance(Duration.ofHours(1));
        currentAuditorProvider.changeAuditor(MODIFY_USER);

        entityManager.clear();

        TestAuditVersionEntity foundEntity =
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

        assertEquals(
            originalVersion + 1L,
            foundEntity.version()
        );
    }
}