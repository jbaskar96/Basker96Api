package com.maan.common.jasper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maan.common.jasper.request.JasperDocumentReq;
import com.maan.common.jasper.service.JasperService;
import com.maan.common.upload.response.JasperDocumentRes;

@RestController
@RequestMapping("/pdf")
public class JasperController {
	
	@Autowired
	private JasperService jasper;
	
	@PostMapping("/adminpolicycertificate") 
	private JasperDocumentRes adminpolicycertificate(@RequestBody JasperDocumentReq req) {
		return jasper.adminpolicycertificate(req);
	}
	
	@PostMapping("/adminpolicycertificatesubreport1") 
	private JasperDocumentRes adminpolicycertificatesubreport1(@RequestBody JasperDocumentReq req) {
		return jasper.adminpolicycertificatesubreport1(req);
	}
	
	@PostMapping("/commonpolicyreport") 
	private JasperDocumentRes commonpolicyreport(@RequestBody JasperDocumentReq req) {
		return jasper.commonpolicyreport(req);
	}
	
	@PostMapping("/debit") 
	private JasperDocumentRes debit(@RequestBody JasperDocumentReq req) {
		return jasper.debit(req);
	}
	
	@PostMapping("/motorreport") 
	private JasperDocumentRes motorreport(@RequestBody JasperDocumentReq req) {
		return jasper.motorreport(req);
	}
	
	@PostMapping("/policycertificate") 
	private JasperDocumentRes policycertificate(@RequestBody JasperDocumentReq req) {
		return jasper.policycertificate(req);
	}
	
	@PostMapping("/policycertificatenew") 
	private JasperDocumentRes policycertificatenew(@RequestBody JasperDocumentReq req) {
		return jasper.policycertificatenew(req);
	}
	
	@PostMapping("/quotation") 
	private JasperDocumentRes quotation(@RequestBody JasperDocumentReq req) {
		return jasper.quotation(req);
	}
	
	@PostMapping("/quotationsubreport1") 
	private JasperDocumentRes quotationsubreport1(@RequestBody JasperDocumentReq req) {
		return jasper.quotationsubreport1(req);
	}
	
	@PostMapping("/receipt") 
	private JasperDocumentRes receipt(@RequestBody JasperDocumentReq req) {
		return jasper.receipt(req);
	}
	
	@PostMapping("/schedule") 
	private JasperDocumentRes schedule(@RequestBody JasperDocumentReq req) {
		return jasper.schedule(req);
	}
	
	@PostMapping("/schedulesubreport1") 
	private JasperDocumentRes schedulesubreport1(@RequestBody JasperDocumentReq req) {
		return jasper.schedulesubreport1(req);
	}
	
}
