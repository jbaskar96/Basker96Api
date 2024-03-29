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
* Domain class for entity "ProductMaster"
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
@IdClass(ProductMasterId.class)
@Table(name="PRODUCT_MASTER")


public class ProductMaster implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="PRODUCT_ID", nullable=false)
    private BigDecimal productId ;

    @Id
    @Column(name="INS_COMPANY_ID", nullable=false)
    private BigDecimal insCompanyId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="PRODUCT_NAME", length=100)
    private String     productName ;

    @Column(name="PRODUCT_CODE", length=25)
    private String     productCode ;

    @Column(name="CORE_APPCODE", length=10)
    private String     coreAppcode ;

    @Column(name="BRANCH_CODE", length=8)
    private String     branchCode ;

    @Column(name="DISPLAY_ORDER")
    private BigDecimal displayOrder ;

    @Column(name="PAYMENT_YN", length=5)
    private String     paymentYn ;

    @Column(name="PAYMENT_REDIR_URL", length=1000)
    private String     paymentRedirUrl ;

    @Column(name="APP_LOGIN_URL", length=100)
    private String     appLoginUrl ;

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
    @OneToMany(mappedBy="productMaster")
    private List<PolicytypeMaster> listOfPolicytypeMaster ; 

    @OneToMany(mappedBy="productMaster")
    private List<ClaimDocumentMaster> listOfClaimDocumentMaster ; 



}



