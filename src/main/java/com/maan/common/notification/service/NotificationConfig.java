package com.maan.common.notification.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.maan.common.bean.SmsConfigMaster;
import com.maan.common.notification.mail.dto.MailMaster;
import com.maan.common.notification.sms.dto.SmsConfigMasterDto;
import com.maan.common.repository.MailMasterRepository;
import com.maan.common.repository.SmsConfigMasterRepository;

@Configuration
public class NotificationConfig {

	@Autowired
	private MailMasterRepository mailm;
	
	@Autowired
	private SmsConfigMasterRepository smsm; 
	
	@Bean
	 @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	//@PostConstruct
	public List<MailMaster> getMailMasters() {
		List<MailMaster> mails=new ArrayList<MailMaster>();
		ModelMapper mapper = new ModelMapper();
		List<com.maan.common.bean.MailMaster> masters = mailm.findAll();
		for (com.maan.common.bean.MailMaster master : masters) {			
			MailMaster rep=mapper.map(master, MailMaster.class);
			mails.add(rep);
		}
		return mails;
	}
	
	@Bean
	 @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	//@PostConstruct
	public List<SmsConfigMasterDto> getSmsConfigMasters() {
		List<SmsConfigMasterDto> smss=new ArrayList<SmsConfigMasterDto>();
		ModelMapper mapper = new ModelMapper();
		List<SmsConfigMaster> masters = smsm.findAll();
		for (SmsConfigMaster master : masters) {			
			SmsConfigMasterDto rep=mapper.map(master, SmsConfigMasterDto.class);
			smss.add(rep);
		} 
		return smss;
	}
	
	
}
