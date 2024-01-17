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
public class BrokerTaxDetailsResp {

	@JsonProperty("PolicyFeeStatus")
	private String policyFeeStatus;
	@JsonProperty("PolicyFees")
	private String policyFees;
	@JsonProperty("GovtTaxStatus")
	private String govtTaxStatus;
	@JsonProperty("GovtTax")
	private String govtTax;
	@JsonProperty("EmergencyFundStatus")
	private String emergencyFundStatus;
	@JsonProperty("EmergencyFund")
	private String emergencyFund;
	@JsonProperty("effectiveDate")
	private String effectiveDate;
	@JsonProperty("TaxApplicable")
	private String taxApplicable;
}
