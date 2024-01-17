package com.maan.common.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchReportResponse {

	@JsonProperty("EntryDate")
	private String entryDate;
	@JsonProperty("BranchName")
	private String branchName;
	@JsonProperty("LoginId")
	private String loginId;
	@JsonProperty("ApplicationId")
	private String applicationId;
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("PolicyNo")
	private String policyNo;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("EffectiveDate")
	private String effectiveDate;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("CustomerName")
	private String customerName;
	@JsonProperty("TransportDesc")
	private String transportDesc;
	@JsonProperty("CoverName")
	private String coverName;
	@JsonProperty("SaleTermName")
	private String saleTermName;
	@JsonProperty("TotalSaleTermCharges")
	private String totalSaleTermCharges;
	@JsonProperty("TotalToleraneCharges")
	private String totalToleraneCharges;
	@JsonProperty("ExtraCoverName")
	private String extraCoverName;
	@JsonProperty("SuminsuredLocal")
	private String suminsuredLocal;
	@JsonProperty("CurrencyName")
	private String currencyName;
	@JsonProperty("ExchangeRate")
	private String exchangeRate;
	@JsonProperty("SuminsuredForeign")
	private String suminsuredForeign;
	@JsonProperty("MarinePremium")
	private String marinePremium;
	@JsonProperty("WarPremium")
	private String warPremium;
	@JsonProperty("ExcessPremium")
	private String excessPremium;
	@JsonProperty("CommissionAmount")
	private String commissionAmount;
	@JsonProperty("PolicyFee")
	private String policyFee;
	@JsonProperty("TotalPremiumLocal")
	private String totalPremiumLocal;
	@JsonProperty("ForeignCurrency")
	private String foreignCurrency;
	@JsonProperty("TotalPremiumForeign")
	private String totalPremiumForeign;
	@JsonProperty("CommissionPercent")
	private String commissionPercent;
	@JsonProperty("TransitFromCity")
	private String transitFromCity;
	@JsonProperty("TransitFromCountry")
	private String transitFromCountry;
	@JsonProperty("FinalDestCity")
	private String finalDestCity;
	@JsonProperty("FinalDestCountry")
	private String finalDestCountry;
	@JsonProperty("DebitNoteNo")
	private String debitNoteNo;
	@JsonProperty("CreditNoteNo")
	private String creditNoteNo;
	@JsonProperty("Referral")
	private String referral;
	@JsonProperty("ReferralStatus")
	private String referralStatus;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("GoodsDesc")
	private String goodsDesc;
	@JsonProperty("InspectionFee")
	private String inspectionFee;
	@JsonProperty("PolicyExcessPercentage")
	private String policyExcessPercentage;
	@JsonProperty("MarginPremium")
	private String marginPremium;
	@JsonProperty("WarlandPremium")
	private String warlandPremium;
	@JsonProperty("GoodsCatagory")
	private String goodsCatagory;
	@JsonProperty("QuoteRefNo")
	private String quoteRefNo;
	@JsonProperty("OpenVesselName")
	private String openVesselName;
	@JsonProperty("VoyageNumber")
	private String voyageNumber;
	@JsonProperty("InvoiceNumber")
	private String invoiceNumber;
	@JsonProperty("InvoiceDate")
	private String invoiceDate;
	@JsonProperty("PoNumber")
	private String poNumber;
	@JsonProperty("ConsignedFrom")
	private String consignedFrom;
	@JsonProperty("SupplierName")
	private String supplierName;
	@JsonProperty("LcBankOther")
	private String lcBankOther;
	@JsonProperty("LcNumber")
	private String lcNumber;
	@JsonProperty("LcDate")
	private String lcDate;
	@JsonProperty("BlAwbNo")
	private String blAwbNo;
	@JsonProperty("BlAwbDate")
	private String blAwbDate;
	@JsonProperty("SailingDate")
	private String sailingDate;
	@JsonProperty("Suminsured")
	private String suminsured;
	@JsonProperty("MarginRate")
	private String marginRate;
	@JsonProperty("WarRate")
	private String warRate;
	@JsonProperty("MarineRate")
	private String marineRate;
	@JsonProperty("WarlandRate")
	private String warlandRate;
	@JsonProperty("WarMarginRate")
	private String warMarginRate;
	@JsonProperty("WarMarginPremium")
	private String warMarginPremium;
	@JsonProperty("ModeOfPayment")
	private String modeOfPayment;
	@JsonProperty("ReceiptNumber")
	private String receiptNumber;
	@JsonProperty("Via")
	private String via;
	@JsonProperty("IntegrationStatus")
	private String integrationStatus;
	@JsonProperty("IntegrationError")
	private String integrationError;
	@JsonProperty("ExcessDescription")
	private String excessDescription;
}
