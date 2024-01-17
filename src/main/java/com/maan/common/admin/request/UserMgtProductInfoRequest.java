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
public class UserMgtProductInfoRequest {

	@JsonProperty("ProductYN")
	private String productYN;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("ProductName")
	private String productName;
	@JsonProperty("InsuranceEndLimit")
	private String insuranceEndLimit;
	@JsonProperty("SpecialDiscount")
	private String specialDiscount;
	@JsonProperty("UserFreight")
	private String userFreight;
	@JsonProperty("PayReceipt")
	private String payReceipt;
}
