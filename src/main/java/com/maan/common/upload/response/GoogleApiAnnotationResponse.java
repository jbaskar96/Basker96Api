package com.maan.common.upload.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.error.Errors;

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
public class GoogleApiAnnotationResponse {

	@JsonProperty("labelAnnotations")
	private List<LabelAnnotationRes> labelResponse;
	@JsonProperty("safeSearchAnnotation")
	private SafeSearchAnnotation safesearchResponse;
	@JsonProperty("textAnnotations")
	private List<TextAnnotations> textResponse;
	@JsonProperty("landmarkAnnotations")
	private List<LandmarkAnnotations> landmarkResponse;	
	@JsonProperty("faceAnnotations")
	private List<FaceAnnotations> faceannotation;
	
	@JsonProperty("Errors")
	private List<Errors> errors;
	
}
