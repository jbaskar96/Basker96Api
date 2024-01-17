/*
*  Copyright (c) 2019. All right reserved
* Created on 2021-11-19 ( Date ISO 2021-11-19 - Time 13:16:48 )
* Generated by Telosys Tools Generator ( version 3.3.0 )
*/
package com.maan.common.master.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.maan.common.error.Error;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maan.common.bean.NotifTemplateMaster;
import com.maan.common.master.request.NotifTemplateMasterReq;
import com.maan.common.master.response.NotifTemplateMasterRes;
import com.maan.common.master.response.sucessRes;
import com.maan.common.master.service.NotifTemplateMasterService;
import com.maan.common.repository.NotifTemplateMasterRepository;
/**
* <h2>NotifTemplateMasterServiceimpl</h2>
*/
@Service
@Transactional
public class NotifTemplateMasterServiceImpl implements NotifTemplateMasterService {

@Autowired
private NotifTemplateMasterRepository repository;


private Logger log=LogManager.getLogger(NotifTemplateMasterServiceImpl.class);
/*
public NotifTemplateMasterServiceImpl(NotifTemplateMasterRepository repo) {
this.repository = repo;
}

  */
 @Override
    public NotifTemplateMaster create(NotifTemplateMaster d) {

       NotifTemplateMaster entity;

        try {
            entity = repository.save(d);

        } catch (Exception ex) {
			log.error(ex);
            return null;
        }
        return entity;
    }

    
    @Override
    public NotifTemplateMaster update(NotifTemplateMaster d) {
        NotifTemplateMaster c;

        try {
            c = repository.saveAndFlush(d);

        } catch (Exception ex) {
			log.error(ex);
            return null;
        }
        return c;
    }

/*
    @Override
    public NotifTemplateMaster getOne(long id) {
        NotifTemplateMaster t;

        try {
            t = repository.findById(id).orElse(null);

        } catch (Exception ex) {
			log.error(ex);
            return null;
        }
        return t;
    }

*/
    @Override
    public List<NotifTemplateMaster> getAll() {
        List<NotifTemplateMaster> lst;

        try {
            lst = repository.findAll();

        } catch (Exception ex) {
			log.error(ex);
            return Collections.emptyList();
        }
        return lst;
    }


    @Override
    public long getTotal() {
        long total;

        try {
            total = repository.count();
        } catch (Exception ex) {
            log.error(ex);
			return 0;
        }
        return total;
    }


	@Override
	@Transactional
	public sucessRes insertnotiftemplatemaster(NotifTemplateMasterReq req) {

			sucessRes res = new sucessRes();
	        try {
	        	
				Calendar calendar = Calendar.getInstance(); 
			 	calendar.setTime(req.getEffectiveDate());
			    calendar.set(Calendar.HOUR_OF_DAY, 0);
			    calendar.set(Calendar.MINUTE, 0);
			    calendar.set(Calendar.SECOND, 0);
			    calendar.set(Calendar.MILLISECOND, 0);
			    
			    Calendar c = Calendar.getInstance();
			    c.setTime(req.getEffectiveDate());
			    c.set(Calendar.HOUR_OF_DAY, 23);
			    c.set(Calendar.MINUTE, 59);
			    c.set(Calendar.SECOND, 59);
			    c.set(Calendar.MILLISECOND, 59);


				Date effectiveDatefrom = calendar.getTime();
				Date effectiveDateto = c.getTime();
	           	        	
	        	List<NotifTemplateMaster> opt = repository.findBySnoAndStatusAndInsIdAndEffectiveDateBetweenOrderByAmendidDesc(req.getSno(),req.getStatus(),req.getInsId(),effectiveDatefrom,effectiveDateto);
	        	
	        	if(opt.size()!=0) {
	        		
					ModelMapper modelMapper = new ModelMapper();
					NotifTemplateMaster ent = modelMapper.map(req, NotifTemplateMaster.class);
					
					ent.setEntryDate(new Date());
					ent.setEffectiveDate(req.getEffectiveDate());
					ent.setAmendid(opt.get(0).getAmendid().add(new BigDecimal(1)));
					repository.save(ent);
					
					res.setResponse("Updated SucessFully");
					
	        	}else {
	        				
					BigDecimal sno = new BigDecimal(1);
					
					List<NotifTemplateMaster> list = repository.findByInsIdOrderBySnoDesc( req.getInsId());
					if(!(list.size()==0)) {
						sno = list.get(0).getSno().add(new BigDecimal(1));
					}
	        		
					ModelMapper modelMapper = new ModelMapper();
					NotifTemplateMaster ent = modelMapper.map(req, NotifTemplateMaster.class);
					
					if(req.getSno()==null) {
						ent.setSno(sno);
					}else {
						ent.setSno(req.getSno());
					}	
					ent.setEntryDate(new Date());
					ent.setEffectiveDate(req.getEffectiveDate());
					ent.setAmendid(new BigDecimal("0"));
					repository.save(ent);
					res.setResponse("Inserted SucessFully");
	        		
	        	}
				
	        } catch (Exception ex) {
				log.error(ex);
	            res= null;
	        }
	        return res;
	    }


	@Override
	public List<NotifTemplateMasterRes> getenotiftemplatemaster(NotifTemplateMasterReq req) {
		
		List<NotifTemplateMasterRes> reslist = new ArrayList<NotifTemplateMasterRes>();
		try {

			List<NotifTemplateMaster> entList = repository.findByInsIdOrderByEntryDateDesc( req.getInsId());
			
			entList = entList.stream().filter(distinctByKey(o -> Arrays.asList(o.getInsId(),o.getStatus(),o.getSno()))).collect(Collectors.toList());

			for (NotifTemplateMaster NotifTemplateMaster : entList) {

				ModelMapper modelMapper = new ModelMapper();
				NotifTemplateMasterRes res = modelMapper.map(NotifTemplateMaster, NotifTemplateMasterRes.class);

				reslist.add(res);
			}

		} catch (Exception ex) {
			log.error(ex);
			reslist = null;
		}
		return reslist;
		
	}


	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
  		 Map<Object, Boolean> seen = new ConcurrentHashMap<>();
  		 return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	@Override
	public NotifTemplateMasterRes notiftemplatemasterid(NotifTemplateMasterReq req) {

		NotifTemplateMasterRes res = new NotifTemplateMasterRes();
		try {

			List<NotifTemplateMaster> entList = repository.findBySnoAndStatusAndInsIdOrderByEntryDateDesc(req.getSno(),req.getStatus(), req.getInsId());

			ModelMapper modelMapper = new ModelMapper();
			res = modelMapper.map(entList.get(0), NotifTemplateMasterRes.class);

		} catch (Exception ex) {
			log.error(ex);
			res = null;
		}
		return res;

	}


	@Override
	public List<Error> validation(NotifTemplateMasterReq req) {

		List<Error> errorList = new ArrayList<Error>();

		try {
			if (req.getInsId() == null || StringUtils.isBlank(req.getInsId().toString())) {
				errorList.add(new Error("01", "InsuranceId", "Please Enter InsuranceId"));
			}
			if (req.getStatus() == null || StringUtils.isBlank(req.getStatus())) {
				errorList.add(new Error("02", "Status", "Please Enter Status"));
			}
			
			
			
			//Mail
			if (req.getMailRequired() == null || StringUtils.isBlank(req.getMailRequired())) {
				errorList.add(new Error("03", "MailRequired", "Please Enter MailRequired"));
			}else if(req.getMailRequired().equals("Y")) {
				
				if (req.getMailSubject() == null || StringUtils.isBlank(req.getMailSubject())) {
					errorList.add(new Error("03", "MailSubject", "Please Enter MailSubject"));
				}
				if (req.getMailBody() == null || StringUtils.isBlank(req.getMailBody())) {
					errorList.add(new Error("03", "MailBody", "Please Enter MailBody"));
				}
				if (req.getMailRegards() == null || StringUtils.isBlank(req.getMailRegards())) {
					errorList.add(new Error("03", "MailRegards", "Please Enter MailRegards"));
				}
			}
			
			//SMS
			if (req.getSmsRequired() == null || StringUtils.isBlank(req.getSmsRequired())) {
				errorList.add(new Error("04", "SmsRequired", "Please Enter SmsRequired"));
			}else if(req.getSmsRequired().equals("Y")) {
				
				if (req.getSmsSubject() == null || StringUtils.isBlank(req.getSmsSubject())) {
					errorList.add(new Error("04", "SmsSubject", "Please Enter SmsSubject"));
				}
				if (req.getSmsBodyEn() == null || StringUtils.isBlank(req.getSmsBodyEn())) {
					errorList.add(new Error("04", "SmsBody", "Please Enter SmsBody"));
				}
				if (req.getSmsRegards() == null || StringUtils.isBlank(req.getSmsRegards())) {
					errorList.add(new Error("04", "SmsRegards", "Please Enter SmsRegards"));
				}
			}
			
			if (req.getWhatsappRequired() == null || StringUtils.isBlank(req.getWhatsappRequired())) {
				errorList.add(new Error("05", "WhatsappRequired", "Please Enter WhatsappRequired"));
			}else if(req.getWhatsappRequired().equals("Y")) {
				
				if (req.getWhatsappSubject() == null || StringUtils.isBlank(req.getWhatsappSubject())) {
					errorList.add(new Error("05", "WhatsappSubject", "Please Enter WhatsappSubject"));
				}
				if (req.getWhatsappBodyEn() == null || StringUtils.isBlank(req.getWhatsappBodyEn())) {
					errorList.add(new Error("05", "WhatsappBodyEn", "Please Enter WhatsappBodyEn"));
				}
				if (req.getWhatsappRegards() == null || StringUtils.isBlank(req.getWhatsappRegards())) {
					errorList.add(new Error("05", "WhatsappRegards", "Please Enter WhatsappRegards"));
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterday = cal.getTime();
			if (req.getEffectiveDate() == null || StringUtils.isBlank(req.getEffectiveDate().toString())) {
				errorList.add(new Error("06", "EffectiveDate", "Please Enter EffectiveDate"));
			}else if(req.getEffectiveDate().before(yesterday) ) {
				errorList.add(new Error("06", "EffectiveDate", "Please Enter Future Date as EffectiveDate"));
			} 
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return errorList;

	}

}
