package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.MScreen;
import com.maan.common.bean.MScreenId;

@Repository
public interface MScreenRepository  extends JpaRepository<MScreen, MScreenId>{

	List<MScreen> findByMsModuleid(BigDecimal msModuleid);
	
	@Query(value="SELECT NVL(MAX (MS_SCREENID) + 1,1) AS  Screen_Id FROM M_SCREEN WHERE MS_MODULEID = ?1",nativeQuery=true)
	Long getScreenId(Long screenId);
	
	List<MScreen> findByMsModuleidAndMsScreenid(BigDecimal msModuleid,BigDecimal msScreenid);
	
	
	
}
