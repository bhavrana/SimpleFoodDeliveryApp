package com.lucidity.fooddelivery.service;

import com.lucidity.fooddelivery.domain.FoodOrder;
import com.lucidity.fooddelivery.exception.FoodDeliveryException;
import com.lucidity.fooddelivery.request.input.FoodOrderDeliveryInput;
import com.lucidity.fooddelivery.request.output.decorate.FoodOrderDeliveryOutput;
import com.lucidity.fooddelivery.request.output.helper.FoodOrderDeliveryOutputHelper;
import com.lucidity.fooddelivery.service.create.FoodOrderCreateService;
import com.lucidity.fooddelivery.service.process.FoodOrderProcessService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodOrderService {

    private final FoodOrderCreateService foodOrderCreateService;

    private final FoodOrderProcessService foodOrderProcessService;

    private final FoodOrderDeliveryOutputHelper foodOrderDeliveryOutputHelper;

    public FoodOrderService(FoodOrderCreateService foodOrderCreateService, FoodOrderProcessService foodOrderProcessService, FoodOrderDeliveryOutputHelper foodOrderDeliveryOutputHelper) {
        this.foodOrderCreateService = foodOrderCreateService;
        this.foodOrderProcessService = foodOrderProcessService;
        this.foodOrderDeliveryOutputHelper = foodOrderDeliveryOutputHelper;
    }

    public FoodOrderDeliveryOutput deliverFoodOrders(final FoodOrderDeliveryInput foodOrderDeliveryInput) {

        try {
            List<FoodOrder> pendingFoodOrders = foodOrderCreateService.createFoodOrder(foodOrderDeliveryInput);
            Pair<Double, List<String>> result = foodOrderProcessService.getFoodOrderProcessSequence(pendingFoodOrders);
            return foodOrderDeliveryOutputHelper.foodOrderDeliveryOutput(result);
        } catch (FoodDeliveryException ex) {
            throw new FoodDeliveryException("Failed to determine food delivery sequence.", ex);
        }
    }
}
