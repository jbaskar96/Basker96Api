/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:55 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:55 )
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
* Domain class for entity "PersonalInfo"
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
@Table(name="PERSONAL_INFO")


public class PersonalInfo implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="CUSTOMER_ID", nullable=false)
    private BigDecimal customerId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="APPLICATION_ID", length=25)
    private String     applicationId ;

    @Column(name="TITLE", length=10)
    private String     title ;

    @Column(name="FIRST_NAME", length=200)
    private String     firstName ;

    @Column(name="LAST_NAME", length=200)
    private String     lastName ;

    @Column(name="NATIONALITY", length=25)
    private String     nationality ;

    @Temporal(TemporalType.DATE)
    @Column(name="DOB")
    private Date       dob ;

    @Column(name="GENDER", length=1)
    private String     gender ;

    @Column(name="TELEPHONE", length=100)
    private String     telephone ;

    @Column(name="MOBILE", length=60)
    private String     mobile ;

    @Column(name="FAX", length=50)
    private String     fax ;

    @Column(name="EMAIL", length=100)
    private String     email ;

    @Column(name="ADDRESS1", length=500)
    private String     address1 ;

    @Column(name="ADDRESS2", length=500)
    private String     address2 ;

    @Column(name="OCCUPATION", length=50)
    private String     occupation ;

    @Column(name="POBOX", length=30)
    private String     pobox ;

    @Column(name="COUNTRY", length=25)
    private String     country ;

    @Column(name="EMIRATE", length=50)
    private String     emirate ;

    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    @Temporal(TemporalType.DATE)
    @Column(name="INCEPTION_DATE")
    private Date       inceptionDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="EXPIRY_DATE")
    private Date       expiryDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Column(name="LOGIN_ID", length=50)
    private String     loginId ;

    @Column(name="AC_EXECUTIVE_ID")
    private BigDecimal acExecutiveId ;

    @Column(name="AGENCY_CODE", length=25)
    private String     agencyCode ;

    @Column(name="OA_CODE", length=25)
    private String     oaCode ;

    @Column(name="COMPANY_NAME", length=200)
    private String     companyName ;

    @Column(name="MISSIPPI_CUSTOMER_CODE", length=50)
    private String     missippiCustomerCode ;

    @Column(name="CITY", length=70)
    private String     city ;

    @Column(name="FREIGHT_FORWARD_USER", length=35)
    private String     freightForwardUser ;

    @Column(name="CUSTOMER_LOGIN_ID", length=15)
    private String     customerLoginId ;

    @Column(name="CUSTOMER_SOURCE", length=75)
    private String     customerSource ;

    @Column(name="FD_CODE", length=25)
    private String     fdCode ;

    @Column(name="CLIENT_CUSTOMER_ID", length=30)
    private String     clientCustomerId ;

    @Column(name="CUST_AR_NO", length=50)
    private String     custArNo ;

    @Column(name="CUST_NAME", length=200)
    private String     custName ;

    @Column(name="STATE", length=100)
    private String     state ;

    @Column(name="PASSPORT_NUMBER", length=200)
    private String     passportNumber ;

    @Column(name="NRC", length=200)
    private String     nrc ;

    @Column(name="ALTERNATE_MOBILE", length=100)
    private String     alternateMobile ;

    @Column(name="CUSTOMER_TYPE", length=10)
    private String     customerType ;

    @Column(name="COMPANY_REG_NO", length=50)
    private String     companyRegNo ;

    @Column(name="CUST_NAME_ARABIC", length=200)
    private String     custNameArabic ;

    @Column(name="DOB_AR", length=20)
    private String     dobAr ;

    @Column(name="SCHEME_ID")
    private BigDecimal schemeId ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



