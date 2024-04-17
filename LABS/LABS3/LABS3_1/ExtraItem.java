package Napredno_Programiranje.LABS.LABS3.LABS3_1;

import java.util.Objects;

public class ExtraItem implements Item {
    private final String typeExtra;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if(type.equals("Coke") || type.equals("Ketchup")) typeExtra = type;
        else throw new InvalidExtraTypeException("InvalidExtraTypeException");

    }

    @Override
    public int getPrice() {
        if(typeExtra.equals("Ketchup"))return 3;
        else return 5;
    }

    public String getType() {
        return typeExtra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraItem extraItem = (ExtraItem) o;
        return Objects.equals(typeExtra, extraItem.typeExtra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeExtra);
    }
}
