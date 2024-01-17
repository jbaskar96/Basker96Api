package com.maan.common.upload.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WhatsappReqClaim {
	
	@JsonProperty("Claimrefno")
	private String claimrefno;
	@JsonProperty("Partyno")
	private BigDecimal partyno;
	@JsonProperty("Losstypeid")
	private BigDecimal Losstypeid;
	@JsonProperty("Doctypeid")
	private BigDecimal doctypeid;

}
