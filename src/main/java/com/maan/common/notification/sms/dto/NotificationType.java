package com.maan.common.notification.sms.dto;
 

import lombok.Getter;

@Getter
public enum NotificationType {
	
	
	MAIL("mail")	,SMS("sms"),WHATSAPP("whatsapp");
	
	 

	private NotificationType(String type) {
		this.type = type;
	}

	private String type;
}
