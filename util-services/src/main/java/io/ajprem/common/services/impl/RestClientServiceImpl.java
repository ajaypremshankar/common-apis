/**
 *
 */
package io.ajprem.common.services.impl;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ajprem.common.services.RestClientService;
import io.ajprem.common.services.model.ServerDetails;

@SuppressWarnings({ "rawtypes", "unchecked" })

@Service(value = "restClientService")
@Scope(scopeName = "prototype")
public class RestClientServiceImpl extends BaseServiceImpl implements RestClientService {

	RestTemplate restTemplate = new RestTemplate();
	private ServerDetails serverDetails;

	@Override
	public ResponseEntity get(final String relativeURI, final Class classType, final Object... urlParams) {
		return restTemplate.getForEntity(getCompleteURIWith(relativeURI, urlParams), classType);
	}

	@Override
	public ResponseEntity post(final String relativeURI, final Class classType, final Object objectToSend)
	{

		ResponseEntity result = null;
		try {
			if (objectToSend != null && objectToSend instanceof MultiValueMap<?, ?>) {
				HttpHeaders reqHead = new HttpHeaders();
				reqHead.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
				result = restTemplate.postForEntity(getCompleteURIWith(relativeURI),
						new HttpEntity<>(objectToSend, reqHead), classType);
			} else {
				HttpEntity request = new HttpEntity<>(objectToSend);
				result = restTemplate.exchange(getCompleteURIWith(relativeURI), HttpMethod.POST, request, classType);
			}
		} catch (HttpStatusCodeException e) {
			String responseString = e.getResponseBodyAsString();

			ObjectMapper mapper = new ObjectMapper();

			Object response = null;

			try {
				response = mapper.readValue(responseString, Object.class);
			} catch (IOException e1) {
				throw new RuntimeException("RestClientService - Error in parsing Response", e1);
			}

			result = new ResponseEntity(response, new HttpHeaders(), e.getStatusCode());
		}

		return result;
	}

	private String getCompleteURIWith(final String relativeURI, final Object... params) {
		StringBuilder url = new StringBuilder(serverDetails.getUrl()).append("/").append(relativeURI);

		for (Object param : params) {
			url.append("/").append(param.toString());
		}

		return url.toString();
	}


	@Override
	public void setServerDetails(final ServerDetails serverDetails) {
		this.serverDetails = serverDetails;

	}

}
