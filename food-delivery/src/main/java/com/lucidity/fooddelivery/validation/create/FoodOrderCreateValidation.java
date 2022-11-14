package com.lucidity.fooddelivery.validation.create;

import com.lucidity.fooddelivery.exception.FoodDeliveryException;
import com.lucidity.fooddelivery.repository.CustomerRepository;
import com.lucidity.fooddelivery.repository.DeliveryAgentRepository;
import com.lucidity.fooddelivery.repository.RestaurantRepository;
import com.lucidity.fooddelivery.request.input.FoodOrderDeliveryInput;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class FoodOrderCreateValidation {

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final DeliveryAgentRepository deliveryAgentRepository;


    public FoodOrderCreateValidation(CustomerRepository customerRepository, RestaurantRepository restaurantRepository,
                                     DeliveryAgentRepository deliveryAgentRepository) {
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
    }

    public void validateFoodOrderDeliveryInput(final FoodOrderDeliveryInput foodOrderDeliveryInput) {
        if(foodOrderDeliveryInput == null) {
            throw new FoodDeliveryException("Exception : Received food order delivery input request is null");
        }

        if(!deliveryAgentRepository.findById(foodOrderDeliveryInput.getDeliveryAgentId()).isPresent()) {
            throw new FoodDeliveryException("Exception : Delivery agent ID not found : " + foodOrderDeliveryInput.getDeliveryAgentId());
        }

        HashMap<Long, Long> map = foodOrderDeliveryInput.getCustomerToRestaurantMap();

        if(map == null || map.isEmpty()) {
            throw new FoodDeliveryException("Exception : Received food order delivery input request without customers and restaurants details");
        }

        for(Map.Entry<Long, Long> entry : map.entrySet()) {
            if(!customerRepository.findById(entry.getKey()).isPresent()) {
                throw new FoodDeliveryException("Exception : Customer ID : " + entry.getKey() + " not found for restaurant ID : " + entry.getValue());
            }
            if(!restaurantRepository.findById(entry.getValue()).isPresent()) {
                throw new FoodDeliveryException("Exception : Restaurant ID : " + entry.getValue() + " not found for customer ID : " + entry.getKey());
            }
        }
    }
}
