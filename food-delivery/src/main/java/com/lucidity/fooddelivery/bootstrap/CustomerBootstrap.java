package com.lucidity.fooddelivery.bootstrap;

import com.lucidity.fooddelivery.domain.Customer;
import com.lucidity.fooddelivery.domain.FoodOrder;
import com.lucidity.fooddelivery.domain.Location;
import com.lucidity.fooddelivery.repository.CustomerRepository;
import com.lucidity.fooddelivery.repository.FoodOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomerBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CustomerRepository customerRepository;
    private final FoodOrderRepository foodOrderRepository;

    public CustomerBootstrap(CustomerRepository customerRepository, FoodOrderRepository foodOrderRepository) {
        this.customerRepository = customerRepository;
        this.foodOrderRepository = foodOrderRepository;
    }

    public List<Customer> getCustomers() {
        Location surajLocation = new Location();
        surajLocation.setLatitude(11.9146);
        surajLocation.setLongitude(55.7386);

        Customer suraj = new Customer();
        suraj.setLocation(surajLocation);
        suraj.setCustomerName("Suraj");

        Location poojaLocation = new Location();
        poojaLocation.setLatitude(17.9106);
        poojaLocation.setLongitude(89.6326);

        Customer pooja = new Customer();
        pooja.setLocation(poojaLocation);
        pooja.setCustomerName("Pooja");

        List<Customer> customers = new ArrayList<>();
        customers.add(suraj);
        customers.add(pooja);

        return customers;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        customerRepository.saveAll(getCustomers());
    }
}
