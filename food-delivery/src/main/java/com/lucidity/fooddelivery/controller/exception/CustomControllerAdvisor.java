package com.lucidity.fooddelivery.controller.exception;

import com.lucidity.fooddelivery.exception.FoodDeliveryException;
import com.lucidity.fooddelivery.request.output.decorate.DefaultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvisor {

    @ExceptionHandler(FoodDeliveryException.class)
    public ResponseEntity<DefaultResponse> handleNoOtpException(FoodDeliveryException ex){
        DefaultResponse response = new DefaultResponse(DefaultResponse.Status.FAILED, ex.getMessage());
        return ResponseEntity.ok(response);
    }
}
