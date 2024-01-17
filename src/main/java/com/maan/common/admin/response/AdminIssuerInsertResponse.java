package com.maan.common.admin.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.auth.dto.resp.DefaultAllResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.maan.common.error.Error;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminIssuerInsertResponse {

	@JsonProperty("Result")
	private  boolean status;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private boolean IsError;

	@JsonProperty("ErrorMessage")
	private List<Error> errors;

	@JsonProperty("ErroCode")
	private int erroCode;

	@JsonProperty("DefaultValue")
	private DefaultAllResponse defaultValue;
}
