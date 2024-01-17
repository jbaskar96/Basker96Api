package com.maan.common.admin.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.maan.common.error.Error;
import com.maan.common.admin.request.AdminBrokerListReq;
import com.maan.common.admin.request.AdminMenuTypeReq;
import com.maan.common.admin.request.AdminNewBrokerReq;
import com.maan.common.admin.request.AdminNewIssuerReq;
import com.maan.common.admin.request.AttachedBranchReq;
import com.maan.common.admin.request.BranchListRequest;
import com.maan.common.admin.request.BrokerInfoRequest;
import com.maan.common.admin.request.BrokerLoginProductInsReq;
import com.maan.common.admin.request.BrokerProductEditReq;
import com.maan.common.admin.request.IssuerBranchRequest;
import com.maan.common.admin.request.IssuerChangePassReq;
import com.maan.common.admin.request.IssuerExcludedDeleteRequest;
import com.maan.common.admin.request.IssuerIncludedRequest;
import com.maan.common.admin.request.IssuerIncludedSaveRequest;
import com.maan.common.admin.request.IssuerInfoRequest;
import com.maan.common.admin.request.NewAdminInsertReq;
import com.maan.common.admin.request.ProductRequest;
import com.maan.common.admin.request.ProductsWiseMenuRequest;
import com.maan.common.admin.request.TitleDropDownRequest;
import com.maan.common.admin.request.UserCertificateRequest;
import com.maan.common.admin.request.UserMgtInsertRequest;
import com.maan.common.admin.request.UserMgtProductInsertRequest;
import com.maan.common.admin.request.UserProductEditRequest;
import com.maan.common.admin.request.UserProductValidReq;
import com.maan.common.admin.response.AdminBrokerDetailsResp;
import com.maan.common.admin.response.AdminBrokerInsertResponse;
import com.maan.common.admin.response.AdminBrokerResponse;
import com.maan.common.admin.response.AdminBrokerViewResponse;
import com.maan.common.admin.response.AdminDetailsResponse;
import com.maan.common.admin.response.AdminIssuerInfoResponse;
import com.maan.common.admin.response.AdminIssuerInsertResponse;
import com.maan.common.admin.response.AdminIssuerResponse;
import com.maan.common.admin.response.AdminMenuResponse1;
import com.maan.common.admin.response.AdminMenuTypeResp1;
import com.maan.common.admin.response.AdminTableListResponse;
import com.maan.common.admin.response.AdminUserMgtListResponse;
import com.maan.common.admin.response.AdminUserResponse;
import com.maan.common.admin.response.AdminUserResponse1;
import com.maan.common.admin.response.BranchCodeResponse;
import com.maan.common.admin.response.BranchCodeResponse1;
import com.maan.common.admin.response.BranchDetailsResp;
import com.maan.common.admin.response.BranchDetailsResp1;
import com.maan.common.admin.response.BroCommissionDetailsResp;
import com.maan.common.admin.response.BrokerDetailsResponse;
import com.maan.common.admin.response.BrokerEditResponse;
import com.maan.common.admin.response.BrokerMagDropDownResponse;
import com.maan.common.admin.response.BrokerMagDropDownResponse1;
import com.maan.common.admin.response.BrokerProductEditResp;
import com.maan.common.admin.response.BrokerTaxDetailsResp;
import com.maan.common.admin.response.ChangePasswordResponse;
import com.maan.common.admin.response.CommanAdminBrokerViewResp;
import com.maan.common.admin.response.CommanBrokerProductEditResp;
import com.maan.common.admin.response.CommanUserProductEditResp;
import com.maan.common.admin.response.CommanUserProductEditResp1;
import com.maan.common.admin.response.CommonBrokerEditResp;
import com.maan.common.admin.response.CommonBrokerInsertResp;
import com.maan.common.admin.response.CommonExcludedBrokerResp;
import com.maan.common.admin.response.CommonIncludedBrokerResp;
import com.maan.common.admin.response.CommonProductWiseMenuResponse;
import com.maan.common.admin.response.CommonUserEditResponse;
import com.maan.common.admin.response.ExcludedDetailsResponse;
import com.maan.common.admin.response.IncludedDetailsResponse;
import com.maan.common.admin.response.IssuerDetailsResponse;
import com.maan.common.admin.response.IssuerInfoResponse;
import com.maan.common.admin.response.IssuerProductInfoResp;
import com.maan.common.admin.response.IssuerProductInfoResp1;
import com.maan.common.admin.response.ProductResponse;
import com.maan.common.admin.response.ProductWiseMenuListResponse;
import com.maan.common.admin.response.ProductWiseMenuResponse;
import com.maan.common.admin.response.UserCommisionEditResp;
import com.maan.common.admin.response.UserDetailsResponse;
import com.maan.common.admin.response.UserEditResponse;
import com.maan.common.admin.response.UserMagOcCertificateResponse;
import com.maan.common.admin.response.UserMgtDropDownResponse1;
import com.maan.common.admin.response.UserMgtInsertResponse;
import com.maan.common.admin.response.UserMgtInsertResponse1;
import com.maan.common.admin.response.UserProductEditResp;
import com.maan.common.admin.service.AdminService;
import com.maan.common.auth.token.EncryDecryService;
import com.maan.common.auth.token.passwordEnc;
import com.maan.common.bean.BranchMaster;
import com.maan.common.bean.BrokerBranchMaster;
import com.maan.common.bean.BrokerCompanyMaster;
import com.maan.common.bean.BrokerMaster;
import com.maan.common.bean.CountryMaster;
import com.maan.common.bean.ListItemValue;
import com.maan.common.bean.LoginExcuteDetails;
import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.LoginUserDetails;
import com.maan.common.bean.MenuMaster;
import com.maan.common.bean.PersonalInfo;
import com.maan.common.bean.ProductMaster;
import com.maan.common.error.ErrorInstance;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.BrokerBranchMasterRepository;
import com.maan.common.repository.BrokerCompanyMasterRepository;
import com.maan.common.repository.BrokerMasterRepository;
import com.maan.common.repository.CityMasterRepository;
import com.maan.common.repository.CountryMasterRepository;
import com.maan.common.repository.ListItemValueRepository;
import com.maan.common.repository.LoginExcuteDetailsRepository;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.repository.LoginRsaUserDetailsRepository;
import com.maan.common.repository.LoginUserDetailsRepository;
import com.maan.common.repository.MenuMasterRepository;
import com.maan.common.repository.PersonalInfoRepository;
import com.maan.common.repository.ProductMasterRepository;
import com.maan.common.bean.LoginRsaUserDetails;
import com.maan.common.bean.LoginRsaUserDetailsID;
@Service
public class AdminImplementation implements AdminService{
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	private Logger log=LogManager.getLogger(AdminImplementation.class);
	
	@Autowired
	private MenuMasterRepository menuRepo;
	@Autowired
	private LoginMasterRepository loginRepo;
	@Autowired
	private BrokerCompanyMasterRepository brokerCompanyRepo;
	@Autowired
	private LoginMasterRepository autheRepository;
	@Autowired
	private LoginUserDetailsRepository loginUserRep;
	@Autowired
	private EncryDecryService endecryService;
	@Autowired
	private BranchMasterRepository branchmasterrepository;
	@Autowired
	private ProductMasterRepository productmasterrepository;
	@Autowired
	private PersonalInfoRepository personalRepo;
	@Autowired
	private BrokerMasterRepository brokerRepo;
	@Autowired
	private LoginExcuteDetailsRepository loginexRepo;
	@Autowired
	private ListItemValueRepository listRepo;
	@Autowired
	private CountryMasterRepository countryRepo;
	@Autowired
	private PersonalInfoRepository personalRep;
	@Autowired
	private BrokerCompanyMasterRepository brokercompanymasterrepository;
	@Autowired
	private CityMasterRepository citymasterrepository; 
	@Autowired
	private MenuMasterRepository menumasterrepository;
	@Autowired
	private CompQuote compQuote;
	@Autowired
	private LoginRsaUserDetailsRepository loginrsauserrepo;
	@Autowired
	private BrokerBranchMasterRepository brokerbranchRepo;
	
	
	SimpleDateFormat sdfFormat = new SimpleDateFormat("dd/MM/yyyy");  
	@Override
	public AdminBrokerResponse getAdminBrokerList(AdminBrokerListReq req,HttpServletRequest http) {
		AdminBrokerResponse res = new AdminBrokerResponse();
		List<AdminBrokerDetailsResp> listres = new ArrayList<AdminBrokerDetailsResp>();
		int count = 0;
		try {
			List<BrokerCompanyMaster> brokers = brokerCompanyRepo.findByBranchCode(req.getBranchCode());
			List<String> brokerCodes = brokers.stream().map(BrokerCompanyMaster :: getAgencyCode  ).collect(Collectors.toList());
			List<LoginMaster> brokerloginDatas = loginRepo.findByOaCodeIn(brokerCodes );
			
			List<String> brokerLoginIds =brokerloginDatas.stream().map(LoginMaster :: getLoginId  ).collect(Collectors.toList());
			List<PersonalInfo> personalDatas = personalRepo.findByLoginIdInOrderByCustomerIdDesc(brokerLoginIds) ;
			List<String> agencycode = personalDatas.stream().map(PersonalInfo :: getAgencyCode  ).collect(Collectors.toList());
			List<BrokerCompanyMaster> list = brokerCompanyRepo.findByBranchCodeAndAgencyCodeInOrderByCompanyName(req.getBranchCode(),agencycode);
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					AdminBrokerDetailsResp bro = new AdminBrokerDetailsResp();
					bro.setCustomerId(s.getCustomerId()==null?"":s.getCustomerId().toString());
					bro.setContactPerson(s.getContactPerson()==null?"":s.getContactPerson().toString());
					bro.setCompanyName(s.getCompanyName()==null?"":s.getCompanyName().toString());
					bro.setAgencyCode(s.getAgencyCode()==null?"":s.getAgencyCode().toString());
					bro.setRsaBrokerCode(s.getRsabrokerCode()==null?"":s.getRsabrokerCode().toString());
					bro.setStatus(s.getStatus()==null?"":s.getStatus().toString());
					PersonalInfo per=personalRepo.findByAgencyCodeAndApplicationId(s.getAgencyCode(),req.getApplicationId());
					bro.setCreateDate(per.getEntryDate()==null?"":sdfFormat.format(per.getEntryDate()));
					bro.setLoginId(per.getLoginId()==null?"":per.getLoginId().toString());
					listres.add(bro);
				});
			}
			res.setAdminBrokerDetails(listres);
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		return res;
	}

	@Override
	public CommanAdminBrokerViewResp getBrokerInformation(BrokerInfoRequest req, HttpServletRequest http) {
		CommanAdminBrokerViewResp comRes = new CommanAdminBrokerViewResp();
		AdminBrokerViewResponse res = new AdminBrokerViewResponse();
		List<BrokerDetailsResponse> brolist = new ArrayList<BrokerDetailsResponse>();
		List<BroCommissionDetailsResp> brocomlist = new ArrayList<BroCommissionDetailsResp>();
		List<BranchDetailsResp> bralist = new ArrayList<BranchDetailsResp>();
		int count = 0;
		try {
			List<String>applicationId=new ArrayList<>();
			applicationId.add("2");
			applicationId.add("3");
			List<PersonalInfo> list=personalRepo.findByAgencyCodeAndApplicationIdIn(req.getAgencyCode(),applicationId);
			if(!CollectionUtils.isEmpty(list)) {
				PersonalInfo per=list.get(0);
				List<BrokerCompanyMaster> bcomp= brokerCompanyRepo.findByAgencyCode(req.getAgencyCode());
				if(!CollectionUtils.isEmpty(bcomp)) {
					List<LoginMaster>login=loginRepo.findByoaCodeAndBranchCode(req.getAgencyCode(),req.getBranchCode());
					if(!CollectionUtils.isEmpty(login)) {
						BrokerDetailsResponse brores = new BrokerDetailsResponse();
						//brores.setRegionCode(per.getRe==null?"":s.get("REGION_CODE").toString());
						//brores.setBranchCode(s.get("BRANCH_CODE")==null?"":s.get("BRANCH_CODE").toString());
						brores.setTitle(per.getTitle()==null?"":per.getTitle().toString());
						brores.setGender(per.getGender()==null?"":per.getGender().toString());
						brores.setFirstName(per.getFirstName()==null?"":per.getFirstName().toString());
						brores.setLastName(per.getLastName()==null?"":per.getLastName().toString());
						brores.setNationality(per.getNationality()==null?"":per.getNationality().toString());
						brores.setNationalityName("");
						brores.setDateOfBirth(per.getDob()==null?"":sdfFormat.format(per.getDob()));
						brores.setTelephoneNo(per.getTelephone()==null?"":per.getTelephone().toString());
						brores.setMobileNo(per.getMobile()==null?"":per.getMobile().toString());
						brores.setFax(per.getFax()==null?"":per.getFax().toString());
						brores.setEmail(per.getEmail()==null?"":per.getEmail().toString());
						brores.setAddress1(per.getAddress1()==null?"":per.getAddress1().toString());
						brores.setAddress2(per.getAddress2()==null?"":per.getAddress2().toString());
						//brores.setAddress3(per.getAddress3()==null?"":per.getAddress3().toString());
						brores.setOccupation(per.getOccupation()==null?"":per.getOccupation().toString());
						brores.setCityName(per.getCity()==null?"":per.getCity().toString());
						brores.setCountry(per.getCountry()==null?"":per.getCountry().toString());
						brores.setCountryName(per.getCountry()==null?"":per.getCountry().toString());
						brores.setPobox(per.getPobox()==null?"":per.getPobox().toString());
						brores.setCompanyName(bcomp.get(0).getCompanyName()==null?"":bcomp.get(0).getCompanyName().toString());
						brores.setAgencyCode(bcomp.get(0).getAgencyCode()==null?"":bcomp.get(0).getAgencyCode().toString());
						brores.setEntryDate(per.getEntryDate()==null?"":sdfFormat.format(per.getEntryDate()));
						brores.setStatus(login.get(0).getStatus()==null?"":login.get(0).getStatus().toString());
						brores.setCity(per.getCity()==null?"":per.getCity().toString());
						//brores.setMissippiId(login.get(0).getMi==null?"":s.get("MISSIPPI_ID").toString());
						//brores.setApprovedPreparedBy(s.get("APPROVED_PREPARED_BY")==null?"":s.get("APPROVED_PREPARED_BY").toString());
						//brores.setRsaBrokerCode(s.get("RSA_BROKER_CODE")==null?"":s.get("RSA_BROKER_CODE").toString());
						//brores.setAcExecutiveId(s.get("AC_EXECUTIVE_ID")==null?"":s.get("AC_EXECUTIVE_ID").toString());
						//brores.setCustomerName(s.get("CUST_NAME")==null?"":s.get("CUST_NAME").toString());
						//brores.setCustomerArNo(s.get("CUST_AR_NO")==null?"":s.get("CUST_AR_NO").toString());
						//brores.setLoginId(s.get("LOGIN_ID")==null?"":s.get("LOGIN_ID").toString());
						//brores.setCustomerId(s.get("CUSTOMER_ID")==null?"":s.get("CUSTOMER_ID").toString());
						//brores.setIssuerCommissionOpenCover(s.get("ISSUER_COMMISSION_OPENCOVER")==null?"":s.get("ISSUER_COMMISSION_OPENCOVER").toString());
						//brores.setIssuerCommissionOneOff(s.get("ISSUER_COMMISSION_ONEOFF")==null?"":s.get("ISSUER_COMMISSION_ONEOFF").toString());
						//brores.setImagePath(s.get("IMAGE_PATH")==null?"":s.get("IMAGE_PATH").toString());
						//brores.setSubBranch(s.get("SUB_BRANCH")==null?"":s.get("SUB_BRANCH").toString());
						brolist.add(brores);
					}
				}
			}
			List<LoginMaster>login= loginRepo.findByAgencyCode(req.getAgencyCode());
			if(!CollectionUtils.isEmpty(login)) {
				List<LoginUserDetails> loginuser = loginUserRep.findByAgencyCode(req.getAgencyCode());
				if(!CollectionUtils.isEmpty(login)) {
					loginuser.forEach(s -> {
						BroCommissionDetailsResp comres = new BroCommissionDetailsResp();
						comres.setProductId(s.getProductId()==null?"":s.getProductId().toString());
						ProductMaster pro=productmasterrepository.findByProductId(new BigDecimal(s.getProductId().toString()));
						comres.setProductName(pro.getProductName()==null?"":pro.getProductName().toString());
						comres.setCommission(s.getCommission()==null?"":s.getCommission().toString());
						comres.setInsuranceEndLimit(s.getInsuranceEndLimit()==null?"":s.getInsuranceEndLimit().toString());
						comres.setSpecialDiscount(s.getSpecialDiscount()==null?"":s.getSpecialDiscount().toString());
						//comres.setReferalStatus(s.get("REFERAL")==null?"":s.get("REFERAL").toString());
						comres.setStatus(s.getStatus()==null?"":s.getStatus().toString());
						comres.setDiscountPremium(s.getDiscountOfPremium()==null?"0":s.getDiscountOfPremium().toString());
						comres.setMinPremiumAmount(s.getMinPremiumAmount()==null?"0":s.getMinPremiumAmount().toString());
						comres.setBackDateAllowed(s.getBackDateAllowed()==null?"0":s.getBackDateAllowed().toString());
						comres.setProductCommission(s.getProCommission()==null?"0":s.getProCommission().toString());
						comres.setProductStartDate(s.getProStartDate()==null?"":s.getProStartDate().toString());
						comres.setProductExpiryDate(s.getProExpiryDate()==null?"":s.getProExpiryDate().toString());
						comres.setLoadingPremium(s.getLoadingOfPremium()==null?"0":s.getLoadingOfPremium().toString());
						comres.setPayReceiptStatus(s.getPayReceiptStatus()==null?"N":s.getPayReceiptStatus().toString());
						comres.setReceiptStatus(s.getReceiptStatus()==null?"N":s.getReceiptStatus().toString());
						comres.setFreightDebitOption(s.getFreightDebitOption()==null?"Y":s.getFreightDebitOption().toString());
						comres.setProvisionforPremium(s.getProvisionForPremium()==null?"N":s.getProvisionForPremium().toString());
						//comres.setRemarks(s.get("REMARKS")==null?"":s.get("REMARKS").toString());
						comres.setPolicyType(s.getPolicytypeId()==null?"":s.getPolicytypeId().toString());
						//comres.setCheckerYn(s.get("CHECKER_YN")==null?"":s.get("CHECKER_YN").toString());
						brocomlist.add(comres);
					});
				}
			}
			
			List<BranchMaster> branch = branchmasterrepository.findByBranchCodeAndStatusOrderByAmendIdDesc(req.getBranchCode(), "Y");
			if(!CollectionUtils.isEmpty(branch)) {
				branch.forEach(s -> {
					BranchDetailsResp bra = new BranchDetailsResp();
					bra.setBranchCode(s.getBranchCode()==null?"":s.getBranchCode().toString()); 
					bra.setBranchName(s.getBranchName()==null?"":s.getBranchName().toString()); 
					bralist.add(bra);
				});
			}
			res.setBrokerDetails(brolist);
			res.setCommissionDetails(brocomlist);  
			res.setBranchDetails(bralist);  
			comRes.setBrokerDetails(res);  
			count = 1;
			
			comRes.setMessage("Success");
			comRes.setIsError(false);
			comRes.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			comRes.setMessage("No Message");
			comRes.setIsError(true);
			comRes.setErroCode(1);
		}

		return comRes;
	}

	@Override
	public CommonBrokerEditResp getBrokerInformationEdit(BrokerInfoRequest req, HttpServletRequest http) {
		CommonBrokerEditResp comRes = new CommonBrokerEditResp();
		BrokerEditResponse res = new BrokerEditResponse();
		List<BrokerDetailsResponse> brolist = new ArrayList<BrokerDetailsResponse>();
		List<BroCommissionDetailsResp> brocomlist = new ArrayList<BroCommissionDetailsResp>();
		List<BrokerTaxDetailsResp> taxlist = new ArrayList<BrokerTaxDetailsResp>();
		int count = 0;
		try {
			List<String>applicationId=new ArrayList<>();
			applicationId.add("2");
			applicationId.add("3");
			List<PersonalInfo> list=personalRepo.findByAgencyCodeAndApplicationIdIn(req.getAgencyCode(),applicationId);
			if(!CollectionUtils.isEmpty(list)) {
				PersonalInfo per=list.get(0);
				List<BrokerCompanyMaster> bcomp= brokerCompanyRepo.findByAgencyCode(req.getAgencyCode());
				if(!CollectionUtils.isEmpty(bcomp)) {
					List<LoginMaster>login=loginRepo.findByoaCodeAndBranchCode(req.getAgencyCode(),req.getBranchCode());
					if(!CollectionUtils.isEmpty(login)) {
						BrokerDetailsResponse brores = new BrokerDetailsResponse();
						//brores.setRegionCode(per.getRe==null?"":s.get("REGION_CODE").toString());
						//brores.setBranchCode(s.get("BRANCH_CODE")==null?"":s.get("BRANCH_CODE").toString());
						brores.setTitle(per.getTitle()==null?"":per.getTitle().toString());
						brores.setGender(per.getGender()==null?"":per.getGender().toString());
						brores.setFirstName(per.getFirstName()==null?"":per.getFirstName().toString());
						brores.setLastName(per.getLastName()==null?"":per.getLastName().toString());
						brores.setNationality(per.getNationality()==null?"":per.getNationality().toString());
						brores.setNationalityName("");
						brores.setDateOfBirth(per.getDob()==null?"":sdfFormat.format(per.getDob()));
						brores.setTelephoneNo(per.getTelephone()==null?"":per.getTelephone().toString());
						brores.setMobileNo(per.getMobile()==null?"":per.getMobile().toString());
						brores.setFax(per.getFax()==null?"":per.getFax().toString());
						brores.setEmail(per.getEmail()==null?"":per.getEmail().toString());
						brores.setAddress1(per.getAddress1()==null?"":per.getAddress1().toString());
						brores.setAddress2(per.getAddress2()==null?"":per.getAddress2().toString());
						//brores.setAddress3(per.getAddress3()==null?"":per.getAddress3().toString());
						brores.setOccupation(per.getOccupation()==null?"":per.getOccupation().toString());
						brores.setCityName(per.getCity()==null?"":per.getCity().toString());
						brores.setCountry(per.getCountry()==null?"":per.getCountry().toString());
						brores.setCountryName(per.getCountry()==null?"":per.getCountry().toString());
						brores.setPobox(per.getPobox()==null?"":per.getPobox().toString());
						brores.setCompanyName(bcomp.get(0).getCompanyName()==null?"":bcomp.get(0).getCompanyName().toString());
						brores.setAgencyCode(bcomp.get(0).getAgencyCode()==null?"":bcomp.get(0).getAgencyCode().toString());
						brores.setEntryDate(per.getEntryDate()==null?"":sdfFormat.format(per.getEntryDate()));
						brores.setStatus(login.get(0).getStatus()==null?"":login.get(0).getStatus().toString());
						brores.setCity(per.getCity()==null?"":per.getCity().toString());
						//brores.setMissippiId(login.get(0).getMi==null?"":s.get("MISSIPPI_ID").toString());
						//brores.setApprovedPreparedBy(s.get("APPROVED_PREPARED_BY")==null?"":s.get("APPROVED_PREPARED_BY").toString());
						//brores.setRsaBrokerCode(s.get("RSA_BROKER_CODE")==null?"":s.get("RSA_BROKER_CODE").toString());
						//brores.setAcExecutiveId(s.get("AC_EXECUTIVE_ID")==null?"":s.get("AC_EXECUTIVE_ID").toString());
						//brores.setCustomerName(s.get("CUST_NAME")==null?"":s.get("CUST_NAME").toString());
						//brores.setCustomerArNo(s.get("CUST_AR_NO")==null?"":s.get("CUST_AR_NO").toString());
						//brores.setLoginId(s.get("LOGIN_ID")==null?"":s.get("LOGIN_ID").toString());
						//brores.setCustomerId(s.get("CUSTOMER_ID")==null?"":s.get("CUSTOMER_ID").toString());
						//brores.setIssuerCommissionOpenCover(s.get("ISSUER_COMMISSION_OPENCOVER")==null?"":s.get("ISSUER_COMMISSION_OPENCOVER").toString());
						//brores.setIssuerCommissionOneOff(s.get("ISSUER_COMMISSION_ONEOFF")==null?"":s.get("ISSUER_COMMISSION_ONEOFF").toString());
						//brores.setImagePath(s.get("IMAGE_PATH")==null?"":s.get("IMAGE_PATH").toString());
						//brores.setSubBranch(s.get("SUB_BRANCH")==null?"":s.get("SUB_BRANCH").toString());
						brolist.add(brores);
					}
				}
			}
			List<LoginMaster>login= loginRepo.findByAgencyCode(req.getAgencyCode());
			if(!CollectionUtils.isEmpty(login)) {
				List<LoginUserDetails> loginuser = loginUserRep.findByAgencyCode(req.getAgencyCode());
				if(!CollectionUtils.isEmpty(login)) {
					loginuser.forEach(s -> {
						BroCommissionDetailsResp comres = new BroCommissionDetailsResp();
						comres.setProductId(s.getProductId()==null?"":s.getProductId().toString());
						ProductMaster pro=productmasterrepository.findByProductId(new BigDecimal(s.getProductId().toString()));
						comres.setProductName(pro.getProductName()==null?"":pro.getProductName().toString());
						comres.setCommission(s.getCommission()==null?"":s.getCommission().toString());
						comres.setInsuranceEndLimit(s.getInsuranceEndLimit()==null?"":s.getInsuranceEndLimit().toString());
						comres.setSpecialDiscount(s.getSpecialDiscount()==null?"":s.getSpecialDiscount().toString());
						//comres.setReferalStatus(s.get("REFERAL")==null?"":s.get("REFERAL").toString());
						comres.setStatus(s.getStatus()==null?"":s.getStatus().toString());
						comres.setDiscountPremium(s.getDiscountOfPremium()==null?"0":s.getDiscountOfPremium().toString());
						comres.setMinPremiumAmount(s.getMinPremiumAmount()==null?"0":s.getMinPremiumAmount().toString());
						comres.setBackDateAllowed(s.getBackDateAllowed()==null?"0":s.getBackDateAllowed().toString());
						comres.setProductCommission(s.getProCommission()==null?"0":s.getProCommission().toString());
						comres.setProductStartDate(s.getProStartDate()==null?"":s.getProStartDate().toString());
						comres.setProductExpiryDate(s.getProExpiryDate()==null?"":s.getProExpiryDate().toString());
						comres.setLoadingPremium(s.getLoadingOfPremium()==null?"0":s.getLoadingOfPremium().toString());
						comres.setPayReceiptStatus(s.getPayReceiptStatus()==null?"N":s.getPayReceiptStatus().toString());
						comres.setReceiptStatus(s.getReceiptStatus()==null?"N":s.getReceiptStatus().toString());
						comres.setFreightDebitOption(s.getFreightDebitOption()==null?"Y":s.getFreightDebitOption().toString());
						comres.setProvisionforPremium(s.getProvisionForPremium()==null?"N":s.getProvisionForPremium().toString());
						//comres.setRemarks(s.get("REMARKS")==null?"":s.get("REMARKS").toString());
						comres.setPolicyType(s.getPolicytypeId()==null?"":s.getPolicytypeId().toString());
						//comres.setCheckerYn(s.get("CHECKER_YN")==null?"":s.get("CHECKER_YN").toString());
						brocomlist.add(comres);
					});
				}
			}
			List<BrokerMaster>brokertax=brokerRepo.findByAgencyCodeAndBranchCode(req.getAgencyCode(),req.getBranchCode());//need to add amend id
			
			if(!CollectionUtils.isEmpty(brokertax)) {
				brokertax.forEach(s -> {
					BrokerTaxDetailsResp tax = new BrokerTaxDetailsResp();
					tax.setPolicyFees(s.getPolicyFee()==null?"":s.getPolicyFee().toString());
					tax.setPolicyFeeStatus(s.getPolicyFeeStatus()==null?"":s.getPolicyFeeStatus().toString());
					//tax.setGovtTaxStatus(s.get("GOV_TAXSTATUS")==null?"":s.get("GOV_TAXSTATUS").toString());
					//tax.setGovtTax(s.get("GOVT_TAX")==null?"":s.get("GOVT_TAX").toString());
					//tax.setEmergencyFund(s.get("EMER_FUND")==null?"":s.get("EMER_FUND").toString());
					//tax.setEmergencyFundStatus(s.get("EMERGENCY_FUNDSTATUS")==null?"":s.get("EMERGENCY_FUNDSTATUS").toString());
					//tax.setEffectiveDate(s.get("EFFEC_DATE")==null?"":s.get("EFFEC_DATE").toString());
					//tax.setTaxApplicable(s.get("TAX_APPLICABLE")==null?"":s.get("TAX_APPLICABLE").toString());
					taxlist.add(tax);
				});
			}
			res.setBrokerDetails(brolist);
			res.setCommissionDetails(brocomlist);  
			res.setTaxDetails(taxlist);
			comRes.setBrokerDetails(res);  
			count = 1;
			
			comRes.setMessage("Success");
			comRes.setIsError(false);
			comRes.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			comRes.setMessage("No Message");
			comRes.setIsError(true);
			comRes.setErroCode(1);
		}
		
		return comRes;
	}

	@Override
	public AdminBrokerInsertResponse InsertNewBrokerDetails(AdminNewBrokerReq req, HttpServletRequest http,List<String> validation) {
		AdminBrokerInsertResponse res = new AdminBrokerInsertResponse();
		int count = 0;
		try {
			if(validation==null){
				if(StringUtils.isBlank(req.getAgencyCode()) && StringUtils.isBlank(req.getCustomerId())) {
				String customerid = brokerCompanyRepo.getCustomerSeqGenerated();
				String brokerCode = brokerCompanyRepo.getBrokerSeqGenerated();
				req.setAgencyCode(brokerCode);req.setCustomerId(customerid);  
				}
				int pocount = 1;
				int personalcount = compQuote.insertBrokerPersonalInfo(req,req.getCustomerId(),req.getAgencyCode());
				int brcompcount = compQuote.insertBrokerCompanyMaster(req,req.getCustomerId(),req.getAgencyCode());
				int brmascount =  compQuote.insertBrokerMaster(req,req.getAgencyCode());
				int logincount =  compQuote.insertNewLonginMaster(req,req.getAgencyCode());
				log.info("Policy Count ==> "+pocount+"   PersonalInfo Count ==> "+personalcount+"   Broker Company Count ==>"+brcompcount+"   Broker Master Count ==>"+brmascount+"   Login Count ==>"+logincount);
				if(pocount == 1 && personalcount == 1 && brcompcount == 1 && brmascount == 1 && logincount == 1) {
				res.setAgencyCode(req.getAgencyCode());
				res.setCustomerId(req.getCustomerId());  
				count = 1;
				}
			}else if(validation != null && validation.size()>0 ){
				List<Error> errorList = validationResponse(validation);
				res.setErrors(errorList);
				
			}
		}catch (Exception e) {
			log.info(e);
			
		}
		
		return res;
	}

	

	

	@Override
	public AdminIssuerResponse getAdminIssuerList(TitleDropDownRequest req, HttpServletRequest http) {
		AdminIssuerResponse res = new AdminIssuerResponse();
		List<IssuerDetailsResponse> isslist = new ArrayList<IssuerDetailsResponse>();
		int count = 0;
		try {
			List<LoginMaster>list= autheRepository.findByBranchCodeAndUsertype(req.getBranchCode(),"RSAIssuer");
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					IssuerDetailsResponse iss = new IssuerDetailsResponse();
					iss.setCompanyName(s.getUsername()==null?"":s.getUsername().toString());
					iss.setUserType(s.getUsertype()==null?"":s.getUsertype().toString());
					iss.setLoginId(s.getLoginId()==null?"":s.getLoginId().toString());
					iss.setCreateDate(s.getEntryDate()==null?"":sdfFormat.format(s.getEntryDate()));
					iss.setStatus(s.getStatus()==null?"":s.getStatus().toString());
					isslist.add(iss);
				});
			}
			res.setIssuerDetails(isslist);
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}

	@Override
	public AdminIssuerInfoResponse getIssuerInformation(IssuerInfoRequest req, HttpServletRequest http) {
		AdminIssuerInfoResponse res = new AdminIssuerInfoResponse();
		List<IssuerInfoResponse> infolist = new ArrayList<IssuerInfoResponse>();
		int count = 0;
		try {
			List<LoginMaster>list= autheRepository.findByBranchCodeAndUsertypeAndLoginId(req.getBranchCode(),"RSAIssuer",req.getLoginId());
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					IssuerInfoResponse info = new IssuerInfoResponse();
					info.setSubBranch(s.getSubBranch()==null?"":s.getSubBranch().toString());
					info.setUserName(s.getUsername()==null?"":s.getUsername().toString());
					info.setUserMail(s.getUserMail()==null?"":s.getUserMail().toString());
					info.setCoreLoginId(s.getCoreLoginId()==null?"":s.getCoreLoginId().toString());
					info.setLoginId(s.getLoginId()==null?"":s.getLoginId().toString());
					info.setProductId(s.getProductId()==null?"":s.getProductId().toString());
					info.setStatus(s.getStatus()==null?"":s.getStatus().toString());
					info.setAttachedBranch(s.getAttachedBranch()==null?"":s.getAttachedBranch().toString());
					info.setInceptionDate(s.getInceptionDate()==null?"":s.getInceptionDate().toString());
					info.setRegionCode(s.getRegionCode()==null?"":s.getRegionCode().toString());
					info.setBranchCode(s.getBranchCode()==null?"":s.getBranchCode().toString());
					infolist.add(info);
				});
			}
			res.setIssuerDetails(infolist);
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		 
		return res;
	}

	@Override
	public BrokerMagDropDownResponse1 getBrokerMangDropDown() {
		
		BrokerMagDropDownResponse1 res1 = new BrokerMagDropDownResponse1();
		
		List<BrokerMagDropDownResponse> reslist = new ArrayList<BrokerMagDropDownResponse>();
		try {
			List<LoginExcuteDetails>list=loginexRepo.findByStatusOrderByLoginExcuteidAmendIdDesc("Y");
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					BrokerMagDropDownResponse res = new BrokerMagDropDownResponse();
					res.setAcExecutiveId(s.getLoginExcuteid().getAcexecutiveid()==null?"":s.getLoginExcuteid().getAcexecutiveid().toString());
					res.setAcExecutiveName(s.getAcexecutiveName()==null?"":s.getAcexecutiveName().toString());
					reslist.add(res);
				});
			}
			res1.setResult(reslist);
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}
		return res1;
	}

	@Override
	public AdminIssuerInsertResponse InsertNewIssuerDetails(AdminNewIssuerReq req, HttpServletRequest http,List<String> validation) {
		int count = 0;
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		try {
			if(validation==null){
			int logcount = insertNewIssuerLonginMaster(req); 
			int logusercount = insertNewIssuerLonginUserDetails(req);
			log.info("Login Master Count ==> "+logcount+"   Login User Detail Count ==> "+logusercount);
			if(logcount == 1 && logusercount == 1 ) {
				res.setStatus(true);
				count = 1;
				res.setMessage("Success");
				res.setIsError(false);
				res.setErroCode(0);
			}
			}else if(validation != null && validation.size()>0 ){
				List<Error> errorList = validationResponse(validation);
				res.setErrors(errorList);
				res.setMessage("No Message");
				res.setIsError(true);
				res.setErroCode(1);
			}
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}
	
	public int insertNewIssuerLonginMaster(AdminNewIssuerReq req) {
		int count = 0;
		try {
			LoginMaster login = new LoginMaster();
			List<LoginMaster> loglist = autheRepository.findByLoginId(req.getLoginId());
			if (!CollectionUtils.isEmpty(loglist)) {
				login = loglist.get(0);
			} else if (!"edit".equalsIgnoreCase(req.getOptionMode())) {
				login.setLoginId(req.getLoginId());
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				login.setPassword(epass);
				/*String pass = bCryptPasswordEncoder.encode(req.getPassword());
				login.setTokenpassword(pass);*/
				login.setStatus("Y");
				login.setAccesstype("BOTH");
				login.setAppId("16");
				login.setPwdCount("0");
			}
			login.setStatus(StringUtils.isBlank(req.getStatus())?"Y":req.getStatus());
			login.setUsertype("RSAIssuer");
			login.setUsername(req.getIssuerName());
			login.setUserid(new BigDecimal(1));
			login.setAgencyCode(req.getLoginId() + 1);
			login.setOaCode(req.getLoginId());
			login.setCompanyId(new BigDecimal(1));
			login.setCreatedBy(req.getLoginUserType());
			Instant now = Instant.now();
			Instant after = now.plus(Duration.ofDays(45));
			Date dateAfter = Date.from(after);
			login.setPassdate(dateAfter);
			login.setBranchCode(req.getBranchCode());
			login.setRegionCode(req.getRegionCode());
			login.setCoreLoginId(req.getCoreLoginId());
			login.setUserMail(req.getEmailId());
			login.setEntryDate(new Date());
			if (StringUtils.isNotBlank(req.getEffectiveDate())) {
				Date effdate = sdfFormat.parse(req.getEffectiveDate());
				login.setInceptionDate(effdate);
			}
			login.setSubBranch(req.getBrokerLinkLocation());
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
			autheRepository.save(login);
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}
	public int insertNewIssuerLonginUserDetails(AdminNewIssuerReq req) {
		int count = 0;
		try {
			for (int i = 0; i < req.getProductInfo().size(); i++) {
				LoginUserDetails user = new LoginUserDetails();
				
				List<LoginUserDetails> userlist = loginUserRep.findByloginIdAndProductId(req.getLoginId(),new BigDecimal(req.getProductInfo().get(i).getProductId()));
				if (!CollectionUtils.isEmpty(userlist)) {
					user = userlist.get(0);
				} else if (!"edit".equalsIgnoreCase(req.getOptionMode())) {
					List<LoginUserDetails> luserlist = loginUserRep.findAllByOrderByAmendIdDesc();
					Long userid = luserlist.get(0).getUserId()==null?0l:Long.valueOf(luserlist.get(0).getUserId().toString())+1;
					user.setUserId(new BigDecimal(userid));
					user.setStatus("Y");
					user.setCompanyId(new BigDecimal(1));
				}
				user.setUserName(req.getIssuerName());
				user.setAgencyCode(req.getLoginId() + 1);
				user.setOaCode(req.getLoginId());
				//Long amendid = queryem.getMaxAmendIdLoginUserDetls(req.getLoginId());
				user.setAmendId(new BigDecimal(0));
				user.setEntryDate(new Date());
				if (StringUtils.isNotBlank(req.getEffectiveDate())) {
					Date effdate = sdfFormat.parse(req.getEffectiveDate());
					user.setEffectiveDate(effdate);
				}
				loginUserRep.save(user);
			}
			count = 1;
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}
	@Override
	public IssuerProductInfoResp1 getIssuerProductDetails(BrokerInfoRequest req) {
		
		IssuerProductInfoResp1 res1 = new IssuerProductInfoResp1();
		List<IssuerProductInfoResp> res = new ArrayList<IssuerProductInfoResp>();
		List<ProductMaster>list = null;
		try {
			List<BranchMaster>branch=branchmasterrepository.findByBranchCodeAndStatusOrderByAmendIdDesc(req.getBranchCode(),"Y");
			if(StringUtils.isNotBlank(req.getAgencyCode())) {
				List<LoginUserDetails>loginuser=loginUserRep.findByAgencyCode(req.getAgencyCode());
				List<BigDecimal> product = loginuser.stream().map(LoginUserDetails :: getProductId  ).collect(Collectors.toList());
				list=productmasterrepository.findByStatusAndProductIdNotInAndBranchCode("Y",product,branch.get(0).getBelongingBranch());
			}else {
				list=productmasterrepository.findByStatusAndBranchCode("Y",branch.get(0).getBelongingBranch());
			}
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					IssuerProductInfoResp prod = new IssuerProductInfoResp();
					prod.setProductId(s.getProductId()==null?"":s.getProductId().toString());
					prod.setProductName(s.getProductName()==null?"":s.getProductName().toString());
					prod.setCompanyId(s.getInsCompanyId()==null?"":s.getInsCompanyId().toString());
					res.add(prod);
				});
			}
			res1.setResult(res);
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}
		return res1;
	}
	
	@Override
	public BranchCodeResponse1 getIssuerBranchDetails(IssuerBranchRequest req) {
		
		BranchCodeResponse1 res = new BranchCodeResponse1();
		List<BranchCodeResponse> combranch = new ArrayList<BranchCodeResponse>();
		try {
			List<BranchMaster> branch = branchmasterrepository.findByRegionCodeAndStatus(req.getRegionCode(),"Y");
			if(!CollectionUtils.isEmpty(branch)) {
				for(int i=0;i<branch.size();i++) {
					BranchCodeResponse res1 = new BranchCodeResponse();
					res1.setBranchCode(branch.get(i).getBranchCode());  
					res1.setBranchName(branch.get(i).getBranchName()); 
					combranch.add(res1);
				}
			}
			res.setResult(combranch);
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		return res;
	}

	@SuppressWarnings("static-access")
	@Override
	public ChangePasswordResponse getIssuerChangePassword(IssuerChangePassReq req,HttpServletRequest http,List<String> validation) {
		ChangePasswordResponse res = new ChangePasswordResponse();
		int count = 0;
		try {
			if(validation==null){
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				String pass = endecryService.encrypt(req.getPassword());
				List<LoginMaster>login=autheRepository.findByLoginId(req.getAgencyCode());
				if(login!=null) {
					LoginMaster logdet=login.get(0);
					logdet.setPassword(pass);
					logdet.setEncryptPassword(epass);
					logdet.setStatus("Y");
					logdet.setPwdCount("0");
					autheRepository.save(logdet);
					count=1;
				}
				res.setMessage("Success");
				res.setIsError(false);
				res.setErroCode(0);
			}else if(validation != null && validation.size()>0 ){
				List<Error> errorList = validationResponse(validation);
				res.setErrors(errorList);
				res.setMessage("No Message");
				res.setIsError(true);
				res.setErroCode(1);
			}
			if(count == 1) {
				res.setMessages("Successfully Changed Password !!!");
			}else {
				res.setMessages("Password Updated Failure !!!");
			}
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}

	@SuppressWarnings("static-access")
	@Override
	public ChangePasswordResponse getBrokerChangePassword(IssuerChangePassReq req,HttpServletRequest http,List<String> validation) {
		ChangePasswordResponse res = new ChangePasswordResponse();
		int count = 0;
		try {
			if(validation==null){
				passwordEnc passEnc = new passwordEnc();
				String epass = passEnc.crypt(req.getPassword().trim());
				String pass = endecryService.encrypt(req.getPassword());
				List<LoginMaster>login=autheRepository.findByLoginId(req.getAgencyCode());
				if(login!=null) {
					LoginMaster logdet=login.get(0);
					logdet.setPassword(pass);
					logdet.setEncryptPassword(epass);
					logdet.setStatus("Y");
					logdet.setPwdCount("0");
					autheRepository.save(logdet);
					count=1;
				}
				res.setMessage("Success");
				res.setIsError(false);
				res.setErroCode(0);
			}else if(validation != null && validation.size()>0 ){
				List<Error> errorList = validationResponse(validation);
				res.setErrors(errorList);
				res.setMessage("No Message");
				res.setIsError(true);
				res.setErroCode(1);
			}
			if(count == 1) {
				res.setMessages("Successfully Changed Password !!!");
			}else {
				res.setMessages("Password Updated Failure !!!");
			}
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		return res;
	}
	
	private List<Error> validationResponse(List<String> validate) {
		List<Error> errorsList = new ArrayList<Error>();
		try {
			String validationDetails = "";
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
			log.info(e);
		}
		return errorsList;
	}
	@Override
	public CommonIncludedBrokerResp getIssuerIncludedBroke(IssuerIncludedRequest req, HttpServletRequest http) {
		CommonIncludedBrokerResp res = new CommonIncludedBrokerResp();
		List<IncludedDetailsResponse> inclist = new ArrayList<IncludedDetailsResponse>();
		int count = 0;
		try {
			List<BrokerCompanyMaster> list = brokerCompanyRepo.findByBranchCode(req.getBranchCode());
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					IncludedDetailsResponse inc = new IncludedDetailsResponse();
					inc.setAgencyCode(s.getAgencyCode()==null?"":s.getAgencyCode().toString());
					inc.setCompanyName(s.getCompanyName()==null?"":s.getCompanyName().toString());
					inc.setContactPerson(s.getContactPerson()==null?"":s.getContactPerson().toString());
					inc.setAddress1(s.getAddress1()==null?"":s.getAddress1().toString());
					inc.setAddress2(s.getAddress2()==null?"":s.getAddress2().toString());
					inc.setAddress3(s.getAddress3()==null?"":s.getAddress3().toString());
					inc.setCity(s.getCity()==null?"":s.getCity().toString());
					inc.setCountry(s.getCountry()==null?"":s.getCountry().toString());
					inc.setPhoneNo(s.getPhone()==null?"":s.getPhone().toString());
					inc.setFax(s.getFax()==null?"":s.getFax().toString());
					inc.setPoBox(s.getPobox()==null?"":s.getPobox().toString());
					inc.setCityName(s.getCity()==null?"":s.getCity().toString());
					inc.setRemarks(s.getRemarks()==null?"":s.getRemarks().toString());
					inc.setStatus(s.getStatus()==null?"":s.getStatus().toString());
					inc.setCustomerId(s.getCustomerId()==null?"":s.getCustomerId().toString());
					inc.setBranchCode(s.getBranchCode()==null?"":s.getBranchCode().toString());
					//inc.setMissippiId(s.get("MISSIPPI_ID")==null?"":s.get("MISSIPPI_ID").toString());
					//inc.setApprovedPreparedBy(s.get("APPROVED_PREPARED_BY")==null?"":s.get("APPROVED_PREPARED_BY").toString());
					inc.setRsaBrokeCode(s.getRsabrokerCode()==null?"":s.getRsabrokerCode().toString());
					inc.setAcExecutiveId(s.getAcExecutiveId()==null?"":s.getAcExecutiveId().toString());
					
					List<BranchMaster>branch=branchmasterrepository.findByBranchCodeAndStatusOrderByAmendId(req.getBranchCode(),"Y");
					if(!CollectionUtils.isEmpty(branch)) {
						inc.setBranchName(branch.get(0).getBranchName()==null?"":branch.get(0).getBranchName().toString());
					}
					List<LoginRsaUserDetails>rsauser=loginrsauserrepo.findByLoginRsaUserDetailsidLoginidAndLoginRsaUserDetailsidBrokercode(req.getLoginId(), s.getAgencyCode());
					List<String> product = new ArrayList<>();
					rsauser.forEach(k -> {
						product.add(k.getLoginRsaUserDetailsid().getProductid()==null?"":k.getLoginRsaUserDetailsid().getProductid().toString());
					});
					
					List<ProductMaster>productinfo=productmasterrepository.findByStatusAndProductIdNotIn("Y",product);
					List<String> productdet = new ArrayList<>();
					productinfo.forEach(i -> {
						productdet.add(i.getProductId()==null?"":i.getProductId().toString());
					});
					inc.setProductId(product);
					inclist.add(inc);
				});
			}
			res.setIncludedDetails(inclist);
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}
	
	
	@Override
	public CommonExcludedBrokerResp getIssuerExcludedBroke(IssuerIncludedRequest req, HttpServletRequest http) {
		CommonExcludedBrokerResp res = new CommonExcludedBrokerResp();
		List<ExcludedDetailsResponse> exclist = new ArrayList<ExcludedDetailsResponse>();
		int count = 0;
		try {
			List<LoginRsaUserDetails>list=loginrsauserrepo.findByLoginRsaUserDetailsidLoginid(req.getLoginId());
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					ExcludedDetailsResponse exc = new ExcludedDetailsResponse();
					exc.setAgencyCode(s.getLoginRsaUserDetailsid().getBrokercode()==null?"":s.getLoginRsaUserDetailsid().getBrokercode().toString());
					List<BrokerCompanyMaster> bcomp= brokerCompanyRepo.findByAgencyCode(exc.getAgencyCode());
					if(!CollectionUtils.isEmpty(bcomp)) {
						exc.setCompanyName(bcomp.get(0).getCompanyName()==null?"":bcomp.get(0).getCompanyName().toString());
						exc.setBranchCode(bcomp.get(0).getBranchCode()==null?"":bcomp.get(0).getBranchCode().toString());
					}
					List<BranchMaster>branch=branchmasterrepository.findByBranchCodeAndStatusOrderByAmendId(exc.getBranchCode(),"Y");
					if(!CollectionUtils.isEmpty(branch)) {
						exc.setBranchName(branch.get(0).getBranchName()==null?"":branch.get(0).getBranchName().toString());
					}
					
					exc.setProductId(s.getLoginRsaUserDetailsid().getProductid()==null?"":s.getLoginRsaUserDetailsid().getProductid().toString());
					exclist.add(exc);
				});
			}
			res.setExcludedDetails(exclist);  
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}

	@Override
	public AdminIssuerInsertResponse getIssuerIncludedInsert(IssuerIncludedSaveRequest req, HttpServletRequest http) {
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		int count = 0;
		try {
			if(!CollectionUtils.isEmpty(req.getBrokerInfo())) {
				for(int i=0;i<req.getBrokerInfo().size();i++) {
					LoginRsaUserDetails rsa = new LoginRsaUserDetails();
					LoginRsaUserDetailsID rsaid = new LoginRsaUserDetailsID();
					rsaid.setLoginid(req.getLoginId());
					rsaid.setBrokercode(StringUtils.isBlank(req.getBrokerInfo().get(i).getBrokerCode())?"":req.getBrokerInfo().get(i).getBrokerCode());   
					rsaid.setBranchcode(req.getBranchCode());  
					//Long amendid = queryem.getMaxAmendIdLoginRsaUser(req.getLoginId());
					rsaid.setAmendid(0L);  
					rsa.setCommission(0.0);
					rsa.setStatus("Y");
					rsa.setEntrydate(new Date());
					rsa.setStartdate(new Date());
					Instant now = Instant.now(); 
					Instant after= now.plus(Duration.ofDays(365));
					Date dateAfter = Date.from(after);
					rsa.setEnddate(dateAfter);
					for(int j=0;j<req.getBrokerInfo().get(i).getProductInfo().size();j++) {
						rsaid.setProductid(req.getBrokerInfo().get(i).getProductInfo().get(j).getProductId()==null?0L:Long.valueOf(req.getBrokerInfo().get(i).getProductInfo().get(j).getProductId().trim()));  
						rsa.setLoginRsaUserDetailsid(rsaid);  
						loginrsauserrepo.save(rsa);
					}
				}
				res.setStatus(true);
				count = 1;
			}
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}

	@Override
	public AdminIssuerInsertResponse getIssuerExcludedInsert(IssuerExcludedDeleteRequest req, HttpServletRequest http) {
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		int count = 0;
		try {
			if(!CollectionUtils.isEmpty(req.getBrokerInfo())) {
				for(int i=0;i<req.getBrokerInfo().size();i++) {
					count = loginrsauserrepo.deleteByLoginRsaUserDetailsidLoginidAndLoginRsaUserDetailsidBrokercode(req.getLoginId(),req.getBrokerInfo().get(i).getBrokerCode()); 
					log.info("delete the issuer included is ==>"+i+"  "+count);
				}
				if(count == 1) {
				res.setStatus(true);
				}
			}
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}

	@Override
	public AdminUserResponse1 getAdminUserTypeList() {
		
		AdminUserResponse1 res1 = new AdminUserResponse1();
		List<AdminUserResponse> res = new ArrayList<AdminUserResponse>();
		try {
			List<ListItemValue>list=listRepo.findByItemTypeAndStatusOrderByItemValue("USER_TYPE", "Y");
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					AdminUserResponse user = new AdminUserResponse();
					user.setCode(s.getItemValue()==null?"":s.getItemValue().toString());
					user.setCodeDescription(s.getItemDesc()==null?"":s.getItemDesc().toString());
					res.add(user);
				});
			}
			res1.setResult(res);
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}
		return res1;
	}
	
	@Override
	public AdminMenuResponse1 getAdminMenuTypeList(TitleDropDownRequest req) {
		
		AdminMenuResponse1 res1 = new AdminMenuResponse1();
		/*List<AdminMenuResponse> res = new ArrayList<AdminMenuResponse>();
		try {
			List<Map<String,Object>> list = queryem.getAdminMenuTypeList(req); 
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					AdminMenuResponse menu = new AdminMenuResponse();
					menu.setCategoryDetailId(s.get("CATEGORY_DETAIL_ID")==null?"":s.get("CATEGORY_DETAIL_ID").toString());
					menu.setCategoryDetailName(s.get("DETAIL_NAME")==null?"":s.get("DETAIL_NAME").toString());
					menu.setRemarks(s.get("REMARKS")==null?"":s.get("REMARKS").toString());
					menu.setStatus(s.get("STATUS")==null?"":s.get("STATUS").toString());
					menu.setStatus1(s.get("STATUS1")==null?"":s.get("STATUS1").toString());
					menu.setParentMenu(s.get("PARENT_MENU")==null?"":s.get("PARENT_MENU").toString());
					menu.setParentMenuName(s.get("PARENT_MENU_NAME")==null?"":s.get("PARENT_MENU_NAME").toString());
					res.add(menu);
				});
			}	
			res1.setResult(res);
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}*/
		return res1;
	}

	@Override
	public AdminUserResponse1 getUnderWriterGradeList() { 
		
		AdminUserResponse1 res1 = new AdminUserResponse1();
		/*List<AdminUserResponse> res = new ArrayList<AdminUserResponse>();
		try {
			
			List<Map<String,Object>> list = queryem.getUnderWriterGradeList(); 
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					AdminUserResponse user = new AdminUserResponse();
					user.setCode(s.get("CODE")==null?"":s.get("CODE").toString());
					user.setCodeDescription(s.get("CODEDESC")==null?"":s.get("CODEDESC").toString());
					res.add(user);
				});
				res1.setResult(res);
			}
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}*/
		return res1;
	}

	@Override
	public AdminTableListResponse getAdminList(TitleDropDownRequest req,HttpServletRequest http) {
		AdminTableListResponse res = new AdminTableListResponse();
		List<AdminDetailsResponse> admlist = new ArrayList<AdminDetailsResponse>();
		int count = 0;
		try {
			
			List<BigDecimal> userid = new ArrayList<BigDecimal>();
			userid.add(new BigDecimal(2));
			userid.add(new BigDecimal(4));
			
			List<LoginMaster> list = autheRepository.findByUseridInAndBranchCode(userid, req.getBranchCode());
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					AdminDetailsResponse adm = new AdminDetailsResponse();
					adm.setLoginId(s.getLoginId()==null?"":s.getLoginId());
					adm.setUserName(s.getUsername()==null?"":s.getUsername());
					adm.setUserId(s.getUserid()==null?"":s.getUserid().toString());
					adm.setUserType(s.getUsertype()==null?"":s.getUsertype());
					adm.setBranchCode(s.getBranchCode()==null?"":s.getBranchCode());
					
					List<BranchMaster> branch = branchmasterrepository.findByBranchCodeAndStatusOrderByAmendIdDesc(s.getBranchCode(),"Y");
					
					adm.setBranchName(branch.get(0).getBranchName()==null?"":branch.get(0).getBranchName());
					
					if(s.getStatus().equals("Y")) {
						adm.setStatus1("ACTIVE");
					}else if(s.getStatus().equals("N")) {
						adm.setStatus1("DEACTIVE");
					}else if(s.getStatus().equals("D")) {
						adm.setStatus1("DELETE");
					}else if(s.getStatus().equals("L")) {
						adm.setStatus1("LOCKED");
					}else if(s.getStatus().equals("T")) {
						adm.setStatus1("LOCKED");
					}
					
					adm.setStatus(s.getStatus()==null?"":s.getStatus());
					adm.setProductId(s.getProductId()==null?"":s.getProductId());
					adm.setMenuId(s.getMenuId()==null?"":s.getMenuId());
					adm.setBrokerCode(s.getBrokerCodes()==null?"":s.getBrokerCodes());
					adm.setUserMail(s.getUserMail()==null?"":s.getUserMail());
					adm.setAttachedBranch(s.getAttachedBranch()==null?"":s.getAttachedBranch());
					adm.setAttachedUnderWriter(s.getAttachedUw()==null?"":s.getAttachedUw());
					adm.setRegionCode(s.getRegionCode()==null?"":s.getRegionCode());
					admlist.add(adm);
				});
				res.setAdminDetails(admlist);  
			}
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}

	@Override
	public CommonProductWiseMenuResponse getProductsWiseMenuList(ProductsWiseMenuRequest req) {
		CommonProductWiseMenuResponse comRes = new CommonProductWiseMenuResponse();
		List<ProductWiseMenuListResponse> prores = new ArrayList<ProductWiseMenuListResponse>();
		List<ProductWiseMenuResponse> res = new ArrayList<ProductWiseMenuResponse>();
		try {
			if(!CollectionUtils.isEmpty(req.getProductInfo())) {
				String proid = "";
				for(int j=0;j<req.getProductInfo().size();j++) {
					proid += req.getProductInfo().get(j).getProductId();
					if(j <req.getProductInfo().size()-1) {
						proid += ",";
					}
				}
				if(!StringUtils.contains(proid, "11")){
					ProductRequest rdd = new ProductRequest();
					rdd.setProductId("11");
					req.getProductInfo().add(rdd);
				}
				for(int i=0;i<req.getProductInfo().size();i++) {
					ProductWiseMenuListResponse menu = new ProductWiseMenuListResponse();
					
					ProductMaster map = productmasterrepository.findByProductIdAndStatus(new BigDecimal(req.getProductInfo().get(i).getProductId()),"Y");
					menu.setProductId(map.getProductId()==null?"":map.getProductId().toString());
					menu.setProductName(map.getProductName()==null?"":map.getProductName());
					
					List<BranchMaster> branch = branchmasterrepository.findByBranchCodeAndStatusOrderByAmendIdDesc(req.getBranchCode(),"Y");
					
					List<MenuMaster> list = menumasterrepository.findByStatusAndBranchCodeAndProductIdAndUsertype("Y", branch.get(0).getBelongingBranch(), map.getProductId(), "admin");
					
					if(!CollectionUtils.isEmpty(list)) {
						list.forEach(s -> {
							ProductWiseMenuResponse prod = new ProductWiseMenuResponse();
							prod.setMenuId(s.getMenuId()==null?"":s.getMenuId().toString());
							prod.setMenuName(s.getMenuName()==null?"":s.getMenuName());
							res.add(prod);
						});
					}	
					menu.setMenuInfo(res); 
					prores.add(menu);
				}
			}
			comRes.setProductMenuResponse(prores);  
			comRes.setMessage("Success");
			comRes.setIsError(false);
			comRes.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			comRes.setMessage("No Message");
			comRes.setIsError(true);
			comRes.setErroCode(1);
		}
		return comRes;
	}

	@Override
	public AdminIssuerInsertResponse getAdminInsert(NewAdminInsertReq req, HttpServletRequest http,List<String> validation) {
		int logcount = 0;
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		try {
			if(validation==null){
			logcount = compQuote.insertNewAdminBlock(req);
				res.setMessage("Success");
				res.setIsError(false);
				res.setErroCode(0);
			}else if(validation != null && validation.size()>0 ){
				List<Error> errorList = validationResponse(validation);
				res.setErrors(errorList);
				res.setMessage("No Message");
				res.setIsError(true);
				res.setErroCode(1);
				
			}
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		return res;
	}

	@Override
	public AdminTableListResponse getAdminEditList(IssuerInfoRequest req, HttpServletRequest http) {
		AdminTableListResponse res = new AdminTableListResponse();
		List<AdminDetailsResponse> admlist = new ArrayList<AdminDetailsResponse>();
		int count = 0;
		try {
			
			List<BigDecimal> userid = new ArrayList<BigDecimal>();
			userid.add(new BigDecimal(2));
			userid.add(new BigDecimal(4));
			
			List<LoginMaster> list = autheRepository.findByUseridInAndBranchCodeAndLoginId(userid, req.getBranchCode(), req.getLoginId());
			
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					AdminDetailsResponse adm = new AdminDetailsResponse();
					adm.setLoginId(s.getLoginId()==null?"":s.getLoginId());
					adm.setUserName(s.getUsername()==null?"":s.getUsername());
					adm.setUserId(s.getUserid()==null?"":s.getUserid().toString());
					adm.setUserType(s.getUsertype()==null?"":s.getUsertype());
					adm.setBranchCode(s.getBranchCode()==null?"":s.getBranchCode());
					
					List<BranchMaster> branch = branchmasterrepository.findByBranchCodeAndStatusOrderByAmendIdDesc(s.getBranchCode(),"Y");
					
					adm.setBranchName(branch.get(0).getBranchName()==null?"":branch.get(0).getBranchName());
					
					if(s.getStatus().equals("Y")) {
						adm.setStatus1("ACTIVE");
					}else if(s.getStatus().equals("N")) {
						adm.setStatus1("DEACTIVE");
					}else if(s.getStatus().equals("D")) {
						adm.setStatus1("DELETE");
					}else if(s.getStatus().equals("L")) {
						adm.setStatus1("LOCKED");
					}else if(s.getStatus().equals("T")) {
						adm.setStatus1("LOCKED");
					}
					
					adm.setStatus(s.getStatus()==null?"":s.getStatus());
					adm.setProductId(s.getProductId()==null?"":s.getProductId());
					adm.setMenuId(s.getMenuId()==null?"":s.getMenuId());
					adm.setBrokerCode(s.getBrokerCodes()==null?"":s.getBrokerCodes());
					adm.setUserMail(s.getUserMail()==null?"":s.getUserMail());
					adm.setAttachedBranch(s.getAttachedBranch()==null?"":s.getAttachedBranch());
					adm.setAttachedUnderWriter(s.getAttachedUw()==null?"":s.getAttachedUw());
					adm.setRegionCode(s.getRegionCode()==null?"":s.getRegionCode());
					admlist.add(adm);
				});
				res.setAdminDetails(admlist);  
			}
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}

	@Override
	public UserMgtDropDownResponse1 getUserMgtDropDownList(TitleDropDownRequest req) {
		UserMgtDropDownResponse1 res1 = new UserMgtDropDownResponse1();
		/*UserMgtDropDownResponse res = new UserMgtDropDownResponse();
		List<UserCountryResponse> coulist = new ArrayList<UserCountryResponse>();
		List<UserCityResponse> citylist = new ArrayList<UserCityResponse>();
		List<UserNationalityResponse> natlist = new ArrayList<UserNationalityResponse>();
		List<UserTitleResponse> titlelist = new ArrayList<UserTitleResponse>();
		try {
			
			
			List<BranchMaster> branch = branchmasterrepository.findByBranchCodeAndStatusOrderByAmendIddDesc(req.getBranchCode(),"Y");
			
			//Country table not there 
			List<Map<String,Object>> country = null;//queryem.getUserMgtCountryList(req.getBranchCode());
			List<Map<String,Object>> nationality = null;//queryem.getUserMgtNationalityList(req.getBranchCode());
			List<Map<String,Object>> title = null; //queryem.getUserMgtTitleList(req.getBranchCode());		
			List<CityMaster> city = citymasterrepository.findByCountryIdAndStatus(branch.get(0).getOriginationCountryId(), "Y");
			//List<Map<String,Object>> city = queryem.getUserMgtCityList(req.getBranchCode());
			
			
			if(!CollectionUtils.isEmpty(country)) {
				country.forEach(s -> {
					UserCountryResponse cou = new UserCountryResponse();
					cou.setCountryCode(s.get("COUNTRY_ID")==null?"":s.get("COUNTRY_ID").toString());
					cou.setCountryName(s.get("COUNTRY_NAME")==null?"":s.get("COUNTRY_NAME").toString());
					coulist.add(cou);
				});
			}
			if(!CollectionUtils.isEmpty(city)) {
				city.forEach(s -> {
					UserCityResponse ci = new UserCityResponse();
					ci.setCityCode(s.getCityId()==null?"":s.getCityId().toString());
					ci.setCityName(s.getCityName()==null?"":s.getCityName().toString());
					citylist.add(ci);
				});
			}
			if(!CollectionUtils.isEmpty(nationality)) {
				nationality.forEach(s -> {
					UserNationalityResponse nat = new UserNationalityResponse();
					nat.setNationalityCode(s.get("COUNTRY_ID")==null?"":s.get("COUNTRY_ID").toString());
					nat.setNationalityName(s.get("NATIONALITY_NAME")==null?"":s.get("NATIONALITY_NAME").toString());
					natlist.add(nat);
				});
			}
			if(!CollectionUtils.isEmpty(title)) {
				title.forEach(s -> {
					UserTitleResponse tit = new UserTitleResponse();
					tit.setTitleId(s.get("TITLE_ID")==null?"":s.get("TITLE_ID").toString());
					tit.setTitleName(s.get("TITLE_NAME")==null?"":s.get("TITLE_NAME").toString());
					titlelist.add(tit);
				});
			}
			res.setCountryDetails(coulist);
			res.setCityDetails(citylist);
			res.setNationalityDetails(natlist);
			res.setTitleDetails(titlelist); 
			
			res1.setResult(res);
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}*/
		return res1;
	}

	@Override
	public AdminUserMgtListResponse getUserMgtTableList(TitleDropDownRequest req,HttpServletRequest http) {
		AdminUserMgtListResponse res = new AdminUserMgtListResponse();
		List<UserDetailsResponse> userlist = new ArrayList<UserDetailsResponse>();
		int count = 0;
		try {
			
			List<String> login = new ArrayList<String>();
			login.add("NONE");
			
			List<LoginMaster> list = autheRepository.findByUsertypeAndBranchCodeAndOaCodeNotAndLoginIdNotIn("User", req.getBranchCode(), "90011", login);
			
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					
					List<String> appid = new ArrayList<String>();
					appid.add("2");
					appid.add("2");
					List<PersonalInfo> personallist = personalRep.findByAgencyCodeAndApplicationIdInOrderByAmendIdDesc(s.getAgencyCode(),appid);
					PersonalInfo per = personallist.get(0);
					
					UserDetailsResponse user = new UserDetailsResponse();
					
					if(s.getStatus().equals("Y")) {
						user.setStatus("ACTIVE");
					}else if(s.getStatus().equals("N")) {
						user.setStatus("DEACTIVE");
					}else if(s.getStatus().equals("D")) {
						user.setStatus("DELETE");
					}else if(s.getStatus().equals("L")) {
						user.setStatus("LOCKED");
					}else if(s.getStatus().equals("T")) {
						user.setStatus("LOCKED");
					}
					
					
					user.setCompanyName(per.getCompanyName()==null?"":per.getCompanyName());
					user.setUserType(s.getUsertype()==null?"":s.getUsertype());
					user.setLoginId(s.getLoginId()==null?"":s.getLoginId());
					user.setAgencyCode(per.getAgencyCode());
					user.setApplicationId(per.getApplicationId()==null?"":per.getApplicationId());
					user.setCreateDate(dateFormat.format(per.getEntryDate()));
					
					List<BrokerCompanyMaster> bro = brokercompanymasterrepository.findByAgencyCode(s.getAgencyCode());
					user.setBrokerName(bro.get(0).getCompanyName());
					userlist.add(user);
				});
			}
			res.setUserDetails(userlist);  
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}
	
	@Override
	public CommonUserEditResponse getUserMgtEditTableList(UserCertificateRequest req,HttpServletRequest http) {
		CommonUserEditResponse res = new CommonUserEditResponse();
		List<UserEditResponse> editlist = new ArrayList<UserEditResponse>();
		int count = 0;
		try {
			
			List<String> str = new ArrayList<String>();
			str.add("2");
			str.add("3");
			
			List<PersonalInfo> list = personalRep.findByAgencyCodeAndApplicationIdIn(req.getAgencyCode(),str);
			
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
			
					List<LoginMaster> login = autheRepository.findByAgencyCode(req.getAgencyCode());
					
					UserEditResponse edit = new UserEditResponse();
					edit.setTitle(s.getTitle()==null?"":s.getTitle());
					edit.setGender(s.getGender()==null?"":s.getGender());
					edit.setUserId(login.get(0).getLoginId()==null?"":login.get(0).getLoginId());
					edit.setFirstName(s.getFirstName()==null?"":s.getFirstName());
					edit.setLastName(s.getLastName()==null?"":s.getLastName());
					edit.setNationality(s.getNationality()==null?"":s.getNationality());
					edit.setDateOfBirth(s.getDob()==null?"":dateFormat.format(s.getDob()));
					edit.setTelephoneNo(s.getTelephone()==null?"":s.getTelephone());
					edit.setMobileNo(s.getMobile()==null?"":s.getMobile());
					edit.setFax(s.getFax()==null?"":s.getFax());
					edit.setEmail(s.getEmail()==null?"":s.getEmail());
					edit.setAddress1(s.getAddress1()==null?"":s.getAddress1());
					edit.setAddress2(s.getAddress2()==null?"":s.getAddress2());
					edit.setCity(s.getEmirate()==null?"":s.getEmirate());
					edit.setCountry(s.getCountry()==null?"":s.getCountry());
					edit.setEntryDate(s.getEntryDate()==null?"":dateFormat.format(s.getEntryDate()));
					edit.setStatus(login.get(0).getStatus()==null?"":s.getStatus());
					edit.setLoginId(login.get(0).getLoginId()==null?"":login.get(0).getLoginId());
					edit.setUserType(login.get(0).getUsertype()==null?"":login.get(0).getUsertype());
					edit.setCustomerName(s.getCustName()==null?"":s.getCustName());
					edit.setCustomerArNo(s.getCustArNo()==null?"":s.getCustArNo());
					edit.setCustomerId(s.getCustomerId()==null?"":s.getCustomerId().toString());
					edit.setAgencyCode(login.get(0).getOaCode()==null?"":login.get(0).getOaCode());
					edit.setSubBranch(login.get(0).getSubBranch()==null?"":login.get(0).getSubBranch());
					edit.setUserAgencyCode(s.getAgencyCode()==null?"":s.getAgencyCode());
					edit.setOccupation(s.getOccupation()==null?"":s.getOccupation());
					
					//edit.setBranchCode(s.getBRANCH_CODE")==null?"":s.get("BRANCH_CODE").toString());
					//edit.setRegionCode(s.get("REGION_CODE")==null?"":s.get("REGION_CODE").toString());
					
					List<BrokerCompanyMaster> brokercom = brokercompanymasterrepository.findByAgencyCode(login.get(0).getAgencyCode());
					
					//edit.setNationalityName(s.getNationality());
					//edit.setCountryName(s.getCOUNTRY_NAME")==null?"":s.get("COUNTRY_NAME").toString());
					//edit.setPobox(login.get);
					//edit.setCityName(s.get("EMIRATE_NAME")==null?"":s.get("EMIRATE_NAME").toString());
					edit.setCompanyName(brokercom.get(0).getCompanyName()==null?"":brokercom.get(0).getCompanyName());
					edit.setBrokerOrganization(login.get(0).getUsername()==null?"":login.get(0).getUsername());
					
					editlist.add(edit);
				});
			}
			res.setUserDetailsResponse(editlist);
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		return res;
	}
	
	@Override
	public UserMgtInsertResponse1 UserMgtInsertOrUpdate(UserMgtInsertRequest req, HttpServletRequest http,List<String> validation){
		
		UserMgtInsertResponse1 res1 = new UserMgtInsertResponse1();
		UserMgtInsertResponse res = new UserMgtInsertResponse();
		int count = 0;
		try {
			if(validation==null){
			if(StringUtils.isBlank(req.getCustomerId()) && StringUtils.isBlank(req.getUserAgencyCode())) {
				String customerid = brokerCompanyRepo.getCustomerSeqGenerated();
				String uAgencyCode = brokerCompanyRepo.getUserSeqGenerated();
				req.setCustomerId(customerid);
				req.setUserAgencyCode(uAgencyCode);  
			}
			int personalcount = compQuote.insertUserMgtPersonalInfo(req);
			int logincount = compQuote.insertNewUserMgtLonginMaster(req);
			log.info("PersonalInfo Count ==> "+personalcount+"   Login Count ==>"+logincount);
			if(personalcount == 1 && logincount == 1) {
			res.setAgencyCode(req.getUserAgencyCode());
			res.setStatus(true); 
			res.setCustomerId(req.getCustomerId());  
			count = 1;
			}
			res1.setResult(res1);
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);

			}else if(validation != null && validation.size()>0 ){
				List<Error> errorList = validationResponse(validation);
				res1.setErrorMessage(errorList);
				res1.setMessage("No Message");
				res1.setIsError(true);
				res1.setErroCode(1);
				res1.setErrorMessage(errorList);
			}
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}
		
		return res1;
	}

	
	@Override
	public BranchDetailsResp1 getAdminReportBranchList(IssuerIncludedRequest req){
		
		BranchDetailsResp1 res1 = new BranchDetailsResp1();
		/*List<BranchDetailsResp> res = new ArrayList<BranchDetailsResp>();
		List<Map<String,Object>> list = null;
		try {
    		list =  queryem.getAdminQuoteRefBranchDetails(req.getLoginId());
    		if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					BranchDetailsResp bra = new BranchDetailsResp();
					bra.setBranchCode(s.get("BRANCH_CODE")==null?"":s.get("BRANCH_CODE").toString());
					bra.setBranchName(s.get("BRANCH_NAME")==null?"":s.get("BRANCH_NAME").toString());
					res.add(bra);
				});
    		}	
    		res1.setResult(res);
    		res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}*/
		return res1;
	}
	

	@Override
	public AdminMenuTypeResp1 getAdminBasedMenuList(AdminMenuTypeReq req) {
		
		AdminMenuTypeResp1 res1 = new AdminMenuTypeResp1();
		/*List<AdminMenuTypeResp> resp = new ArrayList<AdminMenuTypeResp>();
		try {
			List<BigDecimal>menuId=new ArrayList<>();
			for(int i= 0 ;i<req.getMenuInfo().size();i++) {
				menuId.add(new BigDecimal(req.getMenuInfo().get(i).getMenuId()));
			}
			if(CollectionUtils.isEmpty(menuId)) {
				menuId.add(new BigDecimal("9999"));
			}
			List<MenuMaster>list=menuRepo.findByProductIdAndBranchCodeAndMenuIdNotIn(req.getProductId(),req.getBranchCode(),menuId);
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					AdminMenuTypeResp res = new AdminMenuTypeResp();
					res.setMenuId(s.getMenuId()==null?"":s.getMenuId().toString());
					res.setMenuName(s.getMenuName()==null?"":s.getMenuName().toString());
					res.setMenuUrl(s.getMenuUrl()==null?"":s.getMenuUrl().toString());
					res.setParentMenu(s.getParentMenu()==null?"":s.getParentMenu().toString());
					resp.add(res);
				});
			}
			res1.setResult(resp);
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}*/
		return res1;
	}

	@Override
	public AdminBrokerInsertResponse InsertBrokerProductDetails(BrokerLoginProductInsReq req, HttpServletRequest http,List<String> validation) {
		int count = 0;
		AdminBrokerInsertResponse res = new AdminBrokerInsertResponse();
		try {
			if(validation==null){
			int logcount = insertBrokerProductLonginMaster(req); 
			int logusercount = insertBrokerProductLonginUserDetails(req);
			log.info("Login Master Count ==> "+logcount+"   Login User Detail Count ==> "+logusercount);
			if(logcount == 1 && logusercount == 1 ) {
				res.setAgencyCode(req.getAgencyCode());
				res.setCustomerId(req.getCustomerId()); 
				count = 1;
			}
			}else if(validation != null && validation.size()>0 ){
				List<Error> errorList = validationResponse(validation);
				res.setErrors(errorList);
			}
		}catch (Exception e) {
			log.info(e);
		}
		
		return res;
	}

	public int insertBrokerProductLonginMaster(BrokerLoginProductInsReq req) {
		int count = 0;
		try {
			List<LoginMaster> list = autheRepository.findByAgencyCode(req.getAgencyCode());
			if(!CollectionUtils.isEmpty(list)) {
				LoginMaster login=list.get(0);
				login.setReferal(req.getRemarks());
				autheRepository.save(login);
				count = 1;
			}
			
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}

	public int insertBrokerProductLonginUserDetails(BrokerLoginProductInsReq req) {
		int count = 0;
		try {  
			List<LoginMaster> list = autheRepository.findByAgencyCode(req.getAgencyCode());
			if(!CollectionUtils.isEmpty(list)) {
				LoginMaster login=list.get(0);
			LoginUserDetails user = new LoginUserDetails();
			user.setLoginId(login.getLoginId());
			user.setProductId(new BigDecimal(login.getProductId()));
			
			List<LoginUserDetails> ludlist = loginUserRep.findByloginIdAndProductId(login.getLoginId(),new BigDecimal(req.getProductId()));
			Long userid = 0L;
			if(!CollectionUtils.isEmpty(ludlist)) {
				userid = Long.valueOf(ludlist.get(0).getUserId().toString());
			}else {
				List<LoginUserDetails> luserlist = loginUserRep.findAllByOrderByAmendIdDesc();
				 userid = luserlist.get(0).getUserId()==null?0l:Long.valueOf(luserlist.get(0).getUserId().toString())+1;
			}
			user.setUserId(new BigDecimal(userid));
			user.setUserName(req.getCustomerName());
			user.setAgencyCode(req.getAgencyCode());
			user.setOaCode(req.getAgencyCode());
			user.setStatus("Y");
			user.setCompanyId(new BigDecimal(2L));
			user.setCommission(new BigDecimal(req.getCommission()));
			user.setInsuranceStartLimit(new BigDecimal(1000));
			user.setInsuranceEndLimit(new BigDecimal(req.getInsuranceEndLimit()));
			user.setDiscountOfPremium(new BigDecimal(req.getDiscountPremium()));
			user.setRelativeUserId(new BigDecimal(0));
			user.setCustomerId(StringUtils.isBlank(req.getCustomerId()) ? new BigDecimal(0) : new BigDecimal(req.getCustomerId()));
			user.setMinPremiumAmount(new BigDecimal(req.getMinPremiumAmount()));
			user.setBackDateAllowed(new BigDecimal(req.getBackDateAllowed()));
			user.setLoadingOfPremium(new BigDecimal(req.getLoadingPremium()));
			user.setProvisionForPremium(req.getProvision());
			user.setFreightDebitOption(req.getFreight());
			user.setPayReceiptStatus(req.getPayReceipt());
			user.setReceiptStatus(req.getPayReceipt());
			user.setSchemeId("30".equals(req.getProductId()) ? new BigDecimal(7) : new BigDecimal(0));
			//Long amendid = queryem.getMaxAmendIdLoginUserDetlsProduct(login.getLoginid().getLoginid(),req.getProductId());
			user.setAmendId(new BigDecimal(0));
			user.setEntryDate(new Date());
			user.setInceptionDate(new Date());
			loginUserRep.save(user);
			//}
			count = 1;
			}
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}
	@Override
	public CommanBrokerProductEditResp getEditBrokerProductDetails(BrokerProductEditReq req, HttpServletRequest http) {
		CommanBrokerProductEditResp comRes = new CommanBrokerProductEditResp();
		int count = 0;
		try {
			BrokerProductEditResp res = new BrokerProductEditResp();
			List<BrokerProductEditResp>resp=new ArrayList<>();
				List<LoginMaster> login = autheRepository.findByAgencyCode(req.getAgencyCode());
				List<LoginUserDetails> list = loginUserRep.findByloginIdAndProductId(login.get(0).getLoginId(),new BigDecimal(req.getProductId()));
				if(!CollectionUtils.isEmpty(list)) {
					LoginUserDetails user=list.get(0);
					res.setProductId(String.valueOf(user.getProductId()));
					//res.setRemarks(login.getReferal());
					res.setUserId(String.valueOf(user.getUserId()));
					res.setCustomerName(user.getUserName());
					res.setAgencyCode(user.getAgencyCode());
					res.setCommission(user.getCommission().toString());
					res.setInsuranceEndLimit(user.getInsuranceEndLimit().toString());
					res.setDiscountPremium(user.getDiscountOfPremium().toString());
					res.setCustomerId(String.valueOf(user.getCustomerId()));
					res.setMinPremiumAmount(user.getMinPremiumAmount().toString());
					res.setBackDateAllowed(user.getBackDateAllowed().toString());
					res.setLoadingPremium(user.getLoadingOfPremium().toString());
					res.setProvision(user.getProvisionForPremium());
					res.setFreight(user.getFreightDebitOption());
					res.setPayReceipt(user.getPayReceiptStatus());
					resp.add(res);
					comRes.setProductInformation(resp);
				}
				
			count = 1;
			comRes.setMessage("Success");
			comRes.setIsError(false);
			comRes.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
			comRes.setMessage("No Message");
			comRes.setIsError(true);
			comRes.setErroCode(1);
		}
		
		return comRes;
	}

	@Override
	public ProductResponse getDeleteBrokerProductDetails(BrokerProductEditReq req) {
		ProductResponse res = new ProductResponse();
		try {
			
			int count = loginUserRep.deleteByProductIdAndAgencyCode(new BigDecimal(req.getProductId()),req.getAgencyCode());
			if(count == 1) {
				res.setMessages("Successfully data is deleted !!!"); 
			}else{
				res.setMessages("Failure data is deleted !!!"); 
			}
			res.setMessage("Success");
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setErroCode(1);
		}
		return res;
	}

	@Override
	public UserMagOcCertificateResponse getUserMgtOCCertificate(UserCertificateRequest req,HttpServletRequest http) {/*
		UserMagOcCertificateResponse res = new UserMagOcCertificateResponse();
		/*List<UserCertificateResponse> certlist = new ArrayList<UserCertificateResponse>();
		int count = 0;
		try {
			List<Map<String,Object>> list = queryem.getUserMgtOCCertificate(req.getAgencyCode());
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach(s -> {
					UserCertificateResponse cert = new UserCertificateResponse();
					cert.setOpenCoverNo(s.get("OPEN_COVER_NO")==null?"":s.get("OPEN_COVER_NO").toString());
					cert.setProposalNo(s.get("PROPOSAL_NO")==null?"":s.get("PROPOSAL_NO").toString());
					cert.setMissippiOpenCoverNo(s.get("MISSIPPI_OPENCOVER_NO")==null?"":s.get("MISSIPPI_OPENCOVER_NO").toString());
					cert.setCustomerSchedule(s.get("CUSTOMER_SCHEDULE")==null?"":s.get("CUSTOMER_SCHEDULE").toString());
					cert.setCustomerDebit(s.get("CUSTOMER_DEBIT")==null?"":s.get("CUSTOMER_DEBIT").toString());
					cert.setCustCustomerDebit(s.get("CUSTOMER_CUSTOMERDEBIT")==null?"":s.get("CUSTOMER_CUSTOMERDEBIT").toString());
					cert.setCompanyName(s.get("NAME")==null?"":s.get("NAME").toString());
					certlist.add(cert);
				});
			}
			res.setUserCertificateResponse(certlist); 
			count = 1;
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		DefaultAllResponse deflt = compQuote.setDefaultValue(http,"getUserMgtOCCertificate");
		if(count == 1) {
			deflt.setStatusDescription("Successfully Data Getted"); 
		}else {
			deflt.setStatusDescription("Some Errors Occured"); 
		}
		res.setDefaultValue(deflt); */
		return null;
	}

	@Override
	public AdminIssuerInsertResponse InsertUserMgtProductDetails(UserMgtProductInsertRequest req, HttpServletRequest http,UserProductValidReq validation) {
		int count = 0;
		AdminIssuerInsertResponse res = new AdminIssuerInsertResponse();
		try {
			if(validation.getValidation()==null){
			int logusercount = compQuote.insertUserProductLonginUserDetails(req,validation);
			log.info("Login User Detail Count ==> "+logusercount);
			if(logusercount == 1 ) {
				res.setStatus(true);
				count = 1;
				}
			res.setMessage("Success");
			res.setIsError(false);
			res.setErroCode(0);
			
			}else if(validation.getValidation() != null && validation.getValidation().size()>0 ){
				List<Error> errorList = validationResponse(validation.getValidation());
				res.setErrors(errorList);
				
				res.setMessage("No Message");
				res.setIsError(true);
				res.setErroCode(1);
			}
			
		}catch (Exception e) {
			log.info(e);
		
			res.setMessage("No Message");
			res.setIsError(true);
			res.setErroCode(1);
		}
		
		return res;
	}
	
	@Override
	public CommanUserProductEditResp1 getUserProductEditDetails(UserProductEditRequest Userreq,HttpServletRequest http) {
		
		CommanUserProductEditResp1 res1 = new CommanUserProductEditResp1();
		CommanUserProductEditResp res = new CommanUserProductEditResp();
		List<ProductMaster> list = new ArrayList<ProductMaster>();
		List<UserProductEditResp> prolist = new ArrayList<UserProductEditResp>();
		List<UserCommisionEditResp> commision = new ArrayList<UserCommisionEditResp>();
		int count = 0;
		try {
			BrokerInfoRequest req = new BrokerInfoRequest();
			req.setBranchCode(Userreq.getBranchCode());
			req.setAgencyCode(Userreq.getUserAgencyCode());
			
			List<BranchMaster> belongingbranch = branchmasterrepository.findByBranchCodeAndStatusOrderByAmendIdDesc(req.getBranchCode(), "Y");
			
			List<BigDecimal> prod = new ArrayList<BigDecimal>();
			List<LoginUserDetails> login = loginUserRep.findByAgencyCode(req.getAgencyCode());
			for (LoginUserDetails loginUserDetails : login) {
				prod.add(loginUserDetails.getProductId());
			}
			
			list = productmasterrepository.findByProductIdNotInAndBranchCodeAndStatusOrderByProductId(prod, belongingbranch.get(0).getBelongingBranch(), "Y");
			//list = queryem.getProductDetailsinUnderWriter(req);
			
			if(CollectionUtils.isEmpty(list)) {
				
				list = productmasterrepository.findByBranchCodeAndStatusOrderByProductId(belongingbranch.get(0).getBelongingBranch(), "Y");
				//list = queryem.getProductDetailsinUnderWriterWithoutAgency(req);
			}
			
			if(!CollectionUtils.isEmpty(list)) {
				for(int i=0;i<list.size();i++) {
					UserProductEditResp pro = new UserProductEditResp();
					pro.setProductId(list.get(i).getProductId()==null?"":list.get(i).getProductId().toString());
					pro.setProductName(list.get(i).getProductName());
					pro.setCompanyId(list.get(i).getInsCompanyId()==null?"":list.get(i).getInsCompanyId().toString());
					prolist.add(pro);
				}
			}
			
			//Join Query Pending
			
			List<LoginUserDetails> comlist = loginUserRep.findByAgencyCodeAndOaCodeOrderByAmendIdDesc(Userreq.getUserAgencyCode(), req.getAgencyCode());
			//List<Map<String,Object>> comlist = queryem.getCommisionUserMgt(Userreq);
			if(!CollectionUtils.isEmpty(comlist)) {
				for(int j=0;j<comlist.size();j++) {
					
					List<ProductMaster> produ = productmasterrepository.findByProductIdAndBranchCodeAndStatusOrderByProductId(comlist.get(j).getProductId(), belongingbranch.get(0).getBelongingBranch(), "Y");
					
					UserCommisionEditResp commi = new UserCommisionEditResp();
					commi.setProductId(produ.get(0).getProductId()==null?"":produ.get(0).getProductId().toString());
					commi.setProductName(produ.get(0).getProductName()==null?"":produ.get(0).getProductName());
					commi.setProductYN(produ.get(0).getProductId()==null?"":produ.get(0).getProductId().toString());
					
					//commi.setSpecialDiscount(comlist.get(j).getSpecialdiscount()==null?"":comlist.get(j).getSpecialdiscount().toString());
					//commi.setFreight(comlist.get(j).getFreightdebitoption());
					//commi.setInsuranceEndLimit(comlist.get(j).getInsuranceendlimit()==null?"":comlist.get(j).getInsuranceendlimit().toString());
					//commi.setOpenCoverNo(comlist.get(j).getOpencoverno()==null?"":comlist.get(j).getOpencoverno().toString());
					//commi.setReceipt(comlist.get(j).getPayreceiptstatus());
					commision.add(commi);
				}
			}
			res.setProductResponse(prolist);
			res.setCommisionResponse(commision); 
			count = 1;
			res1.setMessage("Success");
			res1.setIsError(false);
			res1.setErroCode(0);
			
		}catch (Exception e) {
			log.info(e);
		
			res1.setMessage("No Message");
			res1.setIsError(true);
			res1.setErroCode(1);
		}
		return res1;
	}

	@Override
	public BranchDetailsResp1 getPortFolioRegionList(IssuerIncludedRequest req) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int insertUserProductLonginUserDetails(UserMgtProductInsertRequest req, UserProductValidReq valreq) {
		int count = 0;
		try {
			List<Map<String, Object>> map = valreq.getMapvalue();
			LoginUserDetails user = new LoginUserDetails();
			List<LoginMaster> login = autheRepository.findByAgencyCode(req.getUserAgencyCode());
			for (int i = 0; i < map.size(); i++) {
				if ("Y".equalsIgnoreCase(map.get(i).get("product").toString())) {
				
				List<LoginUserDetails> ludlist = loginUserRep.findByloginIdAndProductId(login.get(0).getLoginId(),new BigDecimal(login.get(0).getProductId()));
				BigDecimal userid = new BigDecimal(0);
				if(ludlist.size()!=0) {
					userid = ludlist.get(0).getUserId();
				}else {
					//userid = queryem.getMaxUserIdLoginUserDetls();
				}
				user.setUserId(userid);
				user.setUserName(req.getCustomerName());
				user.setAgencyCode(req.getUserAgencyCode());
				user.setOaCode(req.getAgencyCode());
				user.setStatus("Y");
				user.setCompanyId(new BigDecimal(1));
				user.setCommission(new BigDecimal(0.0));
				user.setInsuranceStartLimit(new BigDecimal(0));
				//user.setInsuranceEndLimit(map.get(i).getinsEndLimit") == null ? 0L: Long.valueOf(map.get(i).get("insEndLimit").toString()));
				//user.setDiscountofpremium(req.getDiscountPremium());
				user.setRelativeUserId(new BigDecimal(0));
				user.setCustomerId(StringUtils.isBlank(req.getCustomerId()) ? new BigDecimal(0) : new BigDecimal(req.getCustomerId()));
				// user.setMinpremiumamount(req.getMinPremiumAmount());
				// user.setBackdateallowed(req.getBackDateAllowed());
				// user.setLoadingofpremium(req.getLoadingPremium());
				// user.setProvisionforpremium(req.getProvision());
				user.setFreightDebitOption(map.get(i).get("freight") == null ? "N" : map.get(i).get("freight").toString());
				user.setPayReceiptStatus(map.get(i).get("receipt") == null ? "N" : map.get(i).get("receipt").toString());
				// user.setReceiptstatus(req.getPayReceipt());
				user.setSchemeId("30".equals(map.get(i).get("uproductId").toString())? new BigDecimal(7) : new BigDecimal(0)); 
				//Long amendid = queryem.getMaxAmendIdLoginUserDetlsProduct(login.getLoginid().getLoginid(),String.valueOf(user.getLoginUserId().getProductid()));
				//userId.setAmendid(0L);
				//user.setLoginUserId(userId);
				user.setOpenCoverNo(map.get(i).get("open_cover_no") == null ? "" : map.get(i).get("open_cover_no").toString());
				user.setSpecialDiscount(map.get(i).get("specialDis") == null ? new BigDecimal(0.0):new BigDecimal(map.get(i).get("specialDis").toString()));
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

	@Override
	public AdminUserResponse1 getCountryList(String branchCode) {
		AdminUserResponse1 res1=new AdminUserResponse1();
		List<AdminUserResponse> res = new ArrayList<AdminUserResponse>();
		try {
		List<BranchMaster>branch= branchmasterrepository.findByBranchCode(branchCode);
		if(!CollectionUtils.isEmpty(branch)) {
			String origincountry=branch.get(0).getOriginationCountryId()==null?"":branch.get(0).getOriginationCountryId().toString();
			List<CountryMaster>country1=countryRepo.findByCountrypkCountryidAndEffectivedateLessThanEqualOrderByEffectivedateDesc(Long.parseLong(origincountry),new Date());
			List<CountryMaster> country =  country1.stream().filter(distinctByKey(o -> Arrays.asList(o.getCountrypk().getCountryid() ))).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(country)) {
				country.forEach(s -> {
					AdminUserResponse user = new AdminUserResponse();
					user.setCode(s.getCountrypk().getCountryid()==null?"":s.getCountrypk().getCountryid().toString());
					user.setCodeDescription(s.getCountryname()==null?"":s.getCountryname().toString());
					res.add(user);
				});
			}
		}
		res1.setResult(res);
		res1.setMessage("Success");
		res1.setIsError(false);
		res1.setErroCode(0);
		
	}catch (Exception e) {
		log.info(e);
	
		res1.setMessage("No Message");
		res1.setIsError(true);
		res1.setErroCode(1);
	}
	return res1;
	}

	@Override
	public AdminUserResponse1 getNationalList(String branchCode) {
		AdminUserResponse1 res1=new AdminUserResponse1();
		List<AdminUserResponse> res = new ArrayList<AdminUserResponse>();
		try {
		
			List<CountryMaster>country=countryRepo.findByNationalitynameIsNotNullAndEffectivedateLessThanEqualOrderByEffectivedateDesc(new Date());
			List<CountryMaster> filterBybranchCode =  country.stream().filter(distinctByKey(o -> Arrays.asList(o.getCountrypk().getCountryid() ))).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(filterBybranchCode)) {
				filterBybranchCode.forEach(s -> {
					AdminUserResponse user = new AdminUserResponse();
					user.setCode(s.getCountrypk().getCountryid()==null?"":s.getCountrypk().getCountryid().toString());
					user.setCodeDescription(s.getNationalityname()==null?"":s.getNationalityname().toString());
					res.add(user);
				});
			}
		res1.setResult(res);
		res1.setMessage("Success");
		res1.setIsError(false);
		res1.setErroCode(0);
		
	}catch (Exception e) {
		log.info(e);
	
		res1.setMessage("No Message");
		res1.setIsError(true);
		res1.setErroCode(1);
	}
	return res1;
	}
	//Fiter Details By Key
	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
	    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@Override
	public AdminUserResponse1 getBrokerBranchList(BranchListRequest req) {
		AdminUserResponse1 res1=new AdminUserResponse1();
		List<AdminUserResponse> res = new ArrayList<AdminUserResponse>();
		try {
			List<String> branchId = req.getAttachedBranchInfo().stream().map(AttachedBranchReq :: getAttachedBranchId  ).collect(Collectors.toList());
			List<BrokerBranchMaster>brokerbranch1=brokerbranchRepo.findByMgenBranchIdInAndStatusAndEffectiveDateLessThanEqualOrderByEffectiveDateDesc(branchId,"Y",new Date());
			List<BrokerBranchMaster> brokerbranch =  brokerbranch1.stream().filter(distinctByKey(o -> Arrays.asList(o.getBranchId() ))).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(brokerbranch)) {
				brokerbranch.forEach(s -> {
					AdminUserResponse user = new AdminUserResponse();
					user.setCode(s.getBranchId()==null?"":s.getBranchId().toString());
					user.setCodeDescription(s.getBranchName()==null?"":s.getBranchName().toString());
					res.add(user);
				});
			}
		res1.setResult(res);
		res1.setMessage("Success");
		res1.setIsError(false);
		res1.setErroCode(0);
		
	}catch (Exception e) {
		log.info(e);
	
		res1.setMessage("No Message");
		res1.setIsError(true);
		res1.setErroCode(1);
	}
	return res1;
	}
}
