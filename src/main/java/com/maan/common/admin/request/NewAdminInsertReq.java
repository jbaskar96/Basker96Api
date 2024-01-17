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
public class NewAdminInsertReq {

	
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("Password")
	private String password;
	@JsonProperty("UserType")
	private String userType;
	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("RegionCode")
	private String regionCode;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Mode")
	private String mode;
	@JsonProperty("MenuInfo")
	private List<MenuIdRequest> menuInfo;
	@JsonProperty("ProductInfo")
	private List<ProductRequest> productInfo;
	@JsonProperty("BrokerInfo")
	private List<AdminBrokerRequest> brokerInfo;
	@JsonProperty("UnderWriterInfo")
	private List<UnderWriterRequest> underWriterInfo;
	@JsonProperty("AttachedBranchInfo")
	private List<AttachedBranchReq> attachedBranchInfo;
}
