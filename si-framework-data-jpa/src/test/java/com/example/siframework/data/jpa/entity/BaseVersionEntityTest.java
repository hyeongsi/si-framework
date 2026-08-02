package com.example.siframework.data.jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseVersionEntity의 버전 생성 및 증가 동작을 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
@Transactional
class BaseVersionEntityTest {

    @Autowired
    private TestVersionEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void 엔티티를_저장하면_버전이_설정된다() {
        //given
        TestVersionEntity entity =
            new TestVersionEntity("최초 이름");

        //when
        TestVersionEntity savedEntity =
            repository.save(entity);

        entityManager.flush();

        //then
        assertNotNull(savedEntity.version());
        assertTrue(savedEntity.version() >= 0L);
    }

    @Test
    void 엔티티를_수정하면_버전이_증가한다() {
        //given
        TestVersionEntity entity =
            repository.save(
                new TestVersionEntity("최초 이름")
            );

        entityManager.flush();

        Long entityId = entity.id();
        Long originalVersion = entity.version();

        entityManager.clear();

        TestVersionEntity foundEntity =
            repository.findById(entityId)
                .orElseThrow();

        //when
        foundEntity.changeName("변경된 이름");
        entityManager.flush();

        //then
        assertEquals(
            originalVersion + 1L,
            foundEntity.version()
        );
    }
}