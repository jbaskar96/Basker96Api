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
import java.util.List;
import javax.persistence.*;




/**
* Domain class for entity "MotorOpcoverMaster"
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
@Table(name="MOTOR_OPCOVER_MASTER")


public class MotorOpcoverMaster implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="OPCOVER_ID", nullable=false)
    private BigDecimal opcoverId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    @Column(name="OPCOVER_DESC", length=1000)
    private String     opcoverDesc ;

    @Column(name="COREAPP_CODE", nullable=false, length=10)
    private String     coreappCode ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="PRODUCT", nullable=false)
    private BigDecimal product ;

    @Column(name="BRANCH_CODE", nullable=false, length=10)
    private String     branchCode ;

    @Column(name="POLICY_SUBTYPE_ID")
    private BigDecimal policySubtypeId ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="motorOpcoverMaster")
    private List<MotorOpcoverDetails> listOfMotorOpcoverDetails ; 



}



