/*
 * Java domain class for entity "OfsDataDetails" 
 * Created on 2022-01-17 ( Date ISO 2022-01-17 - Time 11:34:42 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-01-17 ( 11:34:42 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.maan.common.bean.OfsDataDetails;
import com.maan.common.bean.OfsDataDetailsId;
/**
 * <h2>OfsDataDetailsRepository</h2>
 *
 * createdAt : 2022-01-17 - Time 11:34:42
 * <p>
 * Description: "OfsDataDetails" Repository
 */
 
 
 
public interface OfsDataDetailsRepository  extends JpaRepository<OfsDataDetails,OfsDataDetailsId > , JpaSpecificationExecutor<OfsDataDetails> {


	List<OfsDataDetails> findByQuoteNoAndSchemeId(BigDecimal quoteno, BigDecimal schemeid);
   
	@Query(value="Select sum(Premium_Amount)  from Ofs_Data_Details Where Quote_no=?1 and Scheme_Id=?2 and COVERAGES_ID in (select MF_FIELDID from m_field Where  upper(COVERAGETYPE)='B')", nativeQuery=true)
	BigDecimal getTotalPremium(BigDecimal quoteno, BigDecimal schemeid);
	@Query(value="Select sum(Premium_Amount)  from Ofs_Data_Details Where Quote_no=?1 and Scheme_Id=?2 and COVERAGES_ID in (select MF_FIELDID from m_field Where  upper(COVERAGETYPE)='O')", nativeQuery=true)
	BigDecimal getOptinalPremium(BigDecimal quoteno, BigDecimal schemeid); 

	void deleteByQuoteNo(BigDecimal quoteNo  );

	List<OfsDataDetails> findByQuoteNoAndLocationIdOrderByCoveragesIdAsc(BigDecimal quoteno, BigDecimal locationId);
	List<OfsDataDetails> findByQuoteNo(BigDecimal quoteNo);
}
