package com.maan.common.menu.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.common.error.Error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {

	private String quote_no;
	private String status;
	private String lapsed_remarks;
	private String remarks;
	private String lapsed_date;
	private String customer_id;
	private String quotation_date;
	private String validity_date;
	private String customer_name;
	private String Reference_no;
	private String login_id;
	private String company_name;
	private String broker_company_name;
	private String premia_customer_name;
	private String email;
	private String inception_date;
	private String premium;
	private String application_no;
	private String validity_period;
	private String reject_desc;
	private String code;
	private String codedesc;
	private String itemdesc;
	private String param1;
	private String param2;
	private String countryid;
	private String countryname;
	private String policy_no;
	private String overall_premium;
	private String last_name;
	private String excess_premium;
	private String open_cover_no;
	private String debit_note_no;
	private String credit_note_no;
	private String amend_id;
	private String receipt_no;
	private String broker_name;
	private String credit_no;
	private String insured_name;
	private String commision;
	private String payment_mode;
	private String effective_date;
	private String expiry_date;
	private String entry_date;
	private String product_name;
	private String broker_id;
	private String vehicle_id;
	private String policytype;
	private String content_type_id;	
	private String policy_start_date;
	private String org_status;
	private String status_type;
	private String status_type_name;
	private String oldreferenceno;
	private String travelcoverId;
	private String modeofpayment;
	private List<Error> errors;
	private String branchname;
	private String aaa_cardno;
	private String rsa_cardno;
	private String vehicletype;
	private String integstatus;
	private String integerrordesc;
	private String mobileno;
	private String referralRemarks;
	private String comments;
	private String uploadtranid;
	private String hodreferralremarks;
	private String istpl;
	private String policyholderid;
	private String applicationid;
	@JsonProperty("HodApprovedBy")
	private String hodreferralapprovedby;
	@JsonProperty("HODReferralRequestDate")
	private String hodreferralrequestdate;
	@JsonProperty("HODReferralApprovedDate")
	private String hodreferralapproveddate;
	@JsonProperty("NoOfDayinHodPending")
	private String noofdayinhodpending;
	@JsonProperty("NoOfDayinHodApproved")
	private String noofdayinhodapproved;
	@JsonProperty("RenewalYN")
	private String renewalyn;
	@JsonProperty("RenewalPolicyNo")
	private String renewalpolicyno;
	@JsonProperty("Orangecardno")
	private String orangecardno;
	@JsonProperty("Orangecardurl")
	private String orangecardurl;
	@JsonProperty("Plateno")
	private String plateno;
	@JsonProperty("Chassisno")
	private String chassisno;
	@JsonProperty("Makenamedesc")
	private String makenamedesc;
	@JsonProperty("Modelnamedesc")
	private String modelnamedesc;
	@JsonProperty("Trim")
	private String trim;
	@JsonProperty("Bodynamedesc")
	private String bodynamedesc;
	@JsonProperty("Geodesc")
	private String geodesc;
	@JsonProperty("SumInsured")
	private String suminsured;
	@JsonProperty("Promocode")
	private String promocode;
	private String branchCode;
	// New Attribute
	@JsonProperty("SchemeId")
	private String schemeid;
	@JsonProperty("SchemeName")
	private String schemeName;
	@JsonProperty("CustApproveDate")
	private String custapprovedate;
	@JsonProperty("CreatedBy")
	private String createdby;
}

