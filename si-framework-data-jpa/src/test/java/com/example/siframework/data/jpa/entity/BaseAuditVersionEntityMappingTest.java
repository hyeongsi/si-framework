package com.example.siframework.data.jpa.entity;

import jakarta.persistence.Version;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BaseAuditVersionEntityMappingTest {

    @Test
    void version_필드에_Version_애너테이션이_선언되어_있다()
        throws NoSuchFieldException {

        // given
        Field versionField =
            BaseAuditVersionEntity.class
                .getDeclaredField("version");

        // when
        Version version =
            versionField.getAnnotation(Version.class);

        // then
        assertNotNull(version);
    }
}