package com.maan.common.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="LOGIN_RSAUSER_DETAILS")
public class LoginRsaUserDetails {

	
	@EmbeddedId
	private LoginRsaUserDetailsID loginRsaUserDetailsid;
	
	@Column(name="COMMISSION")
	private Double commission;
	@Column(name="OPEN_COVER_NO")
	private String opencoverno;	
	@Column(name="ENTRY_DATE")
	private Date entrydate;
	@Column(name="START_DATE")
	private Date startdate;
	@Column(name="END_DATE")
	private Date enddate;
	@Column(name="REMARKS")
	private String remarks;
	@Column(name="STATUS")
	private String status;
	@Column(name="CANCEL_DATE")
	private Date canceldate;
	@Column(name="SUB_BRANCH")
	private String subbranch;
}
