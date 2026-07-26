package com.example.siframework.data.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TestAuditEntity의 영속성 동작을 검증하는
 * 테스트 전용 Repository다.
 */
interface TestAuditEntityRepository
    extends JpaRepository<TestAuditEntity, Long> {
}