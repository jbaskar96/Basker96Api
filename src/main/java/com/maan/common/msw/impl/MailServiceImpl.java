package com.maan.common.msw.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.maan.common.bean.LoginMaster;
import com.maan.common.msw.MailService;
import com.maan.common.upload.request.MSWReq;

@Service
public class MailServiceImpl implements MailService {


	private static Session session = null;

	private Logger log = LogManager.getLogger(getClass());

	private Map<String, String> getdetails(Map<String, Object> getmaildetails) {
		Map<String, String> details = new HashMap<String, String>();
		try {
			details.put("SMTP_HOST", getmaildetails.get("SMTP_HOST").toString());
			details.put("SMTP_USER", getmaildetails.get("SMTP_USER").toString());
			details.put("SMTP_PWD", getmaildetails.get("SMTP_PWD").toString());
			details.put("MAIL_FROM", getmaildetails.get("SMTP_USER").toString());
			details.put("SMTP_PORT", getmaildetails.get("SMTP_PORT").toString());

			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true");
			// prop.put("mail.transport.protocol", "smtps");
			prop.put("mail.smtp.starttls.enable", "true");
			// prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.host", details.get("SMTP_HOST"));
			prop.put("mail.smtp.port", details.get("SMTP_PORT"));
			// prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(details.get("SMTP_USER"), details.get("SMTP_PWD"));
				}
			});
		} catch (Exception e) {
			log.error(e);
		}
		return details;
	}

	@Override
	public String sendmail_tocust(MSWReq request) {
		String response = "";
		try {
			String filepath = "";

			MimeMessage mimeMessage = new MimeMessage(session);

			request = getmailsubbody(request);

			String[] tomail = request.getTomail();
			String[] ccmail = request.getCcmail();

			File file = null;

			if (tomail != null) {
				InternetAddress[] addressToo = null;
				InternetAddress[] addressCc = null;

				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				helper.setSubject(request.getMailsubject());
				helper.setText(request.getMailbody(), true);

				if (StringUtils.isNotBlank(request.getFilepath())) {
					filepath = request.getFilepath();
					file = getfile(filepath);
					if (file != null)
						helper.addAttachment(file.getName(), file);
				}

				if (request.getFilepaths() != null) {
					for (int i = 0; i < request.getFilepaths().size(); i++) {
						filepath = request.getFilepaths().get(i);
						file = getfile(filepath);
						if (file != null)
							helper.addAttachment(file.getName(), file);
					}
				}

				mimeMessage.setFrom("   ");
				addressToo = new InternetAddress[tomail.length];

				for (int i = 0; i < tomail.length; i++) {
					if (StringUtils.isNotBlank(tomail[i])) {
						addressToo[i] = new InternetAddress(tomail[i]);
						mimeMessage.addRecipient(Message.RecipientType.TO, addressToo[i]);
					}
				}

				if (ccmail != null) {
					addressCc = new InternetAddress[ccmail.length];
					for (int i = 0; i < ccmail.length; i++) {
						if (StringUtils.isNotBlank(ccmail[i])) {
							addressCc[i] = new InternetAddress(ccmail[i]);
							mimeMessage.addRecipient(Message.RecipientType.CC, addressCc[i]);
						}
					}
				}

				Transport.send(mimeMessage);
				response = "Mail Sended For this QuoteNo: " + request.getQuote_no();

			}

		} catch (Exception e) {
			log.error(e);
			response = "Mail Not Sended For this QuoteNo: " + request.getQuote_no();
		}

		log.info("sendmail_tocust--> response: " + response);

		return response;
	}

	private MSWReq getmailsubbody(MSWReq request) {
		String type = StringUtils.isBlank(request.getType()) ? "" : request.getType();
		String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id();
		String quoteno = request.getQuote_no();
		String reqrefno = request.getRequestreferenceno();

		log.info("getmailsubbody--> type: " + type + " productid: " + productid);
		log.info("getmailsubbody--> quoteno: " + quoteno + " reqrefno: " + reqrefno);

		try {
			Map<String, Object> customerdet = new HashMap<>();
			Map<String, Object> otpdet = new HashMap<>();



		} catch (Exception e) {
			log.error(e);
		}
		return request;
	}

	private File getfile(String filepath) {
		File file = null;
		try {
			file = new File(filepath);
		} catch (Exception e) {
			log.error(e);
		}
		return file;
	}

	private String getUserMail(String userId) {
		String userMail = "";
		try {
			
	
			return userMail;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
