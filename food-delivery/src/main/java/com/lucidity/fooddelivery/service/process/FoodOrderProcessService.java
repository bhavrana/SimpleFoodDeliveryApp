package com.lucidity.fooddelivery.service.process;

import com.lucidity.fooddelivery.domain.DeliveryAgent;
import com.lucidity.fooddelivery.domain.FoodOrder;
import com.lucidity.fooddelivery.exception.FoodDeliveryException;
import com.lucidity.fooddelivery.service.process.context.Vertex;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FoodOrderProcessService {

    private final FoodOrderProcessHelper foodOrderProcessHelper;

    public FoodOrderProcessService(FoodOrderProcessHelper foodOrderProcessHelper) {
        this.foodOrderProcessHelper = foodOrderProcessHelper;
    }

    public Pair<Double, List<String>> getFoodOrderProcessSequence(List<FoodOrder> pendingFoodOrders) {
        try {
            // Get the adjacency list for the restaurants, customers and delivery agent as vertexes
            HashMap<Vertex, List<Pair<Double, Vertex>>> adjList = foodOrderProcessHelper.getAdjList(pendingFoodOrders);
            // Restaurant and customer relationship map
            HashMap<String, String> customerRestaurantMap = foodOrderProcessHelper.getCustomerRestaurantMap(pendingFoodOrders);

            PriorityQueue<Pair<Double, List<String>>> pq = new PriorityQueue<>((a, b) -> (int) (a.getFirst() - b.getFirst()));
            DeliveryAgent deliveryAgent = pendingFoodOrders.get(0).getDeliveryAgent();
            Vertex graphRoot = new Vertex("D" + deliveryAgent.getId(), deliveryAgent.getLocation());
            HashSet<Vertex> visited = new HashSet<>();
            List<String> visitSeq = new ArrayList<>();

            // Get the sequence for the order deliveries and total time
            for (String customer : customerRestaurantMap.keySet()) {
                visited.clear();
                visitSeq.clear();
                visitSeq.add(graphRoot.getId());
                foodOrderProcessHelper.processOrderSequenceAndTime(adjList, visited, graphRoot, customer,
                        customerRestaurantMap, pq, 0.0, visitSeq);
            }
            return pq.peek();
        } catch (Exception ex) {
            throw new FoodDeliveryException("Exception: Failed to get food order process sequence : " + ex.getMessage(), ex);
        }
    }
}
