package com.example.siframework.sample.member.service;

import com.example.siframework.core.exception.BusinessException;
import com.example.siframework.sample.member.domain.Member;
import com.example.siframework.sample.member.error.MemberErrorCode;
import com.example.siframework.sample.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 회원 등록과 조회 업무를 담당하는 서비스다.
 *
 * <p>회원 도메인 객체와 Repository를 조합하고
 * 회원 업무의 트랜잭션 경계를 정의한다.</p>
 */
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 서비스를 생성한다.
     *
     * @param memberRepository 회원 Repository
     */
    public MemberService(
        MemberRepository memberRepository
    ) {
        this.memberRepository = Objects.requireNonNull(
            memberRepository,
            "회원 Repository는 null일 수 없습니다."
        );
    }

    /**
     * 신규 회원을 등록한다.
     *
     * <p>이미 사용 중인 로그인 ID인 경우
     * 회원 중복 업무 예외를 발생시킨다.</p>
     *
     * @param loginId 로그인 ID
     * @param name 회원 이름
     * @return 등록된 회원
     * @throws BusinessException 로그인 ID가 중복된 경우
     */
    @Transactional
    public Member register(
        String loginId,
        String name
    ) {
        validateDuplicateLoginId(loginId);

        Member member = new Member(
            loginId,
            name
        );

        return memberRepository.save(member);
    }

    /**
     * 회원 식별자로 회원을 조회한다.
     *
     * @param memberId 회원 식별자
     * @return 조회된 회원
     * @throws BusinessException 회원이 존재하지 않는 경우
     */
    public Member findById(Long memberId) {
        return memberRepository.findByIdOrThrow(
            memberId,
            MemberErrorCode.MEMBER_NOT_FOUND,
            "회원을 찾을 수 없습니다. memberId="
                + memberId
        );
    }

    /**
     * 로그인 ID로 회원을 조회한다.
     *
     * @param loginId 로그인 ID
     * @return 조회된 회원
     * @throws BusinessException 회원이 존재하지 않는 경우
     */
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
            .orElseThrow(() ->
                new BusinessException(
                    MemberErrorCode.MEMBER_NOT_FOUND,
                    "회원을 찾을 수 없습니다. loginId="
                        + loginId
                )
            );
    }

    /**
     * 로그인 ID의 중복 여부를 검증한다.
     *
     * @param loginId 검증할 로그인 ID
     * @throws BusinessException 이미 사용 중인 경우
     */
    private void validateDuplicateLoginId(
        String loginId
    ) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new BusinessException(
                MemberErrorCode.DUPLICATE_LOGIN_ID,
                "이미 사용 중인 로그인 ID입니다. loginId="
                    + loginId
            );
        }
    }
}