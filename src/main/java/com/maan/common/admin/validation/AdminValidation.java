package com.maan.common.admin.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.maan.common.admin.request.AdminNewBrokerReq;
import com.maan.common.admin.request.AdminNewIssuerReq;
import com.maan.common.admin.request.BrokerLoginProductInsReq;
import com.maan.common.admin.request.IssuerChangePassReq;
import com.maan.common.admin.request.NewAdminInsertReq;
import com.maan.common.admin.request.UserMgtInsertRequest;
import com.maan.common.admin.request.UserMgtProductInsertRequest;
import com.maan.common.admin.request.UserProductValidReq;
import com.maan.common.bean.BranchMaster;
import com.maan.common.bean.BrokerCompanyMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.repository.BranchMasterRepository;
import com.maan.common.repository.BrokerCompanyMasterRepository;
import com.maan.common.repository.LoginMasterRepository;


@Component
public class AdminValidation {
	
	@Autowired
	private BranchMasterRepository branchmasterrepository;
	@Autowired
	private LoginMasterRepository autheRepository;
	
	private Logger log=LogManager.getLogger(AdminValidation.class);
	@Autowired
	private LoginMasterRepository loginRepo;
	@Autowired
	private BrokerCompanyMasterRepository brokerCompanyRepo;
	private static ForkJoinPool forkJoinPool = new ForkJoinPool(5);
	
	
	public List<String> BrokerNewInsertValidation(AdminNewBrokerReq req) {
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isBlank(req.getApprovedby())){
				list.add("1403");
	    	}if(StringUtils.isBlank(req.getBrokerCode())){  
	    		list.add("1404");
	    	}else if(validateBcode(req.getBrokerCode(),req.getAgencyCode(),req.getBranchCode()) > 0) {
	    		list.add("1405");
	    	}else if("new".equalsIgnoreCase(req.getMode()) && valBraWiseBcode(req.getBrokerCode(),req.getBranchCode())>0) {
	    		list.add("1406");
	    	}
	    	if(StringUtils.isEmpty(req.getCity())){
	    		list.add("1407");
	    	}if("VARIOUS".equalsIgnoreCase(req.getCity())){
	    		if(StringUtils.isBlank(req.getOtherCity()))
	    			list.add("1408");
	    		else if(!StringUtils.isAlpha(req.getOtherCity())){
	    			list.add("1409");
	        	}
	    	}
	    	if(StringUtils.isBlank(req.getOtherCity()))
	    		req.setOtherCity("");
	    	if(StringUtils.isEmpty(req.getCountry())){
	    		list.add("1410");
	    	}
	    	if(StringUtils.isNotBlank(req.getPoBox()) && !StringUtils.isNumeric(req.getPoBox())){
	    		list.add("1411");
	    	}if(!StringUtils.isNumeric(req.getMobileNo())){
	    		list.add("1412");
	    	}if(StringUtils.isEmpty(req.getCustFirstName())){
	    		list.add("1413");
	    	}else if(StringUtils.isNumeric(req.getCustFirstName())){
	    		list.add("1414");
	    	}if(StringUtils.isEmpty(req.getNationality())){
	    		list.add("1415");
	    	}if(!StringUtils.isNumeric(req.getTelephoneNo())){
	    		list.add("1416");
	    	}if(StringUtils.isEmpty(req.getEmail())){
	    		list.add("1451");
	    	}else if(StringUtils.contains(req.getEmail(), " "))
	    		list.add("1452");
	    	else if(!emailValidate(req.getEmail())){
	    		list.add("1453");
	    	}if(StringUtils.isEmpty(req.getExecutive())){
	    		list.add("1420");
	    	}
	    	List<LoginMaster> admin=loginRepo.findByLoginId(req.getLoginId()); 
	    	if("new".equals(req.getMode())){
	    		if(StringUtils.isEmpty(req.getLoginId().trim())){
	    			list.add("1454");
	        	}else if(StringUtils.contains(req.getLoginId(), " ")) {
	        		list.add("1455");
	        	}else if(admin.size()>0 && !"edit".equalsIgnoreCase(req.getMode())) {
	        		list.add("1456");
	        	}
	    		if(!"edit".equalsIgnoreCase(req.getMode())){
	    		if(StringUtils.isEmpty(req.getPassword().trim())){
	    			list.add("1458");
	        	}else if(StringUtils.contains(req.getPassword(), " "))
	        		list.add("1459");
	    		if(StringUtils.isEmpty(req.getRePassword().trim())){
	    			list.add("1401");
	        	}else if(!req.getPassword().equals(req.getRePassword().trim())){
	        		list.add("1402");
	        	}else if(!validPassword(req.getPassword().trim())){
	        		list.add("1428");
	        	}else if(admin.size()>0){
	        		list.add("1456");
	        	}
	    		}
	    	}
	    	/*if(StringUtils.isBlank(req.getRegionCode())) {
				list.add("1224");
			}else {
				Optional<RegionMaster> region = regionMasRep.findByRegionMasterIdRegioncodeAndStatus(req.getRegionCode(),"Y");
				if(!region.isPresent()) {
					list.add("1235");
				}
			}
			if(StringUtils.isBlank(req.getBranchCode())) {
				list.add("1223");
			}else if(StringUtils.isNotBlank(req.getRegionCode())) {
				List<BranchMaster> branch = branchRep.findByBranchmasterpkBranchcodeAndBranchmasterpkRegioncodeAndStatus(req.getBranchCode(),req.getRegionCode(),"Y");
				if(CollectionUtils.isEmpty(branch)) {
					list.add("1236");
				}
			}*/
	    	if("Y".equalsIgnoreCase(req.getPolicyFeeStatus())){
	    		if(StringUtils.isEmpty(req.getPolicyFee())){
	    			list.add("1430");
	        	}else  {
	        		try {
		    			Double.parseDouble(req.getPolicyFee());
		    			if(Double.parseDouble(req.getPolicyFee())>99.999){	
		    				list.add("1431");
		    			}
		    		}
		    		catch(Exception e) {
		    			log.info(e);
		    		}
	        	}
			}if("Y".equalsIgnoreCase(req.getGovtFeeStatus())){
	    		if(StringUtils.isEmpty(req.getGovetFee())){
	    			list.add("1432");
	        	}else {
	        		try {
		    			Double.parseDouble(req.getGovetFee());
		    			if(Double.parseDouble(req.getGovetFee())>99.999){	
		    				list.add("1433");
		    			}
		    		}
		    		catch(Exception e) {
		    			log.info(e);
		    		}
	        	}
			}
			if("Y".equalsIgnoreCase(req.getEmergencyFee())){
	    		if(StringUtils.isEmpty(req.getEmergencyFund())){
	    			list.add("1434");
	        	}else if(!StringUtils.isNumeric(req.getEmergencyFund())){
	        		list.add("1435");
	        	}else if(Integer.parseInt(req.getEmergencyFund())>99.999){
	        		list.add("1435");
	        	}
			}
			if(StringUtils.isNotBlank(req.getOneOffCommission()) && !decimalValidate(req.getOneOffCommission())) {
				list.add("1436");
			}if(StringUtils.isNotBlank(req.getOpenCoverCommission()) && !decimalValidate(req.getOpenCoverCommission())) {
				list.add("1437");
			}if(StringUtils.isEmpty(req.getEffectiveDate())){
				list.add("1438");
	    	}if(StringUtils.isBlank(req.getBorkerOrganization())) {
				list.add("1439");
			}
		}catch (Exception e) {
			log.info(e);
		}
		return list;
	}
	
	@SuppressWarnings("static-access")
	private boolean emailValidate(String email) {
		String email_patrn = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(email_patrn);
		return pattern.matches(email_patrn,email);
	}
	@SuppressWarnings("static-access")
	public static boolean decimalValidate(final String name){
		String regx = "[0-9]+([,.][0-9]{1,4})?";
	    Pattern pattern = Pattern.compile(regx);
	    return pattern.matches(regx, name);
	}
	public boolean validPassword(String newpassword) {
		Pattern pattern=Pattern.compile("(?=\\S+$).{7,20}");
    	Matcher matcher = pattern.matcher(newpassword);
    	return matcher.matches();
	}
	private Long validateBcode(String brokerCode, String agencyCode, String branchCode) {
		Long value = 0L;
		try {
			if(StringUtils.isNotBlank(agencyCode)){
				List<BrokerCompanyMaster>blixt=brokerCompanyRepo.findByRsabrokerCodeAndAgencyCodeAndBranchCode(brokerCode,agencyCode,branchCode);
				value = (long) blixt.size();
			}else {
				List<BrokerCompanyMaster>blixt=brokerCompanyRepo.findByRsabrokerCodeAndBranchCode(brokerCode,branchCode);
				value = (long) blixt.size();
			}
		}catch (Exception e) {
			log.info(e);
		}
		return value;
	}
	private Long valBraWiseBcode(String brokerCode, String branchCode) {
		Long value = 0L;
		try {
			List<BrokerCompanyMaster>blixt=brokerCompanyRepo.findByRsabrokerCodeAndBranchCode(brokerCode,branchCode);
			value = (long) blixt.size();
		}catch (Exception e) {
			log.info(e);
		}
		return value;
	}
	public List<String> BrokerPasswordValidation(IssuerChangePassReq req) {   
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isBlank(req.getPassword().trim())){
				list.add("1400");
	    	}if(StringUtils.isBlank(req.getRePassword().trim())){
	    		list.add("1401");
	    	}if(!req.getRePassword().equals(req.getPassword().trim())){
	    		list.add("1402");
	    	}
		}catch (Exception e) {
			log.info(e);
		}
		return list;
	}
	public List<String> BrokerProductInsertValidation(BrokerLoginProductInsReq req) {
		List<String> list = new ArrayList<String>();
		try {
			if("3".equals(req.getProductId())){
		    	if(req.getCommission() == null){
		    		list.add("1480");
		    	}
		    	if(req.getInsuranceEndLimit() == null){
		    		list.add("1481");
		    	}if(req.getMinPremiumAmount() == null){
		    		list.add("1482");
		    	}
		    	if("Y".equalsIgnoreCase(req.getProvision())){
		    		if(req.getLoadingPremium() == null){
		    			list.add("1483");
		    		}if(req.getDiscountPremium() == null){
		    			list.add("1484");
		    		}
		    	}else{
					req.setLoadingPremium(null);
					req.setDiscountPremium(null);
				}
		    	if(StringUtils.isBlank(req.getBackDateAllowed())){
		    		list.add("1485");
		    	}else if(!StringUtils.isNumeric(req.getBackDateAllowed())){
		    		list.add("1486");
		    	}
			}if(StringUtils.isBlank(req.getPayReceipt())){
				list.add("1487");
	    	}if(StringUtils.isBlank(req.getFreight())){
	    		list.add("1488");
	    	}
	    	if(StringUtils.isBlank(req.getRemarks())){
	    		list.add("1489");
	    	}	
		}catch (Exception e) {
			log.info(e);
		}
		return list; 
	}
	public List<String> IssuerInsertValidation(AdminNewIssuerReq req) {   
		List<String> list = new ArrayList<String>();
		try {
			List<LoginMaster> admin=loginRepo.findByLoginId(req.getLoginId()); 
			if(StringUtils.isBlank(req.getIssuerName())) {
				list.add("1450");
			}if(StringUtils.isBlank(req.getEmailId().trim())) {
				list.add("1451");
			}else if(StringUtils.contains(req.getEmailId(), " ")) {
				list.add("1452");
			}else if(!emailValidate(req.getEmailId())) {
				list.add("1453");
			}if(StringUtils.isBlank(req.getLoginId().trim())) {
				list.add("1454");
			}else if(StringUtils.contains(req.getLoginId(), " ")) {
				list.add("1455");
			}else if(admin.size()>0 && !"edit".equalsIgnoreCase(req.getOptionMode())) {
				list.add("1456");
			}if(StringUtils.isBlank(req.getCoreLoginId().trim())) {
				list.add("1457");
			}else if(req.getCoreLoginId().length() > 3) {
				list.add("1390");
			}if(!"edit".equalsIgnoreCase(req.getOptionMode())){
			if(StringUtils.isBlank(req.getPassword().trim())) {
				list.add("1458");
			}else if(StringUtils.contains(req.getPassword(), " ")) {
				list.add("1459");
		 	}
			}if(CollectionUtils.isEmpty(req.getProductInfo())) {
		 		list.add("1460");
		 	}if(CollectionUtils.isEmpty(req.getAttachedBranchInfo())){
		 		list.add("1461");
		 	}if(StringUtils.isEmpty(req.getEffectiveDate())){
		 		list.add("1462");
	    	}
		 	/*if(StringUtils.isBlank(req.getRegionCode())) {
				list.add("1224");
			}else {
				Optional<RegionMaster> region = regionMasRep.findByRegionMasterIdRegioncodeAndStatus(req.getRegionCode(),"Y");
				if(!region.isPresent()) {
					list.add("1235");
				}
			}
			if(StringUtils.isBlank(req.getBranchCode())) {
				list.add("1223");
			}else if(StringUtils.isNotBlank(req.getRegionCode())) {
				List<BranchMaster> branch = branchRep.findByBranchmasterpkBranchcodeAndBranchmasterpkRegioncodeAndStatus(req.getBranchCode(),req.getRegionCode(),"Y");
				if(CollectionUtils.isEmpty(branch)) {
					list.add("1236");
				}
			}*/
		}catch (Exception e) {
			log.info(e);
		}
		return list;  
	}
	public List<String> AdminInsertValidation(NewAdminInsertReq req) {
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isBlank(req.getUserType())) {
				list.add("1463");
			}if(StringUtils.isBlank(req.getRegionCode())) {
				list.add("1224");
			}else {
				/*Optional<RegionMaster> region = regionMasRep.findByRegionMasterIdRegioncodeAndStatus(req.getRegionCode(),"Y");
				if(!region.isPresent()) {
					list.add("1235");
				}*/
			}
			if(StringUtils.isBlank(req.getBranchCode())) {
				list.add("1223");
			}else if(StringUtils.isNotBlank(req.getRegionCode())) {
				List<BranchMaster> branch = branchmasterrepository.findByBranchCodeAndRegionCodeAndStatus(req.getBranchCode(),req.getRegionCode(),"Y");
				if(CollectionUtils.isEmpty(branch)) {
					list.add("1236");
				}
			}
			if(CollectionUtils.isEmpty(req.getAttachedBranchInfo())) {
				list.add("1462");
			}if(StringUtils.isBlank(req.getUserName())) {
				list.add("1464");
			}if(StringUtils.isBlank(req.getLoginId())) {
				list.add("1454");
			}else if(StringUtils.contains(req.getLoginId(), " ")) {
				list.add("1455");
			}
			if(!"edit".equalsIgnoreCase(req.getMode())){
			if(StringUtils.isBlank(req.getPassword())) {
				list.add("1458");
			}else if(StringUtils.contains(req.getPassword(), " ")) {
				list.add("1459");
			}
			
			LoginMaster data = autheRepository.findByloginId(req.getLoginId());
			
			if(data!=null) {
				list.add("1456");
			}
			}
			if(CollectionUtils.isEmpty(req.getProductInfo())) {
				list.add("1460");
			}if(CollectionUtils.isEmpty(req.getMenuInfo())) {
				list.add("1465");
			}if(StringUtils.isBlank(req.getEmail().trim())) {
				list.add("1451");
			}else if(StringUtils.contains(req.getEmail(), " ")) {
				list.add("1452");
			}else if(!emailValidate(req.getEmail().trim())) {
				list.add("1453");
			}if(StringUtils.isBlank(req.getStatus())) {
				list.add("1466");
			}
		}catch (Exception e) {
			log.info(e);
		}
		return list;  
		
	}


	public List<String> UserInsertValidation(UserMgtInsertRequest req) {  
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isEmpty(req.getAgencyCode())){
				list.add("1467");
	    	}if(StringUtils.isEmpty(req.getCustFirstName())){
	    		list.add("1413");
	    	}else if(StringUtils.isNumeric(req.getCustFirstName())){
	    		list.add("1414");
	    	}if(StringUtils.isEmpty(req.getNationality())){
	    		list.add("1470");
	    	}if(StringUtils.isEmpty(req.getCity())){
	    		list.add("1407");
	    	}if(StringUtils.isEmpty(req.getCountry())){
	    		list.add("1410");
	    	}if(StringUtils.isEmpty(req.getPoBox())){
	    		list.add("1472");
	    	}else if(!StringUtils.isNumeric(req.getPoBox())){
	    		list.add("1411");
	    	}if(!StringUtils.isBlank(req.getTelephoneNo()) && !StringUtils.isNumeric(req.getTelephoneNo())) {
	    		list.add("1416");
			}if(!StringUtils.isBlank(req.getMobileNo()) && !StringUtils.isNumeric(req.getMobileNo())) {
				list.add("1412");
			}if(StringUtils.isEmpty(req.getEmail().trim())) {
				list.add("1451");
	    	}else if(StringUtils.contains(req.getEmail(), " ")) {
	    		list.add("1452");
	    	}else if(!emailValidate(req.getEmail())){
	    		list.add("1453");
	    	}if(StringUtils.isEmpty(req.getLoginId().trim())) {
	    		list.add("1454");
	    	}else if(StringUtils.contains(req.getLoginId(), " ")) {
	    		list.add("1455");
	    	}
	    	if(!"edit".equalsIgnoreCase(req.getMode())){
	    	if(StringUtils.isEmpty(req.getPassword().trim())) {
	    		list.add("1458");
	    	}else if(StringUtils.contains(req.getPassword(), " ")) {
	    		list.add("1459");
	    	}if(StringUtils.isEmpty(req.getRePassword().trim())){
	    		list.add("1401");
        	}else if(!req.getPassword().equals(req.getRePassword().trim())){
        		list.add("1402");
        	}else if(!validPassword(req.getPassword().trim())){
        		list.add("1428");
        	}/*else if(queryem.getAdminInformation(req.getLoginId()).size()>0){
        		list.add("1456");
        	}
	    	if(StringUtils.isBlank(req.getRegionCode())) {
				list.add("1224");
			}else {
				Optional<RegionMaster> region = regionMasRep.findByRegionMasterIdRegioncodeAndStatus(req.getRegionCode(),"Y");
				if(!region.isPresent()) {
					list.add("1235");
				}
			}
			if(StringUtils.isBlank(req.getBranchCode())) {
				list.add("1223");
			}else if(StringUtils.isNotBlank(req.getRegionCode())) {
				List<BranchMaster> branch = branchRep.findByBranchmasterpkBranchcodeAndBranchmasterpkRegioncodeAndStatus(req.getBranchCode(),req.getRegionCode(),"Y");
				if(CollectionUtils.isEmpty(branch)) {
					list.add("1236");
				}
			}*/
	    	}	
		}catch (Exception e) {
			log.info(e);
		}
		return list;  
	}
	public UserProductValidReq UserProductInsertValidation(UserMgtProductInsertRequest req) {
		int count = 0;
		UserProductValidReq res = new UserProductValidReq();
		List<Map<String,Object>> commisionDetail = new ArrayList<Map<String,Object>>();
		List<String> list = new ArrayList<String>();
		try {
		for (int i = 0; i <req.getProductInfo().size(); i++) {
			if ("Y".equals(req.getProductInfo().get(i).getProductYN())) {
				if (!"11".equals(req.getProductInfo().get(i).getProductId())) {
					if (StringUtils.isBlank(req.getProductInfo().get(i).getSpecialDiscount())) {
						list.add("1490");
					} else if (!decimalValidate(req.getProductInfo().get(i).getSpecialDiscount())) {
						list.add("1491");
					}
					if (StringUtils.isEmpty(req.getProductInfo().get(i).getInsuranceEndLimit())) {
						list.add("1481");
					} else if (!decimalValidate(req.getProductInfo().get(i).getInsuranceEndLimit())) {
						list.add("1492");
					}
				}
				count++;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if ("Y".equalsIgnoreCase(req.getProductInfo().get(i).getProductYN()))
				map.put("product", "Y");
			else
				map.put("product", "N");

			map.put("uproductId", StringUtils.isBlank(req.getProductInfo().get(i).getProductId())?"0":req.getProductInfo().get(i).getProductId());
			map.put("uproductName", StringUtils.isBlank(req.getProductInfo().get(i).getProductName())?"":req.getProductInfo().get(i).getProductName());
			map.put("specialDis", StringUtils.isBlank(req.getProductInfo().get(i).getSpecialDiscount())?"0":req.getProductInfo().get(i).getSpecialDiscount());
			map.put("insEndLimit", StringUtils.isBlank(req.getProductInfo().get(i).getInsuranceEndLimit())?"0":req.getProductInfo().get(i).getInsuranceEndLimit());
			map.put("receipt", StringUtils.isBlank(req.getProductInfo().get(i).getPayReceipt())? "N" : req.getProductInfo().get(i).getPayReceipt());
			map.put("freight", StringUtils.isBlank(req.getProductInfo().get(i).getUserFreight())? "N" : req.getProductInfo().get(i).getUserFreight());
			if ("11".equals(req.getProductInfo().get(i).getProductId()) && "Y".equals(req.getProductInfo().get(i).getProductYN())) {
				String opencover = "";
				for(int j=0;j<req.getOpenCoverInfo().size();j++) {
					opencover +=req.getOpenCoverInfo().get(j).getOpenCoverNo();
					if(j<req.getOpenCoverInfo().size()-1) {
						opencover +=",";
					}
				}
				map.put("open_cover_no", opencover);
			}else {
				map.put("open_cover_no", "");
			}
			commisionDetail.add(map);
			
		}
		if (count == 0) {
			list.add("1493");
		}
		res.setMapvalue(commisionDetail);
		res.setValidation(list);
		}catch (Exception e) {
			log.info(e);
		}
		return res;
	}
}  