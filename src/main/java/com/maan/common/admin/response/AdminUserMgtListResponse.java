package com.maan.common.admin.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.auth.dto.resp.DefaultAllResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserMgtListResponse {

	@JsonProperty("Result")
	private List<UserDetailsResponse> userDetails;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errorMessage;

	@JsonProperty("ErroCode")
	private int erroCode;
	
	@JsonProperty("AdditionalData")
	private DefaultAllResponse defaultValue;

}
