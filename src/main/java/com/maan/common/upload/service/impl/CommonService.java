package com.maan.common.upload.service.impl;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
 
@Service
public class CommonService {

	@Autowired
	private HttpServletRequest servletRequest;
	@PersistenceContext
	private EntityManager em;
	/*@Autowired
	private MarineConstantDetailRepo constantRepo;*/

	private Logger log = LogManager.getLogger(getClass());

	public String formatdate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date d = sdf.parse(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.S");
			date = sdf2.format(d);
		} catch (Exception e) {
			log.error(e);
		}
		return date;
	}

	public String formatdatewithtime(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date d = sdf.parse(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
			date = sdf2.format(d);
		} catch (Exception e) {
			log.error(e);
		}
		return date;
	}

	public Date formatdate2(String date) {
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			date = sdf.format(sdf.parse(date));
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
			d = sdf2.parse(date);
		} catch (Exception e) {
			log.error(e);
		}
		return d;
	}

	public String formatdatewithouttime(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date d = sdf.parse(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			date = sdf2.format(d);
		} catch (Exception e) {
			log.error(e);
		}
		return date;
	}

	public String formatdatewithouttime(Date date) {
		String dates = "";
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			dates = sdf2.format(date);
		} catch (Exception e) {
			log.error(e);
		}
		return dates;
	}

	public Date formatdatewithouttime2(String date) {
		Date d = null;
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			d = sdf2.parse(date);
		} catch (Exception e) {
			log.error(e);
		}
		return d;
	}

	public String formatdatewithtime2(Date date) {
		String dates = "";
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy kk.mm.ss");
			dates = sdf2.format(date);
		} catch (Exception e) {
			log.error(e);
		}
		return dates;
	}

	public String formatdatewithtime3(Date date) {
		String dates = "";
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy_kk.mm.ss.SSS");
			dates = sdf2.format(date);
		} catch (Exception e) {
			log.error(e);
		}
		return dates;
	}

	public String formatdatewithtime4(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date d = sdf.parse(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			date = sdf2.format(d);
		} catch (Exception e) {
			log.error(e);
		}
		return date;
	}

	public Date formatdatewithouttime3(String date) {
		Date d = null;
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			d = sdf2.parse(date);
		} catch (Exception e) {
			log.error(e);
		}
		return d;
	}

	public Date formatdatewithouttime4(String date) {
		Date d = null;
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
			d = sdf2.parse(date);
		} catch (Exception e) {
			log.error(e);
		}
		return d;
	}

	public String formatdate_ddMMMyyyy(Date date) {
		String d = null;
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
			d = sdf2.format(date);
		} catch (Exception e) {
			log.error(e);
		}
		return d;
	}

	public String ddMMMyyyy(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
			date = sdf2.format(d);
		} catch (Exception e) {
			log.error(e);
		}
		return date;
	}

	public Date formatdatewithouttime5(String date) {
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			d = sdf.parse(date);
		} catch (Exception e) {
			log.error(e);
		}
		return d;
	}

	public Date formatdatewithouttime6(String date) {
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			d = sdf.parse(date);
		} catch (Exception e) {
			log.error(e);
		}
		return d;
	}

	public String formatdatewithMMM(Date date) {
		String dates = "";
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy");
			dates = sdf2.format(date);
		} catch (Exception e) {
			log.error(e);
		}
		return dates;
	}

	public String fileDateFormat() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyssSSSSSS");
			String format = sdf.format(new Date());

			return format;
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	public Date particular_date(int count) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, count);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(cal.getTime());
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			log.error(e);
		}
		return d;
	}

	public String addDays(String oldDate, int day) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			try {
				// Setting the date to the given date
				c.setTime(sdf.parse(oldDate));
			} catch (ParseException e) {
				log.error(e);
			}
			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, day);
			// Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());
			return newDate;
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	public Date addDays(int day) {
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;
			try {
				// Setting the date to the given date
				c.setTime(new Date());
			} catch (Exception e) {
				log.error(e);
			}
			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, day);
			// Date after adding the days to the given date
			date = c.getTime();
			return date;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public Date addMinutes(int minutes) {
		try {
			Date date = null;

			Calendar calendar = Calendar.getInstance();
			// Add minutes to current date
			calendar.add(Calendar.MINUTE, minutes);

			date = calendar.getTime();

			return date;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public Date getfirstDateOfPreviousMonth() {
		try {
			Date firstDateOfPreviousMonth = null;
			Calendar aCalendar = Calendar.getInstance();
			aCalendar.add(Calendar.MONTH, -1);
			aCalendar.set(Calendar.DATE, 1);
			firstDateOfPreviousMonth = aCalendar.getTime();
			return firstDateOfPreviousMonth;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public Date getlastDateOfPreviousMonth() {
		try {
			Date lastDateOfPreviousMonth = null;
			Calendar aCalendar = Calendar.getInstance();
			aCalendar.add(Calendar.MONTH, -1);
			aCalendar.set(Calendar.DATE, 1);
			aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastDateOfPreviousMonth = aCalendar.getTime();
			return lastDateOfPreviousMonth;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public String reqPrint(Object response) {
		ObjectMapper mapper = new ObjectMapper();
		String resp = "";
		try {
			// log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
			// resp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
			resp = mapper.writeValueAsString(response);
			log.info(mapper.writeValueAsString(response));
		} catch (Exception e) {
			log.error(e);
		}
		return resp;
	}

	public Properties getwebserviceurlProperty() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("WebServiceURL.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (Exception e) {
			log.error(e);
		}
		return prop;
	}

	public Properties getapplicationProperty() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (Exception e) {
			log.error(e);
		}
		return prop;
	}

	public Properties getQuery() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("OracleQuery.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (Exception e) {
			log.error(e);
		}
		return prop;
	}
	/*
	 * public String getfileurl(MSWReq req) { try { String name = req.getQuote_no()
	 * + "Schedule"; String filename = name + ".pdf"; String filePath =
	 * servletRequest.getRealPath("pdf/" + filename); File file = new
	 * File(filePath); if (!file.exists()) { URL url = new URL(req.getFilepath());
	 * // FileUtils.copyURLToFile(url, new File(filePath)); } return filePath; }
	 * catch (Exception e) { log.error(e); } return ""; }
	 */

	public String copyFile(String from, String to) {
		String path = "";
		try {
			log.info("copyFile--> from: " + from);
			log.info("copyFile--> to: " + to);
			Path src = Paths.get(from);
			Path dest = Paths.get(to);

			boolean exist = src.toFile().exists();
			log.info("copyFile--> exist: " + exist);

			if (!exist) {
				Thread.sleep(10000);
				Path copy = Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
				path = copy.toAbsolutePath().toString();
			} else {
				Path copy = Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
				path = copy.toAbsolutePath().toString();
			}
			log.info("copyFile--> path: " + path);
			return path;
		} catch (Exception e) {
			log.error(e);
		}
		return path;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRequestData(String loginId, String inputQuery) {
		List<Map<String, Object>> mapList = null;
		try {
			Query query = em.createNativeQuery(inputQuery);
			query.setParameter(1, loginId);
			@SuppressWarnings("rawtypes")
			NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
			nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			mapList = query.getResultList();
		} catch (Exception ex) {
			log.error(ex);
		}
		return mapList;
	}

	public String getBranchBasedLoginId(String branchCode) {
		String loginid = "";
		try {
			String loginQuery = getQuery().getProperty("EXISTING_LOGINID");
			List<Map<String, Object>> mapList1 = getRequestData(branchCode, loginQuery);
			if (mapList1.size() > 0) {
				loginid = mapList1.get(0).get("LOGINID") == null ? "" : mapList1.get(0).get("LOGINID").toString();
			}
			return loginid;
		} catch (Exception ex) {
			log.error(ex);
		}
		return null;
	}

	/*
	 * public List<MarineConstantDetail> getMarineConstantDetails(String type,
	 * String productId) { List<MarineConstantDetail> data = new
	 * ArrayList<MarineConstantDetail>(); try { data = constantRepo.
	 * findByStatusAndRsacodeAndProductidOrderByMarineConstantDetailIdCategorydetailidAsc
	 * ("Y", type, Long.valueOf(productId)); return data; } catch (Exception ex) {
	 * log.error(ex); } return null; }
	 * 
	 */	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getbranchall(String loginId, String inputQuery) {
		List<Map<String, Object>> mapList = null;
		try {
			Query query = em.createNativeQuery(inputQuery);
			query.setParameter(1, loginId);
			@SuppressWarnings("rawtypes")
			NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
			nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			mapList = query.getResultList();
		} catch (Exception ex) {
			log.error(ex);
		}
		return mapList;
	}
}
