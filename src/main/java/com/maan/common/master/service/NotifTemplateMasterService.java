/*
*  Copyright (c) 2019. All right reserved
* Created on 2021-11-19 ( Date ISO 2021-11-19 - Time 13:16:48 )
* Generated by Telosys Tools Generator ( version 3.3.0 )
*/
package com.maan.common.master.service;
import java.util.List;
import com.maan.common.error.Error;

import com.maan.common.bean.NotifTemplateMaster;
import com.maan.common.master.request.NotifTemplateMasterReq;
import com.maan.common.master.response.NotifTemplateMasterRes;
import com.maan.common.master.response.sucessRes;
/**
* <h2>NotifTemplateMasterServiceimpl</h2>
*/
public interface NotifTemplateMasterService  {

NotifTemplateMaster create(NotifTemplateMaster d);
NotifTemplateMaster update(NotifTemplateMaster d);
//NotifTemplateMaster getOne(long id) ;
 List<NotifTemplateMaster> getAll();
long getTotal();
//boolean delete(long id);
sucessRes insertnotiftemplatemaster(NotifTemplateMasterReq req);
List<NotifTemplateMasterRes> getenotiftemplatemaster(NotifTemplateMasterReq req);
NotifTemplateMasterRes notiftemplatemasterid(NotifTemplateMasterReq req);
List<Error> validation(NotifTemplateMasterReq req);

}
