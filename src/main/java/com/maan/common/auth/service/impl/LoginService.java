package com.maan.common.auth.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.maan.common.auth.dto.ForgotPasswordRequest;
import com.maan.common.auth.dto.resp.DefaultAllResponse;
import com.maan.common.auth.token.EncryDecryService;
import com.maan.common.auth.token.passwordEnc;
import com.maan.common.bean.BranchMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.MailMaster;
import com.maan.common.bean.MailMasterId;
import com.maan.common.bean.SessionDetails;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.repository.MailMasterRepository;
import com.maan.common.repository.SessionDetailsRepository;
import com.maan.common.error.Error;


@Component
public class LoginService {
	private Logger log = LogManager.getLogger(LoginService.class);
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
	@Autowired                         
	private LoginQueryImpletation queryem;  
	@Autowired                                         
	private LoginMasterRepository autheRepository;  
	@Autowired                               
	private MailMasterRepository mailMasRep; 
	//@Autowired                       
	//private MailTrigger mailTrigger; 
	@Autowired                                 
	private EncryDecryService endecryService;
	@Autowired
	private SessionDetailsRepository sessionRep;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private BranchMasterRepository branchRep;
	
	
	
	//@Autowired
	//private SurveyorApprovalDetailServiceImpl surApprovService;
	
	@Autowired
	private EncryDecryService encryDecry;
	
	SimpleDateFormat sdfFormat = new SimpleDateFormat();
	
	public String[] validateUser(ForgotPasswordRequest req, String pwd, String app_id, String pwdcount, List<Error> valid) {
		String result[] = new String[4];
		int pwdcount1 = Integer.parseInt(pwdcount);
		try {
			// String epwd = Jcrypt.crypt(pwd);passEnc
			//String epwd = endecryService.encrypt(pwd);
			passwordEnc passEnc = new passwordEnc();
			String epwd = passEnc.crypt(pwd);
			Map<String, Object> login = queryem.getLoginMaster(req.getUserId()  , app_id);
			if (login == null || login.size() <= 0) {
				// result[0]= ResourceBundle.getBundle("app_field_names").getString("E113");
				// result[2]="E113";
				valid.add(new Error("E113","Login","You are not authorize user"));
			} else {
				Map<String, Object> loginels = null;
				loginels = queryem.getLoginMasterelse(req.getUserId()  , epwd, app_id);
				if (loginels == null || loginels.size() <= 0) {
					loginels = queryem.getLoginMasterelse(req.getUserId()  , pwd, app_id);
				}
				if (loginels == null || loginels.size() <= 0) {
					if (Integer.parseInt(loginels.get("PWD_COUNT").toString()) == (pwdcount1 - 2)) {
						valid.add(new Error("E114","",""));
						/// result[0]= ResourceBundle.getBundle("app_field_names").getString("E114");
						// result[2]="E114";
					} else if (Integer.parseInt(loginels.get("PWD_COUNT").toString()) == (pwdcount1 - 1)) {
						valid.add(new Error("E117","",""));
						result[0] = "This User is locked due to invalid login of more than " + pwdcount1 + " times";
						// result[2]="E117";
					} else {
						valid.add(new Error("E115","",""));
						// result[0]= ResourceBundle.getBundle("app_field_names").getString("E115");
						// result[2]="E115";
					}
					updateCount(req  , pwdcount, app_id);
				} else {
					if (loginels == null || "N".equals(loginels.get("STATUS"))) {
						// result[0]= ResourceBundle.getBundle("app_field_names").getString("E116");
						// result[1]=loginels.get("status").toString();
						// result[2]="E116";
						valid.add(new Error("E116","",""));
					} else if (Integer.parseInt(loginels.get("PWD_COUNT").toString()) > pwdcount1) {
						result[0] = "This User is locked due to invalid login of more than " + pwdcount1 + " times";
						// result[2]="E117";
						valid.add(new Error("E117","",""));
					} else if ("T".equals(loginels.get("STATUS"))) {
						result[0] = "changepwd";
						// result[2]="E119";
						valid.add(new Error("E119","",""));
					} else {
						result[0] = null;
						result[1] = loginels.get("STATUS").toString();
						// result[2]="E100";
						// valid.add("E100");
						/*int status = autheRepository.updatePwdCountReset(userId, "0", app_id);
						log.info("password count update ====> " + status);*/
						List<LoginMaster> marlog = autheRepository.findByLoginIdAndAppId(req.getUserId()  ,app_id);
		               if(!CollectionUtils.isEmpty(marlog)) {
		            	   LoginMaster log = marlog.get(0);
		            	   log.setPwdCount("0");
		            	   autheRepository.save(log);
		               }
					}
				}
			}
		} catch (Exception e) {
			log.info(e);
		}
		return result;
	}

	private void updateCount(ForgotPasswordRequest req, String pwdcount, String app_id) {
		int pwdcount1 = Integer.parseInt(pwdcount);
		int pwdCount = 0;
		try {
			queryem.updatePwdCount(req.getUserId()  , app_id);
			pwdCount = queryem.getPwdInvaildCount(req.getUserId(), app_id);
			if (pwdCount == pwdcount1) {
				queryem.updatePwdStatus("L", req.getUserId(), app_id);
				sendUserPwd(req , "locked", app_id);
			}
		} catch (Exception e) {
			log.info(e);
		}
	}

	public String sendUserPwd( ForgotPasswordRequest req, String temp, String appId) {
		String mail = "";
		String expireTime = "";
		String result = "";
		try {
			MailMasterId id = new MailMasterId();
			id.setApplicationId(appId);
			id.setInsCompanyId(new BigDecimal(appId));
			MailMaster mailmas = mailMasRep.findById(id).get();
		//	Map<String, Object> map = queryem.getUserInfo(req.getUserId() , appId);
		//	mail = map.get("USER_MAIL").toString();
		//	expireTime = queryem.getExpireTime(mailmas.getExpTime().toString());
			if (mailmas == null) {
				result = "User doesn't Exists";
			} else {
				String password = getTempPassword(req.getUserId() , appId);
				
				/* //guestLogin
				String guestUserName = "guest";
				String guestPassword = "Admin@01";	
				//For Token
				LoginMaster loginid = autheRepository.findByloginId(guestUserName);	
				LoginRequest mslogin = new LoginRequest();
				mslogin.setUserId(guestUserName);
				mslogin.setCompanyId(loginid.getCompanyId().toString() );
				mslogin.setLoginType(loginid.getUsertype() );
				mslogin.setPassword(guestPassword);
				 
			//	HttpServletRequest http =HttpServletRequest ;
				String http =  HttpServletRequest.BASIC_AUTH ;	
				TravelLoginResponse guestLogin = loginCon.login(mslogin,http.get  );
				String authCode = guestLogin.getToken(); */
				
				//ExpireTime
				LoginMaster userLogin = autheRepository.findByloginId(req.getUserId());
				
				// Send Password Through Kafka
				String notifTemplateId = "FORGOT_PASSWORD";
				
				//SurSubmail  mailReq = new SurSubmail();
				List<String>  keys = new ArrayList<String>();
				keys.add(password);
				keys.add(sdfFormat.format(userLogin.getExpiryDate()));
				keys.add(req.getUserId());
				keys.add(req.getCompanyId());
				
				//mailReq.setKeys(keys);
				//mailReq.setAuthcode(req.getAuthCode());
				//mailReq.setNotificationTemplateId(notifTemplateId);
				//surApprovService.sendSurSubmail(mailReq);
				
			/*//Send Mail Old Code
			 * 	String password = getTempPassword(user, appId);
				Map<String, String> details = new HashMap<String, String>();
				if (details != null) {
					details.put("MAIL_TO", mail);
					details.put("MAIL_CC", mailmas.getMailCc().toString());
					details.put("USER", user);
					details.put("USERNAME", map.get("USERNAME").toString());
					details.put("PASSWORD", password);
					details.put("SMTP_HOST", mailmas.getSmtpHost().toString());
					details.put("SMTP_USER", mailmas.getSmtpUser().toString());
					details.put("SMTP_PWD", mailmas.getSmtpPwd().toString());
					details.put("MAIL_FROM", mailmas.getSmtpUser().toString());
					details.put("SMTP_PORT", mailmas.getSmtpPort() == null ? "465" : mailmas.getSmtpPort().toString());
					//details.put("SMTP_PORT", "25");
					details.put("expireTime", expireTime); 
				}
				result = mailTrigger.sendPasswordMail(details, temp); */
			}
		} catch (Exception e) {
			log.info(e);
		}
		return result;
	}

	@SuppressWarnings("static-access")
	private String getTempPassword(String user, String appId) {
		final String alphabet = "Aa2Bb@3Cc#4Dd$5Ee%6Ff7Gg&8Hh9Jj2Kk=3L4Mm/5Nn@6Pp7Qq#8Rr$9Ss%2Tt3Uu&4Vv5Ww+6Xx=7Yy8Zz\\9";
		final int N = alphabet.length();
		String temppwd = "";
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			temppwd += alphabet.charAt(r.nextInt(N));
		}
		try {
			passwordEnc passEnc = new passwordEnc();
			String password =  passEnc.crypt(temppwd);
			//String password = endecryService.encrypt(temppwd);// new passwordEnc().crypt(temppwd);sas
			// queryem.updateTempPassword(password,user, appId);
			log.info("newpwd ==>" + password + ":userId ==>" + user + ":Temppassword==>" + temppwd);
			Long count = queryem.getChangePassowrdCount(user, password);
			if (count == 0) {
				List<String> statusList = new ArrayList<String>();
				statusList.add("T");
				statusList.add("Y");
				List<LoginMaster> loginList = autheRepository.findByLoginIdAndAppIdAndStatusIn(user, appId,
						statusList);
				if (!CollectionUtils.isEmpty(loginList)) {
					LoginMaster model = loginList.get(0);
					model.setLpass5(loginList.get(0).getLpass4());
					model.setLpass4(loginList.get(0).getLpass3());
					model.setLpass3(loginList.get(0).getLpass2());
					model.setLpass2(loginList.get(0).getLpass1());
					model.setLpass1(loginList.get(0).getPassword());
					model.setStatus("Y");
					model.setPwdCount("0");
					Instant now = Instant.now();
					Instant after = now.plus(Duration.ofDays(-1));
					Date dateAfter = Date.from(after);
					model.setPassdate(dateAfter);
					model.setExpiryDate(dateAfter);
					autheRepository.save(model);
					String encpass = endecryService.encrypt(temppwd);
					autheRepository.updatePasswordInLM(password,encpass, user);
				}
			}
		} catch (Exception e) {
			log.info(e);
		}
		return temppwd;
	}

	public boolean checkPasswordChange(String userId, String userStatus, String exp_time, String exp_date,
			String app_id,String insuranceid) {
		boolean result = false;
		try {
			if ("Y".equals(userStatus)) {
				int expiDate1 = Integer.parseInt(exp_date);
				int days = queryem.getVaildPwdDay(userId, app_id,insuranceid);
				log.info("Password Changed Before =>" + days + " Days");
				result = days < expiDate1;
			}
			if ("T".equals(userStatus)) {
				int expiTime1 = Integer.parseInt(exp_time);
				int hours = queryem.getVaildPwdTime(userId, app_id);
				log.info("Password Changed Before =>" + hours + " hours");
				result = hours < expiTime1;
			}
			if ("guest".equalsIgnoreCase(userId))
				return true;
		} catch (Exception e) {
			log.info(e);
		}
		return result;
	}

	public Map<String, Object> getUserInfo(String userId, String app_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = queryem.getUserBasicInfo(userId, app_id);
		} catch (Exception e) {
			log.info(e);
		}
		return map;
	}

	public Map<String, Object> getBrokerDetails(String branchCode) {
		Map<String, Object> broDetails = new HashMap<String, Object>();
		try {
			BranchMaster bra = branchRep.findByBranchCode(branchCode).get(0) ; 
			if (null != bra) {
				broDetails.put("Branch", branchCode);
				broDetails.put("CurrencyName",
						StringUtils.isBlank(bra.getCurrencyAbbreviation()) ? bra.getCurrencyAbbreviation() : "SAR");
				broDetails.put("Orgination",
						StringUtils.isBlank(String.valueOf(bra.getOriginationCountryId()))
								? bra.getOriginationCountryId()
								: "1");
				broDetails.put("Destination",
						StringUtils.isBlank(String.valueOf(bra.getDestinationCountryId()))
								? bra.getDestinationCountryId()
								: "1");
				broDetails.put("CurrencyAbb",
						StringUtils.isBlank(bra.getCurrencyAbbreviation())?bra.getCurrencyAbbreviation() :"SAR");
				broDetails.put("CurrencyDecimal",
						StringUtils.isBlank(String.valueOf(bra.getDecimalPlaces()))?bra.getDecimalPlaces() : "2");
				broDetails.put("Tax", StringUtils.isBlank(String.valueOf(bra.getTax()))? bra.getTax() : "0");
				broDetails.put("Site", StringUtils.isBlank(bra.getEmail())?bra.getEmail():"");
				//broDetails.put("LANG", StringUtils.isBlank(bra.getLangyn())?bra.getLangyn(): "");
			}
		} catch (Exception e) {
			log.info(e);
		}
		return broDetails;
	}
	public void insertOrUpdate(Map<String, Object> userDetails, BranchMaster branchName, String tokenid,
			String temptoken, String currencyName) {
		try {
			SessionDetails session = new SessionDetails();
			//SessionTablePk pk = new SessionTablePk();
			session.setBranchCode(
					userDetails.get("BRANCH_CODE") == null ? "" : userDetails.get("BRANCH_CODE").toString());
			session.setBranchName((branchName==null ||branchName.getBranchName()==null)?"":branchName.getBranchName());
			session.setCountryId(
					userDetails.get("COUNTRY_ID") == null ? "" : userDetails.get("COUNTRY_ID").toString());
			session.setMenuId(userDetails.get("MENU_ID") == null ? "" : userDetails.get("MENU_ID").toString());
			session.setTokenId(tokenid);
			session.setUsertype(userDetails.get("USERTYPE") == null ? "" : userDetails.get("USERTYPE").toString());
			session.setLoginId(userDetails.get("LOGIN_ID") == null ? "" : userDetails.get("LOGIN_ID").toString());
			session.setAgencyCode(userDetails.get("AGENCY_CODE") == null ? "" : userDetails.get("AGENCY_CODE").toString());
			session.setCurrencyName(currencyName);
			session.setTempTokenid(temptoken);
			session.setEntryDate(new Date());
			//session.setSessionPk(pk);
			session.setRegionCode((branchName==null ||branchName.getRegionCode()==null)?"":branchName.getRegionCode());  
			sessionRep.save(session);
		} catch (Exception e) {
			log.info(e);
		}
	}
	public DefaultAllResponse setDefaultValue(HttpServletRequest http, String encrypt) {
		DefaultAllResponse res = new DefaultAllResponse();
		try {
			String token = http.getHeader("Authorization");
			//SessionTable session = sessionRep.findBy(token).get();
			SessionDetails session = sessionRep.findByTempTokenid(token);
			res.setBranchCode(session.getBranchCode());
			res.setLoginId(session.getLoginId());
			res.setAgencyCode(session.getAgencyCode());
			res.setToken(bCryptPasswordEncoder.encode(encrypt));
			res.setProductId(session.getProductId());
			res.setUserType(session.getUsertype());
			res.setOpenCoverNo(session.getOpencoverNo());
			res.setCountryCode(session.getCountryId());
			res.setMenuId(session.getMenuId());
			res.setCurrencyName(session.getCurrencyName());
			res.setRegionCode(session.getRegionCode());
			session.setTempTokenid(res.getToken());
			session.setApiLink("");  
			sessionRep.save(session);
		} catch (Exception e) {
			log.info(e);
		}
		return res;
	}

	public String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
