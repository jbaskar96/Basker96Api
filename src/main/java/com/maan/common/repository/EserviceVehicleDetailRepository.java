/*
 * Java domain class for entity "EserviceVehicleDetail" 
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:39 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-02-11 ( 19:14:39 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.common.bean.EserviceVehicleDetail;
import com.maan.common.bean.EserviceVehicleDetailId;
/**
 * <h2>EserviceVehicleDetailRepository</h2>
 *
 * createdAt : 2022-02-11 - Time 19:14:39
 * <p>
 * Description: "EserviceVehicleDetail" Repository
 */
 
 
 
public interface EserviceVehicleDetailRepository  extends JpaRepository<EserviceVehicleDetail,EserviceVehicleDetailId > , JpaSpecificationExecutor<EserviceVehicleDetail> {

	List<EserviceVehicleDetail> findByVehicleReferencenoAndCustomerIdOrderByEntrydateDesc(String vehicleReferenceno ,BigDecimal customerId);

}