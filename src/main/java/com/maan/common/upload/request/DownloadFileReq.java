package com.maan.common.upload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DownloadFileReq {


	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("DocTypeId")
	private String docTypeId;
	@JsonProperty("PassportNo")
	private String passportNo;
	@JsonProperty("ProductId")
	private String productId;
}
