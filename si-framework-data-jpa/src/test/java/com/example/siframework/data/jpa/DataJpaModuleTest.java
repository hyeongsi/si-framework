package com.example.siframework.data.jpa;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * data-jpa 모듈의 기본 의존성 구성을 검증한다.
 *
 * <p>실제 데이터베이스 동작을 검증하는 테스트가 아니라,
 * Jakarta Persistence와 Spring Data JPA 타입을
 * 정상적으로 참조할 수 있는지 확인하는 구성 테스트다.</p>
 */
class DataJpaModuleTest {

    /**
     * Jakarta Persistence API가
     * data-jpa 모듈의 컴파일 경로에 존재하는지 확인한다.
     */
    @Test
    void Jakarta_Persistence_API를_참조할_수_있다() {
        Class<EntityManager> entityManagerType =
            EntityManager.class;

        assertNotNull(entityManagerType);
    }

    /**
     * Spring Data JPA API가
     * data-jpa 모듈의 컴파일 경로에 존재하는지 확인한다.
     */
    @Test
    void Spring_Data_JPA_API를_참조할_수_있다() {
        Class<JpaRepository> repositoryType =
            JpaRepository.class;

        assertNotNull(repositoryType);
    }
}