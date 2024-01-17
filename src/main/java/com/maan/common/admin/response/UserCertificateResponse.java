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
public class UserCertificateResponse {

	@JsonProperty("OpenCoverNo")
	private String openCoverNo;
	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("MissippiOpenCoverNo")
	private String missippiOpenCoverNo;
	@JsonProperty("CustomerSchedule")
	private String customerSchedule;
	@JsonProperty("CustomerDebit")
	private String customerDebit;
	@JsonProperty("CustCustomerDebit")
	private String custCustomerDebit;
	@JsonProperty("CompanyName")
	private String companyName;
}
