package io.ajprem.common.logger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.logging.LogLevel;

import io.ajprem.common.logger.constants.LogConstants;


@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Documented
@Inherited
public @interface Loggable {

	LogLevel level() default LogLevel.INFO;

	String useCase() default LogConstants.DEFAULT_USECASE;

	String step() default LogConstants.DEFAULT_STEP;
}
