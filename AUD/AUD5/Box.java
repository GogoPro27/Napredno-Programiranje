package Napredno_Programiranje.AUD.AUD5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Box<E>{
    private List<E> elements;

    public Box() {
        elements = new ArrayList<E>();
    }

    public void addElement(E element){
        elements.addLast(element);

    }

    public boolean isEmpty(){
        return  elements.isEmpty();
    }

    public E drawItem(){
        Collections.shuffle(elements);
        return elements.getFirst();
    }

}
