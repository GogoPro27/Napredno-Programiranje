package Napredno_Programiranje.LABS.LABS3.LABS3_1;

import java.util.Objects;

public class PizzaItem implements Item {
    private final String typePizza;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if(type.equals("Standard"))typePizza = type;
        else if(type.equals("Pepperoni"))typePizza = type;
        else if(type.equals("Vegetarian"))typePizza = type;
        else throw new InvalidPizzaTypeException("InvalidPizzaTypeException");
    }

    @Override
    public int getPrice() {
        if(typePizza.equals("Standard"))return 10;
        else if(typePizza.equals("Pepperoni"))return 12;
        else return 8;
    }

    public String  getType() {
        return typePizza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaItem pizzaItem = (PizzaItem) o;
        return Objects.equals(typePizza, pizzaItem.typePizza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typePizza);
    }
}
