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
import javax.persistence.*;




/**
* Domain class for entity "SubCoverDetails"
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
@IdClass(SubCoverDetailsId.class)
@Table(name="SUB_COVER_DETAILS")


public class SubCoverDetails implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="QUOTE_NO", nullable=false)
    private BigDecimal quoteNo ;

    @Id
    @Column(name="MF_MODULEID", nullable=false)
    private BigDecimal mfModuleid ;

    @Id
    @Column(name="MF_SCREENID", nullable=false)
    private BigDecimal mfScreenid ;

    @Id
    @Column(name="MF_SUBSCREENID", nullable=false)
    private BigDecimal mfSubscreenid ;

    @Id
    @Column(name="MF_FIELDID", nullable=false)
    private BigDecimal mfFieldid ;

    @Id
    @Column(name="MF_ADDCOV_ID", nullable=false)
    private BigDecimal mfAddcovId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="FIELD_1", length=200)
    private String     field1 ;

    @Column(name="FIELD_2", length=200)
    private String     field2 ;

    @Column(name="FIELD_3", length=200)
    private String     field3 ;

    @Column(name="FIELD_4", length=200)
    private String     field4 ;

    @Column(name="FIELD_5", length=200)
    private String     field5 ;

    @Column(name="FIELD_6", length=200)
    private String     field6 ;

    @Column(name="FIELD_7", length=200)
    private String     field7 ;

    @Column(name="FIELD_8", length=200)
    private String     field8 ;

    @Column(name="FIELD_9", length=200)
    private String     field9 ;

    @Column(name="FIELD_10", length=200)
    private String     field10 ;

    @Column(name="FIELD_11", length=200)
    private String     field11 ;

    @Column(name="FIELD_12", length=200)
    private String     field12 ;

    @Column(name="FIELD_13", length=200)
    private String     field13 ;

    @Column(name="FIELD_14", length=200)
    private String     field14 ;

    @Column(name="FIELD_15", length=200)
    private String     field15 ;

    @Column(name="FIELD_16", length=200)
    private String     field16 ;

    @Column(name="FIELD_17", length=200)
    private String     field17 ;

    @Column(name="FIELD_18", length=200)
    private String     field18 ;

    @Column(name="FIELD_19", length=200)
    private String     field19 ;

    @Column(name="FIELD_20", length=200)
    private String     field20 ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



