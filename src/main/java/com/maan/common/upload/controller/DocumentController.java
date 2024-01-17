package com.maan.common.upload.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maan.common.error.CommonValidationException;
import com.maan.common.upload.request.DocumentUploadDetailsReqRes;
import com.maan.common.upload.request.DocumentUploadRes;
import com.maan.common.upload.request.DownloadFileReq;
import com.maan.common.upload.service.DocumentService;

@RequestMapping("/document")
@RestController
public class DocumentController {

	@Autowired
	private DocumentService docSer;

	@PostMapping("/showdocdet")
	public DocumentUploadDetailsReqRes showdocdet(@RequestBody DocumentUploadDetailsReqRes request) {
		DocumentUploadDetailsReqRes response = docSer.showdocdet(request);
		return response;
	}

	@PostMapping("/uploadeddocdet")
	public DocumentUploadDetailsReqRes uploadeddocdet(@RequestBody DocumentUploadDetailsReqRes request) {
		DocumentUploadDetailsReqRes response = docSer.uploadeddocdet(request);
		return response;
	}

	@PostMapping("/uploadingdocdet")
	public String uploadingdocdet(@RequestBody DocumentUploadDetailsReqRes request) throws CommonValidationException {
		String response = docSer.uploadingdocdet(request);
		return response;
	}

	@PostMapping("/validatedocdet")
	public String validatedocdet(@RequestBody DocumentUploadDetailsReqRes request) throws CommonValidationException {
		String response = docSer.validatedocdet(request);
		return response;
	}

	@PostMapping("/deletedocdet")
	public String deletedocdet(@RequestBody DocumentUploadDetailsReqRes request) {
		String response = docSer.deletedocdet(request);
		return response;
	}

	@PostMapping("/uploadfile")
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("QuoteNo") String quoteno,
			@RequestParam("DocId") String docId) {
		String response = docSer.uploadFile(file,quoteno,docId);
		return response;
	}
	
	@PostMapping("/downloadfile")
	public ResponseEntity<Resource> downloadfile(@RequestParam("filepath") String filepath) {
		ResponseEntity<Resource> response = docSer.downloadfile(filepath);
		return response;
	}
	
	@PostMapping("/deletefile")
	public String deletefile(@RequestParam("filepath") String filepath) {
		String response = docSer.deletefile(filepath);
		return response;
	}
	@PostMapping("/upload")
    public DocumentUploadRes uploadFile(@RequestParam("files") MultipartFile file, @RequestParam("QuoteNo") String QuoteNo,@RequestParam("DocTypeId") String DocTypeId, 
    		@RequestParam("Description") String Description, @RequestParam("ProductId") String proid, @RequestParam("FileName") String FileName, @RequestParam("Uploadtype") String uploadtype, 
    		@RequestParam("DocId") String docid, @RequestParam("PassportNo") String Param, @RequestParam("DocDetect") String docDetect) throws CommonValidationException {
        return docSer.storeFile(file, QuoteNo,DocTypeId,Description,proid,FileName,uploadtype,docid,Param,docDetect);

    }
	 @PostMapping("/download")
	    public ResponseEntity<Resource> downloadFile(@RequestBody DownloadFileReq req,HttpServletRequest request) {
	    	String fileName = docSer.getDocumentName(req);
	        Resource resource = null;
	        if(fileName !=null && !fileName.isEmpty()) {
	        try {
	            resource = docSer.loadFileAsResource(fileName);
	        } catch (Exception e) {
	            e.printStackTrace();
	
	        }
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	
	        }
	        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
	        		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
	
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	    }  
	    
	    @PostMapping("/delete")
	    public String deleteFile(@RequestBody DownloadFileReq req) {
	    	String res="";
	    	String fileName = docSer.getDocumentName(req);
	    	try {
	    		res = docSer.deleteFileSource(req,fileName);
	    	}catch (Exception e) {
				e.printStackTrace();
			}
	    	return res;
	    }
}
