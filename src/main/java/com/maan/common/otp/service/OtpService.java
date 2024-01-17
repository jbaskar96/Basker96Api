package com.maan.common.otp.service;

import java.util.List;

import com.maan.common.error.Error;
import com.maan.common.otp.request.OtpReq;
import com.maan.common.otp.response.OtpValidationRes;
import com.maan.common.upload.response.LoginMasterResDto;

public interface OtpService {

	OtpValidationRes getOTP(OtpReq request);
	
	

	List<Error> validateMobileAndEmail(OtpReq request);

	LoginMasterResDto createUser(OtpReq requst);

}
