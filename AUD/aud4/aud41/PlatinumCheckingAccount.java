package Napredno_Programiranje.AUD.aud4.aud41;

public class PlatinumCheckingAccount extends InterestCheckingAccount{
    public PlatinumCheckingAccount(String name, int balance) {
        super(name, balance);
    }

    @Override
    public void addInterest() {
        this.currentAmount*=(1+INTEREST_RATE*2);
    }
}
