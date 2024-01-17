package com.maan.common.otp.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpValidationRes {

	@JsonProperty("OTPValidationStatus")
	private String otpvalidationstatus;
}
