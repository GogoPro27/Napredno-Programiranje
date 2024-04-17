package Napredno_Programiranje.AUD.aud4.aud41;

import java.util.Arrays;

public class Bank {
    protected Account [] accounts;
    protected int totalAmount;
    protected int maxAccount;

    public Bank(int maxAccount) {
       totalAmount = 0;
       this.maxAccount = maxAccount;
       accounts = new Account[maxAccount];
    }

    public void addAccount(Account newAcc){
        if(totalAmount==maxAccount){
            maxAccount*=2;
            accounts = Arrays.copyOf(accounts,maxAccount);//jakoooo
        }
        accounts[totalAmount++] = newAcc;

    }

    public double totalAssets(){
        double total = 0;
        for(Account a : accounts)total+=a.getCurrentAmount();
        return total;
    }

    public void addInterestToAllAccounts(){
        for(Account acc : accounts){
            if(acc.canHaveInterest()){
                InterestBearingAccount InterestBearingAccount = (InterestBearingAccount) acc;
                InterestBearingAccount.addInterest();
            }

        }
    }
}
