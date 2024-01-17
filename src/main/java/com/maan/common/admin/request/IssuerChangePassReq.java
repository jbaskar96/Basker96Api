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
public class IssuerChangePassReq {

	@JsonProperty("Password")
	private String password;
	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("RePassword")
	private String rePassword;
}
