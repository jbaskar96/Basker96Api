package com.maan.common.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maan.common.notification.service.MailContentService;
import com.maan.common.notification.sms.dto.PushMail;
import com.maan.common.upload.response.ResponseMessage;

@RestController
@RequestMapping("/post/notification")
public class NotificationController {
	
	@Autowired
	private MailContentService service;
	@PostMapping("/mail")
	public ResponseEntity<ResponseMessage> pushMail(@RequestBody PushMail mail){
		try {
			service.sentMail(mail);			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Mail Sent Successfuly"));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Mail is Not sent ,we are facing issue"));
		}
	} 
	@PostMapping("/sms")
	public ResponseEntity<ResponseMessage> pushSms(@RequestBody PushMail mail){
		try {
			service.sentSMS(mail);			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("SMS Sent Successfuly"));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("SMS is Not sent ,we are facing issue"));
		}
	} 
	
}
