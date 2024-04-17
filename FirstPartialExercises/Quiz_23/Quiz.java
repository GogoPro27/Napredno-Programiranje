package Napredno_Programiranje.FirstPartialExercises.Quiz_23;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Quiz {
    private List<Question>questions;

    public Quiz() {
        questions = new ArrayList<>();
    }
    public void addQuestion(String questionData) throws InvalidOperationException {
        String[] parts = questionData.split(";");
        if(parts[0].equals("MC")){
            try {
                questions.add(new MC_Question(parts[0],parts[1],Integer.parseInt(parts[2]),parts[3]));
            }catch (InvalidOperationException e){
                e.message();
            }
        }else{
            questions.add(new TF_Question(parts[0],parts[1],Integer.parseInt(parts[2]),Boolean.parseBoolean(parts[3])));//dali raboti
        }
    }
    public void printQuiz(OutputStream os){
        PrintWriter pw = new PrintWriter(os);

        questions.stream().sorted(Comparator.naturalOrder()).forEach(pw::println);

        pw.flush();
        pw.close();
    }
    public void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException {
        if(answers.size()>questions.size())throw new InvalidOperationException();
        PrintWriter pw = new PrintWriter(os);
        double totalPoints = 0;
        for (int i=0;i<questions.size();i++){
            double currPoints=0;
//            if (answers.get(i).equals(questions.get(i).getAnswer())){
//                currPoints+=questions.get(i).getPoints();
//            }else if(questions.get(i).getType().equals("TF"))currPoints-= 0.2*questions.get(i).getPoints();
            currPoints+=questions.get(i).checkAnswerQuestion(answers.get(i));
            pw.println(String.format("%d. %.2f",i+1,currPoints));
            totalPoints+=currPoints;
            currPoints=0;
        }
        pw.println(String.format("Total points: %.2f",totalPoints));
        pw.flush();
        pw.close();
    }
}
