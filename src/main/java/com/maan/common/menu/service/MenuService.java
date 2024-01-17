package com.maan.common.menu.service;

import java.util.List;

import com.maan.common.domain.OfsDataDto;
import com.maan.common.error.Error;
import com.maan.common.menu.req.DropDownReq;
import com.maan.common.menu.req.ExistingRequest;
import com.maan.common.menu.req.SearchRequest;
import com.maan.common.menu.req.lapsedQuoteReq;
import com.maan.common.menu.res.CommonResponse;
import com.maan.common.menu.res.DropDownResponse;

public interface MenuService {

	List<CommonResponse> getExpireQuoteData(ExistingRequest req);

	List<CommonResponse> getExistingData(ExistingRequest request);

	List<CommonResponse> getExistingDataWQ(ExistingRequest request);

	List<CommonResponse> getRejectQuoteData(ExistingRequest request);
	
	List<CommonResponse> getApprovedData(ExistingRequest req);

	List<CommonResponse> getNonApprovedData(ExistingRequest req);

	List<CommonResponse> getPendingAppovedData(ExistingRequest req);

	List<CommonResponse> getReQuoteData(ExistingRequest req);

	List<CommonResponse> getreferralgrid(ExistingRequest request);

	List<CommonResponse> getapprovedreferralgrid(ExistingRequest request);

	List<CommonResponse> getrejectedreferralgrid(ExistingRequest request);

	List<CommonResponse> getActivePolicy(ExistingRequest req);

	List<CommonResponse> getCancelPolicy(ExistingRequest req);

	List<CommonResponse> getpendingpoliciesgrid(ExistingRequest request);

	List<CommonResponse> getSearchingData(SearchRequest request, List<Error> validate);

	List<DropDownResponse> getSearchByList(DropDownReq req);

	CommonResponse getCopyQuoteData(ExistingRequest req);

	lapsedQuoteReq updatelapsedQuote(lapsedQuoteReq request, List<Error> validate);

	List<DropDownResponse> listItemDropDown(DropDownReq request);

	lapsedQuoteReq getlapsedQuoteInfo(lapsedQuoteReq request);

	List<CommonResponse> getHODApprovedData(ExistingRequest req);

	List<CommonResponse> getHODNonApprovedData(ExistingRequest req);

	List<CommonResponse> getHODPendingAppovedData(ExistingRequest req);

	List<CommonResponse> getHODReQuoteData(ExistingRequest req);

	List<CommonResponse> getAdminHODPendingQuoteList(ExistingRequest req);
	
	List<CommonResponse> getReportsData(ExistingRequest req,List<Error> validate);

	List<Error> validateSearchByData(SearchRequest req);

	List<Error> validateLapsedQuote(lapsedQuoteReq req);

	List<Error> validateReportrequest(ExistingRequest req);

	List<CommonResponse> getAdminExistingQuoteList(ExistingRequest request);

	List<CommonResponse> getAdminApprovedQuoteList(ExistingRequest request);

	List<CommonResponse> getAdminRejectedQuoteList(ExistingRequest request);

	List<CommonResponse> getAdminPortfolioList(ExistingRequest request);

}
