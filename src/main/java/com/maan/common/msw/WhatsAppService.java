package com.maan.common.msw;

import com.maan.common.upload.request.MSWReq;

public interface WhatsAppService {

	String sendWhatsApp_tocust(MSWReq request);

	String send_bulkmsg(String fileyn);

}
