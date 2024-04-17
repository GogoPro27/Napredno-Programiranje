package Napredno_Programiranje.AUD.aud3.calculatorStrategy;

import java.util.Scanner;

public class CalculatorTest {

    private static char getFirstCharacter(String string) {
        return string.trim().toLowerCase().charAt(0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Calculator calculator = new Calculator();
            calculator.init();
            while (true) {
                String line = scanner.nextLine();
                if (getFirstCharacter(line) == 'r')
                    break;

                String[] parts = line.split("\s++");
                try {
                    calculator.execute(parts[0].charAt(0), Double.parseDouble(parts[1]));
                    System.out.println(calculator);
                } catch (OperationNotSupported exception) {
                    System.out.println(exception.getMessage());
                }
            }

            System.out.println(calculator);
            System.out.println("Again ? (Y/N)");
            String line = scanner.nextLine();
            if (getFirstCharacter(line) == 'n')
                break;
        }

    }
}

class Calculator {

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';
    private double state;
    private Strategy strategy;

    public Calculator() {
        state = 0.0;
        strategy = new Addition();
    }

    public String execute(char operator, double value) throws OperationNotSupported {
        if (operator == PLUS) {
            strategy = new Addition();
        } else if (operator == MINUS) {
            strategy = new Subtraction();
        } else if (operator == MULTIPLY) {
            strategy = new Multiplication();
        } else if (operator == DIVIDE) {
            strategy = new Division();
        } else {
            throw new OperationNotSupported(operator);
        }

        state = strategy.execute(state, value);
        return String.format("result %c %f = %f", operator, value, state);
    }

    public void init() {
        System.out.println("Calculator is ON.");
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("result = %.2f", state);
    }
}
interface Strategy {
    double execute(double num1, double num2);
}

class Addition implements Strategy{
    @Override
    public double execute(double num1, double num2) {
        return num1 + num2;
    }
}
class Division implements Strategy {
    @Override
    public double execute(double num1, double num2) {
        if (num2 == 0)
            throw new ArithmeticException();
        return num1 / num2;
    }
}
class Multiplication implements Strategy{
    @Override
    public double execute(double num1, double num2) {
        return num1 * num2;
    }
}
 class OperationNotSupported extends Exception {

    public OperationNotSupported(char operator) {
        super(String.format("The operator %c is not supported.\n", operator));
    }
}
class Subtraction implements Strategy{
    @Override
    public double execute(double num1, double num2) {
        return num1 - num2;
    }
}