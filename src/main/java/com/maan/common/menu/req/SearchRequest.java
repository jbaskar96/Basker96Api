package com.maan.common.menu.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
	
	private String aggregatortype;
	
	private String searchby;

	private String searchvalue;

	private String loginid;

	private String applicationid;

	private String searchvalue1;

	private String subusertype;
	
	@JsonProperty("ProductId")
	private String productid;

}
