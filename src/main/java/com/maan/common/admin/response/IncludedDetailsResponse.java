package com.maan.common.admin.response;

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
public class IncludedDetailsResponse {

	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("ContactPerson")
	private String contactPerson;
	@JsonProperty("Address1")
	private String address1;
	@JsonProperty("Address2")
	private String address2;
	@JsonProperty("Address3")
	private String address3;
	@JsonProperty("City")
	private String city;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("PhoneNo")
	private String phoneNo;
	@JsonProperty("CityName")
	private String cityName;
	@JsonProperty("Fax")
	private String fax;
	@JsonProperty("PoBox")
	private String poBox;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("MissippiId")
	private String missippiId;
	@JsonProperty("ApprovedPreparedBy")
	private String approvedPreparedBy;
	@JsonProperty("RsaBrokeCode")
	private String rsaBrokeCode;
	@JsonProperty("AcExecutiveId")
	private String acExecutiveId;
	@JsonProperty("BranchName")
	private String branchName;
	@JsonProperty("ProductId")
	private List<String> productId;
}
