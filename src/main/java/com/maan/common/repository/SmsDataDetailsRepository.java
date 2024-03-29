/*
 * Java domain class for entity "LoginUserDetails" 
 * Created on 2022-01-13 ( Date ISO 2022-01-13 - Time 18:04:48 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-01-13 ( 18:04:48 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.common.bean.SmsDataDetails;
import com.maan.common.bean.SmsDataDetailsId;
/**
 * <h2>LoginUserDetailsRepository</h2>
 *
 * createdAt : 2022-01-13 - Time 18:04:48
 * <p>
 * Description: "LoginUserDetails" Repository
 */
 
 
 
public interface SmsDataDetailsRepository  extends JpaRepository<SmsDataDetails,SmsDataDetailsId > , JpaSpecificationExecutor<SmsDataDetails> {

}
