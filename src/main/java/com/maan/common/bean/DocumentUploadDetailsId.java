/*
 * Created on 2022-02-11 ( 19:14:39 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.maan.common.bean;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


import java.math.BigDecimal;

/**
 * Composite primary key for entity "DocumentUploadDetails" ( stored in table "DOCUMENT_UPLOAD_DETAILS" )
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
public class DocumentUploadDetailsId implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private String     claimNo ;
    
    private BigDecimal documentRef ;
    
    private String     docTypeId ;
    
    private BigDecimal productId ;
    
    private String     param ;
    
    private BigDecimal insId ;
    
     
}
