package com.lucidity.fooddelivery.bootstrap;

import com.lucidity.fooddelivery.domain.DeliveryAgent;
import com.lucidity.fooddelivery.domain.Location;
import com.lucidity.fooddelivery.repository.DeliveryAgentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DeliveryAgentBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final DeliveryAgentRepository deliveryAgentRepository;

    public DeliveryAgentBootstrap(DeliveryAgentRepository deliveryAgentRepository) {
        this.deliveryAgentRepository = deliveryAgentRepository;
    }

    List<DeliveryAgent> getDeliveryAgents() {
        Location amanLocation = new Location();
        amanLocation.setLatitude(14.9106);
        amanLocation.setLongitude(87.6326);

        DeliveryAgent aman = new DeliveryAgent();
        aman.setLocation(amanLocation);
        aman.setDeliveryAgentName("Aman");
        aman.setAvgSpeed(20);

        Location vijayLocation = new Location();
        vijayLocation.setLatitude(17.9106);
        vijayLocation.setLongitude(89.6326);

        DeliveryAgent vijay = new DeliveryAgent();
        vijay.setLocation(vijayLocation);
        vijay.setDeliveryAgentName("Vijay");
        vijay.setAvgSpeed(25);

        List<DeliveryAgent> deliveryAgents = new ArrayList<>();
        deliveryAgents.add(aman);
        deliveryAgents.add(vijay);

        return deliveryAgents;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        deliveryAgentRepository.saveAll(getDeliveryAgents());
    }
}
