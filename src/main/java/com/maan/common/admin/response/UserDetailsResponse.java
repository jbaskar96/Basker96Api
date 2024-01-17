package com.maan.common.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {

	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("UserType")
	private String userType;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("ApplicationId")
	private String applicationId;
	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("CreateDate")
	private String createDate;
	@JsonProperty("BrokerName")
	private String brokerName;
}
