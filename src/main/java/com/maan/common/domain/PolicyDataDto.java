/*
 * Java domain class for entity "PolicyData" 
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
 * Domain class for entity "PolicyData"
 *
 * @author Telosys Tools Generator
 *
 */
 
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PolicyDataDto implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY 
    //----------------------------------------------------------------------
	@JsonProperty("Policyno")
    private BigDecimal policyNo     ;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
	@JsonProperty("Firstname")
    private String     firstName    ;
	@JsonProperty("Lastname")
    private String     lastName     ;
	@JsonProperty("Email")
    private String     email        ;
	@JsonProperty("Mobilenumber")
    private String     mobileNumber ;
	@JsonProperty("Policystartdate")
    private Date       policyStartDate ;
	@JsonProperty("Customertype")
    private String     customerType ;
	@JsonProperty("Title")
    private String     title        ;
	@JsonProperty("Policyenddays")
    private String     policyEndDays ;

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

      
	  
	  
}