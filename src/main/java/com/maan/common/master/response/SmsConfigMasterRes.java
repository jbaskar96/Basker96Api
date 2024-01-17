/*
 * Java domain class for entity "SmsConfigMaster" 
 * Created on 2021-11-19 ( Date ISO 2021-11-19 - Time 13:16:53 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
package com.maan.common.master.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Domain class for entity "SmsConfigMaster"
 *
 * @author Telosys Tools Generator
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmsConfigMasterRes implements Serializable {

	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY
	// ----------------------------------------------------------------------
	@JsonProperty("InsuranceId")
	private BigDecimal insId;

	// ----------------------------------------------------------------------
	// ENTITY DATA FIELDS
	// ----------------------------------------------------------------------
	@JsonProperty("Smspartyurl")
	private String smsPartyUrl;
	@JsonProperty("Smsusername")
	private String smsUserName;
	@JsonProperty("Smsuserpass")
	private String smsUserPass;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Secureyn")
	private String secureYn;
	@JsonProperty("Senderid")
	private String senderid;
	@JsonProperty("Remarks")
	private String     remarks ;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonProperty("Effectivedate")
    private Date       effectiveDate ;
	@JsonProperty("Amendid")
    private BigDecimal amendid      ;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonProperty("Entrydate")
    private Date       entrydate    ;

}
