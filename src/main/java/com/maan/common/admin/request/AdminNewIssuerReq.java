package com.maan.common.admin.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminNewIssuerReq {

	@JsonProperty("RegionCode")
	private String regionCode;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("LoginUserType")
	private String loginUserType;
	@JsonProperty("Password")
	private String password;
	@JsonProperty("IssuerName")
	private String issuerName;
	@JsonProperty("CoreLoginId")
	private String coreLoginId;
	@JsonProperty("EmailId")
	private String emailId;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("EffectiveDate")
	private String effectiveDate;
	@JsonProperty("OptionMode")
	private String optionMode;
	@JsonProperty("BrokerLinkLocation")
	private String brokerLinkLocation;
	@JsonProperty("AttachedBranchInfo")
	private List<AttachedBranchReq> attachedBranchInfo;
	@JsonProperty("ProductInfo")
	private List<IssuerProductReq> productInfo;
}
