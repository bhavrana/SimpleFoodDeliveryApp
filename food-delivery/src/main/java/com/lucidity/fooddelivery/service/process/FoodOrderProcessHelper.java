package com.lucidity.fooddelivery.service.process;

import com.lucidity.fooddelivery.domain.FoodOrder;
import com.lucidity.fooddelivery.domain.Location;
import com.lucidity.fooddelivery.exception.FoodDeliveryException;
import com.lucidity.fooddelivery.service.process.context.Vertex;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class FoodOrderProcessHelper {

    public double getDistance(Location location1, Location location2)
    {
        double lat1 = location1.getLatitude();
        double lon1 = location1.getLongitude();
        double lat2 = location2.getLatitude();
        double lon2 = location2.getLongitude();

        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    public double getTime(double distance, int speed) {
        return distance/speed * 60;
    }

    public HashMap<Vertex, List<Pair<Double, Vertex>>> getAdjList(List<FoodOrder> pendingFoodOrders) {

        double distance, time;
        HashMap<Vertex, List<Pair<Double, Vertex>>> adjList = new HashMap<>();

        // Add the delivery person adj list
        Vertex deliveryAgentVertex = new Vertex("D" + pendingFoodOrders.get(0).getDeliveryAgent().getId(),
                pendingFoodOrders.get(0).getDeliveryAgent().getLocation());
        deliveryAgentVertex.setPrepTime(0);
        adjList.put(deliveryAgentVertex, new ArrayList<>());

        for(int i = 0; i < pendingFoodOrders.size(); i++) {

            Vertex restaurantVertex = new Vertex("R" + pendingFoodOrders.get(i).getRestaurant().getId(),
                    pendingFoodOrders.get(i).getRestaurant().getLocation());
            restaurantVertex.setPrepTime(pendingFoodOrders.get(i).getRestaurant().getPreparationTime());
            distance = getDistance(deliveryAgentVertex.getLocation(), restaurantVertex.getLocation());
            time = getTime(distance, pendingFoodOrders.get(i).getDeliveryAgent().getAvgSpeed());
            adjList.get(deliveryAgentVertex).add(Pair.of(time, restaurantVertex));
        }

        // Add the restaurant adj list
        for(int i = 0; i < pendingFoodOrders.size(); i++) {
            Vertex restaurantVertex = new Vertex("R" + pendingFoodOrders.get(i).getRestaurant().getId(),
                    pendingFoodOrders.get(i).getRestaurant().getLocation());
            restaurantVertex.setPrepTime(pendingFoodOrders.get(i).getRestaurant().getPreparationTime());
            adjList.put(restaurantVertex, new ArrayList<>());
            for(int j = 0; j < pendingFoodOrders.size(); j++) {
                // for adding the adjacent restaurants
                if(!restaurantVertex.getId().contains(String.valueOf(pendingFoodOrders.get(j).getRestaurant().getId()))) {
                    Vertex rVertex = new Vertex("R" + pendingFoodOrders.get(j).getRestaurant().getId(),
                            pendingFoodOrders.get(j).getRestaurant().getLocation());
                    rVertex.setPrepTime(pendingFoodOrders.get(j).getRestaurant().getPreparationTime());
                    distance = getDistance(restaurantVertex.getLocation(), rVertex.getLocation());
                    time = getTime(distance, pendingFoodOrders.get(j).getDeliveryAgent().getAvgSpeed());

                    adjList.get(restaurantVertex).add(Pair.of(time, rVertex));
                }
                // for adding the adjacent cutomers
                Vertex cVertex = new Vertex("C" + pendingFoodOrders.get(j).getCustomer().getId(),
                        pendingFoodOrders.get(j).getCustomer().getLocation());
                cVertex.setPrepTime(0);

                distance = getDistance(restaurantVertex.getLocation(), cVertex.getLocation());
                time = getTime(distance, pendingFoodOrders.get(j).getDeliveryAgent().getAvgSpeed());

                adjList.get(restaurantVertex).add(Pair.of(time, cVertex));
            }
        }
        // Add customer adjacency list
        for(int i = 0; i < pendingFoodOrders.size(); i++) {
            Vertex customerVertex = new Vertex("C" + pendingFoodOrders.get(i).getCustomer().getId(),
                    pendingFoodOrders.get(i).getCustomer().getLocation());
            adjList.put(customerVertex, new ArrayList<>());
            for(int j = 0; j < pendingFoodOrders.size(); j++) {
                // for adding the adjacent customers
                if(!customerVertex.getId().contains(String.valueOf(pendingFoodOrders.get(j).getCustomer().getId()))) {

                    Vertex cVertex = new Vertex("C" + pendingFoodOrders.get(j).getCustomer().getId(),
                            pendingFoodOrders.get(j).getCustomer().getLocation());
                    cVertex.setPrepTime(0);
                    distance = getDistance(customerVertex.getLocation(), cVertex.getLocation());
                    time = getTime(distance, pendingFoodOrders.get(j).getDeliveryAgent().getAvgSpeed());

                    adjList.get(customerVertex).add(Pair.of(time, cVertex));

                    // for adding the adjacent restaurants
                    Vertex rVertex = new Vertex("R" + pendingFoodOrders.get(j).getRestaurant().getId(),
                            pendingFoodOrders.get(j).getRestaurant().getLocation());
                    rVertex.setPrepTime(pendingFoodOrders.get(j).getRestaurant().getPreparationTime());

                    distance = getDistance(customerVertex.getLocation(), rVertex.getLocation());
                    time = getTime(distance, pendingFoodOrders.get(j).getDeliveryAgent().getAvgSpeed());

                    adjList.get(customerVertex).add(Pair.of(time, rVertex));
                }
            }
        }

        return adjList;
    }

    public HashMap<String, String> getCustomerRestaurantMap(List<FoodOrder> pendingFoodOrders) {
        HashMap<String, String> map = new HashMap<>();

        for(FoodOrder order : pendingFoodOrders) {
            map.put("C" + order.getCustomer().getId(), "R" + order.getRestaurant().getId());
        }
        return map;
    }

    public void processOrderSequenceAndTime(HashMap<Vertex, List<Pair<Double, Vertex>>> adjList, HashSet<Vertex> visited, Vertex cur, String destVertex,
                    HashMap<String, String> customerRestaurantMap, PriorityQueue<Pair<Double, List<String>>> pq,
                    Double totalTime, List<String> visitSeq) {

        try {
            if (cur.getId().contains("C")) {
                // check for the dependency
                String restaurantId = customerRestaurantMap.get(cur.getId());
                boolean flag = false;
                if (!visited.isEmpty()) {
                    for (Vertex v : visited) {
                        if (v.getId().equals(restaurantId)) {
                            flag = true;
                        }
                    }
                }
                if (!flag) {
                    return;
                }
            }

            visited.add(cur);

            if (cur.getId().equals(destVertex) && visited.size() < adjList.size()) {
                visited.remove(cur);
                return;
            }

            if (visited.size() == adjList.size() && cur.getId().equals(destVertex)) {
                List<String> seqList = new ArrayList<>(visitSeq);
                pq.add(Pair.of(totalTime, seqList));
                visited.remove(cur);
                return;
            }

            List<Pair<Double, Vertex>> list = adjList.get(cur);

            for (int i = 0; i < list.size(); i++) {
                Vertex v = list.get(i).getSecond();
                Double time = list.get(i).getFirst();
                Double newTotalTime = 0.0;
                if (!visited.contains(v)) {
                    if (time + totalTime < v.getPrepTime()) {
                        Double remainingTime = v.getPrepTime() - (time + totalTime);
                        newTotalTime = totalTime + time + remainingTime;
                    } else {
                        newTotalTime = totalTime + time;
                    }
                    visitSeq.add(v.getId());
                    processOrderSequenceAndTime(adjList, visited, v, destVertex, customerRestaurantMap, pq, newTotalTime, visitSeq);
                    visitSeq.remove(v.getId());
                }
            }
            visited.remove(cur);
        } catch (Exception ex) {
            throw new FoodDeliveryException("Failed to get the delivery sequence.", ex);
        }
    }
}
