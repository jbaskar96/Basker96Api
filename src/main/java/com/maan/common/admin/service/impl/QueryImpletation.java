package com.maan.common.admin.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Component;

@Component
public class QueryImpletation {

	private Query query = null;
	private Logger log = LogManager.getLogger(QueryImpletation.class);
	@PersistenceContext
	private EntityManager em;
	private Properties prop = new Properties();

	@SuppressWarnings("unchecked")
	public String getCustomerSeqGenerated() throws Exception {
		String code = "";
		// try {
		String qutext = prop.getProperty("GET_CUSTOMERSEQ_GENERATED");
		query = em.createNativeQuery(qutext);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> list = query.getResultList();
		if (!CollectionUtils.isEmpty(list)) {
			code = list.get(0).get("CUSTOMERID") == null ? "" : list.get(0).get("CUSTOMERID").toString();
		}
		return code;
	}
	
	@SuppressWarnings("unchecked")
	public String getUserSeqGenerated() {
		String code = "";
		try {
			String qutext = prop.getProperty("GET_USERSEQ_GENERATED");
			query = em.createNativeQuery(qutext);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>> list = query.getResultList();
			if (!CollectionUtils.isEmpty(list)) {
				code = list.get(0).get("USERCODE") == null ? "" : list.get(0).get("USERCODE").toString();
			}
		} catch (Exception e) {
			log.info(e);
		}
		return code;
	}


}
