package com.maan.common.upload.response;

import java.util.List;

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
public class LandmarkAnnotations {

	/*@JsonProperty("boundingPoly")
	private BoundingPolys boudpoly;*/
	@JsonProperty("description")
	private String descriptiontext;
	@JsonProperty("locations")
	private List<Locations> locationRes;
	@JsonProperty("mid")
	private String imgmid;
	@JsonProperty("score")
	private String imgscore;
}
