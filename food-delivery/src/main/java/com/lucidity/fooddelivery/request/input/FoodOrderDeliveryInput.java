package com.lucidity.fooddelivery.request.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;

@Getter
@AllArgsConstructor
public class FoodOrderDeliveryInput {
    private Long deliveryAgentId;
    private HashMap<Long, Long> customerToRestaurantMap = new HashMap<>();
}
