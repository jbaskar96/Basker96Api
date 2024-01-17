package com.maan.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maan.common.bean.PolicyData;

public interface PolicyDataRepository  extends JpaRepository<PolicyData, String>{
	
	
	@Query(value="SELECT NVL(MAX (POLICY_NO) + 1,1) AS  POLICYNO FROM POLICY_DATA"
			+ "",nativeQuery = true)
	String getPolicyNo();
	

}
