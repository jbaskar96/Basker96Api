/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:50 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:50 )
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
* Domain class for entity "MsCommonDetail"
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
@IdClass(MsCommonDetailId.class)
@Table(name="MS_COMMON_DETAIL")


public class MsCommonDetail implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="REQUESTREFERENCENO", nullable=false, length=200)
    private String     requestreferenceno ;

    @Id
    @Column(name="CDREFERENCENO", nullable=false)
    private BigDecimal cdreferenceno ;

    @Id
    @Column(name="VDREFERENCENO", nullable=false)
    private BigDecimal vdreferenceno ;

    @Id
    @Column(name="POLICYTYPEID", nullable=false)
    private BigDecimal policytypeid ;

    @Id
    @Column(name="EBROKERID", nullable=false)
    private BigDecimal ebrokerid ;

    @Id
    @Column(name="AGENCYCODE", nullable=false, length=100)
    private String     agencycode ;

    @Id
    @Column(name="SCHEMECODE", nullable=false, length=100)
    private String     schemecode ;

    @Id
    @Column(name="VEHICLETYPE", nullable=false)
    private BigDecimal vehicletype ;

    @Id
    @Column(name="SUMINSURED", nullable=false)
    private BigDecimal suminsured ;

    @Id
    @Column(name="HAVEPROMOCODE", nullable=false, length=10)
    private String     havepromocode ;

    @Id
    @Column(name="PROMOCODE", nullable=false, length=100)
    private String     promocode ;

    @Id
    @Column(name="NOOFDAYS", nullable=false)
    private BigDecimal noofdays ;

    @Id
    @Column(name="USERTYPE", nullable=false, length=30)
    private String     usertype ;

    @Id
    @Column(name="PROMOEMPCODE", nullable=false, length=100)
    private String     promoempcode ;

    @Id
    @Column(name="BDMCODE", nullable=false, length=100)
    private String     bdmcode ;

    @Id
    @Column(name="INTERESTEDIN_COMPYN", nullable=false, length=5)
    private String     interestedinCompyn ;

    @Id
    @Column(name="PREVCOMPANYID", nullable=false, length=100)
    private String     prevcompanyid ;

    @Id
    @Column(name="PREVTPLPOLICYYN", nullable=false, length=5)
    private String     prevtplpolicyyn ;

    //--- ENTITY DATA FIELDS 
    @Column(name="MCDREFERENCENO")
    private BigDecimal mcdreferenceno ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="QUOTECREATEDDATE")
    private Date       quotecreateddate ;

    @Column(name="PRODUCTID")
    private BigDecimal productid ;

    @Column(name="LOGINID", length=50)
    private String     loginid ;

    @Column(name="BROKERCODE", length=80)
    private String     brokercode ;

    @Column(name="STATUS", length=5)
    private String     status ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRYDATE")
    private Date       entrydate ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="QUOTETYPE", length=10)
    private String     quotetype ;

    @Column(name="ADMIN_LOGINID", length=50)
    private String     adminLoginid ;

    @Column(name="DIVISIONCODE", length=10)
    private String     divisioncode ;

    @Column(name="CUSTOMERCODE", length=10)
    private String     customercode ;

    @Column(name="SOURCETYPE", length=10)
    private String     sourcetype ;

    @Column(name="IMGREFNO", length=100)
    private String     imgrefno ;

    @Column(name="ELECTRICAL_SI")
    private BigDecimal electricalSi ;

    @Column(name="NONELECTRICAL_SI")
    private BigDecimal nonelectricalSi ;

    @Column(name="CURRENCY_ID")
    private BigDecimal currencyId ;

    @Column(name="CURRENCY_TYPE", length=50)
    private String     currencyType ;

    @Column(name="DRREFERENCENO")
    private BigDecimal drreferenceno ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}


