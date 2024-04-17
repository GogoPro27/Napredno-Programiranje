package Napredno_Programiranje.FirstPartialExercises.MojDDV_15;

public class AmountNotAllowedException extends Exception{
    private int sum;

    public AmountNotAllowedException(int sum) {
        this.sum = sum;
    }
    void message(){
        System.out.println("Receipt with amount "+ sum + " is not allowed to be scanned");
    }
}
