package com.maan.common.menu.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DropDownReq {

	
	private String param;
	private String status;
	private String remarks;
	private String quoteno;
	private String polTypeId;
	private String coverId;
	private String schemeId;
	private String loginId;
	private String branchcode;
	private String brokerCode;
	private String issuer;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("Type")
	private String type;
}
