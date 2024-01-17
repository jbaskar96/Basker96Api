package com.maan.common.admin.request;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProductValidReq {

	private List<Map<String,Object>> mapvalue;
	private List<String> validation;
}
