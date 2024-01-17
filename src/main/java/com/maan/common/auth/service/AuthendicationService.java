package com.maan.common.auth.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.maan.common.auth.dto.LoginRequest;
import com.maan.common.auth.dto.resp.TravelLoginResponse;
import com.maan.common.bean.LoginMaster;
import com.maan.common.error.Error;
public interface AuthendicationService {

	LoginMaster getUserDetails(LoginRequest mslogin, List<Error> validation);
	TravelLoginResponse checkLoginDetails(LoginMaster user, HttpServletRequest http,List<Error> validation, String pwd,String token, String temptoken, String regionCode,String InsuranceId);
	//NewTokenReq getNewTokenGenerated(HttpServletRequest http);
	//LoginChangePasswordResp CheckChangePassword(List<String> validation);
	//LoginChangePasswordResp getSendMailForgotPassword(ForgotPasswordRequest req,List<Error> validation);
	LoginMaster loginuserCheck(LoginRequest mslogin);
	LoginMaster loginuserCheckWithoutIns(LoginRequest mslogin);

}
