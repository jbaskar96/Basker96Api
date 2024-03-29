/*
*  Copyright (c) 2019. All right reserved
* Created on 2021-08-20 ( Date ISO 2021-08-20 - Time 12:06:33 )
* Generated by Telosys Tools Generator ( version 3.3.0 )
*/
package com.maan.common.upload.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maan.common.bean.ClaimDocumentMaster;
import com.maan.common.upload.request.ClaimDocumentMasterReq;
import com.maan.common.upload.response.ClaimDocumentMasterRes;
import com.maan.common.upload.service.ClaimDocumentMasterService;
/**
* <h2>ClaimDocumentMasterServiceimpl</h2>
*/
@Service
@Transactional
public class ClaimDocumentMasterServiceImpl implements ClaimDocumentMasterService {

/*@Autowired
private ClaimDocumentMasterRepository repository; */


private Logger log=LogManager.getLogger(ClaimDocumentMasterServiceImpl.class);
/*
public ClaimDocumentMasterServiceImpl(ClaimDocumentMasterRepository repo) {
this.repository = repo;
}

  */
 @Override
    public ClaimDocumentMaster create(ClaimDocumentMaster d) {

       ClaimDocumentMaster entity;

        try {
          //  entity = repository.save(d);

        } catch (Exception ex) {
			log.error(ex);
            return null;
        }
        return null;
    }

    
    @Override
    public ClaimDocumentMaster update(ClaimDocumentMaster d) {
        ClaimDocumentMaster c;

        try {
        //    c = repository.saveAndFlush(d);

        } catch (Exception ex) {
			log.error(ex);
            return null;
        }
        return null;
    }

/*
    @Override
    public ClaimDocumentMaster getOne(long id) {
        ClaimDocumentMaster t;

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
    public List<ClaimDocumentMaster> getAll() {
        List<ClaimDocumentMaster> lst;

        try {
        //    lst = repository.findAll();

        } catch (Exception ex) {
			log.error(ex);
            return Collections.emptyList();
        }
        return null;
    }


    @Override
    public long getTotal() {
        long total;

        try {
      //      total = repository.count();
        } catch (Exception ex) {
            log.error(ex);
			return 0;
        }
        return 0;
    }


	@Override
	public List<ClaimDocumentMasterRes> getClaimDocumentMaster(ClaimDocumentMasterReq req) {
        List<ClaimDocumentMaster> lst = new ArrayList<ClaimDocumentMaster>();
        List<ClaimDocumentMasterRes> resList = new ArrayList<ClaimDocumentMasterRes>();
        try {
        /*    lst = repository.findByStatusAndCompanyId(req.getStatus(),BigDecimal.valueOf(Long.valueOf(req.getCompanyId())));
			for(int i=0;i<lst.size();i++) {
	            ModelMapper modelMapper = new ModelMapper();
	            ClaimDocumentMasterRes res = new ClaimDocumentMasterRes();
				res = modelMapper.map(lst.get(i), ClaimDocumentMasterRes.class);
				res.setDocumentId(lst.get(i).getDocumentId().toString());
				resList.add(res);
			} */
        } catch (Exception ex) {
			log.error(ex);
            return Collections.emptyList();
        }
        return resList;
    }

/*
    @Override
    public boolean delete(long id) {
        try {
            repository.deleteById(id);
            return true;

        } catch (Exception ex) {
			log.error(ex);
            return false;
        }
    }

 */

}
