/*
 * Java domain class for entity "MailMaster" 
 * Created on 2022-01-13 ( Date ISO 2022-01-13 - Time 18:04:49 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
package com.maan.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



/**
 * Domain class for entity "MailMaster"
 *
 * @author Telosys Tools Generator
 *
 */
 
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailMasterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY 
    //----------------------------------------------------------------------
	@JsonProperty("Applicationid")
    private String     applicationId ;
	@JsonProperty("Inscompanyid")
    private BigDecimal insCompanyId ;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
	@JsonProperty("Mailcc")
    private String     mailCc       ;
	@JsonProperty("Smtphost")
    private String     smtpHost     ;
	@JsonProperty("Smtpuser")
    private String     smtpUser     ;
	@JsonProperty("Smtppwd")
    private String     smtpPwd      ;
	@JsonProperty("Expdate")
    private String     expDate      ;
	@JsonProperty("Exptime")
    private String     expTime      ;
	@JsonProperty("Pwdcnt")
    private BigDecimal pwdCnt       ;
	@JsonProperty("Pwdlen")
    private BigDecimal pwdLen       ;
	@JsonProperty("Homeapplicationid")
    private String     homeApplicationId ;
	@JsonProperty("Address")
    private String     address      ;
	@JsonProperty("Status")
    private String     status       ;
	@JsonProperty("Remarks")
    private String     remarks      ;
	@JsonProperty("Companyname")
    private String     companyName  ;
	@JsonProperty("Toaddress")
    private String     toAddress    ;
	@JsonProperty("Authorizyn")
    private String     authorizYn   ;
	@JsonProperty("Smtpport")
    private BigDecimal smtpPort     ;
	@JsonProperty("Effectivedate")
    private Date       effectiveDate ;
	@JsonProperty("Amendid")
    private BigDecimal amendid      ;
	@JsonProperty("Entrydate")
    private Date       entrydate    ;

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

      
	  
	  
}
