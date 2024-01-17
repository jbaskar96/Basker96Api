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
public class IssuerInfoResponse {

	@JsonProperty("SubBranch")
	private String subBranch;
	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("UserMail")
	private String userMail;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("CoreLoginId")
	private String coreLoginId;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("AttachedBranch")
	private String attachedBranch;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("RegionCode")
	private String regionCode;
	@JsonProperty("BranchCode")
	private String branchCode;
}
