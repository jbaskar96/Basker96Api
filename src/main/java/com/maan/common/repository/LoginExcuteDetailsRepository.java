package com.maan.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.LoginExcuteDetails;
import com.maan.common.bean.LoginExcuteDetailsID;

@Repository
public interface LoginExcuteDetailsRepository extends JpaRepository<LoginExcuteDetails, LoginExcuteDetailsID> {

	List<LoginExcuteDetails> findByLoginExcuteidAcexecutiveid(Long acexecutiveid);


	List<LoginExcuteDetails> findByStatusOrderByLoginExcuteidAmendIdDesc(String string);
	
}