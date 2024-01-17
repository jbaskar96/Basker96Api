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
public class LoginChangePassReq {

	@JsonProperty("Password")
	private String password;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("RePassword")
	private String rePassword;
}
