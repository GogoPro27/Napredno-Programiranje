package Napredno_Programiranje.FirstPartialExercises.Applicant_12;

import java.util.Scanner;

/**
 * I partial exam 2016
 */
public class ApplicantEvaluationTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int creditScore = scanner.nextInt();
        int employmentYears = scanner.nextInt();
        boolean hasCriminalRecord = scanner.nextBoolean();
        int choice = scanner.nextInt();
        Applicant applicant = new Applicant(name, creditScore, employmentYears, hasCriminalRecord);
        Evaluator.TYPE type = Evaluator.TYPE.values()[choice];
        Evaluator evaluator = null;
        try {
            evaluator = EvaluatorBuilder.build(type);
            System.out.println("Applicant");
            System.out.println(applicant);
            System.out.println("Evaluation type: " + type.name());
            if (evaluator.evaluate(applicant)) {
                System.out.println("Applicant is ACCEPTED");
            } else {
                System.out.println("Applicant is REJECTED");
            }
        } catch (InvalidEvaluation invalidEvaluation) {
            System.out.println("Invalid evaluation");
        }
    }
}

class Applicant {
    private String name;

    private int creditScore;
    private int employmentYears;
    private boolean hasCriminalRecord;

    public Applicant(String name, int creditScore, int employmentYears, boolean hasCriminalRecord) {
        this.name = name;
        this.creditScore = creditScore;
        this.employmentYears = employmentYears;
        this.hasCriminalRecord = hasCriminalRecord;
    }

    public String getName() {
        return name;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int getEmploymentYears() {
        return employmentYears;
    }

    public boolean hasCriminalRecord() {
        return hasCriminalRecord;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nCredit score: %d\nExperience: %d\nCriminal record: %s\n",
                name, creditScore, employmentYears, hasCriminalRecord ? "Yes" : "No");
    }
}

interface Evaluator {
    enum TYPE {
        NO_CRIMINAL_RECORD,
        MORE_EXPERIENCE,
        MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE,
        MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE,
        INVALID // should throw exception
    }

    boolean evaluate(Applicant applicant) throws InvalidEvaluation;
}

class EvaluatorBuilder implements Evaluator{
    TYPE type;
    public EvaluatorBuilder(TYPE type) {
        this.type = type;
    }

    public static Evaluator build(TYPE type) throws InvalidEvaluation {
            if(type==TYPE.INVALID)throw new InvalidEvaluation();
            else return new EvaluatorBuilder(type);

        // вашиот код овде

    }

    @Override
    public boolean evaluate(Applicant applicant) throws InvalidEvaluation {
        if(type==TYPE.NO_CRIMINAL_RECORD){
            if(!applicant.hasCriminalRecord())return true;
        }
        if(type==TYPE.MORE_EXPERIENCE){
            if(applicant.getEmploymentYears()>=10)return true;
        }
        if(type==TYPE.MORE_CREDIT_SCORE){
            if(applicant.getCreditScore()>=500)return true;
        }
        if(type==TYPE.NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE){
            return EvaluatorBuilder.build(TYPE.NO_CRIMINAL_RECORD).evaluate(applicant)&&EvaluatorBuilder.build(TYPE.MORE_EXPERIENCE).evaluate(applicant);
        }if(type==TYPE.MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE){
            return EvaluatorBuilder.build(TYPE.MORE_EXPERIENCE).evaluate(applicant)&&EvaluatorBuilder.build(TYPE.MORE_CREDIT_SCORE).evaluate(applicant);
        }
        if(type==TYPE.NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE){
            return EvaluatorBuilder.build(TYPE.NO_CRIMINAL_RECORD).evaluate(applicant)&&EvaluatorBuilder.build(TYPE.MORE_CREDIT_SCORE).evaluate(applicant);
        }
        return false;
    }
}


// имплементација на евалуатори овде


