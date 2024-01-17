package com.maan.common.upload.request;

import java.util.List;

import com.maan.common.error.Error;
import com.maan.common.upload.response.GoogleApiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDocRes {

	private GoogleApiResponse googleapiRes;

	private List<DocumentUploadDetailsReq> doclist;

	private DocumentUploadDetailsReq doc;

	private List<Error> errors;

	private String detectedValue;
	private String detectperc;
	private String insyn;
	private String connector;
	private String param_value;

	private boolean param_check;

}
