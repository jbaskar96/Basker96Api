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
public class SafeSearchAnnotation {

	@JsonProperty("adult")
	private String adultres;
	@JsonProperty("medical")
	private String medicalres;
	@JsonProperty("racy")
	private String racyres;
	@JsonProperty("spoof")
	private String spoofres;
	@JsonProperty("violence")
	private String violenceres;
	
}
