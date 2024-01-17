package com.maan.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

	@JsonProperty("MailId")
	private String mailId;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("InsuranceId")
	private String companyId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("Authcode")
	private String authCode;
}
