package com.lucidity.fooddelivery.service.create;

import com.lucidity.fooddelivery.exception.FoodDeliveryException;
import com.lucidity.fooddelivery.request.input.FoodOrderDeliveryInput;
import com.lucidity.fooddelivery.validation.create.FoodOrderCreateValidation;
import org.springframework.stereotype.Component;

@Component
public class FoodOrderCreateHelper {

    private final FoodOrderCreateValidation foodOrderCreateValidation;


    public FoodOrderCreateHelper(FoodOrderCreateValidation foodOrderCreateValidation) {
        this.foodOrderCreateValidation = foodOrderCreateValidation;
    }

    public void foodOrderDeliveryInputValidation(final FoodOrderDeliveryInput foodOrderDeliveryInput) {
        try {
            foodOrderCreateValidation.validateFoodOrderDeliveryInput(foodOrderDeliveryInput);
        } catch (Exception ex) {
            throw new FoodDeliveryException("Exception: " + ex.getMessage(), ex);
        }
    }
}
