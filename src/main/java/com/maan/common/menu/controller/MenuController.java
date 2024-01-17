package com.maan.common.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maan.common.domain.OfsDataDto;
import com.maan.common.error.Error;
import com.maan.common.menu.req.DropDownReq;
import com.maan.common.menu.req.ExistingRequest;
import com.maan.common.menu.req.SearchRequest;
import com.maan.common.menu.req.lapsedQuoteReq;
import com.maan.common.menu.res.CommonResponse;
import com.maan.common.menu.res.DropDownResponse;
import com.maan.common.menu.service.MenuService;
import com.maan.common.repository.BranchMasterRepository;

@RestController
@RequestMapping("/api")
public class MenuController {

	@Autowired
	private MenuService menuService;
	

	// Quote Register Api's
	@PostMapping("/getexpiredquotes")
	private List<CommonResponse> getExpireQuoteData(@RequestBody ExistingRequest req) {
		return menuService.getExpireQuoteData(req);
	}

	@PostMapping("/getexistingquotes")
	private List<CommonResponse> getExistingData(@RequestBody ExistingRequest request) {
		return menuService.getExistingData(request);  
	}

	@PostMapping("/getExistingDataWQ")
	private List<CommonResponse> getExistingDataWQ(@RequestBody ExistingRequest request) {
		return menuService.getExistingDataWQ(request);
	}

	@PostMapping("/getrejectedquotes")
	private List<CommonResponse> getRejectQuoteData(@RequestBody ExistingRequest request) {
		return menuService.getRejectQuoteData(request);
	}

	// Customer Confirm Api's
	@PostMapping("/approveddata")
	private List<CommonResponse> getApprovedData(@RequestBody ExistingRequest req) {
		return menuService.getApprovedData(req);
	}

	@PostMapping("/nonapproveddata")
	private List<CommonResponse> getNonApprovedData(@RequestBody ExistingRequest req) {
		return menuService.getNonApprovedData(req);
	}

	@PostMapping("/pendingapproveddata")
	private List<CommonResponse> getPendingAppovedData(@RequestBody ExistingRequest req) {
		return menuService.getPendingAppovedData(req);
	}

	@PostMapping("/requote")
	private List<CommonResponse> getReQuoteData(@RequestBody ExistingRequest req) {
		return menuService.getReQuoteData(req);
	}

	// Referal Confirm Api's
	@PostMapping("/referralgrid")
	public List<CommonResponse> getreferralgrid(@RequestBody ExistingRequest request) {
		return menuService.getreferralgrid(request);
	}

	@PostMapping("/approvedreferralgrid")
	public List<CommonResponse> getapprovedreferralgrid(@RequestBody ExistingRequest request) {
		return menuService.getapprovedreferralgrid(request);
	}

	@PostMapping("/rejectedreferralgrid")
	public List<CommonResponse> getrejectedreferralgrid(@RequestBody ExistingRequest request) {
		return menuService.getrejectedreferralgrid(request);
	}

	// Portfolio Api's
	@PostMapping("/getactivepolicy")
	private List<CommonResponse> getActivePolicy(@RequestBody ExistingRequest req) {
		return menuService.getActivePolicy(req);
	}

	@PostMapping("/getcancelpolicy")
	private List<CommonResponse> getCancelPolicy(@RequestBody ExistingRequest req) {
		return menuService.getCancelPolicy(req);
	}

	@PostMapping("/pendingpoliciesgrid")
	public List<CommonResponse> getpendingpoliciesgrid(@RequestBody ExistingRequest request) {
		return menuService.getpendingpoliciesgrid(request);
	}

	@PostMapping("/lapsedQuoteInfo")
	public lapsedQuoteReq getlapsedQuoteInfo(@RequestBody lapsedQuoteReq request) {
		lapsedQuoteReq response = menuService.getlapsedQuoteInfo(request);
		return response;
	}

	// Search Api
	@PostMapping("/searchbylist")
	private List<DropDownResponse> getSearchByList(@RequestBody DropDownReq req) {
		return menuService.getSearchByList(req);
	}

	@PostMapping("/getsearchingdata")
	private List<CommonResponse> getSearchingData(@RequestBody SearchRequest request) {
		List<Error> validate = menuService.validateSearchByData(request);
		return menuService.getSearchingData(request, validate);
	}

	// Copy Quote
	@PostMapping("/getcopyquote")
	private CommonResponse getCopyQuoteData(@RequestBody ExistingRequest req) {
		return menuService.getCopyQuoteData(req);
	}

	@PostMapping("/updatelapsedQuote")
	public lapsedQuoteReq updatelapsedQuote(@RequestBody lapsedQuoteReq request) {
		List<Error> validate = menuService.validateLapsedQuote(request);
		lapsedQuoteReq response = menuService.updatelapsedQuote(request, validate);
		return response;
	}

	@PostMapping("/dropdown")
	private List<DropDownResponse> listItemDropDown(@RequestBody DropDownReq request) {
		List<DropDownResponse> listItemData = menuService.listItemDropDown(request);
		return listItemData;
	}

	// HOD Referral Confirm Api's
	@PostMapping("/hodapproveddata")
	private List<CommonResponse> getHODApprovedData(@RequestBody ExistingRequest req) {
		return menuService.getHODApprovedData(req);
	}

	@PostMapping("/hodnonapproveddata")
	private List<CommonResponse> getHODNonApprovedData(@RequestBody ExistingRequest req) {
		return menuService.getHODNonApprovedData(req);
	}

	@PostMapping("/hodpendingapproveddata")
	private List<CommonResponse> getHODPendingAppovedData(@RequestBody ExistingRequest req) {
		return menuService.getHODPendingAppovedData(req);
	}

	@PostMapping("/hodrequote")
	private List<CommonResponse> getHODReQuoteData(@RequestBody ExistingRequest req) {
		return menuService.getHODReQuoteData(req);
	}
    //Admin Side HOD referral Grids
	@PostMapping("/admin/hodreferraldata")
	private List<CommonResponse> getAdminHODPendingQuoteList(@RequestBody ExistingRequest req) {
		return menuService.getAdminHODPendingQuoteList(req);
	}
	
	@PostMapping("/getreportsdata")
	private List<CommonResponse> getReportsData(@RequestBody ExistingRequest request) {
		List<Error> validate=menuService.validateReportrequest(request);
		List<CommonResponse> reportdata = menuService.getReportsData(request,validate);
		return reportdata;
	}
	
	// Admin Exiting Referal
	@PostMapping("/admin/referalgrid")
	private List<CommonResponse> getAdminExistingQuoteList(@RequestBody ExistingRequest request) {
		
		List<CommonResponse> reportdata = menuService.getAdminExistingQuoteList(request);
		return reportdata;
	}
	
	// Admin Exiting Referal
	@PostMapping("/admin/approvedreferal")
	private List<CommonResponse> getAdminApprovedQuoteList(@RequestBody ExistingRequest request) {
		
		List<CommonResponse> reportdata = menuService.getAdminApprovedQuoteList(request);
		return reportdata;
	}
	
	// Admin Exiting Referal
	@PostMapping("/admin/rejectedreferal")
	private List<CommonResponse> getAdminRejectedQuoteList(@RequestBody ExistingRequest request) {
		
		List<CommonResponse> reportdata = menuService.getAdminRejectedQuoteList(request);
		return reportdata;
	}
	@PostMapping("/admin/portfolio")
	private List<CommonResponse> getAdminPortfolioList(@RequestBody ExistingRequest request) {
		
		List<CommonResponse> reportdata = menuService.getAdminPortfolioList(request);
		return reportdata;
	}

	/*@PostMapping("/admin/hodapproveddata")
	private List<CommonResponse> getAdminHODApproveQuoteList(@RequestBody ExistingRequest req) {
		return menuService.getAdminHODApproveQuoteList(req);
	}

	@PostMapping("/admin/hodrejecteddata")
	private List<CommonResponse> getAdminHODPendingRejectQuoteList(@RequestBody ExistingRequest req) {
		return menuService.getAdminHODPendingRejectQuoteList(req);
	}*/
}
