package com.maan.common.auth.dto.resp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

public class LoginResponse {

	
	@XmlElement(name="Status")
	private String status;
	@XmlElement(name="Result")
	private String result;
	@XmlElement(name="PasswordMsg")
	private String passwordMsg;
	@XmlElement(name="Errors")
	private List<Error> Errors = new ArrayList<Error>();
	@XmlElement(name="ProductDetails")
	private List<Map<String,Object>> productDetails = new ArrayList<Map<String,Object>>();
	@XmlElement(name="LoginId")
	private String loginId;
	@XmlElement(name="UserType")
	private String userType;
	@XmlElement(name="BranchCode")
	private String branchCode;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public List<Map<String, Object>> getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(List<Map<String, Object>> productDetails) {
		this.productDetails = productDetails;
	}
	public String getPasswordMsg() {
		return passwordMsg;
	}
	public void setPasswordMsg(String passwordMsg) {
		this.passwordMsg = passwordMsg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<Error> getErrors() {
		return Errors;
	}
	public void setErrors(List<Error> errors) {
		Errors = errors;
	}
}
