package com.maan.common.msw.impl;

import org.springframework.stereotype.Service;

import com.maan.common.msw.WhatsAppService;
import com.maan.common.upload.request.MSWReq;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

	@Override
	public String sendWhatsApp_tocust(MSWReq request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String send_bulkmsg(String fileyn) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	@Autowired
	private CommonService cs;


	private Logger log = LogManager.getLogger(getClass());

	@Override
	public String sendWhatsApp_tocust(MSWReq request) {
		String response = "";
		try {
			//Long sno = null;
			//List<Long> snos = new ArrayList<>();
			log.info("sendWhatsApp_tocust--> request: ");
			cs.reqPrint(request);

			String type = StringUtils.isBlank(request.getType()) ? "" : request.getType();
			String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id();
			String quoteno = StringUtils.isBlank(request.getQuote_no()) ? "" : request.getQuote_no();

			String usertype = StringUtils.isBlank(request.getUsertype()) ? "" : request.getUsertype();
			String subusertype = StringUtils.isBlank(request.getSubusertype()) ? "" : request.getSubusertype();

			log.info("sendWhatsApp_tocust--> Type: " + type + " productid: " + productid);
			log.info("sendWhatsApp_tocust--> usertype: " + usertype + " subusertype: " + subusertype);

			if (StringUtils.isNotBlank(quoteno) && productid.equals("65")) {
				//MotorDataDetail mdd = mddRepo.findByMotDataDetPK_Quoteno(Long.valueOf(quoteno));

				String agencycode = "";

				//HomePositionMaster hpmdet = hpmRepo.findByHpmPkQuoteno(Long.valueOf(quoteno)).get(0);

				String renewalStatus = "";
				String renewalpolicy = "";

				log.info("sendWhatsApp_tocust--> agencycode: " + agencycode);
				log.info("sendWhatsApp_tocust--> renewalpolicy: " + renewalpolicy + " renewalStatus: " + renewalStatus);

				if (renewalStatus.equalsIgnoreCase("Y") && StringUtils.isNotBlank(renewalpolicy)
						&& agencycode.equals("90016")) {
					if (type.equalsIgnoreCase("REF_APPROVED_MOTOR")) {
						type = "B2C_RENEW_REF_APPROVED_MOTOR";
					} else if (type.equalsIgnoreCase("REF_REJECTED_MOTOR")) {
						type = "B2C_RENEW_REF_REJECTED_MOTOR";
					}

					request.setType(type);

					log.info("sendWhatsApp_tocust--> type: " + type);
				}
			}

			response = setWatiApiRequest(request);

			/*snos = insWhatsappIntegDet(request);
			log.info("sendWhatsApp_tocust--> snos: " + snos.size());
			if (snos.size() > 0 && snos != null) {
				for (int j = 0; j < snos.size(); j++) {
					sno = snos.get(j);
					response = sendwhatsappmsg(request, sno);
				}
			}

		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}

	// Insert Whatsapp Details
	private List<Long> insWhatsappIntegDet(MSWReq request) {
		List<Long> snos = new ArrayList<>();
		try {
			/*
			Long sno = null;

			String type = StringUtils.isBlank(request.getType()) ? "" : request.getType();
			String quoteno = StringUtils.isBlank(request.getQuote_no()) ? "" : request.getQuote_no();
			String policyNo = StringUtils.isBlank(request.getPolicyno()) ? "" : request.getPolicyno();
			String filepath = request.getFilepath();
			String whatsappbody = "";
			String productType = StringUtils.isBlank(request.getProduct_type()) ? "" : request.getProduct_type();

			String tinyurl = request.getTinyurl();
			String reqrefno = request.getRequestreferenceno();
			String mobileno = "", admloginid = "";
			String mobCode = "", branchcode = "";
			String appid = "", loginid = "";
			String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id();
			String otpid = request.getOtpid();
			String usertype = StringUtils.isBlank(request.getUsertype()) ? "" : request.getUsertype();
			String subusertype = StringUtils.isBlank(request.getSubusertype()) ? "" : request.getSubusertype();
			String defaultmobcode = "";
			defaultmobcode = StringUtils.isBlank(defaultmobcode) ? "968" : defaultmobcode;
			String hittype = "",fileyn="";

			List<String> loginids = new ArrayList<>();
			List<Map<String, List<String>>> mobnocodelist = new ArrayList<>();
			List<String> mobnos = new ArrayList<>();
			List<String> mobcodes = new ArrayList<>();

			Map<String, List<String>> mobnocode = new HashMap<String, List<String>>();
			Map<String, Object> whatsappData = new HashMap<String, Object>();
			Map<String, Object> otpdet = new HashMap<String, Object>();


			if (StringUtils.isBlank(filepath)) {
				filepath = cs.getwebserviceurlProperty().getProperty("WhatsApp.default.URL") + filepath;
			}

			if (type.equalsIgnoreCase("GET_OTP_MOTOR")) {
				if (StringUtils.isNotBlank(otpid)) {
					if (productType.equals("Claim")) {
						
					}
				}
			} else if (productid.equalsIgnoreCase("41")) {
				if (type.equalsIgnoreCase("GET_OTP_RSA") || type.equalsIgnoreCase("POLICY_RECEIPT_RSA")) {
					
				}
			} else if ((type.equalsIgnoreCase("CLAIM_INTIMATION_MOTOR")
					|| type.equalsIgnoreCase("CLAIM_INTIMATION_APPROVE")
					|| type.equalsIgnoreCase("CLAIM_INTIMATION_REJECT")
					|| type.equalsIgnoreCase("CLAIM_INTIMATION_PROCESS")
					|| type.equalsIgnoreCase("CLAIM_INTIMATION_PROCESS_APPROVER")
					|| type.equalsIgnoreCase("CLAIM_AWAITING_CLARIFICATION_APPROVED")) && StringUtils.isBlank(otpid)) {

				whatsappData = quoteRepo.getMailContentDetails(reqrefno);

			} else if (type.equalsIgnoreCase("CLAIM_STATUS")) {

				whatsappData = quoteRepo.getclaimstatusdet(reqrefno);

			} else if (type.equalsIgnoreCase("ENORSEMENT_NOTIFY") || type.equalsIgnoreCase("ENDORSEMENT_APPROVE")
					|| type.equalsIgnoreCase("ENDORSEMENT_REJECT")) {

				whatsappData = quoteRepo.getendorsementnotify(reqrefno);

			} else if (type.equalsIgnoreCase("FEEDBACK_MOTOR")) {

				whatsappData = quoteRepo.getremarks(reqrefno);

			} else if (type.equalsIgnoreCase("RENEWAL_POLICY_TINYURL")) {

				whatsappData = quoteRepo.get_renew_tinyurldet(reqrefno);

			}else if(type.equalsIgnoreCase("GET_REF_QUOTE_FLEET")||type.equalsIgnoreCase("FLEET_REF_REJ_UW")
					||type.equalsIgnoreCase("FLEET_REF_APPR_UW")||
					type.equalsIgnoreCase("REF_APPROVED_FLEET")
					|| type.equalsIgnoreCase("REF_REJECTED_FLEET")) {
				whatsappData = quoteRepo.get_fleet_HODRefer_details(reqrefno);
			} else if (StringUtils.isNotBlank(quoteno)) {
				whatsappData = quoteRepo.get_hpm_pi_det(quoteno);
				appid = whatsappData.get("APPLICATION_ID") == null ? ""
						: whatsappData.get("APPLICATION_ID").toString();
				log.info("PolicyConfirm ApplicationID: " + appid);
			}

			if (StringUtils.isNotBlank(type)) {
				if ("POLICY_CONFIRM_MOTOR".equalsIgnoreCase(type)) {

					whatsappbody = getWhatsappContent(request);
					whatsappbody = whatsappbody.replaceAll("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", "");

					if ("1".equalsIgnoreCase(appid)) {
						request.setType("POLICY_RECEIPT_MOTOR");
					}

					log.info("WhatsappBody_Policy_Confirm: " + whatsappbody);
				} else if ("POLICY_CONFIRM_DOMESTIC".equalsIgnoreCase(type)) {
					request.setType("POLICY_CONFIRM_DOMESTIC");
					whatsappbody = getWhatsappContent(request);
					whatsappbody = whatsappbody.replaceAll("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", "");

					if ("1".equalsIgnoreCase(appid)) {
						request.setType("POLICY_RECEIPT_DOMESTIC");
					}

					log.info("WhatsappBody_Policy_Confirm Domestic: " + whatsappbody);
				} else {
					whatsappbody = getWhatsappContent(request);
				}
			}

			if (StringUtils.isNotBlank(whatsappbody)) {
				whatsappbody = whatsappbody.replace("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", "");
				whatsappbody = whatsappbody.replace("<br/>", " ");
				log.info("insWhatsappIntegDet--> whatsappbody: " + whatsappbody);

				Map<String, Object> mtmmap = mtmRepo.getWhatsappTemplate(type, productid);
				
				fileyn = mtmmap.get("FILE_YN") == null ? "N" : mtmmap.get("FILE_YN").toString();
				hittype = mtmmap.get("HIT_TYPE") == null ? "I" : mtmmap.get("HIT_TYPE").toString();
				
				if (productType.equals("Claim")) {
					if (type.equals("CLAIM_INTIMATION_PROCESS_APPROVER")) {
						mobnos = whatsAppRepo.get_claimMobno(reqrefno);
						mobnos.addAll(whatsAppRepo.get_InjuredMobno(reqrefno));
						mobcodes = getmobilecodes(mobnos);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else {
						mobileno = whatsappData.get("MOBILE_NO") == null ? ""
								: whatsappData.get("MOBILE_NO").toString();
						mobnos.add(mobileno);

						if (type.equalsIgnoreCase("CLAIM_INTIMATION_MOTOR")
								|| type.equalsIgnoreCase("CLAIM_INTIMATION_APPROVE")
								|| type.equalsIgnoreCase("CLAIM_INTIMATION_REJECT")) {
							mobnos.addAll(whatsAppRepo.get_InjuredMobno(reqrefno));
						}
						mobcodes = getmobilecodes(mobnos);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					}
				} else {
					if (type.equalsIgnoreCase("CUST_APPROVED_MOTOR") || type.equalsIgnoreCase("CUST_REJECTED_MOTOR")
							|| type.equalsIgnoreCase("CUST_REQUOTE_MOTOR")
							|| type.equalsIgnoreCase("CUST_APPROVED_DOMESTIC")
							|| type.equalsIgnoreCase("CUST_REJECTED_DOMESTIC")
							|| type.equalsIgnoreCase("CUST_REQUOTE_DOMESTIC")) {

						appid = whatsappData.get("APPLICATION_ID") == null ? ""
								: whatsappData.get("APPLICATION_ID").toString();
						loginid = whatsappData.get("LOGIN_ID") == null ? "" : whatsappData.get("LOGIN_ID").toString();
						if (!appid.equals("1")) {
							mobileno = mtmRepo.getmobileno(appid);
						} else {
							mobileno = mtmRepo.getmobileno(loginid);
						}
						mobnos.add(mobileno);
						mobcodes.add(defaultmobcode);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equalsIgnoreCase("MOTOR_REF_REJ_UW") ||type.equalsIgnoreCase("FLEET_REF_REJ_UW")||type.equalsIgnoreCase("FLEET_REF_APPR_UW")
							|| type.equalsIgnoreCase("MOTOR_REF_APPR_UW")) {
						appid = whatsappData.get("APPLICATION_ID") == null ? ""
								: whatsappData.get("APPLICATION_ID").toString();
						loginid = whatsappData.get("LOGIN_ID") == null ? "" : whatsappData.get("LOGIN_ID").toString();
						admloginid = whatsappData.get("HOD_LOGINID") == null ? ""
								: whatsappData.get("HOD_LOGINID").toString();
						if (!appid.equals("1") && subusertype.equalsIgnoreCase("HOD")) {
							mobileno = mtmRepo.getmobileno(appid);
							mobnos.add(mobileno);
							mobcodes.add(defaultmobcode);
							mobnocode.put("WHATSAPPNO", mobnos);
							mobnocode.put("WHATSAPPCODE", mobcodes);
							mobnocodelist.add(mobnocode);
						} else if (!appid.equals("1") && subusertype.equalsIgnoreCase("CEO")) {
							loginids.add(appid.toLowerCase());
							loginids.add(admloginid.toLowerCase());
							mobnos = mtmRepo.getmobilenos(loginids);
							mobcodes = getmobilecodes(mobnos);
							mobnocode.put("WHATSAPPNO", mobnos);
							mobnocode.put("WHATSAPPCODE", mobcodes);
							mobnocodelist.add(mobnocode);
						}
					} else if (type.equalsIgnoreCase("GET_REF_QUOTE_MOTOR") || type.equalsIgnoreCase("GET_REF_QUOTE_FLEET")
							|| type.equalsIgnoreCase("PAYMENT_FAILED_MOTOR_UW")) {
						branchcode = whatsappData.get("BRANCH_CODE") == null ? ""
								: whatsappData.get("BRANCH_CODE").toString();
						mobnos = mtmRepo.getmobilenos(productid, branchcode, "Hod");
						mobcodes = getmobilecodes(mobnos);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equalsIgnoreCase("GET_REF_QUOTE_CEO_MOTOR")) {
						branchcode = whatsappData.get("BRANCH_CODE") == null ? ""
								: whatsappData.get("BRANCH_CODE").toString();
						mobnos = mtmRepo.getmobilenos(productid, branchcode, "Ceo");
						mobcodes = getmobilecodes(mobnos);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equalsIgnoreCase("REF_APPROVED_MOTOR")
							|| type.equalsIgnoreCase("REF_REJECTED_MOTOR")
							|| type.equalsIgnoreCase("B2C_RENEW_REF_APPROVED_MOTOR")
							|| type.equalsIgnoreCase("B2C_RENEW_REF_REJECTED_MOTOR")||
							type.equalsIgnoreCase("REF_APPROVED_FLEET")
							|| type.equalsIgnoreCase("REF_REJECTED_FLEET")) {
						appid = whatsappData.get("APPLICATION_ID") == null ? ""
								: whatsappData.get("APPLICATION_ID").toString();
						loginid = whatsappData.get("LOGIN_ID") == null ? "" : whatsappData.get("LOGIN_ID").toString();
						admloginid = whatsappData.get("HOD_LOGINID") == null ? ""
								: whatsappData.get("HOD_LOGINID").toString();
						if (appid.equals("1") && subusertype.equalsIgnoreCase("HOD")) {
							mobileno = whatsappData.get("WHATSAPP_NO") == null ? ""
									: whatsappData.get("WHATSAPP_NO").toString();
							mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
									: whatsappData.get("WHATSAPP_CODE").toString();
							mobnos.add(mobileno);
							mobcodes.add(mobCode);
							mobnocode.put("WHATSAPPNO", mobnos);
							mobnocode.put("WHATSAPPCODE", mobcodes);
							mobnocodelist.add(mobnocode);
						} else if (appid.equals("1") && subusertype.equalsIgnoreCase("CEO")) {
							mobileno = whatsappData.get("WHATSAPP_NO") == null ? ""
									: whatsappData.get("WHATSAPP_NO").toString();
							mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
									: whatsappData.get("WHATSAPP_CODE").toString();
							String admmobileno = mtmRepo.getmobileno(admloginid);
							mobnos.add(mobileno);
							mobnos.add(admmobileno);
							mobcodes.add(mobCode);
							mobcodes.add(mobCode);
							mobnocode.put("WHATSAPPNO", mobnos);
							mobnocode.put("WHATSAPPCODE", mobcodes);
							mobnocodelist.add(mobnocode);
						}
					} else if (productid.equalsIgnoreCase("41")) {
						if (type.equalsIgnoreCase("GET_OTP_RSA") || type.equalsIgnoreCase("POLICY_RECEIPT_RSA")) {
							whatsappData = hpmRepo.getrsadetails(quoteno);
							mobileno = whatsappData.get("WHATSAPP_NO") == null ? ""
									: whatsappData.get("WHATSAPP_NO").toString();
							mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
									: whatsappData.get("WHATSAPP_CODE").toString();
							mobnos.add(mobileno);
							mobcodes.add(mobCode);
							mobnocode.put("WHATSAPPNO", mobnos);
							mobnocode.put("WHATSAPPCODE", mobcodes);
							mobnocodelist.add(mobnocode);
						}
					} else if (type.equalsIgnoreCase("GET_OTP_MOTOR")) {
						otpdet = omSmsDataDetRepo.getotpdet(otpid);
						reqrefno = otpdet.get("REFERENCE_NO") == null ? "" : otpdet.get("REFERENCE_NO").toString();
						log.info("insWhatsappIntegDet--> reqrefno: " + reqrefno);
						whatsappData = quoteRepo.getpolicytypeforwhatsapp(reqrefno);
						mobileno = whatsappData.get("WHATSAPP_NO") == null ? ""
								: whatsappData.get("WHATSAPP_NO").toString();
						mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
								: whatsappData.get("WHATSAPP_CODE").toString();
						mobnos.add(mobileno);
						mobcodes.add(mobCode);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equalsIgnoreCase("CLAIM_STATUS") || type.equalsIgnoreCase("ENORSEMENT_NOTIFY")
							|| type.equalsIgnoreCase("ENDORSEMENT_APPROVE")
							|| type.equalsIgnoreCase("ENDORSEMENT_REJECT")) {
						mobileno = whatsappData.get("MOBILE_NO") == null ? ""
								: whatsappData.get("MOBILE_NO").toString();
						mobnos.add(mobileno);
						mobcodes.add(defaultmobcode);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equals("POLICY_CONFIRM_MOTOR")) {
						mobileno = whatsappData.get("WHATSAPP_NO") == null ? ""
								: whatsappData.get("WHATSAPP_NO").toString();
						mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
								: whatsappData.get("WHATSAPP_CODE").toString();
						branchcode = whatsappData.get("BRANCH_CODE") == null ? ""
								: whatsappData.get("BRANCH_CODE").toString();
						Map<String, Object> whatsappnomap = whatsAppRepo.getwhatsappno(branchcode);
						mobnos.add(mobileno);
						mobcodes.add(mobCode);

						if (!whatsappnomap.isEmpty()) {
							mobnos.add(whatsappnomap.get("WHATSAPP_NO") == null ? ""
									: whatsappnomap.get("WHATSAPP_NO").toString());
							mobcodes.add(whatsappnomap.get("WHATSAPP_CODE") == null ? ""
									: whatsappnomap.get("WHATSAPP_CODE").toString());
						}
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equals("FEEDBACK_MOTOR")) {
						mobileno = "";
						mobCode = "";
						mobnos.add(mobileno);
						mobcodes.add(mobCode);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equals("RENEWAL_POLICY_TINYURL")) {
						mobileno = whatsappData.get("MOBILE_NO") == null ? ""
								: whatsappData.get("MOBILE_NO").toString();
						mobCode = defaultmobcode;
						mobnos.add(mobileno);
						mobcodes.add(mobCode);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equalsIgnoreCase("GET_OTP_DOMESTIC")) {
						otpdet = omSmsDataDetRepo.getotpdet(otpid);
						reqrefno = otpdet.get("REFERENCE_NO") == null ? "" : otpdet.get("REFERENCE_NO").toString();
						log.info("insWhatsappIntegDet--> reqrefno: " + reqrefno);
						
						whatsappData = empRepo.getdomesticrawdet(reqrefno);
						
						mobileno = whatsappData.get("MOBILE_NO") == null ? ""
								: whatsappData.get("MOBILE_NO").toString();
						mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
								: whatsappData.get("WHATSAPP_CODE").toString();
						mobnos.add(mobileno);
						mobcodes.add(mobCode);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else if (type.equals("POLICY_CONFIRM_DOMESTIC")) {
						mobileno = whatsappData.get("WHATSAPP_NO") == null ? ""
								: whatsappData.get("WHATSAPP_NO").toString();
						mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
								: whatsappData.get("WHATSAPP_CODE").toString();
						String branchCode = whatsappData.get("BRANCH_CODE") == null ? ""
								: whatsappData.get("BRANCH_CODE").toString();
						List<BranchMaster> whatsappnomap = bmRepo
								.findByBranchMasterIdBranchcodeAndStatusAndWhatsappnoIsNotNull(branchCode, "Y");
						mobnos.add(mobileno);
						mobcodes.add(mobCode);

						if (!whatsappnomap.isEmpty()) {
							mobnos.add(whatsappnomap.get(0).getWhatsappno() == null ? ""
									: whatsappnomap.get(0).getWhatsappno().toString());
							mobcodes.add(whatsappnomap.get(0).getWhatsappcode() == null ? ""
									: whatsappnomap.get(0).getWhatsappcode().toString());
						}
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					} else {
						mobileno = whatsappData.get("WHATSAPP_NO") == null ? ""
								: whatsappData.get("WHATSAPP_NO").toString();
						mobCode = whatsappData.get("WHATSAPP_CODE") == null ? ""
								: whatsappData.get("WHATSAPP_CODE").toString();
						mobnos.add(mobileno);
						mobcodes.add(mobCode);
						mobnocode.put("WHATSAPPNO", mobnos);
						mobnocode.put("WHATSAPPCODE", mobcodes);
						mobnocodelist.add(mobnocode);
					}
				}

				log.info("insWhatsappIntegDet--> quoteno: " + quoteno + " PolicyNo: " + policyNo);
				log.info("insWhatsappIntegDet--> mobCodes: " + mobcodes.size() + " mobilenos: " + mobnos.size());

				if (type.equalsIgnoreCase("POLICY_CONFIRM_MOTOR")) {
					String url1 = "";
					String polNo = whatsappData.get("POLICY_NO") == null ? ""
							: whatsappData.get("POLICY_NO").toString();
					filepath = cs.getwebserviceurlProperty().getProperty("Policy_Schedule") + polNo + ".pdf";
					filepath = filepath + url1 + "~";

					if ("1".equalsIgnoreCase(appid)) {
						url1 = cs.getwebserviceurlProperty().getProperty("Debit_Credit_Receipt") + quoteno
								+ "DebitCredit.pdf";
						filepath = filepath + url1 + "~";
					}
					filepath = filepath.substring(0, filepath.length() - 1);
					log.info("insWhatsappIntegDet--> Policy Confirm FilePath: " + filepath);
				} else if (type.equalsIgnoreCase("POLICY_CONFIRM_DOMESTIC")) {
					String url1 = "", mednetCardPath = "", essentialNetwork = "", policywording = "";
					String polNo = whatsappData.get("POLICY_NO") == null ? ""
							: whatsappData.get("POLICY_NO").toString();
					filepath = cs.getwebserviceurlProperty().getProperty("DomesticPolicy_Schedule") + polNo
							+ ".pdf";

					mednetCardPath = cs.getwebserviceurlProperty().getProperty("DomesticMednetURL") + quoteno
							+ "mednet.pdf";
					essentialNetwork = cs.getwebserviceurlProperty().getProperty("DomesticEssentialNetwork");

					policywording = cs.getwebserviceurlProperty().getProperty("DomesticPolicyWording");

					filepath = filepath + "~" + essentialNetwork + "~" + policywording + "~" + mednetCardPath + "~";

					if ("1".equalsIgnoreCase(appid)) {
						url1 = cs.getwebserviceurlProperty().getProperty("DomesticDebit_Credit_Receipt") + quoteno
								+ "DebitCredit.pdf";
						filepath = filepath + url1 + "~";
					}
					filepath = filepath.substring(0, filepath.length() - 1);
					log.info("Policy Confirm FilePath Domestic: " + filepath);
				} else if (type.equalsIgnoreCase("POLICY_RECEIPT_RSA")) {
					String fileurl = cs.getwebserviceurlProperty().getProperty("Policy_Schedule_RSA");
					filepath = fileurl + quoteno + "Schedule.pdf";
				}

				if (mobnocodelist.size() > 0 && mobnocodelist != null) {
					log.info("insWhatsappIntegDet--> mobnocodelist response: ");
					cs.reqPrint(mobnocodelist);

					Map<String, List<String>> map = mobnocodelist.get(0);
					List<String> mobnolist = map.get("WHATSAPPNO");
					List<String> mobcodelist = map.get("WHATSAPPCODE");

					for (int i = 0; i < mobnolist.size(); i++) {
						String whatsappno = mobnolist.get(i);
						String whatsappcode = mobcodelist.get(i);
						if (StringUtils.isNotBlank(whatsappno)) {

							String[] fileList = filepath.split("~");
							for (int j = 0; j < fileList.length; j++) {

								WhatsappIntegrationDetails integWhatsApp = new WhatsappIntegrationDetails();

								sno = whatsAppRepo.getSno();

								integWhatsApp.setProductid(productid);
								integWhatsApp.setTypeid(type);
								integWhatsApp
										.setCode(StringUtils.isBlank(whatsappcode) ? defaultmobcode : whatsappcode);
								integWhatsApp.setMobileno(whatsappno);
								integWhatsApp.setCustomername(whatsappData.get("CUST_NAME") == null ? "MaanSarovar"
										: whatsappData.get("CUST_NAME").toString());
								integWhatsApp.setPolicyno(policyNo);
								integWhatsApp.setQuoteno(quoteno);
								integWhatsApp.setStatus("N");
								integWhatsApp.setEntrydate(new Date());
								integWhatsApp.setRemarks(whatsappbody);
								integWhatsApp.setTinyurl(tinyurl);
								integWhatsApp.setFilepath(fileList[j]);
								integWhatsApp.setHittype(StringUtils.isBlank(hittype) ? "I" : hittype);
								integWhatsApp.setFileyn(StringUtils.isBlank(fileyn) ? "N" : fileyn);
								integWhatsApp.setSno(sno);

								whatsAppRepo.save(integWhatsApp);

								snos.add(sno);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return snos;
	}

	private WhatsAppReq getWhatsappReq(MSWReq request, Long sno) {
		log.info("getWhatsappReq--> QuoteNo: " + request.getQuote_no() + " SNO: " + sno);
		WhatsAppReq req = new WhatsAppReq();
		try {/*
			WhatsappIntegrationDetails reqData = whatsAppRepo.getwhatsappdet(sno);
			if (reqData != null) {

				String filepathurl = StringUtils.isBlank(reqData.getFilepath())
						? cs.getwebserviceurlProperty().getProperty("WhatsApp.default.URL")
						: reqData.getFilepath();

				//String[] whatsappBody = reqData.getRemarks().split("~");
				//String[] filePath = filepathurl.split("~");

				//for (int i = 0; i < whatsappBody.length; i++) {
					String product = StringUtils.isBlank(reqData.getProductid()) ? "" : reqData.getProductid();

					req.setProduct(product.equals("65") ? "MOTOR" : product.equals("35") ? "DOMESTIC" : "");
					req.setType(StringUtils.isBlank(reqData.getTypeid()) ? "" : reqData.getTypeid());
					req.setCode(StringUtils.isBlank(reqData.getCode()) ? "" : reqData.getCode());
					req.setMobile(StringUtils.isBlank(reqData.getMobileno()) ? "" : reqData.getMobileno());
					req.setName(StringUtils.isBlank(reqData.getCustomername()) ? "" : reqData.getCustomername());
					//req.setRemarks(null == whatsappBody[i] ? "" : whatsappBody[i].toString());
					req.setRemarks(reqData.getRemarks());
					//req.setUrl(null == filePath[i] ? "" : filePath[i].toString());
					req.setUrl(filepathurl);
					req.setStatus(StringUtils.isBlank(reqData.getStatus()) ? "Y" : reqData.getStatus());
				//}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return req;
	}

	private String getWhatsappContent(MSWReq req) {
		try {
			String type = req.getType();
			String quoteno = req.getQuote_no();
			String reqrefno = req.getRequestreferenceno();
			String productid = StringUtils.isBlank(req.getProduct_id()) ? "" : req.getProduct_id();

			log.info("getWhatsappContent--> type: " + type + " productid: " + productid);
			log.info("getWhatsappContent--> quoteno: " + quoteno + " reqrefno: " + reqrefno);

			/*Map<String, Object> map = mtmRepo.getWhatsappTemplate(type, productid);
			log.info("getWhatsappContent--> map: " + map.size());

			if (map.size() > 0) {
				Map<String, Object> customerdet = new HashMap<>();
				Map<String, Object> otpdet = new HashMap<>();

				String whatsappbody = map.get("WHATSAPP_BODY_EN") == null ? "" : map.get("WHATSAPP_BODY_EN").toString();
				String whatsappbodyar = map.get("WHATSAPP_BODY_AR") == null ? "" : map.get("WHATSAPP_BODY_AR").toString();
				String whatsappregards = map.get("WHATSAPP_REGARDS") == null ? "" : map.get("WHATSAPP_REGARDS").toString();
				String whatsappregardsar = map.get("WHATSAPP_REGARDS_AR") == null ? ""
						: map.get("WHATSAPP_REGARDS_AR").toString();

				String otp = StringUtils.isBlank(req.getOtp()) ? "" : req.getOtp();
				String tinyurl = StringUtils.isBlank(req.getTinyurl()) ? "" : req.getTinyurl();
				String otpid = req.getOtpid();
				String productType = StringUtils.isBlank(req.getProduct_type()) ? "" : req.getProduct_type();
				String expirydate = "", brokername = "";

				if ((type.equalsIgnoreCase("CLAIM_INTIMATION_MOTOR")
						|| type.equalsIgnoreCase("CLAIM_INTIMATION_APPROVE")
						|| type.equalsIgnoreCase("CLAIM_INTIMATION_REJECT")
						|| type.equalsIgnoreCase("CLAIM_INTIMATION_PROCESS")
						|| type.equalsIgnoreCase("CLAIM_INTIMATION_PROCESS_APPROVER")
						|| type.equalsIgnoreCase("CLAIM_AWAITING_CLARIFICATION_APPROVED"))
						&& StringUtils.isBlank(otpid)) {
					customerdet = quoteRepo.getMailContentDetails(reqrefno);
				} else if (type.equalsIgnoreCase("CLAIM_STATUS")) {
					customerdet = quoteRepo.getclaimstatusdet(reqrefno);
				} else if (type.equalsIgnoreCase("ENORSEMENT_NOTIFY") || type.equalsIgnoreCase("ENDORSEMENT_APPROVE")
						|| type.equalsIgnoreCase("ENDORSEMENT_REJECT")) {
					customerdet = quoteRepo.getendorsementnotify(reqrefno);
				} else if (type.equalsIgnoreCase("GET_OTP_MOTOR") || type.equalsIgnoreCase("GET_OTP_DOMESTIC")) {
					if (productType.equals("Claim")) {
						customerdet = quoteRepo.getclaimdet(reqrefno);
						otpdet = omSmsDataDetRepo.getotpdet(otpid);
						expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
								: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());
					} else if ("35".equalsIgnoreCase(productid)) {

						otpdet = omSmsDataDetRepo.getotpdet(otpid);
						reqrefno = otpdet.get("REFERENCE_NO") == null ? "" : otpdet.get("REFERENCE_NO").toString();
						log.info("getWhatsappContent--> reqrefno: " + reqrefno);

						expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
								: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());
						customerdet = empRepo.getdomesticrawdet(reqrefno);

					}else {
						otpdet = omSmsDataDetRepo.getotpdet(otpid);
						reqrefno = otpdet.get("REFERENCE_NO") == null ? "" : otpdet.get("REFERENCE_NO").toString();
						log.info("getWhatsappContent--> reqrefno: " + reqrefno);
						customerdet = quoteRepo.getpolicytypeforwhatsapp(reqrefno);
						expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
								: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());
					}
				} else if (productid.equalsIgnoreCase("41")) {
					if (type.equalsIgnoreCase("GET_OTP_RSA")) {
						customerdet = hpmRepo.getrsadetails(quoteno);
						otpdet = omSmsDataDetRepo.getotpdet(otpid);
						expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
								: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());
					} else if (type.equalsIgnoreCase("POLICY_RECEIPT_RSA")) {
						customerdet = hpmRepo.getrsadetails(quoteno);
					}
				} else if (type.equalsIgnoreCase("FEEDBACK_MOTOR")) {
					customerdet = quoteRepo.getremarks(reqrefno);
				} else if (type.equalsIgnoreCase("RENEWAL_POLICY_TINYURL")) {
					customerdet = quoteRepo.get_renew_tinyurldet(reqrefno);
				} else if(type.equalsIgnoreCase("GET_REF_QUOTE_FLEET")||type.equalsIgnoreCase("FLEET_REF_REJ_UW")
						||type.equalsIgnoreCase("FLEET_REF_APPR_UW")||
						type.equalsIgnoreCase("REF_APPROVED_FLEET")
						|| type.equalsIgnoreCase("REF_REJECTED_FLEET")) {
					customerdet = quoteRepo.get_fleet_HODRefer_details(reqrefno);
					brokername =quoteRepo.getfleetbrokername(reqrefno);
					brokername = StringUtils.isBlank(brokername) ? "" : brokername;
				}else if (StringUtils.isNotBlank(quoteno)) {
					customerdet = quoteRepo.get_hpm_pi_det(quoteno);
					brokername = quoteRepo.getbrokername(quoteno);
					brokername = StringUtils.isBlank(brokername) ? "" : brokername;
				}

				for (Map.Entry<String, Object> entry : customerdet.entrySet()) {
					if (whatsappbody.contains(entry.getKey().toString())) {
						whatsappbody = whatsappbody.replace("{" + entry.getKey().toString() + "}",
								entry.getValue() == null ? "" : entry.getValue().toString());
					}
					if (StringUtils.isNotBlank(whatsappbodyar)) {
						if (whatsappbodyar.contains(entry.getKey().toString())) {
							whatsappbodyar = whatsappbodyar.replace("{" + entry.getKey().toString() + "}",
									entry.getValue() == null ? "" : entry.getValue().toString());
						}
					}
				}
				
				whatsappbody = whatsappbody.replace("{OTP}", otp);
				whatsappbody = whatsappbody.replace("{TINYURL}", tinyurl);
				whatsappbody = whatsappbody.replace("{OTP_EXPIRY}", expirydate);
				whatsappbody = whatsappbody.replace("{BROKER_NAME}", brokername);

				whatsappbodyar = whatsappbodyar.replace("{OTP}", otp);
				whatsappbodyar = whatsappbodyar.replace("{TINYURL}", tinyurl);
				whatsappbodyar = whatsappbodyar.replace("{OTP_EXPIRY}", expirydate);
				whatsappbodyar = whatsappbodyar.replace("{BROKER_NAME}", brokername);

				whatsappbody = whatsappbody + "<br/><br/>" + whatsappbodyar + "<br/><br/>" + whatsappregards;
				whatsappbody = whatsappbody.replace("<br/>", " ");

				return whatsappbody;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	private String sendwhatsappmsg(MSWReq request, Long sno) {
		String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id();
		String response = "";

		try {
			String type = request.getType();
			WhatsAppReq req = new WhatsAppReq();

			Map<String, Object> map = mtmRepo.getWhatsappTemplate(type, productid);

			if (map.size() > 0) {
				log.info("sendwhatsappmsg--> sno: " + sno);
				
				String fileyn = map.get("FILE_YN") == null ? "N" : map.get("FILE_YN").toString();
				String hittype = map.get("HIT_TYPE") == null ? "I" : map.get("HIT_TYPE").toString();
				
				log.info("sendwhatsappmsg--> hittype: "+hittype);
				
				if (hittype.equalsIgnoreCase("I")) {

					req = getWhatsappReq(request, sno);

					log.info("Whatsapp Req : ");
					cs.reqPrint(req);

					if (fileyn.equalsIgnoreCase("N")) {
						req.setUrl("");
						response = sendwhatsappmsg(request, req, "N", sno, new Date());
					} else if (fileyn.equalsIgnoreCase("Y")) {
						String fileurl = req.getUrl();
						String remarks = req.getRemarks();

						req.setUrl("");
						response = sendwhatsappmsg(request, req, "N", sno, new Date());

						req.setUrl(fileurl);
						req.setRemarks("");
						response = sendwhatsappmsg(request, req, "Y", sno, new Date());
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return response;
	}

	private String sendwhatsappmsg(MSWReq request, WhatsAppReq req, String fileyn, Long sno, Date reqtime) {
		WhatsAppRes whatsAppRes = new WhatsAppRes();

		String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id();
		String resp = "";

		try {
			String status = "", whatsappresp = "", url = "";

			if (fileyn.equalsIgnoreCase("N")) {
				url = cs.getwebserviceurlProperty().getProperty("WhatsAppURL_MSG");
			} else if (fileyn.equalsIgnoreCase("Y")) {
				url = cs.getwebserviceurlProperty().getProperty("WhatsAppURL");
			}

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.set("token", cs.getwebserviceurlProperty().getProperty("WhatsApp_Token"));
			headers.add("Content-Disposition", "inline");

			HttpEntity<WhatsAppReq> entityReq = new HttpEntity<WhatsAppReq>(req, headers);
			ResponseEntity<WhatsAppRes> response = restTemplate.postForEntity(url, entityReq, WhatsAppRes.class);

			log.info("Response_Whatsapp : ");
			cs.reqPrint(response);
			whatsAppRes = response.getBody();

			status = whatsAppRes.getStatus() ? "Y" : "N";
			whatsappresp = status.equals("Y") ? whatsAppRes.getResult() : whatsAppRes.getMessage();

			if (fileyn.equalsIgnoreCase("N")) {
				whatsAppRepo.updwhatsappstatus(status, whatsappresp, sno, productid, status, reqtime, new Date());
			} else if (fileyn.equalsIgnoreCase("Y")) {
				whatsAppRepo.updfilewhatsappstatus(status, whatsappresp, sno, productid, status, reqtime, new Date());
			}

			resp = "WhatsApp Sended For this QuoteNo: " + request.getQuote_no();

		} catch (HttpStatusCodeException e) {
			try {
				log.info(e.getResponseBodyAsString());
				whatsAppRes = new ObjectMapper().readValue(e.getResponseBodyAsString(), WhatsAppRes.class);
			} catch (Exception ex) {
				log.error(ex);
			}
			resp = "Mail Not Sended For this QuoteNo: " + request.getQuote_no();
		} catch (Exception e) {
			if (fileyn.equalsIgnoreCase("N")) {
				whatsAppRepo.updwhatsappstatus("N", e.getLocalizedMessage(), sno, productid, "N", reqtime, new Date());
			} else if (fileyn.equalsIgnoreCase("Y")) {
				whatsAppRepo.updfilewhatsappstatus("N", e.getLocalizedMessage(), sno, productid, "N", reqtime,
						new Date());
			}
			log.error(e);
			resp = "Mail Not Sended For this QuoteNo: " + request.getQuote_no();
		}
		return resp;
	}

	private List<String> getmobilecodes(List<String> mobnos) {
		try {
			List<String> response = new ArrayList<>();
			String defaultmobcode = quoteRepo.getremarks("195", "1");
			defaultmobcode = StringUtils.isBlank(defaultmobcode) ? "968" : defaultmobcode;
			for (int i = 0; i < mobnos.size(); i++) {
				response.add(defaultmobcode);
			}
			return response;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public String send_bulkmsg(String fileyn) {
		try {
			log.info("send_bulkmsg--> fileyn: " + fileyn);

			List<String> sno_list = new ArrayList<>();

			if (fileyn.equalsIgnoreCase("N"))
				sno_list = whatsAppRepo.get_bulksno_wo_file();
			else if (fileyn.equalsIgnoreCase("Y"))
				sno_list = whatsAppRepo.get_bulksno_w_file();

			MSWReq request = new MSWReq();
			WhatsAppRes whatsAppRes = new WhatsAppRes();
			WhatsBulkReq bulkreq = new WhatsBulkReq();

			List<WhatsAppReq> messageList = new ArrayList<>();

			Date reqtime = new Date();

			int upd_count = 0;

			for (int i = 0; i < sno_list.size(); i++) {

				Long sno = StringUtils.isBlank(sno_list.get(i)) ? 0 : Long.valueOf(sno_list.get(i));

				WhatsAppReq req = getWhatsappReq(request, sno);

				messageList.add(req);
			}

			if (messageList.size() > 0) {
				try {
					bulkreq.setMessageList(messageList);

					log.info("send_bulkmsg--> Whatsapp bulkreq : ");
					cs.reqPrint(bulkreq);

					String url = cs.getwebserviceurlProperty().getProperty("WhatsAppBULKURL");
					String status = "", whatsappresp = "";

					RestTemplate restTemplate = new RestTemplate();
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
					headers.set("token", cs.getwebserviceurlProperty().getProperty("WhatsApp_Token"));
					headers.add("Content-Disposition", "inline");
					headers.add("Content-Type", "application/pdf");

					HttpEntity<WhatsBulkReq> entityReq = new HttpEntity<WhatsBulkReq>(bulkreq, headers);
					ResponseEntity<WhatsAppRes> response = restTemplate.postForEntity(url, entityReq,
							WhatsAppRes.class);

					log.info("send_bulkmsg--> response : ");
					cs.reqPrint(response);

					whatsAppRes = response.getBody();

					status = whatsAppRes.getStatus() ? "Y" : "N";
					whatsappresp = status.equals("Y") ? whatsAppRes.getResult() : whatsAppRes.getMessage();

					if (fileyn.equalsIgnoreCase("N")) {
						upd_count = whatsAppRepo.upd_bulk_whatsapp_WOfile(status, whatsappresp, sno_list, status,
								reqtime, new Date());
					} else if (fileyn.equalsIgnoreCase("Y")) {
						upd_count = whatsAppRepo.upd_bulk_whatsapp_Wfile(status, whatsappresp, status, whatsappresp,
								status, reqtime, new Date(), sno_list);
					}

				} catch (HttpStatusCodeException e) {
					try {
						log.info(e.getResponseBodyAsString());
						whatsAppRes = new ObjectMapper().readValue(e.getResponseBodyAsString(), WhatsAppRes.class);
					} catch (Exception ex) {
						log.error(ex);
					}
				} catch (Exception e) {
					if (fileyn.equalsIgnoreCase("N")) {
						upd_count = whatsAppRepo.upd_bulk_whatsapp_WOfile("N", e.getLocalizedMessage(), sno_list, "N",
								reqtime, new Date());
					} else if (fileyn.equalsIgnoreCase("Y")) {
						upd_count = whatsAppRepo.upd_bulk_whatsapp_Wfile("N", e.getLocalizedMessage(), "N",
								e.getLocalizedMessage(), "N", reqtime, new Date(), sno_list);
					}

					log.error(e);
				}

				log.info("send_bulkmsg--> upd_count: " + upd_count);

				log.info("send_bulkmsg--> Whatsapp res : ");
				cs.reqPrint(whatsAppRes);
			}

		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	private String setWatiApiRequest(MSWReq request) {
		try {

			String type = StringUtils.isBlank(request.getType()) ? "" : request.getType();
			String quoteno = StringUtils.isBlank(request.getQuote_no()) ? "" : request.getQuote_no();
			String productid = StringUtils.isBlank(request.getProduct_id()) ? "" : request.getProduct_id();

			if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(quoteno) && StringUtils.isNotBlank(productid)) {

				int count = whatsAppRepo.getCount(type, productid);

				log.info("setWatiApiRequest--> count: " + count);

				if (count > 0) {

					List<WatiApiRequest> reqList = new ArrayList<>();

					Map<String, Object> detail = getWhatsappContents(request);

					String agencyCode = detail.get("AGENCY_CODE") == null ? "" : detail.get("AGENCY_CODE").toString();

					List<Map<String, Object>> list = whatsAppRepo.getStageCodes(type, agencyCode, productid);

					log.info("setWatiApiRequest--> list: " + list.size());

					for (Map<String, Object> map : list) {

						String stage = map.get("STAGE_CODE") == null ? "" : map.get("STAGE_CODE").toString();
						String subStage = map.get("STAGESUB_CODE") == null ? "" : map.get("STAGESUB_CODE").toString();
						String stageOrder = map.get("STAGE_ORDER") == null ? "" : map.get("STAGE_ORDER").toString();
						String fileyn = map.get("FILE_YN") == null ? "N" : map.get("FILE_YN").toString();
						String filePath = "";

						if (fileyn.equalsIgnoreCase("Y")) {
							filePath = setFilePath(map, detail);
							filePath = StringUtils.isBlank(filePath) ? "" : filePath.trim();
						}

						String message = setMessage(map, detail);
						message = StringUtils.isBlank(message) ? "" : message.trim();

						if ((fileyn.equalsIgnoreCase("N") && StringUtils.isNotBlank(message))
								|| (fileyn.equalsIgnoreCase("Y") && StringUtils.isNotBlank(filePath))) {

							WatiApiRequest req = WatiApiRequest.builder()
									.agencycode(agencyCode)
									.currentStage(stage)
									.filePath(filePath)
									.fileYN(fileyn)
									.message(message)
									.productid(productid)
									.quoteNo(quoteno)
									.stageOrder(stageOrder)
									.subStage(subStage)
									.type(type)
									.whatsappCode(detail.get("WHATSAPP_CODE") == null ? ""
											: detail.get("WHATSAPP_CODE").toString())
									.whatsappno(detail.get("WHATSAPP_NO") == null ? ""
											: detail.get("WHATSAPP_NO").toString())
									.build();

							reqList.add(req);
						}
					}

					log.info("setWatiApiRequest--> reqList: " + reqList.size());

					if (reqList.size() > 0) {
						callSaveWatiApi(reqList);
					}
				}
			}

		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	private Map<String, Object> getWhatsappContents(MSWReq req) {
		try {
			String type = req.getType();
			String quoteno = req.getQuote_no();
			String reqrefno = req.getRequestreferenceno();
			String productid = StringUtils.isBlank(req.getProduct_id()) ? "" : req.getProduct_id();
			String whatsappCode = StringUtils.isBlank(req.getWhatsAppCode()) ? "" : req.getWhatsAppCode().trim();
			String whatsappno = StringUtils.isBlank(req.getWhatsAppno()) ? "" : req.getWhatsAppno().trim();

			log.info("getWhatsappContents--> type: " + type + " productid: " + productid);
			log.info("getWhatsappContents--> quoteno: " + quoteno + " reqrefno: " + reqrefno);

			Map<String, Object> customerdet = new HashMap<>();
			Map<String, Object> otpdet = new HashMap<>();
			Map<String, Object> detail = new HashMap<>();

			String otp = StringUtils.isBlank(req.getOtp()) ? "" : req.getOtp();
			String tinyurl = StringUtils.isBlank(req.getTinyurl()) ? "" : req.getTinyurl();
			String otpid = req.getOtpid();
			String productType = StringUtils.isBlank(req.getProduct_type()) ? "" : req.getProduct_type();
			String expirydate = "", brokername = "";

			if ((type.equalsIgnoreCase("CLAIM_INTIMATION_MOTOR") || type.equalsIgnoreCase("CLAIM_INTIMATION_APPROVE")
					|| type.equalsIgnoreCase("CLAIM_INTIMATION_REJECT")
					|| type.equalsIgnoreCase("CLAIM_INTIMATION_PROCESS")
					|| type.equalsIgnoreCase("CLAIM_INTIMATION_PROCESS_APPROVER")
					|| type.equalsIgnoreCase("CLAIM_AWAITING_CLARIFICATION_APPROVED")) && StringUtils.isBlank(otpid)) {

				customerdet = quoteRepo.getMailContentDetails(reqrefno);

			} else if (type.equalsIgnoreCase("CLAIM_STATUS")) {

				customerdet = quoteRepo.getclaimstatusdet(reqrefno);

			} else if (type.equalsIgnoreCase("ENORSEMENT_NOTIFY") || type.equalsIgnoreCase("ENDORSEMENT_APPROVE")
					|| type.equalsIgnoreCase("ENDORSEMENT_REJECT")) {

				customerdet = quoteRepo.getendorsementnotify(reqrefno);

			} else if (type.equalsIgnoreCase("GET_OTP_MOTOR") || type.equalsIgnoreCase("GET_OTP_DOMESTIC")) {
				if (productType.equals("Claim")) {

					customerdet = quoteRepo.getclaimdet(reqrefno);
					otpdet = omSmsDataDetRepo.getotpdet(otpid);
					expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
							: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());

				} else if ("35".equalsIgnoreCase(productid)) {

					otpdet = omSmsDataDetRepo.getotpdet(otpid);
					reqrefno = otpdet.get("REFERENCE_NO") == null ? "" : otpdet.get("REFERENCE_NO").toString();
					log.info("getWhatsappContent--> reqrefno: " + reqrefno);

					expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
							: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());
					customerdet = empRepo.getdomesticrawdet(reqrefno);

				} else {

					otpdet = omSmsDataDetRepo.getotpdet(otpid);
					reqrefno = otpdet.get("REFERENCE_NO") == null ? "" : otpdet.get("REFERENCE_NO").toString();
					log.info("getWhatsappContent--> reqrefno: " + reqrefno);
					customerdet = quoteRepo.getpolicytypeforwhatsapp(reqrefno);
					expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
							: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());

				}
			} else if (productid.equalsIgnoreCase("41")) {
				if (type.equalsIgnoreCase("GET_OTP_RSA")) {

					customerdet = hpmRepo.getrsadetails(quoteno);
					otpdet = omSmsDataDetRepo.getotpdet(otpid);
					expirydate = otpdet.get("EXPIRY_DATE") == null ? ""
							: cs.formatdatewithtime4(otpdet.get("EXPIRY_DATE").toString());

				} else if (type.equalsIgnoreCase("POLICY_RECEIPT_RSA")) {

					customerdet = hpmRepo.getrsadetails(quoteno);

				}
			} else if (type.equalsIgnoreCase("FEEDBACK_MOTOR")) {

				customerdet = quoteRepo.getremarks(reqrefno);

			} else if (type.equalsIgnoreCase("RENEWAL_POLICY_TINYURL")) {

				customerdet = quoteRepo.get_renew_tinyurldet(reqrefno);

			} else if (type.equalsIgnoreCase("GET_REF_QUOTE_FLEET") || type.equalsIgnoreCase("FLEET_REF_REJ_UW")
					|| type.equalsIgnoreCase("FLEET_REF_APPR_UW") || type.equalsIgnoreCase("REF_APPROVED_FLEET")
					|| type.equalsIgnoreCase("REF_REJECTED_FLEET")) {

				customerdet = quoteRepo.get_fleet_HODRefer_details(reqrefno);
				brokername = quoteRepo.getfleetbrokername(reqrefno);
				brokername = StringUtils.isBlank(brokername) ? "" : brokername;

			} else if (type.equalsIgnoreCase("SESS_EXPMSG")) {

				String waid = whatsappCode + whatsappno;

				customerdet = whatsAppRepo.getContactDet(waid, whatsappCode, whatsappno);

			} else if (StringUtils.isNotBlank(quoteno)) {

				customerdet = quoteRepo.get_hpm_pi_det(quoteno);
				brokername = quoteRepo.getbrokername(quoteno);
				brokername = StringUtils.isBlank(brokername) ? "" : brokername;
			}

			if (customerdet.size() > 0) {

				detail.putAll(customerdet);

				detail.put("OTP", otp);

				if (StringUtils.isNotBlank(tinyurl))
					detail.put("TINYURL", tinyurl);

				detail.put("OTP_EXPIRY", expirydate);
				detail.put("BROKER_NAME", brokername);
			}

			return detail;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	private String setMessage(Map<String, Object> map, Map<String, Object> detail) {
		try {

			String whatsappbody = map.get("MESSAGE_CONTENT_EN") == null ? "" : map.get("MESSAGE_CONTENT_EN").toString();
			String whatsappbodyar = map.get("MESSAGE_CONTENT_AR") == null ? ""
					: map.get("MESSAGE_CONTENT_AR").toString();
			String whatsappregards = map.get("MESSAGE_REGARDS_EN") == null ? ""
					: map.get("MESSAGE_REGARDS_EN").toString();
			String whatsappregardsar = map.get("MESSAGE_REGARDS_AR") == null ? ""
					: map.get("MESSAGE_REGARDS_AR").toString();

			for (Map.Entry<String, Object> entry : detail.entrySet()) {
				if (whatsappbody.contains(entry.getKey().toString())) {
					whatsappbody = whatsappbody.replace("{" + entry.getKey().toString() + "}",
							entry.getValue() == null ? "" : entry.getValue().toString());
				}
				if (StringUtils.isNotBlank(whatsappbodyar)) {
					if (whatsappbodyar.contains(entry.getKey().toString())) {
						whatsappbodyar = whatsappbodyar.replace("{" + entry.getKey().toString() + "}",
								entry.getValue() == null ? "" : entry.getValue().toString());
					}
				}
			}

			whatsappbody = whatsappbody + "<br/><br/>" + whatsappbodyar + "<br/><br/>" + whatsappregards;
			whatsappbody = whatsappbody.replace("<br/>", " ");

			log.info("setMessage--> msg: " + whatsappbody);

			return whatsappbody;
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	private String setFilePath(Map<String, Object> map, Map<String, Object> detail) {
		try {
			String path = map.get("FILE_PATH") == null ? "N" : map.get("FILE_PATH").toString();

			for (Map.Entry<String, Object> entry : detail.entrySet()) {
				if (path.contains(entry.getKey().toString())) {
					path = path.replace("{" + entry.getKey().toString() + "}",
							entry.getValue() == null ? "" : entry.getValue().toString());
				}
			}

			return path;
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	private String callSaveWatiApi(List<WatiApiRequest> request) {
		try {

			String url = cs.getwebserviceurlProperty().getProperty("whatsapp.save.stage.data");
			String auth = cs.getwebserviceurlProperty().getProperty("whatsapp.save.stage.data.auth");

			ResponseEntity<String> resEnt = null;

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();

			headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", auth);

			HttpEntity<List<WatiApiRequest>> entityReq = new HttpEntity<>(request, headers);

			resEnt = restTemplate.postForEntity(url, entityReq, String.class);

			String res = resEnt.getBody();

			log.info("callSaveWatiApi--> response: " + res);

		} catch (HttpClientErrorException e) {
			log.error(e);
			cs.reqPrint(e.getResponseBodyAsString());
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}
*/
}
