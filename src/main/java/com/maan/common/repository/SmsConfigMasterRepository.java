/*
 * Java domain class for entity "SmsConfigMaster" 
 * Created on 2022-02-02 ( Date ISO 2022-02-02 - Time 15:32:55 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-02-02 ( 15:32:55 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.common.bean.SmsConfigMaster;
/**
 * <h2>SmsConfigMasterRepository</h2>
 *
 * createdAt : 2022-02-02 - Time 15:32:55
 * <p>
 * Description: "SmsConfigMaster" Repository
 */
 
 
 
public interface SmsConfigMasterRepository  extends JpaRepository<SmsConfigMaster,BigDecimal > , JpaSpecificationExecutor<SmsConfigMaster> {

	List<SmsConfigMaster> findByInsId(BigDecimal insId);

}
