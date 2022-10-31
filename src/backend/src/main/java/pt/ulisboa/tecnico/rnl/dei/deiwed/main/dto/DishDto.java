package pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto;

import java.io.Serializable;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Dish;

// Data Transfer Object, to communicate with frontend
public class DishDto implements Serializable{
    private long id;
    private String name;
    private double price;
    private String type;

    public DishDto() {
    }

    public DishDto(long id, String name, double price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public DishDto(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice(), dish.getType());
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return String.format(
            "DishDto[id=%d, name='%s', price='%f', type='%s']",
            id, name, price, type);
    }
}


