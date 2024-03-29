/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:43 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:43 )
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
* Domain class for entity "MSubscreen"
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
@IdClass(MSubscreenId.class)
@Table(name="M_SUBSCREEN")


public class MSubscreen implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="MSS_MODULEID", nullable=false)
    private BigDecimal mssModuleid ;

    @Id
    @Column(name="MSS_SCREENID", nullable=false)
    private BigDecimal mssScreenid ;

    @Id
    @Column(name="MSS_SUBSCREENID", nullable=false)
    private BigDecimal mssSubscreenid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="MSS_SUBSCREENNAME", length=100)
    private String     mssSubscreenname ;

    @Column(name="MSS_GRIDDATA", length=250)
    private String     mssGriddata ;

    @Column(name="MSS_GRIDYN", length=1)
    private String     mssGridyn ;

    @Column(name="MSS_GRIDURL", length=100)
    private String     mssGridurl ;

    @Temporal(TemporalType.DATE)
    @Column(name="MSS_EFFECTIVEDATE")
    private Date       mssEffectivedate ;

    @Temporal(TemporalType.DATE)
    @Column(name="MSS_ENTRYDATE")
    private Date       mssEntrydate ;

    @Column(name="MSS_STATUS", length=1)
    private String     mssStatus ;

    @Column(name="MSS_DISPLAYORDER")
    private BigDecimal mssDisplayorder ;

    @Column(name="MSS_REMARKS", length=100)
    private String     mssRemarks ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



