package io.ajprem.common.services.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.ajprem.common.services.HashRedisService;


@Service("hashRedisService")
public class HashRedisServiceImpl<T> implements HashRedisService<T> {

	@Resource
	private RedisTemplate<T, T> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations<T, T, Object> hashOps;

	@Override
	public void put(final T key, final T hashKey, final Object value) {
		put(key, hashKey, value, 0);
	}

	@Override
	public void put(final T key, final T hashKey, final Object value, final long exiryInMilliseconds) {
		hashOps.put(key, hashKey, value);
		if (exiryInMilliseconds > 0) {
			redisTemplate.expire(key, exiryInMilliseconds, TimeUnit.MILLISECONDS);
		}

	}

	@Override
	public boolean contains(final T key, final T hashKey) {
		return hashOps.get(key, hashKey) != null;
	}

	@Override
	public Object retrieve(final T key, final T hashKey) {
		return hashOps.get(key, hashKey);
	}

	@Override
	public Map<T, Object> retrieve(final T key) {
		return hashOps.entries(key);
	}

	@Override
	public boolean remove(final T key, final T hashKey) {
		return hashOps.delete(key, hashKey) > 0L;
	}

	@Override
	public boolean contains(final T key) {
		Map<T, Object> entries = hashOps.entries(key);
		return !(entries == null || entries.isEmpty());
	}

}
