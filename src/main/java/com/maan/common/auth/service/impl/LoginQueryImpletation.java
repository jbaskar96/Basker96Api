package com.maan.common.auth.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.maan.common.bean.LoginMaster;
import com.maan.common.repository.LoginMasterRepository;



@Component
public class LoginQueryImpletation {

	@Autowired
	private LoginMasterRepository loginRepo;
	
	private EntityManagerFactory emfactory;
	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	private Query query=null;
	private Logger log=LogManager.getLogger(LoginQueryImpletation.class);
	@PersistenceContext
	private EntityManager em;
	private Properties prop = new Properties();
	
	
	public LoginQueryImpletation() {
        try {
        	InputStream  inputStream = getClass().getClassLoader().getResourceAsStream("oracle.properties");
        	if (inputStream != null) {
				prop.load(inputStream);
			}
        	
        } catch (Exception e) {
            log.info(e);
        }
    }
	public Map<String, Object> getLoginMaster(String userId, String app_id) {  
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			
			List<String> statusList = new ArrayList<String>();
			statusList.add("T");statusList.add("Y");
			List<LoginMaster> loginList = loginRepo.findByLoginIdAndAppIdAndStatusIn(userId,app_id,statusList);
			if(loginList!=null&&loginList.size()>0) {
				LoginMaster loginData= loginList.get(0);
				map.put("PWD_COUNT", loginData.getPwdCount());
				map.put("login_id", loginData.getLoginId());
			}
		}catch(Exception e) {
			log.info(e);
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getLoginMasterelse(String userId, String epwd, String app_id) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String qutext = prop.getProperty("LOGIN_MASTERELSE");
			query = em.createNativeQuery(qutext);		
			query.setParameter(1, userId);
			query.setParameter(2, epwd);
			query.setParameter(3, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				map = list.get(0);
			}
		}catch(Exception e) {
			log.info(e);
		}
		return map;
	}

	public void updatePwdCountReset(String userId, String app_id) {
		try {
			EntityManager em1 = emfactory.createEntityManager();
			String qutext = prop.getProperty("UPD_LOGIN_MASTER");
			query = em1.createNativeQuery(qutext);		
			query.setParameter(1, userId);
			query.setParameter(2, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			em1.getTransaction().begin();
			int rowsUpdated = query.executeUpdate();
			em1.getTransaction().commit();
			em1.close();
			log.info("update login master ======> "+rowsUpdated); 
		}catch(Exception e) {  
			log.info(e);
		}
		
	}

	public void updatePwdCount(String userId, String app_id) {
		try {
			EntityManager em1 = emfactory.createEntityManager();
			String qutext = prop.getProperty("UPD_LOGIN_PWDCOUNT");
			query = em1.createNativeQuery(qutext);		
			query.setParameter(1, userId);
			query.setParameter(2, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			em1.getTransaction().begin();
			int rowsUpdated = query.executeUpdate();
			em1.getTransaction().commit();
			em1.close();
			log.info("update login master password count======> "+rowsUpdated); 
		}catch(Exception e) {  
			log.info(e);
		}
	}

	@SuppressWarnings("unchecked")
	public int getPwdInvaildCount(String userId, String app_id) {
		int rate=0;
		try {
			String qutext = prop.getProperty("COUNT_LOGIN_PWD");
			query = em.createNativeQuery(qutext);	
			query.setParameter(1, userId);
			query.setParameter(2, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				rate = list.get(0).get("PWD_COUNT")==null?0:Integer.parseInt(list.get(0).get("PWD_COUNT").toString());
			} 
		}catch(Exception e) {
			log.info(e);  
		}
		return rate;
	}

	public void updatePwdStatus(String status, String userId, String app_id) {
		try {
			EntityManager em1 = emfactory.createEntityManager();
			String qutext = prop.getProperty("UPD_LOGIN_PWDSTATUST");
			query = em1.createNativeQuery(qutext);		
			query.setParameter(1, status);
			query.setParameter(2, userId);
			query.setParameter(3, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			em1.getTransaction().begin();
			int rowsUpdated = query.executeUpdate();
			em1.getTransaction().commit();
			em1.close();
			log.info("update login master password status======> "+rowsUpdated); 
		}catch(Exception e) {  
			log.info(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserInfo(String user, String appId) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String qutext = prop.getProperty("GET_USER_INFO");
			query = em.createNativeQuery(qutext);		
			query.setParameter(1, user);
			query.setParameter(2, appId);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				map = list.get(0);
			}
		}catch(Exception e) {
			log.info(e);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public String getExpireTime(String time) {  
		String rate="";
		try {
			String qutext = prop.getProperty("GET_MARINERATE_EXPRIT");
			query = em.createNativeQuery(qutext);		
			query.setParameter(1, time);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				rate = list.get(0).get("EXPIRETIME")==null?"":list.get(0).get("EXPIRETIME").toString();
			}
		}catch(Exception e) {
			log.info(e);  
		}
		return rate;
	}
	@SuppressWarnings("unchecked")
	public Long getChangePassowrdCount(String loginId, String epass) {  
		String rate="";
		try {
			String qutext = prop.getProperty("GET_CHANGEPWD_COUNT");
			query = em.createNativeQuery(qutext);		
			query.setParameter(1, loginId);
			query.setParameter(2, epass);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				rate = list.get(0).get("PWDCOUNT")==null?"0":list.get(0).get("PWDCOUNT").toString();
			}
		}catch(Exception e) {
			log.info(e);  
		}
		return Long.valueOf(rate);
	}
	@SuppressWarnings("unchecked")
	public int getVaildPwdDay(String userId, String app_id,String insuranceId) {
		int rate=0;
		try {
			String qutext = prop.getProperty("LOGIN_VALIDPWD_DAY");
			query = em.createNativeQuery(qutext);	
			query.setParameter(1, userId);
			query.setParameter(2, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				rate = list.get(0).get("DAYS")==null?0:Integer.parseInt(list.get(0).get("DAYS").toString());
			} 
		}catch(Exception e) {
			log.info(e);  
		}
		return rate;
	}

	@SuppressWarnings("unchecked")
	public int getVaildPwdTime(String userId, String app_id) {  
		int rate=0;
		try {
			String qutext = prop.getProperty("LOGIN_VALIDPWD_TIME");
			query = em.createNativeQuery(qutext);	
			query.setParameter(1, userId);
			query.setParameter(2, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				rate = list.get(0).get("HOURS")==null?0:Integer.parseInt(list.get(0).get("HOURS").toString());
			} 
		}catch(Exception e) {
			log.info(e);  
		}
		return rate;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserBasicInfo(String userId, String app_id) { 
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String qutext = prop.getProperty("GET_USERBASIC_INFO");
			query = em.createNativeQuery(qutext);		
			query.setParameter(1, userId);
			query.setParameter(2, app_id);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String,Object>> list = query.getResultList();
			if(!CollectionUtils.isEmpty(list)) {
				map = list.get(0);
			}
		}catch(Exception e) {
			log.info(e);
		}
		return map;
	}

}