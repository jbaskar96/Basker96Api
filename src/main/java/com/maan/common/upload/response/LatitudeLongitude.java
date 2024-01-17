package com.maan.common.upload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LatitudeLongitude {
	
	@JsonProperty("latitude")
	private double latitudeRes;
	@JsonProperty("longitude")
	private double longitudeRes;

}
