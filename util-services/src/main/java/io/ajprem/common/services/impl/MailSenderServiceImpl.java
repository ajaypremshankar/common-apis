/**
 *
 */
package io.ajprem.common.services.impl;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.ajprem.common.services.MailSenderService;

/**
 * @author GZTX0077
 *
 */
@SuppressWarnings("rawtypes")
@Service("mailSenderService")
public class MailSenderServiceImpl implements MailSenderService {

	@Resource
	private JavaMailSender mailSender;

	@Resource
	private Configuration freemarkerConfiguration;

	private static final String DEFAULT_ENCODING = "utf-8";

	/*
	 * (non-Javadoc)
	 *
	 * @see com.orange.banyan.identity.common.service.MailSenderService#triggerMail(
	 * org.springframework.mail.SimpleMailMessage)
	 */
	@Override
	public boolean triggerMail(final SimpleMailMessage message) {

		boolean sentFlag = false;

		try {
			this.mailSender.send(message);
			sentFlag = true;
		} catch (Exception e) {
			throw new RuntimeException("Error while sending mail", e);
		}

		return sentFlag;
	}

	/**
	 *
	 */
	@Override
	public boolean sendHtmlMessage(final SimpleMailMessage message, final String templateRelativePath,
			final Map mailArgs) {

		boolean mailSentFlag = false;
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

			helper.setFrom(message.getFrom());
			helper.setTo(message.getTo());
			helper.setSubject(message.getSubject());
			helper.setCc(message.getCc());

			if (message.getBcc() != null) {
				helper.setBcc(message.getBcc());
			}

			String content = generateContent(templateRelativePath, mailArgs);
			helper.setText(content, true);
			mailSender.send(msg);
			mailSentFlag = true;
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send mail : Error with FreeMarker", e);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send mail", e);
		}

		return mailSentFlag;
	}

	/**
	 *
	 * @param templateRelativePath
	 * @param args
	 * @return
	 * @throws MessagingException
	 */
	private String generateContent(final String templateRelativePath, final Map args) throws MessagingException {

		try {
			Template template = freemarkerConfiguration.getTemplate(templateRelativePath, DEFAULT_ENCODING);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, args);
		} catch (IOException e) {
			throw new MessagingException("FreeMarker template not exist", e);
		} catch (TemplateException e) {
			throw new MessagingException("FreeMarker process failed", e);
		}
	}

	/**
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(final JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * @param freemarkerConfiguration
	 *            the freemarkerConfiguration to set
	 */
	public void setFreemarkerConfiguration(final Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

}
