package com.maan.common.auth.dto.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.maan.common.error.Error;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelLoginResponse {

	@JsonProperty("LoginResponse")
    private AuthToken loginResponse;
	@JsonProperty("Errors")
    private List<Error> errors;
	@JsonProperty("Token")
    private String Token;
}
