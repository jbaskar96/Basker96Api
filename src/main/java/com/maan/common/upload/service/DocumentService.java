package com.maan.common.upload.service;

import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.maan.common.error.CommonValidationException;
import com.maan.common.upload.request.DocumentUploadDetailsReqRes;
import com.maan.common.upload.request.DocumentUploadRes;
import com.maan.common.upload.request.DownloadFileReq;

public interface DocumentService {

	DocumentUploadDetailsReqRes showdocdet(DocumentUploadDetailsReqRes request);

	DocumentUploadDetailsReqRes uploadeddocdet(DocumentUploadDetailsReqRes request);

	String validatedocdet(DocumentUploadDetailsReqRes request) throws CommonValidationException;

	String deletedocdet(DocumentUploadDetailsReqRes request);

	String uploadFile(MultipartFile file, String quoteno, String docId);

	String uploadFileToPath(InputStream is, String topath,String originalFilename);
	
	ResponseEntity<Resource> downloadfile(String filepath);
	
	String deletefile(String filepath);

	DocumentUploadRes storeFile(MultipartFile file,String quoteNo, String docTypeId, String filePathName, String description, String proid,
			String fileName, String uploadtype, String docid, String param) throws CommonValidationException;

	String getDocumentName(DownloadFileReq req);

	Resource loadFileAsResource(String fileName);

	String deleteFileSource(DownloadFileReq req, String fileName);

	String uploadingdocdet(DocumentUploadDetailsReqRes request, String commonpath) throws CommonValidationException;

	String uploadingdocdet(DocumentUploadDetailsReqRes request);


}
