package com.maan.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DropDownRes {

	@JsonProperty("Code")
	private String code;
	@JsonProperty("CodeDesc")
	private String message;

}
