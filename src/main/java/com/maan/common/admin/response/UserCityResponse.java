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
public class UserCityResponse {

	@JsonProperty("CityCode")
	private String cityCode;
	@JsonProperty("CityName")
	private String cityName;
}
