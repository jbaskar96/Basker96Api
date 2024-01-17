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
public class BrokerDetailsResponse {

	@JsonProperty("RegionCode")
	private String regionCode;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Gender")
	private String gender;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("Nationality")
	private String nationality;
	@JsonProperty("NationalityName")
	private String nationalityName;
	@JsonProperty("DateOfBirth")
	private String dateOfBirth;
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
	@JsonProperty("City")
	private String city;
	@JsonProperty("CityName")
	private String cityName;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("CountryName")
	private String countryName;
	@JsonProperty("Pobox")
	private String pobox;
	@JsonProperty("CompanyName")
	private String companyName ;
	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("EntryDate")
	private String entryDate;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Address3")
	private String address3;
	@JsonProperty("MissippiId")
	private String missippiId;
	@JsonProperty("ApprovedPreparedBy")
	private String approvedPreparedBy;
	@JsonProperty("BrokerCode")
	private String rsaBrokerCode;
	@JsonProperty("AcExecutiveId")
	private String acExecutiveId;
	@JsonProperty("customerName")
	private String customerName;
	@JsonProperty("CustomerArNo")
	private String customerArNo;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("IssuerCommissionOpenCover")
	private String issuerCommissionOpenCover;
	@JsonProperty("IssuerCommissionOneOff")
	private String issuerCommissionOneOff;
	@JsonProperty("ImagePath")
	private String imagePath;
	@JsonProperty("SubBranch")
	private String subBranch;
}
