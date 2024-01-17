package com.maan.common.upload.controller;

 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maan.common.error.CommonValidationException;
import com.maan.common.error.Error;
import com.maan.common.master.service.PrintReqService;
import com.maan.common.upload.config.BASE64DecodedMultipartFile;
import com.maan.common.upload.request.DocumentUploadDetailsDto;
import com.maan.common.upload.request.DocumentUploadDetailsReq;
import com.maan.common.upload.request.DocumentUploadDetailsReqRes;
import com.maan.common.upload.request.DocumentUploadRes;
import com.maan.common.upload.request.FilePathNameReq;
import com.maan.common.upload.request.WhatsDocUpload;
import com.maan.common.upload.request.WhatsappReqClaim;
import com.maan.common.upload.response.ClaimDocListRes;
import com.maan.common.upload.response.FileJson;
import com.maan.common.upload.response.FilePathNameRes;
import com.maan.common.upload.response.ResponseMessage;
import com.maan.common.upload.response.SucessRes;
import com.maan.common.upload.service.FilesStorageService;

@Controller
public class FilesController {

	@Autowired
	FilesStorageService storageService;
	@Autowired
	private  PrintReqService dateformat;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestBody DocumentUploadDetailsReqRes req) throws CommonValidationException {
		
		dateformat.reqPrint(req);
		
		String message = "";
		MultipartFile file=null;
		
		FileJson filejson = new FileJson();
		DocumentUploadDetailsReqRes docdetails = new DocumentUploadDetailsReqRes();
		
		try {
			docdetails = req;
			if(req.getFile().indexOf(",")!=-1)
			 file= new BASE64DecodedMultipartFile(Base64Utils.decodeFromString(req.getFile().split(",")[1]),docdetails.getDocumentupldet().get(0).getFile_name());
			else
				throw new RuntimeException("Please Check Your Upload File!!");
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Your Uploaded correct File!!"+e.getLocalizedMessage() ) ;
		}
		
		//Mobile No and MailId Validation Block		
    	List<Error> error = new ArrayList<>();
		error = storageService.doctypevalidation(file);		
		if (error != null && error.size() > 0)
				throw  new CommonValidationException(error,file);
		
		try {
			
			filejson.setFile(file);
			filejson.setDocUploadDetails(docdetails);		
			message = storageService.save(filejson);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			e.printStackTrace();
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
	
	//whatsapp
	@PostMapping("/whatsappupload")
	public ResponseEntity<ResponseMessage> whatsappupload(@RequestBody WhatsDocUpload req) throws CommonValidationException {
		String message = "";
		MultipartFile file=null;
			
			WhatsappReqClaim whreq = new WhatsappReqClaim();
			whreq.setClaimrefno(req.getClaimno());
			whreq.setDoctypeid(new BigDecimal(req.getDoctypeid()));
			whreq.setLosstypeid(new BigDecimal(req.getLossid()));
			whreq.setPartyno(new BigDecimal(req.getPartyno()));
			
			
	    	List<Error> error = new ArrayList<>();
			error = storageService.whatsappclaimandpartyandlossval(whreq);
			if (error != null && error.size() > 0) {
					throw  new CommonValidationException(error,null);
			}
			
		try {	
			FileJson filejson = new FileJson();
			ObjectMapper mapper=	new ObjectMapper();
			DocumentUploadDetailsReqRes docdetails = new DocumentUploadDetailsReqRes();
			
			List<DocumentUploadDetailsReq> doclist = new ArrayList<DocumentUploadDetailsReq>();
			DocumentUploadDetailsReq docdetailsreq = new DocumentUploadDetailsReq();
			
			docdetailsreq.setLossId(req.getLossid());
			docdetailsreq.setPartyNo(req.getPartyno());
			docdetailsreq.setDoc_type_id(req.getDoctypeid());
			docdetailsreq.setDoc_id(req.getDoctypeid());
			docdetailsreq.setDescription(req.getDescription());
			docdetailsreq.setFile_name(req.getFilename());
			docdetailsreq.setCreatedby(req.getCreatedby());
			
			docdetailsreq.setParam("WhatsApp");
			docdetailsreq.setUpload_type("CLAIM");
			docdetailsreq.setProduct_id("66");
			docdetailsreq.setIns_id("100002");
			
			doclist.add(docdetailsreq);
			
			docdetails.setDocumentupldet(doclist);
			docdetails.setQuote_no(req.getClaimno());			
			docdetails.setProduct_id("66");
			
			try {
				if(req.getFil().indexOf(",")!=-1)
				 file= new BASE64DecodedMultipartFile(Base64Utils.decodeFromString(req.getFil().split(",")[1]),docdetails.getDocumentupldet().get(0).getFile_name());
				else
					throw new RuntimeException("Please Check Your Upload File!!");
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Your Uploaded correct File!!"+e.getLocalizedMessage() ) ;
			}
			
			filejson.setFile(file);
			filejson.setDocUploadDetails(docdetails);
		
			message = storageService.save(filejson);

			if(message.equals("Inserted Successfully"))
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			else
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
		} catch (Exception e) {
			e.printStackTrace();
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	
/*
	@GetMapping("/files")
	public ResponseEntity<List<FileInfo>> getListFiles() {
		List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

			return new FileInfo(filename, url);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}
*/
	@GetMapping("/files/{filename}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	
	@PostMapping("/getimagefile" )	 
	private ResponseEntity<FilePathNameRes>  getFilePath(@RequestBody FilePathNameReq req) {
		FilePathNameRes    lst = storageService.getFilePath(req);
		
		  if (lst!=null) {
				return new ResponseEntity<FilePathNameRes>(lst,HttpStatus.OK);
			}else{
				return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			}
	}

	
	@PostMapping("/getdoclist")	
	public ResponseEntity<List<ClaimDocListRes>> getdoclist(@RequestBody DocumentUploadDetailsDto req) {
		
		List<ClaimDocListRes> lst = storageService.getdoclist(req);

        if (lst!=null) {
			return new ResponseEntity<List<ClaimDocListRes>>(lst,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/deletedoc")	
	public ResponseEntity<DocumentUploadRes> deletedoc(@RequestBody DocumentUploadDetailsDto req) {
		
		DocumentUploadRes lst = storageService.deletedoc(req);

        if (lst!=null) {
			return new ResponseEntity<DocumentUploadRes>(lst,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/wayoff")	
	public ResponseEntity<SucessRes> wayoff(@RequestBody DocumentUploadDetailsDto req) throws CommonValidationException {
		
    	List<Error> error  = storageService.waiveOffValidation(req);
		if (error != null && error.size() > 0)
				throw  new CommonValidationException(error,req);
		
		SucessRes lst = storageService.wayoff(req);

        if (lst!=null) {
			return new ResponseEntity<SucessRes>(lst,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
	}

}
