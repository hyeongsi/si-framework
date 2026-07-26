package com.example.siframework.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 엔티티의 생성 일시와 최종 수정 일시를 공통으로 관리하는 상위 클래스다.
 *
 * <p>이 클래스를 상속한 엔티티는 다음 필드를 공통으로 갖는다.</p>
 *
 * <ul>
 *     <li>생성 일시: {@code createdAt}</li>
 *     <li>최종 수정 일시: {@code modifiedAt}</li>
 * </ul>
 *
 * <p>Spring Data JPA 감사 기능이 활성화된 환경에서
 * 엔티티 저장과 수정 시점에 값이 자동으로 설정된다.</p>
 *
 * <p>이 클래스는 자체 테이블을 갖지 않으며,
 * 상속한 실제 엔티티의 테이블에 컬럼이 포함된다.</p>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    /**
     * 엔티티가 처음 저장된 일시다.
     *
     * <p>최초 저장 이후에는 변경되지 않는다.</p>
     */
    @CreatedDate
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;

    /**
     * 엔티티가 마지막으로 수정된 일시다.
     *
     * <p>최초 저장 시에도 값이 설정되며,
     * 이후 엔티티가 수정될 때 갱신된다.</p>
     */
    @LastModifiedDate
    @Column(
        name = "modified_at",
        nullable = false
    )
    private LocalDateTime modifiedAt;

    /**
     * 엔티티 생성 일시를 반환한다.
     *
     * @return 생성 일시
     */
    public final LocalDateTime createdAt() {
        return createdAt;
    }

    /**
     * 엔티티 최종 수정 일시를 반환한다.
     *
     * @return 최종 수정 일시
     */
    public final LocalDateTime modifiedAt() {
        return modifiedAt;
    }
}