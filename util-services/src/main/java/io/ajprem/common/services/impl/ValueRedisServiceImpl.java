/**
 *
 */
package io.ajprem.common.services.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import io.ajprem.common.services.ValueRedisService;


@Service("valueRedisService")
public class ValueRedisServiceImpl<T> extends BaseServiceImpl implements ValueRedisService<T> {

	@Resource
	private RedisTemplate<String, T> redisTemplate;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, T> valueOps;

	@Override
	public void put(final String key, final T value) {
		put(key, value, 0);
	}

	@Override
	public void put(final String key, final T value, final long exiryInMilliseconds) {
		valueOps.set(key, value);
		if (exiryInMilliseconds > 0) {
			redisTemplate.expire(key, exiryInMilliseconds, TimeUnit.MILLISECONDS);
		}

	}

	@Override
	public boolean contains(final String key) {
		return valueOps.get(key) != null;
	}

	@Override
	public boolean contains(final String key, final T value) {
		T tokenFromRedis = valueOps.get(key);
		return value.equals(tokenFromRedis);
	}

	@Override
	public T retrieve(final String key) {
		return valueOps.get(key);
	}

	@Override
	public void remove(final String key) {
		valueOps.set(key, null);
	}

}
