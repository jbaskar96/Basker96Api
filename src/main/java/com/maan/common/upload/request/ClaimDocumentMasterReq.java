package com.maan.common.upload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDocumentMasterReq {
	
	@JsonProperty("documentId")
	private String documentId;
	@JsonProperty("docApplicable")
	private String docApplicable;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("InsuranceId")
	private String companyId;
	@JsonProperty("documentDesc")
	private String documentDesc;
	@JsonProperty("policyType")
	private String policyType;
	@JsonProperty("status")
	private String status;
	@JsonProperty("mandatoryStatus")
	private String mandatoryStatus;
	@JsonProperty("entryDate")
	private String entryDate;
	@JsonProperty("displayOrder")
	private String displayOrder;
	@JsonProperty("remarks")
	private String remarks;
	@JsonProperty("effectiveDate")
	private String effectiveDate;
	@JsonProperty("amendId")
	private String amendId;
	@JsonProperty("documentDescArabic")
	private String documentDescArabic;
	@JsonProperty("apiCheck")
	private String apiCheck;
	@JsonProperty("apiCheckName")
	private String apiCheckName;
	@JsonProperty("coreAppcode")
	private String coreAppcode;
	@JsonProperty("agencyCode")
	private String agencyCode;

}
