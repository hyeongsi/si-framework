package com.example.siframework.sample.member.domain;

import com.example.siframework.core.exception.BusinessException;
import com.example.siframework.data.jpa.entity.BaseAuditVersionEntity;
import com.example.siframework.sample.member.error.MemberErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Objects;

/**
 * sample 애플리케이션의 회원 엔티티다.
 *
 * <p>회원의 로그인 ID, 이름, 상태를 관리하며
 * 생성·수정 감사 정보와 낙관적 잠금 버전을 함께 사용한다.</p>
 */
@Entity
@Table(
    name = "member",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_member_login_id",
            columnNames = "login_id"
        )
    }
)
public class Member extends BaseAuditVersionEntity {

    /**
     * 회원 식별자다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 회원 로그인 ID다.
     */
    @Column(
        name = "login_id",
        nullable = false,
        length = 50
    )
    private String loginId;

    /**
     * 회원 이름이다.
     */
    @Column(
        name = "name",
        nullable = false,
        length = 100
    )
    private String name;

    /**
     * 회원의 현재 상태다.
     */
    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        length = 20
    )
    private MemberStatus status;

    /**
     * JPA가 사용하는 기본 생성자다.
     */
    protected Member() {
    }

    /**
     * 신규 회원을 생성한다.
     *
     * <p>신규 회원은 항상 활성 상태로 생성된다.</p>
     *
     * @param loginId 로그인 ID
     * @param name 회원 이름
     */
    public Member(
        String loginId,
        String name
    ) {
        this.loginId = requireText(
            loginId,
            "로그인 ID는 비어 있을 수 없습니다."
        );

        this.name = requireText(
            name,
            "회원 이름은 비어 있을 수 없습니다."
        );

        this.status = MemberStatus.ACTIVE;
    }

    /**
     * 회원 식별자를 반환한다.
     *
     * @return 회원 식별자
     */
    public Long id() {
        return id;
    }

    /**
     * 로그인 ID를 반환한다.
     *
     * @return 로그인 ID
     */
    public String loginId() {
        return loginId;
    }

    /**
     * 회원 이름을 반환한다.
     *
     * @return 회원 이름
     */
    public String name() {
        return name;
    }

    /**
     * 회원 상태를 반환한다.
     *
     * @return 회원 상태
     */
    public MemberStatus status() {
        return status;
    }

    /**
     * 회원 이름을 변경한다.
     *
     * <p>탈퇴한 회원은 이름을 변경할 수 없다.</p>
     *
     * @param name 변경할 회원 이름
     * @throws BusinessException 탈퇴 회원인 경우
     */
    public void changeName(String name) {
        validateNotWithdrawn();

        this.name = requireText(
            name,
            "회원 이름은 비어 있을 수 없습니다."
        );
    }

    /**
     * 회원을 정지 상태로 변경한다.
     *
     * <p>활성 회원만 정지할 수 있다.</p>
     *
     * @throws BusinessException 현재 상태에서 정지할 수 없는 경우
     */
    public void suspend() {
        if (status != MemberStatus.ACTIVE) {
            throw operationNotAllowed(
                "활성 회원만 정지할 수 있습니다."
            );
        }

        status = MemberStatus.SUSPENDED;
    }

    /**
     * 정지된 회원을 다시 활성 상태로 변경한다.
     *
     * @throws BusinessException 현재 상태에서 활성화할 수 없는 경우
     */
    public void activate() {
        if (status != MemberStatus.SUSPENDED) {
            throw operationNotAllowed(
                "정지 회원만 다시 활성화할 수 있습니다."
            );
        }

        status = MemberStatus.ACTIVE;
    }

    /**
     * 회원을 탈퇴 상태로 변경한다.
     *
     * <p>이미 탈퇴한 회원은 다시 탈퇴 처리할 수 없다.</p>
     *
     * @throws BusinessException 이미 탈퇴한 경우
     */
    public void withdraw() {
        if (status == MemberStatus.WITHDRAWN) {
            throw operationNotAllowed(
                "이미 탈퇴한 회원입니다."
            );
        }

        status = MemberStatus.WITHDRAWN;
    }

    /**
     * 탈퇴 회원인지 검증한다.
     */
    private void validateNotWithdrawn() {
        if (status == MemberStatus.WITHDRAWN) {
            throw operationNotAllowed(
                "탈퇴한 회원은 정보를 변경할 수 없습니다."
            );
        }
    }

    /**
     * 허용되지 않은 회원 작업 예외를 생성한다.
     *
     * @param detailMessage 상세 메시지
     * @return 업무 예외
     */
    private BusinessException operationNotAllowed(
        String detailMessage
    ) {
        return new BusinessException(
            MemberErrorCode.MEMBER_OPERATION_NOT_ALLOWED,
            detailMessage
        );
    }

    /**
     * 문자열이 null이거나 공백인지 검증한다.
     *
     * @param value 검증할 값
     * @param message 검증 실패 메시지
     * @return 검증된 문자열
     */
    private static String requireText(
        String value,
        String message
    ) {
        String requiredValue =
            Objects.requireNonNull(value, message);

        if (requiredValue.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return requiredValue;
    }
}