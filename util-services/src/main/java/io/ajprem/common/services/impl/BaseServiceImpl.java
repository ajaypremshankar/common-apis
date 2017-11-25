/**
 *
 */
package io.ajprem.common.services.impl;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;

import io.ajprem.common.services.BaseService;

/**
 *
 */
public class BaseServiceImpl implements BaseService {


	@Resource
	private Environment environment;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(final Environment environment) {
		this.environment = environment;
	}

}
