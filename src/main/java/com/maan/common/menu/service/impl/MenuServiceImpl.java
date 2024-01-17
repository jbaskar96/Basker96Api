package com.maan.common.menu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maan.common.bean.ListItemValue;
import com.maan.common.error.Error;
import com.maan.common.error.ErrorInstance;
import com.maan.common.menu.req.DropDownReq;
import com.maan.common.menu.req.ExistingRequest;
import com.maan.common.menu.req.SearchRequest;
import com.maan.common.menu.req.lapsedQuoteReq;
import com.maan.common.menu.res.CommonResponse;
import com.maan.common.menu.res.DropDownResponse;
import com.maan.common.menu.service.MenuService;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.ListItemValueRepository;
import com.maan.common.repository.PolicyLocationRepository;
import com.maan.common.upload.service.impl.CommonService;
/*import com.maan.common.entity.domestic.DomesticEmployeeDetail;
import com.maan.common.entity.motor.CustomerDetails;
import com.maan.common.entity.motor.QuoteDetails;
import com.maan.common.entity.motor.VehicleDetails;
import com.maan.common.entity.vision.MarineConstantDetail; */
@Service
public class MenuServiceImpl implements MenuService{

	private Logger log = LogManager.getLogger(getClass());

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CommonService commonService;
	@Autowired
	private BranchMasterRepository branchRepo;
	
	@Autowired
	private NonMotorGridServiceImpl nonGridService;
	
	@Autowired
	private MotorGridServiceImpl motorGridService; 
	
	@Autowired
	private ListItemValueRepository listRepo;
	
/*	@Autowired
	private OmSmsDataDetailsRepo omSmsRepo;	
	@Autowired
	private DomesticEmployeeDetailRepo empRepo;
	@Autowired
	private QuoteDetailsRepo quoteRepo;	
	@Autowired
	private WCEmployeeDetailRawRepo empDtlRawRepo; */
	
	SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	
	//********************************* QUOTE-REGISTER ***************************** 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CommonResponse> getExpireQuoteData(ExistingRequest req) {
		Query query = null;
		List<Map<String, Object>> mapList = null;
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		String productId = null == req.getProductid() ? "" : req.getProductid();
		String loginid = "";
		log.info("ExpireQuoteData--> ProductId: " + productId);
		try {
			String branchCode = StringUtils.isBlank(req.getBranchcode()) ? "" : req.getBranchcode();

			if (StringUtils.isBlank(req.getLoginid())) {
				loginid = branchBasedLoginId(branchCode);
			} else {
				loginid = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			}

			String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();
			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			if ("55555".equalsIgnoreCase(subUserType)) {
				appId = "";
			} /*
				 * else if ("77777".equalsIgnoreCase(subUserType)) { appId = appId; } else {
				 * appId = appId; }
				 */

			if (productId.equals("33")) {
				query = em.createNativeQuery(commonService.getQuery().getProperty("TRAVEL_EXPIRE_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
				// query.setParameter(2, req.getProductid());
			} else if (productId.equals("30")) {
				req.setToday(new Date());
				// Get Expired Quote
				modelList = nonGridService.getExistingQuoteDetails(req);
				
				return modelList;
			} else if (productId.equals("65")) {
				req.setToday(new Date());
				// Get Expired Quote
				modelList = motorGridService.getMotorExistingQuoteDetails(req);
				
				return modelList;
			/*	query = em.createNativeQuery(commonService.getQuery().getProperty("MOTOR_EXPIRE_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId); */
			} else if (productId.equals("35")) {
				query = em.createNativeQuery(commonService.getQuery().getProperty("DOMESTIC_EXPIRE_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
				query.setParameter(3, branchCode);
			} else if (productId.equals("75")) {
				query = em.createNativeQuery(commonService.getQuery().getProperty("FLEET_EXPIRE_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, req.getApplicationid() == null ? "1" : req.getApplicationid());
			} else if (productId.equals("77")) {
			//	mapList = empDtlRawRepo.get_LapsedData(loginid, appId, productId);
			}

			if (!productId.equals("77")) {
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				mapList = query.getResultList();
			}

			for (int i = 0; i < mapList.size(); i++) {
				Map<String, Object> map = mapList.get(i);

				CommonResponse expireData = new CommonResponse();

				String customerName = null == map.get("CUSTOMER_NAME") ? "" : String.valueOf(map.get("CUSTOMER_NAME"));

				expireData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
				expireData.setStatus(null == map.get("STATUS") ? "" : String.valueOf(map.get("STATUS")));
				expireData.setLapsed_remarks(
						null == map.get("LAPSED_REMARKS") ? "" : String.valueOf(map.get("LAPSED_REMARKS")));
				expireData.setLapsed_date(null == map.get("LAPSED_DATE") ? "" : String.valueOf(map.get("LAPSED_DATE")));
				expireData.setCustomer_id(null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
				expireData.setQuotation_date(
						null == map.get("QUOTATION_DATE") ? "" : String.valueOf(map.get("QUOTATION_DATE")));
				expireData.setValidity_date(
						null == map.get("VALIDITY_DATE") ? "" : String.valueOf(map.get("VALIDITY_DATE")));
				expireData.setCustomer_name(StringUtils.isBlank(customerName) ? ""
						: customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
				expireData.setPremium(map.get("PREMIUM") == null ? "0" : map.get("PREMIUM").toString());
				expireData.setReference_no(
						map.get("REQUESTREFERENCENO") == null ? "" : map.get("REQUESTREFERENCENO").toString());
				expireData.setLogin_id(loginid);
				expireData.setVehicletype(
						null == map.get("ISCOMMERCIAL_YN") ? "" : String.valueOf(map.get("ISCOMMERCIAL_YN")));
				expireData.setCompany_name(map.get("COMPANY_NAME") == null ? "" : map.get("COMPANY_NAME").toString());
				expireData.setApplication_no(null == map.get("APPLICATION_NO") ? "" : String.valueOf(map.get("APPLICATION_NO"))); 
				expireData.setUploadtranid(null == map.get("UPLOAD_TRAN_ID") ? "" : String.valueOf(map.get("UPLOAD_TRAN_ID")));
				expireData.setStatus_type(null == map.get("STATUS_TYPE") ? "" : String.valueOf(map.get("STATUS_TYPE")));
				expireData.setRenewalyn(null == map.get("RENEWALYN") ? "" : "Y".equalsIgnoreCase(String.valueOf(map.get("RENEWALYN")))?"Yes":"No"); 
				expireData.setRenewalpolicyno(null == map.get("RENEWALPOLICYNO") ? "" : String.valueOf(map.get("RENEWALPOLICYNO")));
				expireData.setStatus_type(null == map.get("STATUS_TYPE") ? "" : String.valueOf(map.get("STATUS_TYPE")));
				modelList.add(expireData);
			}
			return modelList;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public List<CommonResponse> getExistingData(ExistingRequest req) {
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		try {
			if ("35".equalsIgnoreCase(req.getProductid())) {
			//	modelList = getDomesticExistingDetails(req);
			}else if ("30".equalsIgnoreCase(req.getProductid())) {
				
				req.setStatus("Y");
				// Get Existing Quote
				modelList = nonGridService.getExistingQuoteDetails(req);
				
			}else if ("65".equalsIgnoreCase(req.getProductid())) {
				
				req.setStatus("Y");
				// Get Existing Quote
				modelList = motorGridService.getMotorExistingQuoteDetails(req);
				
			}else {
			//	modelList = getExistingDetails(req);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return modelList;
	}

	public List<CommonResponse> getDomesticExistingDetails(ExistingRequest req) {
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		try {
	/*		List<DomesticEmployeeDetail> existdata = null;
			String productId = null == req.getProductid() ? "" : req.getProductid();
			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			log.info("ExistingData -- ProductId: " + productId);
			if (productId.equals("35")) {
				String appId = null == req.getApplicationid() ? "" : req.getApplicationid();
				String loginId = null == req.getLoginid() ? "" : req.getLoginid();
				Date entryDate = dateformat.parse(dateformat.format(new Date()));
				Calendar c = Calendar.getInstance();
				c.setTime(entryDate);
				c.add(Calendar.DATE, -30);
				Date entryDate30 = c.getTime();
				existdata = new ArrayList<DomesticEmployeeDetail>();

				if (StringUtils.isBlank(loginId)) {
					List<BranchMaster> bmData = branchRepo.findByBranchMasterIdBranchcodeAndStatus(req.getBranchcode(),
							"Y");
					if (null != bmData && bmData.size() > 0) {
						String coreAppCode = null == bmData.get(0).getCoreappcode() ? ""
								: bmData.get(0).getCoreappcode();
						loginId = coreAppCode + "001";
					}
				}

				if (StringUtils.isNotBlank(appId)) {
					if ("1".equalsIgnoreCase(appId)) {
						if ("55555".equalsIgnoreCase(subUserType)) {
							existdata = empRepo
									.findByLoginidAndStatusAndBranchcodeAndProductidAndIntegrationstatusIsNullAndCustapprovestatusIsNullAndEntrydateIsAfterOrderByEntrydateDesc(
											loginId, "Y", req.getBranchcode(), Long.valueOf(productId), entryDate30);
						} else {
							existdata = empRepo
									.findByLoginidAndApplicationidAndStatusAndBranchcodeAndProductidAndIntegrationstatusIsNullAndCustapprovestatusIsNullAndEntrydateIsAfterOrderByEntrydateDesc(
											loginId, appId, "Y", req.getBranchcode(), Long.valueOf(productId),
											entryDate30);
						}
					} else if (!"1".equalsIgnoreCase(appId)) {
						if ("55555".equalsIgnoreCase(subUserType)) {
							existdata = empRepo
									.findByBdmcodeAndStatusAndBranchcodeAndProductidAndIntegrationstatusIsNullAndCustapprovestatusIsNullAndEntrydateIsAfterOrderByEntrydateDesc(
											loginId, "Y", req.getBranchcode(), Long.valueOf(productId), entryDate30);
						} else {
							existdata = empRepo
									.findByBdmcodeAndApplicationidAndStatusAndBranchcodeAndProductidAndIntegrationstatusIsNullAndCustapprovestatusIsNullAndEntrydateIsAfterOrderByEntrydateDesc(
											loginId, appId, "Y", req.getBranchcode(), Long.valueOf(productId),
											entryDate30);
						}
						 List<DomesticEmployeeDetail> existdatanew= empRepo.findByBdmcodeIsNullAndLoginidAndApplicationidAndStatusAndBranchcodeAndProductidAndIntegrationstatusIsNullAndCustapprovestatusIsNullAndEntrydateIsAfterOrderByEntrydateDesc(loginId,appId, "Y",req.getBranchcode(), Long.valueOf(productId), entryDate30);
						 if(existdatanew!=null && existdatanew.size()>0) {
							 existdata.addAll(existdatanew);
						}
					}
				} 
			}
			if (null != existdata && existdata.size() > 0) {
				for (int i = 0; i < existdata.size(); i++) {

					DomesticEmployeeDetail data = existdata.get(i);
					String custName = "";
					String firstName = null == data.getSponsorname() ? "" : data.getSponsorname();
					String lastName = null == data.getSponsorlastname() ? "" : data.getSponsorlastname();
					if (StringUtils.isNotBlank(firstName)) {
						custName = firstName;
						if (StringUtils.isNotBlank(lastName)) {
							custName = firstName + " " + lastName;
						}
					}
					CommonResponse existingData = new CommonResponse();
					existingData
							.setReference_no(null == data.getRequestreferenceno() ? "" : data.getRequestreferenceno());
					existingData.setQuote_no(null == data.getQuoteno() ? "" : data.getQuoteno().toString());
					existingData.setCustomer_id(null == data.getCustomerid() ? "" : data.getCustomerid().toString());
					existingData.setQuotation_date(
							null == data.getEntrydate() ? "" : dateformat.format(data.getEntrydate()));
					existingData.setValidity_date(
							null == data.getEntrydate() ? "" : dateformat.format(data.getEntrydate()));
					existingData.setCustomer_name(custName);
					existingData
							.setPremium(null == data.getOverallpremium() ? "0" : data.getOverallpremium().toString());
					existingData.setLogin_id(null == data.getLoginid() ? "0" : data.getLoginid());
					existingData.setInception_date(
							null == data.getPolicystartdate() ? "" : dateformat.format(data.getPolicystartdate()));
					existingData.setExpiry_date(
							null == data.getPolicyenddate() ? "" : dateformat.format(data.getPolicyenddate()));
					existingData.setApplication_no(
							null == data.getApplicationno() ? "" : data.getApplicationno().toString());
					modelList.add(existingData);
				}
				return modelList;
			} */
		} catch (Exception e) {
			log.error(e);
		}
		return modelList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<CommonResponse> getExistingDetails(ExistingRequest req) {
		List<Map<String, Object>> mapList = null;
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		try {
			Query query = null;

			String productId = StringUtils.isBlank(req.getProductid()) ? "" : req.getProductid();
			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			log.info("getExistingDetails--> ProductId: " + productId);

			String loginid = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

			if (productId.equals("65")) {

				if (StringUtils.isBlank(req.getLoginid())) {
					loginid = branchBasedLoginId(req.getBranchcode());
				}

				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}

				query = em.createNativeQuery(commonService.getQuery().getProperty("MOTOR_EXISTING_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
				query.setParameter(3, productId);
			} else if (productId.equals("41")) {

				query = em.createNativeQuery(commonService.getQuery().getProperty("RSA_EXISTING_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
			} else if (productId.equals("75")) {

				if (StringUtils.isBlank(req.getLoginid())) {
					loginid = branchBasedLoginId(req.getBranchcode());
				}

				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}

				query = em.createNativeQuery(commonService.getQuery().getProperty("FLEET_EXISTING_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
				query.setParameter(3, productId);
			} else if (productId.equals("77")) {
				if (StringUtils.isBlank(loginid)) {
					loginid = branchBasedLoginId(req.getBranchcode());
				}
				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}
			//	mapList = empDtlRawRepo.get_wcExistingData(loginid, appId, productId);
			} else {

				query = em.createNativeQuery(commonService.getQuery().getProperty("TRAVEL_EXISTING_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
			}

			if (!productId.equals("77")) {
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				mapList = query.getResultList();
			}

			for (int i = 0; i < mapList.size(); i++) {
				Map<String, Object> map = mapList.get(i);

				CommonResponse existingData = new CommonResponse();

				String customerName = null == map.get("CUSTOMER_NAME") ? "" : String.valueOf(map.get("CUSTOMER_NAME"));

				existingData.setReference_no(
						null == map.get("REFERENCE_NO") ? "" : String.valueOf(map.get("REFERENCE_NO")));
				existingData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
				existingData
						.setCustomer_id(null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
				existingData.setQuotation_date(
						null == map.get("QUOTATION_DATE") ? "" : String.valueOf(map.get("QUOTATION_DATE")));
				existingData.setValidity_date(
						null == map.get("VALIDITY_DATE") ? "" : String.valueOf(map.get("VALIDITY_DATE")));
				existingData.setCustomer_name(StringUtils.isBlank(customerName) ? ""
						: customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
				existingData.setPremium(null == map.get("PREMIUM") ? "" : String.valueOf(map.get("PREMIUM")));
				existingData.setVehicletype(
						null == map.get("ISCOMMERCIAL_YN") ? "" : String.valueOf(map.get("ISCOMMERCIAL_YN")));
				existingData.setLogin_id(loginid);
				existingData
						.setStatus_type(null == map.get("STATUS_TYPE") ? "" : String.valueOf(map.get("STATUS_TYPE")));
				existingData.setUploadtranid(
						null == map.get("UPLOAD_TRAN_ID") ? "" : String.valueOf(map.get("UPLOAD_TRAN_ID")));
				existingData.setCompany_name(
						map.get("COMPANY_NAME") == null ? "" : String.valueOf(map.get("COMPANY_NAME")));
				existingData.setRenewalyn(null == map.get("RENEWALYN") ? "" :  "Y".equalsIgnoreCase(String.valueOf(map.get("RENEWALYN")))?"Yes":"No"); 
				existingData.setRenewalpolicyno(null == map.get("RENEWALPOLICYNO") ? "" : String.valueOf(map.get("RENEWALPOLICYNO")));
				/*
				 * existingData.setInception_date(String.valueOf(mapList.get(i).get(
				 * "INCEPTION_DATE")));
				 * existingData.setEmail(String.valueOf(mapList.get(i).get("EMAIL")));
				 */
				modelList.add(existingData);
			}
			return modelList;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	@Override
	public List<CommonResponse> getExistingDataWQ(ExistingRequest request) {
		try {
			List<CommonResponse> modelList = new ArrayList<CommonResponse>();
			List<Map<String, Object>> mapList = new ArrayList<>();
			String productId = null==request.getProductid()?"":request.getProductid();
			String loginid= null==request.getLoginid()?"":request.getLoginid();
			String appid=  null==request.getApplicationid()?"":request.getApplicationid();
			if(StringUtils.isBlank(loginid)) {
				 loginid =  branchBasedLoginId(request.getBranchcode());
			 }
			log.info("getExistingDataWQ -- ProductId: "+productId);
			if(productId.equals("65")) {
				String subUserType = StringUtils.isBlank(request.getSubusertype())?"":request.getSubusertype();
				if("55555".equalsIgnoreCase(subUserType)) {
					appid ="";
				}else if("77777".equalsIgnoreCase(subUserType)) {
					appid =appid;
				}else {
					appid =appid;
				}
				Query query = em.createNativeQuery(commonService.getQuery().getProperty("MOTOR_WAJAH_EXISTING_DATA"));		
				query.setParameter(1, loginid);
				query.setParameter(2,appid);
				query.setParameter(3,productId);
				@SuppressWarnings("rawtypes")
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query; 
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE); 
				mapList = query.getResultList();
			}else if(productId.equals("30")) {
				
			}
			for(int i=0;i<mapList.size();i++){
				 CommonResponse existingData=new CommonResponse();
				 String customerName=null==mapList.get(i).get("CUSTOMER_NAME")?"":String.valueOf(mapList.get(i).get("CUSTOMER_NAME"));
				 existingData.setReference_no(null==mapList.get(i).get("REFERENCE_NO")?"":String.valueOf(mapList.get(i).get("REFERENCE_NO")));
				 existingData.setQuote_no(null==mapList.get(i).get("QUOTE_NO")?"":String.valueOf(mapList.get(i).get("QUOTE_NO")));
				 existingData.setCustomer_id(null==mapList.get(i).get("CUSTOMER_ID")?"":String.valueOf(mapList.get(i).get("CUSTOMER_ID")));
				 existingData.setQuotation_date(null==mapList.get(i).get("QUOTATION_DATE")?"":String.valueOf(mapList.get(i).get("QUOTATION_DATE")));
				 existingData.setValidity_date(null==mapList.get(i).get("VALIDITY_DATE")?"":String.valueOf(mapList.get(i).get("VALIDITY_DATE")));
				 existingData.setCustomer_name(StringUtils.isBlank(customerName)?"":customerName.length()> 100?customerName.substring(0, 100):customerName);
				 existingData.setPremium(null==mapList.get(i).get("PREMIUM")?"":String.valueOf(mapList.get(i).get("PREMIUM")));
				 existingData.setVehicletype(null==mapList.get(i).get("ISCOMMERCIAL_YN")?"":String.valueOf(mapList.get(i).get("ISCOMMERCIAL_YN")));
				 existingData.setLogin_id(loginid);
				modelList.add(existingData);
			 }
			return modelList;
		}catch (Exception e) {log.error(e);}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CommonResponse> getRejectQuoteData(ExistingRequest req) {
		Query query = null;
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		String productid = StringUtils.isBlank(req.getProductid()) ? "" : req.getProductid();
		log.info("RejectQuoteData -- productid: " + productid);
		String loginid = "";
		try {
			String appId = StringUtils.isBlank(req.getApplicationid())? "1" : req.getApplicationid();
			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			if ("55555".equalsIgnoreCase(subUserType)) {
				appId = "";
			} /*
				 * else if("77777".equalsIgnoreCase(subUserType)) { appId =appId; }else { appId
				 * =appId; }
				 */
			if (StringUtils.isBlank(req.getLoginid())) {
				loginid = branchBasedLoginId(req.getBranchcode());
			} else {
				loginid = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			}
			if ("33".equals(productid)) {
				query = em.createNativeQuery(commonService.getQuery().getProperty("TRAVEL_REJECTED_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
				// query.setParameter(2, req.getProductid());
				
			}else if ("30".equals(productid)) {
				req.setStatus("D");
				// Get Rejected Quote
				modelList = nonGridService.getExistingQuoteDetails(req);
				return modelList;
				
			} 
			else if ("65".equals(productid)) {
				
				req.setStatus("D");
				// Get Existing Quote
				modelList = motorGridService.getMotorExistingQuoteDetails(req);
				return modelList;
			/*	query = em.createNativeQuery(commonService.getQuery().getProperty("MOTOR_REJECTED_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId); */
			} else if ("75".equals(productid)) {
				query = em.createNativeQuery(commonService.getQuery().getProperty("FLEET_REJECTED_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
			} else if ("35".equals(productid)) {
				String branchCode = StringUtils.isBlank(req.getBranchcode()) ? "" : req.getBranchcode();
				query = em.createNativeQuery(commonService.getQuery().getProperty("DOMESTIC_REJECTED_DATA"));
				query.setParameter(1, loginid);
				query.setParameter(2, appId);
				query.setParameter(3, branchCode);
			} else if ("77".equals(productid)) {
				//mapList = empDtlRawRepo.get_RejectData(loginid, appId, productid);
			}

			if (!"77".equals(productid)) {
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				mapList = query.getResultList();
			}

			for (int i = 0; i < mapList.size(); i++) {
				Map<String, Object> map = mapList.get(i);

				CommonResponse rejectData = new CommonResponse();

				String customerName = null == mapList.get(i).get("CUSTOMER_NAME") ? ""
						: String.valueOf(mapList.get(i).get("CUSTOMER_NAME"));

				rejectData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
				rejectData.setLapsed_remarks(
						null == map.get("LAPSED_REMARKS") ? "" : String.valueOf(map.get("LAPSED_REMARKS")));
				rejectData.setLapsed_date(null == map.get("LAPSED_DATE") ? "" : String.valueOf(map.get("LAPSED_DATE")));
				rejectData.setApplication_no(
						null == map.get("APPLICATION_NO") ? "" : String.valueOf(map.get("APPLICATION_NO")));
				rejectData.setCustomer_id(null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
				rejectData.setQuotation_date(
						null == map.get("QUOTATION_DATE") ? "" : String.valueOf(map.get("QUOTATION_DATE")));
				rejectData.setValidity_period(
						null == map.get("VALIDITY_PERIOD") ? "" : String.valueOf(map.get("VALIDITY_PERIOD")));
				rejectData.setCustomer_name(StringUtils.isBlank(customerName) ? ""
						: customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
				rejectData.setLogin_id(loginid);
				rejectData.setStatus(null == map.get("COMPANY_NAME") ? "" : String.valueOf(map.get("COMPANY_NAME")));
				rejectData.setCompany_name(
						null == map.get("COMPANY_NAME") ? "" : String.valueOf(map.get("COMPANY_NAME")));
				rejectData.setReject_desc(null == map.get("REJECT_DESC") ? "" : String.valueOf(map.get("REJECT_DESC")));
				rejectData.setPremium(map.get("PREMIUM") == null ? "0" : map.get("PREMIUM").toString());
				rejectData.setReference_no(
						map.get("REQUESTREFERENCENO") == null ? "" : map.get("REQUESTREFERENCENO").toString());
				rejectData.setVehicletype(
						null == map.get("ISCOMMERCIAL_YN") ? "" : String.valueOf(map.get("ISCOMMERCIAL_YN")));
				rejectData.setStatus(null == map.get("STATUS") ? "" : String.valueOf(map.get("STATUS")));
				rejectData.setStatus_type(null == map.get("STATUS_TYPE") ? "" : String.valueOf(map.get("STATUS_TYPE")));
				rejectData.setUploadtranid(null == map.get("UPLOAD_TRAN_ID") ? "" : String.valueOf(map.get("UPLOAD_TRAN_ID")));
				rejectData.setValidity_date(null == map.get("VALIDITY_DATE") ? "" : String.valueOf(map.get("VALIDITY_DATE")));
				rejectData.setRenewalyn(null == map.get("RENEWALYN") ? "" :  "Y".equalsIgnoreCase(String.valueOf(map.get("RENEWALYN")))?"Yes":"No"); 
				rejectData.setRenewalpolicyno(null == map.get("RENEWALPOLICYNO") ? "" : String.valueOf(map.get("RENEWALPOLICYNO")));
				modelList.add(rejectData);
			}
			return modelList;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	//********************************* CUSTOMER-CONFIRM DETAILS *****************************************
	@Override
	public List<CommonResponse> getPendingAppovedData(ExistingRequest req) {
		List<CommonResponse> pendingapproveRes = new ArrayList<CommonResponse>();
		try {
			List<Map<String, Object>> pendingapproveData = new ArrayList<>();
			
			Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
			
			String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();
			
			log.info("Customer Pending Data--> loginId: " + loginId + " appId: " + appId + " productId: " + productId);
			
			if (StringUtils.isBlank(req.getLoginid())) {
				loginId = branchBasedLoginId(req.getBranchcode());
			}
			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			if ("55555".equalsIgnoreCase(subUserType)) {
				appId = "";
			}
			
			if (productId == 65) {
				// Get Customer Approved Pending  Data
				req.setCustapprovestatus("P");
				pendingapproveRes = motorGridService.getMotorCustomerApprovalDetails(req);
				return pendingapproveRes;
			//	pendingapproveData = omSmsRepo.getMotorApproveNonApproveData("P", loginId, appId, productId);
			}else if (productId == 30) {
				// Get Customer Approved Pending Data
				req.setCustapprovestatus("P");
				pendingapproveRes = nonGridService.getCustomerApprovalDetails(req);
				return pendingapproveRes;
			}  else if (productId == 35) {
			//	pendingapproveData = omSmsRepo.getDomesticCustomerConfirmData("P", loginId, appId, productId);
			}else if (productId == 33) {
			//	pendingapproveData = omSmsRepo.getTravelCustomerConfirmData("P", loginId, appId, productId);
			} 
			else {
			//	pendingapproveData = empDtlRawRepo.get_CommonCustData("P", loginId, appId, productId);
			}
			pendingapproveRes = approveNonApproveRes(pendingapproveData, loginId);

		} catch (Exception e) {
			log.error(e);
		}
		return pendingapproveRes;
	}
	
	@Override
	public List<CommonResponse> getNonApprovedData(ExistingRequest req) {
		List<CommonResponse> nonApproveRes = null;
		try {
			Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
			String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

			log.info("getNonApprovedData-->loginId: " + loginId + " appId: " + appId + " productId: " + productId);

			List<Map<String, Object>> nonApproveData = new ArrayList<>();

			if (StringUtils.isBlank(req.getLoginid())) {
				loginId = branchBasedLoginId(req.getBranchcode());
			}

			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			if ("55555".equalsIgnoreCase(subUserType)) {
				appId = "";
			}

			if (productId == 65) {
				// Get Motor Customer Non Approved Data
				req.setCustapprovestatus("N");
				nonApproveRes = motorGridService.getMotorCustomerApprovalDetails(req);
				return nonApproveRes;
			//	nonApproveData = omSmsRepo.getMotorApproveNonApproveData("N", loginId, appId, productId);
			}else if (productId == 30) {
				// Get Customer Non Approved Data
				req.setCustapprovestatus("N");
				nonApproveRes = nonGridService.getCustomerApprovalDetails(req);
				return nonApproveRes;
			}  else if (productId == 33) {
			//	nonApproveData = omSmsRepo.getApproveNonApproveData("N", loginId, appId);
			} else if (productId == 35) {
			//	nonApproveData = omSmsRepo.getDomesticCustomerConfirmData("N", loginId, appId, productId);
			} else {
			//	nonApproveData = empDtlRawRepo.get_CommonCustData("N", loginId, appId, productId);
			}

			nonApproveRes = approveNonApproveRes(nonApproveData, loginId);

			return nonApproveRes;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	@Override
	public List<CommonResponse> getApprovedData(ExistingRequest req) {
		List<CommonResponse> approveRes = new ArrayList<>();
		try {

			Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
			String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

			log.info("Customer Confirm ApprovedData---loginId: " + loginId + " appId: " + appId + " productId: "
					+ productId);

			if (StringUtils.isBlank(req.getLoginid())) {
				loginId = branchBasedLoginId(req.getBranchcode());
			}

			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			if ("55555".equalsIgnoreCase(subUserType)) {
				appId = "";
			}

			List<Map<String, Object>> approveData = new ArrayList<>();

			if (productId == 65) {
				req.setCustapprovestatus("Y");
				approveRes = motorGridService.getMotorCustomerApprovalDetails(req);
				return approveRes;
				
			//	approveData = omSmsRepo.getMotorApproveNonApproveData("Y", loginId, appId, productId);
			} else if (productId == 30) {
				// Get Customer Approved Data
				req.setCustapprovestatus("Y");
				approveRes = nonGridService.getCustomerApprovalDetails(req);
				return approveRes;
			} else if (productId == 33) {
			//	approveData = omSmsRepo.getApproveNonApproveData("Y", loginId, appId);
			} else if (productId == 35) {
			//	approveData = omSmsRepo.getDomesticCustomerConfirmData("Y", loginId, appId, productId);
			} else {
			//	approveData = empDtlRawRepo.get_CommonCustData("Y", loginId, appId, productId);
			}

			approveRes = approveNonApproveRes(approveData, loginId);

			return approveRes;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	@Override
	public List<CommonResponse> getReQuoteData(ExistingRequest req) {
		List<CommonResponse> requoteres = new ArrayList<>();
		try {
			Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
			String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

			log.info("getReQuoteData---loginId: " + loginId + " appId: " + appId + " productId: " + productId);

			List<Map<String, Object>> requoteData = new ArrayList<>();

			if (StringUtils.isBlank(req.getLoginid())) {
				loginId = branchBasedLoginId(req.getBranchcode());
			}

			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			if ("55555".equalsIgnoreCase(subUserType)) {
				appId = "";
			}

			if (productId == 65) {
				// Get Motor Customer Approved  Re-quoting Data
				req.setCustapprovestatus("R");
				requoteres = motorGridService.getMotorCustomerApprovalDetails(req);
				return requoteres;
			//	requoteData = omSmsRepo.getMotorApproveNonApproveData("R", loginId, appId, productId);
			}else if (productId == 30) {
				// Get Customer Approved Re-quoting Data
				req.setCustapprovestatus("R");
				requoteres = nonGridService.getCustomerApprovalDetails(req);
				return requoteres;
			}  else if (productId == 35) {
			//	requoteData = omSmsRepo.getDomesticCustomerConfirmData("R", loginId, appId, productId);
			}else if (productId == 33) {
			//	requoteData = omSmsRepo.getTravelCustomerConfirmData("R", loginId, appId, productId);
			} else {
			//	requoteData = empDtlRawRepo.get_CommonCustData("R", loginId, appId, productId);
			}

			requoteres = approveNonApproveRes(requoteData, loginId);

			return requoteres;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	private List<CommonResponse> approveNonApproveRes(List<Map<String, Object>> mapList, String loginId) {
		List<CommonResponse> response = new ArrayList<CommonResponse>();
		try {
			if (mapList.size() > 0 && mapList != null) {
				for (int i = 0; i < mapList.size(); i++) {
					Map<String, Object> map = mapList.get(i);

					CommonResponse existingData = new CommonResponse();

					String customerName = null == map.get("CUSTOMER_NAME") ? ""
							: String.valueOf(map.get("CUSTOMER_NAME"));

					existingData.setReference_no(
							null == map.get("REFERENCE_NO") ? "" : String.valueOf(map.get("REFERENCE_NO")));
					existingData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
					existingData.setCustomer_id(
							null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
					existingData.setQuotation_date(
							null == map.get("QUOTATION_DATE") ? "" : String.valueOf(map.get("QUOTATION_DATE")));
					existingData.setValidity_date(
							null == map.get("VALIDITY_DATE") ? "" : String.valueOf(map.get("VALIDITY_DATE")));
					existingData.setCustomer_name(StringUtils.isBlank(customerName) ? ""
							: customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
					existingData.setPremium(null == map.get("PREMIUM") ? "0" : String.valueOf(map.get("PREMIUM")));
					existingData.setRemarks(map.get("CUST_REJECT_REMARKS") == null ? ""
							: String.valueOf(map.get("CUST_REJECT_REMARKS")));
					existingData.setVehicletype(
							map.get("ISCOMMERCIAL_YN") == null ? "" : String.valueOf(map.get("ISCOMMERCIAL_YN")));
					existingData.setLogin_id(loginId);
					existingData
							.setCompany_name(map.get("COMPANY_NAME") == null ? "" : map.get("COMPANY_NAME").toString());
					response.add(existingData);
				}
			} else {
				response = null;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}
	
	private String branchBasedLoginId(String branchCode) {
		String loginId="";
		try {
			String branchQuery = commonService.getQuery().getProperty("EXISTING_LOGINID");
			List<Map<String, Object>> mapList =commonService.getRequestData(branchCode,branchQuery);
			if(mapList.size()>0) {
				loginId =mapList.get(0).get("LOGINID")==null?"":mapList.get(0).get("LOGINID").toString();
			}
		}catch(Exception e) {log.error(e);}
		return loginId;
	}

	//********************************* REFERAL-CONFIRM DETAILS *****************************************
	@Override
	public List<CommonResponse> getreferralgrid(ExistingRequest request) {
		try {
			List<CommonResponse> response = new ArrayList<>();
			
			List<Map<String, Object>> list = new ArrayList<>();
			
			String loginid = StringUtils.isBlank(request.getLoginid()) ? "" : request.getLoginid();
			String productid = StringUtils.isBlank(request.getProductid()) ? "" : request.getProductid();
			String applicationId = StringUtils.isBlank(request.getApplicationid()) ? "" : request.getApplicationid();
			
			log.info("getreferralgrid--> LoginID: " + loginid + " ProductID: " + productid + " applicationId: "+ applicationId);
			
			if (StringUtils.isBlank(loginid)) {
				loginid = branchBasedLoginId(request.getBranchcode());
			}
			
			if ("33".equals(productid)) {
			//	list = omSmsRepo.geTraveltreferaldata(loginid, Long.valueOf(productid));
			}else if ("30".equals(productid)) {
				
				// Get Referal Data
				request.setReferalstatus("Y");
				response = nonGridService.getReferalData(request);
				return response;
			}  else if ("65".equals(productid)) {
				
				// Get Motor Referal Data
				request.setReferalstatus("Y");
				response = motorGridService.getMotorReferalData(request);
				return response;
				
			/*	String subUserType = StringUtils.isBlank(request.getSubusertype()) ? "" : request.getSubusertype();
				if ("55555".equalsIgnoreCase(subUserType)) {
					applicationId = "";
				} */

			//	list = omSmsRepo.getreferaldata(loginid, Long.valueOf(productid), applicationId);
			} else if (productid.equals("77")) {
			//	list = empDtlRawRepo.get_PendingRefData(loginid, applicationId, productid);
			}

			log.info("getreferralgrid--> Size: " + list.size());

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);

				String customerName = map.get("CUSTOMER_NAME") == null ? "" : map.get("CUSTOMER_NAME").toString();

				CommonResponse req = CommonResponse.builder()
						.application_no(map.get("APPLICATION_NO") == null ? "" : map.get("APPLICATION_NO").toString())
						.company_name(map.get("COMPANY_NAME") == null ? "" : map.get("COMPANY_NAME").toString())
						.customer_id(map.get("CUSTOMER_ID") == null ? "" : map.get("CUSTOMER_ID").toString())
						.customer_name(StringUtils.isBlank(customerName) ? ""
								: customerName.length() > 100 ? customerName.substring(0, 100) : customerName)
						.inception_date(map.get("INCEPTION_DATE") == null ? "" : map.get("INCEPTION_DATE").toString())
						.login_id(loginid)
						.quotation_date(map.get("QUOTATION_DATE") == null ? "" : map.get("QUOTATION_DATE").toString())
						.quote_no(map.get("QUOTE_NO") == null ? "" : map.get("QUOTE_NO").toString())
						.Reference_no(
								map.get("REQUESTREFERENCENO") == null ? "" : map.get("REQUESTREFERENCENO").toString())
						.email(map.get("EMAIL") == null ? "" : map.get("EMAIL").toString())
						.premium(map.get("PREMIUM") == null ? "" : map.get("PREMIUM").toString())
						.validity_date(map.get("EXPIRY_DATE") == null ? "" : map.get("EXPIRY_DATE").toString())
						.referralRemarks(map.get("REFERRAL_DESCRIPTION") == null ? ""
								: map.get("REFERRAL_DESCRIPTION").toString())
						.comments(map.get("ADMIN_REMARKS") == null ? "" : map.get("ADMIN_REMARKS").toString())
						.vehicletype(map.get("ISCOMMERCIAL_YN") == null ? "" : map.get("ISCOMMERCIAL_YN").toString())
						.build();
				response.add(req);
			}

			return response;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public List<CommonResponse> getapprovedreferralgrid(ExistingRequest request) {
		try {
			List<CommonResponse> response = new ArrayList<>();

			List<Map<String, Object>> list = new ArrayList<>();

			String loginid = request.getLoginid();
			String productid = StringUtils.isBlank(request.getProductid()) ? "" : request.getProductid();
			String issuer = request.getApplicationid() == null ? "" : request.getApplicationid();

			if (StringUtils.isBlank(request.getLoginid())) {
				loginid = branchBasedLoginId(request.getBranchcode());
			}

			log.info("getapprovedreferralgrid--> LoginID: " + loginid + " ProductID: " + productid);

			if ("33".equals(productid)) {
			//	list = omSmsRepo.getTravelapprovedreferaldata(loginid, Long.valueOf(productid), issuer);
			}else if ("30".equals(productid)) {
				
				// Get Approved Referal Data
				request.setReferalstatus("A");
				response = nonGridService.getReferalData(request);
				return response;
			}  else if ("65".equals(productid)) {
				// Get Approved Referal Data
				request.setReferalstatus("A");
				response = motorGridService.getMotorReferalData(request);
				return response;
				
			/*	String subUserType = StringUtils.isBlank(request.getSubusertype()) ? "" : request.getSubusertype();
				if ("55555".equalsIgnoreCase(subUserType)) {
					issuer = "";
				} */

			//	list = omSmsRepo.getapprovedreferaldata(loginid, Long.valueOf(productid), issuer);
			} else if ("77".equals(productid)) {
			//	list = empDtlRawRepo.get_ApprovedRefData(loginid, issuer, productid);
			}

			log.info("getapprovedreferralgrid--> Size: " + list.size());

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);

				String customerName = map.get("CUSTOMER_NAME") == null ? "" : map.get("CUSTOMER_NAME").toString();

				CommonResponse req = CommonResponse.builder()
						.application_no(map.get("APPLICATION_NO") == null ? "" : map.get("APPLICATION_NO").toString())
						.company_name(map.get("COMPANY_NAME") == null ? "" : map.get("COMPANY_NAME").toString())
						.customer_id(map.get("CUSTOMER_ID") == null ? "" : map.get("CUSTOMER_ID").toString())
						.customer_name(StringUtils.isBlank(customerName) ? ""
								: customerName.length() > 100 ? customerName.substring(0, 100) : customerName)
						.inception_date(map.get("INCEPTION_DATE") == null ? "" : map.get("INCEPTION_DATE").toString())
						.login_id(loginid)
						.quotation_date(map.get("QUOTATION_DATE") == null ? "" : map.get("QUOTATION_DATE").toString())
						.quote_no(map.get("QUOTE_NO") == null ? "" : map.get("QUOTE_NO").toString())
						.Reference_no(
								map.get("REQUESTREFERENCENO") == null ? "" : map.get("REQUESTREFERENCENO").toString())
						.email(map.get("EMAIL") == null ? "" : map.get("EMAIL").toString())
						.premium(map.get("PREMIUM") == null ? "" : map.get("PREMIUM").toString())
						.validity_date(map.get("EXPIRY_DATE") == null ? "" : map.get("EXPIRY_DATE").toString())
						.vehicletype(map.get("ISCOMMERCIAL_YN") == null ? "" : map.get("ISCOMMERCIAL_YN").toString())
						.build();
				response.add(req);
			}
			return response;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public List<CommonResponse> getrejectedreferralgrid(ExistingRequest request) {
		try {
			List<CommonResponse> response = new ArrayList<>();

			List<Map<String, Object>> list = new ArrayList<>();

			String loginid = StringUtils.isBlank(request.getLoginid()) ? "" : request.getLoginid();
			String productid = StringUtils.isBlank(request.getProductid()) ? "" : request.getProductid();
			String issuer = StringUtils.isBlank(request.getApplicationid()) ? "" : request.getApplicationid();

			if (StringUtils.isBlank(loginid)) {
				loginid = branchBasedLoginId(request.getBranchcode());
			}

			log.info("getrejectedreferralgrid--> LoginID: " + loginid + " ProductID: " + productid);

			if ("33".equals(productid)) {
			//	list = omSmsRepo.getTravelrejectedreferralgrid(loginid, Long.valueOf(productid), issuer);
			}else if ("30".equals(productid)) {
				
				// Get Rejected Referal Data
				request.setReferalstatus("N");
				response = nonGridService.getReferalData(request);
				return response;
			}  else if ("65".equals(productid)) {

				// Get Rejected Referal Data
				request.setReferalstatus("N");
				response = motorGridService.getMotorReferalData(request);
				return response;
		/*		String subUserType = StringUtils.isBlank(request.getSubusertype()) ? "" : request.getSubusertype();
				if ("55555".equalsIgnoreCase(subUserType)) {
					issuer = "";
				} */
			//	list = omSmsRepo.getrejectedreferralgrid(loginid, Long.valueOf(productid), issuer);
			} else if ("77".equals(productid)) {
			//	list = empDtlRawRepo.get_RejectRefData(loginid, issuer, productid);
			}

			log.info("getrejectedreferralgrid--> Size: " + list.size());

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);

				String customerName = map.get("CUSTOMER_NAME") == null ? "" : map.get("CUSTOMER_NAME").toString();

				CommonResponse req = CommonResponse.builder()
						.application_no(map.get("APPLICATION_NO") == null ? "" : map.get("APPLICATION_NO").toString())
						.company_name(map.get("COMPANY_NAME") == null ? "" : map.get("COMPANY_NAME").toString())
						.customer_id(map.get("CUSTOMER_ID") == null ? "" : map.get("CUSTOMER_ID").toString())
						.customer_name(StringUtils.isBlank(customerName) ? ""
								: customerName.length() > 100 ? customerName.substring(0, 100) : customerName)
						.inception_date(map.get("INCEPTION_DATE") == null ? "" : map.get("INCEPTION_DATE").toString())
						.login_id(loginid)
						.quotation_date(map.get("QUOTATION_DATE") == null ? "" : map.get("QUOTATION_DATE").toString())
						.quote_no(map.get("QUOTE_NO") == null ? "" : map.get("QUOTE_NO").toString())
						.Reference_no(
								map.get("REQUESTREFERENCENO") == null ? "" : map.get("REQUESTREFERENCENO").toString())
						.email(map.get("EMAIL") == null ? "" : map.get("EMAIL").toString())
						.premium(map.get("PREMIUM") == null ? "" : map.get("PREMIUM").toString())
						.validity_date(map.get("EXPIRY_DATE") == null ? "" : map.get("EXPIRY_DATE").toString())
						.referralRemarks(map.get("REFERRAL_DESCRIPTION") == null ? ""
								: map.get("REFERRAL_DESCRIPTION").toString())
						.comments(map.get("ADMIN_REMARKS") == null ? "" : map.get("ADMIN_REMARKS").toString())
						.vehicletype(map.get("ISCOMMERCIAL_YN") == null ? "" : map.get("ISCOMMERCIAL_YN").toString())
						.build();
				response.add(req);
			}

			return response;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	//********************************* POLICY-APPROVAL DETAILS *****************************************
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CommonResponse> getActivePolicy(ExistingRequest req) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		try {

			String activePolicyQuery = null;

			Query query = null;

			String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();
			String loginid = "";
			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			String productid = StringUtils.isBlank(req.getProductid()) ? "" : req.getProductid();
			String usertype = StringUtils.isBlank(req.getUsertype()) ? "" : req.getUsertype();
					
			log.info("getActivePolicy--> appId : " + appId + " productid: " + productid);

			if ("55555".equalsIgnoreCase(subUserType)) {
				appId = "";
			}

			if (StringUtils.isBlank(req.getLoginid())) {
				loginid = branchBasedLoginId(req.getBranchcode());
			} else {
				loginid = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			}

			if ("65".equals(productid)) {
				
				// Get Ative Policies
				req.setPortfoliostatus("P");
				modelList = motorGridService.getMotorPortFolioDetails(req);
				return modelList;
		/*		if("broker".equalsIgnoreCase(usertype)){
					
					activePolicyQuery = commonService.getQuery().getProperty("MOTOR_ACTIVE_POLICY_BROKER");
					query = em.createNativeQuery(activePolicyQuery);
					query.setParameter(1, loginid);
					query.setParameter(2, productid);
					query.setParameter(3, appId);
				
				}else{
					
				activePolicyQuery = commonService.getQuery().getProperty("MOTOR_ACTIVE_POLICY");
				query = em.createNativeQuery(activePolicyQuery);
				query.setParameter(1, loginid);
				query.setParameter(2, productid);
				query.setParameter(3, appId);
				
				} */
			} else if ("30".equals(productid)) {
				
				// Get Ative Policies
				req.setPortfoliostatus("P");
				modelList = nonGridService.getPortFolioDetails(req);
				return modelList;
			}  else if ("35".equals(productid)) {
				activePolicyQuery = commonService.getQuery().getProperty("DOMESTIC_ACTIVE_POLICY");

				query = em.createNativeQuery(activePolicyQuery);
				query.setParameter(1, loginid);
				query.setParameter(2, productid);
				query.setParameter(3, appId);
			} else if ("41".equals(productid)) {
				activePolicyQuery = commonService.getQuery().getProperty("RSA_ACTIVE_POLICY");
				query = em.createNativeQuery(activePolicyQuery);
				query.setParameter(1, loginid);
				query.setParameter(2, productid);
			} else if ("33".equals(productid)) {
				activePolicyQuery = commonService.getQuery().getProperty("TRAVEL_ACTIVE_POLICY");
				query = em.createNativeQuery(activePolicyQuery);
				query.setParameter(1, loginid);
				query.setParameter(2, productid);
			} else {
			//	mapList = empDtlRawRepo.get_CommonActivePolicyData(loginid, appId, productid);
			}

			if (query != null) {
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				mapList = query.getResultList();
			}

			for (int i = 0; i < mapList.size(); i++) {
				Map<String, Object> map = mapList.get(i);

				CommonResponse activeData = new CommonResponse();

				String customerName = null == map.get("CUSTOMER_NAME") ? "" : String.valueOf(map.get("CUSTOMER_NAME"));

				activeData.setPolicy_no(null == map.get("POLICY_NO") ? "" : String.valueOf(map.get("POLICY_NO")));
				activeData.setApplication_no(
						null == map.get("APPLICATION_NO") ? "" : String.valueOf(map.get("APPLICATION_NO")));
				activeData.setCustomer_id(null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
				activeData.setOverall_premium(
						null == map.get("OVERALL_PREMIUM") ? "0" : String.valueOf(map.get("OVERALL_PREMIUM")));
				activeData.setQuotation_date(
						null == map.get("QUOTATION_DATE") ? "" : String.valueOf(map.get("QUOTATION_DATE")));
				activeData.setCustomer_name(StringUtils.isBlank(customerName) ? ""
						: customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
				activeData.setLast_name(null == map.get("LAST_NAME") ? "" : String.valueOf(map.get("LAST_NAME")));
				activeData.setLogin_id(loginid);
				activeData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
				activeData.setExcess_premium(
						null == map.get("EXCESS_PREMIUM") ? "0" : String.valueOf(map.get("EXCESS_PREMIUM")));
				activeData.setCompany_name(
						null == map.get("COMPANY_NAME") ? "" : String.valueOf(map.get("COMPANY_NAME")));
				activeData.setInception_date(
						null == map.get("INCEPTION_DATE") ? "" : String.valueOf(map.get("INCEPTION_DATE")));
				activeData.setOpen_cover_no(
						null == map.get("OPEN_COVER_NO") ? "" : String.valueOf(map.get("OPEN_COVER_NO")));
				activeData.setDebit_note_no(
						null == map.get("DEBIT_NOTE_NO") ? "" : String.valueOf(map.get("DEBIT_NOTE_NO")));
				activeData.setCredit_note_no(
						null == map.get("CREDIT_NOTE_NO") ? "" : String.valueOf(map.get("CREDIT_NOTE_NO")));
				activeData.setAmend_id(null == map.get("AMEND_ID") ? "" : String.valueOf(map.get("AMEND_ID")));
				activeData.setReceipt_no(null == map.get("RECEIPT_NO") ? "" : String.valueOf(map.get("RECEIPT_NO")));
				activeData.setBroker_name(null == map.get("BROKER_NAME") ? "" : String.valueOf(map.get("BROKER_NAME")));
				activeData.setTravelcoverId(
						null == map.get("TRAVEL_COVER_ID") ? "" : String.valueOf(map.get("TRAVEL_COVER_ID")));
				activeData.setModeofpayment(
						null == map.get("MODE_OF_PAYMENT") ? "" : String.valueOf(map.get("MODE_OF_PAYMENT")));
				activeData.setRsa_cardno(null == map.get("RSA_CARDNO") ? "" : String.valueOf(map.get("RSA_CARDNO")));
				activeData.setAaa_cardno(null == map.get("AAA_CARDNO") ? "" : String.valueOf(map.get("AAA_CARDNO")));
				activeData.setReference_no(null == map.get("REQREFNO") ? "" : String.valueOf(map.get("REQREFNO")));
				activeData.setPolicytype(null == map.get("POLICY_TYPE") ? "" : String.valueOf(map.get("POLICY_TYPE")));
				activeData.setVehicletype(
						null == map.get("ISCOMMERCIAL_YN") ? "" : String.valueOf(map.get("ISCOMMERCIAL_YN")));
				activeData.setOrangecardno(null == map.get("ORANGE_CARDNO") ? "" : String.valueOf(map.get("ORANGE_CARDNO")));
				activeData.setOrangecardurl(null == map.get("ORANGE_CARDURL") ? "" : String.valueOf(map.get("ORANGE_CARDURL")));
				modelList.add(activeData);
			}
			return modelList;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<CommonResponse> getCancelPolicy(ExistingRequest req) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		try {
			String cancelPolicyQuery = "";

			String productid = StringUtils.isBlank(req.getProductid()) ? "" : req.getProductid();

			String loginid = "";

			if (StringUtils.isBlank(req.getLoginid())) {
				loginid = branchBasedLoginId(req.getBranchcode());
			} else {
				loginid = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			}

			if ("35".equalsIgnoreCase(productid)) {
				cancelPolicyQuery = commonService.getQuery().getProperty("DOMESTIC_CANCEL_POLICY");
			}else if ("65".equals(productid)) {
				
				// Get Cancelled Policies
				req.setPortfoliostatus("D");
				modelList = motorGridService.getMotorPortFolioDetails(req);
				return modelList;
			}else if ("30".equals(productid)) {
				
				// Get Cancelled Policies
				req.setPortfoliostatus("D");
				modelList = nonGridService.getPortFolioDetails(req);
				return modelList;
			}   else {
				// cancelPolicyQuery = commonService.getQuery().getProperty("CANCEL_POLICY");

			//	mapList = empDtlRawRepo.get_CommonCancelPolicyData(loginid, productid);
			}

			log.info("getCancelPolicy--> loginid: " + loginid + " productid: " + productid);

			if (StringUtils.isNotBlank(cancelPolicyQuery)) {
				Query query = em.createNativeQuery(cancelPolicyQuery);
				query.setParameter(1, productid);
				query.setParameter(2, loginid);

				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				mapList = query.getResultList();
			}

			for (int i = 0; i < mapList.size(); i++) {
				Map<String, Object> map = mapList.get(i);

				CommonResponse cancelData = new CommonResponse();

				String customerName = null == map.get("CUSTOMER_NAME") ? "" : String.valueOf(map.get("CUSTOMER_NAME"));

				cancelData.setPolicy_no(null == map.get("POLICY_NO") ? "" : String.valueOf(map.get("POLICY_NO")));
				cancelData.setStatus(null == map.get("STATUS") ? "" : String.valueOf((map.get("STATUS"))));
				cancelData.setApplication_no(
						null == map.get("APPLICATION_NO") ? "" : String.valueOf(map.get("APPLICATION_NO")));
				cancelData.setCredit_note_no(
						null == map.get("CREDIT_NOTE_NO") ? "" : String.valueOf(map.get("CREDIT_NOTE_NO")));
				cancelData.setCustomer_id(null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
				cancelData.setOverall_premium(
						null == map.get("OVERALL_PREMIUM") ? "" : String.valueOf(map.get("OVERALL_PREMIUM")));
				cancelData.setQuotation_date(
						null == map.get("QUOTATION_DATE") ? "" : String.valueOf(map.get("QUOTATION_DATE")));
				cancelData.setCustomer_name(StringUtils.isBlank(customerName) ? ""
						: customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
				cancelData.setLast_name(null == map.get("LAST_NAME") ? "" : String.valueOf(map.get("LAST_NAME")));
				cancelData.setLogin_id(loginid);
				cancelData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
				cancelData.setExcess_premium(
						null == map.get("EXCESS_PREMIUM") ? "0" : String.valueOf(map.get("EXCESS_PREMIUM")));
				cancelData.setCompany_name(
						null == map.get("COMPANY_NAME") ? "" : String.valueOf(map.get("COMPANY_NAME")));
				cancelData.setInception_date(
						null == map.get("INCEPTION_DATE") ? "" : String.valueOf(map.get("INCEPTION_DATE")));
				cancelData.setOpen_cover_no(
						null == map.get("OPEN_COVER_NO") ? "" : String.valueOf(map.get("OPEN_COVER_NO")));
				cancelData.setDebit_note_no(
						null == map.get("DEBIT_NOTE_NO") ? "" : String.valueOf(map.get("DEBIT_NOTE_NO")));
				cancelData.setAmend_id(null == map.get("AMEND_ID") ? "" : String.valueOf(map.get("AMEND_ID")));
				cancelData.setReceipt_no(null == map.get("RECEIPT_NO") ? "" : String.valueOf(map.get("RECEIPT_NO")));
				cancelData.setBroker_name(null == map.get("BROKER_NAME") ? "" : String.valueOf(map.get("BROKER_NAME")));
				cancelData.setVehicle_id(null == map.get("VEHICLE_ID") ? "" : String.valueOf(map.get("VEHICLE_ID")));
				cancelData.setPolicytype(null == map.get("POLICYTYPE") ? "" : String.valueOf(map.get("POLICYTYPE")));
				cancelData.setVehicletype(
						null == map.get("ISCOMMERCIAL_YN") ? "" : String.valueOf(map.get("ISCOMMERCIAL_YN")));
				cancelData.setReference_no(null == map.get("REQREFNO") ? "" : String.valueOf(map.get("REQREFNO")));
				modelList.add(cancelData);
			}
			return modelList;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CommonResponse> getpendingpoliciesgrid(ExistingRequest req) {
		try {
			List<CommonResponse> response = new ArrayList<CommonResponse>();

			String pendingPolicyQuery = "";

			Query query = null;

			List<Map<String, Object>> list = new ArrayList<>();

			String loginid = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
			String productid = StringUtils.isBlank(req.getProductid()) ? "" : req.getProductid();
			String applicationId = StringUtils.isBlank(req.getApplicationid()) ? "1" : req.getApplicationid();

			if (StringUtils.isBlank(loginid)) {
				loginid = branchBasedLoginId(req.getBranchcode());
			}

			log.info("getpendingpoliciesgrid--> LoginID: " + loginid + " ProductID: " + productid);

			String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
			if ("55555".equalsIgnoreCase(subUserType)) {
				applicationId = "";
			}

			if ("65".equals(productid)) {
				// Get Cancelled Policies
				req.setPortfoliostatus("F");
				response = motorGridService.getMotorPortFolioDetails(req);
				return response;
			/*	pendingPolicyQuery = commonService.getQuery().getProperty("MOTOR_PENDING_POLICY");
				query = em.createNativeQuery(pendingPolicyQuery);
				query.setParameter(1, loginid);
				query.setParameter(2, productid);
				query.setParameter(3, applicationId); */
			}else if ("30".equals(productid)) {
				
				// Get Cancelled Policies
				req.setPortfoliostatus("F");
				response = nonGridService.getPortFolioDetails(req);
				return response;
			} else if ("35".equals(productid)) {
				pendingPolicyQuery = commonService.getQuery().getProperty("DOMESTIC_PENDING_POLICY");
				query = em.createNativeQuery(pendingPolicyQuery);
				query.setParameter(1, loginid);
				query.setParameter(2, productid);
				query.setParameter(3, applicationId);
			} else if ("33".equals(productid)) {
				pendingPolicyQuery = commonService.getQuery().getProperty("TRAVEL_PENDING_POLICY");
				query = em.createNativeQuery(pendingPolicyQuery);
				query.setParameter(1, loginid);
				query.setParameter(2, productid);
			} else {
			//	list = empDtlRawRepo.get_CommonPendingPolicyData(loginid, applicationId, productid);
			}

			if (query != null) {
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				list = query.getResultList();
			}

			log.info("getpendingpoliciesgrid--> Size: " + list.size());

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);

				String customerName = map.get("CUSTOMER_NAME") == null ? "" : map.get("CUSTOMER_NAME").toString();

				CommonResponse resData = CommonResponse.builder()
						.application_no(map.get("APPLICATION_NO") == null ? "" : map.get("APPLICATION_NO").toString())
						.company_name(map.get("COMPANY_NAME") == null ? "" : map.get("COMPANY_NAME").toString())
						.customer_id(map.get("CUSTOMER_ID") == null ? "" : map.get("CUSTOMER_ID").toString())
						.customer_name(StringUtils.isBlank(customerName) ? ""
								: customerName.length() > 100 ? customerName.substring(0, 100) : customerName)
						.inception_date(map.get("INCEPTION_DATE") == null ? "" : map.get("INCEPTION_DATE").toString())
						.login_id(loginid)
						.policy_no(map.get("POLICY_NO") == null ? "" : map.get("POLICY_NO").toString())
						.quotation_date(map.get("QUOTATION_DATE") == null ? "" : map.get("QUOTATION_DATE").toString())
						.quote_no(map.get("QUOTE_NO") == null ? "" : map.get("QUOTE_NO").toString())
						.Reference_no(
								map.get("REQUESTREFERENCENO") == null ? "" : map.get("REQUESTREFERENCENO").toString())
						.email(map.get("EMAIL") == null ? "" : map.get("EMAIL").toString())
						.premium(map.get("PREMIUM") == null ? "" : map.get("PREMIUM").toString())
						.validity_date(map.get("EXPIRY_DATE") == null ? "" : map.get("EXPIRY_DATE").toString())
						.vehicletype(map.get("ISCOMMERCIAL_YN") == null ? "" : map.get("ISCOMMERCIAL_YN").toString())
						.policytype(map.get("POLICY_TYPE") == null ? "" : map.get("POLICY_TYPE").toString()).build();
				response.add(resData);
			}
			return response;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public lapsedQuoteReq getlapsedQuoteInfo(lapsedQuoteReq request) {
		lapsedQuoteReq res = new lapsedQuoteReq();
		String productid = StringUtils.isBlank(request.getProductid()) ? "" : request.getProductid();
		log.info("lapsedQuoteInfo--> productid: " + productid);
		try {
			if (productid.equalsIgnoreCase("65")) {
			//	res.setLapsedQuoteList(omSmsRepo.getLapsedQuotefor65(request.getQuoteno()));

			} else if (productid.equalsIgnoreCase("35")) {
			//	res.setLapsedQuoteList(omSmsRepo.getLapsedQuotefor35(request.getQuoteno()));

			} else if (productid.equalsIgnoreCase("77")) {
			//	res.setLapsedQuoteList(omSmsRepo.getLapsedQuotefor77(request.getQuoteno()));
			} else if (productid.equalsIgnoreCase("75")) {
			//	res.setLapsedQuoteList(omSmsRepo.getLapsedQuotefor75(request.getQuoteno()));
			}else {
			//	res.setLapsedQuoteList(omSmsRepo.getLapsedQuote(request.getQuoteno()));
			}
			
			//res.setLapsedReason(omSmsRepo.getLapsedReason(request.getBranchcode()));
			res.setQuoteNo(request.getQuoteNo());
			res.setBranchCode(request.getBranchCode());
			res.setProductid(request.getProductid());

		} catch (Exception e) {
			log.error(e);
		}
		return res;
	}
	//***************** SEARCHING DETAILS ************************************************
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CommonResponse> getSearchingData(SearchRequest req, List<Error> error) {
		List<CommonResponse> modelList = new ArrayList<CommonResponse>();
		if (error.size() == 0) {

			List<Map<String, Object>> mapList = null;
			try {
				String constant = null;

				if(req.getProductid().equalsIgnoreCase("30")  ) {
					modelList = nonGridService.getSearchedDatas(req);
					return modelList; 
				}else if(req.getProductid().equalsIgnoreCase("65") ) {
					modelList = motorGridService.getMotorSearchedDatas(req);
					return modelList; 
				}
				
				String productId = "", search = "", eserviceSearch = "",
						applicationId = StringUtils.isBlank(req.getApplicationid()) ? "1" : req.getApplicationid();
				productId = req.getProductid();
				if (applicationId.equalsIgnoreCase("Admin")) {
					if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("65")) {
						search = commonService.getQuery().getProperty("SEARCHDATA_ADMIN");
					} else {
						search = commonService.getQuery().getProperty("COMMON_SEARCHDATA_ADMIN");
					}
				} else {
					if (applicationId.equals("1")) {
						if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("65")) {
							search = commonService.getQuery().getProperty("SEARCHDATA");
						} else {
							search = commonService.getQuery().getProperty("COMMON_SEARCHDATA");
						}
					} else {
						if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("65")) {
							search = commonService.getQuery().getProperty("SEARCHDATA_WO_LOGINID");
						} else {
							search = commonService.getQuery().getProperty("COMMON_SEARCHDATA_WO_LOGINID");
						}
					}
				}
				if (applicationId.equalsIgnoreCase("Admin")) {
					if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("65")) {
						eserviceSearch = commonService.getQuery().getProperty("REQ_REF_NO_ADMIN");
					} else if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("35")) {
						eserviceSearch = commonService.getQuery().getProperty("DOMESTIC_REQ_REF_NO_ADMIN");
					}
				} else {
					if (applicationId.equals("1")) {
						if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("65")) {
							eserviceSearch = commonService.getQuery().getProperty("REQ_REF_NO_SEARCH");
						} else if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("35")) {
							eserviceSearch = commonService.getQuery().getProperty("DOMESTIC_REQREFNO_SEARCH");
						}
					} else {
						if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("65")) {
							eserviceSearch = commonService.getQuery().getProperty("REQ_REF_NO_SEARCH_WO_LOGINID");
						} else if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase("35")) {
							eserviceSearch = commonService.getQuery()
									.getProperty("DOMESTIC_REQ_REF_NO_SEARCH_WO_LOGINID");
						}
					}
				}
				String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
				String searchBy = req.getSearchby();
				String searchValue = req.getSearchvalue();
				Query query = null;
				String appId = StringUtils.isBlank(req.getApplicationid()) ? "1" : req.getApplicationid();

				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}

				if (searchBy.equals("QuoteNo")) {
					constant = search + commonService.getQuery().getProperty("QUOTE_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("PolicyNo")) {
					constant = search + commonService.getQuery().getProperty("POLICY_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("CustomerName")) {
					if ("65".equalsIgnoreCase(req.getProductid())) {
						constant = eserviceSearch + commonService.getQuery().getProperty("MOTOR_FIRST_NAME_SEARCH");
					} else {
						constant = search + commonService.getQuery().getProperty("FIRST_NAME_SEARCH");
					}
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("MobileNo")) {
					if ("65".equalsIgnoreCase(req.getProductid())) {
						constant = eserviceSearch + commonService.getQuery().getProperty("MOTOR_MOBILE_SEARCH");
					} else {
						constant = search + commonService.getQuery().getProperty("MOBILE_SEARCH");
					}
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("RequestRefNo")) {
					if ("65".equalsIgnoreCase(req.getProductid())) {
						constant = eserviceSearch + commonService.getQuery().getProperty("E_RequestReferenceNo");
					} else if ("35".equalsIgnoreCase(req.getProductid())) {
						constant = eserviceSearch + commonService.getQuery().getProperty("E_RequestReferenceNo");
					}
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("Civil Id")) {
					constant = search + commonService.getQuery().getProperty("CIVILID_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("AAACard")) {
					constant = search + commonService.getQuery().getProperty("AAACARD_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("RSACard")) {
					constant = search + commonService.getQuery().getProperty("RSACARD_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("OrangeCard")) {
					constant = search + commonService.getQuery().getProperty("ORANGECARD_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("PlateNo")) {

					constant = eserviceSearch + commonService.getQuery().getProperty("E_Plate_Number");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("ChassisNo")) {

					constant = eserviceSearch + commonService.getQuery().getProperty("E_Chassis_No");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("VehicleCategory")) {

					constant = eserviceSearch + commonService.getQuery().getProperty("VEHICLE_CATEGORY_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("VehicleType")) {

					constant = eserviceSearch + commonService.getQuery().getProperty("VEH_TYPE_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("CustomerCode")) {
					constant = search + commonService.getQuery().getProperty("CUSTOMERCODE_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);
				} else if (searchBy.equals("MakeModel")) {

					constant = eserviceSearch + commonService.getQuery().getProperty("MAKE_MODEL_SEARCH");
					query = em.createNativeQuery(constant);
					query.setParameter(1, req.getLoginid());
					query.setParameter(2, req.getProductid());
					query.setParameter(3, appId);
					query.setParameter(4, searchValue);// model name
					query.setParameter(5, req.getSearchvalue1());// make name
				} else if (searchBy.equals("RenewQuotePolicy")) {
					constant = commonService.getQuery().getProperty("RENEW_QUOTE_POLICY_SEARCH");
					query = em.createNativeQuery(constant);

					query.setParameter(1, searchValue);

				}

				if (StringUtils.isNotBlank(constant)) {
					NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
					nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
					mapList = query.getResultList();
				}

				log.info("getSearchingData--> mapList: " + mapList.size());
				for (int i = 0; i < mapList.size(); i++) {
					CommonResponse searchingData = new CommonResponse();

					searchingData.setApplication_no(null == mapList.get(i).get("APPLICATION_NO") ? ""
							: String.valueOf(mapList.get(i).get("APPLICATION_NO")));
					searchingData.setContent_type_id(null == mapList.get(i).get("CONTENT_TYPE_ID") ? ""
							: String.valueOf(mapList.get(i).get("CONTENT_TYPE_ID")));
					searchingData.setQuote_no(null == mapList.get(i).get("QUOTE_NO") ? ""
							: String.valueOf(mapList.get(i).get("QUOTE_NO")));
					searchingData.setCustomer_id(null == mapList.get(i).get("CUSTOMER_ID") ? ""
							: String.valueOf(mapList.get(i).get("CUSTOMER_ID")));
					searchingData.setQuotation_date(null == mapList.get(i).get("QUOTATION_DATE") ? ""
							: String.valueOf(mapList.get(i).get("QUOTATION_DATE")));
					searchingData.setEntry_date(null == mapList.get(i).get("ENTRY_DATE") ? ""
							: String.valueOf(mapList.get(i).get("ENTRY_DATE")));
					searchingData.setValidity_date(null == mapList.get(i).get("VALIDITY_DATE") ? ""
							: String.valueOf(mapList.get(i).get("VALIDITY_DATE")));
					searchingData.setCustomer_name(null == mapList.get(i).get("CUSTOMER_NAME") ? ""
							: String.valueOf(mapList.get(i).get("CUSTOMER_NAME")));
					searchingData.setCompany_name(null == mapList.get(i).get("COMPANY_NAME") ? ""
							: String.valueOf(mapList.get(i).get("COMPANY_NAME")));
					searchingData.setPolicy_start_date(null == mapList.get(i).get("POLICY_START_DATE") ? ""
							: String.valueOf(mapList.get(i).get("POLICY_START_DATE")));
					searchingData.setPremium(null == mapList.get(i).get("PREMIUM") ? "0"
							: String.valueOf(mapList.get(i).get("PREMIUM")));
					searchingData.setEffective_date(null == mapList.get(i).get("EFFECTIVE_DATE") ? ""
							: String.valueOf(mapList.get(i).get("EFFECTIVE_DATE")));
					searchingData.setPolicy_no(null == mapList.get(i).get("POLICY_NO") ? ""
							: String.valueOf(mapList.get(i).get("POLICY_NO")));
					searchingData.setOrg_status(null == mapList.get(i).get("ORG_STATUS") ? ""
							: String.valueOf(mapList.get(i).get("ORG_STATUS")));
					searchingData.setStatus_type(null == mapList.get(i).get("STATUS_TYPE") ? ""
							: String.valueOf(mapList.get(i).get("STATUS_TYPE")));
					searchingData.setStatus_type_name(null == mapList.get(i).get("STATUS_TYPE_NAME") ? ""
							: String.valueOf(mapList.get(i).get("STATUS_TYPE_NAME")));
					searchingData.setPremia_customer_name(mapList.get(i).get("PREMIA_CUSTOMER_NAME") == null ? ""
							: mapList.get(i).get("PREMIA_CUSTOMER_NAME").toString());
					searchingData.setBroker_company_name(mapList.get(i).get("BROKER_COMPANY_NAME") == null ? ""
							: mapList.get(i).get("BROKER_COMPANY_NAME").toString());
					searchingData.setLogin_id(
							mapList.get(i).get("LOGIN_ID") == null ? "" : mapList.get(i).get("LOGIN_ID").toString());
					searchingData.setBranchname(null == mapList.get(i).get("BRANCH_NAME") ? ""
							: mapList.get(i).get("BRANCH_NAME").toString());
					searchingData.setReference_no(null == mapList.get(i).get("REQREFNO") ? ""
							: String.valueOf(mapList.get(i).get("REQREFNO")));
					searchingData.setVehicletype(null == mapList.get(i).get("ISCOMMERCIAL_YN") ? ""
							: String.valueOf(mapList.get(i).get("ISCOMMERCIAL_YN")));
					searchingData.setPolicytype(null == mapList.get(i).get("POLICY_TYPE") ? ""
							: String.valueOf(mapList.get(i).get("POLICY_TYPE")));
					searchingData.setExpiry_date(null == mapList.get(i).get("EXPIRY_DATE") ? ""
							: String.valueOf(mapList.get(i).get("EXPIRY_DATE")));
					searchingData.setMobileno(
							null == mapList.get(i).get("MOBILE") ? "" : String.valueOf(mapList.get(i).get("MOBILE")));
					modelList.add(searchingData);
				}
				return modelList;
			} catch (Exception e) {
				log.error(e);
			}
		} else {
			CommonResponse searchingData = new CommonResponse();

			searchingData.setStatus("false");
			searchingData.setErrors(error);
			modelList.add(searchingData);

			return modelList;
		}
		return null;
	}

	@Override
	public List<DropDownResponse> getSearchByList(DropDownReq req) {
		List<DropDownResponse> modelList = new ArrayList<DropDownResponse>();
		try {
		/*	List<MarineConstantDetail> mapList = new ArrayList<MarineConstantDetail>();

			String type = StringUtils.isBlank(req.getType()) ? "" : req.getType();

			if ("CopyQuote".equalsIgnoreCase(type)) {
				mapList = commonService.getMarineConstantDetails("COPY QUOTE", req.getProductId());
			} else if ("ISSUER".equalsIgnoreCase(type)) {
				mapList = commonService.getMarineConstantDetails("SEARCH", req.getProductId());
			} else {
				mapList = commonService.getMarineConstantDetails(type, req.getProductId());
			}

			for (int i = 0; i < mapList.size(); i++) {
				DropDownResponse data = new DropDownResponse();
				data.setCode(null == mapList.get(i).getDetailname() ? "" : mapList.get(i).getDetailname());
				data.setCodedesc(null == mapList.get(i).getRemarks() ? "" : mapList.get(i).getRemarks());
				modelList.add(data);
			}
			return modelList; */
			
			if(req.getProductId().equalsIgnoreCase("30")    ) {
				String type = StringUtils.isBlank(req.getType()) ? "" : req.getType();
				String itemType = "SEARCH";
				
			/*	if("SEARCH".equalsIgnoreCase(type) ) {
					//Search Data DropDown
					itemType = type ;
				}else if("Copy_Quote".equalsIgnoreCase(type)) {
					// Copy Quote DropDown
					itemType = type ;
				}else {
					//Other DropDown
					itemType = type ;
				} */
				List<ListItemValue>  list = listRepo.findByItemTypeAndStatusOrderByItemValue(itemType, "Y");
				
				for(ListItemValue data : list  ) {
					DropDownResponse res = new DropDownResponse();
					res.setCode(data.getItemCode());
					res.setCodedesc(data.getItemValue());
					modelList.add(res);
				}
				return modelList;
			}else if(req.getProductId().equalsIgnoreCase("65")    ) {
				String type = StringUtils.isBlank(req.getType()) ? "" : req.getType();
				String itemType = "MOTOR_SEARCH";
				
			/*	if("MOTOR_SEARCH".equalsIgnoreCase(type) ) {
					//Search Data DropDown
					itemType = type ;
				}else if("MOTOR_Copy_Quote".equalsIgnoreCase(type)) {
					// Copy Quote DropDown
					itemType = type ;
				}else {
					//Other DropDown
					itemType = type ;
				} */
				List<ListItemValue>  list = listRepo.findByItemTypeAndStatusOrderByItemValue(itemType, "Y");
				
				for(ListItemValue data : list  ) {
					DropDownResponse res = new DropDownResponse();
					res.setCode(data.getItemCode());
					res.setCodedesc(data.getItemValue());
					modelList.add(res);
				}
				return modelList;
			}
			
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	//***************** COPY QUOTE ************************************************
	@Override
	public CommonResponse getCopyQuoteData(ExistingRequest req) {
		CommonResponse res = new CommonResponse();
		if( "65".equalsIgnoreCase(req.getProductid())) {
			res = motorGridService.motorCopyQuoteDetails(req);
		//	res = copyQuoteMotor(req);
		}else if("30".equalsIgnoreCase(req.getProductid()) ) {
			res = nonGridService.copyQuoteDetails(req);
		}
		return res;
	}
	private CommonResponse copyQuoteMotor(ExistingRequest req) {
		CommonResponse comRes = new CommonResponse();
		//QuoteDetails quote = new QuoteDetails();
		try {
			/*		if("RequestRefNo".equals(req.getCopyquoteby())) {
				quote= quoteRepo.getOne(req.getCopyquotevalue());
			}else if("QuoteNo".equals(req.getCopyquoteby())) {
				quote = quoteRepo.findByQuote_no(req.getCopyquotevalue());
			}else if("PolicyNo".equals(req.getCopyquoteby())) {
				Map<String,Object> data = quoteRepo.findByPolicyNo(req.getCopyquotevalue());
				String quoteno = null==data.get("QUOTE_NO")?"":data.get("QUOTE_NO").toString();
				quote = quoteRepo.findByQuote_no(quoteno);
			}
			
				QuoteDetails data = QuoteDetails.builder()
						.requestreferenceno(quoteRepo.getReqRefNo()) 
						.login_id(null==quote.getLogin_id()?null:quote.getLogin_id())
						.application_id(null==quote.getApplication_id()?null:quote.getApplication_id())//issuer == null?1:issuer
						.agency_code(null==quote.getAgency_code()?null:quote.getAgency_code()) // from Login Master
						.broker_code(null==quote.getBroker_code()?null:quote.getBroker_code()) // from Login master
						.executive_id(null==quote.getExecutive_id()?null:quote.getExecutive_id()).
						status("Y")//null==quote.getStatus()?null:quote.getStatus()
					//	.quote_no(null==quote.getQuote_no()?null:Long.valueOf(quote.getQuote_no())) // req emp
						//.application_no(null==quote.getApplication_no()?null:quote.getApplication_no()) // req emp
						//.customer_id(null==quote.getCustomer_id()?null:Long.valueOf(quote.getCustomer_id())) //req emp
						//.policy_no(null==quote.getPolicy_no()?null:quote.getPolicy_no())//req emp
						.product_id(65L)
						.quote_created_date(new Date()).entry_date(new Date())
						.ebroker_id(100L)
						.branch_code(null==quote.getBranch_code()?"":quote.getBranch_code())
						.channel_list(null==quote.getChannel_list()?null:quote.getChannel_list())
						.chassisno(null==quote.getChassisno()?null:quote.getChassisno())
						.source_type(null==quote.getSource_type()?null:quote.getSource_type())
						.customer_code(null==quote.getCustomer_code()?null:quote.getCustomer_code())
						.customer_codename(null==quote.getCustomer_codename()?null:quote.getCustomer_codename())
						.bdm_code(null==quote.getBdm_code()?null:quote.getBdm_code())
						//CustomerInfo
						.customerDetails(saveCustomerInfo(quote))
						//VehicleDetails
						.vehicleDetails(saveVehicleInfo(quote))
					.build();
				quoteRepo.save(data);
				log.info("Request Reference No : " + data.getRequestreferenceno());
				log.info("Old Request Reference Number : " + quote.getRequestreferenceno());
				log.info("Generated Request Reference Number: "+data.getRequestreferenceno());
				
				comRes.setReference_no(data.getRequestreferenceno());
				comRes.setOldreferenceno(quote.getRequestreferenceno());*/
				return comRes;
		}catch(Exception e) {
			List<Error> error = new ArrayList<Error>();
			Error errorList = new Error();
			errorList.setCode("1");
			errorList.setField("Copy Quote");
			errorList.setMessage("Error While inserting Quote Details");
			error.add(errorList);
			comRes.setErrors(error);
			return comRes; 
		}
	}
	
	//CUSTOMER_INFO 
/*		private CustomerDetails saveCustomerInfo(QuoteDetails req) {
			try {
				CustomerDetails cus_Info = CustomerDetails.builder()
						.policyholderid(null==req.getCustomerDetails().getPolicyholderid()?null:req.getCustomerDetails().getPolicyholderid())
						.previousownertitle(null==req.getCustomerDetails().getPreviousownertitle()?null:req.getCustomerDetails().getPreviousownertitle())
						.previousowneriqamaid("0").transferowner("N")
						.dateofbirthg(null==req.getCustomerDetails().getDateofbirthg()?null:req.getCustomerDetails().getDateofbirthg())
						.gender(null==req.getCustomerDetails().getGender()?null:req.getCustomerDetails().getGender())
						.insured_name(null==req.getCustomerDetails().getInsured_name()?null:req.getCustomerDetails().getInsured_name())
						.joint_name(null==req.getCustomerDetails().getJoint_name()?null:req.getCustomerDetails().getJoint_name())
						.mobile_code(null==req.getCustomerDetails().getMobile_code()?null:req.getCustomerDetails().getMobile_code())
						.mobile(null==req.getCustomerDetails().getMobile()?null:req.getCustomerDetails().getMobile())
						.email(null==req.getCustomerDetails().getEmail()?null:req.getCustomerDetails().getEmail())
						.ownerdriveryn("Y")//req.getCustomerInfo().getOwnerAsDriver()
						.occupation(null==req.getCustomerDetails().getOccupation()?null:Long.valueOf(req.getCustomerDetails().getOccupation()))
						.otheroccupation(null==req.getCustomerDetails().getOtheroccupation()?null:req.getCustomerDetails().getOtheroccupation())
						.nationality(null==req.getCustomerDetails().getNationality()?null:req.getCustomerDetails().getNationality())
						.visa(null==req.getCustomerDetails().getVisa()?null:req.getCustomerDetails().getVisa())
						.wilayat(null==req.getCustomerDetails().getWilayat()?null:req.getCustomerDetails().getWilayat())
						.whatsapp_code(null==req.getCustomerDetails().getWhatsapp_code()?null:req.getCustomerDetails().getWhatsapp_code())
						.whatsapp_no(null==req.getCustomerDetails().getWhatsapp_no()?null:req.getCustomerDetails().getWhatsapp_no())
					.build();
			
				return cus_Info;
			}catch (Exception e) {log.error(e);}
			return null;
	} */
		
		//VEHICLE INFO
		
/*		private VehicleDetails saveVehicleInfo(QuoteDetails req) {
			try {
				VehicleDetails reqData = req.getVehicleDetails();
				
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(new Date());
				 cal.add(Calendar.DATE, 1);
				
				VehicleDetails veh_info = VehicleDetails.builder()
							.policystartdate(null==reqData.getPolicystartdate()?null:reqData.getPolicystartdate())
							.policytype(null ==reqData.getPolicytype()?null:reqData.getPolicytype())
							.suminsured(null==reqData.getSuminsured()?null:reqData.getSuminsured())
							.customsid(null==reqData.getCustomsid()?null:reqData.getCustomsid())
							.sequenceno(null==reqData.getSequenceno()?null:reqData.getSequenceno())
							.veh_make_id(null==reqData.getVeh_make_id()?null:reqData.getVeh_make_id())
							.veh_model_id(null==reqData.getVeh_model_id()?null:reqData.getVeh_model_id())
							.veh_manf_country(null==reqData.getVeh_manf_country()?null:reqData.getVeh_manf_country())
							.insurancecompanycode(null==reqData.getInsurancecompanycode()?null:reqData.getInsurancecompanycode())
							.veh_weight(null==reqData.getVeh_weight()?null:reqData.getVeh_weight())
							.veh_cc(null==reqData.getVeh_cc()?null:reqData.getVeh_cc())
							.veh_seating(null==reqData.getVeh_seating()?null:reqData.getVeh_seating())
							.no_of_claims(null==reqData.getNo_of_claims()?null:reqData.getNo_of_claims())
							.no_claim_bonus(null==reqData.getNo_claim_bonus()?null:reqData.getNo_claim_bonus())
							.claimed_amount(null==reqData.getClaimed_amount()?null:reqData.getClaimed_amount())
							.manfactureyear(null==reqData.getManfactureyear()?null:reqData.getManfactureyear())
							.veh_regn_date(null==reqData.getVeh_regn_date()?null:reqData.getVeh_regn_date())
							.excess(null==reqData.getExcess()?null:reqData.getExcess())
							.havepromocode(null==reqData.getHavepromocode()?null:reqData.getHavepromocode())
							.quote_type(null==reqData.getQuote_type()?null:reqData.getQuote_type())
							.promocode(null==reqData.getPromocode()?null:reqData.getPromocode().toUpperCase())
							.gcc_type(null==reqData.getGcc_type()?null:reqData.getGcc_type())
							.veh_parking_type(null==reqData.getVeh_parking_type()?null:reqData.getVeh_parking_type())
							.ph_ncb(null==reqData.getPh_ncb()?null:reqData.getPh_ncb())
							.loyal_flag(null==reqData.getLoyal_flag()?null:reqData.getLoyal_flag())
							.loyalty_percent(null==reqData.getLoyalty_percent()?null:reqData.getLoyalty_percent())
							.claim_yn(null==reqData.getClaim_yn()?null:reqData.getClaim_yn())
							.claim_ratio(null==reqData.getClaim_ratio()?null:reqData.getClaim_ratio())
							.veh_chassis_no(null==reqData.getVeh_chassis_no()?null:reqData.getVeh_chassis_no())
							.veh_plate_char(null==reqData.getVeh_plate_char()?null:reqData.getVeh_plate_char())
							.veh_plate_number(null==reqData.getVeh_plate_number()?null:reqData.getVeh_plate_number())
							.veh_type_id(null==reqData.getVeh_type_id()?null:reqData.getVeh_type_id())
							.veh_engine_no(null==reqData.getVeh_engine_no()?null:reqData.getVeh_engine_no())
							.veh_body_id(null==reqData.getVeh_body_id()?null:reqData.getVeh_body_id())
							.import_yn(null==reqData.getImport_yn()?null:reqData.getImport_yn())
							.import_code(null==reqData.getImport_code()?null:reqData.getImport_code())
							.vehicle_id(null==reqData.getVehicle_id()?null:reqData.getVehicle_id())
							.veh_cylinders(null==reqData.getVeh_cylinders()?null:reqData.getVeh_cylinders())
							.scheme_emp_code(null==reqData.getScheme_emp_code()?null:reqData.getScheme_emp_code())
							.price_id(null==reqData.getPrice_id()?null:reqData.getPrice_id())
							.policyenddate(null==reqData.getPolicyenddate()?null:reqData.getPolicyenddate())
							.vehicle_condition(null==reqData.getVehicle_condition()?null:reqData.getVehicle_condition())
							.vehicle_mileage(null==reqData.getVehicle_mileage()?null:reqData.getVehicle_mileage())
							.vehicle_usage_id(null==reqData.getVehicle_usage_id()?null:reqData.getVehicle_usage_id())
							.model_name(null==reqData.getModel_name()?null:reqData.getModel_name())
							.iscommercial_yn(null==reqData.getIscommercial_yn()?null:reqData.getIscommercial_yn())
							.curr_year_mileage(null==reqData.getCurr_year_mileage()?null:reqData.getCurr_year_mileage())
							.no_of_driver(null==reqData.getNo_of_driver()?null:reqData.getNo_of_driver())
							.noofdays(null==reqData.getNoofdays()?null:reqData.getNoofdays())
							.plate_nationalty(null==reqData.getPlate_nationalty()?null:reqData.getPlate_nationalty())
							.volume_discount(null==reqData.getVolume_discount()?null:reqData.getVolume_discount())
							.no_of_yearlicense(null==reqData.getNo_of_yearlicense()?null:reqData.getNo_of_yearlicense())
						.build();
				
				/*.grey_import_yn("")
				 * .claims_loading("")
				 * .volume_discount("")
				.renewal_claimamt("")
				.renewal_policyno("")
				.renewal_yn("")*/
		/*		return veh_info;
			}catch (Exception e) {log.error(e);}
			return null;	
		} */
		
		@Override
	public lapsedQuoteReq updatelapsedQuote(lapsedQuoteReq request, List<Error> validate) {
		lapsedQuoteReq res = new lapsedQuoteReq();
		String productid = StringUtils.isBlank(request.getProductid()) ? "" : request.getProductid();
		log.info("updatelapsedQuote--> productid: " + productid);
		try {
			if (validate.size() == 0) {
				res.setStatus(true);
				res.setErrors(null);
				if (productid.equalsIgnoreCase("65")) {
					res  = motorGridService.updateLapsedQuote(request);
				//	omSmsRepo.updatelapsedQuotefor65(request.getLapsedremarks(), request.getQuoteno());
				} else if (productid.equalsIgnoreCase("30")) {
					// Update Lapsed Quote
					res  = nonGridService.updateLapsedQuote(request);
					
				} else if (productid.equalsIgnoreCase("35")) {
				//	omSmsRepo.updatelapsedDomesticEmpQuotefor35(request.getLapsedremarks(), request.getQuoteno());
				//	omSmsRepo.updatelapsedDomesticHomeQuotefor35(request.getLapsedremarks(), request.getQuoteno());
				} else if (productid.equalsIgnoreCase("77")) {
				//	omSmsRepo.updatelapsedQuotefor77(request.getLapsedremarks(), request.getQuoteno());
				} else if (productid.equalsIgnoreCase("75")) {
				//	omSmsRepo.updatelapsedQuotefor75(request.getLapsedremarks(), request.getQuoteno());
				}else {
				//	omSmsRepo.updatelapsedQuote(request.getLapsedremarks(), request.getLoginid(), request.getQuoteno());
				}
				if (!(productid.equalsIgnoreCase("75"))) {
				//	omSmsRepo.updatelapsedQuoteforAll(request.getLapsedremarks(), request.getQuoteno());
				}

			} else {
				res.setStatus(false);
				res.setErrors(validate);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return res;
	}
		
		@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
		@Override
	public List<DropDownResponse> listItemDropDown(DropDownReq req) {
		Session ses = null;
		List<Map<String, Object>> mapList = new ArrayList<>();
		
		String constant = null, productid = StringUtils.isBlank(req.getProductId()) ? "" : req.getProductId();
		String loginid = StringUtils.isBlank(req.getLoginId()) ? "" : req.getLoginId();
		
		List<DropDownResponse> modelList = new ArrayList<DropDownResponse>();
		Query query = null;

		log.info("listItemDropDown -- Product id: " + productid + " Loginid: " + loginid + " Type: "
				+ req.getType());
		try {
			if ("LISTITEM".equals(req.getType()) && "4".equals(req.getRemarks())
					&& "RELATIONSHIP".equals(req.getParam())) {
				constant = commonService.getQuery().getProperty("LISTITEM_REMARKS"); 
				query = em.createNativeQuery(constant);
				query.setParameter(1, req.getParam());
			} else if ("LISTITEM".equals(req.getType())) {
				constant = commonService.getQuery().getProperty("LIST_ITEM");
				query = em.createNativeQuery(constant);
				query.setParameter(1, req.getParam());
			} else if ("AGE_BAND".equals(req.getType())) {

				constant = commonService.getQuery().getProperty("LIST_ITEM");
				
				query = em.createNativeQuery(constant);
				query.setParameter(1, req.getType());
			} else if ("COUNTRY".equals(req.getType())) {

				//mapList = omSmsRepo.mapList();
				if ("1".equalsIgnoreCase(req.getParam()) || "2".equalsIgnoreCase(req.getParam())) {

				//	mapList = omSmsRepo.mapList1r2(req.getParam());
				} else if ("3".equalsIgnoreCase(req.getParam())) {

				//	mapList = omSmsRepo.mapList3(req.getParam());
				}
				constant = "";
			} else if ("TRAVELCOVER".equals(req.getType())) {
				constant = commonService.getQuery().getProperty("TRAVELCOVER");
				if ("2".equals(req.getParam())) {
					constant += commonService.getQuery().getProperty("TRAVEL_COVER");
				}
				query = em.createNativeQuery(constant);
			} else if ("NATIONALITY".equals(req.getType())) {
				constant = commonService.getQuery().getProperty("NATIONALITY");
				query = em.createNativeQuery(constant);
			} else if ("CITY".equals(req.getType())) {
				constant = commonService.getQuery().getProperty("CITY");
				query = em.createNativeQuery(constant);

			} else if ("BrokerList".equals(req.getType()) || "executive".equals(req.getType())) {
				constant = commonService.getQuery().getProperty("GET_QUOTATION_DATA");

				query = em.createNativeQuery(constant);
				query.setParameter(1, req.getType());
				query.setParameter(2, req.getProductId());
				query.setParameter(3, req.getBranchcode());
				query.setParameter(4, "");
				query.setParameter(5, "");
				query.setParameter(6, "");
				query.setParameter(7, "");
				query.setParameter(8, "");
				query.setParameter(9, "");
				query.setParameter(10, "");
				query.setParameter(11, "");
				query.setParameter(12, loginid);
				query.setParameter(13, req.getBrokerCode());

			} else if ("ISSUER".equals(req.getType())) {
				constant = commonService.getQuery().getProperty("RSAISSUER");
				query = em.createNativeQuery(constant);
				query.setParameter(1, req.getProductId());
			} else if ("reportbroker".equals(req.getType())) {
				if (productid.equalsIgnoreCase("65")) {
				//	String appId = omSmsRepo.getAppId(loginid);
			/*		if (appId.equals("1")) {
						constant = "SELECT USERNAME||'('||USERTYPE||')' CODE , USERNAME CODEDESC,LOGIN_ID FROM LOGIN_MASTER WHERE OA_CODE IN(SELECT OA_CODE FROM LOGIN_MASTER WHERE LOGIN_ID = '"
								+ loginid + "')  AND STATUS='Y'";
						query = em.createNativeQuery(constant);
					} else {
						constant = commonService.getQuery().getProperty("ISSUER_REPORT_LIST");
						query = em.createNativeQuery(constant);
						query.setParameter(1, loginid);
					} */

					//omSmsRepo.getReportBrokerList(req.getLoginId());
				} else if (productid.equalsIgnoreCase("77")) {
				//	String appid = empDtlRawRepo.get_Appid(loginid);

				/*	if (appid.equals("1")) {
						mapList = empDtlRawRepo.get_reportBrokerU(loginid);
					} else {
						mapList = empDtlRawRepo.get_reportBrokerI(loginid);
					} */

				} else {
					constant = "SELECT (CASE WHEN APPLICATION_ID = '1' AND CUSTOMER_LOGIN_ID IS NOT NULL THEN (CUSTOMER_LOGIN_ID) ELSE LOGIN_ID END) || '(' || (SELECT USERTYPE FROM LOGIN_MASTER WHERE LOGIN_ID = PI.LOGIN_ID) || ')' CODE, (CASE WHEN APPLICATION_ID = '1' AND CUSTOMER_LOGIN_ID IS NOT NULL THEN (CUSTOMER_LOGIN_ID) ELSE LOGIN_ID END) CODEDESC FROM PERSONAL_INFO PI WHERE (LOGIN_ID IN (SELECT LOGIN_ID FROM LOGIN_MASTER WHERE OA_CODE IN (SELECT OA_CODE FROM LOGIN_MASTER WHERE LOGIN_ID ='"
							+ loginid
							+ "')) OR CUSTOMER_LOGIN_ID IN (SELECT LOGIN_ID FROM LOGIN_MASTER WHERE OA_CODE IN (SELECT OA_CODE FROM LOGIN_MASTER WHERE LOGIN_ID ='"
							+ loginid
							+ "'))) AND LOGIN_ID != 'NONE' AND APPLICATION_ID IN ('2', '3', '5') AND OA_CODE IS NOT NULL AND AGENCY_CODE IS NOT NULL ORDER BY APPLICATION_ID";
					query = em.createNativeQuery(constant);
					//omSmsRepo.getReportBrokerList(req.getLoginId());
				}
			} else if ("user".equals(req.getType())) {
				if (productid.equalsIgnoreCase("65")) {
					if (StringUtils.isNotBlank(req.getIssuer()) && (!("1".equalsIgnoreCase(req.getIssuer())))) {
						constant = commonService.getQuery().getProperty("MOTOR_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getBranchcode());
						query.setParameter(2, loginid);
						query.setParameter(3, req.getProductId());
					} else if (StringUtils.isBlank(req.getIssuer()) || "1".equalsIgnoreCase(req.getIssuer())) {
						constant = "SELECT * FROM (SELECT LOGIN_ID CODE ,USERNAME CODEDESC FROM LOGIN_MASTER WHERE  OA_CODE = (SELECT OA_CODE  FROM LOGIN_MASTER WHERE LOGIN_ID =?1 AND USERTYPE != 'Customer' AND USERTYPE != 'Freight') AND USERTYPE != 'Freight' AND STATUS='Y' AND LOGIN_ID IN ( SELECT DISTINCT (A.LOGIN_ID)  FROM ESERVICE_REQUEST_DETAIL A, PERSONAL_INFO B  WHERE  (A.CUSTOMER_ID = B.CUSTOMER_ID OR A.CUSTOMER_ID IS NULL) AND (B.STATUS = 'Y' OR B.STATUS IS NULL)) ) order by CODEDESC";
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getLoginId());
						
					}
				} else if (productid.equalsIgnoreCase("41")) {
					if (StringUtils.isNotBlank(req.getIssuer())) {
						constant = commonService.getQuery().getProperty("RSA_DROPDOWN_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getBranchcode());
						query.setParameter(2, loginid);
						query.setParameter(3, req.getProductId());

					} else {
						constant = "SELECT * FROM (SELECT LOGIN_ID CODE ,USERNAME CODEDESC FROM LOGIN_MASTER WHERE  OA_CODE = (SELECT OA_CODE  FROM LOGIN_MASTER WHERE LOGIN_ID =? AND USERTYPE != 'Customer' AND USERTYPE != 'Freight') AND USERTYPE != 'Freight' AND STATUS='Y' AND LOGIN_ID IN ( SELECT DISTINCT (A.LOGIN_ID)  FROM RSA_MASTER_DETAILS A, PERSONAL_INFO B  WHERE  (A.CUSTOMER_ID = B.CUSTOMER_ID OR A.CUSTOMER_ID IS NULL) AND (B.STATUS = 'Y' OR B.STATUS IS NULL)) ) order by CODEDESC";
						query = em.createNativeQuery(constant);
						query.setParameter(1, loginid);
						// query.setParameter(2, req.getBranchcode());
						// query.setParameter(3, req.getProductId());
					}
				} else if (productid.equalsIgnoreCase("35")) {
					if (StringUtils.isNotBlank(req.getIssuer()) && (!("1".equalsIgnoreCase(req.getIssuer())))) {//
						constant = commonService.getQuery().getProperty("DOMESTIC_ISSUER_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getBranchcode());
						query.setParameter(2, loginid);
						query.setParameter(3, req.getProductId());
					} else if (StringUtils.isBlank(req.getIssuer()) || "1".equalsIgnoreCase(req.getIssuer())) {
						constant = commonService.getQuery().getProperty("DOMESTIC_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getLoginId());
					}
				} else if (productid.equalsIgnoreCase("40")) {
					if (StringUtils.isNotBlank(req.getIssuer()) && (!("1".equalsIgnoreCase(req.getIssuer())))) {//
						constant = commonService.getQuery().getProperty("YACHT_ISSUER_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getBranchcode());
						query.setParameter(2, loginid);
						query.setParameter(3, req.getProductId());
					} else if (StringUtils.isBlank(req.getIssuer()) || "1".equalsIgnoreCase(req.getIssuer())) {
						constant = commonService.getQuery().getProperty("YACHT_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getLoginId());
						query.setParameter(2, req.getProductId());
						
					}
				} else if (productid.equalsIgnoreCase("77")) {
					if (StringUtils.isNotBlank(req.getIssuer()) && !"1".equals(req.getIssuer())) {
				//		mapList = empDtlRawRepo.get_existingBrokerI(req.getBranchcode(), loginid, productid);
					} else if (StringUtils.isBlank(req.getIssuer()) || "1".equalsIgnoreCase(req.getIssuer())) {
				//		mapList = empDtlRawRepo.get_existingBrokerU(loginid);
					}
				}else if (productid.equalsIgnoreCase("75")) {
					if (StringUtils.isNotBlank(req.getIssuer()) && (!("1".equalsIgnoreCase(req.getIssuer())))) {
						constant = commonService.getQuery().getProperty("FLEET_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getBranchcode());
						query.setParameter(2, loginid);
						query.setParameter(3, req.getProductId());
					} else if (StringUtils.isBlank(req.getIssuer()) || "1".equalsIgnoreCase(req.getIssuer())) {
						constant = "SELECT * FROM (SELECT LOGIN_ID CODE ,USERNAME CODEDESC FROM LOGIN_MASTER WHERE  OA_CODE = (SELECT OA_CODE  FROM LOGIN_MASTER WHERE LOGIN_ID =?1 AND USERTYPE != 'Customer' AND USERTYPE != 'Freight') AND USERTYPE != 'Freight' AND STATUS='Y' AND LOGIN_ID IN ( SELECT DISTINCT (A.LOGIN_ID)  FROM FLEET_REQUEST_DETAIL A, PERSONAL_INFO B  WHERE  (A.CUSTOMER_ID = B.CUSTOMER_ID OR A.CUSTOMER_ID IS NULL) AND (B.STATUS = 'Y' OR B.STATUS IS NULL)) ) order by CODEDESC";
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getLoginId());
						
					}
				}else {
					if (StringUtils.isNotBlank(req.getIssuer())) {
						constant = commonService.getQuery().getProperty("TRAVEL_DROPDOWN_BROKER");
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getBranchcode());
						query.setParameter(2, req.getLoginId());
						query.setParameter(3, req.getProductId());

					} else {
						constant = "SELECT * FROM (SELECT LOGIN_ID CODE ,USERNAME CODEDESC FROM LOGIN_MASTER WHERE  OA_CODE = (SELECT OA_CODE  FROM LOGIN_MASTER WHERE LOGIN_ID =? AND USERTYPE != 'Customer' AND USERTYPE != 'Freight') AND USERTYPE != 'Freight' AND STATUS='Y' AND LOGIN_ID IN ( SELECT DISTINCT (A.LOGIN_ID)  FROM TRAVEL_RAW_DETAIL A, PERSONAL_INFO B  WHERE  (A.CUSTOMER_ID = B.CUSTOMER_ID OR A.CUSTOMER_ID IS NULL) AND (B.STATUS = 'Y' OR B.STATUS IS NULL)) ) order by CODEDESC";
						query = em.createNativeQuery(constant);
						query.setParameter(1, req.getLoginId());

					}
				}
			} else if ("CopyQuote".equals(req.getType())) {
				constant = commonService.getQuery().getProperty("COPYQUOTE");
				query = em.createNativeQuery(constant);
				query.setParameter(1, req.getProductId());
			}

			if (StringUtils.isNotBlank(constant)) {

				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				mapList = query.getResultList();
			}

			for (int i = 0; i < mapList.size(); i++) {
				DropDownResponse data = new DropDownResponse();

				data.setCode(null == mapList.get(i).get("CODE") ? "" : String.valueOf(mapList.get(i).get("CODE")));
				data.setCodedesc(
						null == mapList.get(i).get("CODEDESC") ? "" : String.valueOf(mapList.get(i).get("CODEDESC")));
				data.setItemdesc(
						null == mapList.get(i).get("ITEM_DESC") ? "" : String.valueOf(mapList.get(i).get("ITEM_DESC")));
				data.setParam1(
						null == mapList.get(i).get("PARAM1") ? "" : String.valueOf(mapList.get(i).get("PARAM1")));
				data.setParam2(
						null == mapList.get(i).get("PARAM2") ? "" : String.valueOf(mapList.get(i).get("PARAM2")));
				data.setCountryid(null == mapList.get(i).get("COUNTRY_ID") ? ""
						: String.valueOf(mapList.get(i).get("COUNTRY_ID")));
				data.setCountryname(null == mapList.get(i).get("COUNTRY_NAME") ? ""
						: String.valueOf(mapList.get(i).get("COUNTRY_NAME")));
				modelList.add(data);
			}
			return modelList;
		} catch (Exception e) {
			log.error(e);
		}

		return null;
	}

		//HOD Confirm Status
		@Override
		public List<CommonResponse> getHODApprovedData(ExistingRequest req) {
			List<CommonResponse> approveRes = new ArrayList<>();
			try {
				Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
				String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
				String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

				log.info("HOD Referral Confirm ApprovedData---loginId: " + loginId + " appId: " + appId + " productId: "
						+ productId);

				if (StringUtils.isBlank(req.getLoginid())) {
					loginId = branchBasedLoginId(req.getBranchcode());
				}

				String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}

				List<Map<String, Object>> approveData = new ArrayList<>();

				if (productId == 75) {
				//	approveData = omSmsRepo.getFleetApproveNonApproveData(loginId, appId, productId,"Y");
				} 
				approveRes = hodapproveNonApproveRes(approveData, loginId);

				return approveRes;
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}

		@Override
		public List<CommonResponse> getHODNonApprovedData(ExistingRequest req) {
			List<CommonResponse> approveRes = new ArrayList<>();
			try {
				Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
				String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
				String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

				log.info("HOD Referral Confirm ApprovedData---loginId: " + loginId + " appId: " + appId + " productId: "
						+ productId);

				if (StringUtils.isBlank(req.getLoginid())) {
					loginId = branchBasedLoginId(req.getBranchcode());
				}

				String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}

				List<Map<String, Object>> approveData = new ArrayList<>();

				if (productId == 75) {
				//	approveData = omSmsRepo.getFleetApproveNonApproveData(loginId, appId, productId,"N");
				} 
				approveRes = hodapproveNonApproveRes(approveData, loginId);

				return approveRes;
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}

		@Override
		public List<CommonResponse> getHODPendingAppovedData(ExistingRequest req) {
			List<CommonResponse> approveRes = new ArrayList<>();
			try {
				Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
				String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
				String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

				log.info("HOD Referral Confirm ApprovedData---loginId: " + loginId + " appId: " + appId + " productId: "
						+ productId);

				if (StringUtils.isBlank(req.getLoginid())) {
					loginId = branchBasedLoginId(req.getBranchcode());
				}

				String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}

				List<Map<String, Object>> approveData = new ArrayList<>();

				if (productId == 75) {
				//	approveData = omSmsRepo.getFleetApproveNonApproveData(loginId, appId, productId,"P");
				} 
				approveRes = hodapproveNonApproveRes(approveData, loginId);

				return approveRes;
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}

		@Override
		public List<CommonResponse> getHODReQuoteData(ExistingRequest req) {
			List<CommonResponse> approveRes = new ArrayList<>();
			try {
				Long productId = StringUtils.isBlank(req.getProductid()) ? 0 : Long.valueOf(req.getProductid());
				String loginId = StringUtils.isBlank(req.getLoginid()) ? "" : req.getLoginid();
				String appId = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();

				log.info("HOD Referral Confirm ApprovedData---loginId: " + loginId + " appId: " + appId + " productId: "
						+ productId);

				if (StringUtils.isBlank(req.getLoginid())) {
					loginId = branchBasedLoginId(req.getBranchcode());
				}

				String subUserType = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();
				if ("55555".equalsIgnoreCase(subUserType)) {
					appId = "";
				}

				List<Map<String, Object>> approveData = new ArrayList<>();

				if (productId == 75) {
				//	approveData = omSmsRepo.getFleetApproveNonApproveData(loginId, appId, productId,"R");
				} 
				approveRes = hodapproveNonApproveRes(approveData, loginId);

				return approveRes;
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}
		
		private List<CommonResponse> hodapproveNonApproveRes(List<Map<String, Object>> mapList, String loginId) {
			List<CommonResponse> response = new ArrayList<CommonResponse>();
			try {
				if (mapList.size() > 0 && mapList != null) {
					for (int i = 0; i < mapList.size(); i++) {
						Map<String, Object> map = mapList.get(i);

						CommonResponse existingData = new CommonResponse();
						String customerName = null == map.get("CUSTOMER_NAME") ? "": String.valueOf(map.get("CUSTOMER_NAME"));
						existingData.setReference_no(null == map.get("REFERENCE_NO") ? "" : String.valueOf(map.get("REFERENCE_NO")));
						existingData.setUploadtranid(null == map.get("UPLOAD_TRAN_ID") ? "" : String.valueOf(map.get("UPLOAD_TRAN_ID")));
						existingData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
						existingData.setCustomer_id(null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
						existingData.setCustomer_name(StringUtils.isBlank(customerName) ? "": customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
						existingData.setQuotation_date(null == map.get("QUOTATION_DATE") ? "" : String.valueOf(map.get("QUOTATION_DATE")));
						existingData.setValidity_date(null == map.get("VALIDITY_DATE") ? "" : String.valueOf(map.get("VALIDITY_DATE")));
						existingData.setPremium(null == map.get("PREMIUM") ? "0" : String.valueOf(map.get("PREMIUM")));
						existingData.setStatus_type(null == map.get("STATUS_TYPE") ? "" : String.valueOf(map.get("STATUS_TYPE")));
						existingData.setHodreferralrequestdate(null == map.get("HOD_REQ_DATE") ? "" : String.valueOf(map.get("HOD_REQ_DATE")));
						existingData.setHodreferralapproveddate(null == map.get("HOD_APPROVE_DATE") ? "" : String.valueOf(map.get("HOD_APPROVE_DATE")));
						existingData.setHodreferralapprovedby(null == map.get("HOD_APPROVE_BY") ? "" : String.valueOf(map.get("HOD_APPROVE_BY")));
						existingData.setLogin_id(loginId);
						existingData.setRenewalyn(null == map.get("RENEWALYN") ? "" : "Y".equalsIgnoreCase(String.valueOf(map.get("RENEWALYN")))?"Yes":"No");
						existingData.setRenewalpolicyno(null == map.get("RENEWALPOLICYNO") ? "" : String.valueOf(map.get("RENEWALPOLICYNO")));
						/*
						existingData.setRemarks(map.get("CUST_REJECT_REMARKS") == null ? "": String.valueOf(map.get("CUST_REJECT_REMARKS")));
						existingData.setVehicletype(map.get("ISCOMMERCIAL_YN") == null ? "" : String.valueOf(map.get("ISCOMMERCIAL_YN")));*/
						existingData.setCompany_name(StringUtils.isBlank(customerName) ? "": customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
						response.add(existingData);
					}
				} else {
					response = null;
				}
			} catch (Exception e) {
				log.error(e);
			}
			return response;
		}
		@Override
		public List<CommonResponse> getAdminHODPendingQuoteList(ExistingRequest req) {
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				List<String> branchCodelist = new ArrayList<String>();
				String loginId = req.getLoginid();
				String branchCode=req.getBranchcode();
				String productId = req.getProductid();
				String typeofquote=StringUtils.isNotBlank(req.getTypeofquote())?req.getTypeofquote():"P";
				String startdate=req.getQuotestartdate();
				String enddate=req.getQuoteenddate();
				String typeofgrid= req.getTypeofquote();
			
				if(StringUtils.isNotBlank(typeofgrid)) {
					
					if(branchCode.equalsIgnoreCase("ALL")) {
						branchCodelist=getAllBranchCode(loginId);
					}else {
						branchCodelist.add(branchCode);
					}
					
					String hodReferralPendingQuery ="";
						if(("P").equalsIgnoreCase(typeofgrid)) {
							hodReferralPendingQuery =commonService.getQuery().getProperty("GET_FLEET_HOD_PENDING_QUOTES");
						}else if(("Y").equalsIgnoreCase(typeofgrid)||("N").equalsIgnoreCase(typeofgrid)) {
							hodReferralPendingQuery =commonService.getQuery().getProperty("GET_FLEET_HODAPPORREJECT_QUOTES");
						}

						if(StringUtils.isNotBlank(hodReferralPendingQuery)) {
							if(StringUtils.isNotBlank(startdate)&&StringUtils.isNotBlank(enddate)) {
								hodReferralPendingQuery+=" " + commonService.getQuery().getProperty("GET_FLEET_HOD_QUOTE_DURATION");
							}else {
								hodReferralPendingQuery+=" " + commonService.getQuery().getProperty("GET_FLEET_HOD_QUOTE_DURATION30");
							}
							Query query = null;
							query = em.createNativeQuery(hodReferralPendingQuery);
							query.setParameter(1, productId);
							query.setParameter(2, typeofquote);
							query.setParameter(3, branchCodelist);
							if(StringUtils.isNotBlank(startdate)&&StringUtils.isNotBlank(enddate)) {
								query.setParameter(4, startdate);
								query.setParameter(5, enddate);
							}
							
							NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
							nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
							List<Map<String,Object>> mapList = query.getResultList();
							resList = getHODQuoteDetails(mapList);
						}
					}else {log.info("TypeofQuote is Empty");}
			} catch (Exception e) {log.error(e);}
			return resList;
		}
		
		private List<String> getAllBranchCode(String loginId){
			String branchCode="";List<String> branchList = new ArrayList<String>();
			try {	
				String allBranchListQuery = commonService.getQuery().getProperty("PORTFOLIO_ADMIN_BRANCH_LIST");
				List<Map<String, Object>> mapList =commonService.getRequestData(loginId,allBranchListQuery);
				log.info("Admin Branch List Size:"+ mapList.size());
				 for(int i=0;i<mapList.size();i++){
					Map<String, Object> data = mapList.get(i);
					String branch=data.get("BRANCH_CODE")==null?"":data.get("BRANCH_CODE").toString();
					if(StringUtils.isNotBlank(branch)) {
						branchList.add(branch);
					}
				 }
				 branchCode=("'"+StringUtils.join(StringUtils.join(branchList,',').split(","),"','")+"'");
			} catch (Exception e) {log.error(e);}
			return branchList;
		}
		
		private List<CommonResponse> getHODQuoteDetails(List<Map<String, Object>> mapList){
			List<CommonResponse> modelList = new ArrayList<CommonResponse>();
			for (int i = 0; i < mapList.size(); i++) {
				Map<String, Object> map = mapList.get(i);
	
				CommonResponse expireData = new CommonResponse();
				String customerName = null == map.get("CUST_NAME") ? "" : String.valueOf(map.get("CUST_NAME"));
				String brokerName = null == map.get("BROKERNAME") ? "" : String.valueOf(map.get("BROKERNAME"));
				expireData.setQuotation_date(null == map.get("ENTRY_DATE") ? "" : String.valueOf(map.get("ENTRY_DATE")));//null == map.get("QUOTE_CREATED") ? "" : String.valueOf(map.get("QUOTE_CREATED")
				expireData.setApplication_no(null == map.get("APPLICATION_NO") ? "" : String.valueOf(map.get("APPLICATION_NO")));
				expireData.setLogin_id(null == map.get("LOGIN_ID") ? "" : String.valueOf(map.get("LOGIN_ID")));
				expireData.setQuote_no(null == map.get("QUOTE_NO") ? "" : String.valueOf(map.get("QUOTE_NO")));
				expireData.setHodreferralremarks(null == map.get("HODREFERRALREMARKS") ? "" : String.valueOf(map.get("HODREFERRALREMARKS")));
				expireData.setApplicationid(null == map.get("APPLICATION_ID") ? "" : String.valueOf(map.get("APPLICATION_ID")));
				expireData.setCustomer_id(null == map.get("CUSTOMER_ID") ? "" : String.valueOf(map.get("CUSTOMER_ID")));
				expireData.setCustomer_name(StringUtils.isBlank(customerName) ? "" : customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
				expireData.setCompany_name(StringUtils.isBlank(customerName) ? "" : customerName.length() > 100 ? customerName.substring(0, 100) : customerName);
				expireData.setBroker_name(StringUtils.isBlank(brokerName) ? "" : brokerName.length() > 100 ? brokerName.substring(0, 100) : brokerName); 
				expireData.setReference_no(map.get("REQUESTREFERENCENO") == null ? "" : map.get("REQUESTREFERENCENO").toString());
				expireData.setEntry_date(null == map.get("ENTRY_DATE") ? "" : String.valueOf(map.get("ENTRY_DATE")));
				expireData.setIstpl(null == map.get("ISTPL") ? "" : String.valueOf(map.get("ISTPL"))); 
				expireData.setPolicyholderid(null == map.get("POLICYHOLDERID") ? "" : String.valueOf(map.get("POLICYHOLDERID"))); 
				expireData.setPolicytype(null == map.get("POLICYTYPEDESC") ? "" : String.valueOf(map.get("POLICYTYPEDESC")));
				expireData.setHodreferralrequestdate(null == map.get("HOD_REQ_DATE") ? "" : String.valueOf(map.get("HOD_REQ_DATE")));
				expireData.setHodreferralapproveddate(null == map.get("HOD_APPROVE_DATE") ? "" : String.valueOf(map.get("HOD_APPROVE_DATE")));
				expireData.setHodreferralapprovedby(null == map.get("HOD_APPROVE_BY") ? "" : String.valueOf(map.get("HOD_APPROVE_BY")));
				expireData.setNoofdayinhodpending(null == map.get("NOOFDAYINHODPENDING") ? "" : String.valueOf(map.get("NOOFDAYINHODPENDING"))); 
				expireData.setNoofdayinhodapproved(null == map.get("NOOFDAYINHODAPPROVED") ? "" : String.valueOf(map.get("NOOFDAYINHODAPPROVED")));
				expireData.setRenewalyn(null == map.get("RENEWALYN") ? "" :  "Y".equalsIgnoreCase(String.valueOf(map.get("RENEWALYN")))?"Yes":"No"); 
				expireData.setRenewalpolicyno(null == map.get("RENEWALPOLICYNO") ? "" : String.valueOf(map.get("RENEWALPOLICYNO")));
				modelList.add(expireData);
			}
			return modelList;
		}
		
		@SuppressWarnings({ "unchecked", "unused" })
		@Override
		public List<CommonResponse> getReportsData(ExistingRequest req,List<Error> error ) {
			List<CommonResponse> modelList = new ArrayList<CommonResponse>();
			if(error.size()==0) {
				Session ses = null;
				 List<Map<String, Object>> mapList = null;
				 
				 try {
					 
					//using HomePositionMaster table Query query = em.createNativeQuery("SELECT A.POLICY_NO,TO_CHAR(A.INCEPTION_DATE,'DD/MM/YYYY') INCEPTION_DATE,A.OVERALL_PREMIUM,A.REMARKS,A.LOGIN_ID,A.QUOTE_NO,A.DEBIT_NOTE_NO, A.CREDIT_NO,A.RECEIPT_NO,NVL(B.COMPANY_NAME,B.FIRST_NAME) INSURED_NAME,A.COMMISSION,NVL(A.PAYMENT_MODE,'-') PAYMENT_MODE,TO_CHAR(A.EFFECTIVE_DATE ,'DD/MM/YYYY') EFFECTIVE_DATE,TO_CHAR(A.EXPIRY_DATE,'DD/MM/YYYY') EXPIRY_DATE,PM.PRODUCT_NAME,'' BROKER_ID FROM HOME_POSITION_MASTER A,PERSONAL_INFO B, PRODUCT_MASTER PM WHERE  A.LOGIN_ID IN (?) AND A.STATUS='Y' AND  A.PRODUCT_ID=? AND NVL(A.SCHEME_ID,'1')=1 AND A.CUSTOMER_ID=B.CUSTOMER_ID AND PM.PRODUCT_ID = A.PRODUCT_ID AND B.AMEND_ID=(SELECT MAX(AMEND_ID) FROM PERSONAL_INFO WHERE CUSTOMER_ID=B.CUSTOMER_ID) and B.STATUS='Y' and PM.STATUS='Y' and A.EFFECTIVE_DATE BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ORDER BY A.EFFECTIVE_DATE DESC");			
					 //SELECT A.POLICY_NO,TO_CHAR(A.INCEPTION_DATE,'DD/MM/YYYY') INCEPTION_DATE,A.OVERALL_PREMIUM,A.REMARKS,A.LOGIN_ID,A.QUOTE_NO, A.RECEIPT_NO,NVL(B.COMPANY_NAME,B.FIRST_NAME) INSURED_NAME,A.COMMISSION,TO_CHAR(A.EFFECTIVE_DATE ,'DD/MM/YYYY') EFFECTIVE_DATE,TO_CHAR(A.EXPIRY_DATE,'DD/MM/YYYY') EXPIRY_DATE,PM.PRODUCT_NAME,'' BROKER_ID FROM HOME_POSITION_MASTER A,PERSONAL_INFO B, PRODUCT_MASTER PM WHERE  A.LOGIN_ID IN (?1) AND A.STATUS IN('P','D') AND  A.PRODUCT_ID=?2 AND A.CUSTOMER_ID=B.CUSTOMER_ID AND PM.PRODUCT_ID = A.PRODUCT_ID AND B.AMEND_ID=(SELECT MAX(AMEND_ID) FROM PERSONAL_INFO WHERE CUSTOMER_ID=B.CUSTOMER_ID) and B.STATUS='Y' and PM.STATUS='Y' AND B.APPLICATION_ID =?3 and A.ENTRY_DATE BETWEEN TO_DATE(?4,'DD/MM/YYYY') AND TO_DATE(?5,'DD/MM/YYYY') ORDER BY A.ENTRY_DATE DESC
					 if(req.getProductid().equals("65")) {
						 modelList = motorGridService.getReportDataDetails(req);
						 return modelList ; 
				/*		 Query query = null;
						 String appId = req.getApplicationid();//TravelRawDetailRepo.getAppId(req.getLoginid());
						 if(appId.equals("1")) {
							//query = em.createNativeQuery("SELECT A.POLICY_NO,(SELECT REQ_REF_NO FROM MOTOR_DATA_DETAIL MDD WHERE MDD.QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) REQREFNO,(SELECT CASE WHEN MDD.ISCOMMERCIAL_YN ='N' THEN 'PRIVATE' ELSE 'COMMERCIAL' END FROM MOTOR_DATA_DETAIL MDD WHERE A.QUOTE_NO = MDD.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) ISCOMMERCIAL_YN,TO_CHAR(A.ENTRY_DATE, 'DD/MM/YYYY') QUOTATION_DATE,TO_CHAR(A.INCEPTION_DATE,'DD/MM/YYYY') INCEPTION_DATE,A.OVERALL_PREMIUM,A.REMARKS, (CASE WHEN A.APPLICATION_ID='1' THEN A.LOGIN_ID ELSE A.APPLICATION_ID END) LOGIN_ID,A.QUOTE_NO, A.RECEIPT_NO,NVL(B.COMPANY_NAME,B.FIRST_NAME) ||' '||B.JOINT_NAME INSURED_NAME,A.COMMISSION,TO_CHAR(A.EFFECTIVE_DATE ,'DD/MM/YYYY') EFFECTIVE_DATE,TO_CHAR(A.EXPIRY_DATE, 'DD/MM/YYYY') EXPIRY_DATE,PM.PRODUCT_NAME,'' BROKER_ID,(SELECT ERD.POLICYTYPE FROM ESERVICE_REQUEST_DETAIL ERD WHERE ERD.QUOTE_NO=A.QUOTE_NO) POLICY_TYPE, (SELECT MPM.POLICYTYPE_DESC_ENGLISH FROM MOTOR_POLICYTYPE_MASTER MPM, ESERVICE_REQUEST_DETAIL ERD WHERE MPM.POLICYTYPE_ID=ERD.POLICYTYPE AND ERD.QUOTE_NO=A.QUOTE_NO) POLICYTYPE_DESC,(SELECT BRANCH_NAME FROM BRANCH_MASTER WHERE BRANCH_CODE=A.BRANCH_CODE) BRANCH_NAME,A.POL_INTEG_STATUS,A.INTEGRATION_ERROR FROM HOME_POSITION_MASTER A,PERSONAL_INFO B, PRODUCT_MASTER PM WHERE  (CASE WHEN A.LOGIN_ID IS NOT NULL THEN A.LOGIN_ID ELSE A.BDM_CODE END)=(SELECT OA_CODE from LOGIN_MASTER where LOGIN_ID=?1 AND A.STATUS IN('P','D') AND A.PRODUCT_ID=?2 AND A.CUSTOMER_ID=B.CUSTOMER_ID AND PM.PRODUCT_ID = A.PRODUCT_ID AND B.AMEND_ID=(SELECT MAX(AMEND_ID) FROM PERSONAL_INFO WHERE CUSTOMER_ID=B.CUSTOMER_ID) and B.STATUS='Y' and PM.STATUS='Y' AND A.APPLICATION_ID =?3 and A.ENTRY_DATE BETWEEN TO_DATE(?4,'DD/MM/YYYY') AND TO_DATE(?5,'DD/MM/YYYY')  ORDER BY A.INCEPTION_DATE DESC");
							query = em.createNativeQuery("SELECT DISTINCT A.POLICY_NO,(SELECT REQ_REF_NO FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) REQREFNO, (SELECT PLATE_NO_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) PLATE_NO_EN, (SELECT CHASSIS_NO FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) CHASSIS_NO, (SELECT MAKE_NAME_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) MAKE_NAME_EN, (SELECT MODEL_NAME_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) MODEL_NAME_EN, (SELECT TRIM FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) TRIM, (SELECT BODY_NAME_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) BODY_NAME_EN, (SELECT CASE WHEN MDDG.GEOGRAPHICAL_EXTENSION='1' THEN 'Coverage for Oman' WHEN MDDG.GEOGRAPHICAL_EXTENSION='2' THEN 'Coverage for Oman & UAE' ELSE 'Coverage for GCC' END FROM MOTOR_DATA_DETAIL MDDG WHERE MDDG. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDDG.APPLICATION_NO) GEOGRAPHICAL_EXTENSION, (SELECT SUMINSURED FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) SUMINSURED, (SELECT CASE WHEN MDD.ISCOMMERCIAL_YN ='N' THEN 'PRIVATE' ELSE 'COMMERCIAL'END FROM MOTOR_DATA_DETAIL MDD WHERE A.QUOTE_NO = MDD.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) ISCOMMERCIAL_YN, A.ORANGE_CARDNO,( SELECT CASE WHEN MDD.ISCOMMERCIAL_YN ='N' THEN 'PRIVATE' ELSE 'COMMERCIAL' END FROM MOTOR_DATA_DETAIL MDD WHERE A.QUOTE_NO = MDD.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) ISCOMMERCIAL_YN1,(SELECT ERD.PROMOCODE FROM ESERVICE_REQUEST_DETAIL ERD WHERE ERD.QUOTE_NO=A.QUOTE_NO) PROMOCODE,TO_CHAR(A. ENTRY_DATE, 'DD/MM/YYYY') QUOTATION_DATE,TO_CHAR(A.INCEPTION_DATE, 'DD/MM/YYYY') INCEPTION_DATE,A.OVERALL_PREMIUM,A.REMARKS, ( CASE WHEN A.APPLICATION_ID='1' THEN A.LOGIN_ID ELSE A.APPLICATION_ID END) LOGIN_ID,A.QUOTE_NO, A.RECEIPT_NO,NVL(B.COMPANY_NAME,B.FIRST_NAME) ||' '||B.JOINT_NAME INSURED_NAME,A.COMMISSION,TO_CHAR(A.EFFECTIVE_DATE , 'DD/MM/YYYY') EFFECTIVE_DATE,TO_CHAR(A.EXPIRY_DATE, 'DD/MM/YYYY') EXPIRY_DATE,PM.PRODUCT_NAME,'' BROKER_ID,( SELECT ERD.POLICYTYPE FROM ESERVICE_REQUEST_DETAIL ERD WHERE ERD.QUOTE_NO=A.QUOTE_NO) POLICY_TYPE, ( SELECT MPM.POLICYTYPE_DESC_ENGLISH FROM MOTOR_POLICYTYPE_MASTER MPM, ESERVICE_REQUEST_DETAIL ERD WHERE MPM.POLICYTYPE_ID=ERD.POLICYTYPE AND ERD.QUOTE_NO=A.QUOTE_NO) POLICYTYPE_DESC,( SELECT BRANCH_NAME FROM BRANCH_MASTER WHERE BRANCH_CODE=A.BRANCH_CODE) BRANCH_NAME,A.POL_INTEG_STATUS,A. INTEGRATION_ERROR FROM HOME_POSITION_MASTER A,PERSONAL_INFO B, PRODUCT_MASTER PM, LOGIN_MASTER LM WHERE A.STATUS IN('P','D') AND ( (LM.USERTYPE='Broker' AND A.BROKER_CODE=LM.OA_CODE AND LM.LOGIN_ID = ?1 ) OR (LM.USERTYPE='User' AND 1=1 AND A.LOGIN_ID = (?1) ) ) AND A.PRODUCT_ID=?2 AND A.CUSTOMER_ID=B.CUSTOMER_ID AND PM.PRODUCT_ID = A.PRODUCT_ID AND B.AMEND_ID=( SELECT MAX(AMEND_ID) FROM PERSONAL_INFO WHERE CUSTOMER_ID=B.CUSTOMER_ID) and B.STATUS='Y' and PM.STATUS='Y' AND A.APPLICATION_ID =?3 and A.ENTRY_DATE BETWEEN TO_DATE(?4,'DD/MM/YYYY') AND TO_DATE(?5,'DD/MM/YYYY') ORDER BY A.QUOTE_NO DESC"); 
							query.setParameter(1, req.getLoginid());
							 query.setParameter(2, req.getProductid());
							 query.setParameter(3, req.getApplicationid());
							 query.setParameter(4, req.getStartdate());
							 query.setParameter(5, req.getEnddate());
						 }else {
							 query = em.createNativeQuery("SELECT A.POLICY_NO,(SELECT REQ_REF_NO FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) REQREFNO, (SELECT PLATE_NO_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) PLATE_NO_EN, (SELECT CHASSIS_NO FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) CHASSIS_NO, (SELECT MAKE_NAME_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) MAKE_NAME_EN, (SELECT MODEL_NAME_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) MODEL_NAME_EN, (SELECT TRIM FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) TRIM, (SELECT BODY_NAME_EN FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) BODY_NAME_EN, (SELECT CASE WHEN MDDG.GEOGRAPHICAL_EXTENSION='1' THEN 'Coverage for Oman' WHEN MDDG.GEOGRAPHICAL_EXTENSION='2' THEN 'Coverage for Oman & UAE' ELSE 'Coverage for GCC' END FROM MOTOR_DATA_DETAIL MDDG WHERE MDDG. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDDG.APPLICATION_NO) GEOGRAPHICAL_EXTENSION, (SELECT SUMINSURED FROM MOTOR_DATA_DETAIL MDD WHERE MDD. QUOTE_NO=A.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) SUMINSURED, (SELECT CASE WHEN MDD.ISCOMMERCIAL_YN ='N' THEN 'PRIVATE' ELSE 'COMMERCIAL'END FROM MOTOR_DATA_DETAIL MDD WHERE A.QUOTE_NO = MDD.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) ISCOMMERCIAL_YN, A.ORANGE_CARDNO,(SELECT CASE WHEN MDD.ISCOMMERCIAL_YN ='N' THEN 'PRIVATE' ELSE 'COMMERCIAL' END FROM MOTOR_DATA_DETAIL MDD WHERE A.QUOTE_NO = MDD.QUOTE_NO AND A.APPLICATION_NO = MDD.APPLICATION_NO) ISCOMMERCIAL_YN1,(SELECT ERD.PROMOCODE FROM ESERVICE_REQUEST_DETAIL ERD WHERE ERD.QUOTE_NO=A.QUOTE_NO) PROMOCODE,TO_CHAR(A.ENTRY_DATE, 'DD/MM/YYYY') QUOTATION_DATE,TO_CHAR(A.INCEPTION_DATE,'DD/MM/YYYY') INCEPTION_DATE,A. OVERALL_PREMIUM,A.REMARKS,( CASE WHEN A.APPLICATION_ID='1' THEN A.LOGIN_ID ELSE A.APPLICATION_ID END) LOGIN_ID,A.QUOTE_NO, A.RECEIPT_NO,NVL(B. COMPANY_NAME,B.FIRST_NAME) INSURED_NAME,A.COMMISSION,TO_CHAR(A. EFFECTIVE_DATE ,'DD/MM/YYYY') EFFECTIVE_DATE,TO_CHAR(A.EXPIRY_DATE, 'DD/MM/YYYY') EXPIRY_DATE,PM.PRODUCT_NAME,'' BROKER_ID,( SELECT ERD.POLICYTYPE FROM ESERVICE_REQUEST_DETAIL ERD WHERE ERD.QUOTE_NO=A.QUOTE_NO) POLICY_TYPE, ( SELECT MPM.POLICYTYPE_DESC_ENGLISH FROM MOTOR_POLICYTYPE_MASTER MPM, ESERVICE_REQUEST_DETAIL ERD WHERE MPM.POLICYTYPE_ID=ERD.POLICYTYPE AND ERD.QUOTE_NO=A.QUOTE_NO) POLICYTYPE_DESC,( SELECT BRANCH_NAME FROM BRANCH_MASTER WHERE BRANCH_CODE=A.BRANCH_CODE) BRANCH_NAME,A.POL_INTEG_STATUS,A.INTEGRATION_ERROR FROM HOME_POSITION_MASTER A,PERSONAL_INFO B, PRODUCT_MASTER PM WHERE (CASE WHEN A.BDM_CODE IS NULL THEN A.LOGIN_ID ELSE A.BDM_CODE END)=?1 AND A.STATUS IN('P','D') AND A.PRODUCT_ID=?2 AND A.CUSTOMER_ID=B.CUSTOMER_ID AND PM.PRODUCT_ID = A.PRODUCT_ID AND B.AMEND_ID=( SELECT MAX(AMEND_ID) FROM PERSONAL_INFO WHERE CUSTOMER_ID=B.CUSTOMER_ID) and B.STATUS='Y' and PM.STATUS='Y' AND A.APPLICATION_ID =?3 and trunc(A.ENTRY_DATE) BETWEEN TO_DATE(?4,'DD/MM/YYYY') AND TO_DATE(?5,'DD/MM/YYYY') AND A.BRANCH_CODE=?6 ORDER BY A.INCEPTION_DATE DESC");
							 query.setParameter(1, req.getLoginid());
							 query.setParameter(2, req.getProductid());
							 query.setParameter(3, req.getApplicationid());
							 query.setParameter(4, req.getStartdate());
							 query.setParameter(5, req.getEnddate());
							 query.setParameter(6, req.getBranchcode());
						 }
						 
						
						 @SuppressWarnings("rawtypes")
						 NativeQueryImpl nativeQuery = (NativeQueryImpl) query; 
						 nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE); 
						 mapList = query.getResultList();
						 log.info("getReportsData -- Loginid: "+req.getLoginid()+" Productid: "+req.getProductid()+" Size: "+mapList.size());
					 */
					 }else if(req.getProductid().equals("30") ){
						 modelList = nonGridService.getReportDataDetails(req);
						 return modelList ; 
					 } else {
						 Query query = em.createNativeQuery("SELECT A.POLICY_NO,TO_CHAR(A.INCEPTION_DATE,'DD/MM/YYYY') INCEPTION_DATE,A.OVERALL_PREMIUM,A.REMARKS,A.LOGIN_ID,A.QUOTE_NO, A.RECEIPT_NO,NVL(B.COMPANY_NAME,B.FIRST_NAME) INSURED_NAME,A.COMMISSION,TO_CHAR(A.EFFECTIVE_DATE ,'DD/MM/YYYY') EFFECTIVE_DATE,TO_CHAR(A.EXPIRY_DATE, 'DD/MM/YYYY') EXPIRY_DATE,PM.PRODUCT_NAME,'' BROKER_ID,(SELECT ERD.POLICYTYPE FROM ESERVICE_REQUEST_DETAIL ERD WHERE ERD.QUOTE_NO=A.QUOTE_NO) POLICY_TYPE, (SELECT MPM.POLICYTYPE_DESC_ENGLISH FROM MOTOR_POLICYTYPE_MASTER MPM, ESERVICE_REQUEST_DETAIL ERD WHERE MPM.POLICYTYPE_ID=ERD.POLICYTYPE AND ERD.QUOTE_NO=A.QUOTE_NO) POLICYTYPE_DESC,A.POL_INTEG_STATUS,A.INTEGRATION_ERROR FROM HOME_POSITION_MASTER A,PERSONAL_INFO B, PRODUCT_MASTER PM WHERE A.LOGIN_ID IN (?1) AND A.STATUS IN('P','D') AND A.PRODUCT_ID=?2 AND A.CUSTOMER_ID=B.CUSTOMER_ID AND PM.PRODUCT_ID = A.PRODUCT_ID AND B.AMEND_ID=(SELECT MAX(AMEND_ID) FROM PERSONAL_INFO WHERE CUSTOMER_ID=B.CUSTOMER_ID) and B.STATUS='Y' and PM.STATUS='Y' AND B.APPLICATION_ID =?3 and A.ENTRY_DATE BETWEEN TO_DATE(?4,'DD/MM/YYYY') AND TO_DATE(?5,'DD/MM/YYYY') ORDER BY A.INCEPTION_DATE DESC");//SELECT A.POLICY_NO,TO_CHAR(A.INCEPTION_DATE,'DD/MM/YYYY') INCEPTION_DATE,A.OVERALL_PREMIUM,A.REMARKS,A.LOGIN_ID,A.QUOTE_NO, A.RECEIPT_NO,NVL(B.COMPANY_NAME,B.FIRST_NAME) INSURED_NAME,A.COMMISSION,TO_CHAR(A.EFFECTIVE_DATE ,'DD/MM/YYYY') EFFECTIVE_DATE,TO_CHAR(A.EXPIRY_DATE,'DD/MM/YYYY') EXPIRY_DATE,PM.PRODUCT_NAME,'' BROKER_ID FROM HOME_POSITION_MASTER A,PERSONAL_INFO B, PRODUCT_MASTER PM WHERE  A.LOGIN_ID IN (?) AND A.STATUS='Y' AND  A.PRODUCT_ID=? AND A.CUSTOMER_ID=B.CUSTOMER_ID AND PM.PRODUCT_ID = A.PRODUCT_ID AND B.AMEND_ID=(SELECT MAX(AMEND_ID) FROM PERSONAL_INFO WHERE CUSTOMER_ID=B.CUSTOMER_ID) and B.STATUS='Y' and PM.STATUS='Y' AND B.APPLICATION_ID = ? and A.EFFECTIVE_DATE BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ORDER BY A.EFFECTIVE_DATE DESC
						 query.setParameter(1, req.getLoginid());
						 query.setParameter(2, req.getProductid());
						 query.setParameter(3, req.getApplicationid());
						 query.setParameter(4, req.getStartdate());
						 query.setParameter(5, req.getEnddate());
						 @SuppressWarnings("rawtypes")
						 NativeQueryImpl nativeQuery = (NativeQueryImpl) query; 
						 nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE); 
						 mapList = query.getResultList();
						 log.info("getReportsData -- Loginid: "+req.getLoginid()+" Productid: "+req.getProductid()+" Size: "+mapList.size());
					 }
			
				 for(int i=0;i<mapList.size();i++)
				 {
					 CommonResponse reportData=new CommonResponse();
					 String customerName=null==mapList.get(i).get("INSURED_NAME")?"":String.valueOf(mapList.get(i).get("INSURED_NAME"));
					 reportData.setPolicy_no(null==mapList.get(i).get("POLICY_NO")?"":String.valueOf(mapList.get(i).get("POLICY_NO")));
					 reportData.setInception_date(null==mapList.get(i).get("INCEPTION_DATE")?"":String.valueOf(mapList.get(i).get("INCEPTION_DATE")));
					 reportData.setOverall_premium(null==mapList.get(i).get("OVERALL_PREMIUM")?"0":String.valueOf(mapList.get(i).get("OVERALL_PREMIUM")));
					 reportData.setRemarks(null==mapList.get(i).get("REMARKS")?"":String.valueOf(mapList.get(i).get("REMARKS")));
					 reportData.setLogin_id(null==mapList.get(i).get("LOGIN_ID")?"":String.valueOf(mapList.get(i).get("LOGIN_ID")));
					 reportData.setQuote_no(null==mapList.get(i).get("QUOTE_NO")?"":String.valueOf(mapList.get(i).get("QUOTE_NO")));
					 reportData.setDebit_note_no(null==mapList.get(i).get("DEBIT_NOTE_NO")?"":String.valueOf(mapList.get(i).get("DEBIT_NOTE_NO")));
					 reportData.setCredit_no(null==mapList.get(i).get("CREDIT_NO")?"":String.valueOf(mapList.get(i).get("CREDIT_NO")));
					 reportData.setReceipt_no(null==mapList.get(i).get("RECEIPT_NO")?"":String.valueOf(mapList.get(i).get("RECEIPT_NO")));
					 reportData.setQuotation_date(null==mapList.get(i).get("QUOTATION_DATE")?"":String.valueOf(mapList.get(i).get("QUOTATION_DATE")));
					 reportData.setInsured_name(StringUtils.isBlank(customerName)?"":customerName.length()> 100?customerName.substring(0, 100):customerName);
					 reportData.setCommision(null==mapList.get(i).get("COMMISION")?"":String.valueOf(mapList.get(i).get("COMMISION")));
					 reportData.setPayment_mode(null==mapList.get(i).get("PAYMENT_MODE")?"":String.valueOf(mapList.get(i).get("PAYMENT_MODE")));
					 reportData.setEffective_date(null==mapList.get(i).get("EFFECTIVE_DATE")?"":String.valueOf(mapList.get(i).get("EFFECTIVE_DATE")));
					 reportData.setEntry_date(null==mapList.get(i).get("ENTRY_DATE")?"":String.valueOf(mapList.get(i).get("ENTRY_DATE")));
					 reportData.setProduct_name(null==mapList.get(i).get("PRODUCT_NAME")?"":String.valueOf(mapList.get(i).get("PRODUCT_NAME")));
					 reportData.setBroker_id(null==mapList.get(i).get("BROKER_ID")?"":String.valueOf(mapList.get(i).get("BROKER_ID")));
					 reportData.setExpiry_date(null==mapList.get(i).get("EXPIRY_DATE")?"":String.valueOf(mapList.get(i).get("EXPIRY_DATE")));
					 reportData.setPolicytype(null==mapList.get(i).get("POLICYTYPE_DESC")?"":String.valueOf(mapList.get(i).get("POLICYTYPE_DESC")));
					 reportData.setBranchname(null==mapList.get(i).get("BRANCH_NAME")?"":mapList.get(i).get("BRANCH_NAME").toString());
					 reportData.setIntegstatus(null==mapList.get(i).get("POL_INTEG_STATUS")?"":mapList.get(i).get("POL_INTEG_STATUS").toString());
					 reportData.setIntegerrordesc(null==mapList.get(i).get("INTEGRATION_ERROR")?"":mapList.get(i).get("INTEGRATION_ERROR").toString());
					 reportData.setReference_no(null==mapList.get(i).get("REQREFNO")?"":mapList.get(i).get("REQREFNO").toString());
					 reportData.setPolicytype(null==mapList.get(i).get("POLICY_TYPE")?"":mapList.get(i).get("POLICY_TYPE").toString());
					 reportData.setVehicletype(null==mapList.get(i).get("ISCOMMERCIAL_YN1")?"":mapList.get(i).get("ISCOMMERCIAL_YN1").toString());
					 reportData.setPlateno(null==mapList.get(i).get("PLATE_NO_EN")?"":mapList.get(i).get("PLATE_NO_EN").toString());
					 reportData.setChassisno(null==mapList.get(i).get("CHASSIS_NO")?"":mapList.get(i).get("CHASSIS_NO").toString());
					 reportData.setMakenamedesc(null==mapList.get(i).get("MAKE_NAME_EN")?"":mapList.get(i).get("MAKE_NAME_EN").toString());
					 reportData.setModelnamedesc(null==mapList.get(i).get("MODEL_NAME_EN")?"":mapList.get(i).get("MODEL_NAME_EN").toString());
					 reportData.setTrim(null==mapList.get(i).get("TRIM")?"":mapList.get(i).get("TRIM").toString());
					 reportData.setBodynamedesc(null==mapList.get(i).get("BODY_NAME_EN")?"":mapList.get(i).get("BODY_NAME_EN").toString());
					 reportData.setGeodesc(null==mapList.get(i).get("GEOGRAPHICAL_EXTENSION")?"":mapList.get(i).get("GEOGRAPHICAL_EXTENSION").toString());
					 reportData.setSuminsured(null==mapList.get(i).get("SUMINSURED")?"":mapList.get(i).get("SUMINSURED").toString());
					 reportData.setOrangecardno(null==mapList.get(i).get("ORANGE_CARDNO")?"":mapList.get(i).get("ORANGE_CARDNO").toString());
					 reportData.setPromocode(null==mapList.get(i).get("PROMOCODE")?"":mapList.get(i).get("PROMOCODE").toString());
					 modelList.add(reportData);
				 }
				 return modelList;
				 }catch (Exception e) {
					 log.error(e);
					 }
			}else {
				CommonResponse searchingData=new CommonResponse();
				String validationDetails = "";
				List<Error> errorsList = error;
			
				/*	validationDetails = StringUtils.join(error.toArray(new String[error.size()]), ",");
					if (validationDetails.length() > 0) {
						String splitVal[] = validationDetails.split(",");
						for (int i = 0; i < splitVal.length; i++) {
							if (splitVal[i].length() > 0) {
								Error errors = ErrorInstance.get(splitVal[i]);
								if (errors != null)
									errorsList.add(errors);
							}
						}
					} */
					searchingData.setStatus("false");
					searchingData.setErrors(errorsList);
					modelList.add(searchingData);
					return modelList;
			}
			
			return null;
		}
		
		@Override
		public List<Error> validateSearchByData(SearchRequest req) {
			try {
				List<Error> list = new ArrayList<Error>();

				String applicationid = StringUtils.isBlank(req.getApplicationid()) ? "" : req.getApplicationid();
				
				
				// loginId
				if (applicationid.equals("1")) {
					if (StringUtils.isBlank(req.getLoginid())) {
						list.add(new Error("Please Enter LoginId", "loginid", "01"));
					}
				}
				// Product Id
				if (StringUtils.isBlank(req.getProductid())) {
					list.add(new Error("Please Enter ProductId", "productid", "02"));
				}

				// Search By
				if (StringUtils.isBlank(req.getSearchby())) {
					list.add(new Error("Please select Search By", "searchby", "03"));
				}

				// Search Value
				if (StringUtils.isBlank(req.getSearchvalue())) {
					list.add(new Error("Please Enter Search Value", "searchvalue", "04"));
				}
				
				// aggregator type
				/*if (StringUtils.isBlank(req.getAggregatortype())) {
					list.add(new Error("Please select Aggregator Type", "aggregatortype", "05"));
				}*/
				
				return list;
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}

		@Override
		public List<Error> validateLapsedQuote(lapsedQuoteReq req) {
			List<Error> list = new ArrayList<Error>();
			try {
				if (StringUtils.isBlank(req.getLapsedremarks())) {
					list.add(new Error( "1082", "LapsedRemarks","Please select Lapsed Remarks"));
				}
				return list;
			} catch (Exception e) {
				log.error(e);
			}
			return list;
		}
		
		@Override
		public List<Error> validateReportrequest(ExistingRequest req) {
			try {
				List<Error> list = new ArrayList<Error>();
				// loginId
				if (StringUtils.isBlank(req.getLoginid())) {
					list.add(new Error("Please Enter LoginId", "loginid", "01"));
				}

				// Product Id
				if (StringUtils.isBlank(req.getProductid())) {
					list.add(new Error("Please Enter ProductId", "productid", "02"));
				}

				// policystartdate
				if (StringUtils.isBlank(req.getStartdate())) {
					list.add(new Error("Please Select Startdate", "startdate", "03"));
				}

				// policyenddate
				if (StringUtils.isBlank(req.getEnddate())) {
					list.add(new Error("Please Select Enddate", "enddate", "04"));
				}
				return list;
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}

		
		@Override
		public List<CommonResponse> getAdminExistingQuoteList(ExistingRequest request) {
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				if(request.getProductid().equalsIgnoreCase("65") ) {
					request.setAdminreferalstatus("R");
					resList = motorGridService.getAdminExistingQuoteList(request);
					 return resList ; 
				}
				
			} catch( Exception e) {
				e.printStackTrace();
				log.info(" Exception is ---> " + e.getMessage());
				return null;
			}
			return resList;
		}

		
		@Override
		public List<CommonResponse> getAdminApprovedQuoteList(ExistingRequest request) {
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				if(request.getProductid().equalsIgnoreCase("65") ) {
					request.setAdminreferalstatus("A");
					resList = motorGridService.getAdminExistingQuoteList(request);
					 return resList ; 
				}
				
			} catch( Exception e) {
				e.printStackTrace();
				log.info(" Exception is ---> " + e.getMessage());
				return null;
			}
			return resList;
		}

		
		@Override
		public List<CommonResponse> getAdminRejectedQuoteList(ExistingRequest request) {
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				if(request.getProductid().equalsIgnoreCase("65") ) {
					request.setAdminreferalstatus("N");
					resList = motorGridService.getAdminExistingQuoteList(request);
					 return resList ; 
				}
				
			} catch( Exception e) {
				e.printStackTrace();
				log.info(" Exception is ---> " + e.getMessage());
				return null;
			}
			return resList;
		}

		@Override
		public List<CommonResponse> getAdminPortfolioList(ExistingRequest request) {
			List<CommonResponse> resList = new ArrayList<CommonResponse>();
			try {
				if(request.getProductid().equalsIgnoreCase("65") ) {
					resList = motorGridService.getAdminPortfolioList(request);
					 return resList ; 
				}
				
			} catch( Exception e) {
				e.printStackTrace();
				log.info(" Exception is ---> " + e.getMessage());
				return null;
			}
			return resList;
		}
}

