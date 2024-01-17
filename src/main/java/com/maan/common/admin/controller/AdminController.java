package com.maan.common.admin.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.maan.common.admin.request.AdminBrokerListReq;
import com.maan.common.admin.request.AdminMenuTypeReq;
import com.maan.common.admin.request.AdminNewBrokerReq;
import com.maan.common.admin.request.AdminNewIssuerReq;
import com.maan.common.admin.request.BranchListRequest;
import com.maan.common.admin.request.BrokerInfoRequest;
import com.maan.common.admin.request.BrokerLoginProductInsReq;
import com.maan.common.admin.request.BrokerProductEditReq;
import com.maan.common.admin.request.IssuerBranchRequest;
import com.maan.common.admin.request.IssuerChangePassReq;
import com.maan.common.admin.request.IssuerExcludedDeleteRequest;
import com.maan.common.admin.request.IssuerIncludedRequest;
import com.maan.common.admin.request.IssuerIncludedSaveRequest;
import com.maan.common.admin.request.IssuerInfoRequest;
import com.maan.common.admin.request.LoginChangePassReq;
import com.maan.common.admin.request.NewAdminInsertReq;
import com.maan.common.admin.request.ProductsWiseMenuRequest;
import com.maan.common.admin.request.TitleDropDownRequest;
import com.maan.common.admin.request.UserCertificateRequest;
import com.maan.common.admin.request.UserMgtInsertRequest;
import com.maan.common.admin.request.UserMgtProductInsertRequest;
import com.maan.common.admin.request.UserProductEditRequest;
import com.maan.common.admin.request.UserProductValidReq;
import com.maan.common.admin.response.AdminBrokerInsertResponse;
import com.maan.common.admin.response.AdminBrokerInsertResponse1;
import com.maan.common.admin.response.AdminBrokerResponse;
import com.maan.common.admin.response.AdminIssuerInfoResponse;
import com.maan.common.admin.response.AdminIssuerInsertResponse;
import com.maan.common.admin.response.AdminIssuerResponse;
import com.maan.common.admin.response.AdminMenuResponse1;
import com.maan.common.admin.response.AdminMenuTypeResp1;
import com.maan.common.admin.response.AdminTableListResponse;
import com.maan.common.admin.response.AdminUserMgtListResponse;
import com.maan.common.admin.response.AdminUserResponse1;
import com.maan.common.admin.response.BranchCodeResponse1;
import com.maan.common.admin.response.BrokerMagDropDownResponse1;
import com.maan.common.admin.response.ChangePasswordResponse;
import com.maan.common.admin.response.CommanAdminBrokerViewResp;
import com.maan.common.admin.response.CommanBrokerProductEditResp;
import com.maan.common.admin.response.CommanUserProductEditResp1;
import com.maan.common.admin.response.CommonBrokerEditResp;
import com.maan.common.admin.response.CommonExcludedBrokerResp;
import com.maan.common.admin.response.CommonIncludedBrokerResp;
import com.maan.common.admin.response.CommonProductWiseMenuResponse;
import com.maan.common.admin.response.CommonUserEditResponse;
import com.maan.common.admin.response.IssuerProductInfoResp1;
import com.maan.common.admin.response.ProductResponse;
import com.maan.common.admin.response.UserMagOcCertificateResponse;
import com.maan.common.admin.response.UserMgtDropDownResponse1;
import com.maan.common.admin.response.UserMgtInsertResponse1;
import com.maan.common.admin.service.AdminService;
import com.maan.common.admin.validation.AdminValidation;

@RestController
@RequestMapping("/admin")
public class AdminController {

	Gson gson = new Gson();
	private Logger log=LogManager.getLogger(AdminController.class);
	@Autowired
	private AdminService service;
	@Autowired
	private AdminValidation comQuote;
	
	
	// =============== Admin Menu Type ============ //
	@PostMapping("/getAdminBasedMenuList")
	public AdminMenuTypeResp1 getMenuType(@RequestBody AdminMenuTypeReq req) {
		return service.getAdminBasedMenuList(req);
	}
	
	// ============== Broker Management ===========//
	@PostMapping("/getAdminBrokerList")
	public AdminBrokerResponse getBrokerList(@RequestBody AdminBrokerListReq req,HttpServletRequest http) {
		return service.getAdminBrokerList(req,http);
	}
	
	@PostMapping("/getBrokerInformation")
	public CommanAdminBrokerViewResp getBrokerInfo(@RequestBody BrokerInfoRequest req,HttpServletRequest http) {
		return service.getBrokerInformation(req,http);
	}
	
	@PostMapping("/getBrokerEdit")
	public CommonBrokerEditResp getBrokerInfoEdit(@RequestBody BrokerInfoRequest req,HttpServletRequest http) {
		return service.getBrokerInformationEdit(req,http);
	}
	@PostMapping("/AdminNewBrokerInsert")
	public AdminBrokerInsertResponse1 getBrokerInsert(@RequestBody AdminNewBrokerReq req,HttpServletRequest http) {
		
		AdminBrokerInsertResponse1 res1 = new AdminBrokerInsertResponse1();
		AdminBrokerInsertResponse res = new AdminBrokerInsertResponse();
		List<String> validation = new ArrayList<String>();
		try {
			String json = gson.toJson(req);		
			log.info("Marine admin broker insert request ===> "+json);
			validation = comQuote.BrokerNewInsertValidation(req);
			if(!(validation.size()>0)) {  
				validation=null;
			}
			res= service.InsertNewBrokerDetails(req,http,validation);
			
			if(res.getErrors().size()==0){
				res1.setErrorMessage(res.getErrors());
				res1.setMessage("Success");
				res1.setIsError(false);
				res1.setErroCode(0);
			}else {
				res1.setMessage("No Message");
				res1.setIsError(true);
				res1.setErroCode(1);
			}			
		}catch (Exception e) {
			log.info(e);		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}
		return res1;
	}
	
	@GetMapping("/getBrokerMangExecutive")
	public BrokerMagDropDownResponse1 getExecutive() {
		return service.getBrokerMangDropDown();
	}
	
	@PostMapping("/getBrokerPasswordChange")
	public ChangePasswordResponse getBrokerPassword(@RequestBody IssuerChangePassReq req,HttpServletRequest http) {
		ChangePasswordResponse res = new ChangePasswordResponse();
		List<String> validation = new ArrayList<String>();
		try {
			validation = comQuote.BrokerPasswordValidation(req);
			if(!(validation.size()>0)) {  
				validation=null;
			}
			res= service.getBrokerChangePassword(req,http,validation); 
		}catch (Exception e) {
			log.info(e);
		}
		return res;
	}
	
	@PostMapping("/BrokerProductInsert")
	public AdminBrokerInsertResponse1 getProductInsert(@RequestBody BrokerLoginProductInsReq req,HttpServletRequest http) {
		AdminBrokerInsertResponse1 res1 = new AdminBrokerInsertResponse1();
		AdminBrokerInsertResponse res = new AdminBrokerInsertResponse();
		List<String> validation = new ArrayList<String>();
		try {
			String json = gson.toJson(req);		
			log.info("Marine broker product insert request ===> "+json);
			validation = comQuote.BrokerProductInsertValidation(req);
			if(!(validation.size()>0)) {  
				validation=null;
			}
			res= service.InsertBrokerProductDetails(req,http,validation);
			if(res.getErrors().size()==0){
				res1.setErrorMessage(res.getErrors());
				res1.setMessage("Success");
				res1.setIsError(false);
				res1.setErroCode(0);
			}else {
				res1.setMessage("No Message");
				res1.setIsError(true);
				res1.setErroCode(1);
			}			
		}catch (Exception e) {
			log.info(e);		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}
		return res1;
	}
	
	@PostMapping("/getBrokerProductEdit")
	public CommanBrokerProductEditResp getProductEdit(@RequestBody BrokerProductEditReq req,HttpServletRequest http) {
		return service.getEditBrokerProductDetails(req,http);
	}
	
	@PostMapping("/getBrokerProductDelete")
	public ProductResponse getProductDelete(@RequestBody BrokerProductEditReq req) {
		return service.getDeleteBrokerProductDetails(req);
	}
	
	/// ============== Issuer Management ==========///
	
	
	@PostMapping("/getAdminIssuerList")
	public AdminIssuerResponse getIssuerList(@RequestBody TitleDropDownRequest req,HttpServletRequest http) {
		return service.getAdminIssuerList(req,http);
	}
	
	@PostMapping("/getIssuerInformation")
	public AdminIssuerInfoResponse getIssuerInfo(@RequestBody IssuerInfoRequest req,HttpServletRequest http) {
		return service.getIssuerInformation(req,http);
	}
	
	@PostMapping("/AdminNewIssuerInsert")
	public AdminIssuerInsertResponse getIssuerInsert(@RequestBody AdminNewIssuerReq req,HttpServletRequest http) {
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		List<String> validation = new ArrayList<String>();
		try {
		validation = comQuote.IssuerInsertValidation(req);
		if(!(validation.size()>0)) {  
			validation=null;
		}
		String json = gson.toJson(req);		
		log.info("Marine admin issuer insert request ===> "+json);
		res= service.InsertNewIssuerDetails(req,http,validation); 
		
	}catch (Exception e) {
		log.info(e);		
	}
	return res;
	}
	
	@PostMapping("/IssuerProductDetails")
	public IssuerProductInfoResp1 getIssuerProduct(@RequestBody BrokerInfoRequest req) {
		return service.getIssuerProductDetails(req); 
	}
	
	@PostMapping("/BranchDetail")
	public BranchCodeResponse1 getBranch(@RequestBody IssuerBranchRequest req) {
		return service.getIssuerBranchDetails(req); 
	}
	
	@PostMapping("/IssuerChangePassword")
	public ChangePasswordResponse getIssuerPassword(@RequestBody LoginChangePassReq reqs,HttpServletRequest http) {
		ChangePasswordResponse res = new ChangePasswordResponse();
		List<String> validation = new ArrayList<String>();
		try {
			IssuerChangePassReq req = new IssuerChangePassReq();
			req.setPassword(reqs.getPassword());
			req.setRePassword(reqs.getRePassword());  
			req.setAgencyCode(reqs.getLoginId());
			validation = comQuote.BrokerPasswordValidation(req);
			if(!(validation.size()>0)) {  
				validation=null;
			}
			res= service.getIssuerChangePassword(req,http,validation); 
		}catch (Exception e) {
			log.info(e);
		}
		return res;
	}
	
	@PostMapping("/IssuerIncludedBroker")
	public CommonIncludedBrokerResp getIncludedBroker(@RequestBody IssuerIncludedRequest req,HttpServletRequest http) {
		return service.getIssuerIncludedBroke(req,http); 
	}
	
	@PostMapping("/IssuerExcludedBroker")
	public CommonExcludedBrokerResp getExcludedBroker(@RequestBody IssuerIncludedRequest req,HttpServletRequest http) {
		return service.getIssuerExcludedBroke(req,http); 
	}
	
	@PostMapping("/IssuerIncludedInsert")
	public AdminIssuerInsertResponse getIncludedSave(@RequestBody IssuerIncludedSaveRequest req,HttpServletRequest http) {
		return service.getIssuerIncludedInsert(req,http); 
	}
	
	@PostMapping("/included/delete")
	public AdminIssuerInsertResponse IncludeDelete(@RequestBody IssuerExcludedDeleteRequest req,HttpServletRequest http) {
		return service.getIssuerExcludedInsert(req,http);  
	}
	
	
	/// ================= Admin Management ===============///
	
	@GetMapping("/getAdminUserTypeList")
	public AdminUserResponse1 getUserType() {
		return service.getAdminUserTypeList(); 
	}
	@PostMapping("/getBrokerBranchList")
	public AdminUserResponse1 getBrokerBranchList(@RequestBody BranchListRequest req ) {
		return service.getBrokerBranchList(req); 
	}
	@GetMapping("/getAdminCountryList/{BranchCode}")
	public AdminUserResponse1 getCountryList(@PathVariable("BranchCode") String branchCode) {
		return service.getCountryList(branchCode); 
	}
	@GetMapping("/getAdminNationalList/{BranchCode}")
	public AdminUserResponse1 getNationalList(@PathVariable("BranchCode") String branchCode) {
		return service.getNationalList(branchCode); 
	}
	@PostMapping("/getAdminMenuList")
	public AdminMenuResponse1 getMenu(@RequestBody TitleDropDownRequest req) {
		return service.getAdminMenuTypeList(req); 
	}
	//No Table Found
	@GetMapping("/getUnderWriterGradeList")
	public AdminUserResponse1 getGradeList() {
		return service.getUnderWriterGradeList(); 
	}
	//Completed
	@PostMapping("/getProductsWiseMenuList")
	public CommonProductWiseMenuResponse getProdWiseMenuList(@RequestBody ProductsWiseMenuRequest req) {
		return service.getProductsWiseMenuList(req);  
	}
	//Completed
	@PostMapping("/getAdminList")
	public AdminTableListResponse getAdminList(@RequestBody TitleDropDownRequest req,HttpServletRequest http) {
		return service.getAdminList(req,http); 
	}
	//Completed
	@PostMapping("/NewAdminInsert")
	public AdminIssuerInsertResponse getAdminInsert(@RequestBody NewAdminInsertReq req,HttpServletRequest http) {
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		List<String> validation = new ArrayList<String>();
		try {
		validation = comQuote.AdminInsertValidation(req);
		if(!(validation.size()>0)) {  
			validation=null;
		}
		res= service.getAdminInsert(req,http,validation); 
		}catch (Exception e) {
			log.info(e);
		}
		return res;
	}
	//Completed
	@PostMapping("/getAdminEditList")
	public AdminTableListResponse getAdminEdit(@RequestBody IssuerInfoRequest req,HttpServletRequest http) {
		return service.getAdminEditList(req,http); 
	}
	
	/// ================ User Management =================== ///
	//Completed
	@PostMapping("/getUserMgtDropDown")
	public UserMgtDropDownResponse1 getUserDropDown(@RequestBody TitleDropDownRequest req) {
		return service.getUserMgtDropDownList(req); 
	}
	
	/*@PostMapping("/getUserMgtOCCertificate")
	public UserMagOcCertificateResponse getOCCertificate(@RequestBody UserCertificateRequest req,HttpServletRequest http) {
		return service.getUserMgtOCCertificate(req,http); 
	}*/
	//Completed
	@PostMapping("/getUserMgtList")
	public AdminUserMgtListResponse getUserList(@RequestBody TitleDropDownRequest req,HttpServletRequest http) {
		return service.getUserMgtTableList(req,http);   
	}
	//Completed
	@PostMapping("/getUserMgtEditList")
	public CommonUserEditResponse getUserEditList(@RequestBody UserCertificateRequest req,HttpServletRequest http) {
		return service.getUserMgtEditTableList(req,http);     
	}
	//Completed
	@PostMapping("/UserMgtInsertOrUpdate")
	public UserMgtInsertResponse1 getUserInsertOrUpdate(@RequestBody UserMgtInsertRequest req,HttpServletRequest http) {
		UserMgtInsertResponse1 res = new UserMgtInsertResponse1();
		List<String> validation = new ArrayList<String>();
		try {
			String json = gson.toJson(req);		
			log.info("Marine admin broker insert request ===> "+json);
			validation = comQuote.UserInsertValidation(req);
			if(!(validation.size()>0)) {  
				validation=null;
			}
			res= service.UserMgtInsertOrUpdate(req,http,validation);
		}catch (Exception e) {
			log.info(e);
		}
		return res;
	}
	//Completed
	@PostMapping("/UserMgtProductInsert")
	public AdminIssuerInsertResponse getUserProductInsert(@RequestBody UserMgtProductInsertRequest req,HttpServletRequest http) {
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		UserProductValidReq validation = new UserProductValidReq();
		try {
			String json = gson.toJson(req);		
			log.info("Marine user mgt product insert request ===> "+json);
			validation = comQuote.UserProductInsertValidation(req);
			if(!(validation.getValidation().size()>0)) {  
				validation.setValidation(null);
			}
			res= service.InsertUserMgtProductDetails(req,http,validation);  
		}catch (Exception e) {
			log.info(e);
		}
		return res;
	}
	//Completed
	@PostMapping("/user/product/edit")
	public CommanUserProductEditResp1 getUserProduct(@RequestBody UserProductEditRequest req,HttpServletRequest http) {
		return service.getUserProductEditDetails(req,http); 
	}
	
	
}
