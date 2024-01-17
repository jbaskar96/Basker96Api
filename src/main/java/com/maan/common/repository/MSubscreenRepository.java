package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.MSubscreen;
import com.maan.common.bean.MSubscreenId;

@Repository
public interface MSubscreenRepository extends JpaRepository<MSubscreen, MSubscreenId> {
	
	@Query
	List<MSubscreen> findByMssModuleidAndMssScreenid(BigDecimal mssModuleid, BigDecimal mssScreenid);

	@Query(value = "SELECT NVL(MAX (MSS_SUBSCREENID) + 1,1) AS  SubScreen_Id FROM M_SUBSCREEN WHERE MSS_MODULEID = ?1 and MSS_SCREENID =?2",nativeQuery = true)
	Long getSubscreenId(long moduleId, long screenId);
	Optional<MSubscreen> findByMssModuleidAndMssScreenidAndMssSubscreenid(BigDecimal mssModuleid, BigDecimal mssScreenid,BigDecimal mssSubscreenid);

}
