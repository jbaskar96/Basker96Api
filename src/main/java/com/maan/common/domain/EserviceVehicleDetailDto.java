/*
 * Java domain class for entity "EserviceVehicleDetail" 
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:39 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
package com.maan.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.bean.EserviceRequestDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



/**
 * Domain class for entity "EserviceVehicleDetail"
 *
 * @author Telosys Tools Generator
 *
 */
 
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EserviceVehicleDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY 
    //----------------------------------------------------------------------
	@JsonProperty("Vehiclereferenceno")
    private String vehicleReferenceno ;
	@JsonProperty("Vehicleid")
    private BigDecimal vehicleId    ;
	@JsonProperty("Customerid")
    private BigDecimal customerId   ;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
	@JsonProperty("Chassisno")
    private String     chassisNo    ;
	@JsonProperty("Plateno")
    private String     plateNo      ;
	@JsonProperty("Platechar")
    private String     plateChar    ;
	@JsonProperty("Platecharen")
    private String     platecharEn  ;
	@JsonProperty("Makeid")
    private BigDecimal makeId       ;
	@JsonProperty("Makename")
    private String     makeName     ;
	@JsonProperty("Vehiclemodel")
    private BigDecimal vehicleModel ;
	@JsonProperty("Modelname")
    private String     modelName    ;
	@JsonProperty("Vehiclebodytypeid")
    private BigDecimal vehicleBodytypeId ;
	@JsonProperty("Vehiclebodytypename")
    private String     vehicleBodytypeName ;
	@JsonProperty("Vehilcecolorid")
    private BigDecimal vehilceColorid ;
	@JsonProperty("Vehiclecolor")
    private String     vehicleColor ;
	@JsonProperty("Seatingcapacity")
    private BigDecimal seatingCapacity ;
	@JsonProperty("Enginecapacity")
    private BigDecimal engineCapacity ;
	@JsonProperty("Manfyear")
    private BigDecimal manfyear     ;
	@JsonProperty("Engineno")
    private String     engineNo     ;
	@JsonProperty("Vehage")
    private BigDecimal vehage       ;
	@JsonProperty("Claimamt")
    private BigDecimal claimamt     ;
	@JsonProperty("Claimbonus")
    private BigDecimal claimbonus   ;
	@JsonProperty("Vehusageid")
    private BigDecimal vehusageId   ;
	@JsonProperty("Status")
    private String     status       ;
	@JsonProperty("Entrydate")
    private Date       entrydate    ;
	@JsonProperty("Remarks")
    private String     remarks      ;
	@JsonProperty("Civilid")
    private String     civilid      ;
	@JsonProperty("Policystartdate")
    private Date       policyStartdate ;
	@JsonProperty("Leasedyn")
    private String     leasedyn     ;
	@JsonProperty("Suminsured")
    private BigDecimal sumInsured   ;
	@JsonProperty("Electricalsi")
    private BigDecimal electricalSi ;
	@JsonProperty("Nonelectricalsi")
    private BigDecimal nonelectricalSi ;
	@JsonProperty("Excesslimit")
    private BigDecimal excessLimit  ;
	@JsonProperty("Registrationno")
    private String     registrationNo ;
	@JsonProperty("Vehiclevalue")
    private BigDecimal vehicleValue ;
	@JsonProperty("Bankoffinance")
    private String     bankOfFinance ;
	@JsonProperty("Vehicleusage")
    private String     vehicleUsage ;
	@JsonProperty("Driverid")
    private String     driverId     ;
	@JsonProperty("Driverdob")
    private Date       driverDob    ;
	@JsonProperty("Driverclaimdetailyn")
    private String     driverClaimDetailyn ;
	@JsonProperty("Pastclaimyn")
    private String     pastClaimYn  ;
	@JsonProperty("Claimfreeyears")
    private String     claimFreeYears ;
	@JsonProperty("Claimamount")
    private BigDecimal claimAmount  ;
	@JsonProperty("Oldpolicyno")
    private BigDecimal oldPolicyno  ;
	@JsonProperty("Oldpolicyexpirydate")
    private Date       oldPolicyExpirydate ;
	@JsonProperty("Oldpolicyinsurancecompany")
    private String     oldPolicyInsuranceCompany ;
	@JsonProperty("Vehicleowneryn")
    private String     vehicleownerYn ;
	@JsonProperty("Bankid")
    private BigDecimal bankId       ;
	@JsonProperty("Oldpolicycompanyid")
    private BigDecimal oldPolicyCompanyid ;

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
	@JsonProperty("Eservicerequestdetail")
    private EserviceRequestDetail eserviceRequestDetail ;

      
	  
	  
}
