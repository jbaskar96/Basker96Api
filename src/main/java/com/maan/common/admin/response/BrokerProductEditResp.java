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
public class BrokerProductEditResp {

	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("Commission")
	private String commission;
	@JsonProperty("InsuranceEndLimit")
	private String insuranceEndLimit;
	@JsonProperty("DiscountPremium")
	private String discountPremium;
	@JsonProperty("MinPremiumAmount")
	private String minPremiumAmount;
	@JsonProperty("BackDateAllowed")
	private String backDateAllowed;
	@JsonProperty("LoadingPremium")
	private String loadingPremium;
	@JsonProperty("Provision")
	private String provision;
	@JsonProperty("Freight")
	private String freight;
	@JsonProperty("PayReceipt")
	private String payReceipt;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("CustomerName")
	private String customerName;
}
