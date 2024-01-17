package com.maan.common.upload.response;

import org.springframework.web.multipart.MultipartFile;

import com.maan.common.upload.request.DocumentUploadDetailsReqRes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileJson {
	  private MultipartFile file;
	  private DocumentUploadDetailsReqRes docUploadDetails;
}
