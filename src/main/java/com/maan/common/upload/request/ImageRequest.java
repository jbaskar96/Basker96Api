package com.maan.common.upload.request;

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
public class ImageRequest {

	@JsonProperty("FilePath")
	private String filePath;
	@JsonProperty("ByteArray")
	byte[] bytearr;

}
