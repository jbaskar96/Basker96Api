package com.maan.common.otp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.maan.common.error.CommonValidationException;
import com.maan.common.error.Error;
import com.maan.common.otp.request.OtpReq;
import com.maan.common.otp.response.OtpValidationRes;
import com.maan.common.otp.service.OtpService;
import com.maan.common.otp.service.impl.CommonServiceImpl;
import com.maan.common.otp.service.impl.OtpServiceImpl;
import com.maan.common.upload.response.LoginMasterResDto;

@RestController
public class OtpController {
	
	@Autowired
	private OtpService smsService;
	
	@Autowired
	private CommonServiceImpl commonService;
	
	@Autowired
	private OtpServiceImpl otpserviceimpl;
	
	@PostMapping("/getotp")
	public OtpValidationRes getotp(@RequestBody OtpReq request) throws CommonValidationException {
		
		//Mobile No and MailId Validation Block		
    	List<Error> error = new ArrayList<>();
		error = smsService.validateMobileAndEmail(request);		
		if (error != null && error.size() > 0)
				throw  new CommonValidationException(error,request);
		
		//otp Block
		return smsService.getOTP(request);
	}
	
	@PostMapping("/validateotp")
	public ResponseEntity<LoginMasterResDto> validateotp(@RequestBody OtpReq request) throws CommonValidationException{

		LoginMasterResDto otpRes = new LoginMasterResDto();
		
			commonService.reqPrint(request);
			List<Error> errorlist = otpserviceimpl.OTPValidation(request);
			if (errorlist != null && errorlist.size() > 0)
				throw new CommonValidationException(errorlist, request);
			 
			otpRes = otpserviceimpl.createUser(request);
			
	        if (otpRes!=null) {
				return new ResponseEntity<LoginMasterResDto>(otpRes,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			 }
			
	}
	@PostMapping("/createUser")
	public LoginMasterResDto createUser(@RequestBody OtpReq request) throws Exception {
		return smsService.createUser(request);
	}
	
}
