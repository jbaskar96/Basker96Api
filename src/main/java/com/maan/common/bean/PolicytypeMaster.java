/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:56 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:56 )
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
* Domain class for entity "PolicytypeMaster"
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
@IdClass(PolicytypeMasterId.class)
@Table(name="POLICYTYPE_MASTER")


public class PolicytypeMaster implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="POLICYTYPE_ID", nullable=false)
    private BigDecimal policytypeId ;

    @Id
    @Column(name="PRODUCT_ID", nullable=false)
    private BigDecimal productId ;

    @Id
    @Column(name="INS_COMPANY_ID", nullable=false)
    private BigDecimal insCompanyId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="POLICYTYPE_NAME", length=200)
    private String     policytypeName ;

    @Column(name="POLICYTYPE_DESC_ENGLISH", length=200)
    private String     policytypeDescEnglish ;

    @Column(name="POLICYTYPE_DESC_ARABIC", length=200)
    private String     policytypeDescArabic ;

    @Column(name="POLICY_PREFIX", length=5)
    private String     policyPrefix ;

    @Column(name="QUOTETYPE", length=10)
    private String     quotetype ;

    @Column(name="CORE_APPCODE", length=10)
    private String     coreAppcode ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="EXPIRY_DATE")
    private Date       expiryDate ;

    @Column(name="REMARKS", length=300)
    private String     remarks ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="policytypeMaster")
    private List<BrokerCommissionMaster> listOfBrokerCommissionMaster ; 

    @ManyToOne
    @JoinColumns( { 
        @JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID", insertable=false, updatable=false),
        @JoinColumn(name="INS_COMPANY_ID", referencedColumnName="INS_COMPANY_ID", insertable=false, updatable=false) } )
    private ProductMaster productMaster ; 



}


