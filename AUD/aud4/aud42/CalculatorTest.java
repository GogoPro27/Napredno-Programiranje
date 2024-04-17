package Napredno_Programiranje.AUD.aud4.aud42;

import java.util.Scanner;

public class CalculatorTest {

    public static char getCharLower(String line){
        if(!line.trim().isEmpty()){
            return  Character.toLowerCase(line.charAt(0));
        }
        return '?';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){

            Calculator calculator = new Calculator();
            System.out.println(calculator.innit());
            while(true){
                String line = scanner.nextLine();
                char choice = getCharLower(line);
                if(choice == 'r'){
                    System.out.println(String.format("final result = %f",calculator.getRezultat()));
                    break;
                }
                String [] parts = line.split("\\s");
                char operator = parts[0].charAt(0);
                double value = Double.parseDouble(parts[1]);


                try {
                    String result = calculator.execute(operator,value);
                    System.out.println(result);
                    System.out.println(calculator);
                } catch (UnknownOperatorException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("(Y/N)");
            String line = scanner.nextLine();
            if (getCharLower(line) ==  'n')break;
        }
    }
}
