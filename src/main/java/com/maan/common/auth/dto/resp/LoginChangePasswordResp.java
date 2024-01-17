package com.maan.common.auth.dto.resp;

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
public class LoginChangePasswordResp {

	@JsonProperty("Message")
	private String message;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("Errors")
	private List<Error> errors = null;
}
