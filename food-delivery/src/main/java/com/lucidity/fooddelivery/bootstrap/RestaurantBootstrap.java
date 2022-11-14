package com.lucidity.fooddelivery.bootstrap;

import com.lucidity.fooddelivery.domain.Location;
import com.lucidity.fooddelivery.domain.Restaurant;
import com.lucidity.fooddelivery.repository.FoodOrderRepository;
import com.lucidity.fooddelivery.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RestaurantBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RestaurantRepository restaurantRepository;

    public RestaurantBootstrap(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    private List<Restaurant> getRestaurants() {
        Location adigasLocation = new Location();
        adigasLocation.setLatitude(12.9106);
        adigasLocation.setLongitude(77.6326);

        Restaurant adigas = new Restaurant();
        adigas.setLocation(adigasLocation);
        adigas.setRestaurantName("Adigas");
        adigas.setPreparationTime(30);

        Location udupiLocation = new Location();
        udupiLocation.setLatitude(10.4806);
        udupiLocation.setLongitude(74.5316);

        Restaurant udupi = new Restaurant();
        udupi.setLocation(udupiLocation);
        udupi.setRestaurantName("Udupi");
        udupi.setPreparationTime(20);

        Location utscLocation = new Location();
        utscLocation.setLatitude(11.4806);
        utscLocation.setLongitude(75.5316);

        Restaurant utsc = new Restaurant();
        utsc.setLocation(udupiLocation);
        utsc.setRestaurantName("UTSC");
        utsc.setPreparationTime(25);


        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(adigas);
        restaurants.add(udupi);
        restaurants.add(utsc);

        return restaurants;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        restaurantRepository.saveAll(getRestaurants());
    }
}
