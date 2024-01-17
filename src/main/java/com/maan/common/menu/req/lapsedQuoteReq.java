package com.maan.common.menu.req;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.error.Error;

import lombok.Data;

@Data
public class lapsedQuoteReq {

	private List<Map<String, Object>> lapsedReason;

	private List<Map<String, Object>> lapsedQuoteList;

	@JsonProperty("ProductId")
	private String productid;
	
	@JsonProperty("LoginId")
	private String loginId;
	
	@JsonProperty("ApplicationId")
	private String applicationId;
	
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("SchemeId")
	private String schemeId;
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	
	@JsonProperty("LapsedRemarks")
	private String lapsedremarks;
	private boolean status;
	private List<Error> errors;
	
	
/*	private String quoteno;
	private String productid;
	private String branchcode;
	private String lapsedremarks;
	private String loginid;

	private boolean status;

	private List<Error> errors;

	public String getQuoteno() {
		return quoteno;
	}

	public void setQuoteno(String quoteno) {
		this.quoteno = quoteno;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public List<Map<String, Object>> getLapsedReason() {
		return lapsedReason;
	}

	public void setLapsedReason(List<Map<String, Object>> lapsedReason) {
		this.lapsedReason = lapsedReason;
	}

	public List<Map<String, Object>> getLapsedQuoteList() {
		return lapsedQuoteList;
	}

	public void setLapsedQuoteList(List<Map<String, Object>> lapsedQuoteList) {
		this.lapsedQuoteList = lapsedQuoteList;
	}

	public String getLapsedremarks() {
		return lapsedremarks;
	}

	public void setLapsedremarks(String lapsedremarks) {
		this.lapsedremarks = lapsedremarks;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	} */

}
