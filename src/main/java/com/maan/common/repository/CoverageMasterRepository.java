package com.maan.common.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.common.bean.CoverageMaster;

public interface CoverageMasterRepository  extends JpaRepository<CoverageMaster,BigDecimal > , JpaSpecificationExecutor<CoverageMaster> {

	CoverageMaster findByMcCoverid(BigDecimal mcCoverid);
	
}
