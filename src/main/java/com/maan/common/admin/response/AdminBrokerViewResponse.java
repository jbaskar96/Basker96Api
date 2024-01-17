package com.maan.common.admin.response;

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
public class AdminBrokerViewResponse {

	@JsonProperty("BrokerDetails")
	private List<BrokerDetailsResponse> brokerDetails;
	@JsonProperty("BrokerCommissionDetails")
	private List<BroCommissionDetailsResp> commissionDetails;
	@JsonProperty("BranchDetails")
	private List<BranchDetailsResp> branchDetails;
}
