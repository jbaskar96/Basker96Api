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
public class UserCommisionEditResp {

	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("ProductName")
	private String productName;
	@JsonProperty("SpecialDiscount")
	private String specialDiscount;
	@JsonProperty("Freight")
	private String freight;
	@JsonProperty("InsuranceEndLimit")
	private String insuranceEndLimit;
	@JsonProperty("Receipt")
	private String receipt;
	@JsonProperty("OpenCoverNo")
	private String openCoverNo;
	@JsonProperty("ProductYN")
	private String productYN;
}
