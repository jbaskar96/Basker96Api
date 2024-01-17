package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.SubCoverDetails;
import com.maan.common.bean.SubCoverDetailsId;

@Repository
public interface SubCoverDetailsRepository extends JpaRepository<SubCoverDetails, 	SubCoverDetailsId>{
	
	/*@Transactional
	@Modifying
	@Query(value="INSERT INTO SUB_COVER_DETAILS (QUOTE_NO, MF_MODULEID ,MF_SCREENID ,MF_SUBSCREENID ,MF_FIELDID ,MF_ADDCOV_ID ,employeeId ) VALUES (?1 ,?2, ?3, ?4, ?5 , ?6, ?7 )",nativeQuery=true)
	void saveData(@Param("employeeId") String name , Long quoteNo,Long moduleId,Long screenId ,Long subScreenId ,Long fieldId , Long addCoverId ,String subFieldValue  );
	*/
	List<SubCoverDetails> findByQuoteNoAndMfModuleidAndMfScreenidAndMfSubscreenidAndMfFieldid(BigDecimal quoteNo ,BigDecimal mfModuleid
			,BigDecimal mfScreenid ,BigDecimal mfSubscreenid ,BigDecimal mfFieldid );

}
