package com.rzt.service.impl;

import java.util.Calendar;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.rzt.enums.EmailCode;
import com.rzt.schemapojo.Email;
import com.rzt.schemapojo.EmailTemplate;
import com.rzt.schemapojo.Employee;
import com.rzt.service.EmailService;
import com.rzt.repository.EmailRepo;
import com.rzt.repository.EmailTemplateRepo;
import com.rzt.utils.CalendarUtil;
import com.rzt.utils.StringHelper;

/**
 * Service to do Email Operations
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private EmailTemplateRepo emailTemplateRepo;

	@Autowired
	private EmailRepo emailRepo;

	@Autowired
	private Environment env;

	public EmailTemplateRepo getEmailTemplateRepository()
	{
		return emailTemplateRepo;
	}

	public void setEmailTemplateRepository( EmailTemplateRepo emailTemplateRepository )
	{
		this.emailTemplateRepo = emailTemplateRepository;
	}

	@Async
	@Override
	public void sendEmail( Employee toUser, String templateInternalName, Map<String, Object> attributes )
	{
		LOGGER.info("Email to : " + templateInternalName);

		EmailTemplate emailTemplate = emailTemplateRepo.findByInternalName(templateInternalName);
		if( emailTemplate != null )
		{

			String formattedMessage = replaceEmailCodes(emailTemplate.getBody(), toUser, attributes);

			MimeMessage message = mailSender.createMimeMessage();

			MimeMessageHelper helper = getMimeMessageHelper(message, toUser.getEmail(), emailTemplate.getSubject(),
					formattedMessage);
			if( helper == null )
			{
				LOGGER.error("MIME Helper was not instantiated.");
			}
			mailSender.send(message);

			Email email = new Email();
			email.setFromEmail(mailSender.getUsername());
			email.setToEmail(toUser.getEmail());
			email.setSubject(emailTemplate.getSubject());
			email.setBody(formattedMessage);

			emailRepo.save(email);

			LOGGER.info("Email sent to : " + toUser.getEmail());

		}
		else
		{
			LOGGER.info("Template not found...");
		}
	}

	private MimeMessageHelper getMimeMessageHelper( MimeMessage message, String to, String subject, String body )
	{
		MimeMessageHelper helper = null;
		try
		{

			helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject(subject);
			// use the true flag to indicate the text included is HTML
			helper.setText(body, true);

		}
		catch( MessagingException e )
		{
			LOGGER.error("Failed to instantiate MIME Helper ", e);
		}

		return helper;

	}

	/**
	 * Replace All email Codes present in email Template with the actual values
	 * 
	 * @param body
	 * @param employee
	 * @param attributes
	 * @return
	 */
	public String replaceEmailCodes( String body, Employee employee, Map<String, Object> attributes )
	{
		String messageBody = body;
		if( StringHelper.isNullOrNullString(messageBody) || employee == null )
		{
			return "";
		}

		if( messageBody.contains(EmailCode.USER_ID.getCode()) )
		{
			messageBody = messageBody.replaceAll(this.getRegexPatternForEmailCodes(EmailCode.USER_ID.getCode()),
					employee.getId().toString());
		}

		if( messageBody.contains(EmailCode.TO_USER_NAME.getCode()) )
		{
			messageBody = messageBody.replaceAll(this.getRegexPatternForEmailCodes(EmailCode.TO_USER_NAME.getCode()),
					employee.getName());
		}

		if( messageBody.contains(EmailCode.CURRENT_DATETIME.getCode()) )
		{
			messageBody = messageBody.replaceAll(
					this.getRegexPatternForEmailCodes(EmailCode.CURRENT_DATETIME.getCode()),
					CalendarUtil.convertCalendarToString(Calendar.getInstance()));
		}

		if( messageBody.contains(EmailCode.TO_USER_EMAIL_ID.getCode()) )
		{
			messageBody = messageBody.replaceAll(
					this.getRegexPatternForEmailCodes(EmailCode.TO_USER_EMAIL_ID.getCode()), employee.getEmail());
		}

		if( messageBody.contains(EmailCode.HOST_URL.getCode()) )
		{
			messageBody = messageBody.replaceAll(this.getRegexPatternForEmailCodes(EmailCode.HOST_URL.getCode()),
					env.getProperty("web.host.url"));
		}

		if( attributes != null && messageBody.contains(EmailCode.PASSWORD_RESET_TOKEN.getCode()) )
		{
			String token = (String) attributes.get("passwordResetToken");

			if( token == null )
			{
				token = "";
			}

			messageBody = messageBody
					.replaceAll(this.getRegexPatternForEmailCodes(EmailCode.PASSWORD_RESET_TOKEN.getCode()), token);
		}

		// code to replace some special email codes with its values.

		return messageBody;
	}

	/**
	 * Add an email
	 * 
	 * @return
	 */
	public Email addMail()
	{
		return new Email();
	}

	private String getRegexPatternForEmailCodes( String emailCode )
	{
		// As | is a regex operator, escape by adding \
		return emailCode.replaceAll("\\|", "\\\\|");
	}
}
