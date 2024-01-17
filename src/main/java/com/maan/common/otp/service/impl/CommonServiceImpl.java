package com.maan.common.otp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maan.common.bean.LoginMaster;
import com.maan.common.otp.request.OtpReq;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.upload.response.LoginMasterResDto;


@Service
public class CommonServiceImpl {
	
	@Autowired
	private LoginMasterRepository autheRepository;
	
/*	@Autowired 
	private OtpDataDetailRepository otpRepo; */
	
	private Logger log=LogManager.getLogger(CommonServiceImpl.class);
	
	public String reqPrint(Object response) {
		ObjectMapper mapper = new ObjectMapper();
		String resp = "";
		try {
			
			resp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
			log.info(mapper.writeValueAsString(response));
		} catch (Exception e) {
			log.error(e);
		}
		return resp;
	}

	public LoginMasterResDto createUser(OtpReq request) {

		LoginMasterResDto res= new LoginMasterResDto();
		List<LoginMaster> entity = new ArrayList<LoginMaster>();
		try {
			/*		entity = autheRepository.findByLoginId("guest");
			
	Optional<OtpDataDetail> otpData = otpRepo.findByOtpIdAndOtpOrderByEntryDate(request.getOtpId() ,request.getOtp());
			
			LoginMaster save = new LoginMaster();
			LoginMaster entres = entity .get(0);
			
			save.setLoginId(request.getReferenceNo() ); 				
			save.setAccesstype(entres .getAccesstype());
			save.setAcExecutiveCreation(entres .getAcExecutiveCreation());
			save.setAgencyCode(entres .getAgencyCode());
			save.setAllowedIpAddr(entres .getAllowedIpAddr());
			save.setAppId(entres .getAppId());
			save.setAttachedBranch(entres .getAttachedBranch());
			save.setAttachedUw(entres .getAttachedUw());
			
			save.setBranchCode(entres .getBranchCode());	
			save.setBrokerCodes(entres .getBrokerCodes());
			//save.setBranchMaster(entres .getBranchMaster());
			
			save.setCompanyId(entres .getCompanyId());
			save.setCountryId(entres .getCountryId());
			save.setCoreLoginId(entres .getCoreLoginId());
			save.setCreatedBy(request.getAssuredName()  );
			
			save.setEmployeCode(entres .getEmployeCode());
			save.setEncryptPassword(entres .getEncryptPassword());
			save.setEntryDate(entres .getEntryDate());
			save.setExpiryDate(entres .getExpiryDate());
			
			save.setFdCode(entres .getFdCode());
			save.setLpass1(entres .getLpass1());
			save.setLpass2(entres .getLpass2());
			save.setLpass3(entres .getLpass3());
			save.setLpass4(entres .getLpass4());
			save.setLpass5(entres .getLpass5());
			
			save.setMenuId(entres .getMenuId());
			save.setMobileNumber(otpData.get().getMobileNo() );
			
			save.setOaCode(entres .getOaCode());
			save.setPassword(entres .getPassword());
			save.setProductId(entres .getProductId());
			save.setPwdCount(entres .getPwdCount());
			save.setPassdate(entres .getPassdate());
			
			save.setReferal(entres .getReferal());
			save.setRegionCode(entres .getRegionCode());
			save.setRemarks(entres .getRemarks());
			save.setRestictIpStatus(entres .getRestictIpStatus());
			save.setRights(entres .getRights());
			
			save.setStateCode(entres .getStateCode());
			save.setStatus(entres .getStatus());
			save.setSubBranch(entres .getSubBranch());
			save.setSubUserType(entres .getSubUserType());
			save.setSuminsuredEnd(entres .getSuminsuredEnd());
			save.setSuminsuredStart(entres .getSuminsuredStart());
			save.setTokenPassword(entres .getTokenPassword());
			
			save.setUserIdCreation(entres .getUserIdCreation());
			save.setUserMail(entres .getUserMail());
			save.setUsername(request.getAssuredName() );
			save.setUsertype(entres .getUsertype());
			save.setUserid(entres .getUserid());
			
			save.setWajahYn(entres .getWajahYn());
			
			autheRepository.save(save);
			
			res.setLoginId(request.getReferenceNo() );
			res.setUsertype(entres.getUsertype()); */
		} catch (Exception e) {
			log.info(e);
			res=null;
		}
		return res;
	}
}
