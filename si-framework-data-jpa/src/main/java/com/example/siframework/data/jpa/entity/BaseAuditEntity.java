package com.example.siframework.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * 엔티티의 생성·수정 시간과 생성·수정 사용자를
 * 공통으로 관리하는 상위 클래스다.
 *
 * <p>이 클래스를 상속한 엔티티는 다음 감사 정보를 갖는다.</p>
 *
 * <ul>
 *     <li>생성 일시: {@code createdAt}</li>
 *     <li>최종 수정 일시: {@code modifiedAt}</li>
 *     <li>생성 사용자: {@code createdBy}</li>
 *     <li>최종 수정 사용자: {@code modifiedBy}</li>
 * </ul>
 *
 * <p>Spring Data JPA 감사 기능이 활성화되어 있고
 * 현재 감사 사용자를 제공하는 AuditorAware가 등록된 환경에서
 * 저장 및 수정 시 감사 정보가 자동으로 설정된다.</p>
 *
 * <p>이 클래스는 자체 테이블을 갖지 않으며,
 * 상속한 실제 엔티티의 테이블에 컬럼이 포함된다.</p>
 */
@MappedSuperclass
public abstract class BaseAuditEntity extends BaseTimeEntity {

    /**
     * 엔티티를 최초로 생성한 감사 주체의 식별자다.
     *
     * <p>최초 저장 이후에는 변경되지 않는다.</p>
     */
    @CreatedBy
    @Column(
        name = "created_by",
        nullable = false,
        updatable = false,
        length = 100
    )
    private String createdBy;

    /**
     * 엔티티를 마지막으로 수정한 감사 주체의 식별자다.
     *
     * <p>최초 저장 시에도 값이 설정되며,
     * 이후 엔티티가 수정될 때 갱신된다.</p>
     */
    @LastModifiedBy
    @Column(
        name = "modified_by",
        nullable = false,
        length = 100
    )
    private String modifiedBy;

    /**
     * 엔티티 생성 사용자의 식별자를 반환한다.
     *
     * @return 생성 사용자 식별자
     */
    public final String createdBy() {
        return createdBy;
    }

    /**
     * 엔티티 최종 수정 사용자의 식별자를 반환한다.
     *
     * @return 최종 수정 사용자 식별자
     */
    public final String modifiedBy() {
        return modifiedBy;
    }
}