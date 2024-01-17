/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:49 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:49 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.bean;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Table;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;




/**
* Domain class for entity "MotorPolicyCoverage"
*
* @author Telosys Tools Generator
*
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@IdClass(MotorPolicyCoverageId.class)
@Table(name="MOTOR_POLICY_COVERAGE")


public class MotorPolicyCoverage implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="APPLICATION_NO", nullable=false)
    private BigDecimal applicationNo ;

    @Id
    @Column(name="X_ID", nullable=false)
    private BigDecimal xId ;

    @Id
    @Column(name="Y_ID", nullable=false)
    private BigDecimal yId ;

    @Id
    @Column(name="GROUP_ID", nullable=false)
    private BigDecimal groupId ;

    @Id
    @Column(name="VEHICLE_ID", nullable=false)
    private BigDecimal vehicleId ;

    @Id
    @Column(name="POLICYSUBTYPE", nullable=false)
    private BigDecimal policysubtype ;

    @Id
    @Column(name="BRANCH_CODE", nullable=false, length=10)
    private String     branchCode ;

    //--- ENTITY DATA FIELDS 
    @Column(name="X_DATA_NAME", length=500)
    private String     xDataName ;

    @Column(name="Y_DATA_NAME", length=500)
    private String     yDataName ;

    @Column(name="DATA_VALUE")
    private BigDecimal dataValue ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="UPDATED_DATE")
    private Date       updatedDate ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Column(name="DESCRIPTION", length=250)
    private String     description ;

    @Column(name="IS_SELECTED", length=1)
    private String     isSelected ;

    @Column(name="IS_ADDON", length=1)
    private String     isAddon ;

    @Column(name="DELETABLE", length=1)
    private String     deletable ;

    @Column(name="RATE")
    private BigDecimal rate ;

    @Column(name="BASE_RATE")
    private BigDecimal baseRate ;

    @Column(name="MIN_PREMIUM")
    private BigDecimal minPremium ;

    @Column(name="REFERAL_STATUS", length=10)
    private String     referalStatus ;

    @Column(name="SUMINSURED")
    private BigDecimal suminsured ;

    @Column(name="DISPLAY_ORDER")
    private BigDecimal displayOrder ;

    @Column(name="USD_DATA_VALUE")
    private BigDecimal usdDataValue ;

    @Column(name="CURRENCY_ID")
    private BigDecimal currencyId ;

    @Column(name="USD_SUM_INSURED")
    private BigDecimal usdSumInsured ;

    @Column(name="MIN_PREMIUM_USD")
    private BigDecimal minPremiumUsd ;

    @Column(name="GROUP_DESC", length=1000)
    private String     groupDesc ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}


