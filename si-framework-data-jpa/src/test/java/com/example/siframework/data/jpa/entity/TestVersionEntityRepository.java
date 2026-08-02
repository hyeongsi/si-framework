package com.example.siframework.data.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TestVersionEntity의 영속성 동작을 검증하는
 * 테스트 전용 Repository다.
 */
interface TestVersionEntityRepository
    extends JpaRepository<TestVersionEntity, Long> {
}