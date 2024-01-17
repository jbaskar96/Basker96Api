package com.maan.common.upload.request;

import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilePathNameReq {

	@JsonProperty("QuoteNo")
	private String claimnno;
	@JsonProperty("ReqRefNo")
	private String reqrefno;
	@JsonProperty("DocTypeId")
	private String doctypeid;
	@JsonProperty("ProductId")
	private String productid;
	@JsonProperty("InsId")
	private String insid;
}
