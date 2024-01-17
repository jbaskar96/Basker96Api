/*
 * Java domain class for entity "ListItemValue" 
 * Created on 2022-01-17 ( Date ISO 2022-01-17 - Time 11:34:41 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-01-17 ( 11:34:41 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.maan.common.bean.ListItemValue;
/**
 * <h2>ListItemValueRepository</h2>
 *
 * createdAt : 2022-01-17 - Time 11:34:41
 * <p>
 * Description: "ListItemValue" Repository
 */
 
 
 
public interface ListItemValueRepository  extends JpaRepository<ListItemValue,BigDecimal > , JpaSpecificationExecutor<ListItemValue> {

	List<ListItemValue> findByItemTypeAndStatusOrderByItemValue(String itemType, String status);

	@Query(value ="Select Item_Desc From List_Item_Value where Item_Code = ?1 And Item_Type = ?2 ",nativeQuery = true)
	String getItemDesc(String itemCode , String itemType);
}