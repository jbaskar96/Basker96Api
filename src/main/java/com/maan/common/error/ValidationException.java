package com.maan.common.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ValidationException extends Exception{
	
private static final long serialVersionUID = 1L;
	
	private List<ErrorCheck> errors;
	private String policyno;
	private String chassisno;
	
	public ValidationException(ErrorCheck error) {		
			setErrors(new ArrayList<ErrorCheck>());
			this.getErrors().add(error);
	}
	
	public ValidationException(List<ErrorCheck> error) {
			this.setErrors(error);
			
	}

	/*public MotorValidationException(List<Error> error,String requestReferenceNo) {
		this.setErrors(error);
		this.requestReferenceNo=requestReferenceNo;
	}*/
	
	public List<ErrorCheck> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorCheck> error) {
		this.errors = error;
	}

}
