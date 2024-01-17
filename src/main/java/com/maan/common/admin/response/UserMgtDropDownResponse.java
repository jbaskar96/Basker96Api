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
public class UserMgtDropDownResponse {

	@JsonProperty("CountryDetails")
	private List<UserCountryResponse> countryDetails;
	@JsonProperty("CityDetails")
	private List<UserCityResponse> cityDetails;
	@JsonProperty("NationalityDetails")
	private List<UserNationalityResponse> nationalityDetails;
	@JsonProperty("TitleDetails")
	private List<UserTitleResponse> titleDetails;
}
