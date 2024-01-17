package com.maan.common.upload.service;
 
import com.maan.common.error.ErrorRes;
import com.maan.common.upload.request.DocumentUploadDetailsReqRes;
import com.maan.common.upload.request.ImageRequest;
import com.maan.common.upload.response.GoogleApiResponse;

public interface DetectionService {

	ErrorRes insimagedetails(DocumentUploadDetailsReqRes request);

	GoogleApiResponse getimageres(ImageRequest request);

}
