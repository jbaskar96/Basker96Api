package com.maan.common.auth.dto.resp;

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
public class LoginProductResponse {

	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("ProductName")
	private String productName;
	@JsonProperty("SchemeList")
    private List<LoginProductResponse> schemeList;
}

