/*
 * Java domain class for entity "BrokerBranchMaster" 
 * Created on 2022-01-29 ( Date ISO 2022-01-29 - Time 17:21:03 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-01-29 ( 17:21:03 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.common.admin.request.AttachedBranchReq;
import com.maan.common.bean.BrokerBranchMaster;
import com.maan.common.bean.BrokerBranchMasterId;
/**
 * <h2>BrokerBranchMasterRepository</h2>
 *
 * createdAt : 2022-01-29 - Time 17:21:03
 * <p>
 * Description: "BrokerBranchMaster" Repository
 */
 
 
 
public interface BrokerBranchMasterRepository  extends JpaRepository<BrokerBranchMaster,BrokerBranchMasterId > , JpaSpecificationExecutor<BrokerBranchMaster> {

	List<BrokerBranchMaster> findByMgenBranchIdInAndStatusAndEffectiveDateLessThanEqualOrderByEffectiveDateDesc(
			List<String> branchId, String string, Date date);

}