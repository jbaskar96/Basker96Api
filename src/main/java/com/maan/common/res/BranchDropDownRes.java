package com.maan.common.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDropDownRes {

	@JsonProperty("Code")
	private String code;
	@JsonProperty("CodeDesc")
	private String codedesc;
	@JsonProperty("TypeId")
	private String typeid;
	@JsonProperty("RegionCode")
	private String regioncode;
}
