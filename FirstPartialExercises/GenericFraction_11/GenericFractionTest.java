package Napredno_Programiranje.FirstPartialExercises.GenericFraction_11;

import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}
class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException(String message) {
        super(message);
    }
}
// вашиот код овде
class GenericFraction <T extends Number,U extends Number>{

    private T numerator;
    private U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        if(denominator.equals(0))throw new ZeroDenominatorException("Denominator cannot be zero");
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        int pomnozenOvoj,pomnozenDrugiot;
        int zaednickiImenitel = findNewDenominatot(denominator.intValue(),gf.denominator.intValue());
        pomnozenOvoj = zaednickiImenitel / denominator.intValue();
        pomnozenDrugiot = zaednickiImenitel / gf.denominator.intValue();

        Double novNum = numerator.doubleValue()*pomnozenOvoj + gf.numerator.doubleValue()*pomnozenDrugiot;
        Double novDem = Double.valueOf(zaednickiImenitel);
        for (int i = (int) Math.max(novDem,novNum); i >=2 ; i--) {
            if(novDem%i==0 && novNum%i==0){
                novDem/=i;
                novNum/=i;
            }
        }
        return new GenericFraction<Double,Double>(novNum,novDem);
//        return new GenericFraction<Double,Double>(numerator.doubleValue()*pomnozenOvoj + gf.numerator.doubleValue()*pomnozenDrugiot, Double.valueOf(zaednickiImenitel));
    }
    public int findNewDenominatot(int num,int dem){
        int newDem = Math.max(num,dem);
        for (int i = newDem;  i <= num*dem; i++) {
                if(i%num==0&&i%dem==0){
                    newDem = i;
                    return newDem;
                }
        }
        return newDem;
    }
    public double toDouble(){
        return numerator.doubleValue() / denominator.doubleValue();
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f",numerator.doubleValue(),denominator.doubleValue());
    }
}
