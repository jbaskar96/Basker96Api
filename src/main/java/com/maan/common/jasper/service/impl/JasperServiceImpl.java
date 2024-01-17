package com.maan.common.jasper.service.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.maan.common.bean.HomePositionMaster;
import com.maan.common.bean.LoginMaster;
import com.maan.common.bean.MotorDataDetail;
import com.maan.common.jasper.request.JasperDocumentReq;
import com.maan.common.jasper.service.JasperService;
import com.maan.common.repository.HomePositionMasterRepository;
import com.maan.common.repository.LoginMasterRepository;
import com.maan.common.repository.MotorDataDetailRepository;
import com.maan.common.upload.response.JasperDocumentRes;
import com.maan.common.upload.service.thread.GetFileFromPath;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class JasperServiceImpl implements JasperService{


	@Autowired
	private JasperConfiguration config;
	@Autowired
	private LoginMasterRepository loginRepo;
	@Autowired
	private HomePositionMasterRepository homeRepo;
	@Autowired
	private MotorDataDetailRepository motorRepo;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	private JasperDocumentRes getJasperPdfFile(String jasperPath, String filePath,Map<String, Object> input) {
		JasperDocumentRes res = new JasperDocumentRes();
		Connection connection=null;
		try {
			connection=config.getDataSourceForJasper().getConnection();
			InputStream inputStream = this.getClass().getResourceAsStream(jasperPath);
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,input, connection);

			System.out.println("filePath name is ====> "+filePath);
			JasperExportManager.exportReportToPdfFile(jasperPrint,filePath);
			
			GetFileFromPath path=new GetFileFromPath(filePath);
			res.setPdfoutfilepath(path.call().getImgUrl());
			
		}catch(Exception e) {
			e.printStackTrace();		
		}finally {
			if(connection!=null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return res;
	}

	 

	public JasperDocumentRes adminpolicycertificate(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("PvVechicle", req.getPvvechicle());
			input.put("Pvproduct", req.getPvproduct());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/AdminPolicyCertificate.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes adminpolicycertificatesubreport1(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("PvVechicle", req.getPvvechicle());
			input.put("Pvproduct", req.getPvproduct());
			input.put("Pvusername", req.getPvusername());
			input.put("Status", req.getStatus());
			input.put("pvLoginId", req.getPvLoginId());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/AdminPolicyCertificate_subreport1.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes commonpolicyreport(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));
			
			input.put("Pvtype", req.getPvtype());
			input.put("Pvquote", req.getQuoteNo());
			input.put("pvstartdate", req.getPvstartdate());
			input.put("pvenddate", req.getPvenddate());
			input.put("pvstatus", req.getStatus());
			input.put("pvcover", req.getPvcover());
			input.put("pvinput1", req.getPvinput1());
			input.put("pvinput2", req.getPvinput2());
			input.put("pvuser", req.getPvuser());
			input.put("PVPRODUCT", req.getPvproduct());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/commonPolicyReport.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes debit(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			//input.put("imagePath", req.getImagePath());
			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("Pvproduct", req.getPvproduct());
			input.put("Status", req.getStatus());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + "Debit.pdf";
				res = getJasperPdfFile("/report/jasper/Debit.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes motorreport(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));
			
			input.put("StartDate", req.getPvstartdate());
			input.put("EndDate", req.getPvenddate());
			input.put("BranchCode", req.getPvbranch());
			input.put("Pvpolicytype", req.getPvtype());



			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/MotorReport.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes policycertificate(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("PvVechicle", req.getPvvechicle());
			input.put("Pvproduct", req.getPvproduct());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/PolicyCertificate.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes policycertificatenew(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";String fileLoc="";
		try {
			HomePositionMaster home=homeRepo.findByQuoteNo(new BigDecimal(req.getQuoteNo()));
			MotorDataDetail mdata=motorRepo.findByApplicationNoAndVehicleId(home.getApplicationNo(), new BigDecimal(req.getPvvechicle()));
			String polNo=home.getPolicyNo()==null?"":home.getPolicyNo();
			String vehRegNo=mdata.getRegistrationNo()==null?"":mdata.getRegistrationNo().toString();
			String issueDate=home.getInceptionDate()==null?"":sdf.format(home.getInceptionDate());
			String expDate=home.getExpiryDate()==null?"":(sdf1.format(home.getExpiryDate())+" 23:59:00 ");
			String certNo=mdata.getCertificateNo()==null?"":mdata.getCertificateNo();
			String tag="MGen ZM";
			String msg=polNo+"\r\n"+vehRegNo+"\r\n"+issueDate+"\r\n"+expDate+"\r\n"+certNo+"\r\n"+tag;
			try {
				fileLoc=config.getQRCodePath().replaceAll("%20", " ")+req.getQuoteNo()+".JPG";
				generateQRCode(msg, 200, 200, fileLoc);
				File qrFile = new File(fileLoc);
				boolean exists = qrFile.exists();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			LoginMaster login=loginRepo.findByloginId(home.getLoginId());
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));
			input.put("Pvusername",login.getUsername()==null?"":login.getUsername());
			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("PvVechicle", req.getPvvechicle());
			input.put("Pvproduct", req.getPvproduct());
			input.put("QRPath", config.getQRCodePath().replaceAll("%20", " "));
			
			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + "Certifcate.pdf";
				res = getJasperPdfFile("/report/jasper/PolicyCertificateNew.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	public void generateQRCode(String text, int width, int height, String filePath) {
		try {
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
			        .lastIndexOf('.') + 1), new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		}


	@Override
	public JasperDocumentRes quotation(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("Pvproduct", req.getPvproduct());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/Quotation.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes quotationsubreport1(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("Pvproduct", req.getPvproduct());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/Quotation_subreport1.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes receipt(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("Pvproduct", req.getPvproduct());
			//input.put("imagePath", req.getImagePath());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + "Receipt.pdf";
				res = getJasperPdfFile("/report/jasper/Receipt.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public JasperDocumentRes schedule(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			String jasperpath=config.getJasperPath().replaceAll("%20", " ");
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("Pvproduct", req.getPvproduct());
			input.put("SUBREPORT_DIR", jasperpath);
			String jasperPath =  jasperpath + "/Schedule.jasper";
			List<String> subJasperPath = new ArrayList<String>();
			subJasperPath.add(jasperpath + "/Schedule_subreport1.jasper");
			//subJasperPath.add(jasperpath + "/Deductible.jasper");
			//subJasperPath.add(jasperpath + "/VehicleDetails.jasper");
			JasperPrint jasperPrint = fillReport(jasperPath,subJasperPath, input);
			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + "Schedule.pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint,getPdfOutFilePath);
				GetFileFromPath path=new GetFileFromPath(getPdfOutFilePath);
				res.setPdfoutfilepath(path.call().getImgUrl());
				//res = getJasperPdfFile("/report/jasper/Schedule.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	private JasperPrint fillReport(String jasperPath, List<String> subJasperPath, Map<String, Object> input) throws Exception {
		compileReportToFile(jasperPath);
		for(int i=0 ; i< subJasperPath.size() ; i++) {
			compileReportToFile(subJasperPath.get(i));
		}
		Connection connection=config.getDataSourceForJasper().getConnection();
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath,input, connection);
		return jasperPrint;
	}
	private void compileReportToFile(String jasperPath) throws Exception {
		File jasperFile = new File(jasperPath);
		if(!jasperFile.exists()){
			String path=jasperFile.getAbsolutePath().replace(".jasper", ".jrxml");
			String temp = JasperCompileManager.compileReportToFile(path);
		}
	}


	@Override
	public JasperDocumentRes schedulesubreport1(JasperDocumentReq req) {
		JasperDocumentRes res = new JasperDocumentRes();
		String getPdfOutFilePath = "";
		try {
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("imagePath", config.getImagePath().replaceAll("%20", " "));

			input.put("Quoteno", req.getQuoteNo());
			input.put("Pvbranch", req.getPvbranch());
			input.put("Pvproduct", req.getPvproduct());

			if (null != input && input.size() > 0) {

				String directoryname = req.getQuoteNo().replaceAll("[\\/:*?\"<>|]*", "");
				String filePath = config.getCommonPath() + "pdf/" + directoryname;

				File theDir = new File(filePath);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}

				getPdfOutFilePath = filePath + "/" + req.getQuoteNo().replace("/", "-") + ".pdf";
				res = getJasperPdfFile("/report/jasper/Schedule_subreport1.jrxml", getPdfOutFilePath, input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
