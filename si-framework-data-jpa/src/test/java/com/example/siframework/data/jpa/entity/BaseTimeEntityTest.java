package com.example.siframework.data.jpa.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BaseTimeEntity의 JPA 감사 동작을 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
@Transactional
class BaseTimeEntityTest {

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

    @Test
    void 엔티티를_저장하면_생성_일시와_수정_일시가_설정된다() {
        //given
        TestEntity entity = new TestEntity("최초 이름");

        //when
        TestEntity savedEntity = repository.save(entity);
        entityManager.flush();

        //then
        assertNotNull(savedEntity.createdAt());
        assertNotNull(savedEntity.modifiedAt());

        assertEquals(
            savedEntity.createdAt(),
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

        /*
         * 첫 저장과 수정 시각이 동일한 시스템 시각 단위로
         * 기록되는 상황을 피하기 위한 테스트 대기다.
         *
         * 향후 DateTimeProvider를 도입하면 이 대기는 제거한다.
         */
        Thread.sleep(10);

        // 영속성 컨텍스트를 초기화해 DB에서 다시 조회한다.
        entityManager.clear();

        TestEntity foundEntity = repository.findById(entityId)
            .orElseThrow();

        //when
        foundEntity.changeName("변경된 이름");
        entityManager.flush();

        //then
        /*
         * H2의 TIMESTAMP 정밀도 지정하지 않으면 기본 6자리
         * 반면에 LocalDateTime은 9자리라 6자리로 정밀도 조정
         */
        assertEquals(
            originalCreatedAt.plusNanos(500).truncatedTo(ChronoUnit.MICROS),
            foundEntity.createdAt().truncatedTo(ChronoUnit.MICROS)
        );

        assertTrue(
            foundEntity.modifiedAt()
                .isAfter(originalModifiedAt)
        );
    }
}