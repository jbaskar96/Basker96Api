package com.maan.common.upload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionReq {

	private String field;
	private String operator;
	private String value;
	private String connector;
	private String sno;

}
