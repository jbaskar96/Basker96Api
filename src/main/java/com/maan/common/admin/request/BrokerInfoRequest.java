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
public class BrokerInfoRequest {

	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("AgencyCode")
	private String agencyCode;
}
