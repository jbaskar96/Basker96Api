/*
 * Created on 2022-02-11 ( 19:14:51 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.maan.common.bean;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Composite primary key for entity "NotifTemplateMaster" ( stored in table "NOTIF_TEMPLATE_MASTER" )
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
public class NotifTemplateMasterId implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private BigDecimal sno ;
    
    private String     status ;
    
    private BigDecimal insId ;
    
    private Date       effectiveDate ;
    
    private BigDecimal amendid ;
    
     
}
