package com.maan.common.auth.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maan.common.auth.dto.ForgotPasswordRequest;
import com.maan.common.auth.dto.LoginRequest;
import com.maan.common.auth.dto.Menu;
import com.maan.common.auth.dto.resp.AuthToken;
import com.maan.common.auth.dto.resp.LoginProductResponse;
import com.maan.common.auth.dto.resp.LoginResponse;
import com.maan.common.auth.dto.resp.TravelLoginResponse;
import com.maan.common.auth.service.AuthendicationService;
import com.maan.common.auth.token.EncryDecryService;
import com.maan.common.auth.token.passwordEnc;
import com.maan.common.bean.BranchMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.LoginMasterId;
import com.maan.common.bean.MailMaster;
import com.maan.common.bean.MailMasterId;
import com.maan.common.bean.MenuMaster;
import com.maan.common.bean.SessionDetails;
import com.maan.common.error.ErrorInstance;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.repository.MailMasterRepository;
import com.maan.common.repository.MenuMasterRepository;
import com.maan.common.repository.SessionDetailsRepository;
import com.maan.common.error.Error;
 

@Service
public class AuthendicationServiceImpl implements AuthendicationService, UserDetailsService {

	@PersistenceContext
	private EntityManager em;

	private Logger log = LogManager.getLogger(AuthendicationServiceImpl.class);
	@Autowired
	private LoginMasterRepository autheRepository;

	@Autowired
	private EncryDecryService endecryService;

	 @Autowired
	 private LoginService compQuote;

	@Autowired
	private MailMasterRepository mailMasRep;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private BranchMasterRepository branchRep;

	@Autowired
	private SessionDetailsRepository sessionRep;

	// @Autowired
	// private RegionMasterRepository regionRepo;

	@Autowired
	private BranchMasterRepository branchRepo;
	
	@Autowired
	private MenuMasterRepository menuRepo;

	@Override
	@SuppressWarnings("static-access")
	public LoginMaster getUserDetails(LoginRequest mslogin, List<Error> validation) {
		LoginMaster model = new LoginMaster();
		try {
			if (validation == null || validation.size() == 0) {
				String epassddd = endecryService.encrypt(mslogin.getPassword());
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(mslogin.getPassword());
				LoginMasterId loginid = new LoginMasterId();
				// loginid.setLoginid(userName);
				model.setPassword(epass);
				int count = 0;
				try {
					model = autheRepository.findByCompanyIdAndBranchCodeAndLoginIdAndPassword(
							BigDecimal.valueOf(Long.valueOf(mslogin.getCompanyId())), mslogin.getBranchCode(),
							mslogin.getUserId(), epass).get();// getLoginList(userName,epass);

					count = 1;
				} catch (Exception e) {
					count = 0;
					log.info("get login details secount count =====> " + count);
				}
				if (count == 0) {
					model.setPassword(mslogin.getPassword());
					model = autheRepository.findById(loginid).get();// getLoginList(userName,pass);
					model.setPassword(epass);
				}
				model.setEncryptPassword(epassddd);
			} else {
				// List<Error> error = Errorblock(validation);
				// model.setErrors(error);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@SuppressWarnings("static-access")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginMaster user = new LoginMaster();
		try {
			log.info("loadUserByUsername==>" + username);
			List<LoginMaster> userList = autheRepository.findByLoginId(username);
			if (!CollectionUtils.isEmpty(userList)) {
				user = userList.get(0);
				String pass = bCryptPasswordEncoder.encode(endecryService.decrypt(user.getEncryptPassword()));
				user.setTokenPassword(pass);
				Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
				grantedAuthorities.add(new SimpleGrantedAuthority(user.getUsertype()));
				log.info("loadUserByUserType==>" + user.getUsertype());
				log.info("loadUserByPassword==>" + user.getPassword());
				log.info("loadUserByTokenEncrypt==>" + user.getTokenPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getTokenPassword(),
				getAuthority());
	}

	private List<Error> Errorblock(List<String> validate) {
		String validationDetails = "";
		List<Error> errorsList = new ArrayList<Error>();
		try {
			validationDetails = StringUtils.join(validate.toArray(new String[validate.size()]), ",");
			if (validationDetails.length() > 0) {
				String splitVal[] = validationDetails.split(",");

				for (int i = 0; i < splitVal.length; i++) {
					if (splitVal[i].length() > 0) {
						Error errors = ErrorInstance.get(splitVal[i]);
						if (errors != null)
							errorsList.add(errors);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorsList;
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public TravelLoginResponse checkLoginDetails(LoginMaster req, HttpServletRequest http, List<Error> validation,
			String pwd, String tokenid, String temptoken, String RegionCode, String insuranceId) {
		String appId = "100002";
		LoginResponse resp = new LoginResponse();
		AuthToken model = new AuthToken();
		TravelLoginResponse comRes = new TravelLoginResponse();
		Map<String, Object> brokerDet = new HashMap<String, Object>();
		try {
			// String split[] = tokenid.split("\\.");
			// log.info(split[1]);
			String token = temptoken;
			MailMasterId id = new MailMasterId();
			id.setApplicationId(appId);
			id.setInsCompanyId(new BigDecimal(appId));
			BranchMaster branchName = branchRep.findByBranchCodeAndInsCompanyId(req.getBranchCode() , new BigDecimal(appId));
			MailMaster mail = mailMasRep.findById(id).get();
			String pwdlen[] = mail.getPwdLen() == null ? null : mail.getPwdLen().toString().split("-");
			/*
			 * http.getSession().setAttribute("expiTime"+token, mail.getExptime());
			 * http.getSession().setAttribute("expiDate"+token, mail.getExpdate());
			 * http.getSession().setAttribute("pwdCount"+token, mail.getPwdcnt());
			 * http.getSession().setAttribute("pwdLenMin"+token, pwdlen[0]);
			 * http.getSession().setAttribute("pwdLenMax"+token, pwdlen[1]);
			 * http.getSession().setAttribute("appID"+token, appId);
			 */

			ForgotPasswordRequest req2 = new ForgotPasswordRequest();
			req2.setUserId(req.getLoginId());
			log.info("Login::::" + req.getBranchCode());
			if (!(validation.size() > 0)) {
				String[] statuses = compQuote.validateUser(req2, pwd, String.valueOf(mail.getApplicationId()),
						String.valueOf(mail.getPwdCnt()), validation);
				String status1 = statuses[0];
				if (status1 == null) {
					String userStatus = statuses[1];
					boolean pass = compQuote.checkPasswordChange(req.getLoginId(), userStatus, mail.getExpTime(),
							mail.getExpDate(), mail.getApplicationId(), insuranceId);
					if (pass) {
						Map<String, Object> userDetails = compQuote.getUserInfo(req.getLoginId(),
								mail.getApplicationId());
						if (userDetails == null || userDetails.size() <= 0) {
							validation.add(new Error("E110", "Email_id",
									"User does't have Valid E-Mail Id. Please contact Admin"));
						} else {
							resp.setLoginId(StringUtils.isBlank(userDetails.get("LOGIN_ID").toString()) ? ""
									: userDetails.get("LOGIN_ID").toString());
							resp.setBranchCode(req.getBranchCode());
							resp.setUserType(StringUtils.isBlank(userDetails.get("USERTYPE").toString()) ? ""
									: userDetails.get("USERTYPE").toString());
							if ("changepwd".equalsIgnoreCase(resp.getStatus())) {
								resp.setStatus("changepwd");
								resp.setResult("changePWD");
							} else {
								// sessionRep.insertSessionInfo(req.getLoginId(), tokenid,
								// temptoken,compQuote.getClientIpAddr(http));
								SessionDetails table = new SessionDetails();
								// SessionTablePk pk = new SessionTablePk();
								table.setLoginId(req.getLoginId());
								table.setTokenId(tokenid);
								// table.setSessionPk(pk);
								table.setStatus("Y");
								table.setTempTokenid(temptoken);
								table.setEntryDate(new Date());
								sessionRep.save(table);
								if (userDetails != null && userDetails.size() > 0) {
									model.setUserType(userDetails.get("USERTYPE") == null ? ""
											: userDetails.get("USERTYPE").toString());
									model.setOaCode(userDetails.get("OA_CODE") == null ? ""
											: userDetails.get("OA_CODE").toString());
									model.setAgencyCode(userDetails.get("AGENCY_CODE") == null ? ""
											: userDetails.get("AGENCY_CODE").toString());
									model.setMenuId(userDetails.get("MENU_ID") == null ? ""
											: userDetails.get("MENU_ID").toString());
									model.setBranchCode(userDetails.get("BRANCH_CODE") == null ? ""
											: userDetails.get("BRANCH_CODE").toString());
									model.setBranchName(userDetails.get("BRANCH_NAME") == null ? ""
											: userDetails.get("BRANCH_NAME").toString());
									model.setAttachedBranch(userDetails.get("ATTACHED_BRANCH") == null ? ""
											: userDetails.get("ATTACHED_BRANCH").toString());
									model.setBelongingBranch(userDetails.get("BELONGING_BRANCH") == null ? ""
											: userDetails.get("BELONGING_BRANCH").toString());
									model.setAccessType(userDetails.get("ACCESSTYPE") == null ? ""
											: userDetails.get("ACCESSTYPE").toString());
									model.setLoginId(userDetails.get("LOGIN_ID") == null ? ""
											: userDetails.get("LOGIN_ID").toString());
									model.setCompanyId(userDetails.get("COMPANY_ID") == null ? ""
											: userDetails.get("COMPANY_ID").toString());
									model.setToken(temptoken);
									if ("admin".equalsIgnoreCase(userDetails.get("USERTYPE").toString())) {
										/*
										 * http.getSession().setAttribute("MENU_ID"+token,
										 * userDetails.get("MENU_ID")==null?"":userDetails.get("MENU_ID").toString());
										 * http.getSession().setAttribute("user"+token, userDetails.get("LOGIN_ID"));
										 * http.getSession().setAttribute("user1"+token, userDetails.get("USERTYPE"));
										 * http.getSession().setAttribute("usertype"+token,
										 * userDetails.get("USERTYPE"));
										 */

										if ("admin".equalsIgnoreCase(req.getUsertype()) && !("Broker"
												.equalsIgnoreCase(userDetails.get("USERTYPE").toString())
												|| "User".equalsIgnoreCase(userDetails.get("USERTYPE").toString()))) {
											resp.setResult("home");
										} else {
											validation.add(
													new Error("E128", "LoginType", "Please Choose Correct Login Type"));
											resp.setResult("INPUT");
										}

										brokerDet = compQuote
												.getBrokerDetails(userDetails.get("BRANCH_CODE").toString());

									} else {
										String userType = userDetails.get("USERTYPE").toString();
										String loginType = req.getUsertype();
										boolean issuerCondition = ("RSAIssuer".equalsIgnoreCase(userType) )
												|| ("admin".equalsIgnoreCase(loginType))
												|| ("claimofficer".equalsIgnoreCase(loginType))
												|| ("garage".equalsIgnoreCase(loginType))
												|| ("user".equalsIgnoreCase(loginType))
												|| ("surveyor".equalsIgnoreCase(loginType));
										boolean brokerCondition = (!"RSAIssuer".equalsIgnoreCase(userType)
												&& ("Broker".equalsIgnoreCase(loginType)
														|| "".equalsIgnoreCase(loginType)));

										if (issuerCondition || brokerCondition) {
											if (issuerCondition) {
												/*
												 * String attachedBranch = (userDetails.get("ATTACHED_BRANCH") == null
												 * || "".equals(userDetails.get("ATTACHED_BRANCH").toString())) ? "" :
												 * userDetails.get("ATTACHED_BRANCH").toString(); if
												 * ("".equals(attachedBranch)) { validation.add(new Error("E129",
												 * "BranchCode",
												 * "This Operational User is not Attached for any Branch")); } else if
												 * (StringUtils.isNotBlank(req.getBranchCode()) &&
												 * !attachedBranch.contains(req.getBranchCode())) { validation.add(new
												 * Error("E130", "BranchCode",
												 * "This Operational User are not Attached for this Branch to Login"));
												 * }
												 */
											}
											if (!(validation.size() > 0)) {
												// http.getSession().setAttribute("ses",
												// request.getSession(false).getId());
												/*
												 * http.getSession().setAttribute("user1"+token, "brokers");
												 * http.getSession().setAttribute("rsa_type"+token,"s");
												 * http.getSession().setAttribute("usertype"+token,
												 * userDetails.get("USERTYPE"));
												 * http.getSession().setAttribute("user"+token,
												 * userDetails.get("LOGIN_ID"));
												 */
												// http.getSession().setAttribute("userLoginMode",
												// context.getRealPath("").indexOf("Test")!=-1?"Test":"Live");
												// http.getSession().setAttribute("swidth", req.getSwidth());
												brokerDet = compQuote
														.getBrokerDetails(userDetails.get("BRANCH_CODE").toString());
												/*
												 * http.getSession().setAttribute("currencyType"+token,brokerDet.get(
												 * "CurrencyAbb"));
												 * http.getSession().setAttribute("decimalPlace"+token,brokerDet.get(
												 * "CurrencyDecimal"));
												 * http.getSession().setAttribute("BelongingBranch"+token,userDetails.
												 * get("BELONGING_BRANCH"));
												 * http.getSession().setAttribute("LoginType"+token, "B2C");
												 * http.getSession().setAttribute("LoginBranchCode1"+token,userDetails.
												 * get("BRANCH_CODE"));
												 */
												if (("RSAIssuer".equalsIgnoreCase(userType)
														|| "admin".equalsIgnoreCase(loginType))) {
													http.getSession().setAttribute("branchName" + token,
															branchName.getBranchName());
												} else {
													http.getSession().setAttribute("branchName" + token,
															userDetails.get("BRANCH_NAME"));

												}
												/*
												 * http.getSession().setAttribute("AdminBranchCode"+token,
												 * issuerCondition ?
												 * req.getBranchcode():userDetails.get("BRANCH_CODE"));
												 * http.getSession().setAttribute("LoginBranchCode"+token,
												 * issuerCondition ?
												 * req.getBranchcode():userDetails.get("BELONGING_BRANCH"));
												 * http.getSession().setAttribute("adminBranch"+token, issuerCondition ?
												 * req.getBranchcode():userDetails.get("BRANCH_CODE"));
												 */
												resp.setResult("home");
											} else {
												resp.setResult("INPUT");
											}
										} else {
											// validation.add(new Error("E128", "LoginType", "Please Choose Correct
											// Login Type"));
											resp.setResult("INPUT");
										}
									}
									model.setOrginationCountryId(brokerDet.get("Orgination") == null ? ""
											: brokerDet.get("Orgination").toString());
									model.setDestinationCountryId(brokerDet.get("Destination") == null ? ""
											: brokerDet.get("Destination").toString());
									model.setCurrencyAbbreviation(brokerDet.get("CurrencyName") == null ? ""
											: brokerDet.get("CurrencyName").toString());
									model.setTax(brokerDet.get("Tax") == null ? "" : brokerDet.get("Tax").toString());
									model.setSite(
											brokerDet.get("Site") == null ? "" : brokerDet.get("Site").toString());
									model.setLangYn(
											brokerDet.get("LANG") == null ? "" : brokerDet.get("LANG").toString());
									model.setCurrencyDecimal(brokerDet.get("CurrencyDecimal") == null ? ""
											: brokerDet.get("CurrencyDecimal").toString());
									/*
									 * http.getSession().setAttribute("LoginIdType"+token,
									 * req.getLoginid().getLoginid());
									 * http.getSession().setAttribute("selectedBranch"+token,req.getBranchcode());
									 */
									if (!resp.getResult().equalsIgnoreCase("input")
											&& resp.getResult().equalsIgnoreCase("home")) {
										model.setToken(temptoken);
										compQuote.insertOrUpdate(userDetails, branchName, tokenid, temptoken,
												brokerDet.get("CurrencyName") == null ? ""
														: brokerDet.get("CurrencyName").toString());
									}
								}
							}
						}
					} else {
						resp.setStatus("changepwd");
						// http.getSession().setAttribute("cpLoginId"+token,
						// req.getLoginid().getLoginid());
						resp.setResult("changePWD");
					}
				} else if ("changepwd".equalsIgnoreCase(status1)) {
					// http.getSession().setAttribute("cpLoginId"+token,
					// req.getLoginid().getLoginid());
					resp.setStatus("changepwd");
					resp.setResult("changePWD");
				} else if ("changePwd".equalsIgnoreCase(resp.getStatus())) {
					// http.getSession().setAttribute("cpLoginId"+token,req.getLoginid().getLoginid());
					resp.setResult("changePWD");
				} else {
					resp.setResult("INPUT");
				}
			} else if ("changePwd".equalsIgnoreCase(resp.getStatus())) {
				// http.getSession().setAttribute("cpLoginId"+token,req.getLoginid().getLoginid());
				resp.setResult("changePWD");
			} else {
				resp.setResult("INPUT");
			}
			if ("changePWD".equals(resp.getResult())) {
				model.setStatus("ChangePassword");
				model.setPasswordMsg(
						"New Password Should contain one uppercase, one lowercase, one number, one special character(@ # $ %) & length between ");
				// + pwdlen[0] + " to " + pwdlen[1]);
			}
			if (validation != null && validation.size() > 0) {
				// List<Error> error = Errorblock(validation);
				comRes.setErrors(validation);
			}
			if ("home".equals(resp.getResult())) {
				List<LoginProductResponse> list = getProductDetails(req.getLoginId(), req.getUsertype(),
						req.getBranchCode(), String.valueOf(req.getCompanyId()));
				model.setProductDetail(list);
			 if(model.getMenuId()!=null && model.getMenuId().indexOf(",")!=-1) {
				 String[] split = model.getMenuId().split(",");
				 List<String> asList = Arrays.asList(split);
				 model.setMenuList(getMenuList(req.getUsertype(), asList));
			 }				
				
				
			}
			model.setRegionCode(RegionCode);
			comRes.setLoginResponse(model);
		} catch (Exception e) {
			log.info(e);
		}
		return comRes;
	}
	public List<Menu> getMenuList(String usertype ,List<String> menuid){
		try {
			List<MenuMaster> findBymenuList = menuRepo.findBymenuList(menuid,usertype);
			List<Menu> menus=new ArrayList<Menu>();
			List<Menu> menusret=new ArrayList<Menu>();
			for (MenuMaster menuMaster : findBymenuList) {
				Menu m = Menu.builder().name(menuMaster.getMenuName()).url(menuMaster.getMenuUrl()).id(menuMaster.getMenuId().toString()).parent(menuMaster.getParentMenu()).build();
				menus.add(m);
			}
			 List<Menu> collect = menus.stream().filter(i-> "99999".equals(i.getParent())).collect(Collectors.toList());
			log.info("collect"+collect);
			 for (Menu menu : collect) {
				 Menu m = menu;
				 m.setChildren(menus.stream().filter(i -> (!"99999".equals(i.getParent()) && menu.getId().equals(i.getParent()))).collect(Collectors.toList()));
				 menusret.add(m);
			}
			return menusret;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	} 

	@SuppressWarnings("unchecked")
	public List<LoginProductResponse> getProductDetails(String loginId, String userType, String branchCode,
			String companyId) {
		List<LoginProductResponse> resultList = new ArrayList<LoginProductResponse>();
		Query query = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if ("Broker".equalsIgnoreCase(userType) || "User".equalsIgnoreCase(userType)) {
				list = autheRepository.getProductBroker(branchCode, loginId, branchCode, companyId);
			} else {
				String productIds = autheRepository.getProductId(loginId);
				List<LoginMaster> loginlist = autheRepository.findByLoginId(loginId);
				productIds = loginlist.get(0).getProductId().replaceAll(", ", ",");
				productIds = productIds.replaceAll(" ,", ",");
				productIds = productIds.replaceAll(",", "','");
				query = em.createNativeQuery(
						"SELECT PRODUCT_ID, PRODUCT_NAME FROM PRODUCT_MASTER WHERE STATUS='Y' AND BRANCH_CODE=(SELECT   BELONGING_BRANCH FROM   BRANCH_MASTER BM WHERE   BRANCH_CODE = ?1 AND STATUS = 'Y'  AND AMEND_ID =(SELECT   MAX (AMEND_ID) FROM   BRANCH_MASTER BM1 WHERE   BM1.BRANCH_CODE = BM.BRANCH_CODE  AND BM1.STATUS = BM.STATUS)) AND PRODUCT_ID IN ('"
								+ productIds + "') ORDER BY DISPLAY_ORDER ASC");
				query.setParameter(1, branchCode);
				query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				list = query.getResultList();
			}
			if (!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					LoginProductResponse prod = new LoginProductResponse();
					prod.setProductId(s.get("PRODUCT_ID") == null ? "" : s.get("PRODUCT_ID").toString());
					prod.setProductName(s.get("PRODUCT_NAME") == null ? "" : s.get("PRODUCT_NAME").toString());
					prod.setSchemeList(getSchemeList(loginId, userType, branchCode,companyId,s.get("PRODUCT_ID") == null ? "" : s.get("PRODUCT_ID").toString()));
					resultList.add(prod);
				});
			}
		} catch (Exception e) {
			log.info(e);
		}
		return resultList;
	}

	private List<LoginProductResponse> getSchemeList(String loginId, String userType, String branchCode,String companyId, String productId) {
		List<LoginProductResponse> result = new ArrayList<LoginProductResponse>();
		if( !"admin".equalsIgnoreCase(userType)) {
			Query  query = em.createNativeQuery(
					"Select Mm_Moduleid,Mm_Modulename From M_Module Where Mm_Moduleid In(Select Regexp_Substr(Policytype_Id,'[^,]+', 1,Level) From Login_User_Details Lm Where Lm.Login_Id =?1 And Lm.Product_Id=?2 Connect By Regexp_Substr(Policytype_Id,'[^,]+', 1, Level) Is Not Null)");
					/*"select MM_MODULEID,MM_MODULENAME from m_module where mm_moduleid in (1,2)");*/
			query.setParameter(1, loginId);
			query.setParameter(2, productId); 
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>> resultList = query.getResultList();
			if (!CollectionUtils.isEmpty(resultList)) {
				for (Map<String, Object> map : resultList) {
					LoginProductResponse prod = new LoginProductResponse();
					prod.setProductId(map.get("MM_MODULEID") == null ? "" : map.get("MM_MODULEID").toString());
					prod.setProductName(map.get("MM_MODULENAME") == null ? "" : map.get("MM_MODULENAME").toString());					 
					result.add(prod);
				}
			}
			
			
		}else{
			Query  query = em.createNativeQuery(
					"Select Mm_Moduleid,Mm_Modulename From M_Module Where Mm_Moduleid In(Select Regexp_Substr(Policytype_Id,'[^,]+', 1,Level) From Login_User_Details Lm Where Lm.Login_Id =?1 And Lm.Product_Id=?2 Connect By Regexp_Substr(Policytype_Id,'[^,]+', 1, Level) Is Not Null)");
					
			query.setParameter(1, loginId);
			query.setParameter(2, productId); 
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>> resultList = query.getResultList();
			if (!CollectionUtils.isEmpty(resultList)) {
				for (Map<String, Object> map : resultList) {
					LoginProductResponse prod = new LoginProductResponse();
					prod.setProductId(map.get("MM_MODULEID") == null ? "" : map.get("MM_MODULEID").toString());
					prod.setProductName(map.get("MM_MODULENAME") == null ? "" : map.get("MM_MODULENAME").toString());					 
					result.add(prod);
				}
			}
		}
		return result;
	}

	@Override
	public LoginMaster loginuserCheck(LoginRequest mslogin) {
		LoginMaster res = new LoginMaster();
		try {
		res = autheRepository.findByLoginIdAndUsertypeAndCompanyId(mslogin.getUserId(),mslogin.getLoginType(),new BigDecimal(mslogin.getCompanyId()));
		} catch (Exception e) {
			res=null;
			log.info(e);
		}
		return res;
	}

	@Override
	public LoginMaster loginuserCheckWithoutIns(LoginRequest mslogin) {
		LoginMaster res = new LoginMaster();
		try {
		res = autheRepository.findByLoginIdAndCompanyId(mslogin.getUserId(),new BigDecimal(mslogin.getCompanyId()));
		} catch (Exception e) {
			res=null;
			log.info(e);
		}
		return res;
	}
	
}
