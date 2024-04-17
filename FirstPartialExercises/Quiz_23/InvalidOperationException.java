package Napredno_Programiranje.FirstPartialExercises.Quiz_23;

public class InvalidOperationException extends Exception{
    String s;

    public InvalidOperationException() {
        s="Answers and questions must be of same length!";
    }

    public InvalidOperationException(String s) {
        this.s = s+ " is not allowed option for this question";
    }
    public void message(){
        System.out.println(s);
    }
}
