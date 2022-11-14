package com.lucidity.fooddelivery.service.process.context;

import com.lucidity.fooddelivery.domain.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Vertex {

    String id;
    Location location;
    @Setter
    int prepTime;

    public Vertex(String id, Location location) {
        this.id = id;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Vertex that = (Vertex) o;

        if(!this.id.equals(that.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
