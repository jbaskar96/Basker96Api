/*
 * Created on 2022-02-11 ( 19:14:52 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.maan.common.bean;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


import java.math.BigDecimal;

/**
 * Composite primary key for entity "OfsClausesMaster" ( stored in table "OFS_CLAUSES_MASTER" )
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
public class OfsClausesMasterId implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private BigDecimal productId ;
    
    private BigDecimal schemeId ;
    
    private BigDecimal coverId ;
    
    private BigDecimal clausesId ;
    
     
}