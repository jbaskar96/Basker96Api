package com.maan.common.admin.response;

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
public class CommonBranchReportResp {	
	
	@JsonProperty("Result")
	private List<BranchReportResponse> reportResponse;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean status;

	@JsonProperty("ErrorMessage")
	private List<Error> errors;

	@JsonProperty("ErroCode")
	private int erroCode;

}
