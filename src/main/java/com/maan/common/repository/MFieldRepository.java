package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.MField;
import com.maan.common.bean.MFieldId;

@Repository
public interface MFieldRepository extends JpaRepository<MField, 	MFieldId>{

	@Query(value="SELECT NVL(MAX (MF_FIELDID) + 1,1) AS  MF_FIELDID FROM M_FIELD WHERE MF_MODULEID = ?1 and MF_SCREENID =?2 and MF_SUBSCREENID =?3"
			+ "",nativeQuery = true)
	Long getFieldId(long moduleId, long screenId, long subScreenId);
	
	List<MField> findByMfModuleidAndMfScreenidAndMfSubscreenidOrderByMfFieldidAsc(BigDecimal mfModuleid ,BigDecimal mfScreenid,BigDecimal mfSubscreenid);
	
	List<MField> findByMfModuleidAndMfScreenid(BigDecimal mfModuleid ,BigDecimal mfScreenid);
	
	List<MField>findByMfModuleidAndMfScreenidAndMfSubscreenid(BigDecimal mfModuleid,BigDecimal mfScreenid,BigDecimal mfSubscreenid);
	
	MField findByMfModuleidAndMfScreenidAndMfSubscreenidAndMfFieldid(BigDecimal mfModuleid,BigDecimal mfScreenid,BigDecimal mfSubscreenid,BigDecimal mfFieldid);
}
