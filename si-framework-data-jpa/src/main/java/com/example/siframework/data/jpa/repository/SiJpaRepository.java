package com.example.siframework.data.jpa.repository;

import com.example.siframework.core.error.ErrorCode;
import com.example.siframework.core.exception.BusinessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Objects;

/**
 * SI 공통 프레임워크에서 사용하는 JPA Repository의
 * 최상위 공통 계약이다.
 *
 * <p>프로젝트의 개별 Repository는 Spring Data JPA의
 * {@link JpaRepository}를 직접 상속하는 대신
 * 이 인터페이스를 상속하는 것을 기본 원칙으로 한다.</p>
 *
 * <p>식별자 기반 필수 조회처럼 여러 Repository에서 반복되는
 * 공통 데이터 접근 기능을 제공한다.</p>
 *
 * <p>이 인터페이스 자체는 실제 Repository Bean으로 생성되지 않는다.</p>
 *
 * @param <T> Repository가 관리하는 엔티티 타입
 * @param <ID> 엔티티 식별자 타입
 */
@NoRepositoryBean
public interface SiJpaRepository<T, ID>
    extends JpaRepository<T, ID> {

    /**
     * 식별자로 엔티티를 조회하고, 존재하지 않으면
     * 지정된 오류 코드의 업무 예외를 발생시킨다.
     *
     * <p>예외 메시지는 오류 코드에 정의된 기본 메시지를 사용한다.</p>
     *
     * @param id 조회할 엔티티 식별자
     * @param errorCode 엔티티가 존재하지 않을 때 사용할 오류 코드
     * @return 조회된 엔티티
     * @throws BusinessException 엔티티가 존재하지 않는 경우
     */
    default T findByIdOrThrow(
        ID id,
        ErrorCode errorCode
    ) {
        Objects.requireNonNull(
            errorCode,
            "오류 코드는 null일 수 없습니다."
        );

        return findById(id)
            .orElseThrow(() ->
                new BusinessException(errorCode)
            );
    }

    /**
     * 식별자로 엔티티를 조회하고, 존재하지 않으면
     * 상세 메시지를 포함한 업무 예외를 발생시킨다.
     *
     * <p>상세 메시지가 null이거나 공백이면
     * 오류 코드의 기본 메시지가 사용된다.</p>
     *
     * @param id 조회할 엔티티 식별자
     * @param errorCode 엔티티가 존재하지 않을 때 사용할 오류 코드
     * @param detailMessage 예외에 포함할 구체적인 메시지
     * @return 조회된 엔티티
     * @throws BusinessException 엔티티가 존재하지 않는 경우
     */
    default T findByIdOrThrow(
        ID id,
        ErrorCode errorCode,
        String detailMessage
    ) {
        Objects.requireNonNull(
            errorCode,
            "오류 코드는 null일 수 없습니다."
        );

        return findById(id)
            .orElseThrow(() ->
                new BusinessException(
                    errorCode,
                    detailMessage
                )
            );
    }
}