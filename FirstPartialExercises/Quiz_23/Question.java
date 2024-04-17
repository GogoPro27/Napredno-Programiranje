package Napredno_Programiranje.FirstPartialExercises.Quiz_23;

public abstract class Question implements Comparable<Question>{
    private final String type;
    private final String text;
    private final int points;

    public Question(String type, String text,int points) {
        this.type = type;
        this.text = text;
        this.points = points;
    }

    public abstract String getType();

    public int getPoints(){
        return points;
    }

    @Override
    public int compareTo(Question o) {
        if(getPoints()<o.getPoints())return 1;
        else if(getPoints()>o.getPoints())return -1;
        return 0;
    }

    public String getText() {
        return text;
    }
    public abstract double checkAnswerQuestion(String a);

    protected abstract String getAnswer() ;
}

