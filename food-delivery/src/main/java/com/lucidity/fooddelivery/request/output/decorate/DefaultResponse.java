package com.lucidity.fooddelivery.request.output.decorate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@Setter
public class DefaultResponse {

    private Status status;
    private String message;

    public DefaultResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }
    public enum Status {
        FAILED,
        SUCCESS
    }
}

