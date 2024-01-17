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
public class IssuerExcludedDeleteRequest {

	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("BrokerInfo")
	private List<ExcludedBrokerRequest> brokerInfo;
}
