package com.maan.common.admin.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maan.common.admin.request.AdminNewBrokerReq;
import com.maan.common.admin.request.NewAdminInsertReq;
import com.maan.common.admin.request.UserMgtInsertRequest;
import com.maan.common.admin.request.UserMgtProductInsertRequest;
import com.maan.common.admin.request.UserProductValidReq;
import com.maan.common.auth.dto.resp.DefaultAllResponse;
import com.maan.common.auth.token.EncryDecryService;
import com.maan.common.auth.token.passwordEnc;
import com.maan.common.bean.BranchMaster;
import com.maan.common.bean.BrokerCompanyMaster;
import com.maan.common.bean.BrokerMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.LoginMasterId;
import com.maan.common.bean.LoginUserDetails;
import com.maan.common.bean.PersonalInfo;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.BrokerCompanyMasterRepository;
import com.maan.common.repository.BrokerMasterRepository;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.repository.LoginUserDetailsRepository;
import com.maan.common.repository.PersonalInfoRepository;


@Component
public class CompQuote {

	DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
	SimpleDateFormat sdfFormat = new SimpleDateFormat("dd/MM/yyyy");  
	private Logger log = LogManager.getLogger(AdminImplementation.class);

	@Autowired
	private LoginMasterRepository autheRepository;
	@Autowired
	private LoginUserDetailsRepository loginUserRep;
	@Autowired
	private PersonalInfoRepository personalRep;
	@Autowired
	private EncryDecryService endecryService;
	@Autowired
	private BranchMasterRepository branchmasterrepository;
	@Autowired
	private PersonalInfoRepository personalRepo;
	@Autowired
	private BrokerCompanyMasterRepository brokerCompanyRepo;
	@Autowired
	private BrokerMasterRepository brokerRepo;
	
	public int insertUserProductLonginUserDetails(UserMgtProductInsertRequest req, UserProductValidReq valreq) {
		int count = 0;
		try {
			List<Map<String, Object>> map = valreq.getMapvalue();
			LoginUserDetails user = new LoginUserDetails();
			List<LoginMaster> login = autheRepository.findByAgencyCode(req.getUserAgencyCode());
			for (int i = 0; i < map.size(); i++) {
				if ("Y".equalsIgnoreCase(map.get(i).get("product").toString())) {

					List<LoginUserDetails> ludlist = loginUserRep.findByloginIdAndProductId(login.get(0).getLoginId(),
							new BigDecimal(login.get(0).getProductId()));
					BigDecimal userid = new BigDecimal(0);
					if (ludlist.size() != 0) {
						userid = ludlist.get(0).getUserId();
					} else {
						// userid = queryem.getMaxUserIdLoginUserDetls();
					}
					user.setUserId(userid);
					user.setUserName(req.getCustomerName());
					user.setAgencyCode(req.getUserAgencyCode());
					user.setOaCode(req.getAgencyCode());
					user.setStatus("Y");
					user.setCompanyId(new BigDecimal(1));
					user.setCommission(new BigDecimal(0.0));
					user.setInsuranceStartLimit(new BigDecimal(0));
					// user.setInsuranceEndLimit(map.get(i).getinsEndLimit") == null ? 0L:
					// Long.valueOf(map.get(i).get("insEndLimit").toString()));
					// user.setDiscountofpremium(req.getDiscountPremium());
					user.setRelativeUserId(new BigDecimal(0));
					user.setCustomerId(StringUtils.isBlank(req.getCustomerId()) ? new BigDecimal(0)
							: new BigDecimal(req.getCustomerId()));
					// user.setMinpremiumamount(req.getMinPremiumAmount());
					// user.setBackdateallowed(req.getBackDateAllowed());
					// user.setLoadingofpremium(req.getLoadingPremium());
					// user.setProvisionforpremium(req.getProvision());
					user.setFreightDebitOption(
							map.get(i).get("freight") == null ? "N" : map.get(i).get("freight").toString());
					user.setPayReceiptStatus(
							map.get(i).get("receipt") == null ? "N" : map.get(i).get("receipt").toString());
					// user.setReceiptstatus(req.getPayReceipt());
					user.setSchemeId("30".equals(map.get(i).get("uproductId").toString()) ? new BigDecimal(7)
							: new BigDecimal(0));
					// Long amendid =
					// queryem.getMaxAmendIdLoginUserDetlsProduct(login.getLoginid().getLoginid(),String.valueOf(user.getLoginUserId().getProductid()));
					// userId.setAmendid(0L);
					// user.setLoginUserId(userId);
					user.setOpenCoverNo(
							map.get(i).get("open_cover_no") == null ? "" : map.get(i).get("open_cover_no").toString());
					user.setSpecialDiscount(map.get(i).get("specialDis") == null ? new BigDecimal(0.0)
							: new BigDecimal(map.get(i).get("specialDis").toString()));
					user.setEntryDate(new Date());
					user.setInceptionDate(new Date());
					loginUserRep.save(user);
				}
			}
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}

	public int insertUserMgtPersonalInfo(UserMgtInsertRequest req) {
		int count = 0;
		try {
			PersonalInfo info = new PersonalInfo();
			info.setCustomerId(new BigDecimal(req.getCustomerId()));
			info.setApplicationId("2");
			info.setTitle(req.getTitle());
			info.setFirstName(req.getCustFirstName());
			info.setLastName(req.getCustLastName());
			info.setAmendId(new BigDecimal(1));
			info.setNationality(req.getNationality());
			if (StringUtils.isNotBlank(req.getDateOfBirth())) {
				Date datebirth = format.parse(req.getDateOfBirth());
				info.setDob(datebirth);
			}
			info.setGender(req.getGender());
			info.setTelephone(req.getTelephoneNo());
			info.setMobile(req.getMobileNo());
			info.setFax(req.getFax());
			info.setEmail(req.getEmail());
			info.setAddress1(req.getAddress1());
			info.setAddress2(req.getAddress2());
			info.setOccupation(req.getOccupation());
			info.setPobox(req.getPoBox());
			info.setCountry(req.getCountry());
			info.setEmirate(req.getCity());
			info.setLoginId(req.getLoginId());
			info.setStatus(StringUtils.isBlank(req.getStatus()) ? "Y" : req.getStatus());
			info.setEntryDate(new Date());
			info.setAgencyCode(req.getUserAgencyCode());
			info.setOaCode(req.getAgencyCode());
			List<LoginMaster> login = autheRepository.findByAgencyCode(req.getAgencyCode());
			if (null != login) {
				// info.setBranchCode(login.getBranchCode());
				// info.setRegionCode(login.getRegionCode());
			}
			personalRep.save(info);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}

	public DefaultAllResponse setDefaultValue(HttpServletRequest http, String encrypt) {
		DefaultAllResponse res = new DefaultAllResponse();
		/*try {
			String token = http.getHeader("Authorization");
			SessionTable session = sessionRep.findById(token).get();
			res.setBranchCode(session.getBranchcode());
			res.setLoginId(session.getLoginid());
			res.setAgencyCode(session.getAgencycode());
			res.setToken(bCryptPasswordEncoder.encode(encrypt));
			res.setProductId(session.getProductid());
			res.setUserType(session.getUsertype());
			res.setOpenCoverNo(session.getOpencoverno());
			res.setCountryCode(session.getCountryid());
			res.setMenuId(session.getMenuid());
			res.setCurrencyName(session.getCurrencyname());
			res.setRegionCode(session.getRegioncode());
			session.setTemptokenid(res.getToken());
			session.setApilink("");
			sessionRep.save(session);
		} catch (Exception e) {
			log.info(e);
		}*/
		return res;
	}

	@SuppressWarnings("static-access")
	public int insertNewUserMgtLonginMaster(UserMgtInsertRequest req) {
		int count = 0;
		try {
			LoginMaster login = new LoginMaster();
			List<LoginMaster> loglist = autheRepository.findByLoginId(req.getLoginId());
			if (!CollectionUtils.isEmpty(loglist)) {
				login = loglist.get(0);
			} else if (!"edit".equalsIgnoreCase(req.getMode())) {
				LoginMasterId loginid = new LoginMasterId();
				loginid.setLoginId(req.getLoginId());
				String pass = endecryService.encrypt(req.getPassword());
				login.setEncryptPassword(pass);  
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				//loginid.setPassword(epass);
				/*String pass = bCryptPasswordEncoder.encode(req.getPassword());
				login.setTokenpassword(pass);*/
				login.setUserIdCreation("N");
				login.setAcExecutiveCreation("N");
				login.setAccesstype("BOTH");
				login.setReferal("Y");
				login.setPwdCount("0");
				login.setAppId("16");
				login.setCompanyId(new BigDecimal(1));
			}
			login.setUsertype(req.getUserType());
			login.setUserid(StringUtils.isBlank(req.getUserId())?new BigDecimal(1):new BigDecimal(req.getUserId()));
			login.setUsername(req.getCustFirstName());
			login.setAgencyCode(req.getUserAgencyCode());
			login.setOaCode(req.getAgencyCode());
			login.setCreatedBy(req.getAgencyCode());
			login.setStatus(StringUtils.isBlank(req.getStatus())?"Y":req.getStatus());
			Instant now = Instant.now();
			Instant after = now.plus(Duration.ofDays(45));
			Date dateAfter = Date.from(after);
			login.setPassdate(dateAfter);
			List<LoginMaster> loginage = autheRepository.findByAgencyCode(req.getAgencyCode());
			if(null != loginage) {
			login.setBranchCode(loginage.get(0).getBranchCode());
			login.setRegionCode(loginage.get(0).getRegionCode());
			}
			login.setUserMail(req.getEmail());
			login.setSubBranch(req.getSubBranchCode());
			login.setEntryDate(new Date());
			login.setCompanyId(StringUtils.isBlank(req.getCountry()) ? new BigDecimal(1) : new BigDecimal(req.getCountry()));
			autheRepository.save(login);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}

	public int insertNewAdminBlock(NewAdminInsertReq req) {
		int count = 0;
		try {
			LoginMaster login = new LoginMaster();
			List<LoginMaster> loglist = autheRepository.findByLoginId(req.getLoginId());
			if (!CollectionUtils.isEmpty(loglist)) {
				login = loglist.get(0);
			} else if (!"edit".equalsIgnoreCase(req.getMode())) {

				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				login.setPassword(epass);
				String pass = endecryService.encrypt(req.getPassword());
				login.setEncryptPassword(pass);   
				login.setAppId("16");
				login.setPwdCount("0");
				login.setAcExecutiveCreation("Y");
				login.setUserIdCreation("Y");
				login.setAccesstype("BOTH");
				login.setCreatedBy("admin");
			}
			login.setUsertype(StringUtils.isBlank(req.getUserType()) ? "admin" : req.getUserType());
			login.setUsername(req.getUserName());
			login.setUserid(new BigDecimal(2));
			login.setAgencyCode(req.getLoginId().toString());
			login.setOaCode(req.getLoginId());
			
			List<BranchMaster> countryId = branchmasterrepository.findByBranchCode(req.getBranchCode());
					//queryem.getAdminOrginalCountry(req.getBranchCode());
			
			login.setCountryId(countryId.get(0).getOriginationCountryId());
			login.setStatus(StringUtils.isBlank(req.getStatus()) ? "Y" : req.getStatus());
			login.setBranchCode(req.getBranchCode());
			login.setRegionCode(req.getRegionCode());
			login.setCompanyId(new BigDecimal(1));
			login.setUserMail(req.getEmail());
			login.setEntryDate(new Date());
			Instant now = Instant.now();
			Instant after = now.plus(Duration.ofDays(45));
			Date dateAfter = Date.from(after);
			login.setPassdate(dateAfter);
			String productIds = "";
			for (int i = 0; i < req.getProductInfo().size(); i++) {
				productIds += req.getProductInfo().get(i).getProductId();
				if (i < req.getProductInfo().size() - 1) {
					productIds += ",";
				}
			}
			login.setProductId(productIds);
			String attachedbranch = "";
			for (int i = 0; i < req.getAttachedBranchInfo().size(); i++) {
				attachedbranch += req.getAttachedBranchInfo().get(i).getAttachedBranchId();
				if (i < req.getAttachedBranchInfo().size() - 1) {
					attachedbranch += ",";
				}
			}
			login.setAttachedBranch(attachedbranch);
			String menuId = "";
			for (int i = 0; i < req.getMenuInfo().size(); i++) {
				menuId += req.getMenuInfo().get(i).getMenuId();
				if (i < req.getMenuInfo().size() - 1) {
					menuId += ",";
				}
			}
			login.setMenuId(menuId);
			String brokerCode = "";
			if (!CollectionUtils.isEmpty(req.getBrokerInfo())) {
				for (int i = 0; i < req.getBrokerInfo().size(); i++) {
					brokerCode += req.getBrokerInfo().get(i).getBrokerCode();
					if (i < req.getBrokerInfo().size() - 1) {
						brokerCode += ",";
					}
				}
				login.setBrokerCodes(brokerCode);
			}
			String uwGrade = "";
			if (!CollectionUtils.isEmpty(req.getUnderWriterInfo())) {
				for (int i = 0; i < req.getUnderWriterInfo().size(); i++) {
					uwGrade += req.getUnderWriterInfo().get(i).getUnderWriter();
					if (i < req.getUnderWriterInfo().size() - 1) {
						uwGrade += ",";
					}
				}
				login.setAttachedUw(","+uwGrade+",");
			}
			autheRepository.save(login);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}

	public int insertBrokerPersonalInfo(AdminNewBrokerReq req, String customerId, String agencyCode) {
		int count = 0;
		try {
			PersonalInfo info = new PersonalInfo();
			info.setCustomerId(new BigDecimal(customerId));
			info.setApplicationId("2");
			info.setTitle(req.getTitle());
			info.setFirstName(req.getCustFirstName());
			info.setLastName(req.getCustLastName());
			info.setAmendId(new BigDecimal(1));
			info.setNationality(req.getNationality());
			if (StringUtils.isNotBlank(req.getDateOfBirth())) {
				Date datebirth = sdfFormat.parse(req.getDateOfBirth());
				info.setDob(datebirth);
			}
			info.setGender(req.getGender());
			info.setTelephone(req.getTelephoneNo());
			info.setMobile(req.getMobileNo());
			info.setFax(req.getFax());
			info.setEmail(req.getEmail());
			info.setAddress1(req.getAddress1());
			info.setAddress2(req.getAddress2());
			info.setOccupation(req.getOccupation());
			info.setPobox(req.getPoBox());
			info.setCountry(req.getCountry());
			info.setEmirate(req.getCity());
			info.setLoginId(req.getLoginId());
			info.setStatus(StringUtils.isBlank(req.getStatus())?"Y":req.getStatus());
			info.setEntryDate(new Date());
			info.setAgencyCode(agencyCode);
			info.setOaCode(agencyCode);
			//info.setBranch_code(req.getBranchCode());
			//info.setRegion_code(req.getRegionCode());
			personalRepo.save(info);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}
	public int insertBrokerCompanyMaster(AdminNewBrokerReq req, String customerid, String brokerCode) {
		int count = 0;
		try {
			BrokerCompanyMaster brocom = new BrokerCompanyMaster();
			brocom.setAgencyCode(brokerCode);
			brocom.setCompanyName(req.getBorkerOrganization());
			brocom.setContactPerson(req.getCustFirstName());
			brocom.setAddress1(req.getAddress1());
			brocom.setAddress2(req.getAddress2());
			brocom.setCity(req.getCity());
			brocom.setCountry(req.getCountry());
			brocom.setPhone(req.getMobileNo());
			brocom.setPobox(StringUtils.isBlank(req.getPoBox()) ? null : new BigDecimal(req.getPoBox()));
			brocom.setFax(req.getFax());
			//brocom.setEmirate(req.getCity());
			brocom.setStatus(StringUtils.isBlank(req.getStatus())?"Y":req.getStatus());
			brocom.setCustomerId(new BigDecimal(customerid));
			brocom.setBranchCode(req.getBranchCode());
			//brocom.setMissippiid(req.getMissippiId());
			//brocom.setApprovedpreparedby(req.getApprovedby());
			brocom.setBrokerCode(req.getBrokerCode());
			brocom.setAcExecutiveId(StringUtils.isBlank(req.getExecutive()) ? null : new BigDecimal(req.getExecutive()));
			//brocom.setIssuercommissiononeoff(StringUtils.isBlank(req.getOneOffCommission()) ? 0.0
			//		: Double.parseDouble(req.getOneOffCommission()));
			//brocom.setIssuercommissionopencover(StringUtils.isBlank(req.getOpenCoverCommission()) ? 0.0
			//		: Double.parseDouble(req.getOpenCoverCommission()));
			brokerCompanyRepo.save(brocom);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}
	public int insertBrokerMaster(AdminNewBrokerReq req, String brokerCode) {
		int count = 0;
		try {
			BrokerMaster bromas = new BrokerMaster();
			bromas.setAgencyCode(brokerCode);
			//String amendid = queryem.getMaxAmendIdBrokerMaster(brokerCode, req.getBranchCode());
			//bromas.setAmendid(
			//		StringUtils.isBlank(amendid) || "null".equalsIgnoreCase(amendid) ? 1L : Long.valueOf(amendid));
			bromas.setBranchCode(req.getBranchCode());
			bromas.setPolicyFeeStatus(req.getPolicyFeeStatus());
			//bromas.setGovttaxstatus(req.getGovtFeeStatus());
			//bromas.setEmergencyfundstatus(req.getEmergencyFee());
			bromas.setPolicyFee(StringUtils.isBlank(req.getPolicyFee()) ? null : new BigDecimal(req.getPolicyFee()));
			//bromas.setGovttax(StringUtils.isBlank(req.getGovetFee()) ? 0.0 : Double.parseDouble(req.getGovetFee()));
			//bromas.setEmergencyfund(
			//		StringUtils.isBlank(req.getEmergencyFund()) ? 0.0 : Double.parseDouble(req.getEmergencyFund()));
			bromas.setStatus(StringUtils.isBlank(req.getStatus())?"Y":req.getStatus());
			bromas.setEntryDate(new Date());
			//bromas.setTaxapplicable(req.getTaxApplicable());
			/*if (StringUtils.isNotBlank(req.getEffectiveDate())) {
				Date effdate = sdfFormat.parse(req.getEffectiveDate());
				bromas.setEffectivedate(effdate);
			}*/
			brokerRepo.save(bromas);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}

	public int insertNewLonginMaster(AdminNewBrokerReq req, String brokerCode) {
		int count = 0;
		try {
			LoginMaster login = new LoginMaster();
			List<LoginMaster> loglist = autheRepository.findByLoginId(req.getLoginId());
			if (!CollectionUtils.isEmpty(loglist)) {
				login = loglist.get(0);
			} else if (!"edit".equalsIgnoreCase(req.getMode())) {
				login.setLoginId(req.getLoginId());
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				login.setPassword(epass);
				
			}
			login.setUsertype("Broker");
			login.setUsername(req.getCustFirstName());
			login.setUserid(new BigDecimal(1));
			login.setAgencyCode(brokerCode);
			login.setOaCode(brokerCode);
			login.setCompanyId(new BigDecimal(2));
			login.setCreatedBy("Admin");
			login.setStatus(StringUtils.isBlank(req.getStatus())?"Y":req.getStatus());
			login.setUserIdCreation("Y");
			login.setAcExecutiveCreation("Y");
			login.setAccesstype("BOTH");
			Instant now = Instant.now();
			Instant after = now.plus(Duration.ofDays(45));
			Date dateAfter = Date.from(after);
			login.setPassdate(dateAfter);
			login.setBranchCode(req.getBranchCode());
			login.setRegionCode(req.getRegionCode());
			login.setUserMail(req.getEmail());
			login.setSubBranch(req.getSubBranchCode());
			login.setPwdCount("0");
			login.setAppId("16");
			// login.setCoreloginid(req.getLoginId());
			login.setCountryId(StringUtils.isBlank(req.getCountry()) ? new BigDecimal(1) : new BigDecimal(req.getCountry()));
			autheRepository.save(login);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}
}
