package com.maan.common.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CountryMasterId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="COUNTRY_ID")
	private Long countryid;
	@Column(name="AMEND_ID")
	private Long amendid;
}
