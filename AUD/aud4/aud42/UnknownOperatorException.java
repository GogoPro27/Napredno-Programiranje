package Napredno_Programiranje.AUD.aud4.aud42;

public class UnknownOperatorException extends Exception{

    public UnknownOperatorException(char operator) {
        super(String.format("This operator %c is not valid",operator));
    }
}
