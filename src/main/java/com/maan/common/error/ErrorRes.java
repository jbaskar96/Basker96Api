package com.maan.common.error;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRes {

	@JsonProperty("ReqRefNo")
	private String reqRefNo;
	@JsonProperty("AgencyCode")
	private String agencycode;
	@JsonProperty("PvPolicyType")
	private String pvpolicytype;
	@JsonProperty("PvType")
	private String pvtype;
	@JsonProperty("PvVehicleId")
	private String pvvehicleid;
	@JsonProperty("PvProduct")
	private String pvproduct;

	private boolean status;

	private String success;
	private String mcdRefno;

	@JsonProperty("UserType")
	private String usertype;
	@JsonProperty("Admin_LoginId")
	private String admin_loginid;

	@JsonProperty("ReferalRemarks")
	private String referalremarks;
	@JsonProperty("ReferralDescription")
	private String referraldesc;

	private String google_visionsno;
	private String detected_barcode;
	private String detectedValue;
	private String detectperc;
	private String insyn;

	private List<Error> errors;

}
