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
public class ExcludedDetailsResponse {

	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("BranchName")
	private String branchName;
	@JsonProperty("ProductId")
	private String productId;
}
