package com.example.siframework.data.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TestEntity의 저장과 조회를 수행하는 테스트 전용 Repository다.
 */
interface TestEntityRepository
    extends JpaRepository<TestEntity, Long> {
}