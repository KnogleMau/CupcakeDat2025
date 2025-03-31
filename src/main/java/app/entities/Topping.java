package app.entities;

import java.math.BigDecimal;

public class Topping {

    private final int id;
    private final String name;
    private final BigDecimal price;

    public Topping(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
