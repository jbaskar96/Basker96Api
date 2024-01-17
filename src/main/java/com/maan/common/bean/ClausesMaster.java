/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:38 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:38 )
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
import javax.persistence.*;




/**
* Domain class for entity "ClausesMaster"
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
@IdClass(ClausesMasterId.class)
@Table(name="CLAUSES_MASTER")


public class ClausesMaster implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="SNO__", nullable=false)
    private BigDecimal sno ;

    @Id
    @Column(name="COVER_ID", nullable=false)
    private BigDecimal coverId ;

    @Id
    @Column(name="CLAUSES_ID", nullable=false)
    private BigDecimal clausesId ;

    @Id
    @Column(name="BRANCH_CODE", nullable=false, length=8)
    private String     branchCode ;

    //--- ENTITY DATA FIELDS 
    @Column(name="CLAUSES_DESCRIPTION", nullable=false, length=1000)
    private String     clausesDescription ;

    @Column(name="EXTRA_COVER_ID")
    private BigDecimal extraCoverId ;

    @Column(name="RSACODE", length=10)
    private String     rsacode ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Column(name="DISPLAY_ORDER")
    private BigDecimal displayOrder ;

    @Column(name="PDF_LOCATION", length=200)
    private String     pdfLocation ;

    @Column(name="OPTIONAL_TYPE", length=5)
    private String     optionalType ;

    @Column(name="INT_CODE", length=100)
    private String     intCode ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



