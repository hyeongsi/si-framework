package com.example.siframework.data.jpa.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/**
 * 엔티티의 낙관적 잠금 버전을 공통으로 관리하는 상위 클래스다.
 *
 * <p>동일한 엔티티를 여러 트랜잭션이 동시에 수정하는 경우
 * 먼저 반영된 변경을 이후 요청이 덮어쓰지 않도록 충돌을 감지한다.</p>
 *
 * <p>이 클래스는 자체 테이블을 갖지 않으며,
 * 상속한 실제 엔티티의 테이블에 version 컬럼이 포함된다.</p>
 *
 * <p>버전 값은 JPA 영속성 제공자가 관리하므로
 * 애플리케이션 코드에서 직접 변경하면 안 된다.</p>
 */
@MappedSuperclass
public abstract class BaseVersionEntity {

    /**
     * 엔티티의 낙관적 잠금 버전이다.
     *
     * <p>엔티티가 수정될 때마다 JPA가 값을 증가시키며,
     * UPDATE 또는 DELETE 시 기존 버전과 일치하는지 확인한다.</p>
     */
    @Version
    private Long version;

    /**
     * 현재 낙관적 잠금 버전을 반환한다.
     *
     * <p>아직 영속화되지 않은 엔티티에서는 null일 수 있다.</p>
     *
     * @return 현재 버전
     */
    public final Long version() {
        return version;
    }
}