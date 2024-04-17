package Napredno_Programiranje.AUD.aud4.aud41;

public class InterestCheckingAccount extends Account implements InterestBearingAccount{
    public static double INTEREST_RATE = 0.03;

    public InterestCheckingAccount(String name, int balance) {
        super(name, balance);
    }

    @Override
    boolean canHaveInterest() {
        return true;
    }

    @Override
    public void addInterest() {
        this.currentAmount*=(1+INTEREST_RATE);
    }

}
