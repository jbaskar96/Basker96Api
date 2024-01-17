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
public class AdminDetailsResponse {

	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("UserType")
	private String userType;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("BranchName")
	private String branchName;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Status1")
	private String status1;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("MenuId")
	private String menuId;
	@JsonProperty("BrokerCode")
	private String brokerCode;
	@JsonProperty("UserMail")
	private String userMail;
	@JsonProperty("AttachedUnderWriter")
	private String attachedUnderWriter;
	@JsonProperty("AttachedBranch")
	private String attachedBranch;
	@JsonProperty("RegionCode")
	private String regionCode;
}
