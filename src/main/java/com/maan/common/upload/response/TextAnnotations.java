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
public class TextAnnotations {

	/*@JsonProperty("boundingPoly")
	private BoundingPolys boundingPolyResponse;*/
	@JsonProperty("description")
	private String descriptiontext;
	@JsonProperty("locale")
	private String localelang;
}
