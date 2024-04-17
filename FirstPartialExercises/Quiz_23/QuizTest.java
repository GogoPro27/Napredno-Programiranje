package Napredno_Programiranje.FirstPartialExercises.Quiz_23;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizTest {
    public static void main(String[] args) throws InvalidOperationException {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            }catch (InvalidOperationException e){
                e.message();
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}