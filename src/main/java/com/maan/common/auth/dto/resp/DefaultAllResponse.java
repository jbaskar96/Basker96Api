package com.maan.common.auth.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultAllResponse {

	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("OpenCoverNo")
	private String openCoverNo;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("UserType")
	private String userType;
	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("Token")
	private String token;
	@JsonProperty("CountryCode")
	private String countryCode;
	@JsonProperty("CurrencyName")
	private String currencyName;
	@JsonProperty("MenuId")
	private String menuId;
	@JsonProperty("RegionCode")
	private String regionCode;
	@JsonProperty("StatusCode")
	private String statusCode="Error";
	@JsonProperty("StatusDescription")
	private String statusDescription;
}
