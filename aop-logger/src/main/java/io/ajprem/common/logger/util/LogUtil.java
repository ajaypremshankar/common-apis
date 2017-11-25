package io.ajprem.common.logger.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.logging.LogLevel;

import io.ajprem.common.logger.annotation.Loggable;
import io.ajprem.common.logger.annotation.SkipLogging;
import io.ajprem.common.logger.constants.LogConstants;


public class LogUtil {
	private LogUtil() {

	}

	public static LogEntry obtainMethodEntryLog(final ProceedingJoinPoint joinPoint) {
		LogEntry logEntry = LogUtil.newEntry();

		prepareLog(joinPoint, logEntry);

		logEntry.appendMessage("Entering : ").appendMessage(logEntry.getUseCase()).appendMessage(".")
				.appendMessage(logEntry.getStep());

		Object[] args = skipParamLoggingIfSkipLoggingAnnotationPresent(joinPoint);
		if (args != null) {
			logEntry.appendMessage(" with argument[s] = ").appendMessage(Arrays.toString(args));
		}
		logEntry.setData(args);

		return logEntry;
	}

	public static LogEntry obtainMethodExitLog(final ProceedingJoinPoint joinPoint, final Object result) {
		LogEntry logEntry = LogUtil.newEntry();

		prepareLog(joinPoint, logEntry);

		logEntry.appendMessage("Exit: ").appendMessage(logEntry.getUseCase()).appendMessage(".")
				.appendMessage(logEntry.getStep()).appendMessage(" with message ").appendMessage(result);

		logEntry.setData(result);
		return logEntry;
	}

	public static LogEntry obtainMethodFailureLog(final ProceedingJoinPoint joinPoint, final Exception e) {
		LogEntry logEntry = LogUtil.newEntry();

		prepareLog(joinPoint, logEntry);

		/*
		 * Explicitly set Log Level to Error in case of exception (only if not
		 * fatal already)
		 */
		if (!LogLevel.FATAL.name().equals(logEntry.getLogLevel())) {
			logEntry.setLogLevel(LogLevel.ERROR.name());
		}

		logEntry.appendMessage("Exception occurred in : ").appendMessage(logEntry.getUseCase()).appendMessage(".")
				.appendMessage(logEntry.getStep()).appendMessage(e);
		logEntry.setData(Arrays.toString(e.getStackTrace()));
		return logEntry;
	}

	private static void prepareLog(final ProceedingJoinPoint joinPoint, final LogEntry logEntry) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		Method method = signature.getMethod();

		String logLevel = null;
		String useCase = null;
		String step = null;

		if (signature.getDeclaringType().isAnnotationPresent(Loggable.class)) {
			Loggable loggableOnClass = (Loggable) signature.getDeclaringType().getAnnotation(Loggable.class);

			/*
			 * Set data from Class annotation only if no/default values are
			 * present
			 */
			logLevel = loggableOnClass.level().name();
			useCase = loggableOnClass.useCase();
			step = loggableOnClass.step();

		}

		/*
		 * Preference to the Loggable annotation on method. Otherwise pick from
		 * Class
		 */
		if (method.isAnnotationPresent(io.ajprem.common.logger.annotation.Loggable.class)) {
			Loggable loggableOnMetod = method.getAnnotation(Loggable.class);

			if (!loggableOnMetod.level().equals(LogConstants.DEFAULT_LOG_LEVEL_TO_SEND)) {
				logLevel = loggableOnMetod.level().name();
			}
			if (!LogConstants.DEFAULT_USECASE.equals(loggableOnMetod.useCase())) {
				useCase = loggableOnMetod.useCase();
			}
			if (!LogConstants.DEFAULT_STEP.equals(loggableOnMetod.step())) {
				step = loggableOnMetod.step();
			}

		}

		logEntry.setLogLevel(logLevel != null ? logLevel : LogConstants.DEFAULT_LOG_LEVEL_TO_SEND.name());
		logEntry.setUseCase(useCase != null ? useCase : joinPoint.getSignature().getDeclaringTypeName());
		logEntry.setStep(step != null ? step : joinPoint.getSignature().getName());

		logEntry.setClassName(joinPoint.getSignature().getDeclaringTypeName());
		logEntry.setMethodName(joinPoint.getSignature().getName());

	}

	public static LogEntry newEntry() {
		return newEntry(null, null, "");
	}

	public static LogEntry newEntry(final String useCase, final String step) {
		return newEntry(useCase, step, "");
	}

	public static LogEntry newEntry(final String useCase, final String step, final String initialMessage) {
		return new LogEntry(useCase, step, null, initialMessage);
	}

	private static Object[] skipParamLoggingIfSkipLoggingAnnotationPresent(final ProceedingJoinPoint joinPoint) {
		Annotation[][] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
		Object[] input = joinPoint.getArgs();
		List<Object> output = new ArrayList<>();
		boolean isPresent = false;
		int arg = -1;
		for (Annotation[] annotation1 : annotations) {
			arg++;
			isPresent = false;
			for (Annotation annotation : annotation1) {
				if (annotation instanceof SkipLogging) {
					isPresent = true;
					break;
				}
			}
			if (!isPresent) {
				output.add(input[arg]);
			}
		}
		return output.toArray();
	}

}
