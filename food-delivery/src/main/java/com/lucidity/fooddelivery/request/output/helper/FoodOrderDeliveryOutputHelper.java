package com.lucidity.fooddelivery.request.output.helper;

import com.lucidity.fooddelivery.request.output.decorate.FoodOrderDeliveryOutput;
import com.lucidity.fooddelivery.request.output.raw.FoodOrderDeliveryDetailsOutput;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import java.util.List;

@NoArgsConstructor
@Component
public class FoodOrderDeliveryOutputHelper {
    public FoodOrderDeliveryOutput foodOrderDeliveryOutput(Pair<Double, List<String>> result) {
        FoodOrderDeliveryDetailsOutput foodOrderDeliveryDetailsOutput = new FoodOrderDeliveryDetailsOutput(result.getSecond(), result.getFirst());
        FoodOrderDeliveryOutput foodOrderDeliveryOutput = new FoodOrderDeliveryOutput();
        foodOrderDeliveryOutput.setFoodOrderDeliveryDetailsOutput(foodOrderDeliveryDetailsOutput);
        return foodOrderDeliveryOutput;
    }
}
