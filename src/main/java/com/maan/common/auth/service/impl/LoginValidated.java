package com.maan.common.auth.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maan.common.auth.dto.LoginRequest;
import com.maan.common.auth.token.passwordEnc;
import com.maan.common.bean.LoginMaster;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.error.Error;
@Component
public class LoginValidated {
	private Logger log = LogManager.getLogger(LoginValidated.class);
	@Autowired
	private LoginMasterRepository autheRepository;

	public List<Error> LoginValidation(LoginRequest req) {
		List<Error> list = new ArrayList<Error>();
		Optional<LoginMaster> model = null;
		Long count = null;
		String bracount = req.getBranchCode();
		try {
			if (StringUtils.isBlank(req.getUserId())) {
				list.add(new Error("1220", "UserId", "Please enter the userId"));
			}
			if (StringUtils.isBlank(req.getPassword())) {
				list.add(new Error("1221", "Password", "Please enter the password"));
			}
			if (StringUtils.isBlank(req.getBranchCode())) {
				list.add(new Error("1220", "BranchCode", "Please enter the branchCode"));
			}
			if (StringUtils.isNotBlank(req.getBranchCode())) {
				/*
				 * bracount = autheRepository.validateBranch(req.getBranchCode());
				 * if(bracount==0) { list.add(new
				 * Error("1225","BranchCode","Please enter the valid branchCode")); }
				 */
			}

			if (StringUtils.isBlank(req.getCompanyId())) {
				list.add(new Error("1220", "CompanyId", "Please enter the companyId"));
			}
			if (!StringUtils.isNumeric(req.getCompanyId())) {
				list.add(new Error("1225", "CompanyId", "Please enter numeric digits "));
			} else if (StringUtils.isNotBlank(req.getCompanyId())) {
				count = autheRepository.validateComanyId(req.getCompanyId());
				if (count == 0) {
					list.add(new Error("1225", "CompanyId", "Please enter the valid companyId"));
				}
			} else if (!StringUtils.isNumeric(req.getCompanyId())) {
				list.add(new Error("1225", "CompanyId", "Please enter numeric digits "));
			}
			if (StringUtils.isBlank(req.getPassword())) {
				list.add(new Error("1221", "Password", "Please enter the password"));
			}
			if (StringUtils.isNotBlank(req.getPassword()) && StringUtils.isNotBlank(req.getUserId()) && count != 0
					&& bracount != null) {
				// String epass = endecryService.encrypt(req.getPassword().trim());
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				LoginMaster loginid = new LoginMaster();
				// LoginMasterId dd = new LoginMasterId();
				loginid.setLoginId(req.getUserId());
				// loginid.setLoginid(dd);
				// loginid.getLoginid().setLoginid(req.getUserId());
				loginid.setPassword(epass);
				try {
					model = autheRepository.findByCompanyIdAndBranchCodeAndLoginIdAndPassword(
							BigDecimal.valueOf(Long.valueOf(req.getCompanyId())), req.getBranchCode(), req.getUserId(),
							epass);
				} catch (Exception e) {
					log.info("get login details encrypt count =====> ");
				}
				if (!model.isPresent()) {
					try {
						loginid.setPassword(req.getPassword().trim());
						model = autheRepository.findByCompanyIdAndBranchCodeAndLoginIdAndPassword(
								BigDecimal.valueOf(Long.valueOf(req.getCompanyId())), req.getBranchCode(),
								req.getUserId(), epass);
					} catch (Exception e) {
						log.info("get login details original cout =====> ");
					}
				}
				// log.info("get login class details =====> "+model);
				if (!model.isPresent()) {
					list.add(new Error("1222", "UserDetails", "Invalid Password"));
				}
			}
			if ("admin".equalsIgnoreCase(req.getLoginType())) {

				/* }else { */
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				LoginMaster loginid = new LoginMaster();
				loginid.setLoginId(req.getUserId());// getLoginid().
				loginid.setPassword(epass);
				model = autheRepository.findByCompanyIdAndBranchCodeAndLoginIdAndPassword(
						BigDecimal.valueOf(Long.valueOf(req.getCompanyId())), req.getBranchCode(), req.getUserId(),
						epass);
				String userType = StringUtils.isBlank(model.get().getUsertype()) ? "" : model.get().getUsertype();
				String loginType = req.getLoginType();
				boolean issuerCondition = ("RSAIssuer".equalsIgnoreCase(userType));
				boolean brokerCondition = (!"RSAIssuer".equalsIgnoreCase(userType)
						&& ("Broker".equalsIgnoreCase(loginType) || "".equalsIgnoreCase(loginType)
								|| "user".equalsIgnoreCase(loginType)));
				boolean adminCondition = ("admin".equalsIgnoreCase(userType));
				if (issuerCondition || brokerCondition || adminCondition) {
					if (issuerCondition || adminCondition) {
						String attachedBranch = (StringUtils.isBlank(model.get().getAttachedBranch())
								|| model.get().getAttachedBranch() == null) ? "" : model.get().getAttachedBranch();
						if ("".equals(attachedBranch)) {
							// list.add("2773");
						} else if (StringUtils.isNotBlank(req.getBranchCode())
								&& !attachedBranch.contains(req.getBranchCode())) {
							// list.add("2774");
						}
					}

				} else {
					// list.add("2775");
				}
			}
		} catch (Exception e) {
			log.info(e);
		}
		return list;
	}

	/*
	 * public List<Error> LoginChangePwdValidation(IssuerChangePassReq req) {
	 * List<Error> list = new ArrayList<Error>(); try { Long comCount =null; Long
	 * branchCount =null; if (StringUtils.isBlank(req.getBranchCode())) {
	 * list.add(new Error("1220","BranchCode","Please enter the branchCode"));
	 * }if(StringUtils.isNotBlank(req.getBranchCode())) { List<String> branches =
	 * new ArrayList<String>(Arrays.asList(req.getBranchCode().split(","))); for(int
	 * i = 0;i<branches.size();i++ ) { branchCount =
	 * autheRepository.validateBranch(branches.get(i) ); if(branchCount==0) {
	 * list.add(new Error("1225","BranchCode",branches.get(i) +
	 * " is not a valid branchCode")); } } }
	 * 
	 * if (StringUtils.isBlank(req.getCompanyId())) { list.add(new
	 * Error("1220","CompanyId","Please enter the companyId")); }if
	 * (!StringUtils.isNumeric(req.getCompanyId())){ list.add(new
	 * Error("1225","CompanyId","Please enter numeric digits ")); }else
	 * if(StringUtils.isNotBlank(req.getCompanyId())) { comCount =
	 * autheRepository.validateComanyId(req.getCompanyId()); if(comCount==0) {
	 * list.add(new Error("1225","CompanyId","Please enter the valid companyId")); }
	 * }else if (!StringUtils.isNumeric(req.getCompanyId())){ list.add(new
	 * Error("1225","CompanyId","Please enter numeric digits "));
	 * }if(StringUtils.isBlank(req.getOldpassword())){ list.add(new
	 * Error("101","Oldpassword","Please enter the password"));
	 * }if(StringUtils.isBlank(req.getNewPassword())){ list.add(new
	 * Error("102","NewPassword","Please enter the newpassword")); }else
	 * if(req.getNewPassword().equals(req.getOldpassword())){ list.add(new
	 * Error("103","ChangePassword","Oldpassword  and Newpassword should not match")
	 * ); }else if(!validPassword(req.getNewPassword())){ list.add(new
	 * Error("104","NewPassword","Please enter the valid password")); }else if
	 * (StringUtils.isNotBlank(req.getOldpassword()) &&
	 * StringUtils.isNotBlank(req.getUserId())&& comCount!=0&&branchCount!=0) {
	 * passwordEnc passEnc = new passwordEnc(); String epass =
	 * passEnc.crypt(req.getOldpassword().trim()); Optional<LoginMaster> model
	 * =autheRepository.findByCompanyIdAndBranchCodeAndLoginIdAndPassword(BigDecimal
	 * .valueOf(Long.valueOf(req.getCompanyId())), req.getBranchCode(),
	 * req.getUserId(), epass); if(!model.isPresent()) { list.add(new
	 * Error("105","ChangePassword","You are not authorized user..!")); } } }catch
	 * (Exception e) { log.info(e); } return list; } public List<Error>
	 * forgetPwdValidation(ForgotPasswordRequest req) { List<Error> list = new
	 * ArrayList<Error>(); try { Long branchCount =null; Long comCount=null;
	 * List<LoginMaster> model =null; if (StringUtils.isBlank(req.getBranchCode()))
	 * { // Sami list.add(new
	 * Error("1220","BranchCode","Please enter the branchCode")); }
	 * if(StringUtils.isBlank(req.getBranchCode())) { LoginMaster loginData =
	 * autheRepository.findByLoginIdAndCompanyId(req.getUserId() ,new
	 * BigDecimal(req.getCompanyId()));
	 * 
	 * List<String> branches = new
	 * ArrayList<String>(Arrays.asList(loginData.getBranchCode().split(",")));
	 * for(int i = 0;i<branches.size();i++ ) { branchCount =
	 * autheRepository.validateBranch(branches.get(i) ); if(branchCount==0) {
	 * list.add(new Error("1225","BranchCode",branches.get(i) +
	 * " is not a valid branchCode")); } } } if
	 * (StringUtils.isBlank(req.getCompanyId())) { list.add(new
	 * Error("1220","CompanyId","Please enter the companyId")); }if
	 * (!StringUtils.isNumeric(req.getCompanyId())){ list.add(new
	 * Error("1225","CompanyId","Please enter numeric digits ")); }else
	 * if(StringUtils.isNotBlank(req.getCompanyId())) { comCount =
	 * autheRepository.validateComanyId(req.getCompanyId()); if(comCount==0) {
	 * list.add(new Error("1225","CompanyId","Please enter the valid companyId")); }
	 * 
	 * }if (StringUtils.isBlank(req.getUserId())) { list.add(new
	 * Error("1220","UserId","Please enter the userId"));
	 * }if(StringUtils.isBlank(req.getMailId())) { list.add(new
	 * Error("1260","MaildId","Please enter the mailId")); }else
	 * if(!emailValidate(req.getMailId())) { list.add(new
	 * Error("1261","MaildId","Please enter the valid mailId")); }else
	 * if(StringUtils.isNotBlank(req.getUserId())&&StringUtils.isNotBlank(req.
	 * getCompanyId())&&StringUtils.isNotBlank(req.getBranchCode())&&StringUtils.
	 * isNotBlank(req.getMailId())&& branchCount!=0&&comCount!=0) { model =
	 * autheRepository.findByAppIdAndUserMailAndCompanyIdAndBranchCode("16",req.
	 * getMailId(),BigDecimal.valueOf(Long.valueOf(req.getCompanyId())),req.
	 * getBranchCode()); if(CollectionUtils.isEmpty(model)) { list.add(new
	 * Error("1261","MaildId","Your are enter not authenticated mailId")); } }
	 * if(StringUtils.isNotBlank(req.getUserId())&&StringUtils.isNotBlank(req.
	 * getCompanyId())&&StringUtils.isNotBlank(req.getBranchCode())&&!
	 * CollectionUtils.isEmpty(model)&& branchCount!=0&&comCount!=0) {
	 * List<LoginMaster> table =
	 * autheRepository.findByLoginIdAndAppIdAndUserMailAndCompanyIdAndBranchCode(req
	 * .getUserId(),"16",req.getMailId(),BigDecimal.valueOf(Long.valueOf(req.
	 * getCompanyId())),req.getBranchCode()); if(CollectionUtils.isEmpty(table)) {
	 * list.add(new Error("1261","User","Your are not authenticated user")); } }
	 * }catch (Exception e) { log.info(e); } return list; }
	 */
	public boolean validPassword(String newpassword) {
		Pattern pattern = Pattern.compile("(?=\\S+$).{7,20}");
		Matcher matcher = pattern.matcher(newpassword);
		return matcher.matches();
	}

	@SuppressWarnings("static-access")
	private boolean emailValidate(String email) {
		String email_patrn = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(email_patrn);
		return pattern.matches(email_patrn, email);
	}
}