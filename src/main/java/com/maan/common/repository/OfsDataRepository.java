/*
 * Java domain class for entity "OfsData" 
 * Created on 2022-01-17 ( Date ISO 2022-01-17 - Time 11:34:42 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-01-17 ( 11:34:42 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.maan.common.bean.OfsData;
 
 
 
public interface OfsDataRepository  extends JpaRepository<OfsData,BigDecimal > , JpaSpecificationExecutor<OfsData> {
	@Query(value="Select Min_Premium_Amount From Login_User_Details Lud Where lud.scheme_id=?1 and Lud.Product_Id=?3 And Agency_Code= (Select Oa_Code From Login_Master Lm Where Lm.Login_Id= (Select Login_Id From Home_Position_Master Where  Quote_No=?2))  And Amend_Id=(Select Max(Amend_Id) From Login_User_Details Lm Where Lm.Agency_Code=Lud.Agency_Code And Lm.Product_Id=Lud.Product_Id and lm.scheme_id=?1)", nativeQuery=true)
	BigDecimal getMiniumumPremium(BigDecimal schmeid,BigDecimal quoteno,String productId);

	@Query(value ="SELECT (QUOTE_NO_SEQ.NEXTVAL) FROM DUAL" , nativeQuery=true)
	String getSeqQuoteNo();
	
	Page<OfsData>  findByLoginIdAndApplicationIdAndBranchcodeAndStatusAndSchemeId(Pageable paging ,String loginId ,String applicationId ,String branchcode,String status, BigDecimal schemeId );
	
	Page<OfsData>  findByLoginIdAndApplicationIdAndBranchcodeAndStatusAndSchemeIdAndEffectiveDateBefore(Pageable paging ,String loginId ,String applicationId ,String branchcode,
			String status, BigDecimal schemeId,Date effectiveDate );
	//Page<OfsData> findByLoginId(Pageable paging ,String loginId);
	
	OfsData findByQuoteNoAndLoginId(BigDecimal quoteNo,String loginId );
	OfsData findByQuoteNo(BigDecimal quoteNo);
}
