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
public class WhatsDocUpload {

	@JsonProperty("file")
	private String fil;
	
	@JsonProperty("LossId")
	private String lossid;
	@JsonProperty("PartyNo")
	private String partyno;
	@JsonProperty("DocTypeId")
	private String doctypeid;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("FileName")
	private String filename;
	@JsonProperty("CreatedBy")
	private String createdby;
	@JsonProperty("ClaimNo")
	private String claimno;
	
	

}
