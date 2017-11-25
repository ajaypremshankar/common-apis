package io.ajprem.common.services;

import java.util.Map;

public interface HashRedisService<T> {

	Object retrieve(T key, T hashKey);

	boolean contains(T key, T hashKey);

	void put(T key, T hashKey, Object value, long exiryInMilliseconds);

	void put(T key, T hashKey, Object value);

	boolean remove(T key, T hashKey);

	boolean contains(T key);

	Map<T, Object> retrieve(T key);

}
