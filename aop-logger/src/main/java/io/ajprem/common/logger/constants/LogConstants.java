package io.ajprem.common.logger.constants;

import org.springframework.boot.logging.LogLevel;

public class LogConstants {
	private LogConstants() {

	}

	public static final String DEFAULT_USECASE = "io.ajprem";
	public static final String DEFAULT_STEP = "default";

	public static final LogLevel DEFAULT_LOG_LEVEL_TO_SEND = LogLevel.INFO;
}
