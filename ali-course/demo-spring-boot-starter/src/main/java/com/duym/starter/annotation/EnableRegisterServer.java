package com.duym.starter.annotation;

import com.duym.starter.config.ConfigMarker;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author duym
 * @date 2023/04/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ConfigMarker.class)
public @interface EnableRegisterServer {
}
