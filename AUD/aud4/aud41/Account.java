package Napredno_Programiranje.AUD.aud4.aud41;

abstract public class Account {
    protected String name;
    protected long id;
    protected double currentAmount;

    public static long idSeed = 1;

    public Account(String name, int balance) {
        this.name = name;//ona go prai samo so ednakvo, zasto?? ne li e posigurno vaka??
        this.currentAmount = balance;
        id = idSeed++;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void deposit(double amount){
        currentAmount +=amount;
    }
    public void withdraw(double amount){
        if(currentAmount>=amount) {
            currentAmount -=amount;
        }
       // else throw new InsufficientAmountException(amount);
    }

    @Override
    public String toString() {
        return String.format("%d: %.2f", id , currentAmount);
    }
    abstract boolean canHaveInterest();

}
