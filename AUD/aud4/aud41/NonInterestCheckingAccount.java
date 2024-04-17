package Napredno_Programiranje.AUD.aud4.aud41;

public class NonInterestCheckingAccount extends Account{
    public NonInterestCheckingAccount(String name, int balance) {
        super(name, balance);
    }

    @Override
    boolean canHaveInterest() {
        return false;
    }
}
