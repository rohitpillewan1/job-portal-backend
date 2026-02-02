package com.rohit.job_protal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.rohit.job_protal.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleCompanyAlreadyExist(
            CompanyAlreadyExistException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, ex.getMessage()));
    }
    @ExceptionHandler(SkillAlreadyExist.class)
    public ResponseEntity<ApiResponse> handleSkillAlreadyExist(
            SkillAlreadyExist ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, ex.getMessage()));
    }
    
    @ExceptionHandler(DuplicateJobException.class)
    public ResponseEntity<ApiResponse> handleJobAlreadyExist(
            DuplicateJobException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(false, ex.getMessage()));
    }
    
    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExist(
    		UserAlreadyExist ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(false, ex.getMessage()));
    }
   

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse> handleGenericException(
//            Exception ex) {
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ApiResponse(false, "Something went wrong"));
//    }
    
    @ExceptionHandler(JobNotFound.class)
    public ResponseEntity<ApiResponse> handleJobExsitence(JobNotFound ex){
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,ex.getMessage()));
    }

    @ExceptionHandler(UnauthenticatedUser.class)
    public ResponseEntity<ApiResponse> handleUnAuthenticatedUser(UnauthenticatedUser ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, ex.getBindingResult().getFieldError().getDefaultMessage()));
    }
    
}
