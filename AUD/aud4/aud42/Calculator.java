package Napredno_Programiranje.AUD.aud4.aud42;

public class Calculator {

    Strategy strategy;
    protected double rezultat;

    public Calculator() {
        this.rezultat = 0.0;
    }

    public double getRezultat() {
        return rezultat;
    }

    @Override
    public String toString() {
        return String.format("Updated result: %.2f",rezultat);
    }
    public String innit(){
      return String.format(" result = %.2f ", rezultat);
    }
    public String execute(char operation, double value) throws UnknownOperatorException {
        if (operation == '+') {
            strategy = new Addition();
        } else if (operation == '-') {
            strategy = new Subtraction();
        } else if (operation == '*') {
            strategy = new Multiplication();
        } else if (operation == '/') {
            strategy = new Division();
        }
        else throw new UnknownOperatorException(operation);
        rezultat = strategy.calculate(rezultat , value);

        return String.format("result %c %.2f = %.2f ",operation, value ,rezultat);
    }
}
