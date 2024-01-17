package com.maan.common.upload.service.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maan.common.bean.DocumentUploadDetails;
import com.maan.common.error.CommonValidationException;
import  com.maan.common.error.Error;
import com.maan.common.error.ErrorRes;
import com.maan.common.repository.DocumentUploadDetailsRepository;
import com.maan.common.upload.request.DocumentUploadDetailsReq;
import com.maan.common.upload.request.DocumentUploadDetailsReqRes;
import com.maan.common.upload.request.DocumentUploadRes;
import com.maan.common.upload.request.DownloadFileReq;
import com.maan.common.upload.service.DetectionService;
import com.maan.common.upload.service.DocumentService;

import net.coobird.thumbnailator.Thumbnails;
 

@Service
public class DocumentServiceImpl implements DocumentService {
/*
	@Autowired
	private MotorDataDetailRepo mddRepo;
	@Autowired
	private HomePositionMasterRepo hpmRepo;*/
	@Autowired
	private CommonService commomSer;
/*	@Autowired
	private DocumentUploadDetailsRepository docRepo; */
	@Autowired
	private DetectionService detectSer;
	@Autowired
	private DocumentUploadDetailsRepository docRepo;
	@Autowired
	private DocumentService docSer;
	private Path fileStorageLocation;
	@Value("${file.upload-dir}")
	private String uploadDir;
	private Logger log = LogManager.getLogger(getClass());

	@Override
	public DocumentUploadDetailsReqRes showdocdet(DocumentUploadDetailsReqRes request) {
		DocumentUploadDetailsReqRes response = new DocumentUploadDetailsReqRes();
		try {
			String quoteno = request.getQuote_no();
			String productid = StringUtils.isBlank(request.getProduct_id()) ? "65" : request.getProduct_id();
			//String agencycode = StringUtils.isBlank(request.getAgencycode()) ? "" : request.getAgencycode();
			String docid = "", param = "";

			List<Map<String, Object>> list = new ArrayList<>();
			List<Map<String, Object>> reflist = new ArrayList<>();
			List<DocumentUploadDetailsReq> docuploaddetails = new ArrayList<>();

			List<String> docids = new ArrayList<>();

			log.info("showdocdet--> quoteno: " + quoteno + " productid: " + productid);
/*
			if (productid.equals("65")) {
				MotorDataDetail mdd = mddRepo.findByMotDataDetPK_Quoteno(Long.valueOf(quoteno));
				List<HomePositionMaster> hpmList = hpmRepo.findByHpmPkQuoteno(Long.valueOf(quoteno));
				HomePositionMaster hpm = hpmList.get(0);
				Long policytype = mdd.getPolicytype();

				String refcodes = StringUtils.isBlank(hpm.getReferalcodes()) ? "" : hpm.getReferalcodes();
				String renewalyn = StringUtils.isBlank(hpm.getRenewalstatus()) ? "N" : hpm.getRenewalstatus();

				String quotetype = StringUtils.isBlank(mdd.getQuotetype()) ? "" : mdd.getQuotetype();
				String breakinsuranceyn = StringUtils.isBlank(mdd.getBreakininsurance_yn()) ? "N"
						: mdd.getBreakininsurance_yn();
				String prevcomp = StringUtils.isBlank(mdd.getPrevious_company()) ? "" : mdd.getPrevious_company();
				String manfyear = StringUtils.isBlank(mdd.getManufacture_year()) ? "" : mdd.getManufacture_year();
				String regyear = StringUtils.isBlank(mdd.getRegistration_year()) ? "" : mdd.getRegistration_year();

				String currdate = commomSer.formatdatewithouttime(new Date());
				String[] currdatesplit = currdate.split("/");
				currdate = currdatesplit[2];
				Long curryear = Long.valueOf(currdate);
				Long manfyearage = 0L, regyearage = 0L;

				manfyearage = StringUtils.isBlank(manfyear) ? 10 : (curryear - Long.valueOf(manfyear));
				regyearage = StringUtils.isBlank(regyear) ? 10 : (curryear - Long.valueOf(regyear));

				if (StringUtils.isNotBlank(refcodes)) {
					String refcodearr[] = refcodes.split(",");
					for (int i = 0; i < refcodearr.length; i++) {
						if (StringUtils.isNotBlank(refcodearr[i])) {
							Map<String, Object> docidmap = docRepo.getdocid(refcodearr[i]);
							String refdocid = docidmap.get("DOCUMENT_ID") == null ? ""
									: docidmap.get("DOCUMENT_ID").toString();
							if (StringUtils.isNotBlank(refdocid))
								docids.add(refdocid);
						}
					}
				}

				if (renewalyn.equalsIgnoreCase("Y") && breakinsuranceyn.equalsIgnoreCase("N")
						&& prevcomp.equals("1025783")) {
					list = docRepo.getdocdet_newORrenewal(quoteno, policytype, productid, agencycode);
				} else if (quotetype.equalsIgnoreCase("DQ") && manfyearage == 0 && regyearage == 0) {
					list = docRepo.getdocdet_newORrenewal(quoteno, policytype, productid, agencycode);
				} else {
					list = docRepo.getdocdet(quoteno, policytype, productid, agencycode);
				}

				if (docids != null && docids.size() > 0)
					reflist = docRepo.getdocdet_ref(quoteno, policytype, productid, docids, agencycode);

				log.info("showdocdet--> Quoteno: " + quoteno + " reflist Size: " + reflist.size());

				for (int i = 0; i < reflist.size(); i++) {
					docid = reflist.get(i).get("DOCUMENT_ID") == null ? ""
							: reflist.get(i).get("DOCUMENT_ID").toString();

					DocumentUploadDetailsReq res = DocumentUploadDetailsReq.builder().doc_id(docid)
							.doc_desc(reflist.get(i).get("DOCUMENT_DESC") == null ? ""
									: reflist.get(i).get("DOCUMENT_DESC").toString())
							.status(reflist.get(i).get("MANDATORY_STATUS") == null ? ""
									: reflist.get(i).get("MANDATORY_STATUS").toString())
							.doc_desc_arabic(reflist.get(i).get("DOCUMENT_DESC_ARABIC") == null ? ""
									: reflist.get(i).get("DOCUMENT_DESC_ARABIC").toString())
							.param("").upload_type(reflist.get(i).get("DOC_TYPE") == null ? ""
									: reflist.get(i).get("DOC_TYPE").toString())
							.build();
					docuploaddetails.add(res);
				}

			} else if (productid.equals("33")) {
				Map<String, Object> map = docRepo.getpoltypeownersid33(quoteno);

				Long poltype = map.get("TRAVEL_COVER_ID") == null ? 0L
						: Long.valueOf(map.get("TRAVEL_COVER_ID").toString());

				list = docRepo.getdocdet(quoteno, poltype, productid, agencycode);
			} else {
				Long poltype = StringUtils.isBlank(request.getPolicytype()) ? 1L
						: Long.valueOf(request.getPolicytype());

				list = docRepo.getdocdet(quoteno, poltype, productid, agencycode);
			}*/

			log.info("showdocdet--> Quoteno: " + quoteno + " Size: " + list.size());

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				
				docid = map.get("DOCUMENT_ID") == null ? "" : map.get("DOCUMENT_ID").toString();

				DocumentUploadDetailsReq res = DocumentUploadDetailsReq.builder()
						.doc_id(docid)
						.doc_desc(map.get("DOCUMENT_DESC") == null ? ""
								: map.get("DOCUMENT_DESC").toString())
						.status(map.get("MANDATORY_STATUS") == null ? ""
								: map.get("MANDATORY_STATUS").toString())
						.doc_desc_arabic(map.get("DOCUMENT_DESC_ARABIC") == null ? ""
								: map.get("DOCUMENT_DESC_ARABIC").toString())
						.param(param)
						.upload_type(map.get("DOC_TYPE") == null ? "" : map.get("DOC_TYPE").toString())
						.build();
				docuploaddetails.add(res);
			}

			response = DocumentUploadDetailsReqRes.builder()
					.quote_no(quoteno)
					.documentupldet(docuploaddetails)
					.product_id(productid)
					//.agencycode(agencycode)
					.build();

			return response;
		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}

	@Override
	public DocumentUploadDetailsReqRes uploadeddocdet(DocumentUploadDetailsReqRes request) {
		DocumentUploadDetailsReqRes response = new DocumentUploadDetailsReqRes();
		try {
			String quoteno = request.getQuote_no();
			String productid = StringUtils.isBlank(request.getProduct_id()) ? "65" : request.getProduct_id();
			//String agencycode = StringUtils.isBlank(request.getAgencycode()) ? "" : request.getAgencycode();
			String param="";
			
			List<DocumentUploadDetailsReq> docuploaddetails = new ArrayList<>();
			List<DocumentUploadDetailsReq> docuploadnulldetails = new ArrayList<>();

			List<Map<String, Object>> list = new ArrayList<>();
			List<Map<String, Object>> listnull = new ArrayList<>();
			
			log.info("uploadeddocdet--> quoteno: " + quoteno + " productid: " + productid);
			/*
			if (productid.equals("65")) {
				list = docRepo.getuploadeddocdet(quoteno, productid, "1", agencycode);
			} else if (productid.equals("33")) {
				list = docRepo.getuploadeddocdet33(quoteno, productid, "1", agencycode);
				listnull = docRepo.getuploadeddocnulldet(quoteno, productid, "1", agencycode);
			} else {
				String poltype = StringUtils.isBlank(request.getPolicytype()) ? "1" : request.getPolicytype();

				list = docRepo.getuploadeddocdet(quoteno, productid, poltype, agencycode);
			}*/

			log.info("uploadeddocdet--> list Size: " + list.size() + " listnull size: "+listnull.size());

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);

				String param1 = map.get("PARAM") == null ? "" : map.get("PARAM").toString();
				String param2 = map.get("PARAM2") == null ? "" : map.get("PARAM2").toString();
				String param3 = map.get("PARAM3") == null ? "" : map.get("PARAM3").toString();
				param = param1 + "~" + param2 + "~" + param3;

				String com = "";

				com = map.get("COMMON_FILE_PATH") == null ? "" : map.get("COMMON_FILE_PATH").toString();
				com = com.replace("\\", "/");

				if (StringUtils.isBlank(com)) {
					com = map.get("FILE_PATH_NAME") == null ? "" : map.get("FILE_PATH_NAME").toString();
					com = com.replace("\\", "/");
				}

				DocumentUploadDetailsReq res = DocumentUploadDetailsReq.builder()
						.description(map.get("DESCRIPTION") == null ? "" : map.get("DESCRIPTION").toString())
						.doc_id(map.get("DOCUMENT_ID") == null ? "" : map.get("DOCUMENT_ID").toString())
						.file_name(map.get("FILE_NAME") == null ? "" : map.get("FILE_NAME").toString())
						.file_path_name(com)
						.op_remarks(map.get("OP_REMARKS") == null ? "" : map.get("OP_REMARKS").toString())
						.op_status(map.get("OP_STATUS") == null ? "" : map.get("OP_STATUS").toString())
						.doc_desc(map.get("DOCUMENT_DESC") == null ? "" : map.get("DOCUMENT_DESC").toString())
						.status(map.get("OP_STA") == null ? "" : map.get("OP_STA").toString())
						.doc_desc_arabic(map.get("DOCUMENT_DESC_ARABIC") == null ? ""
								: map.get("DOCUMENT_DESC_ARABIC").toString())
						.upload_type(map.get("DOC_TYPE") == null ? "" : map.get("DOC_TYPE").toString())
						.param(param)
						.valueto_detect(map.get("DETECT") == null ? "" : map.get("DETECT").toString())
						.build();

				docuploaddetails.add(res);
			}
			
			for (int i = 0; i < listnull.size(); i++) {
				Map<String, Object> map = listnull.get(i);

				String param1 = map.get("PARAM") == null ? "" : map.get("PARAM").toString();
				String param2 = map.get("PARAM2") == null ? "" : map.get("PARAM2").toString();
				String param3 = map.get("PARAM3") == null ? "" : map.get("PARAM3").toString();
				param = param1 + "~" + param2 + "~" + param3;

				String com = "";

				com = map.get("COMMON_FILE_PATH") == null ? "" : map.get("COMMON_FILE_PATH").toString();
				com = com.replace("\\", "/");

				if (StringUtils.isBlank(com)) {
					com = map.get("FILE_PATH_NAME") == null ? "" : map.get("FILE_PATH_NAME").toString();
					com = com.replace("\\", "/");
				}

				DocumentUploadDetailsReq res = DocumentUploadDetailsReq.builder()
						.description(map.get("DESCRIPTION") == null ? "" : map.get("DESCRIPTION").toString())
						.doc_id(map.get("DOCUMENT_ID") == null ? "" : map.get("DOCUMENT_ID").toString())
						.file_name(map.get("FILE_NAME") == null ? "" : map.get("FILE_NAME").toString())
						.file_path_name(com)
						.op_remarks(map.get("OP_REMARKS") == null ? "" : map.get("OP_REMARKS").toString())
						.op_status(map.get("OP_STATUS") == null ? "" : map.get("OP_STATUS").toString())
						.doc_desc(map.get("DOCUMENT_DESC") == null ? "" : map.get("DOCUMENT_DESC").toString())
						.status(map.get("OP_STA") == null ? "" : map.get("OP_STA").toString())
						.doc_desc_arabic(map.get("DOCUMENT_DESC_ARABIC") == null ? ""
								: map.get("DOCUMENT_DESC_ARABIC").toString())
						.upload_type(map.get("DOC_TYPE") == null ? "" : map.get("DOC_TYPE").toString())
						.param(param)
						.valueto_detect(map.get("DETECT") == null ? "" : map.get("DETECT").toString())
						.build();

				docuploadnulldetails.add(res);
			}
			
			response = DocumentUploadDetailsReqRes.builder()
					.quote_no(quoteno)
					.documentupldet(docuploaddetails)
					//.documentuplnulldet(docuploadnulldetails)
					.product_id(productid)
					//.agencycode(agencycode)
					.build();
			
		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}

	@Override
	public String uploadingdocdet(DocumentUploadDetailsReqRes request, String commonpath) throws CommonValidationException {
		String response = "", quoteno = request.getQuote_no();
		String productid = request.getProduct_id();
		//String poltype = StringUtils.isBlank(request.getPolicytype()) ? "1" : request.getPolicytype();
		String passport[]=new String[3];
		String param="",param1="",param2="";
		List<DocumentUploadDetailsReq> list = request.getDocumentupldet();

		log.info("uploadingdocdet--> quoteno: " + quoteno + " productid: " + productid);
		log.info("uploadingdocdet--> Size: " + list.size());

		ErrorRes errRes = new ErrorRes();
		List<Error> errorlist = new ArrayList<>();
		Error err = new Error();

		try {
			for (int i = 0; i < list.size(); i++) {
				DocumentUploadDetailsReq docreq = list.get(i);
				
				log.info("uploadingdocdet--> getDoc_type_id(): " + docreq.getDoc_type_id());
				
				int deletecount =0;
				param=docreq.getParam();
				
				log.info("uploadingdocdet--> deletecount: " + deletecount);
			
				log.info("uploadingdocdet--> param: " + docreq.getParam());
				
				errRes = detectSer.insimagedetails(request);
				
				if (errRes.getErrors() == null) {
					err = new Error();
				} else if (errRes.getErrors().size() > 0) {
					err = errRes.getErrors().get(0);
				}
				
				if (StringUtils.isBlank(err.getMessage())) {
				/*	if("33".equals(productid)) {
						//String passport[]=docreq.getParam().split("~");
						deletecount=docRepo.deletetraveldocdetail(quoteno, productid, docreq.getDoc_type_id(),passport[0]);
					}*/
					
					Double detectperc = StringUtils.isBlank(errRes.getDetectperc()) ? null
							:Double.valueOf(errRes.getDetectperc());
					
					BigDecimal runningNo = new BigDecimal(1000);
					List<DocumentUploadDetails> no = docRepo.findAllByOrderByDocumentRefDesc();
					if(no.size()!=0) {
						runningNo = no.get(0).getDocumentRef().add(new BigDecimal(1));
					}
					Random random = new Random();
					String uuidAsString = String.format("%04d", random.nextInt(10000));

			        /*//skip accident details
			        if(!docreq.getUpload_type().equals("CLAIM-ACC"))
			        	docRepo.deleteByClaimNoAndDocTypeIdAndLossIdAndPartyNo(quoteno,new BigDecimal(docreq.getDoc_type_id()) ,docreq.getLossId(),docreq.getPartyNo());*/
			        
					DocumentUploadDetails docpk=DocumentUploadDetails.builder()
							.docTypeId(docreq.getDoc_type_id())
							.productId(new BigDecimal(productid))
							.claimNo(quoteno)
							.documentRef(new BigDecimal(uuidAsString))
							.param(StringUtils.isBlank(param)?"0":param)					
							.commonFilePath(commonpath)
							.description(docreq.getDescription())
							.detect(errRes.getDetectedValue())
							.detectPerc(BigDecimal.valueOf(deletecount))
							.docId(runningNo.toString())							 
							.errorRes(StringUtils.isBlank(err.getMessage())?"":err.getMessage().trim())
							.fileName(docreq.getFile_name())
							.filePathName(docreq.getFile_path_name())
							.opRemarks(errRes.getGoogle_visionsno())
							.opStatus(docreq.getOp_status())
							.uploadType(docreq.getUpload_type())
							.insId(BigDecimal.valueOf(Long.valueOf(docreq.getIns_id())))
							.amendId(BigDecimal.valueOf(0L))
							//.param(docreq.getParam())
							.param2(docreq.getParam2())
							.param3(param2)							
							.status("Y")
							.createdby(docreq.getCreatedby())
							.uploadType(docreq.getUpload_type())
							.uploadedTime(new Date())
							//.vtypeId(poltype)
							.partyNo(docreq.getPartyNo())
							.lossId(docreq.getLossId())
							.build();
					
					docRepo.save(docpk);
					response = "Inserted Successfully";					
					/*inscount = docRepo.insdocdet(quoteno, docreq.getDoc_type_id(),
							docreq.getFile_path_name(), docreq.getDescription(),
							StringUtils.isBlank(productid) ? 0L : Long.valueOf(productid), "Y",
							docreq.getFile_name(), poltype, docreq.getOp_status(),
							errRes.getGoogle_visionsno(), docreq.getUpload_type(), docreq.getDoc_id(),
							docreq.getParam(), commonpath);*/

				} else if (err != null && StringUtils.isNotBlank(err.getMessage())) {
					errorlist.add(err);
				}
			}
		} catch (Exception e) {
			log.error(e);			
			response = "Not Inserted";
		}

		if (errorlist.size() > 0 && errorlist != null)
			throw new CommonValidationException(errorlist, quoteno);		

		return response;
	}

	@Override
	public String validatedocdet(DocumentUploadDetailsReqRes request) throws CommonValidationException {
		String response = "", status = "";
		String quoteno = request.getQuote_no();
		String productid = StringUtils.isBlank(request.getProduct_id()) ? "65" : request.getProduct_id();
		//String agencycode = StringUtils.isBlank(request.getAgencycode()) ? "" : request.getAgencycode();
		String docdesc = "";

		Long getvalue = 0L, getvalue_ref = 1L, policytype = 0L;

		List<Error> errorlist = new ArrayList<Error>();
		List<String> docids = new ArrayList<String>();

		try {
			log.info("validatedocdet--> quoteno: " + quoteno + " productid: " + productid);
/*
			if (productid.equals("65")) {
				MotorDataDetail mdd = mddRepo.findByMotDataDetPK_Quoteno(Long.valueOf(quoteno));
				HomePositionMaster hpm = hpmRepo.findByHpmPkQuoteno(Long.valueOf(quoteno)).get(0);

				policytype = mdd.getPolicytype();

				String refcodes = StringUtils.isBlank(hpm.getReferalcodes()) ? "" : hpm.getReferalcodes();
				String renewalyn = StringUtils.isBlank(hpm.getRenewalstatus()) ? "N" : hpm.getRenewalstatus();
				String remarks = StringUtils.isBlank(hpm.getRemarks()) ? "" : hpm.getRemarks();

				String quotetype = StringUtils.isBlank(mdd.getQuotetype()) ? "" : mdd.getQuotetype();
				String breakinsuranceyn = StringUtils.isBlank(mdd.getBreakininsurance_yn()) ? "N"
						: mdd.getBreakininsurance_yn();
				String prevcomp = StringUtils.isBlank(mdd.getPrevious_company()) ? "" : mdd.getPrevious_company();
				String manfyear = StringUtils.isBlank(mdd.getManufacture_year()) ? "" : mdd.getManufacture_year();
				String regyear = StringUtils.isBlank(mdd.getRegistration_year()) ? "" : mdd.getRegistration_year();

				String currdate = commomSer.formatdatewithouttime(new Date());
				String[] currdatesplit = currdate.split("/");
				currdate = currdatesplit[2];
				Long curryear = Long.valueOf(currdate);
				Long manfyearage = 0L, regyearage = 0L;

				manfyearage = StringUtils.isBlank(manfyear) ? 10 : (curryear - Long.valueOf(manfyear));
				regyearage = StringUtils.isBlank(regyear) ? 10 : (curryear - Long.valueOf(regyear));

				if (StringUtils.isNotBlank(refcodes)) {
					String refcodearr[] = refcodes.split(",");
					for (int i = 0; i < refcodearr.length; i++) {
						if (StringUtils.isNotBlank(refcodearr[i])) {
							Map<String, Object> docidmap = docRepo.getdocid(refcodearr[i]);
							String refdocid = docidmap.get("DOCUMENT_ID") == null ? ""
									: docidmap.get("DOCUMENT_ID").toString();
							if (StringUtils.isNotBlank(refdocid))
								docids.add(refdocid);
						}
					}
				}

				if (renewalyn.equalsIgnoreCase("Y") && breakinsuranceyn.equalsIgnoreCase("N")
						&& prevcomp.equals("1025783")) {
					getvalue = docRepo.getvalue_newORrenewal(policytype, quoteno, productid, agencycode);
				} else if (quotetype.equalsIgnoreCase("DQ") && manfyearage == 0 && regyearage == 0) {
					getvalue = docRepo.getvalue_newORrenewal(policytype, quoteno, productid, agencycode);
				} else {
					getvalue = docRepo.getvalue(policytype, quoteno, productid, agencycode);
				}

				if (docids != null && docids.size() > 0)
					getvalue_ref = docRepo.getvalue_ref(policytype, quoteno, productid, docids, agencycode);

				status = docRepo.getstatus(policytype);
				status = StringUtils.isBlank(status) ? "" : status;

				log.info("validatedocdet--> DocumentValue: " + getvalue + " getvalue_ref: " + getvalue_ref);

				if ((getvalue == 0 || getvalue_ref == 0) && status.equals("Y")) {
					errorlist.add(new Error("Please Upload Mandatory Documents", "Document", "101"));
				}

			 
			} else if (productid.equals("66")) {
				Long poltype = StringUtils.isBlank(request.getPolicytype()) ? 1L
						: Long.valueOf(request.getPolicytype());

				getvalue = docRepo.getvalue(poltype, quoteno, productid, agencycode);
				status = "Y";

				if (getvalue == 0) {
					List<String> docdesclist = docRepo.get_mandatory_docdesc(poltype, productid, quoteno, agencycode);
					for (int i = 0; i < docdesclist.size(); i++) {
						if (StringUtils.isNotBlank(docdesclist.get(i)))
							errorlist.add(
									new Error("Please Upload " + docdesclist.get(i) + " Document", "Document", "101"));
					}
				}

				if (productid.equals("66")) {
					Map<String, Object> map = docRepo.getaccidentdet(quoteno);

					String personinjure = map.get("PERSON_INJURE_YN") == null ? ""
							: map.get("PERSON_INJURE_YN").toString();
					String propertydamage = map.get("PROPERTY_DAMAGE_YN") == null ? ""
							: map.get("PROPERTY_DAMAGE_YN").toString();
					String otheraccid = map.get("OTHER_MAJORACCIDENT_TYPE") == null ? ""
							: map.get("OTHER_MAJORACCIDENT_TYPE").toString();

					if (personinjure.equalsIgnoreCase("Y") || propertydamage.equalsIgnoreCase("Y")
							|| StringUtils.isNotBlank(otheraccid)) {
						int count = docRepo.getdocidcount(quoteno, "74", productid);
						if (count <= 0) {
							docdesc = docRepo.get_docdesc("74", poltype, productid, agencycode);
							errorlist.add(new Error("Please Upload " + docdesc + " Document", "Document", "101"));
							getvalue = 0L;
						}
					}
				}
			} else if (productid.equals("33")) {
				int less70cnt = 0, above70cnt = 0, doc47cnt = 0, doc50cnt = 0, docmastercnt = 0;

				Map<String, Object> map = docRepo.getpoltypeownersid33(quoteno);

				Long poltype = map.get("TRAVEL_COVER_ID") == null ? 0L
						: Long.valueOf(map.get("TRAVEL_COVER_ID").toString());

				status = docRepo.getstatus(poltype);
				status = StringUtils.isBlank(status) ? "" : status;

				docmastercnt = docRepo.getdocmastercnt(poltype, productid, agencycode);

				if (docmastercnt > 0) {
					less70cnt = docRepo.getlessthan70cnt(quoteno);
					doc47cnt = docRepo.getdoccount(quoteno, "47", productid);

					above70cnt = docRepo.getabove70cnt(quoteno);
					doc50cnt = docRepo.getdoccount(quoteno, "50", productid);
				}

				log.info("validatedocdet--> less70cnt: " + less70cnt + " doc47cnt: " + doc47cnt);
				log.info("validatedocdet--> above70cnt: " + above70cnt + " doc50cnt: " + doc50cnt);

				if (status.equals("Y")) {
					if (!((less70cnt == doc47cnt) && (above70cnt == doc50cnt))) {
						errorlist.add(new Error("Please Upload Mandatory Documents", "Document", "101"));
					}
				}
			} else*/ /*{
				Long poltype = StringUtils.isBlank(request.getPolicytype()) ? 1L
						: Long.valueOf(request.getPolicytype());

				getvalue = docRepo.getvalue(poltype, quoteno, productid, agencycode);
				status = "Y";

				if (getvalue == 0) {
					List<String> docdesclist = docRepo.get_mandatory_docdesc(poltype, productid, quoteno, agencycode);
					for (int i = 0; i < docdesclist.size(); i++) {
						if (StringUtils.isNotBlank(docdesclist.get(i)))
							errorlist.add(new Error("Please Upload " + docdesclist.get(i) + " Document", "Document", "101"));
					}
				}
			}*/

			log.info("validatedocdet--> quoteno: " + quoteno + " policytype: " + policytype);

		} catch (Exception e) {
			log.error(e);
		}

		if (errorlist != null && errorlist.size() > 0)
			throw new CommonValidationException(errorlist, quoteno);

		response = "Successfully Validated";

		return response;
	}

	@Override
	public String deletedocdet(DocumentUploadDetailsReqRes request) {
		String response = "";
		try {
			String quoteno = request.getQuote_no();
			String filepath = request.getDocumentupldet().get(0).getFile_path_name();
			String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id();

			int deletecount = 0;

			log.info("deletedocdet--> quoteno: " + quoteno + " productid: " + productid);
			log.info("deletedocdet--> FilePath: " + filepath);
			if("33".equals(productid)) {
			/*	DocumentUploadDetails entres = new DocumentUploadDetails();
				entres = docRepo.findByClaimNoAndCommonFilePathAndProductId(quoteno, filepath, BigDecimal.valueOf(Long.valueOf(productid)));				
				entres.setFilePathName("");
				entres.setFileName("");
				entres.setCommonFilePath("");				
				docRepo.save(entres);			*/	
			}else {
		//	deletecount = docRepo.deleteByClaimNoAndCommonFilePathAndProductId(quoteno, filepath, BigDecimal.valueOf(Long.valueOf(productid)));
			}
			log.info("deletedocdet--> Count: " + deletecount);
			response = deletecount > 0 ? "Deleted Successfully" : "Not Deleted";

		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}

	@Override
	public String uploadFile(MultipartFile file, String quoteno, String docId) {
		try {

			String classpath = this.getClass().getClassLoader().getResource("").getPath();
			classpath = classpath.replaceAll("classes/", "documents/");
			classpath = classpath.replaceAll("%20", " ");

			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			
			String topath = classpath + quoteno + docId + commomSer.fileDateFormat() + "." + extension;
			
			log.info("uploadFile--> quoteno: " + quoteno + " docId: " + docId);
			log.info("uploadFile--> topath: "+topath);

			InputStream is = file.getInputStream();
			
			/*Thread_Common job = new Thread_Common("Upload File", docSer, is, topath,file.getOriginalFilename());
			Thread thread = new Thread(job);
			thread.setName("Upload File " + quoteno + docId);
			thread.setDaemon(false);
			thread.start();*/

			return topath;
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}
	
	@Override
	public String uploadFileToPath(InputStream is, String topath,String originalFilename) {
		String extension = "";
		try {
			extension = FilenameUtils.getExtension(originalFilename);

			Thumbnails.of(is)
				.size(750, 750)
				.outputFormat(extension)
				.toFile(topath);

		} catch (Exception e) {
			log.error(e);
			try {
				File file = new File(topath);

				// temp FileUtils.copyInputStreamToFile(is, file);

			} catch (Exception e1) {
				log.error(e1);
			}
		}
		return "";
	}
	
	@Override
	public ResponseEntity<Resource> downloadfile(String filepath) {
		ResponseEntity<Resource>  responseEntity = null;
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.IMAGE_JPEG);
			File file = new File(filepath);
			Path path = Paths.get(file.getAbsolutePath());
		    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		    return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .body(resource);
		} catch (Exception e) {
			log.error(e);
		}
		return responseEntity;
	}
	
	@Override
	public String deletefile(String filepath) {
		try {
			String classpath = this.getClass().getClassLoader().getResource("").getPath();
			classpath = classpath.replaceAll("classes/", "documents/");
			classpath = classpath.replaceAll("%20", " ");
			log.info("classpath: " + classpath);
			if (filepath != null && StringUtils.isNotBlank(filepath)) {
				String fname[] = filepath.split("/");
				String fileName = fname[fname.length - 1];
				String tfilepath = classpath + fileName;
				File file = new File(tfilepath);
				boolean del = file.delete();
				if (del)
					return "Success";
			}
		} catch (Exception e) {
			log.error(e);
		}
		return "Failed";

	}

	@Override
	public DocumentUploadRes storeFile(MultipartFile file,String quoteNo, String docTypeId,  String description, String proid,
			String fileName, String uploadtype, String docid, String param, String docDetect) throws CommonValidationException {
		 String originalFileName =file.getOriginalFilename();
		 DocumentUploadRes res=new DocumentUploadRes();
		 DocumentUploadDetailsReq doc=new DocumentUploadDetailsReq();
         List<DocumentUploadDetailsReq>docreq1=new ArrayList<DocumentUploadDetailsReq>();
         DocumentUploadDetailsReqRes request=new DocumentUploadDetailsReqRes();
	        try {
	        	this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
	        	Files.createDirectories(this.fileStorageLocation);
	            // Check if the file's name contains invalid characters
	            if(originalFileName.contains("..")) {
	                log.info("Sorry! Filename contains invalid path sequence " + originalFileName);
	            }
	            
	            SimpleDateFormat sdf = new SimpleDateFormat("'on'ddMMMyyyy_h-mm-ss-SSSSSS_a");
				Calendar cal = Calendar.getInstance();
				String date1 = sdf.format(cal.getTime());
	           String fileName1=fileName.replace("."+FilenameUtils.getExtension(originalFileName), "")+ quoteNo + "_1_" + docid + "_" + docTypeId + date1 + "." +FilenameUtils.getExtension(originalFileName);
	            Path targetLocation = this.fileStorageLocation.resolve(fileName1);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	            
	            doc.setDoc_type_id(docTypeId);
	            doc.setFile_path_name(targetLocation+"");
	            doc.setDescription(description);
	            doc.setProduct_id(proid);
	            doc.setFile_name(fileName);
	            doc.setUpload_type(uploadtype);
	            doc.setDoc_id(docid);
	            doc.setParam(param);
	            doc.setValueto_detect(docDetect);
	            docreq1.add(doc);
	            request.setDocumentupldet(docreq1);
	            request.setQuote_no(quoteNo);
	           
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
	        String response = "", quoteno = request.getQuote_no();
    		String productid = StringUtils.isBlank(request.getProduct_id()) ? "65" : request.getProduct_id();
    		//String poltype = StringUtils.isBlank(request.getPolicytype()) ? "1" : request.getPolicytype();

    		List<DocumentUploadDetailsReq> list = request.getDocumentupldet();

    		log.info("uploadingdocdet--> quoteno: " + quoteno + " productid: " + productid);
    		log.info("uploadingdocdet--> Size: " + list.size());

    		ErrorRes errRes = new ErrorRes();
    		List<Error> errorlist = new ArrayList<>();
    		Error err = new Error();
	    		try {
	    			for (int i = 0; i < list.size(); i++) {
	    				DocumentUploadDetailsReq docreq = list.get(i);
	    				
	    				productid = docreq.getProduct_id();
	    				
	    				log.info("uploadingdocdet--> getDoc_type_id(): " + docreq.getDoc_type_id());
	    				
	    				int deletecount =0;
	    				/*if("33".equals(productid)) {
	    					deletecount=docRepo.deletetraveldocdetail(quoteno, productid, docreq.getDoc_type_id(),docreq.getParam());
	    				}else*/ {
	    			//		docRepo.deleteByClaimNoAndProductIdAndDocTypeId(quoteno, BigDecimal.valueOf(Long.valueOf(productid)), docreq.getDoc_type_id());
	    				}
	    				log.info("uploadingdocdet--> deletecount: " + deletecount);
	    			
	    				log.info("uploadingdocdet--> param: " + docreq.getParam());
	    				String dest = "", commonpath = "";
	    				
	    				errRes = detectSer.insimagedetails(request);
	    				
	    				if (errRes.getErrors() == null) {
	    					err = new Error();
	    				} else if (errRes.getErrors().size() > 0) {
	    					err = errRes.getErrors().get(0);
	    				}
	    				
	    				if (StringUtils.isBlank(err.getMessage())) {
	    					
	    					dest = commomSer.getapplicationProperty().getProperty("common.file.path");
	    					String date = commomSer.formatdatewithtime3(new Date());
	    					String filename = StringUtils.isBlank(docreq.getFile_name()) ? "" : docreq.getFile_name();
	    					filename = filename.replaceAll("\\s", "");

	    					String destination = dest + docreq.getDoc_type_id() + "_" + date + "_" + filename;
	    					commonpath = commomSer.copyFile(docreq.getFile_path_name(), destination);
	    					commonpath = StringUtils.isBlank(commonpath) ? "" : commonpath;
	    					commonpath = commonpath.replace("\\", "/");
	    					log.info("uploadingdocdet--> commonpath: " + commonpath + " filename: " + filename);
	    					
	    					Double detectperc = StringUtils.isBlank(errRes.getDetectperc()) ? null
	    							:Double.valueOf(errRes.getDetectperc());
	    					
	    					Random random = new Random();
	    			        String uuidAsString = String.format("%04d", random.nextInt(10000));
	    			        String uuidAsString1 = String.format("%04d", random.nextInt(10000));
	    			        
	    						DocumentUploadDetails docpk=DocumentUploadDetails.builder()
	    							.docTypeId(uuidAsString1)
	    							.productId(BigDecimal.valueOf(Long.valueOf(productid)))
	    							.claimNo(quoteno)
	    							.param(StringUtils.isBlank(docreq.getParam())?"0":docreq.getParam())
	    							 .commonFilePath(commonpath)
	    							.description(docreq.getDescription())
	    							.detect(errRes.getDetectedValue())
	    							.detectPerc(BigDecimal.valueOf(detectperc))
	    							.docId(docreq.getDoc_id())
	    							.uploadType(docreq.getUpload_type())
	    							.insId(BigDecimal.valueOf(Long.valueOf(docreq.getIns_id())))
	    							.amendId(BigDecimal.valueOf(0L))
	    							.errorRes(StringUtils.isBlank(err.getMessage())?"":err.getMessage().trim())
	    							.fileName(docreq.getFile_name())
	    							.filePathName(docreq.getFile_path_name())
	    							.opRemarks(errRes.getGoogle_visionsno())
	    							.opStatus(docreq.getOp_status())
	    							//.param(docreq.getParam())
	    							.param2(null)
	    							.param3(null)
	    							.documentRef(BigDecimal.valueOf(Long.valueOf(uuidAsString)))
	    							.status("Y")
	    							.createdby(docreq.getCreatedby())
	    							.uploadType(docreq.getUpload_type())
	    							.uploadedTime(new Date())
	    							//.vtypeId(poltype)
	    							.build();
	    					
	    				//	docRepo.save(docpk);

	    				} else if (err != null && StringUtils.isNotBlank(err.getMessage())) {
	    					errorlist.add(err);
	    				}
	    			}
	    		} catch (Exception e) {
	    			log.error(e);
	    			
	    			res.setMasseage("Not Inserted");
	    		}

	    		if (errorlist.size() > 0 && errorlist != null)
	    			throw new CommonValidationException(errorlist, quoteno);
	    		res.setMasseage("Inserted Successfully");

	    		return res;
	}
	@Override
    public String getDocumentName(DownloadFileReq req) {
    	String fileName= "";
    	try {
    	//	fileName= docRepo.getUploadDocumnetPath(req.getQuoteNo(),req.getProductId(),req.getDocTypeId(),req.getPassportNo());
	    	
    	}catch (Exception e) {log.info(e);}
    	return fileName;
    }

	@Override
	public Resource loadFileAsResource(String fileName) {
		  try {
	        	this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
	        	Files.createDirectories(this.fileStorageLocation);
	            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	               //throw new FileNotFoundException("File not found " + fileName);
	            }
	
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	           // throw new FileNotFoundException("File not found " + fileName);
	        }
	        return null;
	}

	@Override
	public String deleteFileSource(DownloadFileReq req, String fileName) {
		String res="";
		try {
			this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        	Files.createDirectories(this.fileStorageLocation);
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            boolean delete = Files.deleteIfExists(filePath);  
            if(delete) {
         /*   	DocumentUploadDetails entRes = docRepo.findByClaimNoAndProductIdAndDocTypeIdAndParam(req.getQuoteNo(),BigDecimal.valueOf(Long.valueOf(req.getProductId())),req.getDocTypeId(),req.getPassportNo());
            	entRes.setFilePathName("");
            	entRes.setFileName("");
            	docRepo.save(entRes); */
            	res="File deleted successfully";	          
            	}
            }catch (Exception e) {
			log.info(e);
			res="Fail to delete file";
		}
		return res;
	}

	@Override
	public String uploadingdocdet(DocumentUploadDetailsReqRes request) {
		// TODO Auto-generated method stub
		return null;
	}

}
