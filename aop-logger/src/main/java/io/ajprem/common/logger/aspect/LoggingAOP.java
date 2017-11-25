

package io.ajprem.common.logger.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ajprem.common.logger.annotation.SkipLogging;
import io.ajprem.common.logger.util.LogUtil;


@SuppressWarnings({ "unchecked" })
@Component
@Aspect
@SkipLogging
@Order(10)
@Configurable

public class LoggingAOP {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Advice that logs when a method is entered and exited. Only when class/method
	 * is not annotated with SkipLogging
	 *
	 * @param joinPoint
	 *            join point for advice
	 * @return result
	 * @throws Throwable
	 *             throws IllegalArgumentException
	 */
	@Around("within(io.ajprem..*) && !@within(io.ajprem.common.logger.annotation.SkipLogging)"
			+ " && !@annotation(io.ajprem.common.logger.annotation.SkipLogging)")
	public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		// Log only if class/method is not annotated with SkipLogging
		if (!(signature.getDeclaringType().isAnnotationPresent(SkipLogging.class)
				|| method.isAnnotationPresent(SkipLogging.class))) {
			try {
				// Log before method entry
				logger.info(mapper.writeValueAsString(LogUtil.obtainMethodEntryLog(joinPoint)));

				Object result = joinPoint.proceed();

				// Log after method exit
				logger.info(mapper.writeValueAsString(LogUtil.obtainMethodExitLog(joinPoint, result)));
				return result;
			} catch (Exception e) {
				// Log after exception
				logger.info(mapper.writeValueAsString(LogUtil.obtainMethodFailureLog(joinPoint, e)));
				throw e;
			}
		} else

		{
			return joinPoint.proceed();
		}
	}

}

