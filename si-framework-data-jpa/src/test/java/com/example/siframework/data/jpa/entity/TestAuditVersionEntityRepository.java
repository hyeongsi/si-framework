package com.example.siframework.data.jpa.entity;

import com.example.siframework.data.jpa.repository.SiJpaRepository;

/**
 * TestAuditVersionEntity의 영속성 동작을 검증하는
 * 테스트 전용 Repository다.
 *
 * <p>프레임워크의 공통 JPA Repository 계약을 상속한다.</p>
 */
public interface TestAuditVersionEntityRepository
    extends SiJpaRepository<TestAuditVersionEntity, Long> {
}