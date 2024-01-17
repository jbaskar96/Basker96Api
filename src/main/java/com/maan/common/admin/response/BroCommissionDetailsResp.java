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
public class BroCommissionDetailsResp {

	@JsonProperty("ProductName")
	private String productName;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("Commission")
	private String commission;
	@JsonProperty("InsuranceEndLimit")
	private String insuranceEndLimit;
	@JsonProperty("SpecialDiscount")
	private String specialDiscount;
	@JsonProperty("ReferalStatus")
	private String referalStatus;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("DiscountPremium")
	private String discountPremium;
	@JsonProperty("MinPremiumAmount")
	private String minPremiumAmount;
	@JsonProperty("BackDateAllowed")
	private String backDateAllowed;
	@JsonProperty("ProductCommission")
	private String productCommission;
	@JsonProperty("ProductStartDate")
	private String productStartDate;
	@JsonProperty("ProductExpiryDate")
	private String productExpiryDate;
	@JsonProperty("LoadingPremium")
	private String loadingPremium;
	@JsonProperty("PayReceiptStatus")
	private String payReceiptStatus;
	@JsonProperty("ReceiptStatus")
	private String receiptStatus;
	@JsonProperty("FreightDebitOption")
	private String freightDebitOption;
	@JsonProperty("ProvisionforPremium")
	private String provisionforPremium;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("PolicyType")
	private String policyType;
	@JsonProperty("CheckerYn")
	private String checkerYn;
}
