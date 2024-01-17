package com.maan.common.upload.service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.maan.common.error.Error;
import com.maan.common.upload.request.DocumentUploadDetailsDto;
import com.maan.common.upload.request.DocumentUploadRes;
import com.maan.common.upload.request.FilePathNameReq;
import com.maan.common.upload.request.WhatsappReqClaim;
import com.maan.common.upload.response.ClaimDocListRes;
import com.maan.common.upload.response.FileJson;
import com.maan.common.upload.response.FilePathNameRes;
import com.maan.common.upload.response.SucessRes;

public interface FilesStorageService {

	public String save(FileJson filejson);

	public Resource load(String filename);

	public Stream<Path> loadAll(String claimNo);

	List<ClaimDocListRes> getdoclist(DocumentUploadDetailsDto req);

	FilePathNameRes getFilePath(FilePathNameReq req);

	public DocumentUploadRes deletedoc(DocumentUploadDetailsDto req);

	public SucessRes wayoff(DocumentUploadDetailsDto req);

	public List<com.maan.common.error.Error> waiveOffValidation(DocumentUploadDetailsDto req);

	public List<Error> whatsappclaimandpartyandlossval(WhatsappReqClaim whreq);

	public List<Error> doctypevalidation(MultipartFile file);

}

