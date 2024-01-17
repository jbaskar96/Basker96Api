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
public class LoginRsaUserDetailsID implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name="LOGIN_ID")
	private String loginid;
	@Column(name="BROKER_CODE")
	private String brokercode;
	@Column(name="PRODUCT_ID")
	private Long productid;
	@Column(name="BRANCH_CODE")
	private String branchcode;
	@Column(name="AMEND_ID")
	private Long amendid;
}
