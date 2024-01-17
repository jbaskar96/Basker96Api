package com.maan.common.upload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WhatsAppReq {

	private String product;
	private String type;
	private String mobile;
	private String code;
	private String name;
	private String url;
	private String remarks;
	private String status;

}
