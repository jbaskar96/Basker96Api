package com.maan.common.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.maan.common.bean.PolicyLocation;
import com.maan.common.bean.PolicyLocationId;

public interface PolicyLocationRepository extends JpaRepository<PolicyLocation, PolicyLocationId> {
	
	@Transactional
	@Modifying
	void deleteByPolicyNo(String policyNo); 
		
	List<PolicyLocation> findByPolicyNo(String policyNo);
	List<PolicyLocation> findByPolicyNoOrderByLocationIdAsc(String policyNo);
}
		


