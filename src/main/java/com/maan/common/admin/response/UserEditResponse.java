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
public class UserEditResponse {

	@JsonProperty("RegionCode")
	private String regionCode;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Gender")
	private String gender;
	@JsonProperty("UserId")
	private String userId;
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
	@JsonProperty("City")
	private String city;
	@JsonProperty("CityName")
	private String cityName;
	@JsonProperty("UserType")
	private String userType;
	@JsonProperty("BrokerOrganization")
	private String brokerOrganization;
	@JsonProperty("customerName")
	private String customerName;
	@JsonProperty("CustomerArNo")
	private String customerArNo;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("SubBranch")
	private String subBranch;
	@JsonProperty("UserAgencyCode")
	private String userAgencyCode;
}
