package com.lucidity.fooddelivery.request.output.decorate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucidity.fooddelivery.request.output.raw.FoodOrderDeliveryDetailsOutput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderDeliveryOutput {

    @JsonProperty("FoodDelivery")
    private FoodOrderDeliveryDetailsOutput foodOrderDeliveryDetailsOutput;
}
