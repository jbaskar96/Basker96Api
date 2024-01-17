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
public class BrokerInformationResp {

	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("PoBox")
	private String poBox;
	@JsonProperty("Mobile")
	private String mobile;
	@JsonProperty("CityName")
	private String cityName;
	@JsonProperty("CityCode")
	private String cityCode;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("MissippiCustomerCode")
	private String missippiCustomerCode;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Address1")
	private String address1;
	@JsonProperty("Address2")
	private String address2;
	@JsonProperty("Address")
	private String address;
	@JsonProperty("CustomerArNo")
	private String customerArNo;
	@JsonProperty("CustomerName")
	private String customerName;
	@JsonProperty("VatRegNo ")
	private String vatRegNo;
	@JsonProperty("CustomerArabicName")
	private String customerArabicName;
}
