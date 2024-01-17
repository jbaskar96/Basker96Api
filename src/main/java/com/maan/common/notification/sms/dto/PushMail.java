package com.maan.common.notification.sms.dto;

import java.util.List;
 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PushMail {
	@NotNull(message = "Notfication Type Must contains Value mail / sms / whatsapp")
	private NotificationType notificationType ;
	
	@NotEmpty(message = "Notfication Template Id Is Must be  Fill.")
	private String notificationTemplateId;
	private String autoFrameYn;
	private List<String> keyvalues;
	@NotEmpty(message = "Insurance Id Should Not be Fill")
	private String insuranceId;
	
	private String fileattach;
	private List<String> fileattachkeys;
}
