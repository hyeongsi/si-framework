package com.example.siframework.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * SI 공통 프레임워크에서 사용하는 JPA Repository의
 * 최상위 공통 계약이다.
 *
 * <p>프로젝트의 개별 Repository는 Spring Data JPA의
 * {@link JpaRepository}를 직접 상속하는 대신
 * 이 인터페이스를 상속하는 것을 기본 원칙으로 한다.</p>
 *
 * <p>현재는 별도의 공통 메서드를 선언하지 않지만,
 * 향후 다음과 같은 프레임워크 공통 기능의 확장 지점으로 사용한다.</p>
 *
 * <ul>
 *     <li>식별자 기반 필수 조회</li>
 *     <li>공통 예외 변환</li>
 *     <li>공통 Repository 구현체 적용</li>
 *     <li>Repository 기능 정책 통일</li>
 * </ul>
 *
 * <p>이 인터페이스 자체는 실제 Repository Bean으로
 * 생성되지 않는다.</p>
 *
 * @param <T> Repository가 관리하는 엔티티 타입
 * @param <ID> 엔티티 식별자 타입
 */
@NoRepositoryBean
public interface SiJpaRepository<T, ID>
    extends JpaRepository<T, ID> {
}