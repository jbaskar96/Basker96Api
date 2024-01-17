package com.maan.common.menu.service.impl;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;
import com.maan.common.bean.BranchMaster;
import com.maan.common.bean.BrokerCompanyMaster;
import com.maan.common.bean.HomePositionMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.MModule;
import com.maan.common.bean.OfsData;
import com.maan.common.bean.OfsDataDetails;
import com.maan.common.bean.PersonalInfo;
import com.maan.common.bean.PolicyLocation;
import com.maan.common.bean.ProductMaster;
import com.maan.common.domain.OfsDataDetailsDto;
import com.maan.common.domain.OfsDataDto;
import com.maan.common.domain.PolicyLocationDto;
import com.maan.common.menu.req.ExistingRequest;
import com.maan.common.menu.req.SearchRequest;
import com.maan.common.menu.req.lapsedQuoteReq;
import com.maan.common.menu.res.CommonResponse;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.BrokerCompanyMasterRepository;
import com.maan.common.repository.HomePositionMasterRepository;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.repository.MModuleRepository;
import com.maan.common.repository.OfsDataDetailsRepository;
import com.maan.common.repository.OfsDataRepository;
import com.maan.common.repository.PersonalInfoRepository;
import com.maan.common.repository.PolicyLocationRepository;
import com.maan.common.repository.ProductMasterRepository;

@Service
public class NonMotorGridServiceImpl {

	@Autowired
	private BranchMasterRepository branchRepo;
	
	@Autowired
	private OfsDataRepository ofsDataRepo;
	
	@Autowired
	private LoginMasterRepository loginRepo;
	
	@Autowired
	private BrokerCompanyMasterRepository brokerCompanyRepo;
	
	@Autowired
	private MModuleRepository mmodelRepo;
	
	@Autowired
	private ProductMasterRepository productRepo;
	
	@Autowired
	private HomePositionMasterRepository homeRepo;
	
	@Autowired
	private PersonalInfoRepository personalRepo;
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private OfsDataDetailsRepository ofsDataDetailRepo;

	@Autowired
	private PolicyLocationRepository locationRepo; 
	
	private Logger log =LogManager.getLogger(NonMotorGridServiceImpl.class);
	
	SimpleDateFormat sdfFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	
	
	//***************** Get Existing Quote Details *****************//
	public List<CommonResponse> getExistingQuoteDetails(ExistingRequest req) {
		List<CommonResponse> datas = new ArrayList<CommonResponse>();
		try {
			String status = req.getStatus();
			// Get Common Details
			Map<String, String> commonDetails = getCommonDetails(req);

			Page<OfsData> entityList = null;
		
			if (StringUtils.isNotBlank(status)) {
				// Rejected Or Existing Quote
				Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("quoteNo").descending());
				entityList = ofsDataRepo.findByLoginIdAndApplicationIdAndBranchcodeAndStatusAndSchemeId(paging, req.getLoginid(), req.getApplicationid(),
								req.getBranchcode(), status, new BigDecimal(req.getSchemeid()));

			} else if (req.getToday() != null && StringUtils.isNotBlank(req.getToday().toString())) {
				// Expired Quote
				status = "Y";
				Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("effectiveDate").descending());
				entityList = ofsDataRepo.findByLoginIdAndApplicationIdAndBranchcodeAndStatusAndSchemeIdAndEffectiveDateBefore(paging,req.getLoginid(), req.getApplicationid(), 
								req.getBranchcode(), status,new BigDecimal(req.getSchemeid()), req.getToday());
			}
			for (OfsData data : entityList.getContent()) {
				CommonResponse res = new CommonResponse();

				res.setAaa_cardno(null);
				res.setAmend_id(data.getAmendId().toString());
				res.setApplication_no(data.getApplicationNo().toString());
				res.setApplicationid(data.getApplicationId());
				res.setBranchname(commonDetails.get("BranchName"));
				res.setBroker_company_name(commonDetails.get("BrokerCompanyName"));
				res.setBroker_id(commonDetails.get("BrokerCodes"));
				res.setBroker_name(commonDetails.get("BrokerName"));
				res.setContent_type_id(data.getContentTypeId() == null ? "" : data.getContentTypeId().toString());
				res.setCountryid(data.getCountry());
				res.setCountryname(null);
				res.setCustomer_id(data.getCustomerId() == null ? "" : data.getCustomerId().toString());
				res.setCustomer_name(data.getFirstName() + " " + data.getLastName());
				res.setEmail(data.getEmail());
				res.setInsured_name(data.getFirstName() + data.getLastName());
				res.setInception_date(data.getInceptionDate() == null ? "" : sdfFormat.format(data.getInceptionDate()));
				res.setLast_name(data.getLastName());
				res.setLogin_id(req.getLoginid());
				res.setMobileno(data.getTelephone());
				res.setPolicy_start_date(data.getInceptionDate() == null ? "" : sdfFormat.format(data.getInceptionDate()));
				res.setPolicyholderid(data.getCustomerId() == null ? "" : data.getCustomerId().toString());
				//res.setPolicytype(data.getCustomerType());
				res.setQuote_no(data.getQuoteNo().toString());
				res.setQuotation_date(data.getEntryDate() == null ? "" : sdfFormat.format(data.getEntryDate()));
				res.setValidity_date(data.getExpiryDate() == null ? "" : sdfFormat.format(data.getExpiryDate()));
				res.setExpiry_date(data.getExpiryDate() == null ? "" : sdfFormat.format(data.getExpiryDate()));
				res.setPremium(data.getActualPremium() == null ? "" : data.getActualPremium().toString());
				res.setProduct_name(commonDetails.get("ModuleName"));
				res.setPremia_customer_name(data.getFirstName() + " " + data.getLastName());
				res.setProduct_name(commonDetails.get("ProductName"));
				res.setQuote_no(data.getQuoteNo().toString());
				res.setReferralRemarks(data.getReferralRemarks());
				res.setRemarks(data.getRemarks());
				res.setStatus(data.getStatus());
				res.setSchemeid(req.getSchemeid());
				res.setSchemeName(commonDetails.get("SchemeName"));
				datas.add(res);
			}
			return datas;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	//***************** Get Common Details *****************//
	public Map<String, String> getCommonDetails(ExistingRequest req) {
		Map<String, String> list = new HashMap<String, String>();
		try {
			
			List<LoginMaster> loginData = loginRepo.findByLoginId(req.getLoginid()); 
			String brokercode = loginData.get(0).getBrokerCodes();
			list.put("BrokerCodes", loginData.get(0).getBrokerCodes());

			List<BrokerCompanyMaster> broCompany = brokerCompanyRepo.findByAgencyCode(brokercode); 
			if (broCompany != null && broCompany.size() > 0) {		
				list.put("BrokerCompanyName", broCompany.get(0).getCompanyName());
				list.put("BrokerName", broCompany.get(0).getContactPerson());
			}
			
			ProductMaster data = productRepo.findByProductId(new BigDecimal(req.getProductid()));
			if (data != null) {	
				list.put("ProductName", data.getProductName());
			}
			
			MModule mdata = mmodelRepo.findByMmModuleid(new BigDecimal(req.getSchemeid()));
			if (mdata != null) {	
				list.put("SchemeName", mdata.getMmModulename());
			}
			
			if (StringUtils.isNotBlank(req.getBranchcode())) { 
				BranchMaster bmData = branchRepo.findByBranchCode(req.getBranchcode()).get(0);
				if (bmData != null) {		
					list.put("BranchCode", bmData.getBranchCode().toString());
					list.put("BranchName", bmData.getBranchName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return list;
		}
		return list;
	}
	
	//***************** Get Customer Approval Detail *****************//
	public List<CommonResponse> getCustomerApprovalDetails(ExistingRequest req){
		List<CommonResponse> resList = new ArrayList<CommonResponse>();
		try {
			// Get Common Details
			Map<String, String> commonDetails = getCommonDetails(req);
			
			Calendar cal = new GregorianCalendar(); 
			cal.add(Calendar.DAY_OF_MONTH, -30);
			Date before30 = cal.getTime();
			
			//Custmer Approval Details
			Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("entryDate").descending());
			String remarksNot = "Referal";
			String custAppStatus = req.getCustapprovestatus();
			Page<HomePositionMaster> homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndCustApproveStatusAndEntryDateAfterAndRemarksOrRemarksNot(paging,
					"Y", req.getLoginid(), req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode(), new BigDecimal(req.getSchemeid()) ,custAppStatus , before30 ,null ,remarksNot );
			
			//Get Home Position Mastrer & PErsonal Info Details
			 resList = getPolicyDetails( req , homeDatas.getContent() ,  commonDetails);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return resList;
		}
		return resList; 
	}
	
	//***************** Get Referal Data *****************//
	public List<CommonResponse> getReferalData(ExistingRequest req){
		List<CommonResponse> resList = new ArrayList<CommonResponse>();
		try {
			// Get Common Details
			Map<String, String> commonDetails = getCommonDetails(req);
			
			String status ="" ;
			String remarks = "";
			Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("entryDate").descending());
			Page<HomePositionMaster> homeDatas = null ;
			List<HomePositionMaster>	homeDataFilter = new ArrayList<HomePositionMaster>();
			
			 if(req.getReferalstatus().equalsIgnoreCase("Y") ) {
				// Referal Data
			 	 status = "Y";
			 	 remarks ="Referal";
			 	 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndRemarks(paging, status , req.getLoginid(),
			 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode(), new BigDecimal(req.getSchemeid())   ,remarks );
			 	 //Filter By Broker Code
				 homeDataFilter = homeDatas.getContent().stream().filter(o -> o.getBrokerCode().equals(commonDetails.get("BrokerCodes"))).collect(Collectors.toList());	
			 } else if(req.getReferalstatus().equalsIgnoreCase("A")) {
				// Approved Referal Data	
				 status = "Y";
				 remarks ="Admin";	
				 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndRemarksAndPaymentStatusIsNull(paging, status , 
						 		req.getLoginid(), req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode(), new BigDecimal(req.getSchemeid())   ,remarks );
				 homeDataFilter = homeDatas.getContent();
			 } else if(req.getReferalstatus().equalsIgnoreCase("N")) {
				 // Rejected Referal Data
				 status = "R";
				 remarks ="Referal";
				 String adminReferalStatus = "N";	
				 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndRemarksAndAdminReferralStatusAndPaymentStatusIsNull(paging,
				 			status , req.getLoginid(), req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode(), new BigDecimal(req.getSchemeid())   ,remarks ,adminReferalStatus);
				 homeDataFilter = homeDatas.getContent();
			 }
			 
			//Get Home Position Master & Personal Info Details
			 resList = getPolicyDetails( req , homeDataFilter ,  commonDetails);
			 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return resList;
		}
		return resList; 
	}
	
	//***************** Port Folio Details *****************//
	public List<CommonResponse> getPortFolioDetails(ExistingRequest req){
		List<CommonResponse> resList = new ArrayList<CommonResponse>();
		try {
			// Get Common Details
			Map<String, String> commonDetails = getCommonDetails(req);
			
			String status ="" ;
			Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("entryDate").descending());
			Page<HomePositionMaster> homeDatas = null ;
			List<HomePositionMaster>	homeDataFilter = new ArrayList<HomePositionMaster>();	
			
			if(req.getPortfoliostatus().equalsIgnoreCase("P") ) {
				// Active Polices Details 
				 status = "P";
			 	 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndIntegrationErrorIsNull(paging, status , req.getLoginid(),
			 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode(), new BigDecimal(req.getSchemeid()) );
			 	//Filter By Broker Code
				 homeDataFilter = homeDatas.getContent().stream().filter(o -> o.getBrokerCode().equals(commonDetails.get("BrokerCodes"))).collect(Collectors.toList());	 
			} else if(req.getPortfoliostatus().equalsIgnoreCase("D")) {
				// Cancelled Polices Details 
				 status = "D";
			 	 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndPolicyNoIsNotNull(paging, status , req.getLoginid(),
			 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode(), new BigDecimal(req.getSchemeid()) );
			 	 //Filter By Broker Code
				 homeDataFilter = homeDatas.getContent().stream().filter(o -> o.getBrokerCode().equals(commonDetails.get("BrokerCodes"))).collect(Collectors.toList());	
			} else if (req.getPortfoliostatus().equalsIgnoreCase("F")) {
				// Pending Polices Details 
				String integStatus = "F";
			 	 homeDatas = homeRepo.findByLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndIntegrationStatus(paging,  req.getLoginid(),
			 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode(), new BigDecimal(req.getSchemeid()) , integStatus);
			 	
				 homeDataFilter = homeDatas.getContent();
			}
			
		   //Get Home Position Mastrer & PErsonal Info Details
		   resList = getPolicyDetails( req , homeDataFilter ,  commonDetails);
			 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return resList;
		}
		return resList; 
	}
	
	
	
	
	//******************Search Datas ********************//
	public List<CommonResponse> getSearchedDatas(SearchRequest req){
		List<CommonResponse> resList = new ArrayList<CommonResponse>();
		try {
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			Query query  = null;
			String column = req.getSearchby();
			String value = req.getSearchvalue();
			
			if(req.getSearchby().equalsIgnoreCase("MOBILE") || req.getSearchby().equalsIgnoreCase("CUST_NAME") ||  req.getSearchby().equalsIgnoreCase("FIRST_NAME") 
					|| req.getSearchby().equalsIgnoreCase("LAST_NAME") ) {
				
				query =  em.createNativeQuery(" Select HPM.QUOTE_NO , HPM.POLICY_NO , HPM.CUSTOMER_ID , HPM.LOGIN_ID , HPM.APPLICATION_ID , HPM.PRODUCT_ID ,"
						+ " HPM.BRANCH_CODE , HPM.SCHEME_ID  From Home_Position_master HPM Where HPM.CUSTOMER_ID IN ( Select PI.CUSTOMER_ID From Personal_Info "
						+ " PI where "  +" Lower ( PI." + column +  " ) =  Lower ( '"+ value + "' ) )" ) ;		
			}else {	
				query =  em.createNativeQuery("Select QUOTE_NO , POLICY_NO , CUSTOMER_ID ,LOGIN_ID , APPLICATION_ID ,PRODUCT_ID , "
						+ "BRANCH_CODE ,SCHEME_ID From Home_Position_master where " + " Lower ( " + column + " ) =  Lower ( '"+ value + "' ) " );	
			}
			if (query != null) {
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				mapList = query.getResultList();
			}
			
			for(Map<String, Object> data : mapList ) {
				ExistingRequest req2 = new ExistingRequest();
				req2.setQuoteno(data.get("QUOTE_NO") == null ? "" :data.get("QUOTE_NO").toString() );
				req2.setLoginid(data.get("LOGIN_ID") == null ? "" :data.get("LOGIN_ID").toString() );
				req2.setApplicationid(data.get("APPLICATION_ID") == null ? "" :data.get("APPLICATION_ID").toString() );
				req2.setProductid(data.get("PRODUCT_ID") == null ? "" :data.get("PRODUCT_ID").toString() );
				req2.setBranchcode(data.get("BRANCH_CODE") == null ? "" :data.get("BRANCH_CODE").toString() );
				req2.setSchemeid(data.get("SCHEME_ID") == null ? "" :data.get("SCHEME_ID").toString() );
				
				// Get Common Details
				Map<String, String> commonDetails = getCommonDetails(req2);
				
				List<HomePositionMaster> homeDataFilter = homeRepo.findByLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndQuoteNoOrderByQuoteNoDesc(req2.getLoginid(),
						req2.getApplicationid(), new BigDecimal(req2.getProductid()) , req2.getBranchcode(), new BigDecimal(req2.getSchemeid()), new BigDecimal(req2.getQuoteno()) );
				
				//Get Home Position Mastrer & PErsonal Info Details
				List<CommonResponse> resList2 = getPolicyDetails( req2 , homeDataFilter ,  commonDetails);
				
				resList.addAll(resList2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return resList;
		}
		return resList; 
	}
	
	//******************Copy Quote Datas ********************//
	@Transactional
	public CommonResponse copyQuoteDetails(ExistingRequest req ) {
		CommonResponse res = new CommonResponse();
		try {
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			 Query query  = null;
			 String column = req.getCopyquoteby();
			 String value = req.getCopyquotevalue();
			 
			 if(req.getCopyquoteby().equalsIgnoreCase("QUOTE_NO") || req.getCopyquoteby().equalsIgnoreCase("POLICY_NO")  ) {
				 
				 query =  em.createNativeQuery(" Select QUOTE_NO ,CUSTOMER_ID , SCHEME_ID ,LOGIN_ID , APPLICATION_ID , BRANCHCODE From Ofs_Data Where " 
						 + column + " = " +  value ) ;
			 } else {
				
				 query =  em.createNativeQuery("Select QUOTE_NO , POLICY_NO , CUSTOMER_ID ,LOGIN_ID , APPLICATION_ID ,PRODUCT_ID , "
							+ "BRANCH_CODE ,SCHEME_ID From Home_Position_master where " + " Lower ( " + column + " ) =  Lower ( '"+ value + "' ) " );
			 }
			 
			 if (query != null) {
					NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
					nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
					mapList = query.getResultList();
			 }
			 
			 if( mapList.size() > 0 ) {
				 Map<String, Object> data = mapList.get(0);			 
				 String oldQuoteNo = data.get("QUOTE_NO")==null? "": data.get("QUOTE_NO").toString() ;
				 		  
				OfsData ofsData =  ofsDataRepo.findByQuoteNo(new BigDecimal(oldQuoteNo) );
				if(ofsData!=null ) {	
					String newQuoteNo = ofsDataRepo.getSeqQuoteNo();
					 //Copy Quote  OFs Data 
					 String saveRes = ofsDataSave(oldQuoteNo , newQuoteNo);
					 
					 if(saveRes.equalsIgnoreCase("Success")) {
						 //Copy Quote  OFs Data Detail
						 saveRes =  saveOfsDataDetail(oldQuoteNo , newQuoteNo);
						 
						 // Copy Quote Location Details
						 saveRes =  saveLocationDetails(oldQuoteNo , newQuoteNo);
					 }
					 
					// Get Common Details
					Map<String, String> commonDetails = getCommonDetails(req);
				 
					 res.setOldreferenceno(oldQuoteNo);
					 res.setQuote_no(newQuoteNo);
					 res.setReference_no(newQuoteNo); 
					 res.setCustomer_name(ofsData.getFirstName() + " "+ ofsData.getLastName());
					 res.setBranchname(commonDetails.get("BranchName"));
					 res.setBroker_company_name(commonDetails.get("BrokerCompanyName"));
					 res.setBroker_id(commonDetails.get("BrokerCodes"));
					 res.setBroker_name(commonDetails.get("BrokerName"));
					 res.setProduct_name(commonDetails.get("ProductName"));
					 res.setSchemeName(commonDetails.get("SchemeName"));
					 res.setInception_date(ofsData.getInceptionDate() == null ? "" : sdfFormat.format(ofsData.getInceptionDate()));
					 res.setLast_name(ofsData.getLastName());
					 res.setLogin_id(ofsData.getLoginId());
					 res.setMobileno(ofsData.getTelephone());
					 res.setPolicy_start_date(ofsData.getInceptionDate() == null ? "" : sdfFormat.format(ofsData.getInceptionDate()));
					 res.setQuotation_date(ofsData.getEntryDate() == null ? "" : sdfFormat.format(ofsData.getEntryDate()));
					 res.setValidity_date(ofsData.getExpiryDate() == null ? "" : sdfFormat.format(ofsData.getExpiryDate()));
					 res.setExpiry_date(ofsData.getExpiryDate() == null ? "" : sdfFormat.format(ofsData.getExpiryDate()));
				}
			}		 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return res;
		}
		return res; 
	}
	
	public String ofsDataSave(String oldQuoteNo , String newQuoteNo ) {
		String saveRes = "";
		try {
			OfsData ofsData =  ofsDataRepo.findByQuoteNo(new BigDecimal(oldQuoteNo) );
			ModelMapper mapper = new ModelMapper(); 
			 OfsDataDto dto = mapper.map(ofsData ,OfsDataDto.class);
			 dto.setQuoteNo(new BigDecimal(newQuoteNo));
			 OfsData saveOfs = mapper.map(dto ,OfsData.class); 
			 ofsDataRepo.save(saveOfs);	
			 saveRes = "Success";
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return saveRes;
		}
		return saveRes; 
	}
	
	public String saveOfsDataDetail(String oldQuoteNo , String newQuoteNo) {
		String saveRes = "";
		try {				
			// Copy Quote Ofs Data Detail
			List<OfsDataDetails> ofsDetailsData = ofsDataDetailRepo.findByQuoteNo(new BigDecimal(oldQuoteNo));
			ModelMapper mapper = new ModelMapper(); 
			if(ofsDetailsData.size()>0 ) {
			//Dto	
			List<OfsDataDetailsDto> dtoList = mapper.map(ofsDetailsData, new TypeToken<List<OfsDataDetailsDto>>() {}.getType());
			dtoList.forEach( o -> o.setQuoteNo(new BigDecimal(newQuoteNo)) ) ; 
			//Save
			List<OfsDataDetails> saveOfsDataDetail = mapper.map(dtoList, new TypeToken<List<OfsDataDetails>>() {}.getType());
			
			ofsDataDetailRepo.saveAll(saveOfsDataDetail);
			}
				saveRes = "Success";
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return saveRes;
		}
		return saveRes; 
	}
	
	public String saveLocationDetails(String oldQuoteNo , String newQuoteNo) {
		String saveRes = "";
		try {		
		// Copy Quote Policy Location
			List<PolicyLocation> polLoc = locationRepo.findByPolicyNo(oldQuoteNo); 
			ModelMapper mapper = new ModelMapper();
			if( polLoc.size()>0) {
				
				List<PolicyLocationDto> dtoList = mapper.map(polLoc, new TypeToken<List<PolicyLocationDto>>() {}.getType());
				dtoList.forEach( o -> o.setPolicyNo(newQuoteNo) ) ; 
				List<PolicyLocation> saveLocs =  mapper.map(dtoList, new TypeToken<List<PolicyLocation>>() {}.getType());
				
				locationRepo.saveAll(saveLocs);
				saveRes = "Success";
			}		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return saveRes;
		}
		return saveRes; 
	}

	//******************Update Lapsed Quote********************//
	public lapsedQuoteReq updateLapsedQuote(lapsedQuoteReq request) {
		lapsedQuoteReq res = new lapsedQuoteReq();
		try {
			HomePositionMaster  data = homeRepo.findByQuoteNo(new BigDecimal(request.getQuoteNo()));
			if(data!=null) {
				data.setStatus("D");
				data.setLapsedDate(new Date());
				data.setLapsedRemarks(request.getLapsedremarks());
				data.setLapsedUpdatedBy(request.getLoginId());
				homeRepo.save(data);
				
				res = request ;
				res.setStatus(true);
				res.setErrors(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return res;
		}
		return res; 
	}

	//******************Get Quote Report********************//
	public List<CommonResponse> getReportDataDetails(ExistingRequest req) {
		List<CommonResponse> resList = new ArrayList<CommonResponse>();
		try{
			List<String> status = new ArrayList<String>();
			status.add("P");
			status.add("D");
			Date startDate = sdfFormat.parse(req.getStartdate());
			Date endDate = sdfFormat.parse(req.getEnddate());
			endDate.setHours(23);
			endDate.setMinutes(59);
			
			List<HomePositionMaster> homeDatas = homeRepo.findByStatusInAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndSchemeIdAndEntryDateBetweenOrderByQuoteNoDesc(
					status, req.getLoginid() , req.getApplicationid(),new BigDecimal(req.getProductid()) ,req.getBranchcode(),new BigDecimal(req.getSchemeid()) ,startDate , endDate ); 
			
			if(homeDatas.size()>0 ) {
				// Get Common Details
				Map<String, String> commonDetails = getCommonDetails(req);
				resList = getPolicyDetails(req , homeDatas , commonDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return resList;
		}
		return resList; 
	}
	
	//******************Home Position Master & Policy Info Details ********************//
		public List<CommonResponse> getPolicyDetails(ExistingRequest req , List<HomePositionMaster> homeDatas , Map<String, String> commonDetails){
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				
				//Customer Ids
				List<BigDecimal> customerIds = homeDatas.stream().map(HomePositionMaster :: getCustomerId ).collect(Collectors.toList());
				List<PersonalInfo> personalDatas = personalRepo.findByCustomerIdInOrderByEntryDateDesc(customerIds) ;
				
				// Get Quote Details
				for (HomePositionMaster data : homeDatas) {
					CommonResponse res = new CommonResponse();
					
					List<PersonalInfo> 	perInfos = personalDatas.stream().filter(o -> o.getCustomerId().equals(data.getCustomerId()) ).collect(Collectors.toList()) ;
					PersonalInfo	perInfo = perInfos.get(0);
					
					res.setAmend_id(data.getAmendId().toString());
					res.setApplication_no(data.getApplicationNo().toString());
					res.setApplicationid(data.getApplicationId());
					res.setBranchname(commonDetails.get("BranchName"));
					res.setBroker_company_name(commonDetails.get("BrokerCompanyName"));
					res.setBroker_id(commonDetails.get("BrokerCodes"));
					res.setBroker_name(commonDetails.get("BrokerName"));
					res.setContent_type_id(data.getContentTypeId() == null ? "" : data.getContentTypeId().toString());
					res.setCountryid(perInfo.getNationality());
					res.setCustapprovedate(data.getCustApproveDate()==null ? "" :sdfFormat.format(data.getCustApproveDate()));
					res.setCustomer_id(data.getCustomerId() == null ? "" : data.getCustomerId().toString());
					res.setCustomer_name(perInfo.getCustName());
					res.setEmail(perInfo.getEmail());
					res.setEffective_date(data.getEffectiveDate() == null ? "" : sdfFormat.format(data.getEffectiveDate()));
					res.setExpiry_date(data.getExpiryDate() == null ? "" : sdfFormat.format(data.getExpiryDate()));
					res.setExcess_premium(data.getExcessPremium() == null ?"" : data.getExcessPremium().toString());
					res.setInsured_name(perInfo.getCustName());
					res.setInception_date(data.getInceptionDate() == null ? "" : sdfFormat.format(data.getInceptionDate()));
					res.setIntegerrordesc(data.getIntegrationError());
					res.setIntegstatus(data.getIntegrationStatus());
					res.setLast_name(perInfo.getLastName());
					res.setLogin_id(req.getLoginid());
					res.setLapsed_date(data.getLapsedDate() == null ? "" : sdfFormat.format(data.getLapsedDate()));
					res.setLapsed_remarks(data.getLapsedRemarks());
					res.setMobileno(perInfo.getMobile());
					res.setModeofpayment(data.getPaymentMode());
					res.setOverall_premium(data.getOverallPremium() ==null?"" :data.getOverallPremium().toString() );
					res.setPolicy_start_date(data.getInceptionDate() == null ? "" : sdfFormat.format(data.getInceptionDate()));
					res.setPayment_mode(data.getPaymentMode() );					
					res.setPolicy_no(data.getPolicyNo());
					res.setPremium(data.getPremium()	 == null ? "" : data.getPremium().toString());
					res.setPremia_customer_name(perInfo.getFirstName() + " " + perInfo.getLastName());
					res.setProduct_name(commonDetails.get("ProductName"));			
					res.setQuote_no(data.getQuoteNo().toString());
					res.setQuotation_date(data.getEntryDate() == null ? "" : sdfFormat.format(data.getEntryDate()));
					res.setReceipt_no(data.getReceiptNo());
					res.setReferralRemarks(data.getReferralDescription());
					res.setReject_desc(data.getCustRejectRemarks());
					res.setRemarks(data.getRemarks());		
					res.setRenewalpolicyno(data.getRenewalOldPolicy());
					res.setRenewalyn(data.getRenewalDateYn());
					res.setStatus(data.getStatus());
					res.setSchemeid(req.getSchemeid());
					res.setSchemeName(commonDetails.get("SchemeName"));
					res.setUploadtranid(data.getTranId()==null ?"" : data.getTranId().toString());
					res.setValidity_period(null);
					res.setVehicle_id(data.getVehicleId()==null ?"": data.getVehicleId().toString());
					res.setValidity_date(data.getExpiryDate() == null ? "" : sdfFormat.format(data.getExpiryDate()));
										
				/*	res.setAaa_cardno(null);
				    res.setCountryname(null);
					res.setModelnamedesc(null);
					res.setNoofdayinhodapproved(null);
					res.setNoofdayinhodpending(null);
					res.setOldreferenceno(null);
					res.setOpen_cover_no(null);
					res.setOrangecardno(null);
					res.setOrangecardurl(null);
					res.setOrg_status(null);
					res.setPolicyholderid(null);
					res.setPlateno(null);
					res.setPromocode(null);	
					res.setReference_no(null);
					res.setStatus_type(null);
					res.setStatus_type_name(null);
					res.setSuminsured(null);
					res.setTravelcoverId(null);
					res.setTrim(null);
					res.setVehicletype(null); */
					
					resList.add(res);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				return resList;
			}
			return resList; 
		}
}
