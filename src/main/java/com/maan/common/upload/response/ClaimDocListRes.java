package com.maan.common.upload.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDocListRes {

	@JsonProperty("amendId")
	private String amendId;
	@JsonProperty("companyId")
	private String companyId;
	@JsonProperty("documentId")
	private String documentId;	
	@JsonProperty("ProductId")
	private String ProductId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("documentDesc")
	private String documentDesc;
	@JsonProperty("docApplicable")
	private String docApplicable;
	@JsonProperty("entryDate")
	private String entryDate;
	
	@JsonProperty("Claimno")
	private String claimNo;
	@JsonProperty("Documentref")
	private String documentRef;
	@JsonProperty("Doctypeid")
	private String docTypeId;
	@JsonProperty("FileName")
	private String fileName ;
	@JsonProperty("FilePathName")
	private String filePathName ;
	@JsonProperty("PartyNo")
	private String partyno;
	@JsonProperty("LossId")
	private String lossid;
	@JsonProperty("Waiveoffyn")
	private String waiveoffyn;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("Waiveoffdate")
	private Date waiveoffdate;
	@JsonProperty("Waiveoffby")
	private String waiveoffby;
	
	@JsonProperty("Param")
	private String param;
	@JsonProperty("Waiveoffclaimremarks")
	private String waiveoffclaimremarks;
	@JsonProperty("IMG_URL")
	private String imgurl;


}
