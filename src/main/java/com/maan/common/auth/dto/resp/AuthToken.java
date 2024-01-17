package com.maan.common.auth.dto.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.auth.dto.Menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

	@JsonProperty("Token")
    private String token;
	@JsonProperty("LoginId")
    private String loginId;
	@JsonProperty("InsuranceId")
    private String companyId;
	@JsonProperty("PasswordMsg")
    private String passwordMsg;
	@JsonProperty("UserType")
    private String userType;
	@JsonProperty("AgencyCode")
    private String agencyCode;
	@JsonProperty("MenuId")
    private String menuId;
	@JsonProperty("Status")
    private String status;
	@JsonProperty("BranchCode")
    private String branchCode;
	@JsonProperty("BelongingBranch")
    private String belongingBranch;
	@JsonProperty("AttachedBranch")
    private String attachedBranch;
	@JsonProperty("AccessType")
    private String accessType;
	@JsonProperty("OrginationCountryId")
    private String orginationCountryId;
	@JsonProperty("DestinationCountryId")
    private String destinationCountryId;
	@JsonProperty("CurrencyAbbreviation")
    private String currencyAbbreviation;
	@JsonProperty("CurrencyDecimal")
    private String currencyDecimal;
	@JsonProperty("Tax")
    private String tax;
	@JsonProperty("Site")
    private String site;
	@JsonProperty("LangYn")
    private String langYn;
	@JsonProperty("BranchName")
    private String branchName;
	@JsonProperty("OaCode")
    private String oaCode;
	@JsonProperty("ProductDetail")
    private List<LoginProductResponse> productDetail;
	@JsonProperty("RegionCode")
    private String regionCode;
	
	@JsonProperty("menuList")
    private List<Menu> menuList;
	
	
	//@JsonProperty("BranchCodeList")
   // private List<BranchDropDownRes> BranchCodeList;
}
