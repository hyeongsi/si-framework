package com.example.siframework.data.jpa.entity;

import com.example.siframework.core.error.CommonErrorCode;
import com.example.siframework.core.exception.BusinessException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * SiJpaRepository의 공통 조회 기능을 검증한다.
 */
@SpringBootTest(classes = JpaAuditingTestConfiguration.class)
@Transactional
class SiJpaRepositoryIntegrationTest {

    @Autowired
    private TestAuditVersionEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 식별자로_존재하는_엔티티를_조회한다() {
        // given
        TestAuditVersionEntity savedEntity =
            repository.save(
                new TestAuditVersionEntity(
                    "공통 Repository"
                )
            );

        entityManager.flush();
        entityManager.clear();

        // when
        TestAuditVersionEntity foundEntity =
            repository.findByIdOrThrow(
                savedEntity.id(),
                CommonErrorCode.RESOURCE_NOT_FOUND
            );

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
    void 엔티티가_존재하지_않으면_기본_메시지의_업무_예외가_발생한다() {
        // given
        Long notExistingId = Long.MAX_VALUE;

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> repository.findByIdOrThrow(
                    notExistingId,
                    CommonErrorCode
                        .RESOURCE_NOT_FOUND
                )
            );

        // then
        assertSame(
            CommonErrorCode.RESOURCE_NOT_FOUND,
            exception.errorCode()
        );

        assertEquals(
            "COMMON-004",
            exception.code()
        );

        assertEquals(
            CommonErrorCode.RESOURCE_NOT_FOUND.message(),
            exception.getMessage()
        );
    }

    @Test
    void 엔티티가_존재하지_않으면_상세_메시지의_업무_예외가_발생한다() {
        // given
        Long notExistingId = Long.MAX_VALUE;

        String detailMessage =
            "테스트 엔티티를 찾을 수 없습니다. ID: "
                + notExistingId;

        // when
        BusinessException exception =
            assertThrows(
                BusinessException.class,
                () -> repository.findByIdOrThrow(
                    notExistingId,
                    CommonErrorCode
                        .RESOURCE_NOT_FOUND,
                    detailMessage
                )
            );

        // then
        assertSame(
            CommonErrorCode.RESOURCE_NOT_FOUND,
            exception.errorCode()
        );

        assertEquals(
            detailMessage,
            exception.getMessage()
        );

        assertEquals(
            CommonErrorCode.RESOURCE_NOT_FOUND.message(),
            exception.defaultMessage()
        );
    }

    @Test
    void 오류_코드가_null이면_조회에_실패한다() {
        // given
        TestAuditVersionEntity savedEntity =
            repository.save(
                new TestAuditVersionEntity(
                    "오류 코드 검증"
                )
            );

        entityManager.flush();

        // when
        NullPointerException exception =
            assertThrows(
                NullPointerException.class,
                () -> repository.findByIdOrThrow(
                    savedEntity.id(),
                    null
                )
            );

        // then
        assertEquals(
            "오류 코드는 null일 수 없습니다.",
            exception.getMessage()
        );
    }
}