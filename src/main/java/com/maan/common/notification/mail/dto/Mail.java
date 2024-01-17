package com.maan.common.notification.mail.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mail implements Serializable {
	
	private List<String> tomails;
	private List<String> ccmails;
	private List<String> files;
	private String subject;
	private String mailbody;
	private String mailbodyContenttype;
	
	private MailMaster master;
}
