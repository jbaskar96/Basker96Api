package com.maan.common.upload.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maan.common.bean.DocumentUploadDetails;
import com.maan.common.bean.DocumentUploadDetailsId;
import com.maan.common.error.Error;
import com.maan.common.repository.DocumentUploadDetailsRepository;
import com.maan.common.upload.request.DocumentUploadDetailsDto;
import com.maan.common.upload.request.DocumentUploadRes;
import com.maan.common.upload.request.FilePathNameReq;
import com.maan.common.upload.request.WhatsappReqClaim;
import com.maan.common.upload.response.ClaimDocListRes;
import com.maan.common.upload.response.FileJson;
import com.maan.common.upload.response.FilePathNameRes;
import com.maan.common.upload.response.SucessRes;
import com.maan.common.upload.service.ClaimDocumentMasterService;
import com.maan.common.upload.service.FilesStorageService;
import com.maan.common.upload.service.thread.GetFileFromPath;

import net.coobird.thumbnailator.Thumbnails;
 

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
	
	private Logger log = LogManager.getLogger(getClass());
	
	public static ForkJoinPool forkJoinPool = new ForkJoinPool(5);
	@Autowired
	DocumentServiceImpl documentserviceimpl;
	@Autowired
	DocumentUploadDetailsRepository docDetailRepo;
	/*@Autowired
	private ClaimDetailsRepository claimRepo; */

	@Autowired
	ClaimDocumentMasterService claimdoc;
	
/*	@Autowired
 * 
	ClaimDocumentMasterRepository claimdocmastrepo;
	@Autowired
	private ClaimPartyDetailsRepository claimpartyrepository;

	@Autowired
	private ClaimLosstypeMasterRepository lossMas;
	@Autowired
	private ClaimLossDetailsRepository cldRepo; */
	
	@Value("${file.upload-dir}")
	private String directoryPath;

	@Value("${common.file.path}")
	private String orginalPath;
	
	
	public static  Map<String,String> ALLOWED_CONTENTTYPE;
	  static {
		  ALLOWED_CONTENTTYPE = new HashMap<String, String>();
		  //Image
		  ALLOWED_CONTENTTYPE.put(".bmp","image/bmp");
		  ALLOWED_CONTENTTYPE.put(".jpg","image/jpeg");
		  ALLOWED_CONTENTTYPE.put(".jpeg","image/jpeg");
		  //Doc
		  ALLOWED_CONTENTTYPE.put(".doc","application/msword");
		  ALLOWED_CONTENTTYPE.put(".docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		  ALLOWED_CONTENTTYPE.put(".pdf","application/pdf");		  
		  ALLOWED_CONTENTTYPE.put(".xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		  ALLOWED_CONTENTTYPE.put(".xls","application/vnd.ms-excel");
		  //Vid
		  ALLOWED_CONTENTTYPE.put(".avi","video/x-msvideo");
		  ALLOWED_CONTENTTYPE.put(".3gp","video/3gpp"); 
		  ALLOWED_CONTENTTYPE.put(".mpeg","video/mpeg");
		  ALLOWED_CONTENTTYPE.put(".mp4","video/mp4");
		  ALLOWED_CONTENTTYPE.put(".m4v","video/m4v");
		  ALLOWED_CONTENTTYPE.put(".flv","video/x-flv");
		  ALLOWED_CONTENTTYPE.put(".mp4","video/mp4");
		  ALLOWED_CONTENTTYPE.put(".m3u8","application/x-mpegURL");
		  ALLOWED_CONTENTTYPE.put(".ts","video/MP2T");
		  ALLOWED_CONTENTTYPE.put(".3gp","video/3gpp");
		  ALLOWED_CONTENTTYPE.put(".mov","video/quicktime");
		  ALLOWED_CONTENTTYPE.put(".avi","video/x-msvideo");
		  ALLOWED_CONTENTTYPE.put(".wmv","video/x-ms-wmv");

	  }
	  
	  @Override
		public List<Error> doctypevalidation(MultipartFile file) {

			List<Error> errorList = new ArrayList<Error>();

			boolean containsValue = ALLOWED_CONTENTTYPE.containsValue(file.getContentType());
			if (!containsValue) {
				errorList.add(new Error("01", "ReferenceNo", file.getContentType() + " is Not Allowed"));
			}
			
			long fileSizeInBytes = file.getSize();
			double size_kb = fileSizeInBytes /1024;
			double size_mb = size_kb / 1024;

			if (size_mb > 25) { 
				errorList.add(new Error("01", "FileSize","File Size Must be 25Mb Current file value is" + size_mb + "MB")); 
			}
			return errorList;
	} 
	  
	@Override
	public String save(FileJson filejson) {
		
		String res="";
		MultipartFile file = filejson.getFile();
		try {
			boolean containsValue = ALLOWED_CONTENTTYPE.containsValue(file.getContentType());
			if(!containsValue)
				throw new RuntimeException(file.getContentType()+" is Not Allowed " ) ;
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
		
			Random random = new Random();
			
			//CompressImage
			Path destination = Paths.get(directoryPath) ; //this.root.resolve(file.getOriginalFilename())
			String newfilename= random.nextInt(100) + generateFileName()+"."+FilenameUtils.getExtension(file.getOriginalFilename());
			Files.copy(file.getInputStream(),destination.resolve(newfilename));
			
			//OrginalImg
			String fileextension = FilenameUtils.getExtension(file.getOriginalFilename());
			String newfilename1= "";
			
			System.out.println(fileextension);
			
			if(fileextension.equals("bmp") || fileextension.equals("jpg") || fileextension.equals("jpeg")) {
				newfilename1= orginalPath + random.nextInt(100) + generateFileName()+"."+FilenameUtils.getExtension(file.getOriginalFilename());
				File file1 = new File(directoryPath+newfilename);
				CompressImage(file1,newfilename1);
			}else {
				newfilename1 = directoryPath+newfilename;
			}
			
			filejson.getDocUploadDetails().getDocumentupldet().get(0).setFile_name(file.getOriginalFilename());
			filejson.getDocUploadDetails().getDocumentupldet().get(0).setParam2(newfilename);
			filejson.getDocUploadDetails().getDocumentupldet().get(0).setFile_path_name(directoryPath+newfilename);
			
			
			res=documentserviceimpl.uploadingdocdet(filejson.getDocUploadDetails(),newfilename1);
			
		} catch (Exception e) {
			res= null;
			e.printStackTrace();
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return res;
	}
	private String generateFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhmmssSSSSSSa");
		Calendar cal = Calendar.getInstance();
		String date = sdf.format(cal.getTime());	
		return date;
	}
	@Override
	public Resource load(String filename) {
		try {
			Path file =Paths.get(directoryPath) ;
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	/*
	 * @Override public void deleteAll() {
	 * FileSystemUtils.deleteRecursively(root.toFile()); }
	 */

	@Override
	public Stream<Path> loadAll(String claimNo) {
		try{
		/*	List<DocumentUploadDetails> list = docDetailRepo.findByClaimNo(claimNo);
			List<Callable<Object>> queue = new ArrayList<Callable<Object>>();
 			for (int i = 0; i < list.size(); i++) {
				String filepathName=list.get(i).getFilePathName();
				GetFileFromPath path=new GetFileFromPath(filepathName);
				queue.add(path);
			}
			
			MyTaskList taskList = new MyTaskList(queue);
			ConcurrentLinkedQueue<Future<Object>> invoke = (ConcurrentLinkedQueue<Future<Object>>) forkJoinPool
					.invoke(taskList);
			
			int success = 0;
			for (Future<Object> callable : invoke) {
				System.out.println(callable.getClass() + "," + callable.isDone());
				if (callable.isDone()) {
					try {
						File object = (File) callable.get();
						
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			} */
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public FilePathNameRes getFilePath(FilePathNameReq req) {
		FilePathNameRes res = new FilePathNameRes();
		try {
			
			DocumentUploadDetails getFile = docDetailRepo.findByClaimNoAndDocTypeIdAndDocumentRefAndInsIdAndProductId(req.getClaimnno(),req.getDoctypeid(),new BigDecimal(req.getReqrefno()),new BigDecimal( req.getInsid()),new BigDecimal(req.getProductid())).get();
			ModelMapper mapper = new ModelMapper();
			res = mapper.map(getFile, FilePathNameRes.class);
				if(StringUtils.isNotBlank(res.getFilepathname()) && new File(res.getFilepathname()).exists()) {
						res.setImgurl(new GetFileFromPath(res.getCommonfilepath()).call().getImgUrl());
						res.setDocrefno(getFile.getDocumentRef().toString());
				}else
					 System.out.println("File is Not found!!"+res.getFilepathname());
			} catch (Exception e) {
				e.printStackTrace();
				//Log.info("Exception Is --->" + e.getMessage());
				return null;
			}
		return res;
	}

	@Override
	public List<ClaimDocListRes> getdoclist(DocumentUploadDetailsDto req) {
		
		List<ClaimDocListRes> resList = new ArrayList<ClaimDocListRes>();
		
		try {
			List<DocumentUploadDetails> list = docDetailRepo.findByClaimNo(req.getClaimNo());
			for(int i=0;i<list.size();i++) {
				ClaimDocListRes res = new ClaimDocListRes();
				DocumentUploadDetails ent = list.get(i);
				
				res.setAmendId(ent.getAmendId().toString());
				res.setCompanyId(ent.getInsId().toString());
				res.setDocumentId(ent.getDocId());
				res.setProductId(ent.getProductId().toString());
				res.setStatus(ent.getStatus());
				res.setDocumentDesc(ent.getDescription());			
				res.setDocApplicable(ent.getUploadType());
				res.setEntryDate(ent.getUploadedTime()==null?"":ent.getUploadedTime().toString());	
				res.setFilePathName(ent.getFilePathName());
				
				res.setClaimNo(ent.getClaimNo());
				res.setDocumentRef(ent.getDocumentRef()==null?"":ent.getDocumentRef().toString());
				res.setDocTypeId(ent.getDocTypeId().toString());
				res.setFileName(ent.getFileName());
				res.setPartyno(ent.getPartyNo());
				res.setLossid(ent.getLossId());
				res.setWaiveoffyn(ent.getWaiveoffYn());
				res.setWaiveoffby(ent.getWaiveoffby());
				res.setWaiveoffdate(ent.getWaiveoffdate());
				res.setRemarks(ent.getRemarks());
				res.setParam(ent.getParam());
				res.setWaiveoffclaimremarks(ent.getWaiveoffclaimremarks());
				resList.add(res);
				
			}
			
        } catch (Exception ex) {
			ex.printStackTrace();
			resList=null;
        }
        return resList;			
	}
	
	@Override
	@Transactional
	public DocumentUploadRes deletedoc(DocumentUploadDetailsDto req) {
		
		DocumentUploadRes res = new DocumentUploadRes();		
		try {
			
		//	docDetailRepo.deleteByClaimNoAndDocumentRefAndDocTypeIdAndFilePathName(req.getClaimNo(), req.getDocumentRef(), new BigDecimal(req.getDocTypeId()), req.getFilePathName());
			res.setMasseage("Deleted Sucessfully");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			res=null;
        }
        return res;			
	}
	@Override
	public SucessRes wayoff(DocumentUploadDetailsDto req) {

		
		SucessRes res = new SucessRes();		
		try {
			
			DocumentUploadDetailsId id = new DocumentUploadDetailsId();
			id.setClaimNo(req.getClaimNo());
			id.setDocTypeId(req.getDocTypeId());
			id.setDocumentRef(req.getDocumentRef());
			id.setInsId(req.getInsId());
			id.setParam(req.getParam());
			id.setProductId(req.getProductId());
			
		/*	Optional<DocumentUploadDetails> ent = docDetailRepo.findById(id);
			
			if(ent.isPresent()) {
				ent.get().setWaiveoffYn(req.getWaiveoffyn());
				ent.get().setWaiveoffby(req.getWaiveoffby());
				ent.get().setRemarks(req.getRemarks());
				ent.get().setWaiveoffclaimremarks(req.getWaiveoffclaimremarks());
				if(req.getWaiveoffby()!=null)
					ent.get().setWaiveoffdate(new Date());
				docDetailRepo.save(ent.get()); 
				res.setResponse("waiveoff Approved");
			}else {
				
				DocumentUploadDetails docupdetail = new DocumentUploadDetails();
				
				Random random = new Random();
		        String uuidAsString = String.format("%04d", random.nextInt(10000));

		        docupdetail.setClaimNo(req.getClaimNo());
		        docupdetail.setDocTypeId(new BigDecimal(req.getDocTypeId()));
		        docupdetail.setDocumentRef(new BigDecimal(uuidAsString));
		        docupdetail.setInsId(req.getInsId());
		        docupdetail.setProductId(req.getProductId());
		        docupdetail.setStatus("Y");
		        docupdetail.setDescription(req.getDescription());
		        docupdetail.setAmendId(new BigDecimal("0"));
		        docupdetail.setDocId(req.getDocTypeId());;
		        
		        docupdetail.setParam("WAIVEOFF");
		        docupdetail.setWaiveoffYn(req.getWaiveoffyn());
		        docupdetail.setWaiveoffby(req.getWaiveoffby());
		        docupdetail.setRemarks(req.getRemarks());		        
		        docupdetail.setPartyNo(req.getPartyNo());
		        docupdetail.setLossId(req.getLossId());
		        docupdetail.setWaiveoffclaimremarks(req.getWaiveoffclaimremarks());
		        
		        docDetailRepo.save(docupdetail);
		        
		        res.setResponse("waiveoff Submitted");
			}
			*/

			
		} catch (Exception ex) {
			ex.printStackTrace();
			res=null;
        }
        return res;
		
	}
	
	public List<com.maan.common.error.Error> waiveOffValidation(DocumentUploadDetailsDto req) {
		try {
			List<com.maan.common.error.Error> error = new ArrayList<com.maan.common.error.Error>();

			// Remarks

			if (req.getUsertype().equalsIgnoreCase("claimofficer")) {
				if (req.getWaiveoffclaimremarks() == null || StringUtils.isBlank(req.getWaiveoffclaimremarks())) {
					error.add(new com.maan.common.error.Error(req.getDocTypeId(), "DocumentId","Please Add ClaimRemarks for " + req.getDescription()));
				}
				if (req.getWaiveoffyn() == null || StringUtils.isBlank(req.getWaiveoffyn())) {
					error.add(new com.maan.common.error.Error(req.getDocTypeId().toString(), "DocumentId","Please Add Action for " + req.getDescription()));
				}

			}

			/*if (req.getUsertype().equalsIgnoreCase("surveyor")) {
				if (StringUtils.isBlank(req.getRemarks()) || req.getRemarks() == null || "".equals(req.getRemarks())
						|| "null".equalsIgnoreCase(req.getRemarks())) {
					error.add(new com.maan.common.error.Error("01", "Remarks", "Please Enter Remarks"));
				}
			}*/

			return error;
		} catch (Exception e) {
		
		}
		return null;
	}
	@Override
	public List<Error> whatsappclaimandpartyandlossval(WhatsappReqClaim req) {

		String claimno = req.getClaimrefno();
	/*	ClaimDetails claim = new ClaimDetails();
		List<ClaimLossDetails> loss = new ArrayList<ClaimLossDetails>();
		ClaimPartyDetails party = new ClaimPartyDetails(); */
		List<Error> list = new ArrayList<Error>();
		
		try {

		if (StringUtils.isBlank(req.getClaimrefno()) || req.getClaimrefno() == null) {
			list.add(new Error("01", "Claimrefno", "Please Enter ClaimNo"));
		} else if (claimno.length() != 11 || !claimno.substring(0, 3).equals("CI-")
				|| !StringUtils.isNumeric(claimno.substring(4, 11))) {
			list.add(new Error("02", "Claimrefno", "Please Enter Correct pattern of ClaimNo"));
		} else {
		/*	claim = claimRepo.findByClaimRefNo(req.getClaimrefno());
			if (claim == null) {
				list.add(new Error("03", "Claimrefno", "Please type valid claimnumber"));
			} */
		}

		// Party
	/*	if(claim.getClaimNo()!=null) {
		if (req.getPartyno() == null || StringUtils.isBlank(req.getPartyno().toString())) {
			list.add(new Error("04", "Partyno", "Please enter partyNumber"));
		} else {
		/*	party = claimpartyrepository.findByClaimNoAndPartyNo(claim.getClaimNo(), req.getPartyno());
			if (party == null) {
				list.add(new Error("05", "Partyno", "Please choose valid party"));
			} */
		//}} 

		// loss
	/*	if(party.getClaimNo()!=null) {
		if (req.getLosstypeid() == null || StringUtils.isBlank(req.getLosstypeid().toString())) {
			list.add(new Error("06", "Losstypeid", "Please Enter Losstypeid"));
		} else {
			loss = cldRepo.findByClaimNoAndPartyNoAndLosstypeid(claim.getClaimNo(), req.getPartyno(),req.getLosstypeid());
			if (loss.size()==0) {
				list.add(new Error("07", "Losstypeid", "Please choose valid losstype"));
			}
		}} */
		
		//Doctypeid
		
		/*if (loss.size() != 0) {
			if (req.getDoctypeid() == null || StringUtils.isBlank(req.getDoctypeid().toString())) {
				list.add(new Error("08", "Partyno", "Please Enter Doctypeid"));
			} else {

				boolean flag = false;
				ClaimLosstypeMaster lossmas = lossMas.findByCategoryId(req.getLosstypeid());
				if (lossmas.getPartOfLoss() != null) {
					List<String> docid = new ArrayList<String>(Arrays.asList(lossmas.getPartOfLoss().split(",")));
					for (int i = 0; i < docid.size(); i++) {

						if (req.getDoctypeid().equals(new BigDecimal(docid.get(i)))) {
							flag = true;
						}
					}
				}
				if (!flag)
					list.add(new Error("09", "Partyno", "Please choose vaild details"));
			}
		}
	*/
	} catch (Exception e) {
		e.printStackTrace();
	}
		return list;

	}
	
	public void CompressImage(File uploadFile, String documentPath) {

		try {
			String extension = FilenameUtils.getExtension(documentPath);
			File jpgoutput = new File("thumbnail." + extension);
			BufferedImage originalImage = ImageIO.read(uploadFile);
			Thumbnails.of(originalImage).size(750, 750).outputFormat(extension).toFile(jpgoutput);
			FileUtils.copyFile(jpgoutput, new File(documentPath));
			if(jpgoutput.exists()) {
				log.info("Thumbnail File Deleted after conversion");
				FileUtils.deleteQuietly(jpgoutput);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				FileUtils.copyFile(uploadFile, new File(documentPath));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
