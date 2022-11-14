package com.lucidity.fooddelivery.exception;

public class FoodDeliveryException extends RuntimeException {

    public FoodDeliveryException() {
        super();
    }

    public FoodDeliveryException(String message) {
        super(message);
    }

    public FoodDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoodDeliveryException(Throwable cause) {
        super(cause);
    }
}
