/*
 * Java domain class for entity "BrokerCommissionMaster" 
 * Created on 2022-01-13 ( Date ISO 2022-01-13 - Time 18:04:47 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-01-13 ( 18:04:47 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.common.bean.BrokerCommissionMaster;
import com.maan.common.bean.BrokerCommissionMasterId;
/**
 * <h2>BrokerCommissionMasterRepository</h2>
 *
 * createdAt : 2022-01-13 - Time 18:04:47
 * <p>
 * Description: "BrokerCommissionMaster" Repository
 */
 
 
 
public interface BrokerCommissionMasterRepository  extends JpaRepository<BrokerCommissionMaster,BrokerCommissionMasterId > , JpaSpecificationExecutor<BrokerCommissionMaster> {

}
