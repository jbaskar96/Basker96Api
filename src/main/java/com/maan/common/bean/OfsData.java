/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:52 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:52 )
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
* Domain class for entity "OfsData"
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
@Table(name="OFS_DATA")


public class OfsData implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="QUOTE_NO", nullable=false)
    private BigDecimal quoteNo ;

    //--- ENTITY DATA FIELDS 
    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    @Column(name="CUSTOMER_ID")
    private BigDecimal customerId ;

    @Column(name="INSURED_ADDRESS_DIFFERENT", length=1)
    private String     insuredAddressDifferent ;

    @Column(name="ADDRESS1", length=150)
    private String     address1 ;

    @Column(name="POBOX", length=25)
    private String     pobox ;

    @Column(name="COUNTRY", length=25)
    private String     country ;

    @Column(name="EMIRATE", length=25)
    private String     emirate ;

    @Column(name="FREEZONE")
    private BigDecimal freezone ;

    @Column(name="CONTENT_VALUE")
    private BigDecimal contentValue ;

    @Column(name="CONTENT_VALUE_OTHERS")
    private BigDecimal contentValueOthers ;

    @Column(name="CLAIM_STATUS", length=1)
    private String     claimStatus ;

    @Column(name="LAST_YEARS_1")
    private BigDecimal lastYears1 ;

    @Column(name="LAST_YEARS_2")
    private BigDecimal lastYears2 ;

    @Column(name="LAST_YEARS_3")
    private BigDecimal lastYears3 ;

    @Column(name="AMOUNT_1")
    private BigDecimal amount1 ;

    @Column(name="AMOUNT_2")
    private BigDecimal amount2 ;

    @Column(name="AMOUNT_3")
    private BigDecimal amount3 ;

    @Temporal(TemporalType.DATE)
    @Column(name="INCEPTION_DATE")
    private Date       inceptionDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Column(name="APPLICATION_NO", nullable=false)
    private BigDecimal applicationNo ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Column(name="SPECIAL_CONDITION", length=100)
    private String     specialCondition ;

    @Column(name="ACTIVITY_PROFESSION")
    private BigDecimal activityProfession ;

    @Column(name="ACTIVITY_STATUS", length=1)
    private String     activityStatus ;

    @Temporal(TemporalType.DATE)
    @Column(name="EXPIRY_DATE")
    private Date       expiryDate ;

    @Column(name="PROF_OTHERS", length=100)
    private String     profOthers ;

    @Column(name="FREEZONE_OTHERS", length=100)
    private String     freezoneOthers ;

    @Column(name="CONTENT_STATUS", length=1)
    private String     contentStatus ;

    @Column(name="NO_OF_CLAIM1")
    private BigDecimal noOfClaim1 ;

    @Column(name="NO_OF_CLAIM2")
    private BigDecimal noOfClaim2 ;

    @Column(name="NO_OF_CLAIM3")
    private BigDecimal noOfClaim3 ;

    @Column(name="TELEPHONE", length=25)
    private String     telephone ;

    @Column(name="EMAIL", length=40)
    private String     email ;

    @Column(name="CONTENT_TYPE_ID")
    private BigDecimal contentTypeId ;

    @Column(name="PACOVER_YN", length=1)
    private String     pacoverYn ;

    @Column(name="TRACTER_YN", length=1)
    private String     tracterYn ;

    @Column(name="REFERRAL_REMARKS", length=4000)
    private String     referralRemarks ;

    @Column(name="ACTUAL_PREMIUM")
    private BigDecimal actualPremium ;

    @Column(name="DISCOUNT_PREMIUM")
    private BigDecimal discountPremium ;

    @Column(name="ACTUAL_OPPREMIUM")
    private BigDecimal actualOppremium ;

    @Column(name="DISCOUNT_PERCENT")
    private BigDecimal discountPercent ;

    @Column(name="NAME_TITLE", length=50)
    private String     nameTitle ;

    @Column(name="FIRST_NAME", length=200)
    private String     firstName ;

    @Column(name="LAST_NAME", length=200)
    private String     lastName ;

    @Column(name="CUSTOMER_TYPE", length=100)
    private String     customerType ;

    @Column(name="NO_OF_DAYS")
    private BigDecimal noOfDays ;

    @Column(name="LOGIN_ID", length=100)
    private String     loginId ;

    @Column(name="APPLICATION_ID", length=100)
    private String     applicationId ;

    @Column(name="BRANCHCODE", length=100)
    private String     branchcode ;

    @Column(name="END_DATE_ID", length=20)
    private String     endDateId ;

    @Column(name="TITLE_DESC", length=20)
    private String     titleDesc ;

    @Column(name="CUS_TYPE_DESC", length=20)
    private String     cusTypeDesc ;

    @Column(name="SCHEME_ID")
    private BigDecimal schemeId ;

    @Temporal(TemporalType.DATE)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}


