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
public class AdminNewBrokerReq {
	
	@JsonProperty("ValidNcheck")
	private String validNcheck;
	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("RegionCode")
	private String regionCode;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("CustFirstName")
	private String custFirstName;
	@JsonProperty("CustLastName")
	private String custLastName;
	@JsonProperty("Nationality")
	private String nationality;
	@JsonProperty("DateOfBirth")
	private String dateOfBirth;
	@JsonProperty("Gender")
	private String gender;
	@JsonProperty("TelephoneNo")
	private String telephoneNo;
	@JsonProperty("MobileNo")
	private String mobileNo;
	@JsonProperty("Fax")
	private String fax;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Address1")
	private String address1;
	@JsonProperty("Address2")
	private String address2;
	@JsonProperty("Occupation")
	private String occupation;
	@JsonProperty("PoBox")
	private String poBox;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("City")
	private String city;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("BorkerOrganization")
	private String borkerOrganization;
	@JsonProperty("MissippiId")
	private String missippiId;
	@JsonProperty("Approvedby")
	private String approvedby;
	@JsonProperty("BrokerCode")
	private String brokerCode;
	@JsonProperty("Executive")
	private String executive;
	@JsonProperty("OneOffCommission")
	private String oneOffCommission;
	@JsonProperty("OpenCoverCommission")
	private String openCoverCommission;
	@JsonProperty("PolicyFeeStatus")
	private String policyFeeStatus;
	@JsonProperty("GovtFeeStatus")
	private String govtFeeStatus;
	@JsonProperty("PolicyFee")
	private String policyFee;
	@JsonProperty("GovetFee")
	private String govetFee;
	@JsonProperty("EmergencyFund")
	private String emergencyFund;
	@JsonProperty("EmergencyFee")
	private String emergencyFee;
	@JsonProperty("TaxApplicable")
	private String taxApplicable;
	@JsonProperty("EffectiveDate")
	private String effectiveDate;
	@JsonProperty("Password")
	private String password;
	@JsonProperty("RePassword")
	private String rePassword;
	@JsonProperty("SubBranchCode")
	private String subBranchCode;
	@JsonProperty("Mode")
	private String mode;
	@JsonProperty("OtherCity")
	private String otherCity;
}
