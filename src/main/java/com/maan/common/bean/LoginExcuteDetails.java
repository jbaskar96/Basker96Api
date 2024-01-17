package com.maan.common.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="LOGIN_EXECUTIVE_DETAILS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginExcuteDetails {

	@EmbeddedId
	private LoginExcuteDetailsID loginExcuteid;
	
	@Column(name="AC_EXECUTIVE_NAME")
	private String acexecutiveName;
	@Column(name="EFFECTIVE_DATE")
	private String effectivedate;
	@Column(name="COMMISSION")
	private Long commission;
	@Column(name="RSACODE")
	private String rsacode;
	@Column(name="STATUS")
	private String status;
}
