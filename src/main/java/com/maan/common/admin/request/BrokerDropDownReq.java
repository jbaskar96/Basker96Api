package com.maan.common.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrokerDropDownReq {

	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("OpenCoverNo")
	private String openCoverNo;
	@JsonProperty("LoginId")
	private String loginId;
}
