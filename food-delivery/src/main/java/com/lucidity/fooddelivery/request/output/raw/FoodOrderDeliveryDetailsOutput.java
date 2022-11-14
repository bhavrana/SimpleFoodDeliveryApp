package com.lucidity.fooddelivery.request.output.raw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderDeliveryDetailsOutput {

    private List<String> visitSequence;

    private Double expectedTime;
}
