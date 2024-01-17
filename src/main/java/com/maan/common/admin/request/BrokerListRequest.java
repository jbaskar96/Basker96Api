package com.maan.common.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BrokerListRequest {

	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ProductId")
	private String productId;
}
