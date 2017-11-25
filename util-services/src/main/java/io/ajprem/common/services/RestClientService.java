/**
 *
 */
package io.ajprem.common.services;

import org.springframework.http.ResponseEntity;

import io.ajprem.common.services.model.ServerDetails;

@SuppressWarnings("rawtypes")
public interface RestClientService {

	/***
	 *
	 * @param relativeURI
	 * @param classType
	 * @param params
	 * @return
	 */
	public ResponseEntity get(String relativeURI, final Class classType, final Object... params);

	/***
	 *
	 * @param relativeURI
	 * @param classType
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public ResponseEntity post(String relativeURI, final Class classType, final Object request);

	public void setServerDetails(ServerDetails serverDetails);
}
