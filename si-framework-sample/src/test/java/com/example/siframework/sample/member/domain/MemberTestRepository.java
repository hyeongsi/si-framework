package com.example.siframework.sample.member.domain;

import com.example.siframework.data.jpa.repository.SiJpaRepository;

/**
 * Member 엔티티의 JPA 매핑을 검증하기 위한
 * 테스트 전용 Repository다.
 */
interface MemberTestRepository
    extends SiJpaRepository<Member, Long> {
}