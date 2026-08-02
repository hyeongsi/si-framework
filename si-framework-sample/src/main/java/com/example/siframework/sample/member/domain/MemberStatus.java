package com.example.siframework.sample.member.domain;

/**
 * 회원의 현재 업무 상태를 표현한다.
 */
public enum MemberStatus {

    /**
     * 정상적으로 서비스를 사용할 수 있는 상태다.
     */
    ACTIVE,

    /**
     * 서비스 사용이 일시적으로 제한된 상태다.
     */
    SUSPENDED,

    /**
     * 회원이 탈퇴한 상태다.
     */
    WITHDRAWN
}