package com.example.siframework.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * BaseAuditEntity의 동작을 검증하기 위한 테스트 전용 엔티티다.
 */
@Entity
@Table(name = "test_audit_entity")
class TestAuditEntity extends BaseAuditEntity {

    /**
     * 테스트 엔티티 식별자다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 수정 감사 동작을 발생시키기 위한 테스트 값이다.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * JPA가 사용하는 기본 생성자다.
     */
    protected TestAuditEntity() {
    }

    /**
     * 테스트 엔티티를 생성한다.
     *
     * @param name 테스트 이름
     */
    TestAuditEntity(String name) {
        this.name = name;
    }

    /**
     * 식별자를 반환한다.
     *
     * @return 엔티티 식별자
     */
    Long id() {
        return id;
    }

    /**
     * 테스트 이름을 변경한다.
     *
     * @param name 변경할 이름
     */
    void changeName(String name) {
        this.name = name;
    }
}