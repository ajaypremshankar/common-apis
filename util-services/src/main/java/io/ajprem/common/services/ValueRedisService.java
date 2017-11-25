/**
 *
 */
package io.ajprem.common.services;

public interface ValueRedisService<T> {

	/**
	 * @param key
	 */
	void remove(String key);

	T retrieve(String key);

	void put(String key, T value);

	void put(String key, T value, long exiryInMilliseconds);

	boolean contains(String key);

	boolean contains(String key, T value);

}
