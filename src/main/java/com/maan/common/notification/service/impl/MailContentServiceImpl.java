package com.maan.common.notification.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maan.common.bean.NotifTemplateMaster;
import com.maan.common.bean.SmsDataDetails;
import com.maan.common.notification.mail.dto.Mail;
import com.maan.common.notification.mail.dto.MailMaster;
import com.maan.common.notification.service.MailContentService;
import com.maan.common.notification.service.NotificationConfig;
import com.maan.common.notification.service.OracleQuery;
import com.maan.common.notification.service.PushIntoKafka;
import com.maan.common.notification.sms.dto.PushMail;
import com.maan.common.notification.sms.dto.Sms;
import com.maan.common.notification.sms.dto.SmsConfigMasterDto;
import com.maan.common.repository.MailMasterRepository;
import com.maan.common.repository.NotifTemplateMasterRepository;
import com.maan.common.repository.SmsConfigMasterRepository;
import com.maan.common.repository.SmsDataDetailsRepository;


@Service
public class MailContentServiceImpl implements MailContentService {

	@Autowired
	private NotifTemplateMasterRepository notfication;
	@Autowired
	private MailMasterRepository mailm;
	@Autowired
	private NotificationConfig notfConfig;
	
	@Autowired
	private SmsConfigMasterRepository smsm; 
	@Autowired
	private OracleQuery oracle;
	
	@Autowired
	private SmsDataDetailsRepository smsdatarepo; 
	
	@Value(value = "${kafka.producer.job1.link}")
	private String kafkaproducerlink;
	
	@Value(value = "${kafka.producer.job2.link}")
	private String kafkaproducerlink2;
	
	
	@Override
	public Mail pushNotifcation(PushMail request) {
		
		return generateContentReturnMail(request);
	}

	private Mail generateContentReturnMail(PushMail request) {
			try {
				NotifTemplateMaster notifMaster = notfication.findByInsIdAndStatusAndRemarks(new BigDecimal(request.getInsuranceId()), "Y", request.getNotificationTemplateId());
				if(notifMaster!=null  ) {
					Map<String, Object> content=null;
					String queryKey = notifMaster.getQueryKey();
					if(request.getKeyvalues() != null && request.getKeyvalues().size()>0) {
					//	String query=oracle.getQuery(queryKey);
						List<Map<String,Object>> contents = oracle.getListFromQuery(queryKey, request.getKeyvalues());
						content=contents.get(0);
						
					}
					
					if("mail".equals(request.getNotificationType().getType()) && "Y".equals(notifMaster.getMailRequired()) && !"".equals(String.valueOf(content.get("EMAIL_ID")))) {
						String subject=frameContentMail(notifMaster.getMailSubject(),request,content);
						String regards=notifMaster.getMailRegards();						
						String mailbodycontent = frameContentMail(notifMaster.getMailBody(),request,content);
						String body = frameMailTemplate(mailbodycontent,subject,regards);
						Mail mail=new Mail();
						mail.setSubject(subject);
						mail.setMailbodyContenttype("text/html");
						mail.setMailbody(body +"\n\n"+regards);
						List<String> mails=new ArrayList<String>();
						mails.add(String.valueOf(content.get("EMAIL_ID")));
						mail.setTomails(mails);
											
						
						List<String> cc = new ArrayList<String>();
						String ccmails=content.get("CC")==null?null:String.valueOf(content.get("CC"));
						if(ccmails !=null && ccmails.indexOf(",")!=-1) {
							String[] ccmail = ccmails.split(",");
							for(int i=0;i<ccmail.length;i++) 
								cc.add(ccmail[i]);
						}						
						
						if(cc!=null && cc.size()>0)
							mail.setCcmails(cc);
						
						/*MailMaster master = mailm.getById(request.getInsuranceId());*/
						List<MailMaster> filter = notfConfig.getMailMasters().stream().filter(m->(m.getApplicationId().equals(request.getInsuranceId()) && "Y".equals(m.getStatus()))).collect(Collectors.toList());
						
						mail.setMaster(filter.get(0));
						if( "Y".equals(request.getFileattach()) ) {
							mail.setFiles(request.getFileattachkeys());
						}
						
						return mail;
					}
				}
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	private Sms  generateContentReturnSMS(PushMail request) {
		try {
			NotifTemplateMaster notifMaster = notfication.findByInsIdAndStatusAndRemarks(new BigDecimal(request.getInsuranceId()), "Y", request.getNotificationTemplateId());
			if(notifMaster!=null  ) {
				Map<String, Object> content=null;
				String queryKey = notifMaster.getQueryKey();
				if(request.getKeyvalues() != null && request.getKeyvalues().size()>0) {
				//	String query=oracle.getQuery(queryKey);
					List<Map<String, Object>> contents = oracle.getListFromQuery(queryKey, request.getKeyvalues());
					content=contents.get(0);
					
				}
				if("sms".equals(request.getNotificationType().getType()) && "Y".equals(notifMaster.getSmsRequired()) && !"".equals(String.valueOf(content.get("MOBILENO")))) {
					Sms sms=new Sms();
					String contentsms = frameContentSms(notifMaster,request,content);
					
					sms.setMobileCode(content.get("CODE").toString());
					sms.setMobileNo(content.get("MOBILENO").toString());
					sms.setSmsContent(contentsms);
					sms.setSmsSubject(notifMaster.getSmsSubject());
					sms.setSmsRegards(notifMaster.getSmsRegards());
					 
					
					List<SmsConfigMasterDto> collect = notfConfig.getSmsConfigMasters().stream().filter(m->(m.getInsId().compareTo(new BigDecimal(request.getInsuranceId()))==0 && "Y".equals(m.getStatus()))).collect(Collectors.toList());
					sms.setMaster(collect.get(0));
					/// Table Insert
					SmsDataDetails data = new SmsDataDetails();
					
					//id
					String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
					String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
					String date = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
					String hour =  String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
					String minute =  String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
					String second =  String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
					String milliSecond =  String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND));
					
					String smsId = year.substring(2,4) + date + month + hour + minute + second + milliSecond+(int)(Math.random()*100);
					
					//Save
					data.setSNo(new BigDecimal(smsId));
					data.setCustName(String.valueOf(content.get("CUST_NAME")));
					data.setInsId(new BigDecimal(request.getInsuranceId()));
					data.setMobileNo(content.get("MOBILENO").toString());
					data.setPolicyNo(content.get("POLICY_NO").toString());
					data.setProductId(new BigDecimal(62));				 
					data.setSmsRefNo(smsId);
					smsdatarepo.save(data);	 
					
					
					
					return sms;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String frameContentMail(String body, PushMail request,Map<String, Object> content) {
		try {
			
			//String body=;
			if(content!=null && !content.isEmpty()){
				for (Entry<String, Object> entry : content.entrySet()) {
					body=body.replaceAll("\\{"+entry.getKey()+"\\}", entry.getValue()==null?"":entry.getValue().toString()); //entry.getKey()
				}
			}
			
			return body;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String frameContentSms(NotifTemplateMaster notifMaster, PushMail request,Map<String, Object> content) {
		try {
			
			String body=notifMaster.getSmsBodyEn();
			if(content!=null && !content.isEmpty()){
				for (Entry<String, Object> entry : content.entrySet()) {
					body=body.replaceAll("\\{"+entry.getKey()+"\\}",( entry.getValue()==null?"":entry.getValue().toString())); //entry.getKey()
				}
			}
			
			return body;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String frameMailTemplate(String mailbodycontent, String subject, String regards) {
		
		try {
			StringBuilder html = new StringBuilder();			
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/email.html")));

			String val;
			while ((val = br.readLine()) != null) {
				html.append(val);
			}
			String result = html.toString();
			
			result = result.replace("{MailSub}", subject);
			result = result.replace("{MailBody}", mailbodycontent);
			result = result.replace("{MailFooter}", regards);
			
			br.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	 

	@Override
	public boolean sentMail(PushMail request) {
		try {
			Mail pojo = generateContentReturnMail(request);
			if(pojo!=null) {
				//MailMaster master = mailm.getById(request.getInsuranceId());
			//	MailJob job=new MailJob( pojo);
				PushIntoKafka job=new PushIntoKafka(pojo,kafkaproducerlink);
				FutureTask<Object> futureTask = new FutureTask<>(job);

				Thread thread=new Thread(futureTask);		
				 thread.start();
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean sentSMS(PushMail request) {
		 try {
				Sms pojo = generateContentReturnSMS(request);
				if(pojo!=null) {
					/*SmsConfigMaster master = smsm.getById(new BigDecimal(request.getInsuranceId()));
					SmsJob job=new SmsJob(master, pojo);*/
					
					PushIntoKafka job=new PushIntoKafka(pojo,kafkaproducerlink2);				
					
					FutureTask<Object> futureTask = new FutureTask<>(job);
					Thread thread=new Thread(futureTask);		
					 thread.start();
				}
			 return true;
		 }catch (Exception e) {
			 e.printStackTrace();
		}
		return false;
	}
	
	 

}
