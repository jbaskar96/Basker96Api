package com.maan.common.upload.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpMailReq {

	@JsonProperty("notificationType")
	private String notificationtype;
	@JsonProperty("notificationTemplateId")
	private String notificationtemplateid;
	@JsonProperty("autoFrameYn")
	private String autoframeyn;
	@JsonProperty("keyvalues")
	private List<String> keyvalues;
	@JsonProperty("insuranceId")
	private String insuranceid;
	@JsonProperty("fileattach")
	private String fileattach;
	
}
