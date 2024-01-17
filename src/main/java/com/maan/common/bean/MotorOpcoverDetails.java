/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:48 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:48 )
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
* Domain class for entity "MotorOpcoverDetails"
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
@IdClass(MotorOpcoverDetailsId.class)
@Table(name="MOTOR_OPCOVER_DETAILS")


public class MotorOpcoverDetails implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="POLICY_SUBTYPE_ID", nullable=false)
    private BigDecimal policySubtypeId ;

    @Id
    @Column(name="OPCOVER_ID", nullable=false)
    private BigDecimal opcoverId ;

    @Id
    @Column(name="OPCOVERSUB_ID", nullable=false)
    private BigDecimal opcoversubId ;

    @Id
    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    @Id
    @Column(name="GROUP_ID", nullable=false)
    private BigDecimal groupId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="COREAPP_CODE", nullable=false, length=10)
    private String     coreappCode ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="MANDATORY", length=1)
    private String     mandatory ;

    @Column(name="RATE")
    private BigDecimal rate ;

    @Column(name="DESCRIPTION", length=250)
    private String     description ;

    @Column(name="IS_SELECTED", length=1)
    private String     isSelected ;

    @Column(name="IS_ADDON", length=1)
    private String     isAddon ;

    @Column(name="DELETABLE", length=1)
    private String     deletable ;

    @Column(name="CALC_TYPE", length=1)
    private String     calcType ;

    @Temporal(TemporalType.DATE)
    @Column(name="STARTDATE")
    private Date       startdate ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENDDATE")
    private Date       enddate ;

    @Column(name="AMOUNT")
    private BigDecimal amount ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="OPCOVER_ID", referencedColumnName="OPCOVER_ID", insertable=false, updatable=false)
    private MotorOpcoverMaster motorOpcoverMaster ; 

    @ManyToOne
    @JoinColumn(name="POLICY_SUBTYPE_ID", referencedColumnName="POLICYTYPE_ID", insertable=false, updatable=false)
    private MotorPolicytypeMaster motorPolicytypeMaster ; 

    @ManyToOne
    @JoinColumn(name="GROUP_ID", referencedColumnName="GROUP_ID", insertable=false, updatable=false)
    private MotorGroupMaster motorGroupMaster ; 



}


