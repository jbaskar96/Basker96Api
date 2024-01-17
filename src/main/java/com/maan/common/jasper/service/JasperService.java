package com.maan.common.jasper.service;

import com.maan.common.jasper.request.JasperDocumentReq;
import com.maan.common.upload.response.JasperDocumentRes;

public interface JasperService {

	public JasperDocumentRes adminpolicycertificate(JasperDocumentReq req);

	public JasperDocumentRes adminpolicycertificatesubreport1(JasperDocumentReq req);

	public JasperDocumentRes commonpolicyreport(JasperDocumentReq req);

	public JasperDocumentRes debit(JasperDocumentReq req);

	public JasperDocumentRes motorreport(JasperDocumentReq req);

	public JasperDocumentRes policycertificate(JasperDocumentReq req);

	public JasperDocumentRes policycertificatenew(JasperDocumentReq req);

	public JasperDocumentRes quotation(JasperDocumentReq req);

	public JasperDocumentRes quotationsubreport1(JasperDocumentReq req);

	public JasperDocumentRes receipt(JasperDocumentReq req);

	public JasperDocumentRes schedule(JasperDocumentReq req);

	public JasperDocumentRes schedulesubreport1(JasperDocumentReq req);

}
