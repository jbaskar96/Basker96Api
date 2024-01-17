package com.maan.common.menu.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.maan.common.bean.EserviceRequestDetail;
import com.maan.common.bean.EserviceVehicleDetail;
import com.maan.common.bean.HomePositionMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.PersonalInfo;
import com.maan.common.bean.PolicyLocation;
import com.maan.common.bean.ProductMaster;
import com.maan.common.domain.EserviceRequestDetailDto;
import com.maan.common.domain.EserviceVehicleDetailDto;
import com.maan.common.menu.req.ExistingRequest;
import com.maan.common.menu.req.SearchRequest;
import com.maan.common.menu.req.lapsedQuoteReq;
import com.maan.common.menu.res.CommonResponse;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.BrokerCompanyMasterRepository;
import com.maan.common.repository.EserviceRequestDetailRepository;
import com.maan.common.repository.EserviceVehicleDetailRepository;
import com.maan.common.repository.HomePositionMasterRepository;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.repository.PersonalInfoRepository;
import com.maan.common.repository.ProductMasterRepository;

@Service
public class MotorGridServiceImpl {
	
	@Autowired
	private EserviceRequestDetailRepository eserviceReqRepo;
	
	@Autowired
	private LoginMasterRepository loginRepo;
	
	@Autowired
	private BrokerCompanyMasterRepository brokerCompanyRepo;
	
	@Autowired
	private ProductMasterRepository productRepo;
	
	@Autowired
	private BranchMasterRepository branchRepo;
	
	@Autowired
	private HomePositionMasterRepository homeRepo;
	
	@Autowired
	private PersonalInfoRepository personalRepo;
	
	@Autowired
	private EntityManager em;

	@Autowired
	private EserviceRequestDetailRepository eserviceRepo ;
	
	@Autowired
	private EserviceVehicleDetailRepository evehicleRepo ;
	
	private Logger log = LogManager.getLogger(getClass());
	
	SimpleDateFormat sdfFormat = new SimpleDateFormat("dd/MM/yyyy");  
	
	// Get Motor Existing Quote Details
	public List<CommonResponse> getMotorExistingQuoteDetails(ExistingRequest req) {
		List<CommonResponse> resList = new ArrayList<CommonResponse>();
		
		try {
			// Get Motor Common Details
			Map<String, String> commonDetails = getCommonDetails(req);
			
			Page<EserviceRequestDetail>  enityList = null;
			List<EserviceRequestDetail> totalQuotes = new ArrayList<EserviceRequestDetail>();
			String status = "";
			if(StringUtils.isNotBlank(req.getStatus())) {
				// Existing Or Rejected Quotes
				status = req.getStatus();
				
				Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("entryDate").descending());
				
				enityList = eserviceReqRepo.findByLoginIdAndApplicationIdAndBranchCodeAndProductIdAndStatus(paging, req.getLoginid(), req.getApplicationid(), req.getBranchcode(),
							  new BigDecimal( req.getProductid()), status);
					
				
				
			}else if (req.getToday() != null && StringUtils.isNotBlank(req.getToday().toString())) {
				// Exipired Quote
				status = "Y";
				Date before30 = req.getToday();
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -30);
				before30 = c.getTime();
				
				Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("entryDate").descending());
				
				enityList = eserviceReqRepo.findByLoginIdAndApplicationIdAndBranchCodeAndProductIdAndStatusAndEntryDateBefore(paging, req.getLoginid(), req.getApplicationid(), req.getBranchcode(),
							  new BigDecimal( req.getProductid()), status , before30 );
			}
			
			
			// Filter By Not Referal , Not Customer Approved Quotes And Not PortFolio
			List<EserviceRequestDetail> withoutQuoteNo = enityList.getContent().stream().filter(o -> o.getQuoteNo()==null ).collect(Collectors.toList());
			
			List<BigDecimal> quoteNos = enityList.stream().filter(o -> o.getQuoteNo()!=null ).map( EserviceRequestDetail :: getQuoteNo ).collect(Collectors.toList());	
			List<HomePositionMaster> homeDatas = homeRepo.findByQuoteNoIn( quoteNos );
			
			List<EserviceRequestDetail> withQuoteNo = new ArrayList<EserviceRequestDetail>();
			
			if (homeDatas != null && homeDatas.size() > 0) {
				for (HomePositionMaster homeData : homeDatas) {
					List<EserviceRequestDetail> filterEser = enityList.stream().filter(o -> o.getQuoteNo() != null && o.getQuoteNo().equals(homeData.getQuoteNo())).collect(Collectors.toList());

					if (filterEser != null && filterEser.size() > 0) {
						// Customer Approve Status
						if (homeData.getCustApproveStatus() == null) {
							// Remarks
							if (homeData.getRemarks() == null) {
								// Status
								if (homeData.getStatus() != null && !homeData.getStatus().equals("P") && !homeData.getStatus().equals("D") && !homeData.getStatus().equals("F")) {
									
									withQuoteNo.add(filterEser.get(0));
								}
							} else if (homeData.getRemarks() != null && !homeData.getRemarks().equals("Admin") && !homeData.getRemarks().equals("Referal")) {
								// Status
								if (homeData.getStatus() != null && !homeData.getStatus().equals("P") && !homeData.getStatus().equals("D") && !homeData.getStatus().equals("F")) {
									
									withQuoteNo.add(filterEser.get(0));
								}
							}
						}
					}
				}
			}
			
			totalQuotes.addAll(withoutQuoteNo);
			totalQuotes.addAll(withQuoteNo);
			
			for (EserviceRequestDetail data : totalQuotes) {
				CommonResponse res = new CommonResponse();
				
				res.setReference_no(data.getRequestreferenceno());
				res.setApplication_no(data.getApplicationNo()==null?"":data.getApplicationNo().toString());
				res.setApplicationid(data.getApplicationId());
				res.setBranchname(commonDetails.get("BranchName"));
				res.setBroker_company_name(commonDetails.get("BrokerCompanyName"));
				res.setBroker_id(commonDetails.get("BrokerCodes"));
				res.setBroker_name(commonDetails.get("BrokerName"));			
				res.setCountryid(data.getMsCustomerDetail().getCdNationalityId() ==null ?"" : data.getMsCustomerDetail().getCdNationalityId().toString());
				res.setCountryname(data.getMsCustomerDetail().getCdNationalityEn());
				res.setCustomer_id(data.getCustomerId() == null ? "" : data.getCustomerId().toString());
				res.setCustomer_name(data.getMsCustomerDetail().getCdFirstName() + " " + data.getMsCustomerDetail().getCdLastName());
				res.setChassisno(data.getChassisno());
				res.setEmail(data.getEmail());
				res.setEffective_date(data.getMsCustomerDetail().getCdEffectiveDate() ==null ?"" :sdfFormat.format(data.getMsCustomerDetail().getCdEffectiveDate()));
				res.setEntry_date(data.getEntryDate() == null ? "" : sdfFormat.format(data.getEntryDate()));
				res.setExcess_premium(data.getExcess()) ;
				res.setExpiry_date(data.getPolicyenddate() == null ? "" : sdfFormat.format(data.getPolicyenddate()));
				res.setInsured_name(data.getMsCustomerDetail().getCdFirstName() + " " + data.getMsCustomerDetail().getCdLastName());
				res.setInception_date(data.getPolicystartdate() == null ? "" : sdfFormat.format(data.getPolicystartdate()));			
				res.setIntegstatus(data.getIntegrationStatus());
				res.setLast_name(data.getMsCustomerDetail().getCdLastName());
				res.setLogin_id(req.getLoginid());
				res.setLapsed_date(data.getLapsedDate() == null ? "" : sdfFormat.format(data.getLapsedDate()));
				res.setLapsed_remarks(data.getLapsedRemarks());
				res.setMobileno(data.getMsCustomerDetail().getCdMobileNo());
				res.setModelnamedesc(data.getModelName());
				res.setOverall_premium(data.getOverallPremium()==null ?"" :data.getOverallPremium().toString());
				res.setPolicy_start_date(data.getPolicystartdate() == null ? "" : sdfFormat.format(data.getPolicystartdate()));
				res.setPolicyholderid(data.getPolicyholderid() == null ? "" : data.getPolicyholderid());
				res.setPolicytype(data.getPolicytype());
				res.setPremium(data.getPremium() == null ? "" : data.getPremium().toString());
				res.setProduct_name(commonDetails.get("ModuleName"));
				res.setPremia_customer_name(data.getMsCustomerDetail().getCdFirstName() + " " + data.getMsCustomerDetail().getCdLastName());
				res.setProduct_name(commonDetails.get("ProductName"));
				res.setPlateno(data.getVehPlateNumber()==null?"" : data.getVehPlateNumber().toString() );
				res.setPolicy_no(data.getPolicyNo());
				res.setPromocode(data.getPromocode());
				res.setQuote_no(data.getQuoteNo()==null?"":data.getQuoteNo().toString());
				res.setQuotation_date(data.getEntryDate() == null ? "" : sdfFormat.format(data.getEntryDate()));
				res.setReferralRemarks(data.getReferralRemarks());
				res.setRemarks(data.getMsCustomerDetail().getCdRemarks());	
				res.setReference_no(data.getRequestreferenceno());
				res.setReject_desc(data.getCustRejectRemarks());
				res.setRenewalpolicyno(data.getRenewalPolicyno());
				res.setRenewalyn(data.getRenewalYn());		
				res.setStatus(data.getStatus());
				//res.setSchemeid(req.getSchemeid());
				//res.setSchemeName(commonDetails.get("SchemeName"));
				res.setSuminsured(data.getSuminsured());
				res.setValidity_date(data.getPolicyenddate() == null ? "" : sdfFormat.format(data.getPolicyenddate()));
				res.setVehicle_id( data.getVehicleId()==null ? "" : data.getVehicleId().toString() );
				res.setVehicletype(data.getVehTypeId() == null ?"":data.getVehTypeId().toString());
				
			/*  res.setAmend_id(data.getEserviceVehicleDetail().get.getA.toString());
			    res.setAaa_cardno(null);
			    res.setBodynamedesc(null );
			    res.setCode(null  );
				res.setCodedesc(null);
				res.setComments(null);
				res.setCommision(null );
				res.setContent_type_id(data.getContentTypeId() == null ? "" : data.getContentTypeId().toString());
				res.setCompany_name(null);
				res.setCredit_no(null  );
				res.setCredit_note_no(null);
				res.setIntegerrordesc(null );
				res.setModeofpayment(null  );
				res.setMakenamedesc(null);
				res.setNoofdayinhodapproved(null );
				res.setNoofdayinhodpending(null);
				res.setOldreferenceno(null);
				res.setOpen_cover_no(null);
				res.setOrangecardno(null);
				res.setOrangecardurl(null);
				res.setOrg_status(null);
				res.setReceipt_no(null);
				res.setRsa_cardno(null );
				res.setStatus_type(null  );
				res.setStatus_type_name(null);
				res.setTravelcoverId(null  );
				res.setTrim(null);
				res.setUploadtranid(null  ); 
				res.setValidity_period(null  ); */
				resList.add(res);
			}		
			return resList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	//***************** Get Motor Common Details *****************//
		public Map<String, String> getCommonDetails(ExistingRequest req) {
			Map<String, String> list = new HashMap<String, String>();
			try {
				List<LoginMaster> loginData = new ArrayList<LoginMaster>();
				
				loginData = loginRepo.findByLoginId(req.getLoginid()); 
				
				list.put("BrokerCodes",  loginData.get(0).getOaCode());
				
				List<BrokerCompanyMaster> broCompany = brokerCompanyRepo.findByAgencyCode( loginData.get(0).getOaCode()); 
				if (broCompany != null && broCompany.size() > 0) {		
					list.put("BrokerCompanyName", broCompany.get(0).getCompanyName());
					list.put("BrokerName", broCompany.get(0).getContactPerson());
				}
				
				ProductMaster data = productRepo.findByProductId(new BigDecimal(req.getProductid()));
				if (data != null) {	
					list.put("ProductName", data.getProductName());
				}
				
		/*		MModule mdata = mmodelRepo.findByMmModuleid(new BigDecimal(req.getSchemeid()));
				if (mdata != null) {	
					list.put("SchemeName", mdata.getMmModulename());
				} */
				
				if (StringUtils.isNotBlank(req.getBranchcode())) { 
					BranchMaster bmData = branchRepo.findByBranchCode(req.getBranchcode()).get(0) ;
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

		//***************** Get Motor Customer Approval Detail *****************//
		public List<CommonResponse> getMotorCustomerApprovalDetails(ExistingRequest req){
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
				
				Page<HomePositionMaster> homeDatas = null;
				
					homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndCustApproveStatusAndEntryDateAfterAndRemarksOrRemarksNot(paging,
							"Y", req.getLoginid(), req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode() ,custAppStatus , before30 ,null ,remarksNot );
				
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
		public List<CommonResponse> getMotorReferalData(ExistingRequest req){
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
				 	
				 	homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndRemarks(paging, status , req.getLoginid(),
			 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode()   ,remarks );	
					
				 	//Filter By Broker Code
					 homeDataFilter = homeDatas.getContent().stream().filter(o -> o.getBrokerCode().equals(commonDetails.get("BrokerCodes"))).collect(Collectors.toList());	
				 } else if(req.getReferalstatus().equalsIgnoreCase("A")) {
					// Approved Referal Data	
					 status = "Y";
					 remarks ="Admin";	
					
					 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndRemarksAndPaymentStatusIsNull(paging, status , 
							 		req.getLoginid(), req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode()   ,remarks );
					 
					 homeDataFilter = homeDatas.getContent();
				 } else if(req.getReferalstatus().equalsIgnoreCase("N")) {
					 // Rejected Referal Data
					 status = "R";
					 remarks ="Referal";
					 String adminReferalStatus = "N";	
					 
					 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndRemarksAndAdminReferralStatusAndPaymentStatusIsNull(paging,
						 			status , req.getLoginid(), req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode()   ,remarks ,adminReferalStatus);
					 
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
		
		//*****************Get Motor Port Folio Details *****************//
		public List<CommonResponse> getMotorPortFolioDetails(ExistingRequest req){
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
					 
					 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndIntegrationErrorIsNull(paging, status , req.getLoginid(),
				 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode());
					 	 	
				 	//Filter By Broker Code
					 homeDataFilter = homeDatas.getContent().stream().filter(o -> o.getBrokerCode().equals(commonDetails.get("BrokerCodes"))).collect(Collectors.toList());	
					 
				} else if(req.getPortfoliostatus().equalsIgnoreCase("D")) {
					// Cancelled Polices Details 
					 status = "D";
					 
					 homeDatas = homeRepo.findByStatusAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndPolicyNoIsNotNull(paging, status , req.getLoginid(),
			 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode() );
					
				 	 //Filter By Broker Code
					 homeDataFilter = homeDatas.getContent().stream().filter(o -> o.getBrokerCode().equals(commonDetails.get("BrokerCodes"))).collect(Collectors.toList());	
					 
				} else if (req.getPortfoliostatus().equalsIgnoreCase("F")) {
					// Pending Polices Details 
					String integStatus = "F";
					
					homeDatas = homeRepo.findByLoginIdAndApplicationIdAndProductIdAndBranchCodeAndIntegrationStatus(paging,  req.getLoginid(),
			 			 		req.getApplicationid(), new BigDecimal(req.getProductid()) , req.getBranchcode() , integStatus);
					 	 	
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
		public List<CommonResponse> getMotorSearchedDatas(SearchRequest req){
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				Query query  = null;
				String column = req.getSearchby();
				String value = req.getSearchvalue();
				if(req.getSearchby().equalsIgnoreCase("REQUESTREFERENCENO")) {
					
					query =  em.createNativeQuery(" Select HPM.QUOTE_NO , HPM.POLICY_NO , HPM.CUSTOMER_ID , HPM.LOGIN_ID , HPM.APPLICATION_ID , HPM.PRODUCT_ID ,"
		                    + " HPM.BRANCH_CODE  From Home_Position_master HPM Where HPM.CUSTOMER_ID IN ( Select ERD.CUSTOMER_ID From ESERVICE_REQUEST_DETAIL "
		                      +   " ERD where "   +" Lower ( ERD." + column +  " ) =  Lower ( '"+ value + "' ) )"  ) ;
					
				} else if(req.getSearchby().equalsIgnoreCase("MOBILE") || req.getSearchby().equalsIgnoreCase("CUST_NAME") ||  req.getSearchby().equalsIgnoreCase("FIRST_NAME") 
						|| req.getSearchby().equalsIgnoreCase("LAST_NAME") ) {
					
					query =  em.createNativeQuery(" Select HPM.QUOTE_NO , HPM.POLICY_NO , HPM.CUSTOMER_ID , HPM.LOGIN_ID , HPM.APPLICATION_ID , HPM.PRODUCT_ID ,"
							+ " HPM.BRANCH_CODE   From Home_Position_master HPM Where HPM.CUSTOMER_ID IN ( Select PI.CUSTOMER_ID From Personal_Info "
							+ " PI where "  +" Lower ( PI." + column +  " ) =  Lower ( '"+ value + "' ) )" ) ;		
				}else {	
					query =  em.createNativeQuery("Select QUOTE_NO , POLICY_NO , CUSTOMER_ID ,LOGIN_ID , APPLICATION_ID ,PRODUCT_ID , "
							+ "BRANCH_CODE  From Home_Position_master where " + " Lower ( " + column + " ) =  Lower ( '"+ value + "' ) " );	
				}
				if (query != null) {
					NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
					nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
					mapList = query.getResultList();
				}
				
				for(Map<String, Object> data : mapList ) {
					ExistingRequest req2 = new ExistingRequest();
					req2.setCustomerId(data.get("CUSTOMER_ID") == null ? "" :data.get("CUSTOMER_ID").toString() );
					req2.setLoginid(data.get("LOGIN_ID") == null ? "" :data.get("LOGIN_ID").toString() );
					req2.setApplicationid(data.get("APPLICATION_ID") == null ? "" :data.get("APPLICATION_ID").toString() );
					req2.setProductid(data.get("PRODUCT_ID") == null ? "" :data.get("PRODUCT_ID").toString() );
					req2.setBranchcode(data.get("BRANCH_CODE") == null ? "" :data.get("BRANCH_CODE").toString() );
					
					// Get Common Details
					Map<String, String> commonDetails = getCommonDetails(req2);
					
					List<HomePositionMaster> homeDataFilter = homeRepo.findByLoginIdAndApplicationIdAndProductIdAndBranchCodeAndCustomerIdOrderByEntryDateDesc(req2.getLoginid(),
							req2.getApplicationid(), new BigDecimal(req2.getProductid()) , req2.getBranchcode(), new BigDecimal(req2.getCustomerId()) );
					
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
		@Transactional()
		public CommonResponse motorCopyQuoteDetails(ExistingRequest req ) {
			CommonResponse res = new CommonResponse();
			try {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				 Query query  = null;
				 String column = req.getCopyquoteby();
				 String value = req.getCopyquotevalue();
				 
				 
				 if(req.getCopyquoteby().equalsIgnoreCase("REQUESTREFERENCENO") ) {
						
						query =  em.createNativeQuery(" Select HPM.QUOTE_NO , HPM.POLICY_NO , HPM.CUSTOMER_ID , HPM.LOGIN_ID , HPM.APPLICATION_ID , HPM.PRODUCT_ID ,"
			                    + " HPM.BRANCH_CODE  From Home_Position_master HPM Where HPM.CUSTOMER_ID IN ( Select ERD.CUSTOMER_ID From ESERVICE_REQUEST_DETAIL "
			                      +   " ERD where "   +" Lower ( ERD." + column +  " ) =  Lower ( '"+ value + "' ) )"  ) ;
						
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
					 String customerId = data.get("CUSTOMER_ID")==null? "": data.get("CUSTOMER_ID").toString() ;
					 String oldRefNo = "";
					 String newRefNo = "";		  
					List<EserviceRequestDetail> eserviceDatas =  eserviceRepo.findByCustomerIdOrderByEntryDateDesc(new BigDecimal(customerId) );
					if(eserviceDatas.size()>0 ) {	
						EserviceRequestDetail eserviceData = eserviceDatas.get(0);
						 oldRefNo = eserviceData.getRequestreferenceno();
						 newRefNo = eserviceRepo.getSeqRefNo();
						 //Copy Quote  Eservice Rquest Details
						 String saveRes = EservcieRequestSave(oldRefNo ,customerId , newRefNo);
						 
						 if(saveRes.equalsIgnoreCase("Success")) {
							 //Copy Quote  Eservice Vehicle Details
							 saveRes =  EservcieVehicletSave(oldRefNo ,customerId , newRefNo);
		 
						 } 
						  
						// Get Common Details
						Map<String, String> commonDetails = getCommonDetails(req);
					 
						 res.setOldreferenceno(oldRefNo);
						 res.setQuote_no(newRefNo);
						 res.setReference_no(eserviceData.getRequestreferenceno()); 
						 res.setCustomer_name(eserviceData.getMsCustomerDetail().getCdFirstName() + " "+ eserviceData.getMsCustomerDetail().getCdLastName());
						 res.setBranchname(commonDetails.get("BranchName"));
						 res.setBroker_company_name(commonDetails.get("BrokerCompanyName"));
						 res.setBroker_id(commonDetails.get("BrokerCodes"));
						 res.setBroker_name(commonDetails.get("BrokerName"));
						 res.setProduct_name(commonDetails.get("ProductName"));
						 res.setSchemeName(commonDetails.get("SchemeName"));
						 res.setInception_date(eserviceData.getPolicystartdate()== null ? "" : sdfFormat.format(eserviceData.getPolicystartdate()));
						 res.setLast_name(eserviceData.getMsCustomerDetail().getCdLastName());
						 res.setLogin_id(eserviceData.getMsCustomerDetail().getCdLoginId());
						 res.setMobileno(eserviceData.getMsCustomerDetail().getCdMobileNo());
						 res.setPolicy_start_date(eserviceData.getPolicystartdate()== null ? "" : sdfFormat.format(eserviceData.getPolicystartdate()));
						 res.setQuotation_date(eserviceData.getEntryDate()== null ? "" : sdfFormat.format(eserviceData.getEntryDate()));
						 res.setValidity_date(eserviceData.getPolicyenddate() == null ? "" : sdfFormat.format(eserviceData.getPolicyenddate()));
						 res.setExpiry_date(eserviceData.getPolicyenddate() == null ? "" : sdfFormat.format(eserviceData.getPolicyenddate())); 
					} 
				}		 
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				return res;
			}
			return res; 
		}
		
		
		//******************Home Position Master & Policy Info Details ********************//
		public List<CommonResponse> getPolicyDetails(ExistingRequest req , List<HomePositionMaster> homeDatas , Map<String, String> commonDetails){
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				
				//Customer Ids
				List<BigDecimal> customerIds = homeDatas.stream().map(HomePositionMaster :: getCustomerId ).collect(Collectors.toList());
				List<PersonalInfo> personalDatas = personalRepo.findByCustomerIdInOrderByEntryDateDesc(customerIds) ;
				
				//Quote Nos
				List<BigDecimal> quoteNos = homeDatas.stream().map(HomePositionMaster :: getQuoteNo ).collect(Collectors.toList());
				List<EserviceRequestDetail> eserviceRequestDetails = eserviceRepo.findByQuoteNoInOrderByEntryDateDesc(quoteNos );
				
				// Get Quote Details
				for (HomePositionMaster data : homeDatas) {
					CommonResponse res = new CommonResponse();
					
					List<PersonalInfo> 	perInfos = personalDatas.stream().filter(o -> o.getCustomerId().equals(data.getCustomerId()) ).collect(Collectors.toList()) ;
					PersonalInfo	perInfo = perInfos.get(0);
					EserviceRequestDetail eserData = eserviceRequestDetails.get(0);
					
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
					res.setReference_no(eserData.getRequestreferenceno());
					res.setReceipt_no(data.getReceiptNo());
					res.setReferralRemarks(data.getReferralDescription());
					res.setReject_desc(data.getCustRejectRemarks());
					res.setRemarks(data.getRemarks());		
					res.setRenewalpolicyno(data.getRenewalOldPolicy());
					res.setRenewalyn(data.getRenewalDateYn());
					res.setStatus(data.getStatus());
					res.setUploadtranid(data.getTranId()==null ?"" : data.getTranId().toString());
					res.setValidity_period(null);
					res.setVehicle_id(data.getVehicleId()==null ?"": data.getVehicleId().toString());
					res.setValidity_date(data.getExpiryDate() == null ? "" : sdfFormat.format(data.getExpiryDate()));
					res.setBranchCode(data.getBranchCode());		
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
					res.setStatus_type(null);
					res.setStatus_type_name(null);
					res.setSuminsured(null);
					res.setSchemeid(req.getSchemeid());
					res.setSchemeName(commonDetails.get("SchemeName"));
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
		
		
		//***************** Eservcie Request Save********************//
		public String EservcieRequestSave(String oldRefNo ,String customerId , String newRefNo ) {
			String saveRes = "";
			try {
				EserviceRequestDetail eserviceData =  eserviceRepo.findByRequestreferencenoAndCustomerIdOrderByEntryDateDesc(
						oldRefNo , new BigDecimal(customerId) );
				ModelMapper mapper = new ModelMapper();
				EserviceRequestDetailDto dto = mapper.map(eserviceData, EserviceRequestDetailDto.class );
				dto.setRequestreferenceno(newRefNo);
				dto.setCustomerId(new BigDecimal(customerId));
				
				EserviceRequestDetail save = mapper.map(dto, EserviceRequestDetail.class );
				eserviceRepo.save(save);				
				
				 saveRes = "Success";
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				return saveRes;
			}
			return saveRes; 
		}
		
		//***************** Eservcie Vehicel Save********************//
		public String EservcieVehicletSave(String oldRefNo ,String customerId , String newRefNo ) {
			String saveRes = "";
			try {
				List<EserviceVehicleDetail> evehicleDatas =  evehicleRepo.findByVehicleReferencenoAndCustomerIdOrderByEntrydateDesc(
						oldRefNo , new BigDecimal(customerId) );
				
				if(evehicleDatas.size()>0) {
					ModelMapper mapper = new ModelMapper();
					List<EserviceVehicleDetailDto> dtoList = mapper.map(evehicleDatas, new TypeToken<List<EserviceVehicleDetailDto>>() {}.getType());
					dtoList.forEach( o -> o.setVehicleReferenceno(newRefNo) ) ; 
					List<EserviceVehicleDetail> save =  mapper.map(dtoList, new TypeToken<List<EserviceVehicleDetail>>() {}.getType());
					
					evehicleRepo.saveAll(save);				
				}
				 saveRes = "Success";
				
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
				List<HomePositionMaster> homeDatas = null ;
				
				homeDatas =  homeRepo.findByStatusInAndLoginIdAndApplicationIdAndProductIdAndBranchCodeAndEntryDateBetweenOrderByQuoteNoDesc(
							status, req.getLoginid() , req.getApplicationid(),new BigDecimal(req.getProductid()) ,req.getBranchcode() ,startDate , endDate ); 
				
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

		
		//****************** Admin Referal Details ********************//
		public List<CommonResponse> getAdminExistingQuoteList(ExistingRequest req) {
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {

				Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("entryDate").descending());
				Page<HomePositionMaster> homeDatas = null;

				if (req.getAdminreferalstatus().equalsIgnoreCase("R") || req.getAdminreferalstatus().equalsIgnoreCase("A")) {
					// Existing Or Approved Admin Reral Grid
					List<String> status = new ArrayList<String>();
					status.add("Y");
					status.add("E");
					
					// "Referal" For Existing , "Admin" for Rejected
					String Remarks = req.getAdminreferalstatus().equalsIgnoreCase("R")?"Referal" : req.getAdminreferalstatus().equalsIgnoreCase("A")?"Admin" :"";

					
					if (req.getBranchcode().equalsIgnoreCase("All")) {
						// Attached Branches
						List<LoginMaster> loginData = loginRepo.findByLoginId(req.getLoginid());
						if (loginData.get(0).getAttachedBranch() != null) {
							List<String> attachedBranches = Arrays.asList(loginData.get(0).getAttachedBranch().split(","));
							
							List<BrokerCompanyMaster> brokers = brokerCompanyRepo.findByBranchCodeIn(attachedBranches );
							List<String> brokerCodes = brokers.stream().map(BrokerCompanyMaster :: getAgencyCode  ).collect(Collectors.toList());
							List<LoginMaster> brokerloginDatas = loginRepo.findByOaCodeIn(brokerCodes );
							
							//Broker Login Ids
							List<String> brokerLoginIds =brokerloginDatas.stream().map(LoginMaster :: getLoginId  ).collect(Collectors.toList());
							
							homeDatas = homeRepo.findByStatusInAndLoginIdInAndProductIdAndRemarks(paging, status,brokerLoginIds ,new BigDecimal(req.getProductid()),  Remarks);
						}

					
					} else {
						// Particular Branch
						List<BrokerCompanyMaster> brokers = brokerCompanyRepo.findByBranchCode(req.getBranchcode());
						List<String> brokerCodes = brokers.stream().map(BrokerCompanyMaster :: getAgencyCode  ).collect(Collectors.toList());
						List<LoginMaster> brokerloginDatas = loginRepo.findByOaCodeIn(brokerCodes );
						
						//Broker Login Ids
						List<String> brokerLoginIds =brokerloginDatas.stream().map(LoginMaster :: getLoginId  ).collect(Collectors.toList());
						
						homeDatas = homeRepo.findByStatusInAndLoginIdInAndProductIdAndBranchCodeAndRemarks(paging, status,brokerLoginIds ,new BigDecimal(req.getProductid()), req.getBranchcode(), Remarks);
					}
				//
				} else if (req.getAdminreferalstatus().equalsIgnoreCase("N")) {
					// Rejected Admin Reral Grid
					String status ="R";
					String AdminReferalStatus = "N";
					
					if (req.getBranchcode().equalsIgnoreCase("All")) {
						// Attached Branches
						List<LoginMaster> loginData = loginRepo.findByLoginId(req.getLoginid());
						if (loginData.get(0).getAttachedBranch() != null) {
							List<String> attachedBranches = Arrays.asList(loginData.get(0).getAttachedBranch().split(","));
							
							List<BrokerCompanyMaster> brokers = brokerCompanyRepo.findByBranchCodeIn(attachedBranches );
							List<String> brokerCodes = brokers.stream().map(BrokerCompanyMaster :: getAgencyCode  ).collect(Collectors.toList());
							List<LoginMaster> brokerloginDatas = loginRepo.findByOaCodeIn(brokerCodes );
							
							//Broker Login Ids
							List<String> brokerLoginIds =brokerloginDatas.stream().map(LoginMaster :: getLoginId  ).collect(Collectors.toList());
							
							homeDatas = homeRepo.findByStatusAndLoginIdInAndProductIdAndAdminReferralStatus(paging, status,brokerLoginIds ,new BigDecimal(req.getProductid()), AdminReferalStatus);
						}

					
					} else {
						// Particular Branch
						List<BrokerCompanyMaster> brokers = brokerCompanyRepo.findByBranchCode(req.getBranchcode());
						List<String> brokerCodes = brokers.stream().map(BrokerCompanyMaster :: getAgencyCode  ).collect(Collectors.toList());
						List<LoginMaster> brokerloginDatas = loginRepo.findByOaCodeIn(brokerCodes );
						
						//Broker Login Ids
						List<String> brokerLoginIds =brokerloginDatas.stream().map(LoginMaster :: getLoginId  ).collect(Collectors.toList());
						
						homeDatas = homeRepo.findByStatusAndLoginIdInAndProductIdAndBranchCodeAndAdminReferralStatus(paging, status,brokerLoginIds ,new BigDecimal(req.getProductid()), req.getBranchcode(),AdminReferalStatus);
					}
				//
				} 

				// Get Home Position Master & Personal Info Details
				resList = getAdminSidePolicyDetails(req, homeDatas.getContent());

				// Get Broker Details
				for (CommonResponse data : resList) {
					req.setBranchcode(data.getBranchCode());
					req.setLoginid(data.getLogin_id());

					Map<String, String> commonDetails = getCommonDetails(req);

					// Broker Details
					data.setBranchname(commonDetails.get("BranchName"));
					data.setBroker_company_name(commonDetails.get("BrokerCompanyName"));
					data.setBroker_id(commonDetails.get("BrokerCodes"));
					data.setBroker_name(commonDetails.get("BrokerName"));
					data.setProduct_name(commonDetails.get("ProductName"));
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				return resList;
			}
			return resList;
		}
		
		//******************Admin Side Home Position Master & Policy Info Details ********************//
			public List<CommonResponse> getAdminSidePolicyDetails(ExistingRequest req , List<HomePositionMaster> homeDatas ){
				List<CommonResponse> resList = new ArrayList<CommonResponse>();
				try {
					
					//Customer Ids
					List<BigDecimal> customerIds = homeDatas.stream().map(HomePositionMaster :: getCustomerId ).collect(Collectors.toList());
					List<PersonalInfo> personalDatas = personalRepo.findByCustomerIdInOrderByEntryDateDesc(customerIds) ;
					
					//Quote Nos
					List<BigDecimal> quoteNos = homeDatas.stream().map(HomePositionMaster :: getQuoteNo ).collect(Collectors.toList());
					List<EserviceRequestDetail> eserviceRequestDetails = eserviceRepo.findByQuoteNoInOrderByEntryDateDesc(quoteNos );
					
					// Get Quote Details
					for (HomePositionMaster data : homeDatas) {
						CommonResponse res = new CommonResponse();
						
						List<PersonalInfo> 	perInfos = personalDatas.stream().filter(o -> o.getCustomerId().equals(data.getCustomerId()) ).collect(Collectors.toList()) ;
						PersonalInfo	perInfo = perInfos.get(0);
						EserviceRequestDetail eserData = eserviceRequestDetails.get(0);
						
						res.setAmend_id(data.getAmendId().toString());
						res.setApplication_no(data.getApplicationNo().toString());
						res.setApplicationid(data.getApplicationId());
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
						res.setLogin_id(data.getLoginId());
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
						res.setQuote_no(data.getQuoteNo().toString());
						res.setQuotation_date(data.getEntryDate() == null ? "" : sdfFormat.format(data.getEntryDate()));
						res.setReference_no(eserData.getRequestreferenceno());
						res.setReceipt_no(data.getReceiptNo());
						res.setReferralRemarks(data.getReferralDescription());
						res.setReject_desc(data.getCustRejectRemarks());
						res.setRemarks(data.getRemarks());		
						res.setRenewalpolicyno(data.getRenewalOldPolicy());
						res.setRenewalyn(data.getRenewalDateYn());
						res.setStatus(data.getStatus());
						res.setUploadtranid(data.getTranId()==null ?"" : data.getTranId().toString());
						res.setValidity_period(null);
						res.setVehicle_id(data.getVehicleId()==null ?"": data.getVehicleId().toString());
						res.setValidity_date(data.getExpiryDate() == null ? "" : sdfFormat.format(data.getExpiryDate()));
						res.setBranchCode(data.getBranchCode());		
						res.setCreatedby(data.getLoginId());
						
						resList.add(res);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					return resList;
				}
				return resList; 
			}

			public List<CommonResponse> getAdminPortfolioList(ExistingRequest req) {
				List<CommonResponse> resList = new ArrayList<CommonResponse>();
				try {

					Pageable paging = PageRequest.of(Integer.valueOf(req.getLimit()), Integer.valueOf(req.getOffset()),Sort.by("entryDate").descending());
					Page<HomePositionMaster> homeDatas = null;

					List<BrokerCompanyMaster> brokers = brokerCompanyRepo.findByBranchCode(req.getBranchcode());
					List<String> brokerCodes = brokers.stream().map(BrokerCompanyMaster :: getAgencyCode  ).collect(Collectors.toList());
					List<LoginMaster> brokerloginDatas = loginRepo.findByOaCodeIn(brokerCodes );
					
					List<String> brokerLoginIds =brokerloginDatas.stream().map(LoginMaster :: getLoginId  ).collect(Collectors.toList());
					
					homeDatas = homeRepo.findByStatusAndLoginIdInAndProductIdAndEntryDateGreaterThanEqualAndEntryDateLessThanEqual(paging, req.getStatus(),brokerLoginIds ,new BigDecimal(req.getProductid()),sdfFormat.parse(req.getStartdate()),sdfFormat.parse(req.getEnddate()));
							
					// Get Home Position Master & Personal Info Details
					resList = getAdminSidePolicyDetails(req, homeDatas.getContent());

					// Get Broker Details
					for (CommonResponse data : resList) {
						req.setBranchcode(data.getBranchCode());
						req.setLoginid(data.getLogin_id());

						Map<String, String> commonDetails = getCommonDetails(req);

						// Broker Details
						data.setBranchname(commonDetails.get("BranchName"));
						data.setBroker_company_name(commonDetails.get("BrokerCompanyName"));
						data.setBroker_id(commonDetails.get("BrokerCodes"));
						data.setBroker_name(commonDetails.get("BrokerName"));
						data.setProduct_name(commonDetails.get("ProductName"));
					}

				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					return resList;
				}
				return resList;
			}

}
