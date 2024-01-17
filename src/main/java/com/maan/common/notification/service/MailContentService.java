package com.maan.common.notification.service;

import com.maan.common.notification.mail.dto.Mail;
import com.maan.common.notification.sms.dto.PushMail;

public interface MailContentService {
		
		public Mail pushNotifcation(PushMail request);
		 
		public boolean sentMail(PushMail request);
		
		public boolean sentSMS(PushMail request);
}
