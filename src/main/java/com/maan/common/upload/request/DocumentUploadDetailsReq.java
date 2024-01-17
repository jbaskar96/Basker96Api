package com.maan.common.upload.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentUploadDetailsReq {

	@JsonProperty("DocTypeId")
	private String doc_type_id;
	@JsonProperty("FilePathName")
	private String file_path_name;
	@JsonProperty("UploadedTime")
	private String uploaded_time;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("ProductId")
	private String product_id;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("FileName")
	private String file_name;
	@JsonProperty("VTypeId")
	private String vtype_id;
	@JsonProperty("OpStatus")
	private String op_status;
	@JsonProperty("OpRemarks")
	private String op_remarks;
	@JsonProperty("UploadType")
	private String upload_type;
	@JsonProperty("DocId")
	private String doc_id;
	@JsonProperty("InsId")
	private String ins_id;

	@JsonProperty("DocumentDesc")
	private String doc_desc;
	@JsonProperty("DocumentDescArabic")
	private String doc_desc_arabic;
	@JsonProperty("Param")
	private String param;
	@JsonProperty("ErrorRes")
	private String errorRes;
	@JsonProperty("DetectPerc")
	private String detectperc;
	@JsonProperty("Valueto_Detect")
	private String valueto_detect;

	@JsonProperty("PartyNo")
	private String partyNo;
	@JsonProperty("LossId")
	private String lossId;
	@JsonProperty("Waiveoffapproved")
	private String Waiveoffapproved;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("Waiveoffdate")
	private Date waiveoffdate;
	@JsonProperty("Waiveoffby")
	private String waiveoffby;

	@JsonProperty("ByteArray")
	byte[] bytearr;
	@JsonProperty("CreatedBy")
	private String createdby;
	@JsonProperty("Param2")
	private String param2;
}
