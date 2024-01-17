package com.maan.common.admin.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrokerLoginProductInsReq {

	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("CustomerName")
	private String customerName;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("Commission")
	private Double commission;
	@JsonProperty("InsuranceEndLimit")
	private Long insuranceEndLimit;
	@JsonProperty("DiscountPremium")
	private Long discountPremium;
	@JsonProperty("MinPremiumAmount")
	private Long minPremiumAmount;
	@JsonProperty("BackDateAllowed")
	private String backDateAllowed;
	@JsonProperty("LoadingPremium")
	private Long loadingPremium;
	@JsonProperty("Provision")
	private String provision;
	@JsonProperty("Freight")
	private String freight;
	@JsonProperty("PayReceipt")
	private String payReceipt;
	@JsonProperty("Remarks")
	private String remarks;
}
