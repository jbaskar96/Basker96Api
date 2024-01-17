package com.maan.common.auth.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.maan.common.auth.dto.LoginRequest;
import com.maan.common.auth.dto.resp.TravelLoginResponse;
import com.maan.common.auth.service.AuthendicationService;
import com.maan.common.auth.service.impl.LoginValidated;
import com.maan.common.auth.token.JwtTokenUtil;
import com.maan.common.bean.BranchMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.error.CommonValidationException;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.res.BranchDropDownRes;
import com.maan.common.error.Error;

@RestController
@RequestMapping("/authentication")
public class LoginController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	Gson gson = new Gson();
	private Logger log = LogManager.getLogger(LoginController.class);
	@Autowired
	private AuthendicationService authservice;
	@Autowired
	private LoginValidated loginValidationComponent;
	@Autowired
	private BCryptPasswordEncoder cryptoService;
	@Autowired
	private BranchMasterRepository branchrep;

	Gson json = new Gson();

	@SuppressWarnings("unused")
	@PostMapping("/login")
	public TravelLoginResponse login(@RequestBody LoginRequest mslogin, HttpServletRequest http)
			throws CommonValidationException {
				
		String RegionCode = "";
		String BranchCode = "";
		// Claim Officer login validation
		if (mslogin.getLoginType().equals("claimofficer") || mslogin.getLoginType().equals("admin")) {
			LoginMaster loginmas = authservice.loginuserCheck(mslogin);
			
			if (loginmas != null) {
				mslogin.setBranchCode(loginmas.getBranchCode());
				mslogin.setRegionCode(loginmas.getRegionCode());
				BranchCode = loginmas.getBranchCode();
				RegionCode = loginmas.getRegionCode();
			}else {
				List<Error> validation = new ArrayList<Error>();
				Error error = new Error("01", "LoginId", "You Are Not A Authenticated User");
				validation.add(error);
				if (validation != null && validation.size() > 0) {
					throw new CommonValidationException(validation, null);
				}
			}
		}
		// Surveyor, Garage, Financial and User login validation
		if (mslogin.getLoginType().equals("surveyor") || mslogin.getLoginType().equals("garage") || mslogin.getLoginType().equals("user") || mslogin.getLoginType().equals("financial")) {
			LoginMaster loginmas = authservice.loginuserCheckWithoutIns(mslogin);//loginuserCheckWithoutIns(mslogin);
			if (loginmas != null) {
				mslogin.setCompanyId(loginmas.getCompanyId().toString());
				mslogin.setBranchCode(loginmas.getBranchCode());
				mslogin.setRegionCode(loginmas.getRegionCode());
				BranchCode = loginmas.getBranchCode();
				RegionCode = loginmas.getRegionCode();
			}else {
				List<Error> validation = new ArrayList<Error>();
				Error error = new Error("01", "LoginId", "You Are Not A Authenticated User");
				validation.add(error);
				if (validation != null && validation.size() > 0) {
					throw new CommonValidationException(validation, null);
				}
			}
		}
		
		log.info("-----token------" + json.toJson(mslogin));
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		String token = null, temptoken = "";
		TravelLoginResponse auth = new TravelLoginResponse();
		List<Error> validation = loginValidationComponent.LoginValidation(mslogin);
		if (validation != null && validation.size() > 0) {
			throw new CommonValidationException(validation, null);
		}
		final LoginMaster user = authservice.getUserDetails(mslogin, validation);
		// user.setLoginType(mslogin.getLoginType());
		if (null == validation || validation.isEmpty()) {
			token = jwtTokenUtil.generateToken(user);
			log.info("-----token------" + token);
			http.getSession().removeAttribute(user.getLoginId());
			grantedAuthorities = new HashSet<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getUsertype()));
			temptoken = cryptoService.encode("getLogincheck");
		}
		if (null == validation || validation.isEmpty()) {
			auth = authservice.checkLoginDetails(user, http, validation, mslogin.getPassword(), token, temptoken,RegionCode,mslogin.getCompanyId());	
			
			if (auth .getLoginResponse().getBranchCode() != null) {
				List<String> list = new ArrayList<String>(Arrays.asList(BranchCode.split(",")));
				
				List<BranchDropDownRes> droplist = new ArrayList<BranchDropDownRes>(); 
				for(int i=0;i<list.size();i++) {
					
					BranchDropDownRes drop = new BranchDropDownRes();
					BranchMaster branch = branchrep.findByBranchCode(list.get(i)).get(0) ;					
					drop.setCode(branch.getBranchCode());
					drop.setCodedesc(branch.getBranchName());
					drop.setTypeid(branch.getBranchCode());		
					drop.setRegioncode(branch.getRegionCode());
					droplist.add(drop);
				}
				//auth .getLoginResponse().setBranchCodeList(droplist);
				auth .getLoginResponse().setBranchCode(BranchCode);
			}	
			if(mslogin.getWhatsappyn() !=null) {
				if(mslogin.getWhatsappyn().equals("Y")) {
					TravelLoginResponse whatsapp = new TravelLoginResponse();
					whatsapp.setToken(auth.getLoginResponse().getToken());
					return whatsapp;
			}}
			
		} else {

			throw new CommonValidationException(validation, null);
		}
		return auth;
	}

	
}
