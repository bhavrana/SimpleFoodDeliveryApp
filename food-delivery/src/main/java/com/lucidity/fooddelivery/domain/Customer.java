package com.lucidity.fooddelivery.domain;

import lombok.*;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<FoodOrder> foodOrderList = new LinkedList<>();

    public void addFoodOrder(FoodOrder foodOrder) {
        foodOrderList.add(foodOrder);
    }
}
