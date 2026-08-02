package com.example.siframework.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/**
 * 엔티티의 생성·수정 감사 정보와 낙관적 잠금 버전을
 * 함께 관리하는 공통 상위 클래스다.
 *
 * <p>이 클래스를 상속한 엔티티는 다음 정보를 갖는다.</p>
 *
 * <ul>
 *     <li>생성 일시</li>
 *     <li>최종 수정 일시</li>
 *     <li>생성 사용자</li>
 *     <li>최종 수정 사용자</li>
 *     <li>낙관적 잠금 버전</li>
 * </ul>
 *
 * <p>이 클래스는 자체 테이블을 갖지 않으며,
 * 상속한 실제 엔티티의 테이블에 감사 컬럼과
 * 버전 컬럼이 포함된다.</p>
 */
@MappedSuperclass
public abstract class BaseAuditVersionEntity
    extends BaseAuditEntity {

    /**
     * 엔티티의 낙관적 잠금 버전이다.
     *
     * <p>엔티티가 수정될 때마다 JPA가 값을 증가시키며,
     * 애플리케이션에서 직접 변경해서는 안 된다.</p>
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    /**
     * 현재 낙관적 잠금 버전을 반환한다.
     *
     * <p>아직 영속화되지 않은 신규 엔티티에서는
     * null일 수 있다.</p>
     *
     * @return 현재 버전
     */
    public final Long version() {
        return version;
    }
}