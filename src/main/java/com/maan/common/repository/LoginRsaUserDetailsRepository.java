package com.maan.common.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.LoginRsaUserDetails;
import com.maan.common.bean.LoginRsaUserDetailsID;

@Repository 
public interface LoginRsaUserDetailsRepository extends JpaRepository<LoginRsaUserDetails, LoginRsaUserDetailsID>{
  
	@Modifying
	@Transactional
	int deleteByLoginRsaUserDetailsidLoginidAndLoginRsaUserDetailsidBrokercode(String loginId, String brokerCode);

	List<LoginRsaUserDetails> findByLoginRsaUserDetailsidLoginidAndLoginRsaUserDetailsidBrokercode(String loginId,
			String agencyCode);

	List<LoginRsaUserDetails> findByLoginRsaUserDetailsidLoginid(String loginId);
	
}
	
