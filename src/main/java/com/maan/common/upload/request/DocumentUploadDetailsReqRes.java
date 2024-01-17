package com.maan.common.upload.request;

import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.maan.common.error.Error;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentUploadDetailsReqRes {

	@JsonProperty("DocumentUploadDetails")
	private List<DocumentUploadDetailsReq> documentupldet;

	@JsonProperty("QuoteNo")
	private String quote_no;

	@JsonProperty("ProductId")
	private String product_id;
	
	@JsonProperty("file")
	private String file;

}
