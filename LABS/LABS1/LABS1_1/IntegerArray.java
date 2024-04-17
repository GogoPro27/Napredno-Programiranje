package Napredno_Programiranje.LABS.LABS1.LABS1_1;

import java.util.Arrays;

public final class IntegerArray {
    private int[] array;

    public IntegerArray(int[] a) {
        array = Arrays.copyOf(a,a.length);
    }
    public int length(){
        return array.length;
    }
    public int getElementAt(int i){
        return array[i];
    }
    public int sum(){
        int sum = 0;
        for (int j : array) sum += j;
        return sum;
    }
    public double average(){
        return (double)sum()/array.length;
    }
    public IntegerArray getSorted(){
        int [] newArray = new int[array.length];
        int lenght = array.length;
        for(int i=0;i<lenght;i++){
            newArray[i] = array[i];
        }
        for(int i=0;i<newArray.length;i++){
            for(int j=i+1;j<newArray.length;j++){
                if(newArray[i]>newArray[j]){
                    int temp = newArray[i];
                    newArray[i] = newArray[j];
                    newArray[j] = temp;
                }
            }
        }
        return new IntegerArray(newArray);
    }
    public IntegerArray getSorted2(){
        int[] newArray = Arrays.copyOf(array,array.length);
        Arrays.sort(newArray);
        return new IntegerArray(newArray);
    }
    public IntegerArray concat(IntegerArray ia){
        int newSize = array.length+ia.length();
        int[] newArray = new int[newSize];
        for(int i=0;i<newSize;i++){
            if(i<array.length)newArray[i]=array[i];
            else newArray[i]=ia.array[i-array.length];
        }
        return new IntegerArray(newArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerArray that = (IntegerArray) o;
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}
