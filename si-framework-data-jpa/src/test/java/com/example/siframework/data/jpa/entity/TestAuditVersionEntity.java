package com.example.siframework.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * BaseAuditVersionEntity의 감사 및 낙관적 잠금 동작을
 * 검증하기 위한 테스트 전용 엔티티다.
 */
@Entity
@Table(name = "test_audit_version_entity")
public class TestAuditVersionEntity
    extends BaseAuditVersionEntity {

    /**
     * 테스트 엔티티 식별자다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 수정과 버전 증가를 발생시키기 위한 테스트 값이다.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * JPA가 사용하는 기본 생성자다.
     */
    protected TestAuditVersionEntity() {
    }

    /**
     * 테스트 엔티티를 생성한다.
     *
     * @param name 테스트 이름
     */
    public TestAuditVersionEntity(String name) {
        this.name = name;
    }

    /**
     * 식별자를 반환한다.
     *
     * @return 엔티티 식별자
     */
    public Long id() {
        return id;
    }

    /**
     * 이름을 반환한다.
     *
     * @return 테스트 이름
     */
    public String name() {
        return name;
    }

    /**
     * 이름을 변경한다.
     *
     * @param name 변경할 이름
     */
    void changeName(String name) {
        this.name = name;
    }
}