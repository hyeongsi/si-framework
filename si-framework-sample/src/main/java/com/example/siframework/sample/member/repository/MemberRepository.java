package com.example.siframework.sample.member.repository;

import com.example.siframework.data.jpa.repository.SiJpaRepository;
import com.example.siframework.sample.member.domain.Member;

import java.util.Optional;

/**
 * 회원 엔티티의 영속성 처리를 담당하는 Repository다.
 *
 * <p>프레임워크의 {@link SiJpaRepository}를 상속하여
 * Spring Data JPA 기본 기능과 공통 필수 조회 기능을 사용한다.</p>
 */
public interface MemberRepository
    extends SiJpaRepository<Member, Long> {

    /**
     * 지정한 로그인 ID를 사용하는 회원이 존재하는지 확인한다.
     *
     * <p>회원 등록 전 중복 검증에 사용한다.</p>
     *
     * @param loginId 확인할 로그인 ID
     * @return 해당 로그인 ID의 회원이 존재하면 true
     */
    boolean existsByLoginId(String loginId);

    /**
     * 로그인 ID로 회원을 조회한다.
     *
     * @param loginId 조회할 로그인 ID
     * @return 조회된 회원
     */
    Optional<Member> findByLoginId(String loginId);
}