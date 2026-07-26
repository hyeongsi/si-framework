package com.example.siframework.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * BaseTimeEntity의 동작을 검증하기 위한 테스트 전용 엔티티다.
 *
 * <p>테스트 소스에만 존재하므로 운영 JAR에는 포함되지 않는다.</p>
 */
@Entity
@Table(name = "test_entity")
class TestEntity extends BaseTimeEntity {

    /**
     * 테스트 엔티티 식별자다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 수정 감사를 검증하기 위한 테스트 값이다.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * JPA가 사용하는 기본 생성자다.
     */
    protected TestEntity() {
    }

    /**
     * 테스트 엔티티를 생성한다.
     *
     * @param name 테스트 이름
     */
    TestEntity(String name) {
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