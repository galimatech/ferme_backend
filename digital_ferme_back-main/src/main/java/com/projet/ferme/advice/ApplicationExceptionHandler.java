package com.projet.ferme.advice;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.projet.ferme.exception.NoElementAddException;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.entity.utils.ResponseMessage;
import org.springframework.validation.BindException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
    }
    
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(NoElementFoundException.class)
	  public Map<String, String> eltNotFound (NoElementFoundException ex) {
		Map<String, String> errorMap = new HashMap<>(); 
		  errorMap.put("error Message", ex.getMessage());

		  return errorMap;
	  }

      @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ExceptionHandler(NoElementAddException.class)
	  public Map<String, String> eltNorAdd (NoElementAddException ex) {
		Map<String, String> errorMap = new HashMap<>(); 
		  errorMap.put("error Message", ex.getMessage());

		  return errorMap;
	  }
      

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> argumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> erroMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            erroMap.put(error.getField(), error.getDefaultMessage());
        });

        return erroMap;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> notReadableException(HttpMessageNotReadableException ex){
        Map<String, String> erroMap= new HashMap<>();
        erroMap.put(" error message", "Impossible de lire les données");
        return erroMap;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Map<String, String> bindException(BindException ex){
        Map<String, String> errorMap=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return errorMap;

    }

    	
}
