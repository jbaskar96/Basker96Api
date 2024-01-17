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
public class UserMgtProductInsertRequest {

	@JsonProperty("OpenCoverInfo")
	private List<UsermgtOpencoverReq> openCoverInfo;
	@JsonProperty("UserAgencyCode")
	private String userAgencyCode;
	@JsonProperty("CustomerName")
	private String customerName;
	@JsonProperty("AgencyCode")
	private String agencyCode;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("ProductInfo")
	private List<UserMgtProductInfoRequest> productInfo;
}
