/*
 * Created on 2022-02-03 ( 11:31:10 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.maan.common.bean;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


import java.math.BigDecimal;

/**
 * Composite primary key for entity "OfsTransactionDetails" ( stored in table "OFS_TRANSACTION_DETAILS" )
 *
 * @author Telosys
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OfsTransactionDetailsId implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private BigDecimal quoteNo ;
    
    private BigDecimal productId ;
    
    private BigDecimal schemeId ;
    
    private BigDecimal coveragesId ;
    
    private BigDecimal coveragesSubId ;
    
    private BigDecimal moreDetailsId ;
    
    private BigDecimal locationId ;
    
    private BigDecimal moduleId ;
    
    private BigDecimal screenId ;
    
    private BigDecimal subscreenId ;
    
     
}