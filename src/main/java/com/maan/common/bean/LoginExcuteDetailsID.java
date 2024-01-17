package com.maan.common.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LoginExcuteDetailsID implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name="AC_EXECUTIVE_ID")
	private Long acexecutiveid;
	@Column(name="COMPANY_ID")
	private Long companyId;
	@Column(name="AMEND_ID")
	private Long amendId;
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	
}
