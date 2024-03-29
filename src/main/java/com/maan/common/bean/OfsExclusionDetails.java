/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:53 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:53 )
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
* Domain class for entity "OfsExclusionDetails"
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
@IdClass(OfsExclusionDetailsId.class)
@Table(name="OFS_EXCLUSION_DETAILS")


public class OfsExclusionDetails implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="QUOTE_NO", nullable=false)
    private BigDecimal quoteNo ;

    @Id
    @Column(name="PRODUCT_ID", nullable=false)
    private BigDecimal productId ;

    @Id
    @Column(name="SCHEME_ID", nullable=false)
    private BigDecimal schemeId ;

    @Id
    @Column(name="EXCLUSION_ID", nullable=false)
    private BigDecimal exclusionId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="SNO")
    private BigDecimal sno ;

    @Column(name="EXCLUSION_DESCRIPTION", length=4000)
    private String     exclusionDescription ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Column(name="REMARKS", length=4000)
    private String     remarks ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



