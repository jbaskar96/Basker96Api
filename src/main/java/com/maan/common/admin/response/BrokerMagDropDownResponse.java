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
public class BrokerMagDropDownResponse {

	@JsonProperty("AcExecutiveId")
	private String acExecutiveId;
	@JsonProperty("AcExecutiveName")
	private String acExecutiveName;
}
