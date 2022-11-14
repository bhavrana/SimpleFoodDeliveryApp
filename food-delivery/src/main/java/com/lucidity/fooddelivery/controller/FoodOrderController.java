package com.lucidity.fooddelivery.controller;

import com.lucidity.fooddelivery.request.input.FoodOrderDeliveryInput;
import com.lucidity.fooddelivery.request.output.decorate.FoodOrderDeliveryOutput;
import com.lucidity.fooddelivery.service.FoodOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/deliveries")
@Slf4j
public class FoodOrderController {

    private final FoodOrderService foodOrderService;

    public FoodOrderController(FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> foodOrderDelivery(@RequestBody(required = true) FoodOrderDeliveryInput foodOrderDeliveryInput) {
        FoodOrderDeliveryOutput foodOrderDeliveryOutput = foodOrderService.deliverFoodOrders(foodOrderDeliveryInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(foodOrderDeliveryOutput);
    }
}
