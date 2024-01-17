package com.maan.common.upload.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.maan.common.upload.request.ConditionReq;

@Service
public class ConditionSeparator {

	private Logger log = LogManager.getLogger(getClass());

	public List<ConditionReq> get_condition(String condition) {
		List<ConditionReq> response = new ArrayList<ConditionReq>();
		try {
			TreeMap<Integer, String> hmap = new TreeMap<Integer, String>();
			hmap.put(1, "and");
			hmap.put(2, "or");

			StringBuffer cond = new StringBuffer(condition);

			String nextOperator = nextOperator(cond.toString(), hmap);
			int sno = 1;

			List<String> list = null;//fltCondRepo.get_sysmbol();

			TreeMap<Integer, String> condit = new TreeMap<Integer, String>();

			for (int i = 0; i < list.size(); i++) {
				condit.put(i + 1, list.get(i));
			}
			
			String fortempop = "";
			
			while (StringUtils.isNotBlank(nextOperator)) {
				log.info("get_condition--> nextOperator: " + nextOperator);

				String substring = cond.substring(0, cond.indexOf(nextOperator));
				log.info("get_condition--> substring:" + substring);

				String subnext = nextOperator(substring, condit);
				String[] split = substring.split(subnext);
				String field = split[0];
				String value = split[1];
				String operator = subnext;

				ConditionReq condreq = ConditionReq.builder().field(field.trim()).operator(operator.trim())
						.value(value.trim()).connector(fortempop.trim()).sno(Integer.valueOf(sno).toString()).build();
				response.add(condreq);
				sno++;
				cond.delete(0, (cond.indexOf(nextOperator) + nextOperator.length()));
				fortempop = nextOperator;
				nextOperator = nextOperator(cond.toString(), hmap);
			}

			String substring = cond.toString();
			log.info("get_condition--> substring:" + substring);
			{
				String subnext = nextOperator(substring, condit);
				String[] split = substring.split(subnext);
				String field = split[0];
				String value = split[1];
				String operator = subnext;

				ConditionReq condreq = ConditionReq.builder().field(field.trim()).operator(operator.trim())
						.value(value.trim()).connector(fortempop.trim()).sno(Integer.valueOf(sno).toString()).build();
				response.add(condreq);
			}

		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}
	
	public List<ConditionReq> get_condition2(String condition) {
		List<ConditionReq> response = new ArrayList<ConditionReq>();
		try {
			TreeMap<Integer, String> hmap = new TreeMap<Integer, String>();
			hmap.put(1, "&&");
			hmap.put(2, "||");

			StringBuffer cond = new StringBuffer(condition);

			String nextOperator = nextOperator(cond.toString(), hmap);
			int sno = 1;

			//List<String> list = null;//fltCondRepo.get_sysmbol();

			//TreeMap<Integer, String> condit = new TreeMap<Integer, String>();

			/*for (int i = 0; i < list.size(); i++) {
				condit.put(i + 1, list.get(i));
			}*/
			
			String fortempop = "";
			
			while (StringUtils.isNotBlank(nextOperator)) {
				log.info("get_condition--> nextOperator: " + nextOperator);

				String substring = cond.substring(0, cond.indexOf(nextOperator));
				log.info("get_condition--> substring:" + substring);

				//String subnext = nextOperator(substring, condit);
				//String[] split = substring.split(subnext);
				//String field = split[0];
				//String value = split[1];
				//String operator = subnext;

				ConditionReq condreq = ConditionReq.builder()
						.field(substring.trim())
						//.operator(operator.trim())
						//.value(value.trim())
						.connector(fortempop.trim())
						.sno(Integer.valueOf(sno).toString())
						.build();
				response.add(condreq);
				sno++;
				cond.delete(0, (cond.indexOf(nextOperator) + nextOperator.length()));
				fortempop = nextOperator;
				nextOperator = nextOperator(cond.toString(), hmap);
			}

			String substring = cond.toString();
			log.info("get_condition--> substring:" + substring);
			{
				//String subnext = nextOperator(substring, hmap);
				//String[] split = substring.split(subnext);
				//String field = split[0];
				//String value = split[1];
				//String operator = subnext;

				ConditionReq condreq = ConditionReq.builder()
						.field(substring.trim())
						//.operator(operator.trim())
						//.value(value.trim())
						.connector(fortempop.trim())
						.sno(Integer.valueOf(sno).toString())
						.build();
				response.add(condreq);
			}

		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}

	public String nextOperator(String commonStrng, TreeMap<Integer, String> hmap) {
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();

		for (Map.Entry<Integer, String> my : hmap.entrySet()) {
			int index = commonStrng.indexOf(my.getValue());
			map.put(index, (index == -1 ? "" : my.getValue()));
		}

		if (map.size() > 1 && map.firstKey() == -1) {
			map.remove(map.firstKey());
		}

		return map.get(map.firstKey());
	}

}
