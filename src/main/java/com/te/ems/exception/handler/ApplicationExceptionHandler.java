package com.te.ems.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.te.ems.exception.AddressTypeNotProvidedException;
import com.te.ems.exception.EmployeeNotFoundException;
import com.te.ems.response.ErrorResponse;

@RestControllerAdvice
public record ApplicationExceptionHandler() {
	
	@ExceptionHandler(value = {EmployeeNotFoundException.class})
	public ResponseEntity<ErrorResponse> handler(EmployeeNotFoundException e) {
		return ResponseEntity.<ErrorResponse>ofNullable(ErrorResponse.builder()
				.error(e.getMessage())
				.status(HttpStatus.NOT_FOUND)
				.timestamp(LocalDateTime.now())
				.build());
	}
	
	@ExceptionHandler(value = {AddressTypeNotProvidedException.class})
	public ResponseEntity<ErrorResponse> handler(AddressTypeNotProvidedException e) {
		return ResponseEntity.<ErrorResponse>ofNullable(ErrorResponse.builder()
				.error(e.getMessage())
				.status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now())
				.build());
	}

}