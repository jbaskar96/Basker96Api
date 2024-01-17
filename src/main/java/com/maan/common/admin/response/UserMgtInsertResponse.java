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
public class UserMgtInsertResponse {

	@JsonProperty("UserAgencyCode")
	private String agencyCode;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("Status")
	private boolean status=false;
	@JsonProperty("Errors")
	private List<Error> errors = null;
	
}
