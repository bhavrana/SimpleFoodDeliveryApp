package com.lucidity.fooddelivery.service.create;

import com.lucidity.fooddelivery.domain.Customer;
import com.lucidity.fooddelivery.domain.DeliveryAgent;
import com.lucidity.fooddelivery.domain.FoodOrder;
import com.lucidity.fooddelivery.domain.Restaurant;
import com.lucidity.fooddelivery.exception.FoodDeliveryException;
import com.lucidity.fooddelivery.repository.CustomerRepository;
import com.lucidity.fooddelivery.repository.DeliveryAgentRepository;
import com.lucidity.fooddelivery.repository.RestaurantRepository;
import com.lucidity.fooddelivery.request.input.FoodOrderDeliveryInput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class FoodOrderCreateService {

    private final FoodOrderCreateHelper foodOrderCreateHelper;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final DeliveryAgentRepository deliveryAgentRepository;

    public FoodOrderCreateService(FoodOrderCreateHelper foodOrderCreateHelper, CustomerRepository customerRepository, RestaurantRepository restaurantRepository, DeliveryAgentRepository deliveryAgentRepository) {
        this.foodOrderCreateHelper = foodOrderCreateHelper;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
    }

    @Transactional
    public List<FoodOrder> createFoodOrder(final FoodOrderDeliveryInput foodOrderDeliveryInput) {
        // validate the food order delivery input request
        try {
            foodOrderCreateHelper.foodOrderDeliveryInputValidation(foodOrderDeliveryInput);
        } catch (FoodDeliveryException ex) {
            throw new FoodDeliveryException("Food order delivery input validation failed :" + ex);
        }

        DeliveryAgent deliveryAgent = deliveryAgentRepository
                .findById(foodOrderDeliveryInput.getDeliveryAgentId()).get();

        List<FoodOrder> foodOrderList = new LinkedList<>();

        for(Map.Entry<Long, Long> entry : foodOrderDeliveryInput.getCustomerToRestaurantMap().entrySet()) {
            FoodOrder foodOrder = new FoodOrder();
            Customer customer = customerRepository.findById(entry.getKey()).get();
            Restaurant restaurant = restaurantRepository.findById(entry.getValue()).get();

            foodOrder.setCustomer(customer);
            foodOrder.setRestaurant(restaurant);
            foodOrder.setDeliveryAgent(deliveryAgent);

            deliveryAgent.addFoodOrder(foodOrder);
            customer.addFoodOrder(foodOrder);
            restaurant.addFoodOrder(foodOrder);

            deliveryAgentRepository.save(deliveryAgent);
            customerRepository.save(customer);
            restaurantRepository.save(restaurant);

            foodOrderList.add(foodOrder);
        }
        return foodOrderList;
    }
}
