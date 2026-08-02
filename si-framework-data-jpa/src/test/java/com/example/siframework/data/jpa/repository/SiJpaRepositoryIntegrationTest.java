package com.example.siframework.data.jpa.repository;

import com.example.siframework.data.jpa.entity.JpaAuditingTestConfiguration;
import com.example.siframework.data.jpa.entity.TestAuditVersionEntity;
import com.example.siframework.data.jpa.entity.TestAuditVersionEntityRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SiJpaRepository를 상속한 Repository가
 * Spring Data JPA 기본 기능을 정상적으로 제공하는지 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
@Transactional
class SiJpaRepositoryIntegrationTest {

    @Autowired
    private TestAuditVersionEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 공통_Repository로_엔티티를_저장하고_조회한다() {
        // given
        TestAuditVersionEntity entity =
            new TestAuditVersionEntity("공통 Repository");

        // when
        TestAuditVersionEntity savedEntity =
            repository.save(entity);

        entityManager.flush();
        entityManager.clear();

        TestAuditVersionEntity foundEntity =
            repository.findById(savedEntity.id())
                .orElseThrow();

        // then
        assertEquals(
            savedEntity.id(),
            foundEntity.id()
        );

        assertEquals(
            "공통 Repository",
            foundEntity.name()
        );
    }

    @Test
    void 공통_Repository로_엔티티_존재_여부를_확인한다() {
        // given
        TestAuditVersionEntity savedEntity =
            repository.save(
                new TestAuditVersionEntity("존재 여부")
            );

        entityManager.flush();

        // when
        boolean exists =
            repository.existsById(savedEntity.id());

        // then
        assertTrue(exists);
    }
}