package Napredno_Programiranje.LABS.LABS1.LABS1_3;

import java.util.Arrays;
import java.util.Objects;

public class Bank {
    private Account[] accounts;
    private String bankName;
    private double totalTransfersAmount;
    private double totalProvisionAmount;

    public Bank(String name, Account []accounts){
        this.bankName = new String(name);
        this.accounts = Arrays.copyOf(accounts,accounts.length);
        totalTransfersAmount =0;
        totalProvisionAmount =0;
    }
    public boolean makeTransaction(Transaction t){
        int sender=-1,reciever=-1;
        for(int i=0;i<accounts.length;i++){
                if(accounts[i].getId()==t.getFromId())sender=i;
                if(accounts[i].getId()==t.getToId())reciever=i;
        }
        if(sender!=-1 && reciever!=-1){
            if(accounts[sender].getDoubleBalance()>=t.getTotalAmount()){
                Double newBalanceSender = accounts[sender].getDoubleBalance() - t.getTotalAmount();
                accounts[sender].setBalance(newBalanceSender.toString());

                Double newBalanceReciever = accounts[reciever].getDoubleBalance() + t.getDoubleAmount();
                accounts[reciever].setBalance(newBalanceReciever.toString());

                totalTransfersAmount +=t.getDoubleAmount();
                if(t.getDescription().equals("FlatAmount")){
                    totalProvisionAmount += ((FlatAmountProvisionTransaction)t).getDoubleFlatAmount();
                }else{
                    totalProvisionAmount += ((FlatPercentProvisionTransaction)t).getPercent()/100.00 * t.getDoubleAmount();
                }
                return true;
            }else return false;
        }else return false;
    }
    public String totalTransfers(){
        return String.format("%.2f$", totalTransfersAmount);
    }
    public String totalProvision(){
        return String.format("%.2f$", totalProvisionAmount);
    }

    public Account[] getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        String s = "Name: "+ this.bankName +"\n\n";
        for(Account a : accounts){
            s+=a.toString();
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Double.compare(totalTransfersAmount, bank.totalTransfersAmount) == 0 && Double.compare(totalProvisionAmount, bank.totalProvisionAmount) == 0 && Arrays.equals(accounts, bank.accounts) && Objects.equals(bankName, bank.bankName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(bankName, totalTransfersAmount, totalProvisionAmount);
        result = 31 * result + Arrays.hashCode(accounts);
        return result;
    }
}
