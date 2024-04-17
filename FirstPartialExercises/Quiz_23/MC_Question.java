package Napredno_Programiranje.FirstPartialExercises.Quiz_23;

public class MC_Question extends Question{
   private final String answer;

    public MC_Question(String type, String text,int points, String answer) throws InvalidOperationException {
        super(type, text,points);//??
        if(!checkAnswer(answer))throw new InvalidOperationException(answer);
        this.answer = answer;
    }

    public boolean checkAnswer(String a){
        if(a.charAt(0)<'A'||a.charAt(0)>'E')return false;
        return true;
    }

    @Override
    public String getType() {
        return "MC";
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Multiple Choice Question: "+getText()+" Points "+getPoints()+" Answer: "+answer;
    }
    public double checkAnswerQuestion(String a){
        if(a.equals(answer))return getPoints();
        else return getPoints()*-0.2;
    }

}



