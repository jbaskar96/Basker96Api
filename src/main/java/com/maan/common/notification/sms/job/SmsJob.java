package com.maan.common.notification.sms.job;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

import com.maan.common.bean.SmsConfigMaster;
import com.maan.common.notification.sms.dto.Sms;

public class SmsJob implements Callable<Object> {
	private SmsConfigMaster sms;
	private Sms request;
	
	public SmsJob(SmsConfigMaster sms,Sms request) {
		this.sms=sms;
		this.request=request;
	}
	
	@Override
	public Object call() throws Exception {	 
		return sentsms();
	}

 
	 
	private String type="0";	
	private String dlr="0";

	private String destination;	 
	private String source;	 
	private String server;	 
	private int port;
	
	private String sentsms() {
		HttpURLConnection httpConnection =null;
		try {
			// Url that will be called to submit the message
			URL sendUrl = new URL(sms.getSmsPartyUrl());
			httpConnection= (HttpURLConnection) sendUrl
			.openConnection();
			// This method sets the method type to POST so that
			// will be send as a POST request
			httpConnection.setRequestMethod("POST");
			// This method is set as true wince we intend to send
			// input to the server
			httpConnection.setDoInput(true);
			// This method implies that we intend to receive data from server.
			httpConnection.setDoOutput(true);
			// Implies do not use cached data
			httpConnection.setUseCaches(false);
			// Data that will be sent over the stream to the server.
			DataOutputStream dataStreamToServer = new DataOutputStream(
			httpConnection.getOutputStream());
			dataStreamToServer.writeBytes("username="
			+ URLEncoder.encode(sms.getSmsUserName(), "UTF-8") + "&password="
			+ URLEncoder.encode(sms.getSmsUserPass(), "UTF-8") + "&type="
			+ URLEncoder.encode(this.type, "UTF-8") + "&dlr="
			+ URLEncoder.encode(this.dlr, "UTF-8") + "&destination="
			+ URLEncoder.encode(request.getMobileCode()+request.getMobileNo(), "UTF-8") + "&source="
					+ URLEncoder.encode(sms.getSenderid(), "UTF-8") + "&message="
					+ URLEncoder.encode(request.getSmsContent()+request.getSmsRegards(), "UTF-8"));
			System.out.println("SMS request"+dataStreamToServer.toString());
					dataStreamToServer.flush();
					dataStreamToServer.close();
					// Here take the output value of the server.
					BufferedReader dataStreamFromUrl = new BufferedReader(
					new InputStreamReader(httpConnection.getInputStream()));
					String dataFromUrl = "", dataBuffer = "";
					// Writing information from the stream to the buffer
					while ((dataBuffer = dataStreamFromUrl.readLine()) != null) {
					dataFromUrl += dataBuffer;
					}
					/**
					* Now dataFromUrl variable contains the Response received fromthe
					* server so we can parse the response and process it accordingly.
					*/
					dataStreamFromUrl.close();
					System.out.println("Response: " + dataFromUrl);
			
			return  "Sent";
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(httpConnection!=null)
				httpConnection.disconnect();
		}
		return "Not Sent";
	}
}
