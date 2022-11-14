package com.lucidity.fooddelivery.domain;

import lombok.*;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;

    private Integer preparationTime; // in minutes

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<FoodOrder> foodOrderList = new LinkedList<>();

    public void addFoodOrder(FoodOrder foodOrder) {
        foodOrderList.add(foodOrder);
    }
}
