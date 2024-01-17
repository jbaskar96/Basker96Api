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
public class UserNationalityResponse {

	@JsonProperty("NationalityCode")
	private String nationalityCode;
	@JsonProperty("NationalityName")
	private String nationalityName;
}
