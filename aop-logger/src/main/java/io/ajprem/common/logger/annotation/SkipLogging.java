/**
 *
 */
package io.ajprem.common.logger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * Used to prevent the logging of the target function
 */

@Component
@Target(value = { ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SkipLogging {

}
