/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:49 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:49 )
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
* Domain class for entity "MotorQuarterpremDiscounts"
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
@IdClass(MotorQuarterpremDiscountsId.class)
@Table(name="MOTOR_QUARTERPREM_DISCOUNTS")


public class MotorQuarterpremDiscounts implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="POLICYTYPE_ID", nullable=false)
    private BigDecimal policytypeId ;

    @Id
    @Column(name="QUATERID", nullable=false)
    private BigDecimal quaterid ;

    @Id
    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    @Id
    @Column(name="PRODUCT_ID", nullable=false)
    private BigDecimal productId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="STARTRANGE", nullable=false)
    private BigDecimal startrange ;

    @Column(name="ENDRANGE", nullable=false)
    private BigDecimal endrange ;

    @Column(name="DISCOUNT_PERC")
    private BigDecimal discountPerc ;

    @Column(name="STATUS", length=10)
    private String     status ;

    @Temporal(TemporalType.DATE)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;

    @Column(name="REMARKS", length=500)
    private String     remarks ;

    @Column(name="SNO")
    private BigDecimal sno ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



