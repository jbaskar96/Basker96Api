package com.maan.common.otp.service.impl;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.mail.Session;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.maan.common.auth.token.JwtAuthenticationFilter;
import com.maan.common.error.Error;
import com.maan.common.otp.request.OtpReq;
import com.maan.common.otp.response.OtpValidationRes;
import com.maan.common.otp.service.OtpService;
import com.maan.common.upload.request.OtpMailReq;
import com.maan.common.upload.request.TimeBasedOneTimePasswordGenerator;
import com.maan.common.upload.response.LoginMasterResDto;

@Service
public class OtpServiceImpl implements OtpService{	
	
/*	@Autowired
	private OtpDataDetailRepository otpDataRepo;
	@Autowired
	private CommonServiceImpl commonService;
	@Autowired
	private PremiaPolicyDetailRepository policyRepo;
	@Autowired
	private CustomerTableRepository custtabRepo; */
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Value(value = "${MailApi}")
	private String MailApicall;
	@Value(value = "${SmsApi}")
	private String SmsApicall;
	@Value(value = "${BasicAuthPass}")
	private String BasicAuthPass;
	@Value(value = "${BasicAuthName}")
	private String BasicAuthName;
	
	SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static Logger log=LogManager.getLogger(OtpServiceImpl.class);	
	private static Session session1=null; 	
	
	@Override
	public OtpValidationRes getOTP(OtpReq request) {
		try {			
			
			//PremiaPolicyDetail entity = policyRepo.findByQuotationPolicyNo(request.getReferenceNo());
			request.setEmailId("");
			
			int otp=generateOTP();
			String otpstr=Integer.valueOf(otp).toString();
			
			
			String reqrefno=request.getReferenceNo();
			String mobileno=request.getMobileNo();
			
			if(StringUtils.isNotBlank(mobileno)) {
				request.setMobileNo(mobileno);
				request.setOtp(new BigDecimal(otpstr));
				if(request.getOtpId()!=null) {
					updateResendOtp(request);
				}else {
					insertintootpdatadetail(request);
				}
				
				sendMail(request);
				sendSms(request);
			    	
					OtpValidationRes res = new OtpValidationRes();
					res.setOtpvalidationstatus(String.valueOf(request.getOtpId()));
					
			    	insertClaimDetails(request);
				return res;	
			}
		}catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	private void insertintootpdatadetail(OtpReq req) {		
		try {			
			
	/*		int mailotp=generateOTP();			
			long maxsno = otpDataRepo.count();
			
			Date newDate = new Date();
			Calendar c = Calendar.getInstance();
			 c.setTime(newDate);
			 c.add(Calendar.MINUTE, 4);
			 Date entryDate4m = c.getTime();
			Random random = new Random();
			String id = String.format("%04d", random.nextInt(10000));
			 
			OtpDataDetail otp = new OtpDataDetail();
			
			otp.setCode(req.getCode());
			otp.setSNo(new BigDecimal(maxsno+1));
			otp.setOtpType("GET_OTP_CLAIM");
			otp.setOtp(req.getOtp());
			otp.setOtpId(new BigDecimal(id));
			otp.setEntryDate(newDate); 
			otp.setExpiryDate(entryDate4m);
			otp.setMobileNo(req.getMobileNo()); 
			otp.setEmailId(req.getEmailId());
			otp.setSessionId(req.getSessionId()); 
			otp.setStatus("Y");
			otp.setMailOtp(new BigDecimal(mailotp));
			otp.setInsId(req.getInsId());
			otp.setReferenceNo(req.getReferenceNo());
			otp.setWhatsappNo(req.getWhatsappNo());
			otp.setEmailId(req.getEmailId());
			otpDataRepo.save(otp);	
			req.setOtpId(new BigDecimal(id)); */
			
		} catch (Exception e) {log.error(e);}
	}
	
	private void updateResendOtp(OtpReq req) {
		try {
		/*(	List<OtpDataDetail> otpList = otpDataRepo.findByOtpId(req.getOtpId());
			if(otpList!=null&&otpList.size()>0) {
				Date newDate = new Date();
				Calendar c = Calendar.getInstance();
				 c.setTime(newDate);
				 c.add(Calendar.MINUTE, 4);
				 Date entryDate4m = c.getTime();
				 int mailotp=generateOTP();
				 
			    OtpDataDetail updateOtpData =otpList.get(0);
			    updateOtpData.setOtp(req.getOtp()); 
			    updateOtpData.setMailOtp(new BigDecimal(mailotp));
			    updateOtpData.setEntryDate(newDate); 
			    updateOtpData.setExpiryDate(entryDate4m);
			    otpDataRepo.save(updateOtpData);
			    req.setOtpId(req.getOtpId()); 
			} */
		} catch (Exception e) {log.error(e);}
	}
	
	public int generateOTP() {
		int otp = 0;
		try {
			final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
			final Key secretKey;
			{
				final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
				// SHA-1 and SHA-256 prefer 64-byte (512-bit) keys; SHA512 prefers 128-byte keys
				keyGenerator.init(512);
				secretKey = keyGenerator.generateKey();
			}
			Date now = new Date();
			otp = totp.generateOneTimePassword(secretKey, now);
			log.info("getOTP== OTP: " + otp);
		} catch (Exception e) {
			log.error(e);
		}
		return otp;
	}

	public List<Error> OTPValidation(OtpReq request){
		try {
			List<Error> errorlist=new ArrayList<Error>();
			BigDecimal otp=request.getOtp();
			BigDecimal otpid=request.getOtpId();
				
			if(otp==null || request.getOtp().toString().length()>6 || !request.getOtp().toString().matches("[0-9]+")) {
				errorlist.add(new Error("09","OTP","Not a Valid OTP"));
			}else if(checkOtp(otp,otpid)){
				errorlist.add(new Error("09","OTP","Not a Valid OTP"));
			}else if(errorlist.size()==0){
				if(checkOtpExpiry(otp,otpid)){
					errorlist.add(new Error("10","OTP","Otp is Expired"));
				}
			}
			return errorlist;
		}catch (Exception e) {log.error(e);}		
		return null;
	}
	
	public boolean checkOtp(BigDecimal otp,BigDecimal otpId) {
		boolean result = true;		
		try{
			if(otp!=null && otpId!=null) {
			/*	List<OtpDataDetail> otpList =otpDataRepo.findByOtpIdAndOtp(otpId, otp);
				int count= otpList.size();
				if(count>0){
					result = false;
				} */
			}
		}catch(Exception e){log.error(e);}
		return result;
	}

	public boolean checkOtpExpiry(BigDecimal otp,BigDecimal otpId) {
		boolean result = true;		
		try{			
			Date entryDate = new Date();
			if(otp!=null && otpId!=null) {
			/*	List<OtpDataDetail> otpList = otpDataRepo.findByOtpIdAndOtpAndExpiryDateIsAfter(otpId,otp,entryDate);
				int count= otpList.size();
				if(count > 0){
					result = false;
				} */
			}
		}catch(Exception e){log.error(e);}
		return result;
	}

	@Override
	public LoginMasterResDto createUser(OtpReq requst) {
		LoginMasterResDto res = new LoginMasterResDto();
		try {
		//	res = commonService.createUser(requst);			
		} catch (Exception e) {
			log.error(e);
			res = null;
		}
		return res;
	}

	@Override
	public List<Error> validateMobileAndEmail(OtpReq request) {
		
		List<Error> errorList = new ArrayList<Error>();
		
		//PremiaPolicyDetail entity = policyRepo.findByQuotationPolicyNo(request.getReferenceNo());
		
		try {
			
			/*//PolicyNo
			if (StringUtils.isBlank(request.getReferenceNo()) || request.getReferenceNo() == null || "".equals(request.getReferenceNo()) || "null".equalsIgnoreCase(request.getReferenceNo())) {
				errorList.add(new Error("01", "ReferenceNo", "Please Enter Policy No"));
			}else if(!request.getReferenceNo().equals(entity.getQuotationPolicyNo())) {
				errorList.add(new Error("02", "ReferenceNo", "Please Enter Valid Policy No"));
			}
			
			//MobileNo
			if (StringUtils.isBlank(request.getMobileNo()) || request.getMobileNo() == null || "".equals(request.getMobileNo()) || "null".equalsIgnoreCase(request.getMobileNo())) {
				errorList.add(new Error("01", "MobileNo", "Please Enter Mobile No"));
			}else if(!request.getMobileNo().equals(entity.getContactNumber())) {
				errorList.add(new Error("02", "MobileNo", "Please Enter Regestred Mobile No"));
			}
			
			//Code
			if (StringUtils.isBlank(request.getCode()) || request.getCode() == null || "".equals(request.getCode()) || "null".equalsIgnoreCase(request.getCode())) {
				errorList.add(new Error("01", "Code", "Please Enter Code"));
			}else if(!request.getCode().equals(entity.getCode())) {
				errorList.add(new Error("02", "CodeNo", "Please Enter Regestred Code No"));
			}
			
			//Email
			if (StringUtils.isBlank(request.getEmailId()) || request.getEmailId() == null || "".equals(request.getEmailId()) || "null".equalsIgnoreCase(request.getEmailId())) {
				errorList.add(new Error("01", "EmailId", "Please Enter Email Id"));
			}else if(!request.getEmailId().equals(entity.getContactEmailId())) {
				errorList.add(new Error("02", "EmailId", "Please Enter Regestred EmailId"));
			}*/
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return errorList;
	}

	private void insertClaimDetails(OtpReq request) {
		
		/*PremiaPolicyDetail entity = policyRepo.findByQuotationPolicyNo(request.getReferenceNo());
		
		CustomerTable cust = new CustomerTable();		
		String Address = "";
		if(entity.getAddressLine1()!=null)
			Address= Address + "," + entity.getAddressLine1();
		if(entity.getAddressLine2()!=null)
			Address= Address + "," + entity.getAddressLine2();
		if(entity.getAddressLine3()!=null)
			Address= Address + "," + entity.getAddressLine3();
		
		cust.setAddress(Address);
		cust.setBankdetails(null);
		cust.setCustomerName(entity.getContactPerName());
		//cust.setDob();
		cust.setMailid(entity.getContactEmailId());
		cust.setMobileNumber(entity.getContactNumber());
		cust.setPolicyno(entity.getQuotationPolicyNo());
		cust.setStatus("Y");
		
		custtabRepo.save(cust);	*/	
	}
	
	private void sendMail(OtpReq request) {
		
		String url = MailApicall;
		String auth = BasicAuthName +":"+ BasicAuthPass;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );

		List<String> key = new ArrayList<String>();
		key.add(request.getOtpId().toString());

		OtpMailReq mail = new OtpMailReq();
		mail.setAutoframeyn("Y");
		mail.setFileattach("N");
		mail.setInsuranceid("100002");
		mail.setKeyvalues(key);
		mail.setNotificationtemplateid("GET_OTP_CLAIM");
		mail.setNotificationtype("MAIL");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization",authHeader);
		HttpEntity<OtpMailReq> entityReq = new HttpEntity<OtpMailReq>(mail, headers);

		ResponseEntity<Object> response = restTemplate.postForEntity(url, entityReq, Object.class);
		System.out.println(response.getBody());

	}
	private void sendSms(OtpReq request) {
		
		String url = SmsApicall;

		String auth = BasicAuthName +":"+ BasicAuthPass;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );

		List<String> key = new ArrayList<String>();
		key.add(request.getOtpId().toString());

		OtpMailReq sms = new OtpMailReq();
		sms.setAutoframeyn("Y");
		sms.setFileattach("N");
		sms.setInsuranceid("100002");
		sms.setKeyvalues(key);
		sms.setNotificationtemplateid("GET_OTP_CLAIM");
		sms.setNotificationtype("SMS");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization",authHeader);
		HttpEntity<OtpMailReq> entityReq = new HttpEntity<OtpMailReq>(sms, headers);

		ResponseEntity<Object> response = restTemplate.postForEntity(url, entityReq, Object.class);
		System.out.println(response.getBody());

	}

	
}

