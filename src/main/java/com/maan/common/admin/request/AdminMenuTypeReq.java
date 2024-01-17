package com.maan.common.admin.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminMenuTypeReq {

	@JsonProperty("MenuInfo")
	private List<MenuIdRequest> menuInfo;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ProductId")
	private String productId;
}
