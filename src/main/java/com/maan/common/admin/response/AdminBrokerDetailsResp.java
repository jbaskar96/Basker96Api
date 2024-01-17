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
public class AdminBrokerDetailsResp {

	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("ContactPerson")
	private String contactPerson;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("RsaBrokerCode")
	private String rsaBrokerCode;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("CreateDate")
	private String createDate;
	@JsonProperty("LoginId")
	private String loginId;
}
