package Napredno_Programiranje.FirstPartialExercises.MojDDV_15;

public class MojDDVTest {

    public static void main(String[] args) throws AmountNotAllowedException {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");

        try {
            mojDDV.readRecords(System.in);
        }catch (AmountNotAllowedException e){
            e.message();
        }
        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}
