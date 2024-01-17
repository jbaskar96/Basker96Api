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
public class UserMgtInsertRequest {

	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("CustFirstName")
	private String custFirstName;
	@JsonProperty("Nationality")
	private String nationality;
	@JsonProperty("City")
	private String city;
	@JsonProperty("PoBox")
	private String poBox;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("TelephoneNo")
	private String telephoneNo;
	@JsonProperty("MobileNo")
	private String mobileNo;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("Mode")
	private String mode;
	@JsonProperty("Password")
	private String password;
	@JsonProperty("RePassword")
	private String rePassword;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("UserAgencyCode")
	private String userAgencyCode;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("CustLastName")
	private String custLastName;
	@JsonProperty("DateOfBirth")
	private String dateOfBirth;
	@JsonProperty("Gender")
	private String gender;
	@JsonProperty("Fax")
	private String fax;
	@JsonProperty("Address1")
	private String address1;
	@JsonProperty("Address2")
	private String address2;
	@JsonProperty("Occupation")
	private String occupation;
	@JsonProperty("SubBranchCode")
	private String subBranchCode;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("UserType")
	private String userType;
}
