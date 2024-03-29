/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:57 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:57 )
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
* Domain class for entity "SmsDataDetails"
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
@IdClass(SmsDataDetailsId.class)
@Table(name="SMS_DATA_DETAILS")


public class SmsDataDetails implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="S_NO", nullable=false)
    private BigDecimal sNo ;

    @Id
    @Column(name="SMS_REF_NO", nullable=false, length=1000)
    private String     smsRefNo ;

    @Id
    @Column(name="INS_ID", nullable=false)
    private BigDecimal insId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="CUST_NAME", length=1000)
    private String     custName ;

    @Column(name="MOBILE_NO", length=50)
    private String     mobileNo ;

    @Column(name="POLICY_NO", length=100)
    private String     policyNo ;

    @Column(name="SMS_TYPE", length=50)
    private String     smsType ;

    @Column(name="SMS_CONTENT", length=4000)
    private String     smsContent ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQ_TIME")
    private Date       reqTime ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RES_TIME")
    private Date       resTime ;

    @Column(name="RES_STATUS", length=50)
    private String     resStatus ;

    @Column(name="RES_SUCCESS", length=50)
    private String     resSuccess ;

    @Column(name="RES_CODE", length=50)
    private String     resCode ;

    @Column(name="RES_MESSAGE", length=4000)
    private String     resMessage ;

    @Column(name="RES_DATA", length=4000)
    private String     resData ;

    @Column(name="REMARKS", length=4000)
    private String     remarks ;

    @Column(name="PRODUCT_ID")
    private BigDecimal productId ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



