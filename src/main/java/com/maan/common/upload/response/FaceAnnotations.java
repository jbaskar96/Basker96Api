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
public class FaceAnnotations {

	@JsonProperty("angerLikelihood")
	private String angerLikelihooddesc;
	@JsonProperty("blurredLikelihood")
	private String blurredLikelihooddesc;
	@JsonProperty("headwearLikelihood")
	private String headwearLikelihooddesc;
	@JsonProperty("joyLikelihood")
	private String joyLikelihooddesc;
	@JsonProperty("sorrowLikelihood")
	private String sorrowLikelihooddesc;
	@JsonProperty("surpriseLikelihood")
	private String surpriseLikelihooddesc;
	@JsonProperty("underExposedLikelihood")
	private String underExposedLikelihooddesc;
	
}
