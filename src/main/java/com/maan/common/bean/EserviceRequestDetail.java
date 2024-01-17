/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-02-11 ( Date ISO 2022-02-11 - Time 19:14:39 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-02-11 ( 19:14:39 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.common.bean;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Table;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;




/**
* Domain class for entity "EserviceRequestDetail"
*
* @author Telosys Tools Generator
*
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@IdClass(EserviceRequestDetailId.class)
@Table(name="ESERVICE_REQUEST_DETAIL")


public class EserviceRequestDetail implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="REQUESTREFERENCENO", nullable=false, length=200)
    private String     requestreferenceno ;

    @Id
    @Column(name="CUSTOMER_ID", nullable=false)
    private BigDecimal customerId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="INSURANCECOMPANYCODE", length=200)
    private String     insurancecompanycode ;

    @Column(name="LOGIN_ID", length=50)
    private String     loginId ;

    @Column(name="APPLICATION_ID", length=80)
    private String     applicationId ;

    @Column(name="AGENCY_CODE", length=100)
    private String     agencyCode ;

    @Column(name="BROKER_CODE", length=80)
    private String     brokerCode ;

    @Column(name="EXECUTIVE_ID", length=80)
    private String     executiveId ;

    @Column(name="STATUS", length=25)
    private String     status ;

    @Column(name="QUOTE_NO")
    private BigDecimal quoteNo ;

    @Column(name="APPLICATION_NO")
    private BigDecimal applicationNo ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="QUOTE_CREATED_DATE")
    private Date       quoteCreatedDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="POLICYSTARTDATE")
    private Date       policystartdate ;

    @Column(name="POLICY_NO", length=200)
    private String     policyNo ;

    @Column(name="VEHICLE_ID")
    private BigDecimal vehicleId ;

    @Column(name="POLICYTYPE", length=30)
    private String     policytype ;

    @Column(name="PRODUCT_ID")
    private BigDecimal productId ;

    @Column(name="OPCOVER_SELECTED", length=500)
    private String     opcoverSelected ;

    @Column(name="EBROKER_ID")
    private BigDecimal ebrokerId ;

    @Column(name="INSURED_NAME", length=1200)
    private String     insuredName ;

    @Column(name="DATEOFBIRTHG", length=200)
    private String     dateofbirthg ;

    @Column(name="DATEOFBIRTHH", length=200)
    private String     dateofbirthh ;

    @Column(name="MOBILE", length=200)
    private String     mobile ;

    @Column(name="EMAIL", length=100)
    private String     email ;

    @Column(name="GENDER", length=200)
    private String     gender ;

    @Column(name="PREMIUM")
    private BigDecimal premium ;

    @Column(name="VALIDATION_REMARKS", length=4000)
    private String     validationRemarks ;

    @Column(name="POLICYHOLDERID", length=200)
    private String     policyholderid ;

    @Column(name="PREVIOUSOWNERIQAMAID", length=200)
    private String     previousowneriqamaid ;

    @Column(name="SEQUENCENO", length=200)
    private String     sequenceno ;

    @Column(name="CUSTOMSID", length=200)
    private String     customsid ;

    @Column(name="OWNERDRIVERYN", length=200)
    private String     ownerdriveryn ;

    @Column(name="TRANSFEROWNER", length=20)
    private String     transferowner ;

    @Column(name="PREVIOUSOWNERTITLE", length=20)
    private String     previousownertitle ;

    @Column(name="PREVIOUSOWNERNAME", length=200)
    private String     previousownername ;

    @Column(name="VEHICLEPLATETYPE", length=100)
    private String     vehicleplatetype ;

    @Column(name="VEH_TYPE_ID")
    private BigDecimal vehTypeId ;

    @Column(name="VEH_MAKE_ID")
    private BigDecimal vehMakeId ;

    @Column(name="VEH_MODEL_ID")
    private BigDecimal vehModelId ;

    @Column(name="VEH_BODY_ID")
    private BigDecimal vehBodyId ;

    @Column(name="VEH_MANF_COUNTRY")
    private BigDecimal vehManfCountry ;

    @Column(name="VEH_WEIGHT")
    private BigDecimal vehWeight ;

    @Column(name="VEH_CC")
    private BigDecimal vehCc ;

    @Column(name="VEH_ENGINE_NO", length=60)
    private String     vehEngineNo ;

    @Column(name="VEH_CHASSIS_NO", length=60)
    private String     vehChassisNo ;

    @Column(name="VEH_PLATE_CHAR")
    private BigDecimal vehPlateChar ;

    @Column(name="VEH_PLATE_NUMBER")
    private BigDecimal vehPlateNumber ;

    @Column(name="VEH_SEATING")
    private BigDecimal vehSeating ;

    @Column(name="NO_OF_CLAIMS")
    private BigDecimal noOfClaims ;

    @Column(name="NO_CLAIM_BONUS")
    private BigDecimal noClaimBonus ;

    @Column(name="CLAIMED_AMOUNT")
    private BigDecimal claimedAmount ;

    @Column(name="MANFACTUREYEAR")
    private BigDecimal manfactureyear ;

    @Column(name="VEH_REGN_DATE", length=20)
    private String     vehRegnDate ;

    @Column(name="SUMINSURED", length=200)
    private String     suminsured ;

    @Column(name="EXCESS", length=200)
    private String     excess ;

    @Column(name="DEDUCTIBLE")
    private BigDecimal deductible ;

    @Column(name="HAVEPROMOCODE", length=10)
    private String     havepromocode ;

    @Column(name="PROMOCODE", length=100)
    private String     promocode ;

    @Column(name="IMPORT_YN", length=10)
    private String     importYn ;

    @Column(name="IMPORT_CODE")
    private BigDecimal importCode ;

    @Column(name="GCC_TYPE")
    private BigDecimal gccType ;

    @Column(name="OCCUPATION")
    private BigDecimal occupation ;

    @Column(name="VEH_PARKING_TYPE", length=100)
    private String     vehParkingType ;

    @Column(name="REFERRAL_REMARKS", length=4000)
    private String     referralRemarks ;

    @Column(name="PH_NCB")
    private BigDecimal phNcb ;

    @Column(name="PH_LIC_AGE")
    private BigDecimal phLicAge ;

    @Column(name="LOYAL_FLAG", length=10)
    private String     loyalFlag ;

    @Column(name="LOYALTY_PERCENT")
    private BigDecimal loyaltyPercent ;

    @Column(name="CLAIM_YN", length=10)
    private String     claimYn ;

    @Column(name="CLAIM_RATIO")
    private BigDecimal claimRatio ;

    @Temporal(TemporalType.DATE)
    @Column(name="RENEWAL_OLD_EXP_DATE")
    private Date       renewalOldExpDate ;

    @Column(name="RENEWAL_OLD_POLICY", length=100)
    private String     renewalOldPolicy ;

    @Column(name="PROMOTYPE", length=15)
    private String     promotype ;

    @Column(name="SCHEME_EMP_CODE", length=50)
    private String     schemeEmpCode ;

    @Column(name="VEH_CYLINDERS")
    private BigDecimal vehCylinders ;

    @Column(name="PRICE_ID", length=5)
    private String     priceId ;

    @Column(name="QUOTE_TYPE", length=5)
    private String     quoteType ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="POLICYENDDATE")
    private Date       policyenddate ;

    @Column(name="INTEGRATION_STATUS", length=20)
    private String     integrationStatus ;

    @Column(name="OTHEROCCUPATION", length=500)
    private String     otheroccupation ;

    @Column(name="LAPSED_REMARKS", length=200)
    private String     lapsedRemarks ;

    @Temporal(TemporalType.DATE)
    @Column(name="LAPSED_DATE")
    private Date       lapsedDate ;

    @Column(name="BRANCH_CODE", length=20)
    private String     branchCode ;

    @Column(name="VEHICLE_CONDITION", length=100)
    private String     vehicleCondition ;

    @Column(name="VEHICLE_MILEAGE", length=20)
    private String     vehicleMileage ;

    @Column(name="CHANNEL_LIST", length=50)
    private String     channelList ;

    @Column(name="SCHEMEYN", length=1)
    private String     schemeyn ;

    @Column(name="AGENCYTYPE", length=50)
    private String     agencytype ;

    @Column(name="CHASSISNO", length=50)
    private String     chassisno ;

    @Column(name="SEVERITYLEVEL")
    private BigDecimal severitylevel ;

    @Column(name="CLAIMFLAG", length=10)
    private String     claimflag ;

    @Column(name="COM_USER_DISC_1")
    private BigDecimal comUserDisc1 ;

    @Column(name="COM_USER_DISC_2")
    private BigDecimal comUserDisc2 ;

    @Column(name="COM_USER_DISC_3")
    private BigDecimal comUserDisc3 ;

    @Column(name="CLAIMS_LOADING")
    private BigDecimal claimsLoading ;

    @Column(name="JOINT_NAME", length=200)
    private String     jointName ;

    @Column(name="SOURCE_TYPE", length=100)
    private String     sourceType ;

    @Column(name="CUSTOMER_CODE", length=80)
    private String     customerCode ;

    @Column(name="CUSTOMER_CODENAME", length=200)
    private String     customerCodename ;

    @Column(name="VEHICLE_USAGE_ID")
    private BigDecimal vehicleUsageId ;

    @Column(name="MODEL_NAME", length=1500)
    private String     modelName ;

    @Column(name="ISCOMMERCIAL_YN", length=10)
    private String     iscommercialYn ;

    @Column(name="CUST_APPROVE_STATUS", length=10)
    private String     custApproveStatus ;

    @Column(name="CUST_REJECT_REMARKS", length=1000)
    private String     custRejectRemarks ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CUST_APPROVE_DATE")
    private Date       custApproveDate ;

    @Column(name="CURR_YEAR_MILEAGE", length=20)
    private String     currYearMileage ;

    @Column(name="NO_OF_DRIVER", length=20)
    private String     noOfDriver ;

    @Column(name="GREY_IMPORT_YN", length=10)
    private String     greyImportYn ;

    @Column(name="VOLUME_DISCOUNT", length=20)
    private String     volumeDiscount ;

    @Column(name="BDM_CODE", length=100)
    private String     bdmCode ;

    @Column(name="WHATSAPP_CODE", length=20)
    private String     whatsappCode ;

    @Column(name="WHATSAPP_NO")
    private BigDecimal whatsappNo ;

    @Column(name="RENEWAL_YN", length=20)
    private String     renewalYn ;

    @Column(name="RENEWAL_POLICYNO", length=100)
    private String     renewalPolicyno ;

    @Column(name="RENEWAL_CLAIMAMT")
    private BigDecimal renewalClaimamt ;

    @Column(name="PLATE_NATIONALTY")
    private BigDecimal plateNationalty ;

    @Column(name="NOOFDAYS")
    private BigDecimal noofdays ;

    @Column(name="NATIONALITY")
    private BigDecimal nationality ;

    @Column(name="MOBILE_CODE", length=20)
    private String     mobileCode ;

    @Column(name="FINALIZE_YN", length=10)
    private String     finalizeYn ;

    @Column(name="VISA")
    private BigDecimal visa ;

    @Column(name="WILAYAT")
    private BigDecimal wilayat ;

    @Column(name="IS_SALVAGEYN", length=10)
    private String     isSalvageyn ;

    @Column(name="TARGET_PRICE")
    private BigDecimal targetPrice ;

    @Column(name="PREMIUM_LOADING")
    private BigDecimal premiumLoading ;

    @Column(name="PREMIUM_DISCOUNT")
    private BigDecimal premiumDiscount ;

    @Column(name="OLD_APPLICATION_ID", length=80)
    private String     oldApplicationId ;

    @Column(name="EXTRA_OPCOVER_SELECTED", length=1000)
    private String     extraOpcoverSelected ;

    @Column(name="IMGREF_NO", length=10)
    private String     imgrefNo ;

    @Column(name="INTERESTEDIN_COMPYN", length=3)
    private String     interestedinCompyn ;

    @Column(name="STILLWANT_COMPYN", length=3)
    private String     stillwantCompyn ;

    @Column(name="EXTRA_ADDON_PREMIUM")
    private BigDecimal extraAddonPremium ;

    @Column(name="PREV_POLICYTYPE", length=10)
    private String     prevPolicytype ;

    @Column(name="OVERALL_PREMIUM")
    private BigDecimal overallPremium ;

    @Column(name="USER_LOGINID", length=50)
    private String     userLoginid ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LOGOUT_TIME")
    private Date       logoutTime ;

    @Column(name="DRIVING_TYPE")
    private BigDecimal drivingType ;

    @Column(name="AGGR_REFERENCEID", length=80)
    private String     aggrReferenceid ;

    @Column(name="ELECTRICAL_SI")
    private BigDecimal electricalSi ;

    @Column(name="NONELECTRICAL_SI")
    private BigDecimal nonelectricalSi ;

    @Column(name="CURRENCY_ID")
    private BigDecimal currencyId ;

    @Column(name="CURRENCY_TYPE", length=50)
    private String     currencyType ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="eserviceRequestDetail")
    private List<EserviceVehicleDetail> listOfEserviceVehicleDetail ; 

    @ManyToOne
    @JoinColumn(name="CUSTOMER_ID", referencedColumnName="CD_CUSTOMER_ID", insertable=false, updatable=false)
    private MsCustomerDetail msCustomerDetail ; 



}


