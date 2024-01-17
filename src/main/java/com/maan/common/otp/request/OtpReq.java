package com.maan.common.otp.request;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpReq {

	@JsonProperty("Sno")
	private BigDecimal sNo;
	@JsonProperty("Insid")
	private BigDecimal insId;

	@JsonProperty("Otpid")
	private BigDecimal otpId;
	@JsonProperty("Otptype")
	private String otpType;
	@JsonProperty("Otp")
	private BigDecimal otp;
	@JsonProperty("Entrydate")
	private Date entryDate;
	@JsonProperty("Incepdate")
	private Date incepDate;
	@JsonProperty("Expirydate")
	private Date expiryDate;
	@JsonProperty("Mobileno")
	private String mobileNo;
	@JsonProperty("Emailid")
	private String emailId;
	@JsonProperty("Sessionid")
	private String sessionId;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Mailotp")
	private BigDecimal mailOtp;
	@JsonProperty("Referenceno")
	private String referenceNo;
	@JsonProperty("Code")
	private String code;
	@JsonProperty("Whatsappno")
	private String whatsappNo;

	@JsonProperty("AssuredName")
	private String assuredName;
}
