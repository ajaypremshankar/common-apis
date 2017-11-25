/**
 *
 */
package io.ajprem.common.services;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;



@SuppressWarnings("rawtypes")
public interface MailSenderService {

	boolean triggerMail(SimpleMailMessage message);


	/**
	 * @param message
	 * @param templateRelativePath
	 * @param mailArgs
	 * @throws BaseException
	 */

	boolean sendHtmlMessage(SimpleMailMessage message, String templateRelativePath, Map mailArgs);

}
