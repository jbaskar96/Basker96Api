package com.maan.common.msw.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.maan.common.msw.SMSService;
import com.maan.common.upload.request.MSWReq;
import com.maan.common.upload.service.impl.CommonService;

@Service
public class SMSServiceImpl implements SMSService {

	@Autowired
	private CommonService comSer;
	private Logger log = LogManager.getLogger(getClass());

	@Override
	public String sendsms_tocust(MSWReq request) {
		try {
			String response = "";
			String type = StringUtils.isBlank(request.getType()) ? "" : request.getType();
			String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id(), mobno = "";

			String usertype = StringUtils.isBlank(request.getUsertype()) ? "" : request.getUsertype();
			String subusertype = StringUtils.isBlank(request.getSubusertype()) ? "" : request.getSubusertype();

			log.info("sendsms_tocust--> Type: " + type + " productid: " + productid);
			log.info("sendsms_tocust--> usertype: " + usertype + "subusertype: " + subusertype);

			List<String> mobnos = new ArrayList<>();

			request = getsmscontent(request);
			mobnos = request.getMobilenos();
			if (mobnos.size() > 0 && mobnos != null) {
				for (int i = 0; i < mobnos.size(); i++) {
					mobno = mobnos.get(i);
					if (StringUtils.isNotBlank(mobno)) {
						request.setMobileno(mobno);
						comSer.reqPrint(request);
						response = sendingsms(request);
					}
				}
			}

			return response;

		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	private MSWReq getsmscontent(MSWReq req) {
		String type = req.getType();
		String quoteno = req.getQuote_no();
		String reqrefno = req.getRequestreferenceno();
		String productid = StringUtils.isBlank(req.getProduct_id()) ? "" : req.getProduct_id();

		log.info("getsmscontent--> type: " + type + " productid: " + productid);
		log.info("getsmscontent--> quoteno: " + quoteno + " reqrefno: " + reqrefno);

		try {

			Map<String, Object> map = new HashMap<String, Object>(); 
					//mtmRepo.getsmstemplate(type, productid);
			log.info("getsmscontent--> map: " + map.size());

			if (map.size() > 0) {
				Map<String, Object> customerdet = new HashMap<>();
				Map<String, Object> otpdet = new HashMap<>();

				String smssub = map.get("SMS_SUBJECT") == null ? "" : map.get("SMS_SUBJECT").toString();
				String smsbody = map.get("SMS_BODY_EN") == null ? "" : map.get("SMS_BODY_EN").toString();
				String smsbodyar = map.get("SMS_BODY_AR") == null ? "" : map.get("SMS_BODY_AR").toString();
				String smsregards = map.get("SMS_REGARDS") == null ? "" : map.get("SMS_REGARDS").toString();
				String smsregardsar = map.get("SMS_REGARDS_AR") == null ? "" : map.get("SMS_REGARDS_AR").toString();

				String mobileno = "", otpid = req.getOtpid();
				String otp = StringUtils.isBlank(req.getOtp()) ? "" : req.getOtp();
				String tinyurl = StringUtils.isBlank(req.getTinyurl()) ? "" : req.getTinyurl();
				String expirydate = "",
						productType = StringUtils.isBlank(req.getProduct_type()) ? "" : req.getProduct_type();
				String appid = "", loginid = "", brokername = "", admloginid = "";
				String usertype = StringUtils.isBlank(req.getUsertype()) ? "" : req.getUsertype();
				String subusertype = StringUtils.isBlank(req.getSubusertype()) ? "" : req.getSubusertype();

				List<String> loginids = new ArrayList<>();
				List<String> mobnos = new ArrayList<>();

				req.setSmsbody(smsbody);
				req.setSmssubject(smssub);
				req.setMobileno(mobileno);
				req.setCustomerName(customerdet.get("CUST_NAME") == null ? "" : customerdet.get("CUST_NAME").toString());
				req.setPolicyno(customerdet.get("POLICY_NO") == null ? "" : customerdet.get("POLICY_NO").toString());
				req.setMobilenos(mobnos);

			}
			return req;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	private String sendingsms(MSWReq req) {
		String response = "";
		String content = "";
		String status = "";
		try {
			String smsurl = comSer.getwebserviceurlProperty().getProperty("sms.api.url");
			String username = comSer.getwebserviceurlProperty().getProperty("sms.user.name");
			String password = comSer.getwebserviceurlProperty().getProperty("sms.password");
			String senderid = comSer.getwebserviceurlProperty().getProperty("sms.sender.id");
			String source = comSer.getwebserviceurlProperty().getProperty("sms.api.id");
			String mobno = req.getMobileno();
			content = req.getSmsbody();

			if (StringUtils.isNotBlank(mobno)) {
				String requestUrl = smsurl + "username=" + URLEncoder.encode(username, "UTF-8") + "&password="
						+ URLEncoder.encode(password, "UTF-8") + "&phoneno=" + URLEncoder.encode(mobno, "UTF-8")
						+ "&message=" + URLEncoder.encode(content, "UTF-8") + "&sender="
						+ URLEncoder.encode(senderid, "UTF-8") + "&source=" + URLEncoder.encode(source, "UTF-8");
				log.info(requestUrl);
				URL url = new URL(requestUrl);
				HttpURLConnection uc = (HttpURLConnection) url.openConnection();
				String resMsg = uc.getResponseMessage();
				log.info("sendingsms--> Response Message: " + resMsg);
				int resCode = uc.getResponseCode();
				log.info("sendingsms--> Response Code: " + resCode);
				BufferedReader dataStreamFromUrl = new BufferedReader(new InputStreamReader(uc.getInputStream()));

				dataStreamFromUrl.close();
				status = resCode == 200 ? "Y" : "N";
				uc.disconnect();

				//int updcount = omSmsDataDetRepo.updateSMSResponse(resCode, resMsg, requestUrl, req.getOtherrefno(),req.getType(), status);

				response = "SMS Sended For this Quoteno: " + req.getQuote_no();
			}
		} catch (Exception e) {
			log.error(e);
			status = "N";

			//int updcount = omSmsDataDetRepo.updateSMSResponse(500, e.getLocalizedMessage(), "", req.getOtherrefno(),req.getType(), status);

			response = "SMS Not Sended For this Quoteno: " + req.getQuote_no();
		}
		return response;
	}

}
