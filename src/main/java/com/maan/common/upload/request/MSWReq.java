package com.maan.common.upload.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MSWReq {

	@JsonProperty("QuoteNo")
	private String quote_no;

	@JsonProperty("PolicyNo")
	private String policyno;

	@JsonProperty("ProductId")
	private String product_id;

	@JsonProperty("RequestReferenceNo")
	private String requestreferenceno;

	@JsonProperty("Type")
	private String type;

	@JsonProperty("Product_Type")
	private String product_type;

	@JsonProperty("SubUserType")
	private String subusertype;

	@JsonProperty("UserType")
	private String usertype;

	@JsonProperty("TinyUrl")
	private String tinyurl;

	@JsonProperty("MailId")
	private String mailid;

	@JsonProperty("MailSubject")
	private String mailsubject;

	@JsonProperty("MailBody")
	private String mailbody;

	@JsonProperty("MailRegards")
	private String mailregards;

	@JsonProperty("MCode")
	private String mobileCode;

	@JsonProperty("MobileNo")
	private String mobileno;

	@JsonProperty("MobileNos")
	private List<String> mobilenos;

	@JsonProperty("CustomerName")
	private String customerName;

	@JsonProperty("WhatsAppCode")
	private String whatsAppCode;

	@JsonProperty("WhatsAppNo")
	private String whatsAppno;

	@JsonProperty("Otp")
	private String otp;

	@JsonProperty("OtpId")
	private String otpid;

	@JsonProperty("SmsSubject")
	private String smssubject;

	@JsonProperty("SmsBody")
	private String smsbody;

	@JsonProperty("SmsRegards")
	private String smsregards;

	@JsonProperty("OtherRefno")
	private String otherrefno;

	@JsonProperty("FilePath")
	private String filepath;

	@JsonProperty("FilePaths")
	private List<String> filepaths;

	@JsonProperty("ToMailId")
	private String[] tomail;

	@JsonProperty("CCMailId")
	private String[] ccmail;

}
