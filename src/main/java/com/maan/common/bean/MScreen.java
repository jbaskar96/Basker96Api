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
* Domain class for entity "MScreen"
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
@IdClass(MScreenId.class)
@Table(name="M_SCREEN")


public class MScreen implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="MS_MODULEID", nullable=false)
    private BigDecimal msModuleid ;

    @Id
    @Column(name="MS_SCREENID", nullable=false)
    private BigDecimal msScreenid ;

    //--- ENTITY DATA FIELDS 
    @Column(name="MS_SCREENNAME", length=100)
    private String     msScreenname ;

    @Column(name="MS_SAVEURL", length=100)
    private String     msSaveurl ;

    @Column(name="MS_GETURL", length=100)
    private String     msGeturl ;

    @Column(name="MS_LISTURL", length=100)
    private String     msListurl ;

    @Temporal(TemporalType.DATE)
    @Column(name="MS_EFFECTIVEDATE")
    private Date       msEffectivedate ;

    @Temporal(TemporalType.DATE)
    @Column(name="MS_ENTRYDATE")
    private Date       msEntrydate ;

    @Column(name="MS_STATUS", length=1)
    private String     msStatus ;

    @Column(name="MS_DISPLAYORDER")
    private BigDecimal msDisplayorder ;

    @Column(name="MS_REMARKS", length=100)
    private String     msRemarks ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}


