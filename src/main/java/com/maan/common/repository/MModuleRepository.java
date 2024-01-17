package com.maan.common.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.MModule;

@Repository
public interface MModuleRepository  extends JpaRepository<MModule, BigDecimal>{
	
	@Query(value="SELECT NVL(MAX (MM_MODULEID) + 1, 1) as MM_MODULEID FROM M_MODULE",nativeQuery=true)
	Long getModuleid();

	MModule findByMmModuleid(BigDecimal bigDecimal);
	


}
