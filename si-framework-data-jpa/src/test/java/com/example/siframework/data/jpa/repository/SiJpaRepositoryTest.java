package com.example.siframework.data.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SiJpaRepositoryTest {

    @Test
    void JpaRepository를_상속한다() {
        // given
        Class<SiJpaRepository> repositoryType =
            SiJpaRepository.class;

        // when
        boolean assignable =
            JpaRepository.class.isAssignableFrom(
                repositoryType
            );

        // then
        assertTrue(assignable);
    }

    @Test
    void NoRepositoryBean이_선언되어_있다() {
        // when
        NoRepositoryBean annotation =
            SiJpaRepository.class.getAnnotation(
                NoRepositoryBean.class
            );

        // then
        assertNotNull(annotation);
        assertInstanceOf(
            NoRepositoryBean.class,
            annotation
        );
    }
}