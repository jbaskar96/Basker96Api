package com.maan.common.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminMenuResponse {

	@JsonProperty("CategoryDetailId")
	private String categoryDetailId;
	@JsonProperty("CategoryDetailName")
	private String categoryDetailName;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Status1")
	private String status1;
	@JsonProperty("ParentMenu")
	private String parentMenu;
	@JsonProperty("ParentMenuName")
	private String parentMenuName;
}
