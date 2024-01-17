package com.maan.common.upload.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.error.Error;

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
public class GoogleApiResponse {

	@JsonProperty("responses")
	private List<GoogleApiAnnotationResponse> imageresponse;

	@JsonProperty("Errors")
	private List<Error> errors;

}
