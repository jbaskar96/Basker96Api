package com.maan.common.jasper.request;

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
public class JasperDocumentReq {

	@JsonProperty("Quoteno")
	private String quoteNo;
	
	@JsonProperty("Branch")
	private String pvbranch;

	@JsonProperty("Vehicle")
	private String pvvechicle;
	
	@JsonProperty("Product")
	private String pvproduct;
	
	@JsonProperty("Username")
	private String pvusername;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("LoginId")
	private String pvLoginId;
	
	@JsonProperty("Type")
	private String pvtype;
		
	@JsonProperty("Startdate")
	private String pvstartdate;
	
	@JsonProperty("Enddate")
	private String pvenddate;
	
	
	@JsonProperty("Cover")
	private String pvcover;
	
	@JsonProperty("Input1")
	private String pvinput1;
	
	@JsonProperty("Input2")
	private String pvinput2;
	
	@JsonProperty("User")
	private String pvuser;
	

	@JsonProperty("ImagePath")
	private String imagePath;
}
