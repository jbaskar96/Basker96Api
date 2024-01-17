package com.maan.common.error;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler{

	private ResponseEntity<Object> entity=null;
	@ExceptionHandler(CommonValidationException.class)
	protected ResponseEntity<Object> handleUserNotFoundException(CommonValidationException ex,HttpServletResponse response) {
		
		ResponseWrapper responseWrapper = new ResponseWrapper(response);
		responseWrapper.getContent();
		Map<String,Object> hresponse=new HashMap<String, Object>();
	//	hresponse.put("Request", ex.getRequest());
		hresponse.put("Errors", ex.getErrors());
		//hresponse.put("Request", responseWrapper.getContent());
		entity=new ResponseEntity<Object>(hresponse,HttpStatus.OK);
		return entity;
		
	}
}
