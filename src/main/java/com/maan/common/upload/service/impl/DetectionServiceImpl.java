package com.maan.common.upload.service.impl;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.cloud.vision.v1.LocationInfo;
import com.google.protobuf.ByteString;
import com.google.type.LatLng;
import  com.maan.common.error.Error;
import com.maan.common.error.ErrorRes;
import com.maan.common.error.Errors;
import com.maan.common.upload.request.ConditionReq;
import com.maan.common.upload.request.DocumentUploadDetailsReq;
import com.maan.common.upload.request.DocumentUploadDetailsReqRes;
import com.maan.common.upload.request.ImageDocRes;
import com.maan.common.upload.request.ImageRequest;
import com.maan.common.upload.response.FaceAnnotations;
import com.maan.common.upload.response.GoogleApiAnnotationResponse;
import com.maan.common.upload.response.GoogleApiResponse;
import com.maan.common.upload.response.LabelAnnotationRes;
import com.maan.common.upload.response.LandmarkAnnotations;
import com.maan.common.upload.response.LatitudeLongitude;
import com.maan.common.upload.response.Locations;
import com.maan.common.upload.response.TextAnnotations;
import com.maan.common.upload.service.ConditionSeparator;
import com.maan.common.upload.service.DetectionService;
@Service
public class DetectionServiceImpl implements DetectionService {

	@Autowired
	private ImageAnnotatorClient imageAnnotatorClient;
/*	@Autowired
	private GoogleapiDetailsRepo gapidetRepo; */
	@Autowired
	private CommonService commomSer;
/*	@Autowired
	private DocumentUploadDetailsRepository docupldetRepo; */
	 
	@Autowired
	private ConditionSeparator condSep;
	
	private Logger log=LogManager.getLogger(getClass());

	@Override
	public GoogleApiResponse getimageres(ImageRequest request) {
		GoogleApiResponse response = new GoogleApiResponse();
		List<Error> errList = new ArrayList<>();
		try {
			GoogleApiAnnotationResponse gapiannores = new GoogleApiAnnotationResponse();

			List<GoogleApiAnnotationResponse> gapiannoreslist = new ArrayList<>();
			List<TextAnnotations> textannolist = new ArrayList<>();
			List<LabelAnnotationRes> labelannolist = new ArrayList<>();
			List<LandmarkAnnotations> landmarkannolist = new ArrayList<>();
			List<Errors> errorlist = new ArrayList<>();
			List<Locations> locationlist = new ArrayList<>();
			List<FaceAnnotations> faceannotation = new ArrayList<>();
			
			List<AnnotateImageResponse> responsesList = gapires(request);
			
			String desc = "", locale = "", imgmid = "", imgdescription = "", imgscore = "", imgtopicality = "",
					descriptiontext = "";
			Float score = 0F, topicality = 0F;
			
			for (AnnotateImageResponse res : responsesList) {
		        if (res.hasError()) {
		          //log.info("Error: %s\n", res.getError().getMessage());		          	          
		          Errors error=Errors.builder()
		        		  .message(res.getError().getMessage())
		        		  .build();
		          errorlist.add(error);
		          gapiannores.setErrors(errorlist);
		        }
		        for (int i=0;i<res.getLabelAnnotationsList().size();i++) {
		        	EntityAnnotation annotation=res.getLabelAnnotationsList().get(i);
		        	//annotation.getAllFields().forEach((k, v) -> log.info("%s : %s\n", k, v.toString()));
		          
		        	imgdescription=annotation.getDescription();
		        	imgmid=annotation.getMid();
		        	score=annotation.getScore();
		        	imgscore=score.toString();
		        	topicality=annotation.getTopicality();
		        	imgtopicality=topicality.toString();
		        	
		        	LabelAnnotationRes labelanno=LabelAnnotationRes.builder()
		        			.imgdescription(imgdescription)
		        			.imgmid(imgmid)
		        			.imgscore(imgscore)
		        			.imgtopicality(imgtopicality)
		        			.build();
		        	labelannolist.add(labelanno);
		        	gapiannores.setLabelResponse(labelannolist);	        	
		        }	
		        for(int i=0;i<res.getTextAnnotationsList().size();i++) {
		        	EntityAnnotation annotation=res.getTextAnnotationsList().get(i);	        	
			        //annotation1.getAllFields().forEach((k, v) -> log.info("%s : %s\n", k, v.toString()));	
		        		        	
			        desc=annotation.getDescription();
			        locale=annotation.getLocale();		       	        
			         		        
			        TextAnnotations textanno=TextAnnotations.builder()
			          //.boundingPolyResponse(bp)
			          .descriptiontext(desc)
			          .localelang(locale)
			          .build();
			        textannolist.add(textanno);	
			        gapiannores.setTextResponse(textannolist);		        		    		        
		        }
		       for(int i=0;i<res.getLandmarkAnnotationsList().size();i++) {
		        	EntityAnnotation annotation=res.getLandmarkAnnotations(i);
		        	
		        	descriptiontext=annotation.getDescription();
		        	imgmid=annotation.getMid();
		        	score=annotation.getScore();
		        	imgscore=score.toString();
		        	
		        	List<LocationInfo> locationsList = annotation.getLocationsList();
		        	for(int j=0;j<locationsList.size();j++) {	        		
		        		LatLng latLng = locationsList.get(j).getLatLng();
		        		LatitudeLongitude latlong=LatitudeLongitude.builder()
		        				.latitudeRes(latLng.getLatitude())
		        				.longitudeRes(latLng.getLongitude())
		        				.build();
		        		Locations location=Locations.builder()
			        			.latlongResponse(latlong)
			        			.build();
		        		locationlist.add(location);
		        	}
		        	LandmarkAnnotations land=LandmarkAnnotations.builder()
		        			.descriptiontext(descriptiontext)
		        			.imgmid(imgmid)
		        			.imgscore(imgscore)
		        			.locationRes(locationlist)
		        			.build();
		        	landmarkannolist.add(land);
		        	gapiannores.setLandmarkResponse(landmarkannolist);	        	
		       }
		       for(int i=0;i<res.getFaceAnnotationsCount();i++) {
		    	   FaceAnnotation annotation=res.getFaceAnnotations(i);
		    	   FaceAnnotations faceanno=FaceAnnotations.builder()
		    			   .angerLikelihooddesc(annotation.getAngerLikelihood().name())
		    			   .blurredLikelihooddesc(annotation.getBlurredLikelihood().name())
		    			   .headwearLikelihooddesc(annotation.getHeadwearLikelihood().name())
		    			   .joyLikelihooddesc(annotation.getJoyLikelihood().name())
		    			   .sorrowLikelihooddesc(annotation.getSorrowLikelihood().name())
		    			   .surpriseLikelihooddesc(annotation.getSurpriseLikelihood().name())
		    			   .underExposedLikelihooddesc(annotation.getUnderExposedLikelihood().name())
		    			   .build();
		    	   faceannotation.add(faceanno);	    	   
		    	   gapiannores.setFaceannotation(faceannotation);
		       }
			}
			gapiannoreslist.add(gapiannores);
			response.setImageresponse(gapiannoreslist);	

		} catch (Exception e) {
			if(imageAnnotatorClient!=null)
				imageAnnotatorClient.shutdownNow();
			
			errList.add(new Error(e.getLocalizedMessage(),"Document", "101"));
			log.error(e);
		}
		response.setErrors(errList);
		return response;
	}

	public List<AnnotateImageResponse> gapires(ImageRequest request) throws Exception {
		//try {
			String filepath = request.getFilePath();
			List<AnnotateImageRequest> annolist = new ArrayList<>();

			Path paths = Paths.get(filepath);

			boolean exist = paths.toFile().exists();
			log.info("gapires--> exist: " + exist);
			if (!exist) {
				Thread.sleep(2000);
				paths = Paths.get(filepath);
			}

			byte[] data = Files.readAllBytes(paths);
			//byte[] data = request.getBytearr();
			ByteString imgBytes = ByteString.copyFrom(data);
			
			Image img = Image.newBuilder()
					.setContent(imgBytes)
					.build();
			
			Feature feature = Feature.newBuilder()
					.setType(Type.LABEL_DETECTION)
					.setMaxResults(10)
					.build();
			Feature feature1 = Feature.newBuilder()
					.setType(Type.DOCUMENT_TEXT_DETECTION)
					.build();
			Feature feature2 = Feature.newBuilder()
					.setType(Type.IMAGE_PROPERTIES)
					.setMaxResults(10)
					.build();
			Feature feature3 = Feature.newBuilder()
					.setType(Type.TEXT_DETECTION)
					.build();
			Feature feature4 = Feature.newBuilder()
					.setType(Type.LOGO_DETECTION)
					.setMaxResults(50)
					.build();
			Feature feature5 = Feature.newBuilder()
					.setType(Type.WEB_DETECTION)
					.setMaxResults(50)
					.build();
			Feature feature6 = Feature.newBuilder()
					.setType(Type.LANDMARK_DETECTION)
					.setMaxResults(50)
					.build();
			Feature feature7 = Feature.newBuilder()
					.setType(Type.FACE_DETECTION)
					.setMaxResults(50)
					.build();

			AnnotateImageRequest annotrequest = AnnotateImageRequest.newBuilder()
					.addFeatures(0, feature)
					.addFeatures(1, feature1)
					.addFeatures(2, feature2)
					.addFeatures(3, feature3)
					.addFeatures(4, feature4)
					.addFeatures(5, feature5)
					.addFeatures(6, feature6)
					.addFeatures(7, feature7)
					.setImage(img)
					.build();
			annolist.add(annotrequest);

			//log.info("annolist--> "+annolist);

			String path ="";
			path=this.getClass().getClassLoader().getResource("").getPath()+"/report/account/serviceaccount.json";
			path=path.replaceAll("%20", " ");
			log.info("gapires--> path: "+path);
			Credentials myCredentials = ServiceAccountCredentials.fromStream(new FileInputStream(path));

			ImageAnnotatorSettings imageAnnotatorSettings = ImageAnnotatorSettings.newBuilder()
					.setCredentialsProvider(FixedCredentialsProvider.create(myCredentials)).build();

			imageAnnotatorClient = ImageAnnotatorClient.create(imageAnnotatorSettings);
			
			List<AnnotateImageResponse> responsesList = imageAnnotatorClient.batchAnnotateImages(annolist)
					.getResponsesList();
			//log.info("responsesList--> "+responsesList);
			
			imageAnnotatorClient.shutdownNow();
			
			return responsesList;
		//} catch (Exception e) {
			/*if(imageAnnotatorClient!=null)
				imageAnnotatorClient.shutdownNow();*/
			//log.error(e);
		//}
		//return null;
	}
	
	@Override
	public ErrorRes insimagedetails(DocumentUploadDetailsReqRes request) {
		ErrorRes errResponse = new ErrorRes();
		
		String docid = "", apicheck = "", status = "";
		String quoteno = StringUtils.isBlank(request.getQuote_no()) ? "" : request.getQuote_no();

		List<DocumentUploadDetailsReq> doclist = request.getDocumentupldet();

		ImageRequest imgreq = new ImageRequest();
		GoogleApiResponse response = new GoogleApiResponse();

		List<Error> errList = new ArrayList<>();

		String value = "", insyn="Y";
		
		try {
		/*	for (int i = 0; i < doclist.size(); i++) {
				docid = StringUtils.isBlank(doclist.get(i).getDoc_type_id()) ? "0" : doclist.get(i).getDoc_type_id();
				String productid = StringUtils.isBlank(doclist.get(i).getProduct_id()) ? request.getProduct_id()
						: doclist.get(i).getProduct_id();
				
				log.info("insimagedetails--> docid: " + docid + " productid: " + productid);
				
				apicheck = docupldetRepo.getapicheck(docid, productid,"Google API");
				apicheck = StringUtils.isBlank(apicheck) ? "N" : apicheck;
				
				if (apicheck.equals("Y")) {
					String detectperc = "";
					String sno="";

					String filepath = doclist.get(i).getFile_path_name();
					filepath = StringUtils.isBlank(filepath) ? "" : filepath;
					log.info("insimagedetails--> filepath: " + filepath);
					
					filepath = filepath.replace("\\", "/");
					log.info("insimagedetails--> replacedfilepath: " + filepath);
					imgreq.setFilePath(filepath);
					//imgreq.setFilePath("C:\\Users\\New\\Pictures\\pic\\151.jpg");
					
					response = getimageres(imgreq);
					
					log.info("insimagedetails--> getimageres: ");
					commomSer.reqPrint(response);
					
					log.info("insimagedetails--> response.getErrors(): "+response.getErrors().size());
					
					if (response != null && response.getErrors().size()<=0) {
						String agencycode="",aryn="N",locale="";
						
						List<TextAnnotations> textResponse = response.getImageresponse().get(0).getTextResponse();
						locale = StringUtils.isBlank(textResponse.get(0).getLocalelang()) ? "": textResponse.get(0).getLocalelang();

						aryn = locale.equalsIgnoreCase("ar") ? "Y" : "N";
						
					/*	if (productid.equals("65")) {
							MotorDataDetail mdd = mddRepo.findByMotDataDetPK_Quoteno(Long.valueOf(quoteno));

							agencycode = StringUtils.isBlank(mdd.getAgency_code()) ? "" : mdd.getAgency_code();
						}else if(productid.equals("35")) {
							agencycode=mddRepo.get_35AgencyCode(quoteno);
						}else*//* {
							agencycode="88888";
						}

						agencycode = StringUtils.isBlank(agencycode) ? "" : agencycode;

						Map<String, Object> map = gapidetRepo.get_detectDet(docid, productid, agencycode, aryn);

						if (map.size() > 0) {
							ImageDocRes notValidWords_Res = detect_NotValidWords(map, response);

							errList = notValidWords_Res.getErrors();
							log.info("insimagedetails--> notValidWords_Res: ");
							commomSer.reqPrint(notValidWords_Res);

							if (errList.size() <= 0) {
								ImageDocRes validWords = detect_Validwords(map, response);
								if (!productid.equals("33")) {
								errList = validWords.getErrors();
								log.info("insimagedetails--> validWords: ");
								commomSer.reqPrint(validWords);
								}
								if (errList.size() <= 0) {
									ImageDocRes param_check = detect_params(map, response, doclist.get(i));
									log.info("insimagedetails--> param_check: ");
									commomSer.reqPrint(param_check);

									value = param_check.getDetectedValue();
									detectperc = param_check.getDetectperc();
									insyn = param_check.getInsyn();
									errList = param_check.getErrors();
								}
							}

							sno = savegoogleapidet(response, request, status, value);
							log.info("insimagedetails--> sno: " + sno);
						}
						
						errResponse.setInsyn(insyn);
						errResponse.setDetected_barcode(value);
						errResponse.setGoogle_visionsno(sno);
						errResponse.setErrors(errList);
						errResponse.setDetectperc(detectperc);
						errResponse.setDetectedValue(value);

					}else if(response != null && response.getErrors().size()>0) {
						sno = savegoogleapidet(response, request, status, value);
						log.info("insimagedetails--> sno: " + sno);
						
						errResponse.setInsyn("Y");
						errResponse.setDetected_barcode(value);
						errResponse.setGoogle_visionsno(sno);
						errResponse.setErrors(response.getErrors());
						errResponse.setDetectperc(detectperc);
						errResponse.setDetectedValue(value);
					}
				}
			} */
		} catch (Exception e) {
			log.error(e);
		}
		return errResponse;
	}

	private ImageDocRes detect_NotValidWords(Map<String, Object> map, GoogleApiResponse resp) {
		ImageDocRes response = new ImageDocRes();
		try {
			List<Error> errors = new ArrayList<>();
			List<TextAnnotations> textResp = resp.getImageresponse().get(0).getTextResponse();

			String textdesc = StringUtils.isBlank(textResp.get(0).getDescriptiontext()) ? ""
					: textResp.get(0).getDescriptiontext().trim();
			String detect_textdesc=textdesc;
			//String locale = StringUtils.isBlank(textResp.get(0).getLocalelang()) ? "" : textResp.get(0).getLocalelang();

			textdesc = textdesc.replaceAll("\\s", "");
			textdesc = textdesc.replaceAll("\n", "");

			String notValidWords = map.get("NOT_VALID_WORDS") == null ? "" : map.get("NOT_VALID_WORDS").toString();

			if (StringUtils.isNotBlank(notValidWords) && StringUtils.isNotBlank(textdesc)) {

				List<ConditionReq> condList = condSep.get_condition2(notValidWords);

				if (condList.size() == 1) {
					ConditionReq cond0 = condList.get(0);

					// String connector = StringUtils.isBlank(cond0.getConnector()) ? "" :
					// cond0.getConnector().trim();
					String detect_value0 = StringUtils.isBlank(cond0.getField()) ? "" : cond0.getField().trim();
					String value0 = StringUtils.isBlank(cond0.getField()) ? ""
							: cond0.getField().trim().replaceAll("\\s", "").replaceAll("\n", "");

					if (textdesc.toLowerCase().contains(value0.toLowerCase())) {
						errors.add(new Error(
								"Not a Valid Image. Detected Value is " + detect_textdesc + ". Contains Invalid Text " + detect_value0,
								"Document", "101"));
					}
				} else {
					for (int i = 1; i < condList.size(); i++) {
						ConditionReq cond0 = condList.get(i - 1);
						ConditionReq cond1 = condList.get(i);

						String connector = StringUtils.isBlank(cond1.getConnector()) ? "" : cond1.getConnector().trim();

						String detect_value0 = StringUtils.isBlank(cond0.getField()) ? "" : cond0.getField().trim();
						String detect_value1 = StringUtils.isBlank(cond1.getField()) ? "" : cond1.getField().trim();
						
						String value0 = StringUtils.isBlank(cond0.getField()) ? ""
								: cond0.getField().trim().replaceAll("\\s", "").replaceAll("\n", "");
						String value1 = StringUtils.isBlank(cond1.getField()) ? ""
								: cond1.getField().trim().replaceAll("\\s", "").replaceAll("\n", "");

						if (connector.equals("||")) {
							if (textdesc.toLowerCase().contains(value0.toLowerCase())
									|| textdesc.toLowerCase().contains(value1.toLowerCase())) {
								errors.add(new Error("Not a Valid Image. Detected Value is " + detect_textdesc
										+ ". Contains Invalid Text " + detect_value0 + " or " + detect_value1, "Document", "101"));
							}
						} else if (connector.equals("&&")) {
							if (textdesc.toLowerCase().contains(value0.toLowerCase())
									&& textdesc.toLowerCase().contains(value1.toLowerCase())) {
								errors.add(new Error("Not a Valid Image. Detected Value is " + detect_textdesc
										+ ". Contains Invalid Text " + detect_value0 + " and " + detect_value1, "Document", "101"));
							}
						}
					}
				}
			}

			response.setErrors(errors);
		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}
	
	private ImageDocRes detect_Validwords(Map<String, Object> map, GoogleApiResponse resp) {
		ImageDocRes response = new ImageDocRes();
		try {
			List<Error> errors = new ArrayList<>();
			List<TextAnnotations> textResp = resp.getImageresponse().get(0).getTextResponse();

			String textdesc = StringUtils.isBlank(textResp.get(0).getDescriptiontext()) ? ""
					: textResp.get(0).getDescriptiontext().trim();
			String detect_textdesc = textdesc;
			// String locale = StringUtils.isBlank(textResp.get(0).getLocalelang()) ? "" :
			// textResp.get(0).getLocalelang();

			textdesc = textdesc.replaceAll("\\s", "");
			textdesc = textdesc.replaceAll("\n", "");

			String validWords = map.get("VALID_WORDS") == null ? "" : map.get("VALID_WORDS").toString();
			//String error_params = "";

			if (StringUtils.isNotBlank(validWords) && StringUtils.isNotBlank(textdesc)) {

				List<ConditionReq> condList = condSep.get_condition2(validWords);

				int size = condList.size();

				if (size == 1) {
					ConditionReq cond0 = condList.get(0);

					// String connector = StringUtils.isBlank(cond0.getConnector()) ? "" :
					// cond0.getConnector().trim();

					String detect_value0 = StringUtils.isBlank(cond0.getField()) ? "" : cond0.getField().trim();

					String value0 = StringUtils.isBlank(cond0.getField()) ? ""
							: cond0.getField().trim().replaceAll("\\s", "").replaceAll("\n", "");

					if (!textdesc.toLowerCase().contains(value0.toLowerCase())) {
						errors.add(new Error("Not a Valid Image. Detected Value is " + detect_textdesc
								+ ". Doesn't Contain Valid Text " + detect_value0, "Document", "101"));
					}
				} else {
					for (int i = 1; i < size; i++) {
						ConditionReq cond0 = condList.get(i - 1);
						ConditionReq cond1 = condList.get(i);

						String connector = StringUtils.isBlank(cond1.getConnector()) ? "" : cond1.getConnector().trim();

						String detect_value0 = StringUtils.isBlank(cond0.getField()) ? "" : cond0.getField().trim();
						String detect_value1 = StringUtils.isBlank(cond1.getField()) ? "" : cond1.getField().trim();

						String value0 = StringUtils.isBlank(cond0.getField()) ? ""
								: cond0.getField().trim().replaceAll("\\s", "").replaceAll("\n", "");
						String value1 = StringUtils.isBlank(cond1.getField()) ? ""
								: cond1.getField().trim().replaceAll("\\s", "").replaceAll("\n", "");

						if (connector.equals("||")) {
							if (!(textdesc.toLowerCase().contains(value0.toLowerCase())
									|| textdesc.toLowerCase().contains(value1.toLowerCase()))) {

								errors.add(new Error("Not a Valid Image. Detected Value is " + detect_textdesc
										+ ". Doesn't Contains Valid Text " + detect_value0 + " or " + detect_value1, "Document", "101"));
								
								/*if (!textdesc.toLowerCase().contains(value0.toLowerCase())) {
									error_params = error_params.replaceAll(detect_value0, "");
									error_params = error_params + "," + detect_value0;
								}
								
								if (!textdesc.toLowerCase().contains(value1.toLowerCase())) {
									error_params = error_params.replaceAll(detect_value1, "");
									error_params = error_params + "," + detect_value1;
								}*/
							}
						} else if (connector.equals("&&")) {
							if (!(textdesc.toLowerCase().contains(value0.toLowerCase())
									&& textdesc.toLowerCase().contains(value1.toLowerCase()))) {
								
								errors.add(new Error("Not a Valid Image. Detected Value is " + detect_textdesc
										+ ". Doesn't Contains Valid Text " + detect_value0 + " and " + detect_value1, "Document", "101"));
								
								/*if (!textdesc.toLowerCase().contains(value0.toLowerCase())) {
									error_params = error_params.replaceAll(detect_value0, "");
									error_params = error_params + "," + detect_value0;
								}
								
								if (!textdesc.toLowerCase().contains(value1.toLowerCase())) {
									error_params = error_params.replaceAll(detect_value1, "");
									error_params = error_params + "," + detect_value1;
								}*/
							}
						}
					}

					/*error_params = StringUtils.isBlank(error_params) ? ""
							: error_params.replaceAll(",,", ",");

					if (StringUtils.isNotBlank(error_params)) {
						errors.add(new Error("Not a Valid Image. Detected Value is " + detect_textdesc
								+ ". Doesn't Contains Valid Text " + error_params, "Document", "101"));
					}*/
				}
			}

			response.setErrors(errors);
		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}
	
	private ImageDocRes detect_params(Map<String, Object> map, GoogleApiResponse resp, DocumentUploadDetailsReq req) {
		ImageDocRes response = new ImageDocRes();
		try {
			List<Error> errors = new ArrayList<>();
			DecimalFormat deciformat = new DecimalFormat("0.000");

			String param = StringUtils.isBlank(req.getParam()) ? "" : req.getParam().trim();
			String docid = StringUtils.isBlank(req.getDoc_type_id()) ? "" : req.getDoc_type_id();

			log.info("detect_params--> param: " + param + " docid: " + docid);

			List<TextAnnotations> textResp = resp.getImageresponse().get(0).getTextResponse();

			String textdesc = StringUtils.isBlank(textResp.get(0).getDescriptiontext()) ? ""
					: textResp.get(0).getDescriptiontext().trim();
			String detect_textdesc=textdesc;
			String res = "";
			
		//	String docdesc = gapidetRepo.getdocdesc(docid);
			
			Double detper = 0D;
			
			boolean param_check=false;

			String detect_value = map.get("PARAM_DETECT") == null ? "" : map.get("PARAM_DETECT").toString();
			
			if (StringUtils.isNotBlank(param) && StringUtils.isNotBlank(detect_value)
					&& StringUtils.isNotBlank(textdesc)) {

				List<ImageDocRes> detect_paramlist=new ArrayList<>();
				
				List<ConditionReq> condList = condSep.get_condition2(detect_value);
				
				String[] split = param.split("~");

				for (int i = 0; i < condList.size(); i++) {
					ConditionReq cond0 = condList.get(i);

					String connector = StringUtils.isBlank(cond0.getConnector()) ? "" : cond0.getConnector().trim();
					String detect_column0 = StringUtils.isBlank(cond0.getField()) ? "" : cond0.getField().trim();
					
					for (int j = 0; j < split.length; j++) {
						String split_value = StringUtils.isBlank(split[j]) ? "" : split[j].trim();

						String[] split2 = split_value.split("=");

						String temp_param = StringUtils.isBlank(split2[0]) ? "" : split2[0].trim();

						if (temp_param.equalsIgnoreCase(detect_column0)) {
							String temp_paramValue = split2[1];
							log.info("detect_params--> temp_paramValue: " + temp_paramValue);
							ImageDocRes detect_param = detect_param(map, resp, temp_paramValue,req);
							
							detect_param.setConnector(connector);

							detect_paramlist.add(detect_param);
							
							res = res + "~" + detect_param.getDetectedValue();
							
							Double temp_detper = StringUtils.isBlank(detect_param.getDetectperc()) ? 0D
									: Double.valueOf(detect_param.getDetectperc());

							detper = detper + temp_detper;

							break;
						}
					}
				}
				
				res = StringUtils.isBlank(res) ? "" : res.substring(1, res.length());
				
				int size = detect_paramlist.size();

				detper = detper / size;
				
				if (size == 1) {
					ImageDocRes img_doc0 = detect_paramlist.get(0);

					boolean paramCheck0 = img_doc0.isParam_check();

					if (!paramCheck0) {
						errors.add(new Error(
								/*"Current Detection from Image DOES NOT MATCH " + docdesc + " Document Validations For "
										+ param + ". Detected Value is " + detect_textdesc.trim(),
								"Document", "101" */));

						param_check = false;
					}
				} else {
					for (int i = 1; i < size; i++) {
						ImageDocRes img_doc0 = detect_paramlist.get(i - 1);
						ImageDocRes img_doc1 = detect_paramlist.get(i);

						String connector = StringUtils.isBlank(img_doc1.getConnector()) ? ""
								: img_doc1.getConnector().trim();

						boolean paramCheck0 = img_doc0.isParam_check();
						boolean paramCheck1 = img_doc1.isParam_check();
						
						String param0 = img_doc0.getParam_value();
						String param1 = img_doc1.getParam_value();

						if (connector.equals("||")) {
							if (!(paramCheck0 || paramCheck1)) {
								errors.add(new Error(/*"Current Detection from Image DOES NOT MATCH " + docdesc
										+ " Document Validations For " + param0 +" or " + param1 + ". Detected Value is "
										+ detect_textdesc.trim(), "Document", "101"*/));

								param_check = false;
							}
						} else if (connector.equals("&&")) {
							if (!(paramCheck0 && paramCheck1)) {
								errors.add(new Error(/*"Current Detection from Image DOES NOT MATCH " + docdesc
										+ " Document Validations For " + param0 +" and " + param1 + ". Detected Value is "
										+ detect_textdesc.trim(), "Document", "101"*/));

								param_check = false;
							}
						}
					}
				}
			} /*else {
				ImageDocRes detect_param = detect_param(map, resp, "", req);

				param_check = detect_param.isParam_check();

				res = detect_param.getDetectedValue();

				detper = StringUtils.isBlank(detect_param.getDetectperc()) ? 0D
						: Double.valueOf(detect_param.getDetectperc());
			}*/

			response = ImageDocRes.builder()
					.param_check(param_check)
					.detectedValue(res)
					.detectperc(deciformat.format(Math.round(detper)))
					.errors(errors)
					.build();

		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}
	
	@SuppressWarnings("deprecation")
	private ImageDocRes detect_param(Map<String, Object> map, GoogleApiResponse resp, String temp_paramValue,
			DocumentUploadDetailsReq req) {
		ImageDocRes response = new ImageDocRes();
		log.info("ImageDocRes--> Enter: ");
		try {
			DecimalFormat deciformat = new DecimalFormat("#.###");

			List<TextAnnotations> textResp = resp.getImageresponse().get(0).getTextResponse();

			String textdesc = StringUtils.isBlank(textResp.get(0).getDescriptiontext()) ? ""
					: textResp.get(0).getDescriptiontext().trim();
			log.info("ImageDocRes--> textdesc: "+textdesc);
			String detecttext = "", res = "";

			int temp_detectperc = 0;
			int detectperc2 = temp_paramValue.length(), paramlen = temp_paramValue.length();

			Double detper = 0D;
			Double perc = map.get("MINIMUM_PERCENT") == null ? 50D
					: Double.valueOf(map.get("MINIMUM_PERCENT").toString());

			boolean param_check = false;

			if (StringUtils.isNotBlank(temp_paramValue)) {
				log.info("ImageDocRes--> temp_paramValue: "+temp_paramValue);
				boolean check = textdesc.toLowerCase().contains(temp_paramValue.toLowerCase());

				if (!check) {
					for (int k = 1; k < textResp.size(); k++) {
						detecttext = textResp.get(k).getDescriptiontext().trim().toLowerCase();

						temp_detectperc = StringUtils.getLevenshteinDistance(temp_paramValue, detecttext);
						if (temp_detectperc == 0) {
							res = detecttext;
							param_check = true;
							break;
						} else if (temp_detectperc <= detectperc2) {
							detectperc2 = temp_detectperc;
							res = detecttext;
						}
					}
					double detectperc2d = detectperc2;
					detper = 100 - ((detectperc2d / paramlen) * 100);
					if (detper >= perc) {
						//res = detecttext;
						param_check = true;
						log.info("ImageDocRes-- detper >= perc> : "+res);
					}
				} else {
					detper = 100D;
					res = temp_paramValue;
					param_check = true;
					log.info("ImageDocRes-- detper> : "+res);
				}
			} /*else if (docid.equals("102")) {
				for (int i = 1; i < textResp.size(); i++) {
					detecttext = textResp.get(i).getDescriptiontext().trim();
					if (StringUtils.isNumeric(detecttext) && detecttext.length() >= 7) {
						detper = 100D;
						res = detecttext;
						param_check = true;
						break;
					}
				}
			}*/

			response = ImageDocRes.builder()
					.param_check(param_check)
					.detectedValue(res)
					.detectperc(deciformat.format(detper))
					.param_value(temp_paramValue)
					.build();

		} catch (Exception e) {
			log.error(e);
		}
		log.info("ImageDocRes--> Exit: ");
		return response;
	}
	
	private String savegoogleapidet(GoogleApiResponse response,DocumentUploadDetailsReqRes requests,String status,String value) {
		try {
			String sno = "";
			Long maxsno = 0L;

			List<DocumentUploadDetailsReq> request = requests.getDocumentupldet();

			File file = null;

			String docid = "", path = "", filename = "";
			String quoteno = StringUtils.isBlank(requests.getQuote_no()) ? "0" : requests.getQuote_no();
			String productid = StringUtils.isBlank(request.get(0).getProduct_id()) ? requests.getProduct_id()
					: request.get(0).getProduct_id();
			String param = request.get(0).getParam();

			file = getfilestore(response, request.get(0), quoteno);
			path = file.getCanonicalPath();
			filename = file.getName();
			docid = request.get(0).getDoc_type_id();
			path = path.replace("\\", "/");
			log.info("savegoogleapidet--> path: " + path);

			String dest = commomSer.getapplicationProperty().getProperty("common.file.path");
			String date = commomSer.formatdatewithtime2(new Date());
			String destination = dest + docid + "_" + date + "_" + filename;
			String commonpath = commomSer.copyFile(path, destination);
			commonpath = StringUtils.isBlank(commonpath) ? "" : commonpath;
			commonpath = commonpath.replace("\\", "/");
			log.info("savegoogleapidet--> commonpath: " + commonpath);

			if (response.getImageresponse() != null) {
				List<TextAnnotations> textResponse = response.getImageresponse().get(0).getTextResponse();
				value = StringUtils.isBlank(textResponse.get(0).getDescriptiontext()) ? ""
						: textResponse.get(0).getDescriptiontext().trim();
				value = value.replaceAll("\n", "");
			} else if (response.getErrors() != null) {
				if (response.getErrors().size() > 0) {
					value = response.getErrors().get(0).getMessage();
				}
			}

			if(StringUtils.isNotBlank(path)) {
			/*	maxsno= gapidetRepo.getmaxsno();			
				GoogleapiDetails gapidet= new GoogleapiDetails();
				
				gapidet.setDocumentId(BigDecimal.valueOf(Long.valueOf(docid)));
				//gapidet.setEntryDate((new Date()).toString());
				gapidet.setProductId(StringUtils.isBlank(productid)?(BigDecimal.valueOf(0L)):(BigDecimal.valueOf(Long.valueOf(productid))));
				gapidet.setClaimNo(BigDecimal.valueOf(Long.valueOf(quoteno)));
				gapidet.setResFilePath(path);
				gapidet.setSno(BigDecimal.valueOf(Long.valueOf((maxsno))));
				gapidet.setStatus("Y");
				gapidet.setResFileName(filename);
				gapidet.setParam(param);
				gapidet.setDetectedValue(value);
				gapidet.setCommonFilePath(commonpath);
				
				gapidetRepo.save(gapidet); */
			}
			sno=maxsno==null?"":maxsno.toString();
			return sno;
		}catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	private File getfilestore(GoogleApiResponse request, DocumentUploadDetailsReq req, String quoteno) {
		String path = "", filename = "", pathname = "", response = "";
		String date = "";
		String docid = StringUtils.isBlank(req.getDoc_type_id()) ? "" : req.getDoc_type_id().trim();
		File file = null;
		try {
			String classpath = this.getClass().getClassLoader().getResource("").getPath();
			classpath = classpath.replaceAll("classes/", "documents/");
			classpath = classpath.replaceAll("%20", " ");
			log.info("classpath: " + classpath);

			pathname = classpath;
			date = commomSer.formatdatewithtime3(new Date());

			filename = docid + "_" + quoteno + "(" + date + ")";
			filename = filename.replaceAll("\\s", "");
			file = new File(pathname + filename + ".txt");

			file.createNewFile();
			Writer writer = new BufferedWriter(new FileWriter(file));
			response = commomSer.reqPrint(request);
			writer.write(response);
			writer.close();
			path = file.getCanonicalPath();
			filename = file.getName();
			log.info("PathName: " + path + " FileName: " + filename);
		} catch (Exception e) {
			log.error(e);
		}
		return file;
	}
	
}
