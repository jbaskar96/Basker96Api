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
public class CommanUserProductEditResp {

	@JsonProperty("ProductResponse")
	private List<UserProductEditResp> productResponse;
	@JsonProperty("CommisionResponse")
	private List<UserCommisionEditResp> commisionResponse;
	
}
