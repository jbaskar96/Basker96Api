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
public class BrokerInfoResponse {

	/*@JsonProperty("BrokerInformation")
	private List<BrokerInformationResp> brokerInformation;*/
	@JsonProperty("OrigCountryId")
	private String origCountryId;
	@JsonProperty("DestCountryId")
	private String destCountryId;
	@JsonProperty("ExecutiveId")
	private String executiveId;
	@JsonProperty("BrokerCode")
	private String brokerCode;
}
