package com.maan.common.notification.mail.job;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.maan.common.bean.MailMaster;
import com.maan.common.notification.mail.dto.Mail;

public class MailJob implements Callable<Object> {
//	private	MailMaster app;
	private static Session session = null;
	private Properties prop ;
	private MimeMessage mimeMessage;
	private Mail request;
	
	public MailJob(Mail request) {
	//	this.app=app;
		this.request=request;
	}
	
	@Override
	public Object call() throws Exception {
		try {
			sent();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private boolean sent() throws Exception{
		// Initialize 
		getMailInstance();
		
		
		//request = getmailsubbody(request);
		
		List<String> tomail = request.getTomails();
		List<String> ccmail = request.getCcmails();
		
		 

		if (tomail != null && tomail.size()>0) {
			InternetAddress[] addressToo = null;
			InternetAddress[] addressCc = null;

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setSubject(request.getSubject());
			if("text/html".equals(request.getMailbodyContenttype()))
				helper.setText(request.getMailbody(), true);
			else				
				helper.setText(request.getMailbody(), true);

			
			if(request.getFiles()!=null && request.getFiles().size()>0) {
				for (String attachPath : request.getFiles()) {
					File file=loadFilesFromPath(attachPath);
					if (file != null)
						helper.addAttachment(file.getName(), file);
				}
			}
			

			mimeMessage.setFrom(request.getMaster().getToAddress());
			
			
			addressToo = new InternetAddress[tomail.size()];

			for (int i = 0; i < tomail.size(); i++) {
				if (StringUtils.isNotBlank(tomail.get(i))) {
					addressToo[i] = new InternetAddress(tomail.get(i));
					mimeMessage.addRecipient(Message.RecipientType.TO, addressToo[i]);
				}
			}

			if (ccmail != null) {
				addressCc = new InternetAddress[ccmail.size()];
				for (int i = 0; i < ccmail.size(); i++) {
					if (StringUtils.isNotBlank(ccmail.get(i))) {
						addressCc[i] = new InternetAddress(ccmail.get(i));
						mimeMessage.addRecipient(Message.RecipientType.CC, addressCc[i]);
					}
				}
			}

			Transport.send(mimeMessage);
			//response = "Mail Sended For this QuoteNo: " + request.getQuote_no();
			return true;
		}
		return false;
	}
	

	private File loadFilesFromPath(String attachPath) {
		
		try {
			return new File(attachPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	private void getMailInstance() {
	
		try {
			
			/*
			details.put("SMTP_HOST", app.getSmtpHost());
			details.put("SMTP_USER", app.getSmtpUser());
			details.put("SMTP_PWD", app.getSmtpPwd());
			details.put("MAIL_FROM", "");
			details.put("SMTP_PORT", String.valueOf(app.getSmtpPort()));
*/
			prop = new Properties();
			prop.put("mail.smtp.auth", "true");
			// prop.put("mail.transport.protocol", "smtps");
			prop.put("mail.smtp.starttls.enable", "true");
			// prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.host",request.getMaster().getSmtpHost());
			prop.put("mail.smtp.port",request.getMaster().getSmtpPort());
			// prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(request.getMaster().getSmtpUser(), request.getMaster().getSmtpPwd());
				}
			});
			
			 mimeMessage = new MimeMessage(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	 



}
