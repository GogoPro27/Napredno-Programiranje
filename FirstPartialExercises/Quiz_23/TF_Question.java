package Napredno_Programiranje.FirstPartialExercises.Quiz_23;

public class TF_Question extends Question{
    Boolean answer;

    public TF_Question(String type, String text, int points,Boolean answer) {
        super(type, text,points);
        this.answer = answer;
    }

    @Override
    public String getType() {
        return "TF";
    }

    @Override
    public String getAnswer() {
        return answer.toString();
    }

    @Override
    public double checkAnswerQuestion(String a) {
        if (Boolean.parseBoolean(a)==answer)return getPoints();
        else return 0;

    }

    @Override
    public String toString() {
        return "True/False Question: "+getText()+" Points: "+getPoints()+" Answer: "+answer;
    }
}
