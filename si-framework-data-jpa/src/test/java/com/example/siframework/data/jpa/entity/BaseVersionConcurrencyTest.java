package com.example.siframework.data.jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseVersionEntity의 버전 생성 및 증가 동작을 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
class BaseVersionConcurrencyTest {

    @Autowired
    private TestVersionEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void 동일한_버전으로_조회한_엔티티의_나중_수정은_실패한다() {
        // given
        TransactionTemplate transactionTemplate =
            new TransactionTemplate(transactionManager);

        Long entityId = transactionTemplate.execute(status -> {
            TestVersionEntity entity =
                repository.save(
                    new TestVersionEntity("최초 이름")
                );

            entityManager.flush();

            return entity.id();
        });

        assertNotNull(entityId);

        TestVersionEntity firstEntity =
            transactionTemplate.execute(status ->
                repository.findById(entityId)
                    .orElseThrow()
            );

        TestVersionEntity secondEntity =
            transactionTemplate.execute(status ->
                repository.findById(entityId)
                    .orElseThrow()
            );

        assertNotNull(firstEntity);
        assertNotNull(secondEntity);

        assertEquals(
            firstEntity.version(),
            secondEntity.version()
        );

        // 첫 번째 객체의 변경을 먼저 반영한다.
        transactionTemplate.executeWithoutResult(status -> {
            TestVersionEntity managedEntity =
                entityManager.merge(firstEntity);

            managedEntity.changeName("첫 번째 변경");
            entityManager.flush();
        });

        // when
        assertThrows(
            OptimisticLockException.class,
            () -> transactionTemplate.executeWithoutResult(
                status -> {
                    TestVersionEntity managedEntity =
                        entityManager.merge(
                            secondEntity
                        );

                    managedEntity.changeName(
                        "두 번째 변경"
                    );

                    entityManager.flush();
                }
            )
        );
    }
}