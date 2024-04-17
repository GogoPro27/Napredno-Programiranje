package Napredno_Programiranje.FirstPartialExercises.minmax_5;

import java.util.ArrayList;
import java.util.List;

public class MinMax <T extends Comparable<T>>{
    private T min;
    private T max;
    private List<T>list ;

    public MinMax() {
        list = new ArrayList<>();
    }
    public void update(T element) {
        if (list.isEmpty()) {
            min = element;
            max = element;
        } else {
            if (element.compareTo(max) > 0) {
                max = element;
            } else if (element.compareTo(min) < 0) {
                min = element;
            }
        }
        list.add(element);
    }
    public T max(){
        return max;
    }
    public T min(){
        return min;
    }
    public long differentThanMinMax(){
        return list.stream().filter(t->!t.equals(min)&&!t.equals(max)).count();
    }
    @Override
    public String toString() {
        return min + " "+max + " "+differentThanMinMax()+"\n";
    }
}
