package com.maan.common.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminBrokerListReq {

	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ApplicationId")
	private String applicationId;
}
