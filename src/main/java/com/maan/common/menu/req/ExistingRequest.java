package com.maan.common.menu.req;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExistingRequest {

	//@JsonProperty("LoginId")
//	private String loginid;
	@JsonProperty("ProductId")
	private String productid;
	///@JsonProperty("ApplicationId")
	//private String applicationid;
	private String quoteno;
	private String referenceno;
	private String typeid;
	//private String branchcode;
	private String subusertype;
	private String copyquoteby;
	private String copyquotevalue;
	private String typeofquote;
	private String quotestartdate;
	private String quoteenddate;
	private String startdate;
	private String enddate;
	private String usertype;
	
	@JsonProperty("LoginId") 
    public String loginid;
    @JsonProperty("ApplicationId") 
    public String applicationid;
    @JsonProperty("BranchCode") 
    public String branchcode;
    @JsonProperty("SchemeId") 
    public String schemeid;
    @JsonProperty("MenuType") 
    public String menuType;
    @JsonProperty("Limit") 
    public String limit;
    @JsonProperty("OffSet") 
    public String offset;
    private String status;
    private Date today;
    private String custapprovestatus;
    private String referalstatus;
    private String adminreferalstatus;
    private String portfoliostatus;
    @JsonProperty("CustomerId") 
    public String customerId;
    @JsonProperty("BrokerCode") 
    public String brokerCode;
    @JsonProperty("ExecutiveID") 
    public String executiveId;
}
