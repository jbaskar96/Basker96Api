package com.maan.common.admin.response;

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
public class AdminBrokerInsertResponse1 {

	@JsonProperty("Result")
	private AdminBrokerInsertResponse result;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errorMessage;

	@JsonProperty("ErroCode")
	private int erroCode;

}
