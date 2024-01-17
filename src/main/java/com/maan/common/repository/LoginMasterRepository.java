/*
 * Java domain class for entity "LoginMaster" 
 * Created on 2021-07-31 ( Date ISO 2021-07-31 - Time 18:13:33 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2021-07-31 ( 18:13:33 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.LoginMasterId;

/**
 * <h2>LoginMasterRepository</h2>
 *
 * createdAt : 2021-07-31 - Time 18:13:33
 * <p>
 * Description: "LoginMaster" Repository
 */
 
 
 
public interface LoginMasterRepository  extends JpaRepository<LoginMaster,LoginMasterId > , JpaSpecificationExecutor<LoginMaster> {

	List<LoginMaster> findByLoginId(String username);
	
	List<LoginMaster> findByLoginIdAndOaCode(String loginId, String brokerCode);
	List<LoginMaster> findByLoginIdAndAppId(String userId, String app_id);
	List<LoginMaster> findByLoginIdAndAppIdAndStatusIn(String username,String appId,List<String> status);
	@Modifying
	@Transactional
	@Query(value="UPDATE LOGIN_MASTER SET PASSWORD = ?1,ENCRYPT_PASSWORD=?2 WHERE login_id=?3",nativeQuery=true)
	void updatePasswordInLM(String password,String encrpass,String login_id);
	
	Optional<LoginMaster> findByCompanyIdAndBranchCodeAndLoginIdAndPassword(BigDecimal comanyId,String branchCode, String userId, String epass);
	@Query(value="select count(*) from branch_master bm,login_master lm where bm.branch_code =?1 and bm.branch_code=LM.BRANCH_CODE",nativeQuery=true)
	Long validateBranch(String branchCode);
	
	@Query(value="select count(*) from insurance_company_master bcm,login_master lm where bcm.INS_ID =?1 and BCM.INS_ID=LM.COMPANY_ID",nativeQuery=true)
	Long validateComanyId(String companyId);
	List<LoginMaster> findByAppIdAndUserMailAndCompanyIdAndBranchCode(String string, String mailId,BigDecimal companyId, String branchCode);
	List<LoginMaster> findByLoginIdAndAppIdAndUserMailAndCompanyIdAndBranchCode(String userId, String appId, String mailId,BigDecimal companyId,String BranchCode);
	
	@Query(value="SELECT PRODUCT_ID, PRODUCT_NAME FROM PRODUCT_MASTER WHERE STATUS='Y' AND BRANCH_CODE=(SELECT   BELONGING_BRANCH FROM   BRANCH_MASTER BM WHERE   BRANCH_CODE = ?1 AND STATUS = 'Y'  AND AMEND_ID =(SELECT   MAX (AMEND_ID) FROM   BRANCH_MASTER BM1 WHERE   BM1.BRANCH_CODE = BM.BRANCH_CODE  AND BM1.STATUS = BM.STATUS)) AND PRODUCT_ID IN (SELECT PRODUCT_ID FROM LOGIN_USER_DETAILS WHERE AGENCY_CODE=(SELECT AGENCY_CODE FROM LOGIN_MASTER WHERE  LOGIN_ID=?2 and BRANCH_CODE=?3 and  company_id=?4) AND STATUS='Y') ORDER BY DISPLAY_ORDER ASC",nativeQuery=true)
	List<Map<String, Object>> getProductBroker(String branchCode, String loginId,String branchcode, String companyId);
	@Query(value="SELECT PRODUCT_ID FROM LOGIN_MASTER WHERE LOGIN_ID=?1",nativeQuery=true)
	String getProductId(String loginId);

	LoginMaster findByLoginIdAndUsertypeAndCompanyId(String loginId, String usertype,BigDecimal companyid);
	LoginMaster findByLoginIdAndUsertype(String loginId, String usertype);


	LoginMaster findByLoginIdAndCompanyIdAndBranchCode(String createdby, BigDecimal bigDecimal, String branchCode);
	LoginMaster findByLoginIdAndCompanyId(String loginid, BigDecimal insuranceid);
	List<LoginMaster> findByUsertypeOrderByOaCodeAsc(String usertype);

	LoginMaster findByloginId(String loginId);

	List<LoginMaster> findByCompanyIdAndBranchCode(BigDecimal bigDecimal, String branchCode);

	List<LoginMaster> findAllByOrderByOaCodeDesc();

	
	LoginMaster findByLoginIdAndCompanyIdAndBranchCodeAndRegionCode(String createdby, BigDecimal bigDecimal, String branchCode,String regionCode);
	
	LoginMaster findByLoginIdOrderByEntryDate(String username);
	
	Optional<LoginMaster> findByLoginIdOrderByLoginId(String loginId);
	List<LoginMaster> findByBrokerCodes(String brokerCodes);
	List<LoginMaster> findByOaCodeIn(List<String> oaCode);

	List<LoginMaster> findByoaCodeAndBranchCode(String agencyCode, String branchCode);

	List<LoginMaster> findByAgencyCode(String agencyCode);

	List<LoginMaster> findByBranchCodeAndUsertype(String branchCode, String string);

	List<LoginMaster> findByBranchCodeAndUsertypeAndLoginId(String branchCode, String string, String loginId);


	

	
	List<LoginMaster> findByUsertypeAndBranchCodeAndOaCodeNotAndLoginIdNotIn(String usertype,String branchCode,String oaCode,List<String> loginId);
	
	List<LoginMaster> findByUseridInAndBranchCodeAndLoginId(List<BigDecimal> userid,String branchCode,String LoginId);
	
	List<LoginMaster> findByUseridInAndBranchCode(List<BigDecimal> userid,String branchCode);
	
}
