package com.example.siframework.sample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * sample 애플리케이션의 Spring 컨텍스트 구성을 검증한다.
 */
@SpringBootTest
class SiFrameworkSampleApplicationTest {

    /**
     * Spring Boot 애플리케이션 컨텍스트가
     * 오류 없이 생성되는지 검증한다.
     */
    @Test
    void 애플리케이션_컨텍스트가_정상적으로_실행된다() {
        // Spring 컨텍스트 생성 자체가 검증 대상이므로
        // 테스트 본문에는 별도의 검증 코드를 작성하지 않는다.
    }
}