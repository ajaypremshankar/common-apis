package io.ajprem.common.logger.util;

import java.util.Date;

import io.ajprem.common.logger.annotation.SkipLogging;


@SkipLogging
public class LogEntry {

	public LogEntry() {
		this.dateTime = new Date();
		this.message = new StringBuilder("");
	}

	public LogEntry(final String useCase, final String step, final String clientId, final String initialMessage) {
		this.useCase = useCase;
		this.clientId = clientId;
		this.step = step;
		this.dateTime = new Date();
		this.message = new StringBuilder(initialMessage);
	}

	private Date dateTime;
	private String logLevel;
	private String sessionId;
	private String useCase;
	private String step;
	private StringBuilder message;
	private Object data;
	private String clientId; // Set service Id
	private String className;
	private String methodName;

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(final Date dateTime) {
		this.dateTime = dateTime;

	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(final String logLevel) {
		this.logLevel = logLevel;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUseCase() {
		return useCase;
	}

	public void setUseCase(final String useCase) {
		this.useCase = useCase;
	}

	public String getStep() {
		return step;
	}

	public void setStep(final String step) {
		this.step = step;
	}

	public String getMessage() {
		return message.toString();
	}

	public void setMessage(final String message) {
		this.message = new StringBuilder(message);
	}

	public Object getData() {
		return data;
	}

	public void setData(final Object data) {
		this.data = data;
	}

	public LogEntry appendMessage(final Object extraMessage) {
		this.message.append(extraMessage);
		return this;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
