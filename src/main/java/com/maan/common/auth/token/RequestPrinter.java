package com.maan.common.auth.token;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
 
@Service
public class RequestPrinter {
	private Logger log = LogManager.getLogger(RequestPrinter.class);
	public String reqPrint(Object response) {
		ObjectMapper mapper = new ObjectMapper();
		String resp = "";
		try {
			// log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
			// resp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
			resp = mapper.writeValueAsString(response);
			log.info(resp);
		} catch (Exception e) {
			log.error(e);
		}
		return resp;
	}
}
