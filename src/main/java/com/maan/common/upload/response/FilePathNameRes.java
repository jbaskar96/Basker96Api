package com.maan.common.upload.response;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilePathNameRes {


	@JsonProperty("ClaimNo")
	private String claimno ;
	@JsonProperty("ReqRefNo")
	private String reqrefno;
	@JsonProperty("DocTypeId")
	private String doctypeid ;
	@JsonProperty("FilePathName")
	private String filepathname ;
	@JsonProperty("UploadedTime")
	private String uploadedtime;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("ProductId")
	private String productid;
	@JsonProperty("status")
	private String status;
	@JsonProperty("FileName")
	private String filename;
	@JsonProperty("VTypeId")
	private String vtypeid;
	@JsonProperty("OpStatus")
	private String opstatus;
	@JsonProperty("OpRemarks")
	private String opremarks;
	@JsonProperty("UploadType")
	private String uploadtype;
	@JsonProperty("DocId")
	private String docid;
	@JsonProperty("Param")
	private String param;
	@JsonProperty("Param2")
	private String param2;
	@JsonProperty("Param3")
	private String param3;
	@JsonProperty("Detect")
	private String detect;
	@JsonProperty("CommonFilePath")
	private String commonfilepath ;
	@JsonProperty("Errorres")
	private String errorres ;
	@JsonProperty("DetectPerc")
	private String detectperc ;
	@JsonProperty("InsId")
	private String insid;
	@JsonProperty("AmendId")
	private String amendid ;
	@JsonProperty("CreatedBy")
	private String createdby ;
	@JsonProperty("IMG_URL")
	private String imgurl;
	@JsonProperty("Documentref")
	private String docrefno;
		
}
