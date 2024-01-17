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
public class WatiApiRequest {

	@JsonProperty("WhatsAppCode")
	private String whatsappCode;

	@JsonProperty("WhatsAppNo")
	private String whatsappno;

	@JsonProperty("QuoteNo")
	private String quoteNo;

	@JsonProperty("ProductId")
	private String productid;

	@JsonProperty("AgencyCode")
	private String agencycode;

	@JsonProperty("CurrentStage")
	private String currentStage;

	@JsonProperty("SubStage")
	private String subStage;

	@JsonProperty("StageOrder")
	private String stageOrder;

	@JsonProperty("Type")
	private String type;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("FileYN")
	private String fileYN;

	@JsonProperty("FilePath")
	private String filePath;

}
